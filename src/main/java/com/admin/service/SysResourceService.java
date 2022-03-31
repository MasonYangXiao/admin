package com.admin.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
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
import com.admin.controller.request.ResourceRequest;
import com.admin.controller.response.ResourceResponse;
import com.admin.entity.Role;
import com.admin.entity.SysResource;
import com.admin.repository.RoleRepository;
import com.admin.repository.SysResourceRepository;

@Service
public class SysResourceService {

	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private SysResourceRepository sysResourceRepository;
	public void create(ResourceRequest request) {
		SysResource sysResource = new SysResource();
		BeanUtils.copyProperties(request, sysResource);
		sysResource.setDisabled(false);
		sysResourceRepository.save(sysResource);
		
	}

	public void modify(Long id, ResourceRequest request) {
		SysResource sysResource = sysResourceRepository.findById(id).orElseThrow(() -> {
			return new MyException(ErrorCode.FIND_NOT);
		});
		if (StringUtils.isNotBlank(request.getUrl())) {
			sysResource.setUrl(request.getUrl());
		}
		if (StringUtils.isNotBlank(request.getTitle())) {
			sysResource.setTitle(request.getTitle());
		}
		if (StringUtils.isNotBlank(request.getDescription())) {
			sysResource.setDescription(request.getDescription());
		}
		sysResourceRepository.save(sysResource);
		
	}

	public void switchDisable(Long id, boolean disable) {
		SysResource sysResource = sysResourceRepository.findById(id).orElseThrow(() -> {
			return new MyException(ErrorCode.FIND_NOT);
		});
		sysResource.setDisabled(disable);
		sysResourceRepository.save(sysResource);
	}

	public void delete(Long id) {
		SysResource sysResource = sysResourceRepository.findById(id).orElseThrow(() -> {
			return new MyException(ErrorCode.FIND_NOT);
		});
		sysResourceRepository.delete(sysResource);
		
	}

	public ResourceResponse get(Long id) {
		SysResource sysResource = sysResourceRepository.findById(id).orElseThrow(() -> {
			return new MyException(ErrorCode.FIND_NOT);
		});
		ResourceResponse res = new ResourceResponse();
		BeanUtils.copyProperties(sysResource, res);
		return res;
	}
	public DatatablesViewPage<ResourceResponse> list(int page, int limit) {
		Page<SysResource> list = sysResourceRepository.findAll(PageRequest.of(page - 1, limit));
		List<ResourceResponse> data = list.stream().map(p -> {
			ResourceResponse role = new ResourceResponse();
			BeanUtils.copyProperties(p, role);
			return role;
		}).collect(Collectors.toList());
		DatatablesViewPage<ResourceResponse> res = new DatatablesViewPage<ResourceResponse>();
		res.setCode(0);
		res.setMsg(ErrorCode.OK.memo);
		res.setCount(list.getTotalElements());
		res.setData(data);
		return res;
	}

	
	@Transactional
	public List<MenuTreeModel> getResourceTree(Long roleId) {
		Set<SysResource> userSysResource = roleRepository.findById(roleId).get().getSysResource();
		 List<SysResource> sysResources = sysResourceRepository.findAll();
		 List<MenuTreeModel> treeList = new ArrayList<>();
		 sysResources.forEach(p ->{
				MenuTreeModel model = new MenuTreeModel();
				model.setId(p.getId());
				model.setPid(0L);
				model.setDisabled(p.isDisabled());
				model.setTitle(p.getTitle()+":"+p.getUrl());
				model.setChecked(false);
				userSysResource.forEach(m ->{
						if(p.getId().equals(m.getId())) {
							model.setChecked(true);
						}
					});
				treeList.add(model);
			});
		return treeList;
	}

}
