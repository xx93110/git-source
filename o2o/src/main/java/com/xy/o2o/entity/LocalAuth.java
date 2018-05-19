package com.xy.o2o.entity;

import java.util.Date;
/**
 * 本地账号
 * @author YYBear
 *
 */
public class LocalAuth {
	//本地账号ID
	private Long localId;
	//用户名
	private String username;
	//密码
	private String password;
	//创建时间
	private Date createTime;
	//修改时间
	private Date lastEditTime;
	//PersonInfo
	private PersonInfo personInfo;
	public Long getLocalId() {
		return localId;
	}
	public void setLocalId(Long localId) {
		this.localId = localId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getLastEditTime() {
		return lastEditTime;
	}
	public void setLastEditTime(Date lastEditTime) {
		this.lastEditTime = lastEditTime;
	}
	public PersonInfo getPersonInfo() {
		return personInfo;
	}
	public void setPersonInfo(PersonInfo personInfo) {
		this.personInfo = personInfo;
	}
	
}
