package com.lxb.web.dao.sys;

import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

import com.lxb.web.entity.sys.SysUserEntity;

/**
 * 系统用户表
 * 
 * @author Liaoxb
 * @date 2017/11/9
 */
@Repository()
public interface SysUserDao {
    // ======= 自动生成接口 START =========
    void save(SysUserEntity entity);

    void saveSelective(SysUserEntity entity);

    void save(Map<String, Object> map);

    void saveBatch(List<SysUserEntity> list);

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
}
