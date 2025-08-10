package br.compneusgppremium.api.controller;

import br.compneusgppremium.api.controller.model.CoberturaModel;
import br.compneusgppremium.api.controller.model.ColaModel;
import br.compneusgppremium.api.controller.model.ProducaoModel;
import br.compneusgppremium.api.repository.CarcacaRepository;
import br.compneusgppremium.api.repository.ColaRepository;
import br.compneusgppremium.api.repository.ProducaoRepository;
import br.compneusgppremium.api.util.ApiError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/cola")
public class ColaController {
    @Autowired
    private ColaRepository colaRepository;

    @Autowired
    private ProducaoRepository producaoRepository;

    // POST - Criar Cola
    @PostMapping(produces = "application/json; charset=UTF-8")
    public ResponseEntity<?> salvar(@RequestBody ColaModel cola) {
        try {
            // Verifica se a produção existe pelo ID vindo no JSON
            Optional<ProducaoModel> producaoOptional = producaoRepository.findById(cola.getProducao().getId());
            if (producaoOptional.isEmpty()) {
                return ResponseEntity.badRequest().body("Produção não encontrada.");
            }

            // Associa a produção existente
            cola.setProducao(producaoOptional.get());

            // Salva no banco
            ColaModel saved = colaRepository.save(cola);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);

        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao salvar cola", ex, ex.getMessage()));
        }
    }


    // GET - Listar todas
    @GetMapping(produces = "application/json; charset=UTF-8")
    public Object listar() {
        try {
            return colaRepository.findAll();
        } catch (Exception ex) {
            return new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao listar colas", ex, ex.getMessage());
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
    @Transactional
    @PutMapping(path = "/{id}", produces = "application/json; charset=UTF-8")
    public ResponseEntity<Object> atualizar(@PathVariable("id") Integer id, @RequestBody ColaModel novaCola) {
        Optional<ColaModel> optionalCola = colaRepository.findById(id);
        if (optionalCola.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cola não encontrada");
        }

        ColaModel colaExistente = optionalCola.get();

        // Atualiza dataInicio para o momento atual
        colaExistente.setDataInicio(LocalDateTime.now());

        // Atualiza status se vier no corpo (opcional)
        if (novaCola.getStatus() != null) {
            colaExistente.setStatus(novaCola.getStatus());
        }

        colaRepository.save(colaExistente);

        return ResponseEntity.ok(colaExistente);
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
    public ResponseEntity<Object> buscarPorEtiqueta(@PathVariable("etiqueta") String etiqueta) {
        try {
            Optional<ColaModel> cola = colaRepository.findByEtiqueta(etiqueta);

            if (cola.isPresent()) {
                return ResponseEntity.ok(cola.get());
            } else {
                Optional<ProducaoModel> producao = producaoRepository.findByEtiqueta(etiqueta);
                return producao
                        .<ResponseEntity<Object>>map(ResponseEntity::ok)
                        .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body("Nenhuma cola ou produção encontrada para a etiqueta"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao buscar por etiqueta", ex, ex.getMessage()));
        }
    }

}
