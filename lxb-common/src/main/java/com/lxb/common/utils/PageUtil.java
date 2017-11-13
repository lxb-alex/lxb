package com.lxb.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.List;

/**
 * @Description 分页工具类
 * @Author Liaoxb
 * @Date 2017/10/23 10:45:45
 */
public class PageUtil implements Serializable {
    private static final long serialVersionUID = 1L;

    private Logger logger = LoggerFactory.getLogger(PageUtil.class);

    // 总记录数
    private int total;
    // 当前页数
    private int page;
    // 每页显示数
    private int pagesize;
    // 返回记录数据
    private List rows;

    public PageUtil(int total, int page, int pagesize, List rows) {
        this.total = total;
        this.page = page;
        this.pagesize = pagesize;
        this.rows = rows;
    }

    public static int getTotalPage(int total, int pagesize){
        int totalPage = 0;
        if (total>0){
            totalPage = total%pagesize==0 ? total/pagesize : total/pagesize + 1;
        }
        return totalPage;
    }

    /**
     * 得到分页起始页数
     * @param page 当前列表显示页码
     * @param pageSize 每页显示记录数
     * @return
     */
    public static int getRowNum(int page, int pageSize){
        return (page-1)*pageSize;
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPagesize() {
        return pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }
}