package com.lxb.web.dao;

import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

import com.lxb.web.entity.SysRoleEntity;

/**
 * 角色表
 * 
 * @author Liaoxb
 * @date 2017/10/31
 */
@Repository()
public interface SysRoleDao {
    // ======= 自动生成接口 START =========
    void save(SysRoleEntity entity);

    void saveSelective(SysRoleEntity entity);

    void save(Map<String, Object> map);

    void saveBatch(List<SysRoleEntity> list);

    int update(SysRoleEntity entity);

    int updateByPrimaryKeySelective(SysRoleEntity entity);

    int update(Map<String, Object> map);

    int delete(Object id);

    int delete(Map<String, Object> map);

    int deleteBatch(Object[] id);

    SysRoleEntity getSysRoleEntity(Object id);

    List<SysRoleEntity> selectList(Map<String, Object> map);

    List<SysRoleEntity> selectList(Object id);

    int getTotal(Map<String, Object> map);

    int getTotal();
    // ======= 自动生成接口  END =========
}
