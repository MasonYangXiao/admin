package com.admin.controller.request;

import lombok.Data;

@Data
public class RoleRequest {
	
	/** 角色名*/
	private String name;

	/** 描述 */
	private String description;
	/** 排序 */
	private Integer sort;

	/** 状态 是否禁用*/
	private boolean disabled;
}
