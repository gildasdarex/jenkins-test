package com.pej.services;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.vote.AbstractAccessDecisionManager;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
public class MyAccessDecisionManager extends AbstractAccessDecisionManager {
    public MyAccessDecisionManager(List<AccessDecisionVoter<? extends Object>> decisionVoters) {
        super(decisionVoters);
    }

    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        if(configAttributes==null){

            throw new AccessDeniedException("!");
        }
        Iterator<ConfigAttribute> ite = configAttributes.iterator();
        while(ite.hasNext()){
            ConfigAttribute ca = ite.next();
            String needRole = (ca).getAttribute();
            for (GrantedAuthority ga : authentication.getAuthorities()){

                if (needRole.equals(ga.getAuthority())){
                   return;
                }
                else{
                    throw new AccessDeniedException("!");

                }
            }
        }
        throw new AccessDeniedException("!");
    }
    @Override
    public boolean supports(ConfigAttribute attribute) {
        return false;
    }

    /**
     * Iterates through all <code>AccessDecisionVoter</code>s and ensures each can support
     * the presented class.
     * <p>
     * If one or more voters cannot support the presented class, <code>false</code> is
     * returned.
     *
     * @param clazz the type of secured object being presented
     * @return true if this type is supported
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

}


