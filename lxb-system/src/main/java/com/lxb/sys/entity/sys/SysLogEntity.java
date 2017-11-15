package com.lxb.sys.entity.sys;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author Liaoxb
 * @date 2017/11/15
 */
public class SysLogEntity implements Serializable {

	
    /**
	 * 
	 */
	private Integer id;
    /**
	 * 标题
	 */
	private String title;
    /**
	 * 操作类型
	 */
	private String type;
    /**
	 * 远程地址
	 */
	private String remoteAddr;
    /**
	 * 访问代理
	 */
	private String userAgent;
    /**
	 * 请求URI
	 */
	private String requestUri;
    /**
	 * 参数
	 */
	private String params;
    /**
	 * 执行方法
	 */
	private String method;
    /**
	 * 操作人
	 */
	private Integer optation;
    /**
	 * 记录时间
	 */
	private Date createDate;


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
	 * 设置：标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取：标题
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置：操作类型
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 获取：操作类型
	 */
	public String getType() {
		return type;
	}
	/**
	 * 设置：远程地址
	 */
	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}
	/**
	 * 获取：远程地址
	 */
	public String getRemoteAddr() {
		return remoteAddr;
	}
	/**
	 * 设置：访问代理
	 */
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
	/**
	 * 获取：访问代理
	 */
	public String getUserAgent() {
		return userAgent;
	}
	/**
	 * 设置：请求URI
	 */
	public void setRequestUri(String requestUri) {
		this.requestUri = requestUri;
	}
	/**
	 * 获取：请求URI
	 */
	public String getRequestUri() {
		return requestUri;
	}
	/**
	 * 设置：参数
	 */
	public void setParams(String params) {
		this.params = params;
	}
	/**
	 * 获取：参数
	 */
	public String getParams() {
		return params;
	}
	/**
	 * 设置：执行方法
	 */
	public void setMethod(String method) {
		this.method = method;
	}
	/**
	 * 获取：执行方法
	 */
	public String getMethod() {
		return method;
	}
	/**
	 * 设置：操作人
	 */
	public void setOptation(Integer optation) {
		this.optation = optation;
	}
	/**
	 * 获取：操作人
	 */
	public Integer getOptation() {
		return optation;
	}
	/**
	 * 设置：记录时间
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * 获取：记录时间
	 */
	public Date getCreateDate() {
		return createDate;
	}
}
