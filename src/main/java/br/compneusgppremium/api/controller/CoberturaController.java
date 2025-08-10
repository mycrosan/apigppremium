package br.compneusgppremium.api.controller;

import br.compneusgppremium.api.controller.dto.ColaComStatusDTO;
import br.compneusgppremium.api.controller.model.CoberturaModel;
import br.compneusgppremium.api.controller.model.ColaModel;
import br.compneusgppremium.api.controller.model.ProducaoModel;
import br.compneusgppremium.api.repository.CoberturaRepository;
import br.compneusgppremium.api.repository.ColaRepository;
import br.compneusgppremium.api.repository.ProducaoRepository;
import br.compneusgppremium.api.util.ApiError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
    @Autowired
    private ColaRepository colaRepository;

    @PostMapping(produces = "application/json; charset=UTF-8")
    public ResponseEntity<?> salvar(@RequestBody CoberturaModel cobertura) {
        try {
            Optional<ProducaoModel> producaoOptional = producaoRepository.findById(cobertura.getProducao().getId());
            if (producaoOptional.isEmpty()) {
                return ResponseEntity.badRequest().body("Produção não encontrada.");
            }
            ProducaoModel producao = producaoOptional.get();

            // Busca cola válida para essa produção
            Optional<ColaModel> colaOpt = colaRepository.findByProducao(producao);
            if (colaOpt.isEmpty()) {
                return ResponseEntity.badRequest().body("Nenhuma cola cadastrada para esta produção.");
            }

            ColaModel cola = colaOpt.get();

            // Verifica status e validade (exemplo 120 minutos)
            LocalDateTime inicio = cola.getDataInicio();
            if (cola.getStatus() == ColaModel.StatusCola.Vencido ||
                    inicio == null ||
                    inicio.plusMinutes(120).isBefore(LocalDateTime.now())) {
                return ResponseEntity.badRequest().body("Cola está vencida ou inválida para cobertura.");
            }

            cobertura.setProducao(producao);
            cobertura.setDtCreate(new Date());

            CoberturaModel saved = repository.save(cobertura);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);

        } catch (Exception ex) {
            ex.printStackTrace();
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
    @PutMapping(path = "/{id}", produces = "application/json; charset=UTF-8")
    public ResponseEntity<Object> atualizar(@PathVariable("id") Integer id, @RequestBody CoberturaModel novaCobertura) {
        return repository.findById(id)
                .<ResponseEntity<Object>>map(coberturaExistente -> {
                    coberturaExistente.setFotos(novaCobertura.getFotos());
                    coberturaExistente.setProducao(novaCobertura.getProducao());
                    return ResponseEntity.ok(repository.save(coberturaExistente));
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cobertura não encontrada"));
    }


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
            Optional<ColaModel> colaOpt = colaRepository.findByEtiqueta(etiqueta);
            Optional<ProducaoModel> producaoOpt = producaoRepository.findByEtiqueta(etiqueta);

            if (colaOpt.isEmpty() && producaoOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Nenhuma cola ou produção encontrada para a etiqueta");
            }

            ColaModel cola = colaOpt.orElse(null);
            ProducaoModel producao = producaoOpt.orElse(null);

            boolean colaValida = false;
            String mensagem = "Cola não encontrada";

            if (cola != null) {
                // Verifica validade da cola
                LocalDateTime inicio = cola.getDataInicio();

                boolean dentroDoTempo = inicio != null && inicio.plusMinutes(120).isAfter(LocalDateTime.now());

                if ((cola.getStatus() == ColaModel.StatusCola.Aguardando || cola.getStatus() == ColaModel.StatusCola.Pronto)
                        && dentroDoTempo) {
                    colaValida = true;
                    mensagem = "Cola válida para cobertura";
                } else {
                    mensagem = "Cola vencida ou inválida para cobertura";
                }
            }

            ColaComStatusDTO dto = new ColaComStatusDTO(cola, producao, colaValida, mensagem);

            return ResponseEntity.ok(dto);

        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao buscar por etiqueta", ex, ex.getMessage()));
        }
    }

}
