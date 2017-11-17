package com.lxb.sys.service;

import com.lxb.sys.entity.SysLogEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author Liaoxb
 * @date 2017/11/15
 */
public interface SysLogService {
	
	SysLogEntity getSysLogEntity(Integer id);
	
	List<SysLogEntity> selectList(Map<String, Object> map);
	
	int getTotal(Map<String, Object> map);
	
	int save(SysLogEntity sysLog);

    int update(SysLogEntity sysLog);

    int delete(Integer id);

    int deleteBatch(Integer[] ids);
}
