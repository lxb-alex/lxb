package com.lxb.web.service;

import com.lxb.web.entity.SysRoleEntity;

import java.util.List;
import java.util.Map;

/**
 * 角色表
 * 
 * @author Liaoxb
 * @date 2017/10/31
 */
public interface SysRoleService {
	
	SysRoleEntity getSysRoleEntity(Integer id);
	
	List<SysRoleEntity> selectList(Map<String, Object> map);
	
	int getTotal(Map<String, Object> map);
	
	void save(SysRoleEntity sysRole);
	
	void update(SysRoleEntity sysRole);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
