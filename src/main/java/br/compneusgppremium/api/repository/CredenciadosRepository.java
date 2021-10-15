package br.compneusgppremium.api.repository;

import br.compneusgppremium.api.controller.model.CredenciadosModel;
import br.compneusgppremium.api.controller.model.RegraModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "credenciados", path = "credenciados")
public interface CredenciadosRepository extends CrudRepository<CredenciadosModel, Integer> {

}
