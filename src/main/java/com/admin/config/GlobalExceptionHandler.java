package com.admin.config;

import java.nio.charset.Charset;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.admin.common.exception.MyException;
import com.admin.common.response.BaseResponse;
import com.admin.common.utils.ErrorCode;
import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	
	@ExceptionHandler(MyException.class)
	@ResponseBody
	public BaseResponse<Void> myExceptionHandle(MyException myException, ServletRequest request) {
		 if (request != null && request instanceof ContentCachingRequestWrapper) {
	            ContentCachingRequestWrapper wrapper = (ContentCachingRequestWrapper) request;
	            log.error("request_uri:"+wrapper.getRequestURI());
	            log.error("BAD_REQUEST_BODY:{}", StringUtils.toEncodedString(wrapper.getContentAsByteArray(), Charset.forName(wrapper.getCharacterEncoding())));
	        }
		 BaseResponse<Void> rd = new BaseResponse<Void>();
			ErrorCode ercode = myException.getErrorCode();
			rd.setCode(ercode.value);
			rd.setMsg(ercode.memo);
			log.error("接口报错:",myException);
		    String result = JSONObject.toJSONString(rd);
        return rd;
	}
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public BaseResponse<Void> exceptionHandle(Exception e, ServletRequest request) {
		 if (request != null && request instanceof ContentCachingRequestWrapper) {
	            ContentCachingRequestWrapper wrapper = (ContentCachingRequestWrapper) request;
	            log.error("request_uri:"+wrapper.getRequestURI());
	            log.error("BAD_REQUEST_BODY:{}", StringUtils.toEncodedString(wrapper.getContentAsByteArray(), Charset.forName(wrapper.getCharacterEncoding())));
	        }
		BaseResponse<Void> rd = new BaseResponse<Void>();
		ErrorCode ercode = ErrorCode.ERROR;
		rd.setCode(ercode.value);
		rd.setMsg(ercode.memo);
		log.error("接口报错:",e);
		return rd;
	}
}
