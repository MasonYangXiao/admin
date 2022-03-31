package com.admin.controller.request.base;

import lombok.Data;

@Data
public class BaseRequest<T> {
	private T param;
}
