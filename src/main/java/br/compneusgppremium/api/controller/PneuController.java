package br.compneusgppremium.api.controller;

import br.compneusgppremium.api.controller.model.PneuModel;
import br.compneusgppremium.api.repository.PneuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PneuController {

    @Autowired
    private PneuRepository repository;

    @GetMapping(path = "/api/pneu/{id}")
    public ResponseEntity consultar(@PathVariable("id") Integer id){
        return repository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(path = "/api/pneu/salvar")
    public PneuModel salvar(@RequestBody PneuModel pneu){
        return repository.save(pneu);
    }
}
