package com.admin.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;

import com.admin.common.exception.MyException;
import com.admin.common.response.DatatablesViewPage;
import com.admin.common.utils.ErrorCode;
import com.admin.common.utils.TreeUtil;
import com.admin.controller.model.MenuTreeModel;
import com.admin.controller.model.MenuVo;
import com.admin.controller.request.MenuRequest;
import com.admin.controller.request.base.PageListRequest;
import com.admin.controller.response.MenuResponse;
import com.admin.entity.SysMenu;
import com.admin.entity.User;
import com.admin.repository.RoleRepository;
import com.admin.repository.SysMenuRepository;
import com.admin.repository.UserRepository;
import com.admin.security.SecurityUser;
import com.admin.security.SecurityUtil;
import com.admin.sys_menu.HomeInfo;
import com.admin.sys_menu.LogoInfo;
import com.admin.sys_menu.SysMenuResponse;


@Service
public class SysMenuService {
	
	@Resource
	private SysMenuRepository sysMenuRepository;
	@Resource
	private UserRepository userRepository;
	
	@Resource
	private RoleRepository roleRepository;

	@Transactional
	public SysMenuResponse sysMenu() {
		SysMenuResponse res = new SysMenuResponse();
		SecurityUser securityUser = SecurityUtil.getUser();
		User user = userRepository.findById(securityUser.getUid()).get();
		List<SysMenu> menus = new ArrayList<SysMenu>();
		user.getRole().forEach(p ->{
			menus.addAll(p.getSysMenu());
		});
		List<SysMenu> menuList = menus.stream().filter(p -> p.getStatus()).distinct().sorted((o1,o2)->{
			return o1.getSort()-o2.getSort();
		}).collect(Collectors.toList());
		List<MenuVo> menuInfo = new ArrayList<>();
		for (SysMenu e : menuList) {
			MenuVo menuVO = new MenuVo();
			menuVO.setId(e.getId());
			menuVO.setPid(e.getPid());
			menuVO.setHref(e.getHref());
			menuVO.setTitle(e.getTitle());
			menuVO.setIcon(e.getIcon());
			menuVO.setTarget(e.getTarget());
			menuInfo.add(menuVO);
		}
		res.setMenuInfo(TreeUtil.toTree(menuInfo, 0L));
		 HomeInfo homeInfo = new HomeInfo();
		 LogoInfo logoInfo = new LogoInfo();
		 homeInfo.setTitle("首页");
		 homeInfo.setHref("welcome");
		 logoInfo.setTitle("mason");
		 logoInfo.setImage("images/logo.png");
		 res.setLogoInfo(logoInfo);
		 res.setHomeInfo(homeInfo);
		return res;
	}
	
	public DatatablesViewPage<MenuResponse> list(PageListRequest<MenuRequest> request) {
		 List<SysMenu> page = sysMenuRepository.findAll(Sort.by(Direction.ASC,"sort"));
		  List<MenuResponse> data = page.stream().map(p->{
			 MenuResponse menu = new MenuResponse(); 
			 BeanUtils.copyProperties(p, menu);
			 return menu;
		 }).collect(Collectors.toList());
		  DatatablesViewPage<MenuResponse> res = new DatatablesViewPage<MenuResponse>();
		  res.setCode(0);
		  res.setMsg(ErrorCode.OK.memo);
		  res.setCount(data.size());
		  res.setData(data);
		  return res;
	}

	public void create(MenuRequest menu) {
		SysMenu sysMenu = new SysMenu();
		BeanUtils.copyProperties(menu, sysMenu);
		sysMenu.setIcon("fa "+sysMenu.getIcon());
		sysMenu.setCreate_at(LocalDateTime.now());
		sysMenuRepository.save(sysMenu);
	}

	public SysMenu get(Long id) {
		return sysMenuRepository.findById(id).get();
	}

	public void delete(Long id) {
		sysMenuRepository.deleteById(id);
		
	}

	public void switchStatus(Long id, boolean disable) {
		SysMenu sysMenu = sysMenuRepository.findById(id).orElseThrow(() ->{
			return new MyException(ErrorCode.FIND_NOT);
		});
		sysMenu.setStatus(disable);
		sysMenuRepository.save(sysMenu);
		
	}

	public void modify(Long id, MenuRequest menu) {
		SysMenu sysMenu = sysMenuRepository.findById(id).orElseThrow(() ->{
			return new MyException(ErrorCode.FIND_NOT);
		});
		BeanUtils.copyProperties(menu, sysMenu);
		//修正图标
		if(StringUtils.isNotBlank(menu.getIcon())&&!menu.getIcon().equals(sysMenu.getIcon())) {
			sysMenu.setIcon("fa "+menu.getIcon());
		}
		sysMenu.setUpdate_at(LocalDateTime.now());
		sysMenuRepository.save(sysMenu);
	}

	@Transactional
	public List<MenuTreeModel> getMenuTree(Long id) {
		Set<SysMenu> userMenu = roleRepository.findById(id).get().getSysMenu();
		List<SysMenu> sysMenu = sysMenuRepository.findAll();
		List<MenuTreeModel> treeList = new ArrayList<>();
		sysMenu.forEach(p ->{
			MenuTreeModel model = new MenuTreeModel();
			model.setId(p.getId());
			model.setPid(p.getPid());
			model.setDisabled(!p.getStatus());
			model.setTitle(p.getTitle());
			model.setChecked(false);
			if(p.getPid()!= 0) {
				userMenu.forEach(m ->{
					if(p.getId().equals(m.getId())) {
						model.setChecked(true);
					}
				});
			}
			treeList.add(model);
		});
		return TreeUtil.toRoleTree(treeList, 0L);
	}
	
//	private Specification<SysMenu> getwhere(SysMenu sysMenu) {
//		return (root, query, cb) -> {
//			final List<Predicate> predicate = new ArrayList<Predicate>();
//			if (param.getId()!=null) {
//				predicate.add(cb.equal(root.get("id").as(Long.class), param.getId()));
//			}
//			if (param.getGameId()!=null) {
//				predicate.add(cb.equal(root.get("gameId").as(Long.class), param.getGameId()));
//			}
//			if (StringUtils.isNotEmpty(param.getName())) {
//				predicate.add(cb.like(root.get("name").as(String.class), "%"+param.getName()+"%"));
//			}
//			
//			final Predicate[] pre = new Predicate[predicate.size()];
//			return query.where(predicate.toArray(pre)).getRestriction();
//		};
//	}
}
