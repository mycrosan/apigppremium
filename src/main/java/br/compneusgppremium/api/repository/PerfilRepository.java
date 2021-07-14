package br.compneusgppremium.api.repository;

import br.compneusgppremium.api.controller.model.PerfilModel;
import br.compneusgppremium.api.controller.model.PneuModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "perfil", path = "perfil")
public interface PerfilRepository extends CrudRepository<PerfilModel, Integer> {}
