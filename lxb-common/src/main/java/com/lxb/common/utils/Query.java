package com.lxb.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Description 封装列表查询参数
 * @Author Liaoxb
 * @Date 2017/10/23 10:16:16
 */
public class Query extends LinkedHashMap<String, Object> implements Serializable {
    private static final long serialVersionUID = 1L;

    private static Logger logger = LoggerFactory.getLogger(Query.class);

    private static final int DEFAULT_PC_PAGESIZE = 20;
    private static final int DEFAULT_PHONE_PAGESIZE = 10;
    private static final int DEFAULT_IPAD_PAGESIZE = 15;
    // 当前页码
    private int page;
    // 每页显示条数
    private int pageSize;

    public Query(Map<String, Object> params) {
        this.putAll(params);
        this.page = null == params.get("page") ? 1 : Integer.parseInt(params.get("page").toString());
        this.pageSize = null == params.get("pageSize") ? 10 : Integer.parseInt(params.get("pageSize").toString());
        this.put("offset", (page - 1) * pageSize);
        this.put("page", page);
        this.put("limit", pageSize); // 分页截止数 （mysql: limit #{offset}, #{pageSize}）

        // 排序参数
        // 防止SQL注入（因为sidx(排序字段)、order(排序规则)是通过拼接SQL实现排序的，会有SQL注入风险）
        // 默认登记时间排序
        String sidx = null == params.get("sortName") ? "createDate" : params.get("sortName").toString();
        String sortorder = null == params.get("sortorder") ? "ASC" : params.get("sortorder").toString();
        if (SqlInject.isSqlInject(sidx)) {
            logger.error("SQL参数中存在非法字符");
            this.put("sidx", "createDate");
        } else {
            this.put("sidx", sidx);
        }
        this.put("order", sortorder);
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
