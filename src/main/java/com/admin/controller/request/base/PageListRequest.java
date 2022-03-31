package com.admin.controller.request.base;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class PageListRequest<T> extends BaseRequest<T>  {

	private int page;
	private int size;
}
