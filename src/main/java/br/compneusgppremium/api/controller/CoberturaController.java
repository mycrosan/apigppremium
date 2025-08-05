package br.compneusgppremium.api.controller;

import br.compneusgppremium.api.controller.model.CoberturaModel;
import br.compneusgppremium.api.controller.model.ProducaoModel;
import br.compneusgppremium.api.repository.CoberturaRepository;
import br.compneusgppremium.api.repository.ProducaoRepository;
import br.compneusgppremium.api.util.ApiError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/cobertura")
public class CoberturaController {

    @Autowired
    private CoberturaRepository repository;
    @Autowired
    private ProducaoRepository producaoRepository;


    @PostMapping(produces = "application/json; charset=UTF-8")
    public ResponseEntity<?> salvar(@RequestBody CoberturaModel cobertura) {

        try {
            // Busca a produção no banco de dados
            Optional<ProducaoModel> producaoOptional = producaoRepository.findById(cobertura.getProducao().getId());

            if (producaoOptional.isEmpty()) {
                return ResponseEntity.badRequest().body("Produção não encontrada.");
            }

            // Associa produção existente
            cobertura.setProducao(producaoOptional.get());
            cobertura.setDtCreate(new Date());

            // Salva e retorna 201 (created)
            CoberturaModel saved = repository.save(cobertura);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);

        } catch (Exception ex) {
            ex.printStackTrace(); // útil para logs de erro

            ApiError apiError = new ApiError(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Erro ao salvar cobertura.",
                    ex,
                    ex.getCause() != null ? ex.getCause().toString() : "Erro interno"
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);
        }
    }


    // GET - Buscar todas
    @GetMapping(produces = "application/json; charset=UTF-8")
    public Object listar() {
        try {
            return repository.findAll();
        } catch (Exception ex) {
            return new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao listar coberturas", ex, ex.getMessage());
        }
    }

    // GET - Buscar cobertura por ID
    @GetMapping(path = "/{id}", produces = "application/json; charset=UTF-8")
    public ResponseEntity<Object> buscarPorId(@PathVariable("id") Integer id) {
        Optional<CoberturaModel> cobertura = repository.findById(id);
        return cobertura
                .<ResponseEntity<Object>>map(ResponseEntity::ok)  // especifica tipo genérico
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cobertura não encontrada"));
    }


    // PUT - Atualizar
//    @PutMapping(path = "/{id}", produces = "application/json; charset=UTF-8")
//    public ResponseEntity<Object> atualizar(@PathVariable("id") Integer id, @RequestBody CoberturaModel novaCobertura) {
//        return repository.findById(id).map(coberturaExistente -> {
//            coberturaExistente.setFotos(novaCobertura.getFotos());
//            coberturaExistente.setProducao(novaCobertura.getProducao());
//            coberturaExistente.setProducaoId(novaCobertura.getProducaoId());
//            return ResponseEntity.ok(repository.save(coberturaExistente));
//        }).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cobertura não encontrada"));
//    }

    // DELETE - Excluir
    @DeleteMapping(path = "/{id}", produces = "application/json; charset=UTF-8")
    public ResponseEntity<Object> deletar(@PathVariable("id") Integer id) {
        return repository.findById(id).map(cobertura -> {
            repository.deleteById(id);
            return ResponseEntity.ok().build();
        }).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cobertura não encontrada"));
    }

    // GET - Buscar por producaoId
    @GetMapping(path = "/producao/{producaoId}", produces = "application/json; charset=UTF-8")
    public ResponseEntity<Object> buscarPorProducao(@PathVariable("producaoId") Integer producaoId) {
        try {
            Optional<CoberturaModel> cobertura = repository.findByProducaoId(producaoId);
            return cobertura
                    .<ResponseEntity<Object>>map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("Cobertura não encontrada para a produção"));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao buscar por produção", ex, ex.getMessage()));
        }
    }

    @GetMapping(path = "/etiqueta/{etiqueta}", produces = "application/json; charset=UTF-8")
    public ResponseEntity<Object> buscarPorEtiqueta(@PathVariable("etiqueta") String etiqueta) {
        try {
            Optional<CoberturaModel> cobertura = repository.findByEtiqueta(etiqueta);
            if (cobertura.isPresent()) {
                return ResponseEntity.ok(cobertura.get());
            } else {
                Optional<ProducaoModel> producao = producaoRepository.findByEtiqueta(etiqueta);
                return producao
                        .<ResponseEntity<Object>>map(ResponseEntity::ok)
                        .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body("Nenhuma cobertura ou produção encontrada para a etiqueta"));
            }
        } catch (Exception ex) {
            // 👇 Isso exibe o erro completo no console
            ex.printStackTrace();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao buscar por etiqueta", ex, ex.getMessage()));
        }
    }
}
