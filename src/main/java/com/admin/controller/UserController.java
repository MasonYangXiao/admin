package com.admin.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.admin.common.response.BaseResponse;
import com.admin.common.response.DatatablesViewPage;
import com.admin.controller.request.PasswordRequest;
import com.admin.controller.request.UserRequest;
import com.admin.controller.response.MenuResponse;
import com.admin.controller.response.SelectResponse;
import com.admin.controller.response.UserResponse;
import com.admin.entity.User;
import com.admin.security.SecurityUser;
import com.admin.security.SecurityUtil;
import com.admin.service.RoleService;
import com.admin.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;

/**
 * @author mason
 *
 */
@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
    private UserService userService;
	@Autowired
	private RoleService roleService;

    public static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(method = RequestMethod.POST,value = "/add")
    @ResponseBody
    public BaseResponse<Void> create(@RequestBody UserRequest request){
        userService.create(request);
        return BaseResponse.success();
    }

    @RequestMapping(value = "/{id}/modify", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<Void> modify(@PathVariable("id") String id, @RequestBody UserRequest request) {
        userService.modify(id,request);
        return BaseResponse.success();
    }
    
    @RequestMapping(value = "/{id}/password", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<Void> password(@PathVariable("id") String id, @RequestBody PasswordRequest request) {
        userService.password(id,request);
        return BaseResponse.success();
    }

    @RequestMapping(value = "/{id}/status",method = RequestMethod.PUT)
    @ResponseBody
    public BaseResponse<Void> switchStatus(@PathVariable("id") String id,@RequestParam("disable") boolean disable){
        userService.switchStatus(id,disable);
        return BaseResponse.success();
    }
    @RequestMapping(value = "/{id}/delete",method = RequestMethod.DELETE)
    @ResponseBody
    public BaseResponse<Void> delete(@PathVariable("id")String id){
         userService.delete(id);
         return BaseResponse.success();
    }

    @RequestMapping(value = "/form",method = RequestMethod.GET)
    public String form(@RequestParam(value = "id",required = false)String id, Model model){
        String api="/user/add";
        if(StringUtils.isNotBlank(id)){
            model.addAttribute("acount",userService.get(id));
            api="/user/"+id+"/modify";
        }
        model.addAttribute("api",api);
        return  "user/form";
    }
    
    @RequestMapping(value = "/addForm",method = RequestMethod.GET)
    public String addPage(){
        return  "user/add_form";
    }
    @RequestMapping(value = "/updateForm",method = RequestMethod.GET)
    public String updatePage(@RequestParam(value = "id",required = true)String id,Model model){
    	  model.addAttribute("user",userService.get(id));
        return  "user/update_form";
    }
    @RequestMapping(value = "/passwordForm",method = RequestMethod.GET)
    public String passwordPage(@RequestParam(value = "id",required = true)String id,Model model){
    	  model.addAttribute("user",userService.get(id));
        return  "user/password_form";
    }
    @RequestMapping(value = "/indexPasswordForm",method = RequestMethod.GET)
    public String indexPasswordForm(Model model){
    	SecurityUser securityUser = SecurityUtil.getUser();
    	  model.addAttribute("user",userService.get(securityUser.getUid()));
        return  "user/password_form";
    }
    @RequestMapping(value = "/infoForm",method = RequestMethod.GET)
    public String infoPage(Model model){
    	SecurityUser securityUser = SecurityUtil.getUser();
    	model.addAttribute("user",userService.get(securityUser.getUid()));
        return  "user/info_form";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String listPage(Model model){
        return "user/list";
    }
    @RequestMapping(value = "/list")
    @ResponseBody
    public  DatatablesViewPage<UserResponse> list(int page, int limit){
    	return userService.list(page,limit);
    }

    @RequestMapping(value = "/{id}/grant-rule",method = RequestMethod.POST)
    @ResponseBody
    public  BaseResponse<Void> grantRole(@PathVariable("id") String id,  Long mid[]) {
        if(mid==null){
        	mid=new Long[0];
        }
        userService.grantRole(id, Lists.newArrayList(mid));
        return  BaseResponse.success();
    }
    
    @RequestMapping(value = "/select-rule", method = RequestMethod.GET)
    public String selectRole(String id,Model model) {
        model.addAttribute("user",userService.get(id));
        model.addAttribute("ruleTree",roleService.getRuleTree(id));
        return "user/grant-rule";
    }

    @RequestMapping(value = "/selectusername", method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse<SelectResponse> selectusername(String username){
        logger.debug("username:{}",username);
        boolean result = false;
        User listuser = userService.getUserByUname(username);
        if (listuser == null) {
            result = true;
        }
        SelectResponse res = new SelectResponse();
        res.setValid(result);
        return BaseResponse.success(res,SelectResponse.class);
    }

    @RequestMapping(value = "/selectuseremail", method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse<SelectResponse> selectuseremail(String email){
        logger.debug("email:{}",email);
        boolean result = false;
        List<User> listuser = userService.getUserByEmail(email);
        if (listuser.size() < 1) {
            result = true;
        }
        SelectResponse res = new SelectResponse();
        res.setValid(result);
        return BaseResponse.success(res,SelectResponse.class);
    }
    
}
