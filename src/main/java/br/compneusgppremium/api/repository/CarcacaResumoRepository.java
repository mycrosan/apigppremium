package br.compneusgppremium.api.repository;

import br.compneusgppremium.api.controller.model.CarcacaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Date;

@Repository
public interface CarcacaResumoRepository extends JpaRepository<CarcacaModel, Integer> {

    @Query("SELECT COUNT(c) FROM carcaca c WHERE c.dt_create BETWEEN :start AND :end")
    Long countByDateRange(Date start, Date end);
}
