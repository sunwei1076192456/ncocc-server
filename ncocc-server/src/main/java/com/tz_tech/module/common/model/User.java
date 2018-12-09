package com.tz_tech.module.common.model;

import java.io.Serializable;

public class User implements Serializable
{
	/**
	 * 用户ID
	 */
	private String id;
	
	/**
	 * 姓名
	 */
	private String name;
	
	/**
	 * 登录名（工号）
	 */
	private String loginName;
	
	/**
	 * 登录密码
	 */
	private String password;
	
	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 手机
	 */
	private  String phone;

	/**
	 * 手机
	 */
	private  String active;

	/**
	 * 有效起始时间
	 */
	private  String activeDate;

	/**
	 * 失效时间
	 */
	private  String expiredDate;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getActiveDate() {
		return activeDate;
	}

	public void setActiveDate(String activeDate) {
		this.activeDate = activeDate;
	}

	public String getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(String expiredDate) {
		this.expiredDate = expiredDate;
	}

	@Override
	public String toString() {
		return "User{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", loginName='" + loginName + '\'' +
				", password='" + password + '\'' +
				", email='" + email + '\'' +
				", phone='" + phone + '\'' +
				", active='" + active + '\'' +
				", activeDate='" + activeDate + '\'' +
				", expiredDate='" + expiredDate + '\'' +
				'}';
	}

}
