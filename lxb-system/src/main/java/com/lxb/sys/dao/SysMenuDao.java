package com.lxb.sys.dao;

import com.lxb.sys.entity.SysMenuEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 目录表
 * 
 * @author Liaoxb
 * @date 2017/11/13
 */
@Repository()
public interface SysMenuDao {
    // ======= 自动生成接口 START =========
    int save(SysMenuEntity entity);

    int saveSelective(SysMenuEntity entity);

    int save(Map<String, Object> map);

    int saveBatch(List<SysMenuEntity> list);

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
    List<SysMenuEntity> getAll(Map<String, Object> params);

    /**
     * 根据用户id查询所具有权限的菜单
     * @param userId 用户id
     * @return
     */
    List<SysMenuEntity> selectMenuByUserId(String userId);
}
