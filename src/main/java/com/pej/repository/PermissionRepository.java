package com.pej.repository;

import org.springframework.data.repository.CrudRepository;

import com.pej.domains.Permission;

public interface PermissionRepository  extends  CrudRepository <Permission, Integer>{

}
