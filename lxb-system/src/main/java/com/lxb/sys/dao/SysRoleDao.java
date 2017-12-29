package com.lxb.sys.dao;

import com.lxb.sys.entity.SysRoleEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 角色表
 * 
 * @author Liaoxb
 * @date 2017/11/13
 */
@Repository()
public interface SysRoleDao {
    // ======= 自动生成接口 START =========
    int save(SysRoleEntity entity);

    int saveSelective(SysRoleEntity entity);

    int save(Map<String, Object> map);

    int saveBatch(List<SysRoleEntity> list);

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
    Set<SysRoleEntity> selectSet(String id);
}
