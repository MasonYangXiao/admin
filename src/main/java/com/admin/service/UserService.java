package com.admin.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.admin.common.exception.MyException;
import com.admin.common.response.DatatablesViewPage;
import com.admin.common.utils.ErrorCode;
import com.admin.controller.request.PasswordRequest;
import com.admin.controller.request.UserRequest;
import com.admin.controller.response.UserResponse;
import com.admin.entity.Role;
import com.admin.entity.SysMenu;
import com.admin.entity.User;
import com.admin.repository.RoleRepository;
import com.admin.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder md5PasswordEncoder;

	@Autowired
	private RoleRepository roleRepository;

	private void validate(User user) {
		Assert.hasText(user.getUsername(), "账户不能为空");
		if (user.isRoot()) {
			throw new IllegalArgumentException("user loginName cannot is root");
		}
	}

	public void create(UserRequest request) {
		User user = new User();
		BeanUtils.copyProperties(request, user);
		validate(user);
		user.setDisabled(false);
		user.setCreateTime(new Date());
		user.setPassword(md5PasswordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}

	public void modify(String id, UserRequest request) {
		Assert.hasText(id, "参数错误");
		User old = userRepository.findById(id).orElseThrow(() -> {
			return new MyException(ErrorCode.FIND_NOT);
		});
		validate(old);
		if (StringUtils.isNotBlank(request.getUsername())) {
			old.setUsername(request.getUsername());
		}
		if (StringUtils.isNotBlank(request.getEmail())) {
			old.setEmail(request.getEmail());
		}
		userRepository.save(old);

	}

	public void password(String id, PasswordRequest request) {
		Assert.hasText(id, "参数错误");
		User old = userRepository.findById(id).orElseThrow(() -> {
			return new MyException(ErrorCode.FIND_NOT);
		});
		if (!md5PasswordEncoder.matches(request.getOld_password(), old.getPassword())) {
			throw new MyException(ErrorCode.PASSWORD_ERROR);
		}
		;
		old.setPassword(md5PasswordEncoder.encode(request.getPassword()));
		userRepository.save(old);
	}

	public void switchStatus(String id, boolean disable) {
		User user = userRepository.findById(id).orElseThrow(() -> {
			return new MyException(ErrorCode.FIND_NOT);
		});
		validate(user);
		user.setDisabled(disable);
		userRepository.save(user);

	}

	public void delete(String id) {
		User user = userRepository.findById(id).orElseThrow(() -> {
			return new MyException(ErrorCode.FIND_NOT);
		});
		validate(user);
		userRepository.delete(user);
	}

	public void grantRole(String id, ArrayList<Long> ruleId) {
		 Set<Role> roles = roleRepository.findAllById(ruleId).stream().collect(Collectors.toSet());
		 userRepository.findById(id).ifPresent(u ->{
			 u.setRole(roles);
			 userRepository.save(u);
		 });
	}

	public List<User> getUserByEmail(String email) {
		return userRepository.findByEmail(email);

	}

	public User getUserByUname(String username) {
		return userRepository.findByUsername(username);
	}

	public UserResponse get(String id) {
		User user = userRepository.findById(id).orElseThrow(() -> {
			return new MyException(ErrorCode.FIND_NOT);
		});
		UserResponse res = new UserResponse();
		BeanUtils.copyProperties(user, res);
		return res;
	}

	public DatatablesViewPage<UserResponse> list(int page, int limit) {
		Page<User> list = userRepository.findAll(PageRequest.of(page - 1, limit, Sort.by(Direction.ASC, "createTime")));
		List<UserResponse> data = list.stream().map(p -> {
			UserResponse role = new UserResponse();
			BeanUtils.copyProperties(p, role);
			StringBuffer rulesName = new StringBuffer();
			p.getRole().stream().forEach(s -> {
				rulesName.append(s.getName() + " ");
			});
			role.setRoles(rulesName.toString());
			return role;
		}).collect(Collectors.toList());
		DatatablesViewPage<UserResponse> res = new DatatablesViewPage<UserResponse>();
		res.setCode(0);
		res.setMsg(ErrorCode.OK.memo);
		res.setCount(list.getTotalElements());
		res.setData(data);
		return res;
	}

}
