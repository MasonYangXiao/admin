package com.admin.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class SysResource implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8531280395632574469L;

	/** 唯一资源编码 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** 资源名称 */
	private String title;

	/** 状态 是否禁用*/
	private boolean disabled;

	/** 地址 */
	private String url;

	/** 描述 */
	private String description;
	
	@ManyToMany(mappedBy="sysResource")
	@JsonIgnore
	private Set<Role> roles;
}
