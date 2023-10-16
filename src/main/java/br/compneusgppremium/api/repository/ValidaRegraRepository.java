package br.compneusgppremium.api.repository;

import br.compneusgppremium.api.controller.model.PaisModel;
import br.compneusgppremium.api.controller.model.ValidaRegraModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "valida_regra", path = "valida_regra")
public interface ValidaRegraRepository extends CrudRepository<ValidaRegraModel, Integer> {
}
