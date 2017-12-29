package com.lxb.sys.service;


import com.lxb.sys.entity.SysUserEntity;

import java.util.List;
import java.util.Map;

/**
 * 系统用户表
 * 
 * @author Liaoxb
 * @date 2017/11/13
 */
public interface SysUserService {
	
	SysUserEntity getSysUserEntity(Long id);
	
	List<SysUserEntity> selectList(Map<String, Object> map);
	
	int getTotal(Map<String, Object> map);
	
	int save(SysUserEntity sysUser);

    int update(SysUserEntity sysUser);

    int delete(Long id);

    int deleteBatch(Long[] ids);

    SysUserEntity getSysUserEntity(String account);
}
