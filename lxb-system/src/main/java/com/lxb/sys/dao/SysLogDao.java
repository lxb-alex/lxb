package com.lxb.sys.dao;

import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

import com.lxb.sys.entity.SysLogEntity;

/**
 * 
 * 
 * @author Liaoxb
 * @date 2017/11/15
 */
@Repository()
public interface SysLogDao {
    // ======= 自动生成接口 START =========
    int save(SysLogEntity entity);

    int saveSelective(SysLogEntity entity);

    int save(Map<String, Object> map);

    int saveBatch(List<SysLogEntity> list);

    int update(SysLogEntity entity);

    int updateByPrimaryKeySelective(SysLogEntity entity);

    int update(Map<String, Object> map);

    int delete(Object id);

    int delete(Map<String, Object> map);

    int deleteBatch(Object[] id);

    SysLogEntity getSysLogEntity(Object id);

    List<SysLogEntity> selectList(Map<String, Object> map);

    List<SysLogEntity> selectList(Object id);

    int getTotal(Map<String, Object> map);

    int getTotal();
    // ======= 自动生成接口  END =========
}
