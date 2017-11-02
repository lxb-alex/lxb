package com.lxb.web.dao;

import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

import com.lxb.web.entity.SysMenuEntity;

/**
 * 目录表
 * 
 * @author Liaoxb
 * @date 2017/10/31
 */
@Repository()
public interface SysMenuDao {
    // ======= 自动生成接口 START =========
    void save(SysMenuEntity entity);

    void saveSelective(SysMenuEntity entity);

    void save(Map<String, Object> map);

    void saveBatch(List<SysMenuEntity> list);

    int update(SysMenuEntity entity);

    int updateByPrimaryKeySelective(SysMenuEntity entity);

    int update(Map<String, Object> map);

    int delete(Object id);

    int delete(Map<String, Object> map);

    int deleteBatch(Object[] id);

    SysMenuEntity getSysMenuEntity(Object id);

    List<SysMenuEntity> selectList(Map<String, Object> map);

    List<SysMenuEntity> selectList(Object id);

    int getTotal(Map<String, Object> map);

    int getTotal();
    // ======= 自动生成接口  END =========
}
