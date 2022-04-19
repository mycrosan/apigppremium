package br.compneusgppremium.api.controller;

import br.compneusgppremium.api.controller.model.CarcacaModel;
import br.compneusgppremium.api.controller.model.ProducaoModel;
import br.compneusgppremium.api.repository.CarcacaRepository;
import br.compneusgppremium.api.repository.ProducaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
public class ProducaoController {

    @Autowired
    private ProducaoRepository producaoRepository;
    @Autowired
    private CarcacaRepository carcacaRepository;

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
    public Object consultarParametros(@RequestParam Map<Integer, String> parametros) {

        int medida = Integer.parseInt(parametros.get("medida"));
System.out.println(medida);
        try {
            var retornoConsulta = producaoRepository.findByParam(medida);
            if (retornoConsulta.size() > 1) {
                throw new RuntimeException("O sistema encontrou mais de uma carcaca com a mesma etiqueta");
            } else if (retornoConsulta.size() == 1) {
                return retornoConsulta.get(0);
            }
            throw new RuntimeException("Produção " + parametros + " não cadastrada");
        } catch (Exception ex) {
//            ApiError apiError = new ApiError(HttpStatus.EXPECTATION_FAILED, "Não foi encontrado resultado para" + parametros, ex, ex.getCause() != null ? ex.getCause().getCause().getMessage() : "Erro");
            return ex;
        }
    }
}
