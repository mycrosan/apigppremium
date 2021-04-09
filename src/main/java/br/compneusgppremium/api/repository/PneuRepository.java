package br.compneusgppremium.api.repository;

import br.compneusgppremium.api.controller.model.PneuModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "pneu", path = "pneu")
public interface PneuRepository extends CrudRepository<PneuModel, Integer> {}
