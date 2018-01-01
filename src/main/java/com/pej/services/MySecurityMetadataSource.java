package com.pej.services;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.pej.domains.Rolespermission;
import com.pej.domains.Roles;
import com.pej.repository.PermissionRepository;
import com.pej.repository.PermissionRoleRepository;
import com.pej.repository.RolesRepository;
import com.pej.repository.UserRoleRepository;
import com.pej.repository.UsersRepository;
@Component("mySecurityMetadataSource")
public class MySecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    //By a call to spring making
    public MySecurityMetadataSource(PermissionRoleRepository resourcesDao, PermissionRepository permission, UsersRepository usersRepository,UserRoleRepository userRoleRepository ,RolesRepository roleRepository) {
        this.resourcesDao = resourcesDao;
        this.permission=permission;
        this.usersRepository=usersRepository;
        this.userRoleRepository=userRoleRepository;
        this.rolesRepository=roleRepository;
        loadResourceDefine();
    }
@Autowired
    private PermissionRoleRepository resourcesDao;
    @Autowired
    private PermissionRepository permission;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private RolesRepository rolesRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    private static Map<String, Collection<ConfigAttribute>> resourceMap = null;

    public PermissionRoleRepository getResourcesDao() {
        return resourcesDao;
    }

    public void setResourcesDao(PermissionRoleRepository resourcesDao) {
        this.resourcesDao = resourcesDao;
    }

    public Collection<ConfigAttribute> getAllConfigAttributes() {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean supports(Class<?> clazz) {
        // TODO Auto-generated method stub
        return true;
    }
    //Loading all the resources and authority relation making
    private void loadResourceDefine() {
        if(resourceMap == null) {
            resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
            List<Rolespermission> resources =(List<Rolespermission>) this.resourcesDao.findAll();

                for (Roles resource : rolesRepository.findAll()) {
                    Collection<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();
                    for (Rolespermission unrolepermission : this.resourcesDao.listePermissionConcerne(resource.getIdrole()))
                        //To access a package for Spring security Object making
                    {
                        ConfigAttribute configAttribute = new SecurityConfig(permission.findOne(unrolepermission.getIdpermission()).getName());
                        configAttributes.add(configAttribute);
                        resourceMap.put(permission.findOne(unrolepermission.getIdpermission()).getName(), configAttributes);
                    }

                }
                }
        Set<Map.Entry<String, Collection<ConfigAttribute>>> resourceSet = resourceMap.entrySet();
        Iterator<Map.Entry<String, Collection<ConfigAttribute>>> iterator = resourceSet.iterator();

    }




    //Returns the requested resources needed rights making
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {

        String requestUrl = ((FilterInvocation) object).getRequestUrl();
        if(resourceMap == null) {
            loadResourceDefine();

        }
        return resourceMap.get(requestUrl);
    }

}
