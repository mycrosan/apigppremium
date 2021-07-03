package br.compneusgppremium.api.controller;
import br.compneusgppremium.api.controller.model.BorrachaModel;
import br.compneusgppremium.api.repository.BorrachaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
public class BorrachaController {

    @Autowired
    private BorrachaRepository repository;

    @GetMapping(path = "/api/borracha")
    public List<BorrachaModel> findAll() {
        var it = repository.findAll();
        var borrachas = new ArrayList<BorrachaModel>();
        it.forEach(e -> borrachas.add(e));
        return borrachas;
    }

    @GetMapping(path = "/api/borracha/{id}")
    public ResponseEntity consultar(@PathVariable("id") Integer id) {
        return repository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(path = "/api/borracha")
    public Object salvar(@RequestBody BorrachaModel borracha) {
        try {
            return repository.save(borracha);
        } catch (Exception ex) {
            return ex;
        }
    }
}
