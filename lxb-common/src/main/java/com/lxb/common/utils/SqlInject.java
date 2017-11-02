package com.lxb.common.utils;

import java.io.File;

/**
 * @Description 防止SQL注入，过滤字符
 * @Author Liaoxb
 * @Date 2017/10/23 9:27:27
 */
public class SqlInject {
    /**
     * 判断是否存在SQL注入。true：存在注入
     * @param str SQL字符串
     * @return true：存在注入，false：不存在
     */
    public static boolean isSqlInject(String str) {
        if (StringUtil.isBlank(str)) {
            return false;
        }
        str = str.toLowerCase(); // 转为小写
        // 注入字符过滤
        String inject_strs[] = {
                "chr","mid ","master ","truncate ","exec","execute","create ","insert ","select ","delete ",
                "update ","count","drop","char","declare","sitename","net user","xp_cmdshell",";","-","+","'",
                "like '", "where "," table","from","grant","use ","group_concat","column_name",
                "information_schema.columns","table_schema"
        };
        for (int i = 0; i <= inject_strs.length-1; i++) {
            if (str.indexOf(inject_strs[i]) >= 0) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        String str = "abcd" + File.separator + "cdef";
        String inject_str = "'|,|\\|\\\\|+|-";
        System.out.println(str.replace("\\", ""));
    }

}
