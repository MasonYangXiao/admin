package com.admin.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.admin.common.exception.MyException;
import com.admin.common.response.DatatablesViewPage;
import com.admin.common.utils.ErrorCode;
import com.admin.controller.model.MenuTreeModel;
import com.admin.controller.request.RoleRequest;
import com.admin.controller.request.base.PageListRequest;
import com.admin.controller.response.MenuResponse;
import com.admin.controller.response.RoleResponse;
import com.admin.entity.Role;
import com.admin.entity.SysMenu;
import com.admin.entity.SysResource;
import com.admin.repository.RoleRepository;
import com.admin.repository.SysMenuRepository;
import com.admin.repository.SysResourceRepository;
import com.admin.repository.UserRepository;

@Service
public class RoleService {

	@Resource
	private RoleRepository roleRepository;
	
	@Resource
	private UserRepository userRepository;
	
	@Resource
	private SysMenuRepository sysMenuRepository;
	@Resource
	private SysResourceRepository sysResourceRepository;
	
	
	public void create(RoleRequest request) {
		Role role = new Role();
		BeanUtils.copyProperties(request, role);
		roleRepository.save(role);
	}

	public void modify(Long id, RoleRequest request) {
		Role role = roleRepository.findById(id).orElseThrow(() ->{
			return new MyException(ErrorCode.FIND_NOT);
		});
		BeanUtils.copyProperties(request, role);
		roleRepository.save(role);
		
	}

	public void switchStatus(Long id, boolean disable) {
		Role role = roleRepository.findById(id).orElseThrow(() ->{
			return new MyException(ErrorCode.FIND_NOT);
		});
		role.setDisabled(disable);
		roleRepository.save(role);
	}

	public void delete(Long id) {
		roleRepository.deleteById(id);
		
	}

	public DatatablesViewPage<RoleResponse> list(int page, int limit){
		 Page<Role> list = roleRepository.findAll(PageRequest.of(page-1, limit,Sort.by("sort")));
		  List<RoleResponse> data = list.stream().map(p->{
			 RoleResponse role = new RoleResponse(); 
			 BeanUtils.copyProperties(p, role);
			 return role;
		 }).collect(Collectors.toList());
		  DatatablesViewPage<RoleResponse> res = new DatatablesViewPage<RoleResponse>();
		  res.setCode(0);
		  res.setMsg(ErrorCode.OK.memo);
		  res.setCount(list.getTotalElements());
		  res.setData(data);
		return res;
	}

	public RoleResponse get(Long id) {
		Role role = roleRepository.findById(id).orElseThrow(() ->{
			return new MyException(ErrorCode.FIND_NOT);
		});
		RoleResponse res =  new RoleResponse();
		BeanUtils.copyProperties(role, res);
		return res;
	}

	public void grantResource(Long id, ArrayList<Long> resourceIds) {
		 Set<SysResource> sysResource = sysResourceRepository.findAllById(resourceIds).stream().collect(Collectors.toSet());
		 roleRepository.findById(id).ifPresent(role ->{
			 role.setSysResource(sysResource);
			 roleRepository.save(role);
		 });;
	}

	@Transactional
	public void grantMenu(Long id, List<Long> menuIds) {
		 Set<SysMenu> menus = sysMenuRepository.findAllById(menuIds).stream().collect(Collectors.toSet());
		 roleRepository.findById(id).ifPresent(role ->{
			 role.setSysMenu(menus);
			 roleRepository.save(role);
		 });;
	}

	@Transactional
	public List<MenuTreeModel> getRuleTree(String userId) {
		Set<Role> userRule = userRepository.findById(userId).get().getRole();
		 List<Role> roules = roleRepository.findAll();
		 List<MenuTreeModel> treeList = new ArrayList<>();
		 roules.forEach(p ->{
				MenuTreeModel model = new MenuTreeModel();
				model.setId(p.getId());
				model.setPid(0L);
				model.setDisabled(p.isDisabled());
				model.setTitle(p.getName());
				model.setChecked(false);
				userRule.forEach(m ->{
						if(p.getId().equals(m.getId())) {
							model.setChecked(true);
						}
					});
				treeList.add(model);
			});
		return treeList;
	}

}
