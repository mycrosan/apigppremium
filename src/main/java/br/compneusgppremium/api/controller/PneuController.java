package br.compneusgppremium.api.controller;

import br.compneusgppremium.api.controller.model.PneuModel;
import br.compneusgppremium.api.repository.PneuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
public class PneuController {

    @Autowired
    private PneuRepository repository;

    @GetMapping(path = "/api/carcaca")
    public List<PneuModel> findAll() {
        var it = repository.findAll();
        var carcacas = new ArrayList<PneuModel>();
        it.forEach(e -> carcacas.add(e));
        return carcacas;
    }

    @GetMapping(path = "/api/carcaca/{id}")
    public ResponseEntity consultar(@PathVariable("id") Integer id) {
        return repository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(path = "/api/carcaca")
    public Object salvar(@RequestBody PneuModel carcaca) {
        try {
            return repository.save(carcaca);
        } catch (Exception e) {
            return e;
        }
    }

    @DeleteMapping(path = "/api/carcaca/{id}")
    public ResponseEntity <?> delete(@PathVariable("id") Integer id) {
        return repository.findById(id)
                .map(record -> {
                    repository.deleteById(id);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/api/pesquisa/carcaca/{etiqueta}")
    public Object consultarPneu(@PathVariable("etiqueta") String etiqueta) {
        try {
            return repository.findByEtiqueta(etiqueta);
        } catch (Exception e) {
            return e;
        }
    }
}
