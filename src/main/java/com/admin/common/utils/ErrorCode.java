package com.admin.common.utils;




import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum ErrorCode {

	OK("0", "成功"),
	ERROR("500", "服务器错误"),
	PARAMTER_ERROR("501", "参数错误"),
	REQUEST_ERROR("502", "请求失败"), 
	CLEAR_SUCCESS("1", "清除成功"),
	FIND_NOT("513", "查询结果不存在"), 
	PASSWORD_ERROR("514", "密码错误"), 
	;
	
	
	public String value;
	
	public String memo;

	ErrorCode(String value, String memo) {
		this.value = value;
		this.memo = memo;
	}

	private static final Map<String, ErrorCode> lookup = new HashMap<String, ErrorCode>();

	static {
		for (ErrorCode s : EnumSet.allOf(ErrorCode.class)) {
			lookup.put(s.value, s);
		}
	}

	public static ErrorCode get(String value) {
		return lookup.get(value);
	}
}
