package com.lxb.sys.controller;

import com.lxb.common.utils.JsonUtil;

import com.lxb.common.utils.MessageVo;

import com.alibaba.fastjson.JSONObject;
import com.lxb.common.base.BaseController;
import com.lxb.common.utils.PageUtil;
import com.lxb.common.utils.Query;
import com.lxb.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Map;

import com.lxb.sys.entity.SysUserEntity;

/**
 * 系统用户表
 * 
 * @author Liaoxb
 * @date 2017/11/15
 */
@RestController
@RequestMapping("sys/user")
public class SysUserController extends  BaseController{
    private static Logger _log = LoggerFactory.getLogger(SysUserController.class);
	@Autowired
	private SysUserService sysUserService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public JSONObject list(@RequestParam Map<String, Object> params){
        //查询列表数据
        Query query = new Query(params);

        int total = sysUserService.getTotal(query); // 当前条件得到的总记录数
        List<SysUserEntity> sysUserList = null;
        int rowNum =  (query.getPage()-1)*query.getPageSize(); // 当前分页开始记录数
        if (total> rowNum){
				sysUserList = sysUserService.selectList(query);
        }

        PageUtil page = new PageUtil(total, query.getPage(), query.getPageSize(), sysUserList);

        return JsonUtil.convertToJSONObject2(page);
    }
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	public MessageVo info(@PathVariable("id") Long id){
		SysUserEntity sysUser = sysUserService.getSysUserEntity(id);

        return new MessageVo(MessageVo.SUCCESS, sysUser);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	public MessageVo save(@RequestBody SysUserEntity sysUser){
        int count = sysUserService.save(sysUser);

        return MessageVo.success();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	public MessageVo update(@RequestBody SysUserEntity sysUser){
        int count = sysUserService.update(sysUser);

        return MessageVo.success();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	public MessageVo delete(@RequestBody Long[] ids){
        int count = sysUserService.deleteBatch(ids);

        return MessageVo.success();
	}
	
}
