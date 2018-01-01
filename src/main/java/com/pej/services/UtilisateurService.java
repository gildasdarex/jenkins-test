package com.pej.services;

import com.pej.domains.*;
import com.pej.repository.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by darextossa on 7/17/17.
 */

@Service()
public class UtilisateurService {
    Logger logger = LogManager.getLogger(UtilisateurService.class);

    @Autowired private UsersRepository usersRepository;
    @Autowired private RolesRepository roleRepository;
    @Autowired private UserRoleRepository userRoleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    public boolean deleteFormateur(Integer id){
        Utilisateur utilisateur = usersRepository.findOne(id);
        usersRepository.delete(utilisateur);

        return true;
    }



    public boolean createUtilisateur(Utilisateur utilisateur){
        try{
            utilisateur.setPassword(bCryptPasswordEncoder.encode(utilisateur.getPassword()));
            utilisateur = usersRepository.save(utilisateur);
            createRole(utilisateur);
        }
        catch (Exception ex){
            logger.debug("error when create  user " + ex.getMessage());
            return false ;
        }

        return true;
    }


    public boolean editUtilisateur(Utilisateur updateUtilisateur){
        try{
            Utilisateur utilisateur = usersRepository.findOne(updateUtilisateur.getIdusers());

            updateUtilisateur.setUsername(utilisateur.getUsername());
            editRole(updateUtilisateur);

            usersRepository.save(updateUtilisateur);

        }
        catch (Exception ex){
            logger.debug("error when edit  user " + ex.getMessage());
            return false ;
        }

        return true;
    }




    private boolean createRole(Utilisateur utilisateur){
        Roles roles = roleRepository.findByName(utilisateur.getRoleName());
        UsersRole userrole = new UsersRole();
        userrole.setRoles(roles);
        userrole.setUtilisateur(utilisateur);
        userRoleRepository.save(userrole);

        return true;
    }


    private boolean editRole(Utilisateur utilisateur){
        Roles roles = roleRepository.findByName(utilisateur.getRoleName());
        UsersRole userrole = userRoleRepository.findByUtilisateurUsername(utilisateur.getUsername());
        userrole.setRoles(roles);
        userRoleRepository.save(userrole);

        return true;
    }




}
