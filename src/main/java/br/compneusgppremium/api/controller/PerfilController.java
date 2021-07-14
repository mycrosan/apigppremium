package br.compneusgppremium.api.controller;

import br.compneusgppremium.api.controller.model.PerfilModel;
import br.compneusgppremium.api.repository.PerfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class PerfilController {

    @Autowired
    private PerfilRepository repository;

    @GetMapping(path = "/api/perfil")
    public ArrayList<PerfilModel> findAll() {
        var it = repository.findAll();
        ArrayList<PerfilModel> perfis = new ArrayList<>();
        it.forEach(e -> perfis.add(e));
        return perfis;
    }

//    @GetMapping(path = "/api/modelo/{id}")
//    public ResponseEntity consultar(@PathVariable("id") Integer id) {
//        return repository.findById(id)
//                .map(record -> ResponseEntity.ok().body(record))
//                .orElse(ResponseEntity.notFound().build());
//    }
//
//    @PostMapping(path = "/api/modelo")
//    public Object salvar(@RequestBody ModeloModel modelo) {
//        try {
//            return repository.save(modelo);
//        } catch (Exception ex) {
//            return ex;
//        }
//    }
}
