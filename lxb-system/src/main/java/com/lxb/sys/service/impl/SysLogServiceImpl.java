package com.lxb.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.lxb.sys.dao.SysLogDao;
import com.lxb.sys.entity.SysLogEntity;
import com.lxb.sys.service.SysLogService;



@Service("sysLogService")
public class SysLogServiceImpl implements SysLogService {
	@Autowired
	private SysLogDao sysLogDao;
	
	@Override
	public SysLogEntity getSysLogEntity(Integer id){
		return sysLogDao.getSysLogEntity(id);
	}
	
	@Override
	public List<SysLogEntity> selectList(Map<String, Object> map){
		return sysLogDao.selectList(map);
	}
	
	@Override
	public int getTotal(Map<String, Object> map){
		return sysLogDao.getTotal(map);
	}
	
	@Override
	public int save(SysLogEntity sysLog){
        return sysLogDao.save(sysLog);
	}
	
	@Override
	public int update(SysLogEntity sysLog){
        return sysLogDao.update(sysLog);
	}
	
	@Override
	public int delete(Integer id){
        return sysLogDao.delete(id);
	}
	
	@Override
	public int deleteBatch(Integer[] ids){
        return sysLogDao.deleteBatch(ids);
	}
	
}
