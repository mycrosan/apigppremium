package br.compneusgppremium.api.repository;

import br.compneusgppremium.api.controller.model.PneuModel;
import br.compneusgppremium.api.controller.model.UsuarioModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "carcaca", path = "carcaca")
public interface PneuRepository extends CrudRepository<PneuModel, Integer> {

    @Query("from carcaca c where c.numero_etiqueta=:numeroEtiqueta")
    public Iterable<PneuModel> findByEtiqueta(@Param("numeroEtiqueta") String numeroEtiqueta);

}
