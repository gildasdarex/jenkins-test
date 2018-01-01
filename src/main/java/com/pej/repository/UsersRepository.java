package com.pej.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.pej.domains.Roles;
import com.pej.domains.Utilisateur;

public interface UsersRepository extends  CrudRepository <Utilisateur, Integer>{
	@Query("select ut from Utilisateur ut where ut.idusers  not in (select ur.utilisateur.idusers from UsersRole ur  where ur.roles.idrole=?1)" )
	Iterable<Roles> getNotInRoleUser(Integer id);
	
	@Query("select ut from Utilisateur ut where ut.idusers  in (select ur.utilisateur.idusers from UsersRole ur  where ur.roles.idrole=?1)" )
	Iterable<Roles> getInRoleUser(Integer id);
	@Query("select ut from Utilisateur ut where ut.username=?1 and ut.password=?2" )
    Utilisateur verifExistantUser(String username,String password);
	@Query("select ut from Utilisateur ut where ut.username=?1" )
    Utilisateur findByUsername(String username);
   @Query("select ut from Utilisateur ut where ut.firstname=?1 and ut.lastname=?2" )
	    Utilisateur findUserByfristlast(String first,String last);
}
