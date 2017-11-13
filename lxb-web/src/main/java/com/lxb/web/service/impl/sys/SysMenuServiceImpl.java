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
	public int save(SysMenuEntity sysMenu){
        return sysMenuDao.save(sysMenu);
	}
	
	@Override
	public int update(SysMenuEntity sysMenu){
        return sysMenuDao.update(sysMenu);
	}
	
	@Override
	public int delete(Integer id){
        return sysMenuDao.delete(id);
	}
	
	@Override
	public int deleteBatch(Integer[] ids){
        return sysMenuDao.deleteBatch(ids);
	}

	@Override
	public List<SysMenuEntity> getAll(Map<String, Object> params) {
		return sysMenuDao.getAll(params);
	}

}
