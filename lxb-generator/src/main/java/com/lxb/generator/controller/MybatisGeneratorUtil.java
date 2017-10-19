package com.lxb.generator.controller;

import com.lxb.common.utils.AESUtil;
import com.lxb.common.utils.JdbcUtil;
import com.lxb.common.utils.PropertiesFileUtil;
import com.lxb.common.utils.StringUtil;
import com.lxb.generator.utils.VelocityUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description mybatis 模板生成工具类
 * @Author Liaoxb
 * @Date 2017/9/30 10:56:56
 */
public class MybatisGeneratorUtil {

    // generatorConfig 模板路径
    private static String generatorConfig_vm = "/template/generatorConfig.vm";
    /**
     * 获取 .vm 模板
     * @return
     */
    private static List<String> getTemplate(){
        List<String> list = new ArrayList<>();
        list.add("template2/Controller.java.vm");
        list.add("template2/Dao.java.vm");
        list.add("template2/Dao.xml.vm");
        list.add("template2/Entity.java.vm");
        list.add("template2/list.html.vm");
        list.add("template2/list.js.vm");
        list.add("template2/menu.sql.vm");
        list.add("template2/Service.java.vm");
        list.add("template2/ServiceImpl.java.vm");
        return list;
    }
    /**
     * 获取 .vm 模板
     * @return
     */
    private static List<String> getConfigTemplate(){
        List<String> list = new ArrayList<>();
        list.add("template1/db.properties.vm");
        list.add("template1/log4j.properties.vm");
        list.add("template1/spring-mvc.xml.vm");
        list.add("template1/spring-mybatis.xml.vm");
        list.add("template1/web.xml.vm");
        return list;
    }

    // 当前代码生成器module名称
    static String generatorName = PropertiesFileUtil.getInstance("generator").get("generator.module.name");


    /**
     * 根据模板生成 配置文件文件
     *
     * @param jdbc_driver 驱动路径
     * @param jdbc_url 链接
     * @param jdbc_username 帐号
     * @param jdbc_password 密码
     * @param module 项目模块
     * @param package_name 包名
     */
    public static void createConfigFile(String jdbc_driver, String jdbc_url, String jdbc_username, String jdbc_password,
                                        String module,String package_name){
        VelocityContext context = new VelocityContext();
        String mapper_path = package_name + ".dao.mapper";
        String dao_path = package_name + ".dao";

        context.put("module", module);// module 名称
        context.put("package", package_name);// 包路径
        context.put("jdbc_driver", jdbc_driver);// 数据库驱动
        context.put("jdbc_url", jdbc_url);// 数据库路径
        context.put("jdbc_username", jdbc_username);// 数据库登录名
        context.put("jdbc_password", jdbc_password);// 数据库登录密码
        context.put("mapper_path", mapper_path);// mapper.xml文件目录
        context.put("dao_path", dao_path);// dao接口目录

        String rootPath = getRootModulePath() + "src"+ File.separator + "main" + File.separator;
        if(StringUtils.isNotBlank(module)){
            rootPath = getRootModulePath() + module + File.separator + "src"+ File.separator + "main" + File.separator;
        }
        String resourcesPath = rootPath + "resources" + File.separator;
        String webinfoPath = rootPath + "webapp" + File.separator + "WEB-INF" + File.separator;
        try {
            for (String configName: getConfigTemplate()){
                String filePath = resourcesPath + configName.substring(configName.indexOf("/")+1, configName.lastIndexOf("."));
                if (configName.contains("web.xml")){
                    filePath = webinfoPath + configName.substring(configName.indexOf("/")+1, configName.lastIndexOf("."));
                }
                File file = new File(filePath);
                if (file.exists()){
                    file.delete();
                }
                VelocityUtil.generate(configName, filePath, context);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据模板生成generatorConfig.xml文件
     *
     * @param jdbc_driver 驱动路径
     * @param jdbc_url 链接
     * @param jdbc_username 帐号
     * @param jdbc_password 密码
     * @param module 项目模块
     * @param database 数据库
     * @param table_prefix 表前缀
     * @param package_name 包名
     */
    public static void generator(String jdbc_driver, String jdbc_url, String jdbc_username, String jdbc_password,
                                 String module, String database, String table_prefix, String package_name,
                                 Map<String, String> last_insert_id_tables) throws Exception {
        // 查询定制前缀项目的所有表
        List<Map<String, Object>> tables = getTables(jdbc_driver, jdbc_url, jdbc_username, jdbc_password, database, table_prefix);
        /* 1. 生成generatorConfig.xml 文件
        *  2. 运行generator 生成 model，dao（dao,mapper）
        * */
        createGeneratorConfig(jdbc_password, module, package_name, last_insert_id_tables, tables);

        // 删除旧代码
        for (String temp : getTemplate()){
            String path = getFileName(temp, "", package_name, module);
            if (path == null ) continue;
            path = path.substring(0, path.lastIndexOf(File.separator));
            deleteDir(new File(path));
            new File(path).mkdirs();
        }
        System.out.println("========== 开始生成 Controller、service、impl、js、page ==========");
        for (int i = 0; i < tables.size(); i++) {
            String TableName = StringUtil.lineToHump(toString(tables.get(i).get("table_name")));// 大驼峰
            String tableName = StringUtil.toLowerCaseFirstOne(TableName); // 小驼峰
            String createDate = new SimpleDateFormat("yyyy/M/d").format(new Date());
            String tableComment = tables.get(i).get("table_comment")+" "+ TableName;
            // 封装模板数据
            VelocityContext context = new VelocityContext();
            context.put("package", package_name);// 包路径
            context.put("tableComment", tableComment);// 表注释
            context.put("className", TableName);    // 表名称（类名）
            context.put("classname", tableName);// 首字母小写（小驼峰）
            context.put("pathName", tableName);// controller 请求前缀
//            context.put("pk.attrname",null);// 表主键字段
            context.put("author", "Liaoxb");    // 作者
            context.put("createDate", createDate);   // 创建时间

            for (String temp : getTemplate()){
                String path = getFileName(temp, TableName, package_name, module);
                if (path == null ) continue;
                File file = new File(path);
                if (file.exists()){
                    deleteDir(file);
                }
                VelocityUtil.generate(temp, path, context);
            }
        }
        System.out.println("========== 结束生成 Controller、service、impl、js、page ==========");
    }

    /**
     * 获取文件名<br/>
     * 指定 Controller、service、impl、js、page目录
     */
    private static String getFileName(String template, String className, String packageName,String moduleName){
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

        if(template.contains("Controller.java.vm")){
            return javaPath + "controller" + File.separator + className + "Controller.java";
        }

        if(template.contains("Service.java.vm")){
            return javaPath + "service" + File.separator + className + "Service.java";
        }

        if(template.contains("ServiceImpl.java.vm")){
            return javaPath + "service" + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
        }

/*        if(template.contains("Entity.java.vm")){
            return javaPath + "entity" + File.separator + className + "Entity.java";
        }

        if(template.contains("Dao.java.vm")){
            return javaPath + "dao" + File.separator + className + "Dao.java";
        }

        if(template.contains("Dao.xml.vm")){
            return resourcesPath + "dao" + File.separator + className + "Dao.xml";
        }*/

        if(template.contains("list.html.vm")){
            return pagePath +  className.toLowerCase() + ".html";
        }

        if(template.contains("list.js.vm")){
            return jsPath+  className.toLowerCase() + ".js";
        }

        if(template.contains("menu.sql.vm")){
            return resourcesPath + "sql" + File.separator +  className.toLowerCase() + "_menu.sql";
        }

        if(template.contains("log4j.properties.vm")){
            return resourcesPath + File.separator +  className.toLowerCase() + "_menu.sql";
        }

        return null;
    }

    private static void createGeneratorConfig(String jdbc_password, String module, String package_name,
                                         Map<String, String> last_insert_id_tables,List<Map<String, Object>> tables){
        String basePath = getRootModulePath();
        // 生成后的 generatorConfig.xml 文件所在路径
        String generatorConfig_xml =  basePath + generatorName + "//src/main/resources/generatorConfig.xml".replaceFirst("/", "");

        //========== 开始生成generatorConfig.xml文件 ==========
        try {
            VelocityContext context = new VelocityContext();

            String targetProject = basePath + module; // model 和 mapper接口 生成位置
            String targetProject_sqlMap = basePath + module; // mapper xml文件生成位置
            context.put("tables", tables);
            // generator生成文件所在包路径
            context.put("generator_javaModelGenerator_targetPackage", package_name + ".model");
            context.put("generator_sqlMapGenerator_targetPackage", package_name + ".dao.mapper");
            context.put("generator_javaClientGenerator_targetPackage", package_name + ".dao");
            // 文件生成目标项目（project，或则module 路径）
            context.put("targetProject", targetProject);
            context.put("targetProject_sqlMap", targetProject_sqlMap);
            context.put("generator_jdbc_password", AESUtil.AESDecode(jdbc_password));
            context.put("last_insert_id_tables", last_insert_id_tables);

            generatorConfig_xml = URLDecoder.decode(generatorConfig_xml, "UTF-8"); // 解码
            VelocityUtil.generate(generatorConfig_vm, generatorConfig_xml, context);
            // 删除旧代码
            deleteDir(new File(targetProject + "/src/main/java/" + package_name.replaceAll("\\.", "/") + "/model"));
            deleteDir(new File(targetProject + "/src/main/java/" + package_name.replaceAll("\\.", "/") + "/dao/mapper"));
            deleteDir(new File(targetProject_sqlMap + "/src/main/java/" + package_name.replaceAll("\\.", "/") + "/dao"));
            // ========== 结束生成generatorConfig.xml文件 ==========

            // ========== 开始运行MybatisGenerator ==========
            List<String> warnings = new ArrayList<>();
            File configFile = new File(generatorConfig_xml);
            ConfigurationParser cp = new ConfigurationParser(warnings);
            Configuration config = cp.parseConfiguration(configFile);
            DefaultShellCallback callback = new DefaultShellCallback(true);
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
            myBatisGenerator.generate(null); // 调用 mybatisGenerator 生成 model 和 mapper
            for (String warning : warnings) {
                System.out.println(warning);
            }
            // ========== 结束运行MybatisGenerator ==========
            // 删除前面生成的 generatorConfig.xml 文件
            new File(generatorConfig_xml).delete();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 查询定制前缀项目的所有表
     * @param jdbc_driver
     * @param jdbc_url
     * @param jdbc_username
     * @param jdbc_password
     * @param database
     * @param table_prefix
     * @throws SQLException
     */
    private static List<Map<String, Object>> getTables(String jdbc_driver, String jdbc_url, String jdbc_username, String jdbc_password, String database, String table_prefix) throws SQLException {
        List<Map<String, Object>> tables = new ArrayList<>();
        // 查询定制前缀项目的所有表
        JdbcUtil jdbcUtil = new JdbcUtil(jdbc_driver, jdbc_url, jdbc_username, AESUtil.AESDecode(jdbc_password));
        String sql = "SELECT table_name,table_comment FROM INFORMATION_SCHEMA.TABLES WHERE table_schema = '" + database + "' AND table_name LIKE '" + table_prefix + "_%';";
        List<Map> result = jdbcUtil.selectByParams(sql, null);
        for (Map map : result) {
            Map<String, Object> table = new HashMap<>();
            table.put("table_name", map.get("TABLE_NAME"));
            table.put("model_name", StringUtil.lineToHump(toString(map.get("TABLE_NAME"))));
            table.put("table_comment", map.get("TABLE_COMMENT"));
            tables.add(table);
        }
        jdbcUtil.release();
        return tables;
    }

    public static String toString(Object obj) {
        return obj == null ? "" : obj.toString();
    }

    /**
     * module 结构项目，获取顶级module路径
     * @return
     */
    public static String getRootModulePath(){
        String rootPath = MybatisGeneratorUtil.class.getClassLoader().getResource("").getPath();
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

