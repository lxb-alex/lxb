package com.lxb.common.utils;

import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author Liaoxb
 * @Date 2017/11/13 0013 14:15:15
 */
public class JsonUtil {

    /**
     * 将分页工具类对象转换为json对象, 返回json数据<br/>
     *  对大小写有要求的（如：ligerUi）
     * @param page 实体类
     * @return
     */
    public static JSONObject convertToJSONObject(PageUtil page){
        JSONObject obj = new JSONObject();
        obj.put("Total", page.getTotal());
        obj.put("page", page.getPage());
        obj.put("pagesize", page.getPagesize());
        obj.put("totalPage", PageUtil.getTotalPage(page.getTotal(), page.getPagesize()));
        if (page.getRows()!=null){
            obj.put("Rows", page.getRows());
        }
        return obj;
    }
    /**
     * 将分页工具类对象转换为json对象, 返回json数据<br/>
     * @param page 实体类
     * @return
     */
    public static JSONObject convertToJSONObject2(PageUtil page){
        JSONObject obj = new JSONObject();
        obj.put("totalCount", page.getTotal());
        obj.put("currPage", page.getPage());
        obj.put("pageSize", page.getPagesize());
        obj.put("totalPage", PageUtil.getTotalPage(page.getTotal(), page.getPagesize()));
        if (page.getRows()!=null){
            obj.put("list", page.getRows());
        }
        return obj;
    }

    public static JSONObject convertToJSONObject(List list){
        JSONObject json = new JSONObject();
        json.put("list", list);
        return json;
    }

    public static JSONObject convertToJSONObject(Map map){
        JSONObject json = new JSONObject();
        json.put("map", map);
        return json;
    }

    public static JSONObject convertToJSONObject(Object object){
        JSONObject json = new JSONObject();
        json.put("obj", object);
        return json;
    }

}
