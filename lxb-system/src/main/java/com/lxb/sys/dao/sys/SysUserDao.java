package com.lxb.sys.dao.sys;

import com.lxb.sys.entity.sys.SysUserEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 系统用户表
 * 
 * @author Liaoxb
 * @date 2017/11/13
 */
@Repository()
public interface SysUserDao {
    // ======= 自动生成接口 START =========
    int save(SysUserEntity entity);

    int saveSelective(SysUserEntity entity);

    int save(Map<String, Object> map);

    int saveBatch(List<SysUserEntity> list);

    int update(SysUserEntity entity);

    int updateByPrimaryKeySelective(SysUserEntity entity);

    int update(Map<String, Object> map);

    int delete(Object id);

    int delete(Map<String, Object> map);

    int deleteBatch(Object[] id);

    SysUserEntity getSysUserEntity(Object id);

    List<SysUserEntity> selectList(Map<String, Object> map);

    List<SysUserEntity> selectList(Object id);

    int getTotal(Map<String, Object> map);

    int getTotal();
    // ======= 自动生成接口  END =========

    SysUserEntity getSysUser(SysUserEntity userEntity);
}
