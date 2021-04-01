package br.compneusgppremium.api.controller;

import br.compneusgppremium.api.controller.model.PaisModel;
import br.compneusgppremium.api.repository.PaisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PaisController {

    @Autowired
    private PaisRepository repository;

    @GetMapping(path = "/api/pais/{id}")
    public ResponseEntity consultar(@PathVariable("id") Integer id){
        return repository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(path = "/api/pais/salvar")
    public PaisModel salvar(@RequestBody PaisModel pais){
        return repository.save(pais);
    }
}
