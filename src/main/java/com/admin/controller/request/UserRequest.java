package com.admin.controller.request;

import java.util.Date;

import lombok.Data;

@Data
public class UserRequest {
	/** 登录名称 */
	private String username;

	/** 密码 */
	private String password;
	/** 邮箱 */
	private String email;

	/** 是否禁用 */
	private boolean disabled;

	/** 创建时间 */
	private Date createTime;

	/** 最后登录时间 */
	private Date lastTime;
}
