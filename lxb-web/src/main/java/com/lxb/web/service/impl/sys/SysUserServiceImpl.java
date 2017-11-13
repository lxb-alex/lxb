package com.lxb.web.service.impl.sys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.lxb.web.dao.sys.SysUserDao;
import com.lxb.web.entity.sys.SysUserEntity;
import com.lxb.web.service.sys.SysUserService;



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
	
}
