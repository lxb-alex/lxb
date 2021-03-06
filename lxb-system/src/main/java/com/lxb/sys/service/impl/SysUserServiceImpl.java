package com.lxb.sys.service.impl;

import com.lxb.sys.dao.SysUserDao;
import com.lxb.sys.entity.SysUserEntity;
import com.lxb.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
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
	public int save(SysUserEntity sysUser){
        return sysUserDao.save(sysUser);
	}
	
	@Override
	public int update(SysUserEntity sysUser){
        return sysUserDao.update(sysUser);
	}
	
	@Override
	public int delete(Long id){
        return sysUserDao.delete(id);
	}
	
	@Override
	public int deleteBatch(Long[] ids){
        return sysUserDao.deleteBatch(ids);
	}

	@Override
	public SysUserEntity getSysUserEntity(String account) {
		SysUserEntity user = new SysUserEntity();
		user.setAccount(account);
		return sysUserDao.getSysUser(user);
	}

}
