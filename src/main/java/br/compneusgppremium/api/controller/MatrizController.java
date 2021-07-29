package br.compneusgppremium.api.controller;

import br.compneusgppremium.api.controller.model.MatrizModel;
import br.compneusgppremium.api.repository.MatrizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
public class MatrizController {

    @Autowired
    private MatrizRepository repository;

    @GetMapping(path = "/api/matriz")
    public List<MatrizModel> findAll() {
        var it = repository.findAll();
        var matrizes = new ArrayList<MatrizModel>();
        it.forEach(e -> matrizes.add(e));
        return matrizes;
    }

    @GetMapping(path = "/api/matriz/{id}")
    public ResponseEntity consultar(@PathVariable("id") Integer id) {
        return repository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(path = "/api/matriz")
    public Object salvar(@RequestBody MatrizModel matriz) {
        try {
            return repository.save(matriz);
        } catch (Exception e) {
            return e;
        }
    }
}
