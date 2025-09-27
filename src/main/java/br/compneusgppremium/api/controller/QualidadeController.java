package br.compneusgppremium.api.controller;

import br.compneusgppremium.api.controller.model.*;
import br.compneusgppremium.api.repository.CarcacaRepository;
import br.compneusgppremium.api.repository.ProducaoRepository;
import br.compneusgppremium.api.repository.QualidadeRepository;
import br.compneusgppremium.api.util.ApiError;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;



@RestController
@Tag(name = "Qualidade", description = "Operações relacionadas ao controle de qualidade de pneus")
@SecurityRequirement(name = "Bearer Authentication")
public class QualidadeController {

    @Autowired
    private QualidadeRepository qualidadeRepository;

    @Autowired
    private ProducaoRepository producaoRepository;

    @Autowired
    private CarcacaRepository carcacaRepository;

    @PersistenceContext
    EntityManager entityManager;

//    @GetMapping(path = "/api/qualidade")
//    public List<QualidadeModel> findAll() {
//        var it = qualidadeRepository.findAll();
//        var qualidades = new ArrayList<QualidadeModel>();
//        it.forEach(e -> qualidades.add(e));
//        return qualidades;
//    }
    @Operation(summary = "Listar controles de qualidade", description = "Retorna os últimos 100 controles de qualidade ordenados por data de criação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de controles de qualidade retornada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping(path = "/api/qualidade")
    public Object findAll() {
        var sql = "SELECT cq FROM controle_qualidade cq ORDER BY cq.dt_create DESC";
        try {
            Query consulta = entityManager.createQuery(sql);
            return consulta.setMaxResults(100).getResultList();
        } catch (Exception e) {
            return e;
        }
    }

    @Operation(summary = "Consultar controle de qualidade por ID", description = "Retorna um controle de qualidade específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Controle de qualidade encontrado"),
            @ApiResponse(responseCode = "404", description = "Controle de qualidade não encontrado")
    })
    @GetMapping(path = "/api/qualidade/{id}")
    public ResponseEntity consultar(@Parameter(description = "ID do controle de qualidade") @PathVariable("id") Integer id) {
        return qualidadeRepository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Atualizar controle de qualidade", description = "Atualiza um controle de qualidade existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Controle de qualidade atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Controle de qualidade não encontrado")
    })
    @PutMapping(path = "/api/qualidade/{id}")
    public ResponseEntity atualizar(@Parameter(description = "ID do controle de qualidade") @PathVariable("id") Integer id, @RequestBody QualidadeModel qualidade) {
        return qualidadeRepository.findById(id)
                .map(record -> {
                    record.setProducao(qualidade.getProducao());
                    record.setObservacao(qualidade.getObservacao());
                    record.setTipo_observacao(qualidade.getTipo_observacao());
                    QualidadeModel updated = qualidadeRepository.save(record);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Criar controle de qualidade", description = "Cria um novo controle de qualidade")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Controle de qualidade criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PostMapping(path = "/api/qualidade")
    public Object salvar(@RequestBody QualidadeModel qualidade) {

        try {
            var retornoConsulta = qualidadeRepository.findByProducaoId(qualidade.getProducao().getId());
            if (retornoConsulta.isPresent()) {
                throw new RuntimeException("Carcaça Já qualificada");
            }
        } catch (Exception ex) {
            ApiError apiError = new ApiError(HttpStatus.CONFLICT, "Falha", ex, ex.getCause() != null ? ex.getMessage() : "Erro");
            return apiError;
        }


        try {
            var situacao = qualidade.getTipo_observacao().getTipo_classificacao().getId() == 1 ? 3 : 4;
            var statusCarcaca = new StatusCarcacaModel();
            statusCarcaca.setId(situacao);

            var producao = producaoRepository.findById(qualidade.getProducao().getId());
            return carcacaRepository.findById(producao.get().getCarcaca().getId())
                    .map(record -> {
                        record.setStatus("qualify");
                        record.setStatus_carcaca(statusCarcaca);
                        carcacaRepository.save(record);
                        qualidade.setDt_create(new Date());
                        return qualidadeRepository.save(qualidade);
                    });
        } catch (Exception ex) {
            ApiError apiError = new ApiError(HttpStatus.NOT_IMPLEMENTED, "Falha", ex, ex.getCause() != null ? ex.getMessage() : "Erro");
            return apiError;
        }
    }


    @Operation(summary = "Excluir controle de qualidade", description = "Exclui um controle de qualidade pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Controle de qualidade excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Controle de qualidade não encontrado")
    })
    @DeleteMapping(path = "/api/qualidade/{id}")
    public Object delete(@Parameter(description = "ID do controle de qualidade") @PathVariable("id") Integer id) {
        try {
            return qualidadeRepository.findById(id)
                    .map(record -> {
                        qualidadeRepository.deleteById(id);
                        return ResponseEntity.ok().build();
                    }).orElse(ResponseEntity.notFound().build());
        } catch (Exception ex) {
            System.out.println(ex);
            ApiError apiError = new ApiError(HttpStatus.UNPROCESSABLE_ENTITY, "Não foi possível excluir!" + id, ex, ex.getCause() != null ? ex.getCause().getCause().getMessage() : "Erro");
            return apiError;
        }
    }
    @Operation(summary = "Consultar pneu por etiqueta", description = "Consulta informações de um pneu específico pela etiqueta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pneu encontrado"),
            @ApiResponse(responseCode = "404", description = "Pneu não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping(produces = "application/json; charset=UTF-8", path = "/api/qualidade/pesquisa/{etiqueta}")
    public Object consultarPneu(@Parameter(description = "Etiqueta do pneu") @PathVariable("etiqueta") String etiqueta) {
        try {
            var retornoConsulta = qualidadeRepository.findByEtiqueta(etiqueta);
            if (retornoConsulta.size() > 1) {
                throw new RuntimeException("O sistema encontrou mais de uma carcaca com a mesma etiqueta");
            } else if (retornoConsulta.size() == 1) {
                return retornoConsulta.get(0);
            }
            throw new RuntimeException("Carcaça etiqueta " + etiqueta + " não qualificada");
        } catch (Exception ex) {
            ApiError apiError = new ApiError(HttpStatus.PRECONDITION_REQUIRED, "Carcaça" + etiqueta + " não localizada! Tente qualifica-la", ex, ex.getCause() != null ? ex.getCause().getCause().getMessage() : "Erro");
            return apiError;
        }
    }
}
