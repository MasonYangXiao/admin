package com.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.admin.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {


}
