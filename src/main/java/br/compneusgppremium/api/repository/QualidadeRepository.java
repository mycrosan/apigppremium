package br.compneusgppremium.api.repository;
import br.compneusgppremium.api.controller.model.QualidadeModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "controle_qualidade", path = "controle_qualidade")
public interface QualidadeRepository extends CrudRepository<QualidadeModel, Integer> {
}
