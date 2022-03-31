package com.admin.controller.response;

import lombok.Data;

@Data
public class RoleResponse {
	private Long id;

	/** 角色名*/
	private String name;

	/** 描述 */
	private String description;

	/** 状态 是否禁用*/
	private boolean disabled;
	/** 排序 */
	private Integer sort;
}
