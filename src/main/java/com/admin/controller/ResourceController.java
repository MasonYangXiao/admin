package com.admin.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.admin.common.response.BaseResponse;
import com.admin.common.response.DatatablesViewPage;
import com.admin.controller.request.ResourceRequest;
import com.admin.controller.response.ResourceResponse;
import com.admin.service.SysResourceService;


/**
 * @author mason
 *
 */
@Controller
@RequestMapping("/resource")
public class ResourceController{

    @Autowired
    protected SysResourceService sysResourceService;

    @RequestMapping(method = RequestMethod.POST, value = "/add")
    @ResponseBody
    public BaseResponse<Void> create(@RequestBody ResourceRequest request) {
    	sysResourceService.create(request);
    	return BaseResponse.success();
    }
    
    @RequestMapping(value = "/{id}/modify", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<Void> modify(@PathVariable("id") Long id, @RequestBody ResourceRequest request) {
    	sysResourceService.modify(id,request);
    	return BaseResponse.success();
    }


    @RequestMapping(value = "/{id}/disable", method = RequestMethod.PUT)
    @ResponseBody
    public void switchDisable(@PathVariable("id") Long id, @RequestParam("disable") boolean disable) {
    	sysResourceService.switchDisable(id,disable);
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.DELETE)
    @ResponseBody
    public void delete(@PathVariable("id") Long id) {
    	sysResourceService.delete(id);
    }

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String toform(@RequestParam(value = "id", required = false) Long id, Model model) {
        String url = null;
        if(id == null) {
        	url = "/resource/add";
        }else {
        	 model.addAttribute("resource",sysResourceService.get(id));
        	 url = "/resource/" + id + "/modify";
        	
        }
        model.addAttribute("api", url);
        return "resource/form";
    }
    

    @RequestMapping(method = RequestMethod.GET)
    public String listPage(Model model) {
        return "resource/list";
    }
    
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @ResponseBody
    public DatatablesViewPage<ResourceResponse> list(int page, int limit) {
    	return sysResourceService.list(page,limit);
    }



}
