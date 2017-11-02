package com.lxb.web.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 目录表
 * 
 * @author Liaoxb
 * @date 2017/10/31
 */
public class SysMenuEntity implements Serializable {

	
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
	private Integer parentId;
    /**
	 * 
	 */
	private String url;
    /**
	 * 
	 */
	private String identify;
    /**
	 * 
	 */
	private Integer type;
    /**
	 * 
	 */
	private String icon;
    /**
	 * 排序
	 */
	private Integer orderNum;


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
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	/**
	 * 获取：
	 */
	public Integer getParentId() {
		return parentId;
	}
	/**
	 * 设置：
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * 获取：
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * 设置：
	 */
	public void setIdentify(String identify) {
		this.identify = identify;
	}
	/**
	 * 获取：
	 */
	public String getIdentify() {
		return identify;
	}
	/**
	 * 设置：
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取：
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * 设置：
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}
	/**
	 * 获取：
	 */
	public String getIcon() {
		return icon;
	}
	/**
	 * 设置：排序
	 */
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	/**
	 * 获取：排序
	 */
	public Integer getOrderNum() {
		return orderNum;
	}
}
