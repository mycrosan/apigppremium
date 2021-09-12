package br.compneusgppremium.api.controller;
import br.compneusgppremium.api.controller.model.CarcacaModel;
import br.compneusgppremium.api.repository.CarcacaRepository;
import br.compneusgppremium.api.util.ApiError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
public class CarcacaController {

    @Autowired
    private CarcacaRepository repository;

    @GetMapping(path = "/api/carcaca")
    public List<CarcacaModel> findAll() {
        var it = repository.findAll();
        var carcacas = new ArrayList<CarcacaModel>();
        it.forEach(e -> carcacas.add(e));
        return carcacas;
    }

    @GetMapping(path = "/api/carcaca/{id}")
    public ResponseEntity consultar(@PathVariable("id") Integer id) {
        return repository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(path = "/api/carcaca/{id}")
    public ResponseEntity atualizar(@PathVariable("id") Integer id, @RequestBody CarcacaModel carcaca) {
        return repository.findById(id)
                .map(record -> {
                    record.setNumero_etiqueta(carcaca.getNumero_etiqueta());
                    record.setDot(carcaca.getDot());
                    record.setModelo(carcaca.getModelo());
                    record.setMedida(carcaca.getMedida());
                    record.setPais(carcaca.getPais());
                    CarcacaModel updated = repository.save(record);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(path = "/api/carcaca")
    public Object salvar(@RequestBody CarcacaModel carcaca) {
        try {
            return repository.save(carcaca);
        } catch (Exception e) {
            return e;
        }
    }

    @DeleteMapping(path = "/api/carcaca/{id}")
    public ResponseEntity <?> delete(@PathVariable("id") Integer id) {
        return repository.findById(id)
                .map(record -> {
                    repository.deleteById(id);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }

//    @GetMapping(path = "/api/carcaca/pesquisa/{etiqueta}")
//    public Object consultarPneu(@PathVariable("etiqueta") String etiqueta) {
//        try {
//            return repository.findByEtiqueta(etiqueta);
//        } catch (Exception e) {
//            return e;
//        }
//    }

    @GetMapping(path = "/api/carcaca/pesquisa/{etiqueta}")
    public Object consultarPneu(@PathVariable("etiqueta") String etiqueta) {
        try {
            var retornoConsulta = repository.findByEtiqueta(etiqueta);
            if (retornoConsulta.size() > 1) {
                throw new RuntimeException("O sistema encontrou mais de uma regra para os parâmetros enviados, revise as regras cadastradas");
            }else if (retornoConsulta.size() == 1) {
                return retornoConsulta.get(0);
            }
            throw new RuntimeException("Carcaça etiqueta " + etiqueta + " não cadastrada");
        } catch (Exception e) {
            ApiError apiError = new ApiError(HttpStatus.EXPECTATION_FAILED, "Não foi encontrado resultado para etiqueta " + etiqueta, e);
            return apiError;
        }
    }
}
