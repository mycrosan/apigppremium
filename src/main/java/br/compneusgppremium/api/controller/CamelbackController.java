package br.compneusgppremium.api.controller;

import br.compneusgppremium.api.controller.model.PaisModel;
import br.compneusgppremium.api.repository.PaisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CamelbackController {

    @Autowired
    private PaisRepository repository;

    @GetMapping(path = "/api/camelback")
    public List<PaisModel> findAll() {
        var it = repository.findAll();
        var pais = new ArrayList<PaisModel>();
        it.forEach(e -> pais.add(e));
        return pais;
    }

    @GetMapping(path = "/api/camelback/{id}")
    public ResponseEntity consultar(@PathVariable("id") Integer id) {
        return repository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(path = "/api/camelback")
    public Object salvar(@RequestBody PaisModel pais) {
        try {
            return repository.save(pais);
        } catch (Exception ex) {
            return ex;
        }
    }

    @DeleteMapping(path = "/api/camelback/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        return repository.findById(id)
                .map(record -> {
                    repository.deleteById(id);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }
}
