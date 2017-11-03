package com.lxb.web.service;

import com.lxb.web.entity.SysUserEntity;

import java.util.List;
import java.util.Map;

/**
 * 系统用户表
 * 
 * @author Liaoxb
 * @date 2017/10/31
 */
public interface SysUserService {
	
	SysUserEntity getSysUserEntity(Long id);
	
	List<SysUserEntity> selectList(Map<String, Object> map);
	
	int getTotal(Map<String, Object> map);
	
	void save(SysUserEntity sysUser);
	
	void update(SysUserEntity sysUser);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);

    SysUserEntity getSysUserEntity(String account, String password);
}