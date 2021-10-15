package br.compneusgppremium.api.controller;
import br.compneusgppremium.api.controller.model.MedidaModel;
import br.compneusgppremium.api.controller.model.PaisModel;
import br.compneusgppremium.api.repository.MedidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class MedidaController {

    @Autowired
    private MedidaRepository repository;

    @GetMapping(path = "/api/medida")
    public List<MedidaModel> findAll() {
        var it = repository.findAll();
        var medidas = new ArrayList<MedidaModel>();
        it.forEach(e -> medidas.add(e));
        return medidas;
    }

    @GetMapping(path = "/api/medida/{id}")
    public ResponseEntity consultar(@PathVariable("id") Integer id) {
        return repository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(path = "/api/medida")
    public Object salvar(@RequestBody MedidaModel medida) {
        try {
            return repository.save(medida);
        } catch (Exception ex) {
            return ex;
        }
    }

    @PutMapping(path = "/api/medida/{id}")
    public ResponseEntity atualizar(@PathVariable("id") Integer id, @RequestBody MedidaModel medida) {
        return repository.findById(id)
                .map(record -> {
                    record.setDescricao(medida.getDescricao());
                    MedidaModel updated = repository.save(record);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(path = "/api/medida/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        return repository.findById(id)
                .map(record -> {
                    repository.deleteById(id);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }
}
