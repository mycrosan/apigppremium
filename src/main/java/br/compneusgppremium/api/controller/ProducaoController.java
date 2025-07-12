package br.compneusgppremium.api.controller;

import br.compneusgppremium.api.controller.dto.ProducaoDTO;
import br.compneusgppremium.api.controller.model.*;
import br.compneusgppremium.api.repository.CarcacaRepository;
import br.compneusgppremium.api.repository.ProducaoRepository;
import br.compneusgppremium.api.repository.UsuarioRepository;
import br.compneusgppremium.api.util.ApiError;
import br.compneusgppremium.api.util.UsuarioLogadoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.*;

import static org.apache.logging.log4j.util.Strings.isNotBlank;

@RestController
public class ProducaoController {

    @Autowired
    private ProducaoRepository producaoRepository;

    @Autowired
    private CarcacaRepository carcacaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioLogadoUtil usuarioLogadoUtil;

    @PersistenceContext
    EntityManager entityManager;

    @GetMapping(path = "/api/producao")
    public List<ProducaoDTO> findAll() {
        var sql = "SELECT p FROM producao p ORDER BY p.dt_create DESC";
        List<ProducaoModel> producoes = entityManager.createQuery(sql, ProducaoModel.class)
                .setMaxResults(100)
                .getResultList();

        List<ProducaoDTO> dtoList = new ArrayList<>();
        for (ProducaoModel p : producoes) {
            dtoList.add(new ProducaoDTO(
                    p.getId(),
                    p.getCarcaca(),
                    p.getMedida_pneu_raspado(),
                    p.getRegra(),
                    p.getFotos(),
                    p.getDt_create(),
                    p.getCriadoPor() != null ? p.getCriadoPor().getNome() : null,
                    p.getCriadoPor() != null ? p.getCriadoPor().getLogin() : null
            ));
        }

        return dtoList;
    }

    @GetMapping(path = "/api/producao/{id}")
    public ResponseEntity<?> consultar(@PathVariable("id") Integer id) {
        return producaoRepository.findById(id)
                .map(record -> {
                    ProducaoModel p = record;
                    ProducaoDTO dto = new ProducaoDTO(
                            p.getId(),
                            p.getCarcaca(),
                            p.getMedida_pneu_raspado(),
                            p.getRegra(),
                            p.getFotos(),
                            p.getDt_create(),
                            p.getCriadoPor() != null ? p.getCriadoPor().getNome() : null,
                            p.getCriadoPor() != null ? p.getCriadoPor().getLogin() : null
                    );
                    return ResponseEntity.ok().body(dto);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(produces = "application/json; charset=UTF-8", path = "/api/producao")
    public Object salvar(@RequestBody ProducaoModel producao) {
        var statusCarcaca = new StatusCarcacaModel();
        statusCarcaca.setId(2);

        var sql = "SELECT p FROM producao p where p.carcaca.id=" + producao.getCarcaca().getId();

        try {
            Query consulta = entityManager.createQuery(sql);
            List values = consulta.getResultList();
            if (!values.isEmpty()) {
                throw new RuntimeException("Já produzido!");
            }

            return carcacaRepository.findById(producao.getCarcaca().getId())
                    .map(record -> {
                        record.setStatus("in_production");
                        record.setStatus_carcaca(statusCarcaca);
                        carcacaRepository.save(record);

                        producao.setDados(producao.toString());
                        producao.setDt_create(new Date());
                        producao.setDt_update(new Date());
                        producao.setUuid(UUID.randomUUID());

                        Long userId = usuarioLogadoUtil.getUsuarioIdLogado();
                        UsuarioModel usuario = usuarioRepository.findById(userId)
                                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
                        producao.setCriadoPor(usuario);

                        return producaoRepository.save(producao);
                    }).orElseThrow(() -> new RuntimeException("Carcaça não encontrada"));

        } catch (Exception ex) {
            ex.printStackTrace();
            ApiError apiError = new ApiError(HttpStatus.OK, ex.getMessage(), ex,
                    ex.getCause() != null ? ex.getCause().toString() : "Erro");
            return apiError;
        }
    }

    @PutMapping(path = "/api/producao/{id}")
    public ResponseEntity<?> atualizar(@PathVariable("id") Integer id, @RequestBody ProducaoModel producao) {
        return producaoRepository.findById(id)
                .map(record -> {
                    record.setCarcaca(producao.getCarcaca());
                    record.setMedida_pneu_raspado(producao.getMedida_pneu_raspado());
                    record.setRegra(producao.getRegra());
                    ProducaoModel updated = producaoRepository.save(record);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(path = "/api/producao/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        return producaoRepository.findById(id)
                .map(record -> {
                    carcacaRepository.findById(record.getCarcaca().getId())
                            .map(record2 -> {
                                record2.setStatus("start");
                                carcacaRepository.save(record2);
                                return null;
                            });
                    producaoRepository.deleteById(id);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/api/producao/pesquisa")
    public ResponseEntity<?> consultarProducao(
            @RequestParam(required = false) String modeloId,
            @RequestParam(required = false) String marcaId,
            @RequestParam(required = false) String medidaId,
            @RequestParam(required = false) String paisId,
            @RequestParam(required = false) String numeroEtiqueta) {

        StringBuilder sql = new StringBuilder("SELECT pro FROM producao pro WHERE 1 = 1");
        Map<String, Object> parametros = new HashMap<>();

        Integer modeloIdInt = parseIntegerOrNull(modeloId);
        Integer marcaIdInt = parseIntegerOrNull(marcaId);
        Integer medidaIdInt = parseIntegerOrNull(medidaId);
        Integer paisIdInt = parseIntegerOrNull(paisId);

        if (modeloIdInt != null) {
            sql.append(" AND pro.carcaca.modelo.id = :modeloId");
            parametros.put("modeloId", modeloIdInt);
        }
        if (marcaIdInt != null) {
            sql.append(" AND pro.carcaca.modelo.marca.id = :marcaId");
            parametros.put("marcaId", marcaIdInt);
        }
        if (medidaIdInt != null) {
            sql.append(" AND pro.carcaca.medida.id = :medidaId");
            parametros.put("medidaId", medidaIdInt);
        }
        if (paisIdInt != null) {
            sql.append(" AND pro.carcaca.pais.id = :paisId");
            parametros.put("paisId", paisIdInt);
        }

        if (numeroEtiqueta != null && !numeroEtiqueta.isBlank() && !"null".equalsIgnoreCase(numeroEtiqueta.trim())) {
            sql.append(" AND pro.carcaca.numero_etiqueta = :numeroEtiqueta");
            parametros.put("numeroEtiqueta", numeroEtiqueta.trim());
        }

        sql.append(" ORDER BY pro.dt_create ASC");

        try {
            Query query = entityManager.createQuery(sql.toString());

            for (Map.Entry<String, Object> entry : parametros.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }

            @SuppressWarnings("unchecked")
            List<ProducaoModel> producoes = query.getResultList();

            List<ProducaoDTO> dtos = new ArrayList<>();
            for (ProducaoModel p : producoes) {
                dtos.add(new ProducaoDTO(
                        p.getId(),
                        p.getCarcaca(),
                        p.getMedida_pneu_raspado(),
                        p.getRegra(),
                        p.getFotos(),
                        p.getDt_create(),
                        p.getCriadoPor() != null ? p.getCriadoPor().getNome() : null,
                        p.getCriadoPor() != null ? p.getCriadoPor().getLogin() : null
                ));
            }

            return ResponseEntity.ok(dtos);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao consultar produção: " + e.getMessage());
        }
    }


    private Integer parseIntegerOrNull(String valor) {
        if (valor == null || valor.isBlank() || valor.equalsIgnoreCase("null")) {
            return null;
        }
        try {
            return Integer.parseInt(valor);
        } catch (NumberFormatException e) {
            return null;
        }
    }

}
