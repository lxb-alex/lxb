package com.lxb.web.service.impl.sys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.lxb.web.dao.sys.SysMenuDao;
import com.lxb.web.entity.sys.SysMenuEntity;
import com.lxb.web.service.sys.SysMenuService;



@Service("sysMenuService")
public class SysMenuServiceImpl implements SysMenuService {
	@Autowired
	private SysMenuDao sysMenuDao;
	
	@Override
	public SysMenuEntity getSysMenuEntity(Integer id){
		return sysMenuDao.getSysMenuEntity(id);
	}
	
	@Override
	public List<SysMenuEntity> selectList(Map<String, Object> map){
		return sysMenuDao.selectList(map);
	}
	
	@Override
	public int getTotal(Map<String, Object> map){
		return sysMenuDao.getTotal(map);
	}
	
	@Override
	public void save(SysMenuEntity sysMenu){
		sysMenuDao.save(sysMenu);
	}
	
	@Override
	public void update(SysMenuEntity sysMenu){
		sysMenuDao.update(sysMenu);
	}
	
	@Override
	public void delete(Integer id){
		sysMenuDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		sysMenuDao.deleteBatch(ids);
	}
	
}
