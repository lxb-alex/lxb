package com.lxb.sys.service;


import com.lxb.sys.entity.SysRoleEntity;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 角色表
 * 
 * @author Liaoxb
 * @date 2017/11/13
 */
public interface SysRoleService {
	
	SysRoleEntity getSysRoleEntity(Integer id);
	
	List<SysRoleEntity> selectList(Map<String, Object> map);
	
	int getTotal(Map<String, Object> map);
	
	int save(SysRoleEntity sysRole);

    int update(SysRoleEntity sysRole);

    int delete(Integer id);

    int deleteBatch(Integer[] ids);

    /**
     * 根据用户id获取用户具有的所有角色
     * @param id 用户id
     * @return
     */
    Set<SysRoleEntity> selectSet(String id);
}
