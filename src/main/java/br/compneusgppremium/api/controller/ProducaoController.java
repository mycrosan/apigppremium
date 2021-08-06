package br.compneusgppremium.api.controller;

import br.compneusgppremium.api.controller.model.ProducaoModel;
import br.compneusgppremium.api.repository.ProducaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
public class ProducaoController {

    @Autowired
    private ProducaoRepository repository;

    @GetMapping(path = "/api/producao")
    public List<ProducaoModel> findAll() {
        var it = repository.findAll();
        var producaos = new ArrayList<ProducaoModel>();
        it.forEach(e -> producaos.add(e));
        return producaos;
    }

    @GetMapping(path = "/api/producao/{id}")
    public ResponseEntity consultar(@PathVariable("id") Integer id) {
        return repository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(path = "/api/producao")
    public Object salvar(@RequestBody ProducaoModel producao) {
        try {
            return repository.save(producao);
        } catch (Exception ex) {
            return ex;
        }
    }

    @DeleteMapping(path = "/api/producao/{id}")
    public ResponseEntity <?> delete(@PathVariable("id") Integer id) {
        return repository.findById(id)
                .map(record -> {
                    repository.deleteById(id);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }
}
