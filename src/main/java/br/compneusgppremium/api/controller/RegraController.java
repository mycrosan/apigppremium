package br.compneusgppremium.api.controller;
import br.compneusgppremium.api.controller.model.RegraModel;
import br.compneusgppremium.api.repository.RegraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
public class RegraController {

    @Autowired
    private RegraRepository repository;

    @GetMapping(path = "/api/regra")
    public List<RegraModel> findAll() {
        var it = repository.findAll();
        var pneus = new ArrayList<RegraModel>();
        it.forEach(e -> pneus.add(e));
        return pneus;
    }

    @GetMapping(path = "/api/regra/{id}")
    public ResponseEntity consultar(@PathVariable("id") Integer id) {
        return repository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(path = "/api/regra")
    public Object salvar(@RequestBody RegraModel regra) {
        try {
            return repository.save(regra);
        } catch (Exception ex) {
            return ex;
        }
    }
    @GetMapping(path = "/api/pesquisa/regra/{matriz}/{medidaPneuRaspado}")
    public Object consultarRegra(@PathVariable("matriz") Integer matriz, @PathVariable("medidaPneuRaspado") Double medidaPneuRaspado) {
        try {
            return repository.findByMatriz(matriz, medidaPneuRaspado);
        } catch (Exception e) {
            return e;
        }
    }
}
