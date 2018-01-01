package com.pej.repository;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.pej.domains.Periode;
import com.pej.domains.Suivie;
@Repository
public interface SuivieRepository extends CrudRepository<Suivie, Integer>{
    @Query("select s  from Suivie s where s.entreprise.identreprise=?1 and s.formateur.idformateur=?2 and s.annees.idannees=?3" )
    List<Suivie> getByEFASuivies(Integer id, Integer den, Integer an);
    @Query("select s  from Suivie s where s.entreprise.identreprise=?1 and s.formateur.idformateur=?2" )
    List<Suivie> getBySuiviesCalendar(Integer id, Integer den);
    List<Suivie> findByEntrepriseIdentrepriseOrderByDateDesc(Integer identreprise);
    List<Suivie> findByEntrepriseIdentrepriseAndFormateurIdformateurOrderByDateDesc(Integer identreprise, Integer idformateur);
}

