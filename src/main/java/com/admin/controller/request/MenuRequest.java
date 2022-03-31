package com.admin.controller.request;

import java.io.Serializable;

import lombok.Data;

@Data
public class MenuRequest implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = -1571050371925376867L;
	private String title;
	private String href;
	private Long pid;
	private String icon;
	private String target;
	private Integer sort;
	private Boolean status = false;
	private String remark;
}
