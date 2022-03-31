package com.admin.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "system_menu")
public class SysMenu implements Serializable {
  /**
	 * 
	 */
	private static final long serialVersionUID = -5490910470983189109L;
	// 复合主键要用这个注解
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String href;
    private Long pid;
    private String icon;
    private String target;
    private Integer sort;
    private Boolean status;
    private String remark;
    @CreatedDate
    private LocalDateTime create_at;
    @CreatedDate
    private LocalDateTime update_at;
    private LocalDateTime delete_at;
    
    @ManyToMany(mappedBy="sysMenu")
	@JsonIgnore
    private Set<Role> roles;
}
