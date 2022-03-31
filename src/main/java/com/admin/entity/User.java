package com.admin.entity;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class User {
	private static final long serialVersionUID = 1L;

	/** 主键ID */
	@Id
	@Column(length = 64)
	private String id = UUID.randomUUID().toString();

	/** 登录名称 */
	private String username;

	/** 密码 */
	private String password;
	/** 邮箱 */
	private String email;

	/** 是否禁用 */
	private boolean disabled;

	/** 创建时间 */
	private Date createTime;

	/** 最后登录时间 */
	private Date lastTime;
	
	@ManyToMany(fetch = FetchType.EAGER,cascade=CascadeType.REFRESH)
	@JsonIgnore
    private Set<Role> role;
    
    public boolean isRoot(){
		return "root".equals(username);
	}

}
