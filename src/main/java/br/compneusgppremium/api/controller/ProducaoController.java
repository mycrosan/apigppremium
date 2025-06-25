package br.compneusgppremium.api.controller;

import br.compneusgppremium.api.controller.model.*;
import br.compneusgppremium.api.repository.CarcacaRepository;
import br.compneusgppremium.api.repository.ProducaoRepository;
import br.compneusgppremium.api.repository.UsuarioRepository;
import br.compneusgppremium.api.util.ApiError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.*;


@RestController
public class ProducaoController {

    @Autowired
    private ProducaoRepository producaoRepository;
    @Autowired
    private CarcacaRepository carcacaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private br.compneusgppremium.api.util.UsuarioLogadoUtil usuarioLogadoUtil;

    @PersistenceContext
    EntityManager entityManager;

    @GetMapping(path = "/api/producao")
    public Object findAll() {
        var sql = "SELECT p FROM producao p ORDER BY p.dt_create DESC";
        try {
            Query consulta = entityManager.createQuery(sql);
            return consulta.setMaxResults(100).getResultList();
        } catch (Exception e) {
            return e;
        }
    }

    @GetMapping(path = "/api/producao/{id}")
    public ResponseEntity consultar(@PathVariable("id") Integer id) {
        return producaoRepository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
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
                        // Atualiza o status da carcaça
                        record.setStatus("in_production");
                        record.setStatus_carcaca(statusCarcaca);
                        carcacaRepository.save(record);

                        // Preenche campos da produção
                        producao.setDados(producao.toString());
                        producao.setDt_create(new Date());
                        producao.setDt_update(new Date());
                        producao.setUuid(UUID.randomUUID());

                        // Adiciona o usuário logado como criador
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
    public ResponseEntity atualizar(@PathVariable("id") Integer id, @RequestBody ProducaoModel producao) {
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
                                CarcacaModel updated = carcacaRepository.save(record2);
                                return ResponseEntity.ok().body(updated);
                            });
                    producaoRepository.deleteById(id);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/api/producao/pesquisa")
    public Object consultarProducao(@RequestParam Map<Integer, String> params) {
// Iniciando a consulta
        var sql = "SELECT pro FROM producao pro where 1 = 1";
// Montando a consulta

        String modeloId = params.get("modeloId").equals("null") ? "null" : params.get("modeloId");
        if (!modeloId.equals("null"))
            sql = sql + " and pro.carcaca.modelo.id =" + modeloId;

        String marcaId = params.get("marcaId").equals("null") ? "null" : params.get("marcaId");
        if (!marcaId.equals("null"))
            sql = sql + " and pro.carcaca.modelo.marca.id = " + marcaId;

        String medidaId = params.get("medidaId").equals("null") ? "null" : params.get("medidaId");
        if (!medidaId.equals("null"))
            sql = sql + " and pro.carcaca.medida.id = " + medidaId;

        String paisId = params.get("paisId").equals("null") ? "null" : params.get("paisId");
        if (!paisId.equals("null"))
            sql = sql + " and pro.carcaca.pais.id = " + paisId;

        String numeroEtiqueta = params.get("numeroEtiqueta").equals("null") ? "null" : params.get("numeroEtiqueta");
        if (!numeroEtiqueta.equals("null"))
            sql = sql + " and pro.carcaca.numero_etiqueta = " + "'" + numeroEtiqueta + "'";
//
        sql = sql + " ORDER BY pro.dt_create ASC";

        try {
            Query consulta = entityManager.createQuery(sql);
            return consulta.getResultList();
        } catch (Exception e) {
            return e;
        }

    }
}
