package com.lxb.sys.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 目录表
 * 
 * @author Liaoxb
 * @date 2017/11/15
 */
public class SysMenuEntity implements Serializable {

	
    /**
	 * 主键
	 */
	private String id;
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
	 * 权限标识
	 */
	private String permission;
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
	private Integer sort;
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
	 * 设置：权限标识
	 */
	public void setPermission(String permission) {
		this.permission = permission;
	}
	/**
	 * 获取：权限标识
	 */
	public String getPermission() {
		return permission;
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
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	/**
	 * 获取：排序
	 */
	public Integer getSort() {
		return sort;
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
