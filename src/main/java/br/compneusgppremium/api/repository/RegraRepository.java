package br.compneusgppremium.api.repository;

import br.compneusgppremium.api.controller.model.RegraModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "regra", path = "regra")
public interface RegraRepository extends CrudRepository<RegraModel, Integer> {

    @Query("from regra r where (:medidaPneuRaspado >= tamanho_min and :medidaPneuRaspado <= tamanho_max) and (r.matriz.id = :matrizId)")
    public Iterable<Object> findByMatriz(@Param("matrizId") Integer matrizId, @Param("medidaPneuRaspado") Double medidaPneuRaspado);

}
