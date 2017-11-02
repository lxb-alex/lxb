package com.lxb.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.lxb.web.dao.SysRoleDao;
import com.lxb.web.entity.SysRoleEntity;
import com.lxb.web.service.SysRoleService;



@Service("sysRoleService")
public class SysRoleServiceImpl implements SysRoleService {
	@Autowired
	private SysRoleDao sysRoleDao;
	
	@Override
	public SysRoleEntity getSysRoleEntity(Integer id){
		return sysRoleDao.getSysRoleEntity(id);
	}
	
	@Override
	public List<SysRoleEntity> selectList(Map<String, Object> map){
		return sysRoleDao.selectList(map);
	}
	
	@Override
	public int getTotal(Map<String, Object> map){
		return sysRoleDao.getTotal(map);
	}
	
	@Override
	public void save(SysRoleEntity sysRole){
		sysRoleDao.save(sysRole);
	}
	
	@Override
	public void update(SysRoleEntity sysRole){
		sysRoleDao.update(sysRole);
	}
	
	@Override
	public void delete(Integer id){
		sysRoleDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		sysRoleDao.deleteBatch(ids);
	}
	
}
