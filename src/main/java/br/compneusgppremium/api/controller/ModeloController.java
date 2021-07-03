package br.compneusgppremium.api.controller;

import br.compneusgppremium.api.controller.model.ModeloModel;
import br.compneusgppremium.api.repository.ModeloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ModeloController {

    @Autowired
    private ModeloRepository repository;

    @GetMapping(path = "/api/modelo")
    public List<ModeloModel> findAll() {
        var it = repository.findAll();
        var modelos = new ArrayList<ModeloModel>();
        it.forEach(e -> modelos.add(e));
        return modelos;
    }

    @GetMapping(path = "/api/modelo/{id}")
    public ResponseEntity consultar(@PathVariable("id") Integer id) {
        return repository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(path = "/api/modelo")
    public Object salvar(@RequestBody ModeloModel modelo) {
        try {
            return repository.save(modelo);
        } catch (Exception ex) {
            return ex;
        }
    }
}
