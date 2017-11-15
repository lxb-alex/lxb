package com.lxb.sys.controller.sys;

import com.lxb.common.utils.JsonUtil;

import com.lxb.common.utils.MessageVo;

import com.alibaba.fastjson.JSONObject;
import com.lxb.common.base.BaseController;
import com.lxb.common.utils.MessageVo;
import com.lxb.common.utils.PageUtil;
import com.lxb.common.utils.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Map;

import com.lxb.sys.entity.sys.SysLogEntity;
import com.lxb.sys.service.sys.SysLogService;

/**
 * 
 * 
 * @author Liaoxb
 * @date 2017/11/15
 */
@RestController
@RequestMapping("sys/log")
public class SysLogController extends  BaseController{
    private static Logger _log = LoggerFactory.getLogger(SysLogController.class);
	@Autowired
	private SysLogService sysLogService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public JSONObject list(@RequestParam Map<String, Object> params){
        //查询列表数据
        Query query = new Query(params);

        int total = sysLogService.getTotal(query); // 当前条件得到的总记录数
        List<SysLogEntity> sysLogList = null;
        int rowNum =  (query.getPage()-1)*query.getPageSize(); // 当前分页开始记录数
        if (total> rowNum){
				sysLogList = sysLogService.selectList(query);
        }

        PageUtil page = new PageUtil(total, query.getPage(), query.getPageSize(), sysLogList);

        return JsonUtil.convertToJSONObject2(page);
    }
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	public MessageVo info(@PathVariable("id") Integer id){
		SysLogEntity sysLog = sysLogService.getSysLogEntity(id);

        return new MessageVo(MessageVo.SUCCESS, sysLog);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	public MessageVo save(@RequestBody SysLogEntity sysLog){
        int count = sysLogService.save(sysLog);

        return MessageVo.success();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	public MessageVo update(@RequestBody SysLogEntity sysLog){
        int count = sysLogService.update(sysLog);

        return MessageVo.success();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	public MessageVo delete(@RequestBody Integer[] ids){
        int count = sysLogService.deleteBatch(ids);

        return MessageVo.success();
	}
	
}
