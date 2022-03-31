package com.admin.controller.request;

import lombok.Data;

@Data
public class ResourceRequest {
	

	/** 资源名称 */
	private String title;

	/** 状态 是否禁用*/
	private boolean disabled;

	/** 地址 */
	private String url;

	/** 描述 */
	private String description;
}
