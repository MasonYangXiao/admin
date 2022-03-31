package com.admin.controller.response;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class UserResponse {
	
	private String id ;

	/** 登录名称 */
	private String username;

	/** 邮箱 */
	private String email;

	/** 是否禁用 */
	private boolean disabled;
	
	/** 最后登录时间 */
	private Date lastTime;
	
	private String roles;
	
}
