package com.admin.common.response;


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
}
