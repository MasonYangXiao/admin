package com.admin.sys_menu;

import java.util.List;

import com.admin.controller.model.MenuVo;

import lombok.Data;

@Data
public class SysMenuResponse {

	private List<MenuVo> menuInfo; 
	private HomeInfo homeInfo;
	private LogoInfo logoInfo;
}
