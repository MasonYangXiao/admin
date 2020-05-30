package com.admin.common.controller;

import com.admin.common.exception.MyException;
import com.admin.common.response.BaseResponse;
import com.admin.common.utils.ErrorCode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 *  Controlle
 * 
 * @author yangxiaodong
 * @data 
 */
public class BaseController {
	private Logger logger = LoggerFactory.getLogger(BaseController.class);

	public <T> BaseResponse<T> controllerMethod(BaseControllerManner m,Class<T> clzz,String message) {
		BaseResponse<T> rd = new BaseResponse<>();
		ErrorCode result = ErrorCode.OK;
		try {
			Object o = m.manner();
			if(o!=null&&clzz.isInstance(o)){
				rd.setData(clzz.cast(o));
		    }
		} catch (MyException e) {
			result = e.getErrorCode();
			logger.error(message, e);
		} catch (Exception e) {
			result = ErrorCode.ERROR;
			logger.error("服务器错误", e);
		}
		rd.setCode(result.value);
		rd.setMsg(result.memo);
		return rd;
	}
	public BaseResponse<?> controllerMethod(BaseControllerVoidManner m,String message) {
		BaseResponse<?> rd = new BaseResponse<>();
		ErrorCode result = ErrorCode.OK;
		try {
			 m.manner();
		} catch (MyException e) {
			result = e.getErrorCode();
			logger.error(message, e);
		} catch (Exception e) {
			result = ErrorCode.ERROR;
			logger.error("服务器错误", e);
		}
		rd.setCode(result.value);
		rd.setMsg(result.memo);
		return rd;
	}
}
