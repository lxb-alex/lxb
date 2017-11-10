package com.lxb.web.entity.sys;

import java.io.Serializable;
import java.util.Date;



/**
 * 目录表
 * 
 * @author Liaoxb
 * @date 2017/11/10
 */
public class SysMenuEntity implements Serializable {

	
    /**
	 * 主键
	 */
	private Integer id;
    /**
	 * 名称
	 */
	private String name;
    /**
	 * 父级id
	 */
	private Integer parentId;
    /**
	 * 访问路径
	 */
	private String url;
    /**
	 * 
	 */
	private String identify;
    /**
	 * 菜单类型
	 */
	private Integer type;
    /**
	 * 图标
	 */
	private String icon;
    /**
	 * 排序
	 */
	private Integer orderNum;
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
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：主键
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：父级id
	 */
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	/**
	 * 获取：父级id
	 */
	public Integer getParentId() {
		return parentId;
	}
	/**
	 * 设置：访问路径
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * 获取：访问路径
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
	 * 设置：菜单类型
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取：菜单类型
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * 设置：图标
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}
	/**
	 * 获取：图标
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
