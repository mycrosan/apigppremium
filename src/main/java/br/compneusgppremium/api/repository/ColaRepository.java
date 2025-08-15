package br.compneusgppremium.api.repository;

// Sugestão: Mova suas entidades para um pacote 'entity' ou 'model' dedicado.
// Ex: import br.compneusgppremium.api.entity.ColaModel;
import br.compneusgppremium.api.controller.model.ColaModel;
import br.compneusgppremium.api.controller.model.ProducaoModel;
import br.compneusgppremium.api.controller.model.CoberturaModel; // Também precisa ser importado para a query
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ColaRepository extends JpaRepository<ColaModel, Integer> {

    Optional<ColaModel> findByProducaoId(Integer producaoId);

    Optional<ColaModel> findByProducao(ProducaoModel producao);

    @Query("SELECT c FROM ColaModel c " +
            "JOIN c.producao p " +
            "JOIN p.carcaca ca " +
            "WHERE ca.numero_etiqueta = :etiqueta")
    Optional<ColaModel> findByEtiqueta(@Param("etiqueta") String etiqueta);

    /**
     * Busca todas as Colas que ainda não possuem uma Cobertura associada.
     */
    @Query("SELECT c FROM ColaModel c " +
            "WHERE NOT EXISTS (" +
            "   SELECT 1 FROM CoberturaModel cb " +
            "   WHERE cb.producao.id = c.producao.id" +
            ")")
    List<ColaModel> findColasSemCobertura();

}