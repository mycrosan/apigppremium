package br.compneusgppremium.api.controller;

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
            ApiError apiError = new ApiError(HttpStatus.UNPROCESSABLE_ENTITY, ex.getCause() != null ? ex.getCause().getCause().getMessage() : "Erro", ex);
            return apiError;
        }
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
        } catch (Exception e) {
            ApiError apiError = new ApiError(HttpStatus.CONFLICT, "Algo deu errado", e);
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
