package br.compneusgppremium.api.repository;

import br.compneusgppremium.api.controller.model.CoberturaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CoberturaRepository extends JpaRepository<CoberturaModel, Integer> {
    Optional<CoberturaModel> findByProducaoId(Integer producaoId);

    @Query("SELECT c FROM CoberturaModel c JOIN c.producao p JOIN p.carcaca ca WHERE ca.numero_etiqueta = :etiqueta")
    Optional<CoberturaModel> findByEtiqueta(String etiqueta);


}
