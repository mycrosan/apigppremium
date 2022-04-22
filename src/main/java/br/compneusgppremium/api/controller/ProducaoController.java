package br.compneusgppremium.api.controller;

import br.compneusgppremium.api.controller.model.*;
import br.compneusgppremium.api.repository.CarcacaRepository;
import br.compneusgppremium.api.repository.ProducaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
public class ProducaoController {

    @Autowired
    private ProducaoRepository producaoRepository;
    @Autowired
    private CarcacaRepository carcacaRepository;

    @PersistenceContext
    EntityManager entityManager;


    @GetMapping(path = "/api/producao")
    public List<ProducaoModel> findAll() {
        var it = producaoRepository.findAll();
        var values = new ArrayList<ProducaoModel>();
        it.forEach(e -> values.add(e));
        return values;
    }


    @GetMapping(path = "/api/producao/{id}")
    public ResponseEntity consultar(@PathVariable("id") Integer id) {
        return producaoRepository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(path = "/api/producao")
    public Object salvar(@RequestBody ProducaoModel producao) {
        try {
            return carcacaRepository.findById(producao.getCarcaca().getId())
                    .map(record -> {
                        record.setStatus("in_production");
                        CarcacaModel updated = carcacaRepository.save(record);
                        return producaoRepository.save(producao);
                    });
        } catch (Exception ex) {
            return ex;
        }
    }

    @PutMapping(path = "/api/producao/{id}")
    public ResponseEntity atualizar(@PathVariable("id") Integer id, @RequestBody ProducaoModel producao) {
        return producaoRepository.findById(id)
                .map(record -> {
                    record.setCarcaca(producao.getCarcaca());
                    record.setMedida_pneu_raspado(producao.getMedida_pneu_raspado());
                    record.setDados(producao.getDados());
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
//    public Object consultarProducao(@RequestParam("medida") Integer medidaId, @RequestParam("marca") MarcaModel marca, @RequestParam("modelo") ModeloModel modelo, @RequestParam("pais") PaisModel pais) {
    public Object consultarProducao(@RequestParam Map<Integer, String> params) {

        try {
            System.out.println(ProducaoModel.class);
            Query consulta = entityManager.createQuery( "SELECT p FROM producao p");
            return consulta.getSingleResult();
        } catch (Exception e) {
            return e;
        }

//        try {
//
//            Integer medidaId = Integer.parseInt(params.get("medidaId"));
//            Integer marcaId = Integer.parseInt(params.get("marcaId"));
//            Integer modeloId = Integer.parseInt(params.get("modeloId"));
//            Integer paisId = Integer.parseInt(params.get("paisId"));
//
//            var retornoConsulta = producaoRepository.findByParam(medidaId, marcaId, modeloId, paisId);
//
//            if (retornoConsulta.size() > 1) {
//                throw new RuntimeException("O sistema encontrou mais de uma carcaca com a mesma etiqueta");
//            } else if (retornoConsulta.size() == 1) {
//                return retornoConsulta.get(0);
//            }
//            throw new RuntimeException("Produção não cadastrada");
//        } catch (Exception ex) {
////            ApiError apiError = new ApiError(HttpStatus.EXPECTATION_FAILED, "Não foi encontrado resultado para" + parametros, ex, ex.getCause() != null ? ex.getCause().getCause().getMessage() : "Erro");
//            return ex;
//        }
    }
}
