package com.lxb.web.controller.sys;

import com.alibaba.fastjson.JSONObject;
import com.lxb.common.base.BaseController;
import com.lxb.common.utils.MessageVo;
import com.lxb.common.utils.PageUtil;
import com.lxb.common.utils.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

import com.lxb.web.entity.sys.SysRoleEntity;
import com.lxb.web.service.sys.SysRoleService;

/**
 * 角色表
 * 
 * @author Liaoxb
 * @date 2017/11/10
 */
@RestController
@RequestMapping("sys/role")
public class SysRoleController extends  BaseController{
	@Autowired
	private SysRoleService sysRoleService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public JSONObject list(@RequestParam Map<String, Object> params){
        //查询列表数据
        Query query = new Query(params);

        int total = sysRoleService.getTotal(query); // 当前条件得到的总记录数
        List<SysRoleEntity> sysRoleList = null;
        int rowNum =  (query.getPage()-1)*query.getPageSize(); // 当前分页开始记录数
        if (total> rowNum){
				sysRoleList = sysRoleService.selectList(query);
        }

        PageUtil page = new PageUtil(total, query.getPage(), query.getPageSize(), sysRoleList);

        return PageUtil.convertToJSONObject(page);
    }
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	public MessageVo info(@PathVariable("id") Integer id){
		SysRoleEntity sysRole = sysRoleService.getSysRoleEntity(id);

        return new MessageVo(MessageVo.SUCCESS, sysRole);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	public MessageVo save(@RequestBody SysRoleEntity sysRole){
		sysRoleService.save(sysRole);

        return new MessageVo(MessageVo.SUCCESS, null);
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	public MessageVo update(@RequestBody SysRoleEntity sysRole){
		sysRoleService.update(sysRole);

        return new MessageVo(MessageVo.SUCCESS, null);
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	public MessageVo delete(@RequestBody Integer[] ids){
		sysRoleService.deleteBatch(ids);

        return new MessageVo(MessageVo.SUCCESS, null);
	}
	
}
