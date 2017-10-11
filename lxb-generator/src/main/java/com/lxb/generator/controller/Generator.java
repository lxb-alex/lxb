package com.lxb.generator.controller;

import com.lxb.common.utils.PropertiesFileUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author Liaoxb
 * @Date 2017/9/30 15:45:45
 */
public class Generator {

    // 根据命名规范，只修改此常量值即可
    // module
    private static String MODULE = "lxb-web";
    // 数据库
    private static String DATABASE = "lxb";
    // 表前缀
    private static String TABLE_PREFIX = "sys";
    // 包名称
    private static String PACKAGE_NAME = "com.lxb";
    private static String JDBC_DRIVER = PropertiesFileUtil.getInstance("generator").get("generator.jdbc.driver");
    private static String JDBC_URL = PropertiesFileUtil.getInstance("generator").get("generator.jdbc.url");
    private static String JDBC_USERNAME = PropertiesFileUtil.getInstance("generator").get("generator.jdbc.username");
    private static String JDBC_PASSWORD = PropertiesFileUtil.getInstance("generator").get("generator.jdbc.password");
    // 需要insert后返回主键的表配置，key:表名,value:主键名
    private static Map<String, String> LAST_INSERT_ID_TABLES = new HashMap<>();

    static {
        LAST_INSERT_ID_TABLES.put("sys_user", "id");
    }

    /**
     * 自动代码生成
     * @param args
     */
    public static void main(String[] args) throws Exception {
        MybatisGeneratorUtil.generator(JDBC_DRIVER, JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD, MODULE, DATABASE, TABLE_PREFIX, PACKAGE_NAME, LAST_INSERT_ID_TABLES);
    }
}