package br.compneusgppremium.api.repository;

import br.compneusgppremium.api.controller.model.BorrachaModel;
import br.compneusgppremium.api.controller.model.MarcaModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "borracha", path = "borracha")
public interface BorrachaRepository extends CrudRepository<BorrachaModel, Integer> {
}
