package br.compneusgppremium.api.controller;

import br.compneusgppremium.api.controller.model.ModeloModel;
import br.compneusgppremium.api.controller.model.PaisModel;
import br.compneusgppremium.api.repository.ModeloRepository;
import br.compneusgppremium.api.repository.PaisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ModeloController {

    @Autowired
    private ModeloRepository repository;

    @GetMapping(path = "/api/modelo/{id}")
    public ResponseEntity consultar(@PathVariable("id") Integer id){
        return repository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(path = "/api/modelo/salvar")
    public ModeloModel salvar(@RequestBody ModeloModel modelo){
        return repository.save(modelo);
    }
}
