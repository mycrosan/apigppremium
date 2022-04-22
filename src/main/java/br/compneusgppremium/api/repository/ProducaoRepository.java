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
    //@Query("select u.userName from User u inner join u.area ar where ar.idArea = :idArea")

    String query  = "from producao p inner join p.carcaca c inner join c.modelo m where c.medida.id = :medidaId and m.marca.id = :marcaId and c.modelo.id = :modeloId and c.pais.id = :paisId";

    @Query(query)
    public List<Object> findByParam(@Param("medidaId") Integer medidaId, @Param("marcaId") Integer marcaId, @Param("modeloId") Integer modeloId, @Param("paisId") Integer paisId);
//    public List<Object> findByParam(@Param("medidaId") Integer medidaId);

}
