package com.lxb.generator.dao;

import com.lxb.common.utils.JdbcUtil;
import org.apache.commons.lang.StringUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代码生成器
 * @author Liaoxb
 * @email xylxiaobin@gmail.com
 * @date 2016年12月19日 下午3:32:04
 */
public class GeneratorDao {

	public List<Map<String, Object>> selectTableList(String database, String[] tableNames) throws SQLException {
		List<Map<String, Object>> tables = new ArrayList<>();
		// 查询定制前缀项目的所有表
		JdbcUtil jdbcUtil = new JdbcUtil();
		String temp = "\"" + StringUtils.join(tableNames, ",").replaceAll(",","\"\"")+"\"";
		String sql = "SELECT table_name,table_comment FROM INFORMATION_SCHEMA.TABLES WHERE table_schema = '" + database + "' AND table_name in(" + temp + ");";

		List<Map> result = jdbcUtil.selectByParams(sql, null);
		for (Map map : result) {
			Map<String, Object> table = new HashMap<>();
			table.put("table_name", map.get("TABLE_NAME"));
			table.put("table_comment", map.get("TABLE_COMMENT"));
			tables.add(table);
		}
		jdbcUtil.release();
		return tables;
	}

	public List<Map<String, Object>> selectTableList(String database, String table_prefix) throws SQLException {
		List<Map<String, Object>> tables = new ArrayList<>();
		// 查询定制前缀项目的所有表
		JdbcUtil jdbcUtil = new JdbcUtil();
		String sql = "SELECT table_name,table_comment FROM INFORMATION_SCHEMA.TABLES WHERE table_schema = '" + database + "' AND table_name LIKE '" + table_prefix + "_%';";

		List<Map> result = jdbcUtil.selectByParams(sql, null);
		for (Map map : result) {
			Map<String, Object> table = new HashMap<>();
			table.put("table_name", map.get("TABLE_NAME"));
			table.put("table_comment", map.get("TABLE_COMMENT"));
			tables.add(table);
		}
		jdbcUtil.release();
		return tables;
	}

	public List<Map<String, String>> queryColumns(String table_name, String dataBase) throws SQLException {
		String sql = "select column_name, data_type, column_comment, column_key, extra from information_schema.columns" +
				" where table_name = '"+ table_name +"' and table_schema = '" + dataBase + "' order by ordinal_position";
		List<Map<String, String>> list = new ArrayList<>();
		// 查询定制前缀项目的所有表
		JdbcUtil jdbcUtil = new JdbcUtil();

		List<Map> result = jdbcUtil.selectByParams(sql, null);
		for (Map map : result) {
			Map<String, String> table = new HashMap<>();
			table.put("columnName", (String) map.get("COLUMN_NAME"));
			table.put("dataType", (String) map.get("DATA_TYPE"));
			table.put("columnComment", (String) map.get("COLUMN_COMMENT"));
			table.put("columnKey", (String) map.get("COLUMN_KEY"));
			table.put("extra", (String) map.get("EXTRA"));
			list.add(table);
		}
		jdbcUtil.release();
		return list;
	}
}
