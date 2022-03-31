package com.admin.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;

import com.admin.entity.Role;
import com.admin.entity.SysResource;
import com.admin.repository.RoleRepository;
import com.admin.repository.SysResourceRepository;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

/**
 * 定义受保护的http资源
 * 默认情况下，需要在配置文件中定义url与所需的权限，不能从数据库加载
 * @author Jonsy
 */
@Component
public class HttpSecurityResource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    protected RoleRepository roleRepository;

    @Autowired
    protected SysResourceRepository sysResourceRepository;

    private AntPathMatcher pathMatcher=new AntPathMatcher();

    //访问某个资源object需要什么角色
    @Override
    @Transactional
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        Collection<ConfigAttribute> attributes=new HashSet<>();
        FilterInvocation invocation=(FilterInvocation)object;//对于http资源来说，object是FilterInvocation
        List<Role> roles=roleRepository.findAll();
        if(CollectionUtils.isEmpty(roles)){
            return new HashSet<>();
        }
        String requestUrl=invocation.getRequestUrl();
            roles.stream().forEach(role -> {
            Set<SysResource> resources = role.getSysResource();
            if(CollectionUtils.isEmpty(resources)){
                return;
            }
            resources.stream().filter(resource -> !resource.isDisabled()).forEach(resource -> {
                if(pathMatcher.match(resource.getUrl(),requestUrl)) {
                    attributes.add(new SecurityConfig(role.getName()));
                    return;
                }
            });

        });

        return attributes;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        List<Role> allHttpResources=roleRepository.findAll();
        Collection<ConfigAttribute> attributes=new HashSet<>();
        allHttpResources.stream().forEach(role -> {
            attributes.add(new SecurityConfig(role.getName()));
        });
        return attributes;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
