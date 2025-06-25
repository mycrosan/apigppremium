package br.compneusgppremium.api.controller;

import br.compneusgppremium.api.controller.model.PerfilModel;
import br.compneusgppremium.api.controller.model.UsuarioModel;
import br.compneusgppremium.api.repository.PerfilRepository;
import br.compneusgppremium.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PerfilRepository repositoryPerfil;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @GetMapping
    public List<UsuarioModel> findAll() {
        List<UsuarioModel> usuarios = new ArrayList<>();
        repository.findAll().forEach(usuarios::add);
        return usuarios;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioModel> consultar(@PathVariable("id") Long id) {
        Optional<UsuarioModel> usuarioOpt = repository.findById(id);
        return usuarioOpt
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/salvar")
    public UsuarioModel salvar(@RequestBody UsuarioModel usuario) {
        return repository.save(usuario);
    }

    @PostMapping("/criar")
    @Transactional
    public ResponseEntity<?> criarUsuario(@RequestBody UsuarioModel usuarioRequest) {
        try {
            if (loginJaExiste(usuarioRequest.getLogin())) {
                return ResponseEntity.badRequest().body("Login já existe!");
            }

            criptografarSenha(usuarioRequest);

            List<PerfilModel> perfisValidados = carregarPerfisValidados(usuarioRequest.getPerfil());
            usuarioRequest.setPerfil(perfisValidados);

            UsuarioModel savedUser = repository.save(usuarioRequest);

            return ResponseEntity.status(201).body(savedUser);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao criar usuário: " + e.getMessage());
        }
    }

    private boolean loginJaExiste(String login) {
        return repository.findByLogin(login).isPresent();
    }

    private void criptografarSenha(UsuarioModel usuario) {
        usuario.setPassword(encoder.encode(usuario.getPassword()));
    }

    private List<PerfilModel> carregarPerfisValidados(List<PerfilModel> perfisEnviados) {
        if (perfisEnviados == null || perfisEnviados.isEmpty()) {
            return new ArrayList<>();
        }

        List<Integer> idsPerfis = extrairIdsDosPerfis(perfisEnviados);
        List<PerfilModel> perfisEncontrados = buscarPerfisPorIds(idsPerfis);

        if (perfisEncontrados.size() != idsPerfis.size()) {
            throw new RuntimeException("Um ou mais perfis informados não existem.");
        }

        return perfisEncontrados;
    }

    private List<Integer> extrairIdsDosPerfis(List<PerfilModel> perfis) {
        List<Integer> ids = new ArrayList<>();
        for (PerfilModel perfil : perfis) {
            ids.add(perfil.getId());
        }
        return ids;
    }

    private List<PerfilModel> buscarPerfisPorIds(List<Integer> idsPerfis) {
        return (List<PerfilModel>) repositoryPerfil.findAllById(idsPerfis);
    }


}
