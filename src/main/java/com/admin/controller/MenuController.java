package com.admin.controller;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.admin.common.controller.BaseController;
import com.admin.common.response.BaseResponse;
import com.admin.common.response.DatatablesViewPage;
import com.admin.common.utils.ErrorCode;
import com.admin.controller.request.MenuRequest;
import com.admin.controller.request.base.PageListRequest;
import com.admin.controller.response.MenuResponse;
import com.admin.entity.SysMenu;
import com.admin.service.SysMenuService;


/**
 * @author mason
 *
 */
@Controller
@RequestMapping("/menu")
public class MenuController{

    @Autowired
    protected SysMenuService sysMenuService;

    @RequestMapping(method = RequestMethod.POST, value = "/add")
    @ResponseBody
    public BaseResponse<Void> create(@RequestBody MenuRequest menu) {
    	sysMenuService.create(menu);
    	return BaseResponse.success();
    }
    
    @RequestMapping(value = "/{id}/modify", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<Void> modify(@PathVariable("id") Long id, @RequestBody MenuRequest menu) {
    	sysMenuService.modify(id,menu);
    	return BaseResponse.success();
    }


    @RequestMapping(value = "/{id}/status", method = RequestMethod.PUT)
    @ResponseBody
    public void switchStatus(@PathVariable("id") Long id, @RequestParam("disable") boolean disable) {
    	sysMenuService.switchStatus(id,disable);
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.DELETE)
    @ResponseBody
    public void delete(@PathVariable("id") Long id) {
    	sysMenuService.delete(id);
    }

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String toform(@RequestParam(value = "id", required = false) Long id, @RequestParam(value = "udpate", required = false) boolean udpate, Model model) {
        String url = null;
        if(id == null) {
        	url = "/menu/add";
        }else {
        	 model.addAttribute("menu",sysMenuService.get(id));
        	 model.addAttribute("udpate",udpate);
        	if(udpate) {
        		  url = "/menu/" + id + "/modify";
        	}else {
        		url = "/menu/add";
        	}
        	
        }
        model.addAttribute("api", url);
        return "menu/form";
    }
    

    @RequestMapping(method = RequestMethod.GET)
    public String listPage(Model model) {
        return "menu/list";
    }
    
    @RequestMapping("list")
    @ResponseBody
    public DatatablesViewPage<MenuResponse> list(PageListRequest<MenuRequest> request) {
    	return sysMenuService.list(request);
    }



}
