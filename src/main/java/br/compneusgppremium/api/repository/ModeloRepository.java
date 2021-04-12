package br.compneusgppremium.api.repository;

import br.compneusgppremium.api.controller.model.MarcaModel;
import br.compneusgppremium.api.controller.model.ModeloModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "modelo", path = "modelo")
public interface ModeloRepository extends CrudRepository<ModeloModel, Integer> {}
