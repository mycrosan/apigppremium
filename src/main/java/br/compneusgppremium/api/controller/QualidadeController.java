package br.compneusgppremium.api.controller;

import br.compneusgppremium.api.controller.model.QualidadeModel;
import br.compneusgppremium.api.repository.QualidadeRepository;
import br.compneusgppremium.api.util.ApiError;
import br.compneusgppremium.api.util.OperationSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;


@RestController
public class QualidadeController {

    // caminho da imagem
//    private static String caminhoImagem = new OperationSystem().placeImageSystem("qualidade");

    @Autowired
    private QualidadeRepository repository;

    @GetMapping(path = "/api/qualidade")
    public List<QualidadeModel> findAll() {
        var it = repository.findAll();
        var qualidades = new ArrayList<QualidadeModel>();
        it.forEach(e -> qualidades.add(e));
        return qualidades;
    }

    @GetMapping(path = "/api/qualidade/{id}")
    public ResponseEntity consultar(@PathVariable("id") Integer id) {
        return repository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(path = "/api/qualidade/{id}")
    public ResponseEntity atualizar(@PathVariable("id") Integer id, @RequestBody QualidadeModel qualidade) {
        return repository.findById(id)
                .map(record -> {
//                    record.setNumero_etiqueta(qualidade.getNumero_etiqueta());
//                    record.setDot(qualidade.getDot());
//                    record.setModelo(qualidade.getModelo());
//                    record.setMedida(qualidade.getMedida());
//                    record.setPais(qualidade.getPais());
                    QualidadeModel updated = repository.save(record);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(path = "/api/qualidade")
    public Object salvar(@RequestBody QualidadeModel qualidade) {
        try {
//            qualidade.setStatus("start");
            return repository.save(qualidade);
        } catch (Exception e) {
            return e;
        }

    }


    @DeleteMapping(path = "/api/qualidade/{id}")
    public Object delete(@PathVariable("id") Integer id) {
        try {
            return repository.findById(id)
                    .map(record -> {
                        repository.deleteById(id);
                        return ResponseEntity.ok().build();
                    }).orElse(ResponseEntity.notFound().build());
        } catch (Exception ex) {
            System.out.println(ex);
            ApiError apiError = new ApiError(HttpStatus.UNPROCESSABLE_ENTITY, "Não foi possível excluir a carçaca " + id, ex, ex.getCause() != null ? ex.getCause().getCause().getMessage() : "Erro");
            return apiError;
        }
    }

    @GetMapping(path = "/api/qualidade/pesquisa/{etiqueta}")
    public Object consultarPneu(@PathVariable("etiqueta") String etiqueta) {
        try {
//            var retornoConsulta = repository.findByEtiqueta(etiqueta);
//            if (retornoConsulta.size() > 1) {
//                throw new RuntimeException("O sistema encontrou mais de uma qualidade com a mesma etiqueta");
//            } else if (retornoConsulta.size() == 1) {
//                return retornoConsulta.get(0);
//            }
            throw new RuntimeException("Carcaça etiqueta " + etiqueta + " não cadastrada");
        } catch (Exception ex) {
            ApiError apiError = new ApiError(HttpStatus.EXPECTATION_FAILED, "Não foi encontrado resultado para etiqueta " + etiqueta, ex, ex.getCause() != null ? ex.getCause().getCause().getMessage() : "Erro");
            return apiError;
        }
    }

    @GetMapping(path = "/api/image/{caminho}/{idImg}")
    @ResponseBody
    public byte[] exibirImagem(@PathVariable("caminho") String caminho, @PathVariable("idImg") String idImg) throws IOException {
        String caminhoImagem = new OperationSystem().placeImageSystem(caminho);
       File imagemArquivo = new File(caminhoImagem + idImg);
       if(idImg != null || idImg.trim().length() > 0 ){
           System.out.println("No if");
           return Files.readAllBytes(imagemArquivo.toPath());
       }
       return null;
    }
}
