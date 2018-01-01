package com.pej.repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.pej.domains.Entreprise;
import com.pej.domains.EntrepriseFormateur;
import com.pej.domains.Formateur;

import java.util.ArrayList;
import java.util.List;
@Repository
public interface EntreprisesFormateursRepository extends CrudRepository<EntrepriseFormateur, Integer>{
    // liste des rformateurs
    @Query("select ef.formateur  from EntrepriseFormateur ef where ef.entreprise.identreprise=?1" )
    List<Formateur> getEntreprisesFormateurs(Integer id);
    @Query("select ef  from EntrepriseFormateur ef where ef.entreprise.identreprise=?1 and ef.formateur.idformateur=?2" )
    List<EntrepriseFormateur> getByEntreprisesFormateurs(Integer id,Integer den);
    // liste des entreprises
    @Query("select ef.entreprise  from EntrepriseFormateur ef where ef.formateur.idformateur=?1" )
    List<Entreprise> getEntreprisesDuFormateur(Integer id);


}
