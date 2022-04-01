package com.admin.entity;

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
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
public class Role {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** 角色名*/
	private String name;

	/** 描述 */
	private String description;
	/** 排序 */
	private Integer sort = 0;

	/** 状态 是否禁用*/
	private boolean disabled;
	
	@ManyToMany(fetch = FetchType.LAZY,cascade=CascadeType.REFRESH)
	@JsonIgnore
	private Set<SysMenu> sysMenu;
	
	@ManyToMany(mappedBy="role",fetch=FetchType.EAGER)
	@JsonIgnore
	private Set<User> user;
	
	@ManyToMany(fetch = FetchType.EAGER,cascade=CascadeType.REFRESH)
	@JsonIgnore
	private Set<SysResource> sysResource;
	
}
