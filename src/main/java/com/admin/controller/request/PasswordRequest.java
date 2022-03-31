package com.admin.controller.request;

import lombok.Data;

@Data
public class PasswordRequest {
	
	private String old_password;
	private String password;
	
}
