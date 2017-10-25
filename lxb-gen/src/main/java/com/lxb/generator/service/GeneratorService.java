package com.lxb.generator.service;

import com.lxb.generator.dao.GeneratorDao;
import com.lxb.generator.utils.Generator;
import com.lxb.generator.utils.StringUtil;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class GeneratorService {

    public List<Map<String, String>> queryColumns(String table_name, String dataBase) {
        try {
            GeneratorDao GeneratorDao = new GeneratorDao();
            return GeneratorDao.queryColumns(table_name, dataBase);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void generatorCode(String database, String[] tableNames) {
        GeneratorDao dao = new GeneratorDao();
        try {
            List<Map<String, Object>> tables = dao.selectTableList(database, tableNames);
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

    public void generatorCode(String database, String table_prefix) {
        GeneratorDao dao = new GeneratorDao();
        try {
            List<Map<String, Object>> tables = dao.selectTableList(database, table_prefix);
            // 删除之前生成的包结构
            Generator.deletePackage();
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

}
