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

	/**
	 * 获取generator.properties配置文件中配置信息，返回Configuration对象
	 * @return  Configuration对象
	 */
	public static Configuration getConfig(){
		try {
			return new PropertiesConfiguration("generator.properties");
		} catch (ConfigurationException e) {
			throw new RuntimeException("获取配置文件失败，", e);
		}
	}

	/**
	 * 生成文件需要的模板文件
	 * @return list结构的模板文件名称
	 */
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
	 * 准备生成文件前的 文件内容填充封装
	 * @param table map结构的表信息
	 * @param columns list<map> 结构的 表字段信息
	 */
	public static void generatorCode(Map<String, Object> table, List<Map<String, String>> columns){
		generatorCode(table, columns, null);
	}

	/**
	 * 准备生成文件前的 文件内容填充封装
	 * @param table map结构的表信息
	 * @param columns list<map> 结构的 表字段信息
	 * @param table_prefix 表前缀
	 */
	public static void generatorCode(Map<String, Object> table, List<Map<String, String>> columns, String table_prefix){

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
		map.put("tablePrefix", "." + table_prefix); // 如果后面用方式一生成文件，则需要设置该值。若果为方式二，则不设置
		map.put("columns", tableEntity.getColumns());
		map.put("package", config.getString("package"));
		map.put("author", config.getString("author"));
		map.put("email", config.getString("email"));
		map.put("datetime", new SimpleDateFormat("yyyy/M/d").format(new Date()));
        VelocityContext context = new VelocityContext(map);

        //获取模板列表
		for(String templateName : templateNames){
			//一。 生成文件路径：packag.controller.表前缀.文件名.java
			createFile(ClassName, context, templateName, table_prefix);
			//二。 生成文件路径：packag.controller.文件名.java
//			createFile(ClassName, context, templateName, null);
		}
	}

	/**
	 * 生成文件
	 * @param ClassName 类名（文件名称）
	 * @param context 文件内容
	 * @param templateName 模板名称
	 * @param table_prefix 表前缀
	 */
	private static void createFile(String ClassName, VelocityContext context, String templateName, String table_prefix) {
		String filePath = getFileName(templateName, ClassName, config.getString("package"), config.getString("moduleName"), table_prefix);
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
	private static String getFileName(String templateName, String className, String packageName, String moduleName, String table_prefix){
		String page_js = null;
		if (StringUtil.isNotBlank(table_prefix)){
			table_prefix = File.separator + table_prefix;
			page_js = table_prefix + File.separator;
		}
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
			dirExists(javaPath + "controller" + table_prefix);
			return javaPath + "controller" + table_prefix + File.separator + className + "Controller.java";
		}

		if(templateName.contains("Service.java.vm")){
			dirExists(javaPath + "service" + table_prefix);
			return javaPath + "service" + table_prefix + File.separator + className + "Service.java";
		}

		if(templateName.contains("ServiceImpl.java.vm")){
			dirExists(javaPath + "service" + File.separator + "impl" + table_prefix);
			return javaPath + "service" + File.separator + "impl" + table_prefix + File.separator + className + "ServiceImpl.java";
		}

        if(templateName.contains("Entity.java.vm")){
			dirExists(javaPath + "entity" + table_prefix);
            return javaPath + "entity" + table_prefix + File.separator + className + "Entity.java";
        }

        if(templateName.contains("Dao.java.vm")){
			dirExists(javaPath + "dao" + table_prefix);
            return javaPath + "dao" + table_prefix + File.separator + className + "Dao.java";
        }

        if(templateName.contains("Dao.xml.vm")){
			dirExists(resourcesPath + "dao" + table_prefix);
            return resourcesPath + "dao" + table_prefix + File.separator + className + "Dao.xml";
        }

		if(templateName.contains("list.html.vm")){
			dirExists(pagePath + page_js);
			return pagePath + page_js +  className.toLowerCase() + ".html";
		}

		if(templateName.contains("list.js.vm")){
			dirExists(jsPath + page_js);
			return jsPath + page_js +  className.toLowerCase() + ".js";
		}

		if(templateName.contains("menu.sql.vm")){
			dirExists(resourcesPath + "sql" + table_prefix);
			return resourcesPath + "sql" + table_prefix + File.separator +  className.toLowerCase() + "_menu.sql";
		}

		if(templateName.contains("log4j.properties.vm")){
			dirExists(resourcesPath);
			return resourcesPath + File.separator +  className.toLowerCase() + "_menu.sql";
		}

		return null;
	}

	/**
	 * 删除文件或文件夹
	 * @param dir 文件、文件目录路径
	 */
	private static void dirExists(String dir) {
		File file = new File(dir);
		if (!file.exists()){
			file.mkdirs();
		}
	}

	/**
	 * 删除Controller、service、impl、dao、xml、page等等文件夹下与表名称相同的文件
	 * 如果表前缀 table_prefix 为空，则删除所有文件
	 * @param table_prefix 表前缀
	 */
	public static void deletePackage(String table_prefix) {
		if (StringUtil.isNotBlank(table_prefix)) table_prefix = File.separator + table_prefix;
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
		deleteDir(new File(javaPath + "controller" + table_prefix));
		deleteDir(new File(javaPath + "Service" + table_prefix));
		deleteDir(new File(javaPath + "Service" + File.separator + "impl" + table_prefix));
		deleteDir(new File(javaPath + "Entity" + table_prefix));
		deleteDir(new File(javaPath + "Dao" + table_prefix));
		deleteDir(new File(resourcesPath + "dao" + table_prefix));
		deleteDir(new File(resourcesPath + "sql" + table_prefix));
		deleteDir(new File(pagePath + table_prefix));
		deleteDir(new File(jsPath + table_prefix));
	}


	/**
	 * 递归删除非空文件夹
	 * @param dir 要删除的文件或文件目录
	 */
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
