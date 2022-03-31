package com.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.admin.entity.SysMenu;

public interface SysMenuRepository extends JpaRepository<SysMenu, Long>,JpaSpecificationExecutor<SysMenu> {
	
	List<SysMenu> findAllByStatusOrderBySort(Boolean status);
	
} 
