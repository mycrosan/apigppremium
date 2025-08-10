package br.compneusgppremium.api.repository;

import br.compneusgppremium.api.controller.model.ColaModel;
import br.compneusgppremium.api.controller.model.ProducaoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ColaRepository extends JpaRepository<ColaModel, Integer> {

    Optional<ColaModel> findByProducaoId(Integer producaoId);

    @Query("SELECT c FROM cola c " +
            "JOIN c.producao p " +
            "JOIN p.carcaca ca " +
            "WHERE ca.numero_etiqueta = :etiqueta")
    Optional<ColaModel> findByEtiqueta(@Param("etiqueta") String etiqueta);
    Optional<ColaModel> findByProducao(ProducaoModel producao);

}
