package br.compneusgppremium.api.repository;

import br.compneusgppremium.api.controller.model.PneuModel;
import br.compneusgppremium.api.controller.model.RegraModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "regra", path = "regra")
public interface RegraRepository extends CrudRepository<RegraModel, Integer> {}
