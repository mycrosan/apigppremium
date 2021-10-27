package br.compneusgppremium.api.controller;

import br.compneusgppremium.api.controller.model.CredenciadosModel;
import br.compneusgppremium.api.repository.CredenciadosRepository;
import br.compneusgppremium.api.util.ApiError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;


@RestController
public class CredenciadosController {

    @Autowired
    private CredenciadosRepository repository;

    @GetMapping(path = "/api/credenciados")
    public List<CredenciadosModel> findAll() {
        var it = repository.findAll();
        var values = new ArrayList<CredenciadosModel>();
        it.forEach(e -> values.add(e));
        return values;
    }

    @GetMapping(path = "/api/credenciados/{id}")
    public ResponseEntity consultar(@PathVariable("id") Integer id) {
        return repository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(path = "/api/credenciados")
    public Object salvar(@RequestPart("files") MultipartFile[] files, @RequestPart("data") CredenciadosModel credenciados) {
        try {
            credenciados.setStatus("pendente");
            return repository.save(credenciados);
        } catch (Exception ex) {
            ApiError apiError = new ApiError(HttpStatus.EXPECTATION_FAILED, "Algo deu errado!", ex, ex.getCause() != null ? ex.getCause().getCause().getMessage() : "Erro");
            return apiError;
        }
    }

    @PutMapping(path = "/api/credenciados/{id}")
    public ResponseEntity atualizar(@PathVariable("id") Integer id, @RequestBody CredenciadosModel credenciados) {
        return repository.findById(id)
                .map(record -> {
                    record.setContrato_social(credenciados.getContrato_social());
                    record.setFotos(credenciados.getFotos());
                    record.setCnpj(credenciados.getCnpj());
                    record.setNm_fantasia(credenciados.getNm_fantasia());
                    record.setNm_gerente_loja(credenciados.getNm_gerente_loja());
                    record.setEmail_loja(credenciados.getEmail_loja());
                    record.setTelefone_loja(credenciados.getTelefone_loja());
                    record.setSite(credenciados.getSite());
                    record.setLogradouro(credenciados.getLogradouro());
                    record.setNumero(credenciados.getNumero());
                    record.setComplemento(credenciados.getComplemento());
                    record.setBairro(credenciados.getBairro());
                    record.setCidade(credenciados.getCidade());
                    record.setUf(credenciados.getUf());
                    record.setCep(credenciados.getCep());
                    record.setLat(credenciados.getLat());
                    record.setLog(credenciados.getLog());
//                    record.setStatus(credenciados.getStatus());
                    CredenciadosModel updated = repository.save(record);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(path = "/api/credenciados/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        return repository.findById(id)
                .map(record -> {
                    repository.deleteById(id);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }

}
