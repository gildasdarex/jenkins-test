package com.pej.repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.pej.domains.Periode;
@Repository
public interface PeriodeRepository extends CrudRepository<Periode, Integer>{
    Periode findByEntrepriseIdentrepriseAndFormateurIdformateur(Integer idEntreprise, Integer idFormateur);

}
