package br.compneusgppremium.api.controller;

import br.compneusgppremium.api.controller.model.PneuModel;
import br.compneusgppremium.api.repository.PneuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        } catch (Exception ex) {
            return ex;
        }
    }
}
