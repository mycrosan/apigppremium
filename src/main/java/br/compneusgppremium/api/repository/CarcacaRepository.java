package br.compneusgppremium.api.repository;

import br.compneusgppremium.api.controller.model.CarcacaModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "carcaca", path = "carcaca")
public interface CarcacaRepository extends CrudRepository<CarcacaModel, Integer> {

    @Query("from carcaca c where c.numero_etiqueta=:numeroEtiqueta")
    public Iterable<CarcacaModel> findByEtiqueta(@Param("numeroEtiqueta") String numeroEtiqueta);

}
