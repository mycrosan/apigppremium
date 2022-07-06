package br.compneusgppremium.api.repository;

import br.compneusgppremium.api.controller.model.CarcacaModel;
import br.compneusgppremium.api.controller.model.QualidadeModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "qualidade", path = "qualidade")
public interface QualidadeRepository extends CrudRepository<QualidadeModel, Integer> {

//    @Query("from carcaca c where c.numero_etiqueta=:numeroEtiqueta and c.status='start'")
//    public List<Object> findByEtiqueta(@Param("numeroEtiqueta") String numeroEtiqueta);
//
//    @Query("from carcaca c where c.status=:statusFilter")
//    public List<Object> findAllWithFilter(@Param("statusFilter") String statusFilter);
//
//    @Override
//    @Query("from carcaca c where c.status='start'")
//    public Iterable<CarcacaModel> findAll();

}
