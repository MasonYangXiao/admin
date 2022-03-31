package com.admin.controller;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.admin.common.response.BaseResponse;
import com.admin.common.response.DatatablesViewPage;
import com.admin.controller.request.RoleRequest;
import com.admin.controller.request.base.PageListRequest;
import com.admin.controller.response.RoleResponse;
import com.admin.entity.SysResource;
import com.admin.service.RoleService;
import com.admin.service.SysMenuService;
import com.admin.service.SysResourceService;
import com.google.common.collect.Lists;


/**
 * @author mason
 *
 */
@Controller
@RequestMapping("/role")
public class RoleController{

    @Autowired
    protected RoleService roleService;
    
    @Autowired
    protected SysMenuService sysMenuService;
    
    @Autowired
    protected SysResourceService sysResourceService;

    @RequestMapping(method = RequestMethod.POST, value = "/add")
    @ResponseBody
    public BaseResponse<Void> create(@RequestBody RoleRequest role) {
    	roleService.create(role);
    	return BaseResponse.success();
    }
    
    @RequestMapping(value = "/{id}/modify", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<Void> modify(@PathVariable("id") Long id, @RequestBody RoleRequest Role) {
    	roleService.modify(id,Role);
    	return BaseResponse.success();
    }


    @RequestMapping(value = "/{id}/status", method = RequestMethod.PUT)
    @ResponseBody
    public BaseResponse<Void> switchStatus(@PathVariable("id") Long id, @RequestParam("disable") boolean disable) {
    	roleService.switchStatus(id,disable);
    	return BaseResponse.success();
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.DELETE)
    @ResponseBody
    public void delete(@PathVariable("id") Long id) {
    	roleService.delete(id);
    }

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String toform(@RequestParam(value = "id", required = false) Long id,Model model) {
        String url = null;
        if(id == null) {
        	url = "/role/add";
        }else {
        	 model.addAttribute("role",roleService.get(id));
        	 url = "/role/" + id + "/modify";   	
        }
        model.addAttribute("api", url);
        return "role/form";
    }
    

    @RequestMapping(method = RequestMethod.GET)
    public String listPage(Model model) {
        return "role/list";
    }
    
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @ResponseBody
    public DatatablesViewPage<RoleResponse> list(int page, int limit) {
    	return roleService.list(page,limit);
    }
    
  //为角色分配菜单
    @RequestMapping(value = "/grant-resource",method = RequestMethod.GET)
    public String grantResourcePage(Long id,Model model) {
    	model.addAttribute("role",roleService.get(id));
    	model.addAttribute("resourceTree",sysResourceService.getResourceTree(id));
        return "role/grant-resource";
    }

    //为角色分配资源
    @RequestMapping(value = "/{id}/grant-resource",method = RequestMethod.POST)
    public BaseResponse<Void> grantResources(@PathVariable("id") Long id,  Long rid[] ) {
        if(rid==null){
           rid=new Long[0];
        }
        roleService.grantResource(id, Lists.newArrayList(rid));
        return BaseResponse.success();
    }

    //为角色分配菜单
    @RequestMapping(value = "/{id}/grant-menu",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<Void> grantMenu(@PathVariable("id") Long id,Long mid[] ) {
        if(mid==null){
            mid=new Long[0];
        }
        roleService.grantMenu(id,Lists.newArrayList(mid));
        return BaseResponse.success();
    }
    
  //为角色分配菜单
    @RequestMapping(value = "/grant-menu",method = RequestMethod.GET)
    public String grantMenuPage(Long id,Model model) {
    	model.addAttribute("role",roleService.get(id));
    	model.addAttribute("menuTree",sysMenuService.getMenuTree(id));
        return "role/grant-menu";
    }

}
