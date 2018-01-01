package com.pej.repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.pej.domains.Fichefinancement;
import java.util.List;

/**
 * Created by D O N A T I E N on 26/12/2016.
 */
public interface FicheFinancementRepository extends CrudRepository<Fichefinancement, Integer>{
    // liste des fiche financement
    @Query("select ef  from Fichefinancement ef where ef.candidat.idcandidat=?1" )
    List<Fichefinancement> getFicheFinancement(Integer id);


}
