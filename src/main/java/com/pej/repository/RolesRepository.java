package com.pej.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.pej.domains.Roles;

public interface RolesRepository  extends  CrudRepository <Roles, Integer> {
	 @Query("select r from Roles r  where r.name=?1" )
	 Roles findByName(String name);
}
