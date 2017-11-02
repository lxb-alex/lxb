package com.lxb.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lxb.web.dao.SysUserDao;
import com.lxb.web.entity.SysUserEntity;
import com.lxb.web.service.SysUserService;



@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService {
	@Autowired
	private SysUserDao sysUserDao;
	
	@Override
	public SysUserEntity getSysUserEntity(Long id){
		return sysUserDao.getSysUserEntity(id);
	}
	
	@Override
	public List<SysUserEntity> selectList(Map<String, Object> map){
		return sysUserDao.selectList(map);
	}
	
	@Override
	public int getTotal(Map<String, Object> map){
		return sysUserDao.getTotal(map);
	}
	
	@Override
	public void save(SysUserEntity sysUser){
		sysUserDao.save(sysUser);
	}
	
	@Override
	public void update(SysUserEntity sysUser){
		sysUserDao.update(sysUser);
	}
	
	@Override
	public void delete(Long id){
		sysUserDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		sysUserDao.deleteBatch(ids);
	}

	@Override
	public SysUserEntity getSysUserEntity(String account, String password) {
		Map map = new HashMap();
		map.put("accout", account);
		map.put("password", password);
		List<SysUserEntity> list = sysUserDao.selectList(map);
		if (list !=null  && list.size()>0){
			return list.get(0);
		}
		return null;
	}

}
