package com.lxb.sys.service.impl;

import com.lxb.sys.dao.SysRoleDao;
import com.lxb.sys.entity.SysRoleEntity;
import com.lxb.sys.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;


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
	public int save(SysRoleEntity sysRole){
        return sysRoleDao.save(sysRole);
	}
	
	@Override
	public int update(SysRoleEntity sysRole){
        return sysRoleDao.update(sysRole);
	}
	
	@Override
	public int delete(Integer id){
        return sysRoleDao.delete(id);
	}
	
	@Override
	public int deleteBatch(Integer[] ids){
        return sysRoleDao.deleteBatch(ids);
	}

	@Override
	public Set<SysRoleEntity> selectSet(String id) {
		return sysRoleDao.selectSet(id);
	}

}
