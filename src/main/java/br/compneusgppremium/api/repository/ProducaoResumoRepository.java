package br.compneusgppremium.api.repository;

import br.compneusgppremium.api.controller.model.ProducaoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface ProducaoResumoRepository extends JpaRepository<ProducaoModel, Integer> {

    @Query("SELECT COUNT(c) FROM producao c WHERE c.dt_create BETWEEN :start AND :end")
    Long countByDateRange(Date start, Date end);
}
