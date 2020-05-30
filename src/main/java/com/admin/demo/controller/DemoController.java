package com.admin.demo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.admin.common.controller.BaseController;
import com.admin.common.response.BaseResponse;
import com.admin.common.response.DatatablesViewPage;
import com.admin.demo.model.DemoModel;
import com.admin.demo.service.DemoService;

@Controller
@RequestMapping("/demo")
public class DemoController extends BaseController {
	
	private DemoService demoService;
	
	@GetMapping
	public String list(Model model) {
		return "demo/list";
	}
	
	@GetMapping("/getPageList")
	@ResponseBody
	public DatatablesViewPage<DemoModel> getDataPage(HttpServletRequest request) {
		//获取分页控件的信息
		String start = request.getParameter("start");
        String length = request.getParameter("length");
        String draw = request.getParameter("draw");
		int page = Integer.valueOf(start).intValue()/Integer.valueOf(length).intValue();
		//获取前台额外传递过来的查询条件
		DatatablesViewPage<DemoModel> model = demoService.list(page, Integer.valueOf(length).intValue());
		model.setDraw(Integer.valueOf(draw));
		//随便组织的查询结果
		return model;
	}
	@PostMapping("/add")
    public String create(DemoModel model) {
		demoService.create(model);
        return "redirect:/demo";
    }

    @PostMapping("/modify")
    public String modify(DemoModel model) {
    	demoService.modify(model);
        return "redirect:/demo";
    }

    @DeleteMapping("/{id}/delete")
    @ResponseBody
    public void delete(@PathVariable("id") Long id) {
    	demoService.delete(id);
    }

    @GetMapping("/form")
    public String toform(@RequestParam(value = "id", required = false) Long id, Model model) {
        String url = null;
        if (id!=null) {
            model.addAttribute("demo", demoService.get(id));
            url = "/demo/modify";
        } else {
            url = "/demo/add";
        }
        model.addAttribute("api", url);
        return "demo/form";
    }
    
	@GetMapping("/test")
	@ResponseBody
	public BaseResponse<?> update(Long id) {
		return this.controllerMethod(() ->{
			demoService.update(id);
		},"demo测试");
	}
	
}
