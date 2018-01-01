package com.pej.repository;

import com.pej.domains.Utilisateur;
import org.springframework.data.repository.CrudRepository;

import com.pej.domains.UsersRole;;

public interface UserRoleRepository  extends  CrudRepository <UsersRole, Integer>{
    UsersRole findByUtilisateurUsername(String username);
}
