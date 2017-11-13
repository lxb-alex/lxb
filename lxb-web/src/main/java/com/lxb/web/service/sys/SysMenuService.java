package com.lxb.web.service.sys;

import com.lxb.web.entity.sys.SysMenuEntity;

import java.util.List;
import java.util.Map;

/**
 * 目录表
 * 
 * @author Liaoxb
 * @date 2017/11/13
 */
public interface SysMenuService {
	
	SysMenuEntity getSysMenuEntity(Integer id);
	
	List<SysMenuEntity> selectList(Map<String, Object> map);
	
	int getTotal(Map<String, Object> map);
	
	int save(SysMenuEntity sysMenu);

    int update(SysMenuEntity sysMenu);

    int delete(Integer id);

    int deleteBatch(Integer[] ids);

    List<SysMenuEntity> getAll(Map<String, Object> params);
}
