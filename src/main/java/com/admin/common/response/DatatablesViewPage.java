package com.admin.common.response;




import java.util.List;

import lombok.Data;


@Data
public class DatatablesViewPage<T>  {
	 // 状态码
    private Integer code;
    // 业务提示语
    private String msg;
    // 数据对象
    private List<T> data;
    
    private long count; //总数
  
}