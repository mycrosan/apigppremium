package br.compneusgppremium.api.controller;

import br.compneusgppremium.api.controller.model.PneuModel;
import br.compneusgppremium.api.repository.PneuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
public class PneuController {

    @Autowired
    private PneuRepository repository;

    @GetMapping(path = "/api/pneu")
    public List<PneuModel> findAll() {
        var it = repository.findAll();
        var pneus = new ArrayList<PneuModel>();
        it.forEach(e -> pneus.add(e));
        return pneus;
    }

    @GetMapping(path = "/api/pneu/{id}")
    public ResponseEntity consultar(@PathVariable("id") Integer id) {
        return repository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(path = "/api/pneu")
    public Object salvar(@RequestBody PneuModel pneu) {
        try {
            return repository.save(pneu);
        } catch (Exception e) {
            return e;
        }
    }

    @GetMapping(path = "/api/pesquisa/pneu/{etiqueta}")
    public Object consultarPneu(@PathVariable("etiqueta") String etiqueta) {
        try {
            return repository.findByEtiqueta(etiqueta);
        } catch (Exception e) {
            return e;
        }
    }
}
