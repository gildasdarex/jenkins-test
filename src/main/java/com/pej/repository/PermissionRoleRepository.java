package com.pej.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.pej.domains.Rolespermission;;

public interface PermissionRoleRepository extends  CrudRepository <Rolespermission, Integer> {
	@Query("select rp from Rolespermission rp where rp.roles.idrole=?1 " )
    List<Rolespermission> listePermissionConcerne(Integer idroles);
    @Query("select rp from Rolespermission rp where rp.roles.idrole=?1 and rp.idpermission=?2  " )
    Rolespermission verifExistantDoublon(Integer idrole,Integer idpermission);
}
