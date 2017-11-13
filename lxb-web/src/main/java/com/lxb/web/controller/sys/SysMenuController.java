package com.lxb.web.controller.sys;

import com.lxb.common.utils.JsonUtil;

import com.lxb.common.utils.MessageVo;

import com.alibaba.fastjson.JSONObject;
import com.lxb.common.base.BaseController;
import com.lxb.common.utils.MessageVo;
import com.lxb.common.utils.PageUtil;
import com.lxb.common.utils.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

import com.lxb.web.entity.sys.SysMenuEntity;
import com.lxb.web.service.sys.SysMenuService;

/**
 * 目录表
 * 
 * @author Liaoxb
 * @date 2017/11/13
 */
@RestController
@RequestMapping("sys/menu")
public class SysMenuController extends  BaseController{
	@Autowired
	private SysMenuService sysMenuService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public JSONObject list(@RequestParam Map<String, Object> params){
        //查询列表数据
        Query query = new Query(params);

        int total = sysMenuService.getTotal(query); // 当前条件得到的总记录数
        List<SysMenuEntity> sysMenuList = null;
        int rowNum =  (query.getPage()-1)*query.getPageSize(); // 当前分页开始记录数
        if (total> rowNum){
				sysMenuList = sysMenuService.selectList(query);
        }

        PageUtil page = new PageUtil(total, query.getPage(), query.getPageSize(), sysMenuList);

        return JsonUtil.convertToJSONObject2(page);
    }

	/**
	 * 列表
	 */
	@RequestMapping("/getAll")
	public Object getAll(@RequestParam Map<String, Object> params){
		params.put("sidx","id");	// 排序字段
		params.put("order", "ASC");	// 排序方式
		params.put("group", "parent_id");	// 分组字段
		List<SysMenuEntity> list = sysMenuService.getAll(params);
		return JsonUtil.convertToJSONObject(list);
	}
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	public MessageVo info(@PathVariable("id") Integer id){
		SysMenuEntity sysMenu = sysMenuService.getSysMenuEntity(id);

        return new MessageVo(MessageVo.SUCCESS, sysMenu);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	public MessageVo save(@RequestBody SysMenuEntity sysMenu){
        int count = sysMenuService.save(sysMenu);

        return MessageVo.success();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	public MessageVo update(@RequestBody SysMenuEntity sysMenu){
        int count = sysMenuService.update(sysMenu);

        return MessageVo.success();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	public MessageVo delete(@RequestBody Integer[] ids){
        int count = sysMenuService.deleteBatch(ids);

        return MessageVo.success();
	}
	
}
