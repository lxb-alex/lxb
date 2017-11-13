package com.lxb.web.service.impl.sys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.lxb.web.dao.sys.SysRoleDao;
import com.lxb.web.entity.sys.SysRoleEntity;
import com.lxb.web.service.sys.SysRoleService;



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
	
}
