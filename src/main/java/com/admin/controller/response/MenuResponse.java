package com.admin.controller.response;


import lombok.Data;

@Data
public class MenuResponse {
	    private Long id;
	    private String title;
	    private String href;
	    private Long pid;
	    private String icon;
	    private String target;
	    private Integer sort;
	    private Boolean status;
	    private String remark;
}
