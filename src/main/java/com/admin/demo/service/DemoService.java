package com.admin.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.admin.common.response.DatatablesViewPage;
import com.admin.common.utils.RestTemplateUtils;
import com.admin.common.utils.ServiceRequestUrl;
import com.admin.demo.model.DemoModel;
import com.alibaba.fastjson.JSONObject;

@Service
public class DemoService {
	
	@Autowired
	private RestTemplateUtils restTemplateUtils;

	public DatatablesViewPage<DemoModel> list(int page, int size) {
		  DatatablesViewPage<DemoModel> models = new DatatablesViewPage<DemoModel>();
	        Map<String,Object> body = new HashMap<>();
			body.put("page", String.valueOf(page));
			body.put("size",  String.valueOf(size));
			JSONObject result = restTemplateUtils.postJson(ServiceRequestUrl.testService, body).getJSONObject("demos");
			 Long total = result.getLong("totalElements");
			  List<DemoModel> list = result.getJSONArray("content").stream().map(p ->{
				  DemoModel model = JSONObject.parseObject(p.toString(), DemoModel.class);
				 return model;
			 }).collect(Collectors.toList());
//			  models.setData(list);
//			  models.setRecordsTotal(total.intValue());
//			  models.setRecordsFiltered(total.intValue());
			return models;
	}

	public void update(Long id) {
		// TODO Auto-generated method stub
		
	}

	public Object get(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public void create(DemoModel model) {
		// TODO Auto-generated method stub
		
	}

	public void modify(DemoModel model) {
		// TODO Auto-generated method stub
		
	}

	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

}
