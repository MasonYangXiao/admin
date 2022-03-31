package com.admin.controller;


import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.admin.common.response.BaseResponse;
import com.admin.common.utils.ErrorCode;
import com.admin.security.SecurityUser;
import com.admin.security.SecurityUtil;
import com.admin.service.SysMenuService;
import com.admin.sys_menu.SysMenuResponse;



/**
 * @author Jonsy
 *
 */
@Controller
public class IndexController {

	@Resource
    private SysMenuService sysMenuService;

    @RequestMapping("/")
    public String index(Model model) {
    	SecurityUser securityUser = SecurityUtil.getUser();
    	model.addAttribute("user", securityUser);
        return "index";
    }
    
    @RequestMapping("/index")
    public String index2(Model model) {
    	SecurityUser securityUser = SecurityUtil.getUser();
    	model.addAttribute("user", securityUser);
        return "index";
    }
    
    @RequestMapping("/welcome")
    public String welcome() {
        return "welcome";
    }
    
    @GetMapping("/sysMenu")
    @ResponseBody
    public SysMenuResponse sysMenu() {
        return sysMenuService.sysMenu();
    }
    
    @GetMapping("/sysClear")
    @ResponseBody
    public BaseResponse<Void> clear() {
        return BaseResponse.of(ErrorCode.CLEAR_SUCCESS);
    }
}
