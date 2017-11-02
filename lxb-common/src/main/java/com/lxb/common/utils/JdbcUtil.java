package com.lxb.common.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 基于原生jdbc的连接、关闭、修改、查询操作
 * @Author Liaoxb
 * @Date 2017/9/30 13:45:45
 */
public class JdbcUtil {
    private static Log logger = LogFactory.getLog(JdbcUtil.class);

    // 定义数据库的链接
    private Connection conn;
    // 定义sql语句的执行对象
    private PreparedStatement pstmt;
    // 定义查询返回的结果集合
    private ResultSet rs;

    /**
     * 初始化，基于jdbc连接数据库
     *  driver 数据库驱动
     *  url 数据库路径
     *  username 用户名
     *  password 密码
     * @Date 2017/9/30 15:29
     */
    public JdbcUtil() {
        try {
            String driver = PropertiesFileUtil.getPropertiesValue("generator.properties", "generator.jdbc.driver");
            String url = PropertiesFileUtil.getPropertiesValue("generator.properties", "generator.jdbc.url");
            String username = PropertiesFileUtil.getPropertiesValue("generator.properties", "generator.jdbc.username");
            String password = PropertiesFileUtil.getPropertiesValue("generator.properties", "generator.jdbc.password");
            Class.forName(driver);
            conn = DriverManager.getConnection(url, username, password);
            logger.debug("数据库连接成功");
        } catch (Exception e) {
            logger.error(e.getMessage()+" 数据库连接失败");
            e.printStackTrace();
        }
    }

    /**
     * 初始化，基于jdbc连接数据库
     * @param driver 数据库驱动
     * @param url 数据库路径
     * @param username 用户名
     * @param password 密码
     * @Date 2017/9/30 15:29
     */
    public JdbcUtil(String driver, String url, String username, String password) {
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, username, password);
            logger.debug("数据库连接成功");
        } catch (Exception e) {
            logger.error(e.getMessage()+" 数据库连接失败");
            e.printStackTrace();
        }
    }

    /**
     * 更新数据
     * @param sql 操作的sql
     * @param params 参数
     * @return Boolean
     * @throws SQLException
     */
    public boolean updateByParams(String sql, List params) throws SQLException {
        // 影响行数
        int result = -1;
        pstmt = conn.prepareStatement(sql);
        int index = 1;
        // 填充sql语句中的占位符
        if (null != params && !params.isEmpty()) {
            for (int i = 0; i < params.size(); i ++) {
                pstmt.setObject(index ++, params.get(i));
            }
        }
        result = pstmt.executeUpdate();
        return result > 0 ? true : false;
    }

    /**
     * 查询多条记录
     * @param sql 查询操作sql
     * @param params 查询参数
     * @return List<Map>
     */
    public List<Map> selectByParams(String sql, List params) throws SQLException {
        List<Map> list = new ArrayList<>();
        int index = 1;
        pstmt = conn.prepareStatement(sql);
        if (null != params && !params.isEmpty()) {
            for (int i = 0; i < params.size(); i ++) {
                pstmt.setObject(index++, params.get(i));
            }
        }
        rs = pstmt.executeQuery();
        ResultSetMetaData metaData = rs.getMetaData();
        int cols_len = metaData.getColumnCount();
        while (rs.next()) {
            Map map = new HashMap();
            for (int i = 0; i < cols_len; i ++) {
                String cols_name = metaData.getColumnName(i + 1);
                Object cols_value = rs.getObject(cols_name);
                if (null == cols_value) {
                    cols_value = "";
                }
                map.put(cols_name, cols_value);
            }
            list.add(map);
        }
        return list;
    }

    /**
     * 释放连接
     */
    public void release() {
        try {
            if (null != rs) rs.close();
            if (null != pstmt) pstmt.close();
            if (null != conn) conn.close();
        } catch (SQLException e) {
            logger.error(e.getMessage()+" 释放数据库连接错误");
            e.printStackTrace();
        }
        logger.debug("释放数据库连接");
    }

}
