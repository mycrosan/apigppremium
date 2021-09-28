package br.compneusgppremium.api.controller;

import br.compneusgppremium.api.controller.model.CarcacaModel;
import br.compneusgppremium.api.controller.model.RegraModel;
import br.compneusgppremium.api.repository.RegraRepository;
import br.compneusgppremium.api.util.ApiError;
import net.bytebuddy.implementation.bytecode.Throw;
import org.hibernate.engine.jdbc.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@RestController
public class RegraController {

    @Autowired
    private RegraRepository repository;

    @GetMapping(path = "/api/regra")
    public List<RegraModel> findAll() {
        var it = repository.findAll();
        var pneus = new ArrayList<RegraModel>();
        it.forEach(e -> pneus.add(e));
        return pneus;
    }

    @GetMapping(path = "/api/regra/{id}")
    public ResponseEntity consultar(@PathVariable("id") Integer id) {
        return repository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(path = "/api/regra")
    public Object salvar(@RequestBody RegraModel regra) {

        try {
            var retornoConsulta = repository.findByRange(regra.getMatriz().getId(), regra.getMedida().getId(), regra.getModelo().getId(), regra.getPais().getId(), regra.getTamanho_min(), regra.getTamanho_max());
            if (retornoConsulta.size() > 0) {
                throw new RuntimeException("O sistema encontrou uma regra para os parâmetros enviados, revise as regras cadastradas");
            }
            return repository.save(regra);
        } catch (Exception ex) {
            System.out.println(ex);
            ApiError apiError = new ApiError(HttpStatus.EXPECTATION_FAILED, "Regra duplicada", ex, ex.getCause() != null ? ex.getCause().getCause().getMessage() : "Erro");
            return apiError;
        }
    }

    @PutMapping(path = "/api/regra/{id}")
    public ResponseEntity atualizar(@PathVariable("id") Integer id, @RequestBody RegraModel regra) {
        return repository.findById(id)
                .map(record -> {
                    record.setTamanho_min(regra.getTamanho_min());
                    record.setTamanho_max(regra.getTamanho_max());
                    record.setAntiquebra1(regra.getAntiquebra1());
                    record.setAntiquebra2(regra.getAntiquebra2());
                    record.setAntiquebra3(regra.getAntiquebra3());
                    record.setEspessuramento(regra.getEspessuramento());
                    record.setTempo(regra.getTempo());
                    record.setCamelback(regra.getCamelback());
                    record.setMatriz(regra.getMatriz());
                    record.setMedida(regra.getMedida());
                    record.setModelo(regra.getModelo());
                    record.setPais(regra.getPais());
                    RegraModel updated = repository.save(record);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/api/regra/pesquisa/{matriz}/{medida}/{modelo}/{pais}/{medidaPneuRaspado}")
    public Object consultarRegra(@PathVariable("matriz") Integer matriz, @PathVariable("medida") Integer medida, @PathVariable("modelo") Integer modelo, @PathVariable("pais") Integer pais, @PathVariable("medidaPneuRaspado") Double medidaPneuRaspado) {
        try {
            var retornoConsulta = repository.findRule(matriz, medida, modelo, pais, medidaPneuRaspado);
            if (retornoConsulta.size() > 1) {
                throw new RuntimeException("O sistema encontrou mais de uma regra para os parâmetros enviados, revise as regras cadastradas");
            } else if (retornoConsulta.size() == 1) {
                return retornoConsulta.get(0);
            }
            throw new RuntimeException("Nenhuma regra encontrada!");
        } catch (Exception ex) {
            ApiError apiError = new ApiError(HttpStatus.CONFLICT, "O sistema encontrou um problema!", ex, ex.getCause() != null ? ex.getCause().getCause().getMessage() : "Erro");
            return apiError;
        }
    }

    @DeleteMapping(path = "/api/regra/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        return repository.findById(id)
                .map(record -> {
                    repository.deleteById(id);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }

}
