package com.lxb.web.entity.sys;

import java.io.Serializable;
import java.util.Date;



/**
 * 系统用户表
 * 
 * @author Liaoxb
 * @date 2017/11/13
 */
public class SysUserEntity implements Serializable {

	
    /**
	 * 主键，自增
	 */
	private Long id;
    /**
	 * 用户名
	 */
	private String name;
    /**
	 * 登录账号
	 */
	private String accout;
    /**
	 * 登录密码
	 */
	private String password;
    /**
	 * 邮件
	 */
	private String email;
    /**
	 * 联系电话
	 */
	private String phone;
    /**
	 * 创建时间
	 */
	private Date gmtCreate;
    /**
	 * 修改时间
	 */
	private Date gmtModify;
    /**
	 * 0 - 未删除；1 - 删除
	 */
	private Integer isDeleted;


	/**
	 * 设置：主键，自增
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：主键，自增
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：用户名
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：用户名
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：登录账号
	 */
	public void setAccout(String accout) {
		this.accout = accout;
	}
	/**
	 * 获取：登录账号
	 */
	public String getAccout() {
		return accout;
	}
	/**
	 * 设置：登录密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * 获取：登录密码
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * 设置：邮件
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * 获取：邮件
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * 设置：联系电话
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * 获取：联系电话
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * 设置：创建时间
	 */
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getGmtCreate() {
		return gmtCreate;
	}
	/**
	 * 设置：修改时间
	 */
	public void setGmtModify(Date gmtModify) {
		this.gmtModify = gmtModify;
	}
	/**
	 * 获取：修改时间
	 */
	public Date getGmtModify() {
		return gmtModify;
	}
	/**
	 * 设置：0 - 未删除；1 - 删除
	 */
	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}
	/**
	 * 获取：0 - 未删除；1 - 删除
	 */
	public Integer getIsDeleted() {
		return isDeleted;
	}
}
