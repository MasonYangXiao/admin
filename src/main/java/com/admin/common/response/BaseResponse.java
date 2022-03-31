package com.admin.common.response;


import com.admin.common.utils.ErrorCode;

import lombok.Data;

/**
 * 
 * @author yangxiaodong
 * @param <T>
 * @data 2018年3月29日
 */
@Data
public class BaseResponse<T> {
	 // 状态码
    private String code;
    // 业务提示语
    private String msg;
    // 数据对象
    private T data;
    
    
	public static BaseResponse<Void> of(ErrorCode errorCode) {
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setCode(errorCode.value);
		baseResponse.setMsg(errorCode.memo);
		return baseResponse;
	}
	
	public static BaseResponse<Void> success() {
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setCode(ErrorCode.OK.value);
		baseResponse.setMsg(ErrorCode.OK.memo);
		return baseResponse;
	}
	public static <T> BaseResponse<T>  success(Object o, Class<T> clzz) {
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setCode(ErrorCode.OK.value);
		baseResponse.setMsg(ErrorCode.OK.memo);
		baseResponse.setData(o);
		return baseResponse;
	}
	
	public static <T> BaseResponse<T> of(ErrorCode errorCode,Object o, Class<T> clzz) {
		BaseResponse<T> rd = new BaseResponse<>();
		if(o!=null&&clzz.isInstance(o)){
			rd.setData(clzz.cast(o));
	    }
		rd.setCode(errorCode.value);
		rd.setMsg(errorCode.memo);
		return rd;
	}
}
