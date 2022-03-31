package com.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.admin.entity.User;

public interface UserRepository extends JpaRepository<User, String>,JpaSpecificationExecutor<User> {

	User findByUsername(String username);

	List<User> findByEmail(String email);

}
