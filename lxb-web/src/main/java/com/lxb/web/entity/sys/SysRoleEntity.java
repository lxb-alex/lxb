package com.lxb.web.entity.sys;

import java.io.Serializable;
import java.util.Date;



/**
 * 角色表
 * 
 * @author Liaoxb
 * @date 2017/11/9
 */
public class SysRoleEntity implements Serializable {

	
    /**
	 * 
	 */
	private Integer id;
    /**
	 * 
	 */
	private String name;
    /**
	 * 
	 */
	private Integer createUserId;
    /**
	 * 
	 */
	private Date createDate;
    /**
	 * 
	 */
	private Date updateDate;


	/**
	 * 设置：
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：
	 */
	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}
	/**
	 * 获取：
	 */
	public Integer getCreateUserId() {
		return createUserId;
	}
	/**
	 * 设置：
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * 获取：
	 */
	public Date getCreateDate() {
		return createDate;
	}
	/**
	 * 设置：
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	/**
	 * 获取：
	 */
	public Date getUpdateDate() {
		return updateDate;
	}
}