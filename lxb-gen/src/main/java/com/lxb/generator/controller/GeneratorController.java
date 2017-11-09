package com.lxb.generator.controller;

import com.lxb.generator.service.GeneratorService;
import org.apache.commons.lang.StringUtils;

/**
 * 代码生成器
 * 
 * @author Liaoxb
 * @email xylxiaobin@gmail.com
 * @date 2016年12月19日 下午9:12:58
 */
public class GeneratorController {
	public static void main(String[] args) {
		String tableNames[] = {"sys_menu"};
		String database = "lxb";
		String table_prefix = "sys";
		GeneratorService service = new GeneratorService();
		// 根据表前缀反向生成文件
		service.generatorCode(database, table_prefix);
		// 根据表名称集合反向生成文件
//		service.generatorCode(database, tableNames);
	}
}
