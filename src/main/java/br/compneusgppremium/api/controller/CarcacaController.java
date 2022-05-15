package br.compneusgppremium.api.controller;

import br.compneusgppremium.api.controller.model.CarcacaModel;
import br.compneusgppremium.api.controller.model.StatusCarcacaModel;
import br.compneusgppremium.api.repository.CarcacaRepository;
import br.compneusgppremium.api.util.ApiError;
import br.compneusgppremium.api.util.OperationSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.IOException;
import java.util.UUID;


@RestController
public class CarcacaController {

    // caminho da imagem
//    private static String caminhoImagem = new OperationSystem().placeImageSystem("carcaca");

    @Autowired
    private CarcacaRepository repository;

    @PersistenceContext
    EntityManager entityManager;

    @GetMapping(path = "/api/carcaca")
    public Object findAll() {
        var sql = "SELECT c FROM carcaca c where c.status = 'start' or c.status_carcaca = 1 ORDER BY c.dt_create DESC";
        try {
            Query consulta = entityManager.createQuery(sql);
            return consulta.setMaxResults(50).getResultList();
        } catch (Exception e) {
            return e;
        }
    }

    @GetMapping(path = "/api/carcaca/{id}")
    public ResponseEntity consultar(@PathVariable("id") Integer id) {
        return repository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(path = "/api/carcaca/{id}")
    public ResponseEntity atualizar(@PathVariable("id") Integer id, @RequestBody CarcacaModel carcaca) {
        return repository.findById(id)
                .map(record -> {
                    record.setNumero_etiqueta(carcaca.getNumero_etiqueta());
                    record.setDot(carcaca.getDot());
                    record.setModelo(carcaca.getModelo());
                    record.setMedida(carcaca.getMedida());
                    record.setPais(carcaca.getPais());
                    CarcacaModel updated = repository.save(record);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(produces = "application/json; charset=UTF-8", path = "/api/carcaca")
    public Object salvar(@RequestBody CarcacaModel carcaca) {
        var statusCarcaca = new StatusCarcacaModel();
        statusCarcaca.setId(1);
        try {
            var retornoConsulta = repository.findByEtiquetaDuplicate(carcaca.getNumero_etiqueta());
            if (retornoConsulta.size() > 0) {
                throw new RuntimeException("Etiqueta duplicada, favor inserir uma etiqueta diferente");
            }
            carcaca.setStatus("start");
            carcaca.setStatus_carcaca(statusCarcaca);
            carcaca.setDados(carcaca.toString());
            carcaca.setDt_create(new Date());
            carcaca.setUuid(UUID.randomUUID());
            return repository.save(carcaca);
        } catch (Exception e) {
            return e;
        }

    }

    @DeleteMapping(produces = "application/json; charset=UTF-8", path = "/api/carcaca/{id}")
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

    @GetMapping(produces = "application/json; charset=UTF-8", path = "/api/carcaca/pesquisa/{etiqueta}")
    public Object consultarPneu(@PathVariable("etiqueta") String etiqueta) {
        try {
            var retornoConsulta = repository.findByEtiqueta(etiqueta);
            if (retornoConsulta.size() > 1) {
                throw new RuntimeException("O sistema encontrou mais de uma carcaca com a mesma etiqueta");
            } else if (retornoConsulta.size() == 1) {
                return retornoConsulta.get(0);
            }
            throw new RuntimeException("Carcaça etiqueta " + etiqueta + " não cadastrada");
        } catch (Exception ex) {
            ApiError apiError = new ApiError(HttpStatus.EXPECTATION_FAILED, "Não foi encontrado resultado para etiqueta " + etiqueta, ex, ex.getCause() != null ? ex.getCause().getCause().getMessage() : "Erro");
            return apiError;
        }
    }

    @GetMapping(path = "/api/image/{caminho}/{idImg}")
    @ResponseBody
    public byte[] exibirImagem(@PathVariable("caminho") String caminho, @PathVariable("idImg") String idImg) throws IOException {
        String caminhoImagem = new OperationSystem().placeImageSystem(caminho);
        File imagemArquivo = new File(caminhoImagem + idImg);
        if (idImg != null || idImg.trim().length() > 0) {
            System.out.println("No if");
            return Files.readAllBytes(imagemArquivo.toPath());
        }
        return null;
    }
}
