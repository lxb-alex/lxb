package com.lxb.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.lxb.common.base.BaseController;
import com.lxb.common.utils.MessageVo;
import com.lxb.common.utils.PageUtil;
import com.lxb.common.utils.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import com.lxb.web.entity.SysMenuEntity;
import com.lxb.web.service.SysMenuService;

/**
 * 目录表
 *
 * @author Liaoxb
 * @date 2017/10/31
 */
@RestController
@RequestMapping("sysmenu")
public class SysMenuController extends BaseController {
    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public JSONObject list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);

        int total = sysMenuService.getTotal(query); // 当前条件得到的总记录数
        List<SysMenuEntity> sysMenuList = null;
        int rowNum = (query.getPage() - 1) * query.getPageSize(); // 当前分页开始记录数
        if (total > rowNum) {
            sysMenuList = sysMenuService.selectList(query);
        }

        PageUtil page = new PageUtil(total, query.getPage(), query.getPageSize(), sysMenuList);

        return PageUtil.convertToJSONObject(page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public MessageVo info(@PathVariable("id") Integer id) {
        SysMenuEntity sysMenu = sysMenuService.getSysMenuEntity(id);

        return new MessageVo(MessageVo.SUCCESS, sysMenu);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public MessageVo save(@RequestBody SysMenuEntity sysMenu) {
        sysMenuService.save(sysMenu);

        return new MessageVo(MessageVo.SUCCESS, null);
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public MessageVo update(@RequestBody SysMenuEntity sysMenu) {
        sysMenuService.update(sysMenu);

        return new MessageVo(MessageVo.SUCCESS, null);
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public MessageVo delete(@RequestBody Integer[] ids) {
        sysMenuService.deleteBatch(ids);

        return new MessageVo(MessageVo.SUCCESS, null);
    }

}
