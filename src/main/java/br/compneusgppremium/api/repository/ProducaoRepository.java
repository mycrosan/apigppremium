package br.compneusgppremium.api.repository;

import br.compneusgppremium.api.controller.model.ProducaoModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Map;

@RepositoryRestResource(collectionResourceRel = "producao", path = "producao")
public interface ProducaoRepository extends CrudRepository<ProducaoModel, Integer> {
//@Query("select u.userName from User u inner join u.area ar where ar.idArea = :idArea")
    @Query("from producao p inner join p.carcaca c where c.medida = 1")
    public List<Object> findByParam(@Param("medidaId") Integer medida);

}
