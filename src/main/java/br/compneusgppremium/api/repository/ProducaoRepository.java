package br.compneusgppremium.api.repository;

import br.compneusgppremium.api.controller.model.*;
import net.minidev.json.JSONUtil;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Map;

@RepositoryRestResource(collectionResourceRel = "producao", path = "producao")
public interface ProducaoRepository extends CrudRepository<ProducaoModel, Integer> {

    @Query("from producao p where p.carcaca.numero_etiqueta=:numeroEtiqueta")
    public List<Object> findDuplicate(@Param("numeroEtiqueta") String numeroEtiqueta);

}
