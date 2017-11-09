package com.lxb.generator.service;

import com.lxb.generator.dao.GeneratorDao;
import com.lxb.generator.utils.Generator;
import com.lxb.generator.utils.StringUtil;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class GeneratorService {
    /**
     * 查询表信息
     * @param table_name 表名称
     * @param dataBase 数据库
     * @return 表的详细信息
     */
    public List<Map<String, String>> queryColumns(String table_name, String dataBase) {
        try {
            GeneratorDao GeneratorDao = new GeneratorDao();
            return GeneratorDao.queryColumns(table_name, dataBase);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 反向生成文件
     * @param database 数据库
     * @param tableNames 需要反向生成文件的表名称集合
     */
    public void generatorCode(String database, String[] tableNames) {
        GeneratorDao dao = new GeneratorDao();
        try {
            List<Map<String, Object>> tables = dao.selectTableList(database, tableNames);
            // 删除之前生成的包结构
            Generator.deletePackage(null);
            for (int i = 0; i < tables.size(); i++) {
                String table_name = StringUtil.getString(tables.get(i).get("table_name"));// 得到的表名称为下划线命名

                //查询表信息
                Map<String, Object> table = tables.get(i);
                //查询列信息
                List<Map<String, String>> columns = queryColumns(table_name, database);
                //生成代码
                Generator.generatorCode(table, columns);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 反向生成文件
     * @param database 数据库
     * @param table_prefix 表前缀
     */
    public void generatorCode(String database, String table_prefix) {
        GeneratorDao dao = new GeneratorDao();
        try {
            List<Map<String, Object>> tables = dao.selectTableList(database, table_prefix);
            // 删除之前生成的包结构
            Generator.deletePackage(table_prefix);
            for (int i = 0; i < tables.size(); i++) {
                String table_name = StringUtil.getString(tables.get(i).get("table_name"));// 得到的表名称为下划线命名

                //查询表信息
                Map<String, Object> table = tables.get(i);
                //查询列信息
                List<Map<String, String>> columns = queryColumns(table_name, database);
                //生成代码
                Generator.generatorCode(table, columns, table_prefix);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
