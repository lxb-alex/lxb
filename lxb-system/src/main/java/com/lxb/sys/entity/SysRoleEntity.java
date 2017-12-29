package com.lxb.sys.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 角色表
 * 
 * @author Liaoxb
 * @date 2017/11/15
 */
public class SysRoleEntity implements Serializable {

	
    /**
	 * 主键
	 */
	private String id;
    /**
	 * 角色名
	 */
	private String name;
    /**
	 * 创建用户id
	 */
	private Integer createUserId;
    /**
	 * 创建时间
	 */
	private Date createDate;
    /**
	 * 修改时间
	 */
	private Date updateDate;


	/**
	 * 设置：主键
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：主键
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：角色名
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：角色名
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：创建用户id
	 */
	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}
	/**
	 * 获取：创建用户id
	 */
	public Integer getCreateUserId() {
		return createUserId;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreateDate() {
		return createDate;
	}
	/**
	 * 设置：修改时间
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	/**
	 * 获取：修改时间
	 */
	public Date getUpdateDate() {
		return updateDate;
	}
}
