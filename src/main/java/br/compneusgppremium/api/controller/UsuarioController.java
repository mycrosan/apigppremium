package br.compneusgppremium.api.controller;

import br.compneusgppremium.api.controller.model.PneuModel;
import br.compneusgppremium.api.controller.model.UsuarioModel;
import br.compneusgppremium.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @GetMapping(path = "/api/usuario")
    public List<UsuarioModel> findAll() {
        var it = repository.findAll();
        var usuarios = new ArrayList<UsuarioModel>();
        it.forEach(e -> usuarios.add(e));
        return usuarios;
    }


    @GetMapping(path = "/api/usuario/{id}")
    public ResponseEntity consultar(@PathVariable("id") Integer id){
        return repository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(path = "/api/usuario/salvar")
    public UsuarioModel salvar(@RequestBody UsuarioModel usuario){
        return repository.save(usuario);
    }
}
