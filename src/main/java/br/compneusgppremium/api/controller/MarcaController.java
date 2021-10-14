package br.compneusgppremium.api.controller;

import br.compneusgppremium.api.controller.model.MarcaModel;
import br.compneusgppremium.api.controller.model.PaisModel;
import br.compneusgppremium.api.repository.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class MarcaController {

    @Autowired
    private MarcaRepository repository;

    @GetMapping(path = "/api/marca")
    public List<MarcaModel> findAll() {
        var it = repository.findAll();
        var marcas = new ArrayList<MarcaModel>();
        it.forEach(e -> marcas.add(e));
        return marcas;
    }

    @GetMapping(path = "/api/marca/{id}")
    public ResponseEntity consultar(@PathVariable("id") Integer id) {
        return repository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping(path = "/api/marca")
    public Object salvar(@RequestBody MarcaModel marca) {
        try {
            return repository.save(marca);
        } catch (Exception ex) {
            return ex;
        }
    }
    @PutMapping(path = "/api/marca/{id}")
    public ResponseEntity atualizar(@PathVariable("id") Integer id, @RequestBody MarcaModel marca) {
        return repository.findById(id)
                .map(record -> {
                    record.setDescricao(marca.getDescricao());
                    MarcaModel updated = repository.save(record);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(path = "/api/marca/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        return repository.findById(id)
                .map(record -> {
                    repository.deleteById(id);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }
}
