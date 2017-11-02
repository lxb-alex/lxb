package com.lxb.web.service;

import com.lxb.web.entity.SysMenuEntity;

import java.util.List;
import java.util.Map;

/**
 * 目录表
 * 
 * @author Liaoxb
 * @date 2017/10/31
 */
public interface SysMenuService {
	
	SysMenuEntity getSysMenuEntity(Integer id);
	
	List<SysMenuEntity> selectList(Map<String, Object> map);
	
	int getTotal(Map<String, Object> map);
	
	void save(SysMenuEntity sysMenu);
	
	void update(SysMenuEntity sysMenu);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
