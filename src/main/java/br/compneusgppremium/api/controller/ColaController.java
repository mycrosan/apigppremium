package br.compneusgppremium.api.controller;

import br.compneusgppremium.api.controller.dto.ColaComStatusDTO;
import br.compneusgppremium.api.controller.model.ColaModel;
import br.compneusgppremium.api.controller.model.ProducaoModel;
import br.compneusgppremium.api.controller.model.UsuarioModel;
import br.compneusgppremium.api.repository.*;
import br.compneusgppremium.api.util.ApiError;
import br.compneusgppremium.api.util.UsuarioLogadoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/cola")
public class ColaController {
    @Autowired
    private ColaRepository colaRepository;

    @Autowired
    private ProducaoRepository producaoRepository;

    @Autowired
    private UsuarioLogadoUtil usuarioLogadoUtil;

    @Autowired
    private CoberturaRepository coberturaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;


    // POST - Criar Cola
    @PostMapping(produces = "application/json; charset=UTF-8")
    public ResponseEntity<?> salvar(@RequestBody ColaModel cola) {
        try {
            Optional<ProducaoModel> producaoOptional = producaoRepository.findById(cola.getProducao().getId());
            if (producaoOptional.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("mensagem", "Produção não encontrada."));
            }

            ProducaoModel producao = producaoOptional.get();

            // Verifica se já existe cobertura para esse pneu
            if (coberturaRepository.existsByColaProducaoId(producao.getId())) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(Map.of("mensagem", "Já existe cobertura cadastrada para esse pneu."));
            }

            cola.setProducao(producao);

            Long userId = usuarioLogadoUtil.getUsuarioIdLogado();
            UsuarioModel usuario = usuarioRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            cola.setUsuario(usuario);

            ColaModel saved = colaRepository.save(cola);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("mensagem", "Cola cadastrada com sucesso!", "dados", saved));

        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensagem", "Erro ao salvar cola"));
        }
    }


    // GET - Listar todas
    @GetMapping(produces = "application/json; charset=UTF-8")
    public ResponseEntity<?> listar() { // O tipo de retorno agora é ResponseEntity
        try {
            // Busca a lista no repositório
            List<ColaModel> colas = colaRepository.findColasSemCobertura();

            // Retorna a lista no corpo da resposta com status 200 OK
            return ResponseEntity.ok(colas);

        } catch (Exception ex) {
            // Cria o objeto de erro padronizado
            ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Erro ao listar colas sem cobertura", ex, ex.getMessage());

            // Retorna o objeto de erro no corpo com o status 500 INTERNAL_SERVER_ERROR
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiError);
        }
    }

    //    // GET - Buscar por ID
//    @GetMapping(path = "/{id}", produces = "application/json; charset=UTF-8")
//    public ResponseEntity<Object> buscarPorId(@PathVariable("id") Integer id) {
//        return repository.findById(id)
//                .<ResponseEntity<Object>>map(ResponseEntity::ok)
//                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cola não encontrada"));
//    }
//
    // PUT - Atualizar Cola
    @Transactional
    @PutMapping(path = "/{id}", produces = "application/json; charset=UTF-8")
    public ResponseEntity<?> atualizar(@PathVariable("id") Integer id, @RequestBody ColaModel novaCola) {
        try {
            Optional<ColaModel> optionalCola = colaRepository.findById(id);
            if (optionalCola.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("mensagem", "Cola não encontrada."));
            }

            ColaModel colaExistente = optionalCola.get();

            // Valida se já existe cobertura para a produção dessa cola
            if (coberturaRepository.existsByColaProducaoId(colaExistente.getProducao().getId())) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(Map.of("mensagem", "Já existe cobertura cadastrada para esse pneu."));
            }

            // Atualiza dataInicio
            colaExistente.setDataInicio(LocalDateTime.now());

            // Atualiza status, se enviado
            if (novaCola.getStatus() != null) {
                colaExistente.setStatus(novaCola.getStatus());
            }

            // Atualiza responsável
            Long userId = usuarioLogadoUtil.getUsuarioIdLogado();
            UsuarioModel usuario = usuarioRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            colaExistente.setUsuario(usuario);

            ColaModel atualizado = colaRepository.save(colaExistente);

            return ResponseEntity.ok(Map.of("mensagem", "Cola atualizada com sucesso!", "dados", atualizado));

        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensagem", "Erro ao atualizar cola"));
        }
    }


    //
//    // DELETE - Excluir
//    @DeleteMapping(path = "/{id}", produces = "application/json; charset=UTF-8")
//    public ResponseEntity<Object> deletar(@PathVariable("id") Integer id) {
//        return repository.findById(id).map(cola -> {
//            repository.deleteById(id);
//            return ResponseEntity.ok().build();
//        }).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cola não encontrada"));
//    }
//
//    // GET - Buscar por produção
//    @GetMapping(path = "/cola/{colaId}", produces = "application/json; charset=UTF-8")
//    public ResponseEntity<Object> buscarPorCola(@PathVariable("colaId") Integer colaId) {
//        try {
//            Optional<ColaModel> cola = repository.findByColaId(colaId);
//            return cola
//                    .<ResponseEntity<Object>>map(ResponseEntity::ok)
//                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
//                            .body("Cola não encontrada para a produção"));
//        } catch (Exception ex) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao buscar por produção", ex, ex.getMessage()));
//        }
//    }
    @GetMapping(path = "/etiqueta/{etiqueta}", produces = "application/json; charset=UTF-8")
    public ResponseEntity<?> buscarPorEtiqueta(@PathVariable("etiqueta") String etiqueta) {
        try {
            Optional<ColaModel> cola = colaRepository.findByEtiqueta(etiqueta);

            if (cola.isPresent()) {
                ColaModel colaModel = cola.get();

                // Verifica se já existe cobertura para essa produção
                if (coberturaRepository.existsByColaProducaoId(colaModel.getProducao().getId())) {
                    return ResponseEntity.status(HttpStatus.CONFLICT)
                            .body(Map.of("mensagem", "Já existe cobertura cadastrada para esse pneu.", "dados", colaModel));
                }

                return ResponseEntity.ok(Map.of("mensagem", "Cola encontrada com sucesso!", "dados", colaModel));
            } else {
                Optional<ProducaoModel> producao = producaoRepository.findByEtiqueta(etiqueta);

                if (producao.isPresent()) {
                    ProducaoModel producaoModel = producao.get();

                    // Verifica se já existe cobertura para essa produção
                    if (coberturaRepository.existsByColaProducaoId(producaoModel.getId())) {
                        return ResponseEntity.status(HttpStatus.CONFLICT)
                                .body(Map.of("mensagem", "Já existe cobertura cadastrada para esse pneu.", "dados", producaoModel));
                    }

                    return ResponseEntity.ok(Map.of("mensagem", "Produção encontrada, pronta para criar cola.", "dados", producaoModel));
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(Map.of("mensagem", "Etiqueta não encontrada."));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensagem", "Erro ao buscar por etiqueta " + ex.getMessage()));
        }
    }



}
