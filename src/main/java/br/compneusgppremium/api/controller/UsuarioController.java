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
    @PostMapping
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
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> atualizarUsuario(@PathVariable("id") Long id, @RequestBody UsuarioModel usuarioRequest) {
        return repository.findById(id)
                .map(usuarioExistente -> {
                    // Atualiza os campos
                    usuarioExistente.setNome(usuarioRequest.getNome());
                    usuarioExistente.setLogin(usuarioRequest.getLogin());

                    // Se a senha foi alterada, criptografa a nova senha
                    if (usuarioRequest.getPassword() != null && !usuarioRequest.getPassword().isEmpty()) {
                        usuarioExistente.setPassword(encoder.encode(usuarioRequest.getPassword()));
                    }

//                    usuarioExistente.setStatus(usuarioRequest.getStatus());

                    // Valida e atualiza os perfis
                    List<PerfilModel> perfisValidados = carregarPerfisValidados(usuarioRequest.getPerfil());
                    usuarioExistente.setPerfil(perfisValidados);

                    UsuarioModel usuarioAtualizado = repository.save(usuarioExistente);
                    return ResponseEntity.ok(usuarioAtualizado);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return repository.findById(id)
                .map(usuario -> {
                    repository.deleteById(id);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
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
