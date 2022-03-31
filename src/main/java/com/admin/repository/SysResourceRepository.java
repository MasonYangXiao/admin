package com.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.admin.entity.SysResource;

public interface SysResourceRepository extends JpaRepository<SysResource, Long> {

	List<SysResource> findByDisabled(boolean disabled);

}
