package br.compneusgppremium.api.controller;

import br.compneusgppremium.api.controller.model.CarcacaRejeitadaModel;
import br.compneusgppremium.api.repository.CarcacaRejeitadaRepository;
import br.compneusgppremium.api.util.ApiError;
import br.compneusgppremium.api.util.OperationSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@RestController
public class CarcacaRejeitadaController {

    @Autowired
    private CarcacaRejeitadaRepository repository;

    @PersistenceContext
    EntityManager entityManager;

    @GetMapping(path = "/api/carcacarejeitada")
    public Object findAll() {
        var sql = "SELECT cr FROM carcaca_rejeitada cr ORDER BY cr.dt_create DESC";
        try {
            Query consulta = entityManager.createQuery(sql);
            return consulta.setMaxResults(50).getResultList();
        } catch (Exception e) {
            return e;
        }
    }

    //
    @GetMapping(path = "/api/carcacarejeitada/{id}")
    public ResponseEntity consultar(@PathVariable("id") Integer id) {
        return repository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(path = "/api/carcacarejeitada/{id}")
    public ResponseEntity atualizar(@PathVariable("id") Integer id, @RequestBody CarcacaRejeitadaModel carcaca) {
        return repository.findById(id)
                .map(record -> {
                    record.setModelo(carcaca.getModelo());
                    record.setMedida(carcaca.getMedida());
                    record.setPais(carcaca.getPais());
                    CarcacaRejeitadaModel updated = repository.save(record);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(produces = "application/json; charset=UTF-8", path = "/api/carcacarejeitada")
    public Object salvar(@RequestBody CarcacaRejeitadaModel carcaca) {
        var sql = "SELECT cr FROM carcaca_rejeitada cr where cr.modelo.id=" + carcaca.getModelo().getId() +
                " and cr.medida.id=" + carcaca.getMedida().getId() +
                " and cr.pais.id=" + carcaca.getPais().getId();
// Montando a consulta
        try {
            Query consulta = entityManager.createQuery(sql);
            List values = consulta.getResultList();
            if (values.size() > 0) {
                throw new RuntimeException("Já cadastrada!");
            }
            carcaca.setDt_create(new Date());
            carcaca.setUuid(UUID.randomUUID());
            return repository.save(carcaca);
        } catch (Exception ex) {
            System.out.println(ex);
            ApiError apiError = new ApiError(HttpStatus.OK, ex.getMessage(), ex, ex.getCause() != null ? ex.getCause().toString(): "Erro");
            return apiError;
        }

    }

    @DeleteMapping(produces = "application/json; charset=UTF-8", path = "/api/carcacarejeitada/{id}")
    public Object delete(@PathVariable("id") Integer id) {
        try {
            return repository.findById(id)
                    .map(record -> {
                        repository.deleteById(id);
                        return ResponseEntity.ok().build();
                    }).orElse(ResponseEntity.notFound().build());
        } catch (Exception ex) {
            System.out.println(ex);
            ApiError apiError = new ApiError(HttpStatus.UNPROCESSABLE_ENTITY, "Não foi possível excluir a carçaca " + id, ex, ex.getCause() != null ? ex.getCause().getCause().getMessage() : "Erro");
            return apiError;
        }
    }
}
