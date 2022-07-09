package br.compneusgppremium.api.controller;


import br.compneusgppremium.api.controller.model.TipoObservacaoModel;
import br.compneusgppremium.api.repository.TipoObservacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TipoObservacaoController {

    @Autowired
    private TipoObservacaoRepository repository;

    @GetMapping(path = "/api/tipoobservacao")
    public List<TipoObservacaoModel> findAll() {
        var it = repository.findAll();
        var values = new ArrayList<TipoObservacaoModel>();
        it.forEach(e -> values.add(e));
        return values;
    }

    @GetMapping(path = "/api/tipoobservacao/{id}")
    public ResponseEntity consultar(@PathVariable("id") Integer id) {
        return repository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(path = "/api/tipoobservacao")
    public Object salvar(@RequestBody TipoObservacaoModel value) {
        try {
            return repository.save(value);
        } catch (Exception ex) {
            return ex;
        }
    }

    @PutMapping(path = "/api/tipoobservacao/{id}")
    public ResponseEntity atualizar(@PathVariable("id") Integer id, @RequestBody TipoObservacaoModel value) {
        return repository.findById(id)
                .map(record -> {
                    record.setDescricao(value.getDescricao());
                    TipoObservacaoModel updated = repository.save(record);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(path = "/api/tipoobservacao/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        return repository.findById(id)
                .map(record -> {
                    repository.deleteById(id);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }

}
