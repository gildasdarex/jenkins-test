package com.pej.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import com.pej.domains.Rolespermission;
import com.pej.domains.UsersRole;
import com.pej.domains.Utilisateur;
import com.pej.repository.PermissionRepository;
import com.pej.repository.PermissionRoleRepository;
import com.pej.repository.UsersRepository;

@Service()
public class UserDetailsServiceImpl implements UserDetailsService{
	public static final String USER_ROLE = "Administrateur";
	public static final String USER_NAME = "Administrateur";
    @Autowired
    private UsersRepository userRepository;
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private PermissionRoleRepository permissionRoleRepository;
    @Autowired
    private HttpSession httpSession;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utilisateur user = userRepository.findByUsername(username);
        List<Rolespermission> permit=new ArrayList<>();

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        if(user.getUsersRoles().size()> 0){
            httpSession.setAttribute(USER_ROLE, user.getUsersRoles().stream().findAny().get().getRoles().getName());
        }else{
        	httpSession.setAttribute(USER_ROLE, "Administrateur");
        }
        
        httpSession.setAttribute(USER_NAME, user.getFirstname()+" "+user.getLastname());

        for (UsersRole role : user.getUsersRoles())
        	//System.out.println("Connected userrole"+role.getRoles().getName());
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getRoles().getName()));
//            for (Rolespermission rolpermit : permissionRoleRepository.listePermissionConcerne(role.getRoles().getIdrole())) {
//                grantedAuthorities.add(new SimpleGrantedAuthority(role.getRoles().getName()));
//               // System.out.println("Connected userrole"+rolpermit.getRoles().getName());
//            }
       

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }
}
