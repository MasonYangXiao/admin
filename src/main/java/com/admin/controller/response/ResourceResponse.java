package com.admin.controller.response;

import lombok.Data;

@Data
public class ResourceResponse {
	
	private Long id;

	/** 资源名称 */
	private String title;

	/** 状态 是否禁用*/
	private boolean disabled;

	/** 地址 */
	private String url;

	/** 描述 */
	private String description;
}
