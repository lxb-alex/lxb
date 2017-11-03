package com.lxb.generator.utils;

import com.lxb.common.utils.PropertiesFileUtil;
import com.lxb.generator.entity.ColumnEntity;
import com.lxb.generator.entity.TableEntity;
import net.sf.jsqlparser.expression.operators.relational.ExistsExpression;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.output.FileWriterWithEncoding;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 代码生成器   工具类
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年12月19日 下午11:40:24
 */
public class Generator {
	// generator配置文件内容信息
	private static Configuration config = getConfig();
	// 模板名称集合
	private static List<String> templateNames = getTemplateNames();

	public static Configuration getConfig(){
		try {
			return new PropertiesConfiguration("generator.properties");
		} catch (ConfigurationException e) {
			throw new RuntimeException("获取配置文件失败，", e);
		}
	}

	public static List<String> getTemplateNames(){
		List<String> templateNames = new ArrayList<String>();
		templateNames.add("template/Entity.java.vm");
		templateNames.add("template/Dao.java.vm");
		templateNames.add("template/Dao.xml.vm");
		templateNames.add("template/Service.java.vm");
		templateNames.add("template/ServiceImpl.java.vm");
		templateNames.add("template/Controller.java.vm");
		templateNames.add("template/list.html.vm");
		templateNames.add("template/list.js.vm");
		templateNames.add("template/menu.sql.vm");
		return templateNames;
	}

	/**
	 * 生成代码
	 */
	public static void generatorCode(Map<String, Object> table, List<Map<String, String>> columns){

		//表信息
		TableEntity tableEntity = new TableEntity();
		String table_name = StringUtil.getString(table.get("table_name"));
		String ClassName = StringUtil.lineToPascal(table_name); // 大驼峰
		String className = StringUtil.lineToHump(table_name); 	// 小驼峰

		tableEntity.setTableName(table_name);
		tableEntity.setComments(StringUtil.getString(table.get("table_comment")));
		tableEntity.setClassName(ClassName);
		tableEntity.setClassname(className);

		//列信息
		List<ColumnEntity> columsList = new ArrayList<>();
		for(Map<String, String> column : columns){
			ColumnEntity columnEntity = new ColumnEntity();
			columnEntity.setColumnName(column.get("columnName"));
			columnEntity.setDataType(column.get("dataType"));
			columnEntity.setComments(column.get("columnComment"));
			columnEntity.setExtra(column.get("extra"));

			//列名转换成Java属性名
			String AttrName = StringUtil.lineToPascal(columnEntity.getColumnName());
			String attrName = StringUtil.lineToHump(columnEntity.getColumnName());
			columnEntity.setAttrName(AttrName);
			columnEntity.setAttrname(attrName);

			//列的数据类型，转换成Java类型
			String attrType = config.getString(columnEntity.getDataType(), "unknowType");
			columnEntity.setAttrType(attrType);

			//是否主键
			if("PRI".equalsIgnoreCase(column.get("columnKey")) && tableEntity.getPk() == null){
				tableEntity.setPk(columnEntity);
			}

			columsList.add(columnEntity);
		}
		tableEntity.setColumns(columsList);

		//没主键，则第一个字段为主键
		if(tableEntity.getPk() == null){
			tableEntity.setPk(tableEntity.getColumns().get(0));
		}

		//封装模板数据
		Map<String, Object> map = new HashMap<>();
		map.put("tableName", tableEntity.getTableName());
		map.put("comments", tableEntity.getComments());
		map.put("pk", tableEntity.getPk());
		map.put("className", tableEntity.getClassName());
		map.put("classname", tableEntity.getClassname());
		map.put("pathName", tableEntity.getTableName().replace("_", "/").toLowerCase());
		map.put("columns", tableEntity.getColumns());
		map.put("package", config.getString("package"));
		map.put("author", config.getString("author"));
		map.put("email", config.getString("email"));
		map.put("datetime", new SimpleDateFormat("yyyy/M/d").format(new Date()));
        VelocityContext context = new VelocityContext(map);

        //获取模板列表
		for(String templateName : templateNames){
			createFile(ClassName, context, templateName);
		}
	}

	private static void createFile(String ClassName, VelocityContext context, String templateName) {
		String filePath = getFileName(templateName, ClassName, config.getString("package"), config.getString("moduleName"));
		System.out.println("create file : " + filePath);
		if (filePath == null ) return;
		File createFile = new File(filePath);
		if (createFile.exists()){
            createFile.delete();
        }

		// 设置velocity资源加载器
		VelocityEngine ve = new VelocityEngine();
		ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		// 设置编码，解决中文乱码
		ve.setProperty(Velocity.INPUT_ENCODING, "utf8");
		ve.setProperty(Velocity.OUTPUT_ENCODING, "utf8");
		ve.init();

		Template template = ve.getTemplate(templateName);
		FileWriterWithEncoding writer = null;
		try {
            writer = new FileWriterWithEncoding(createFile, "utf-8");
            template.merge(context, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	/**
	 * module 结构项目，获取顶级module路径
	 * @return
	 */
	public static String getRootModulePath(){
		String rootPath = Generator.class.getClassLoader().getResource("").getPath();
		// 当前代码生成器module名称
		String generator = PropertiesFileUtil.getInstance("generator").get("generator.module.name");
		// getResource 方式获取的路径是以“/”开始的，所有从1开始
		rootPath = rootPath.substring(1, rootPath.indexOf(generator));
		try {
			return URLDecoder.decode(rootPath, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取文件名<br/>
	 * 指定 Controller、service、impl、js、page目录
	 */
	private static String getFileName(String templateName, String className, String packageName, String moduleName){
		String rootPath = getRootModulePath() + "src"+ File.separator + "main" + File.separator;
		if(StringUtils.isNotBlank(moduleName)){
			rootPath = getRootModulePath() + moduleName + File.separator + "src"+ File.separator + "main" + File.separator;
		}
		String javaPath = rootPath + "java" + File.separator;
		String resourcesPath =rootPath + "resources" + File.separator;
		String pagePath = rootPath + "webapp" + File.separator + "WEB-INF" + File.separator +"page"+ File.separator ;
		String jsPath = rootPath + "webapp" + File.separator +"js"+ File.separator;

		if(StringUtils.isNotBlank(packageName)){
			javaPath += packageName.replace(".", File.separator) + File.separator;
			resourcesPath += packageName.replace(".", File.separator) + File.separator;
		}

		if(templateName.contains("Controller.java.vm")){
			dirExists(javaPath + "controller");
			return javaPath + "controller" + File.separator + className + "Controller.java";
		}

		if(templateName.contains("Service.java.vm")){
			dirExists(javaPath + "service");
			return javaPath + "service" + File.separator + className + "Service.java";
		}

		if(templateName.contains("ServiceImpl.java.vm")){
			dirExists(javaPath + "service" + File.separator + "impl");
			return javaPath + "service" + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
		}

        if(templateName.contains("Entity.java.vm")){
			dirExists(javaPath + "entity");
            return javaPath + "entity" + File.separator + className + "Entity.java";
        }

        if(templateName.contains("Dao.java.vm")){
			dirExists(javaPath + "dao");
            return javaPath + "dao" + File.separator + className + "Dao.java";
        }

        if(templateName.contains("Dao.xml.vm")){
			dirExists(resourcesPath + "dao");
            return resourcesPath + "dao" + File.separator + className + "Dao.xml";
        }

		if(templateName.contains("list.html.vm")){
			dirExists(pagePath);
			return pagePath +  className.toLowerCase() + ".html";
		}

		if(templateName.contains("list.js.vm")){
			dirExists(jsPath);
			return jsPath+  className.toLowerCase() + ".js";
		}

		if(templateName.contains("menu.sql.vm")){
			dirExists(resourcesPath + "sql");
			return resourcesPath + "sql" + File.separator +  className.toLowerCase() + "_menu.sql";
		}

		if(templateName.contains("log4j.properties.vm")){
			dirExists(resourcesPath);
			return resourcesPath + File.separator +  className.toLowerCase() + "_menu.sql";
		}

		return null;
	}

	private static void dirExists(String dir) {
		File file = new File(dir);
		if (!file.exists()){
			file.mkdirs();
		}
	}

	public static void deletePackage() {

		String moduleName = config.getString("moduleName");
		String packageName = config.getString("package");
		String rootPath = getRootModulePath() + "src"+ File.separator + "main" + File.separator;
		if(StringUtils.isNotBlank(moduleName)){
			rootPath = getRootModulePath() + moduleName + File.separator + "src"+ File.separator + "main" + File.separator;
		}
		String javaPath = rootPath + "java" + File.separator;
		String resourcesPath =rootPath + "resources" + File.separator;
		String pagePath = rootPath + "webapp" + File.separator + "WEB-INF" + File.separator +"page"+ File.separator ;
		String jsPath = rootPath + "webapp" + File.separator +"js"+ File.separator;
		if(StringUtils.isNotBlank(packageName)){
			javaPath += packageName.replace(".", File.separator) + File.separator;
			resourcesPath += packageName.replace(".", File.separator) + File.separator;
		}
		deleteDir(new File(javaPath + "controller"));
		deleteDir(new File(javaPath + "Service"));
		deleteDir(new File(javaPath + "Entity"));
		deleteDir(new File(javaPath + "Dao"));
		deleteDir(new File(resourcesPath + "dao"));
		deleteDir(new File(resourcesPath + "sql"));
		deleteDir(new File(pagePath));
		deleteDir(new File(jsPath));
	}


	// 递归删除非空文件夹
	public static void deleteDir(File dir) {
		if (dir.isDirectory()) {
			File[] files = dir.listFiles();
			for (int i = 0; i < files.length; i++) {
				deleteDir(files[i]);
			}
		}
		dir.delete();
	}

}
