package com.lxb.generator.controller;

import com.lxb.common.utils.AESUtil;
import com.lxb.common.utils.JdbcUtil;
import com.lxb.common.utils.PropertiesFileUtil;
import com.lxb.common.utils.StringUtil;
import com.lxb.generator.utils.VelocityUtil;
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
    // Controller 模板路径
    private static String controller_vm = "/template/Controller.vm";
    // Service 模板路径
    private static String service_vm = "/template/Service.vm";
    // ServiceImpl 模板路径
    private static String serviceImpl_vm = "/template/ServiceImpl.vm";

    // 当前代码生成器module名称
    static String generatorName = PropertiesFileUtil.getInstance("generator").get("generator.module.name");

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

        System.out.println("========== 开始生成Service ==========");
        // 指定 Controller、service、impl 目录
        String ctime = new SimpleDateFormat("yyyy/M/d").format(new Date());
        String controllerPath = getRootModulePath() + module + "/src/main/java/" + package_name.replaceAll("\\.", "/") + "/controller";
        String servicePath = getRootModulePath() + module + "/src/main/java/" + package_name.replaceAll("\\.", "/") + "/service";
        String serviceImplPath = getRootModulePath() + module + "/src/main/java/" + package_name.replaceAll("\\.", "/") + "/service/impl";
        deleteDir(new File(controllerPath));
        deleteDir(new File(servicePath));
        deleteDir(new File(serviceImplPath));
        new File(controllerPath).mkdirs();
        new File(servicePath).mkdirs();
        new File(serviceImplPath).mkdirs();

        for (int i = 0; i < tables.size(); i++) {
            // 指定生成的文件 全路径名称
            String model = StringUtil.lineToHump(toString(tables.get(i).get("table_name")));
            String controller = controllerPath + "/" + model + "Controller.java";
            String service = servicePath + "/" + model + "Service.java";
            String serviceImpl = serviceImplPath + "/" + model + "ServiceImpl.java";
            // 赋值 .vm 文件中的参数
            VelocityContext context = new VelocityContext();
            context.put("package_name", package_name);
            context.put("model", model);
            context.put("author", "Liaoxb");
            context.put("ctime", ctime);
            // 生成 controller
            File controllerFile = new File(controller);
            if (!controllerFile.exists()) {
                VelocityUtil.generate(controller_vm, controller, context);
            }
            // 生成service
            File serviceFile = new File(service);
            if (!serviceFile.exists()) {
                VelocityUtil.generate(service_vm, service, context);
            }
            // 生成serviceImpl
            File serviceImplFile = new File(serviceImpl);
            if (!serviceImplFile.exists()) {
                context.put("mapper", StringUtil.toLowerCaseFirstOne(model));
                VelocityUtil.generate(serviceImpl_vm, serviceImpl, context);
            }
        }
        System.out.println("========== 结束生成Service ==========");
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
        String sql = "SELECT table_name FROM INFORMATION_SCHEMA.TABLES WHERE table_schema = '" + database + "' AND table_name LIKE '" + table_prefix + "_%';";
        List<Map> result = jdbcUtil.selectByParams(sql, null);
        for (Map map : result) {
            Map<String, Object> table = new HashMap<>();
            table.put("table_name", map.get("TABLE_NAME"));
            table.put("model_name", StringUtil.lineToHump(toString(map.get("TABLE_NAME"))));
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

