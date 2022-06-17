package br.compneusgppremium.api.controller;

import br.compneusgppremium.api.controller.model.*;
import br.compneusgppremium.api.repository.CarcacaRepository;
import br.compneusgppremium.api.repository.ProducaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.*;


@RestController
public class ProducaoController {

    @Autowired
    private ProducaoRepository producaoRepository;
    @Autowired
    private CarcacaRepository carcacaRepository;

    @PersistenceContext
    EntityManager entityManager;

    @GetMapping(path = "/api/producao")
    public Object findAll() {
        var sql = "SELECT p FROM producao p ORDER BY p.dt_create DESC";
        try {
            Query consulta = entityManager.createQuery(sql);
            return consulta.setMaxResults(50).getResultList();
        } catch (Exception e) {
            return e;
        }
    }

    @GetMapping(path = "/api/producao/{id}")
    public ResponseEntity consultar(@PathVariable("id") Integer id) {
        return producaoRepository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(path = "/api/producao")
    public Object salvar(@RequestBody ProducaoModel producao) {
        var statusCarcaca = new StatusCarcacaModel();
        statusCarcaca.setId(2);
        try {
            return carcacaRepository.findById(producao.getCarcaca().getId())
                    .map(record -> {
                        record.setStatus("in_production");
                        record.setStatus_carcaca(statusCarcaca);
                        carcacaRepository.save(record);
                        producao.setDados(producao.toString());
                        producao.setDt_create(new Date());
                        producao.setUuid(UUID.randomUUID());
                        return producaoRepository.save(producao);
                    });
        } catch (Exception ex) {
            return ex;
        }
    }

    @PutMapping(path = "/api/producao/{id}")
    public ResponseEntity atualizar(@PathVariable("id") Integer id, @RequestBody ProducaoModel producao) {
        return producaoRepository.findById(id)
                .map(record -> {
                    record.setCarcaca(producao.getCarcaca());
                    record.setMedida_pneu_raspado(producao.getMedida_pneu_raspado());
                    record.setRegra(producao.getRegra());
                    ProducaoModel updated = producaoRepository.save(record);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(path = "/api/producao/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        return producaoRepository.findById(id)
                .map(record -> {
                    carcacaRepository.findById(record.getCarcaca().getId())
                            .map(record2 -> {
                                record2.setStatus("start");
                                CarcacaModel updated = carcacaRepository.save(record2);
                                return ResponseEntity.ok().body(updated);
                            });
                    producaoRepository.deleteById(id);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/api/producao/pesquisa")
    public Object consultarProducao(@RequestParam Map<Integer, String> params) {
// Iniciando a consulta
        var sql = "SELECT pro FROM producao pro where 1 = 1";
// Montando a consulta

        String modeloId = params.get("modeloId").equals("null") ? "null" : params.get("modeloId");
        if (!modeloId.equals("null"))
            sql = sql + " and pro.carcaca.modelo.id =" + modeloId;

        String marcaId = params.get("marcaId").equals("null") ? "null" : params.get("marcaId");
        if (!marcaId.equals("null"))
            sql = sql + " and pro.carcaca.modelo.marca.id = " + marcaId;

        String medidaId = params.get("medidaId").equals("null") ? "null" : params.get("medidaId");
        if (!medidaId.equals("null"))
            sql = sql + " and pro.carcaca.medida.id = " + medidaId;

        String paisId = params.get("paisId").equals("null") ? "null" : params.get("paisId");
        if (!paisId.equals("null"))
            sql = sql + " and pro.carcaca.pais.id = " + paisId;

        String numeroEtiqueta = params.get("numeroEtiqueta").equals("null") ? "null" : params.get("numeroEtiqueta");
        if (!numeroEtiqueta.equals("null"))
            sql = sql + " and pro.carcaca.numero_etiqueta = " + "'" + numeroEtiqueta + "'";
//
        sql = sql + " ORDER BY pro.dt_create ASC";

        try {
            Query consulta = entityManager.createQuery(sql);
            return consulta.setMaxResults(50).getResultList();
        } catch (Exception e) {
            return e;
        }

    }
}
