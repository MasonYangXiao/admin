package com.admin.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import com.admin.common.exception.MyException;
import com.alibaba.fastjson.JSONObject;


@Component
public class RestTemplateUtils {

	
	private static final Logger logger = LoggerFactory.getLogger(RestTemplateUtils.class);

	@Autowired
	private RestTemplate restTemplate;
	
	public JSONObject postJson(String url,Map<String, Object> requestBody) {
		 HttpHeaders headers = new HttpHeaders();
		 headers.setContentType(MediaType.APPLICATION_JSON);
		  //将请求头部和参数合成一个请求
	     HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);
		 ResponseEntity<String> result = restTemplate.postForEntity(url,requestEntity,String.class);
		 if(!result.getStatusCode().is2xxSuccessful()){
			 throw new MyException(ErrorCode.REQUEST_ERROR);
		 }
		  	JSONObject json = JSONObject.parseObject(result.getBody());
		 if(!json.getString("code").equals("0")) {
			 logger.error("错误详情:{}",json.getString("msg"));
			 throw new MyException(ErrorCode.ERROR);
		 }
		 return json.getJSONObject("data");
	}
}
