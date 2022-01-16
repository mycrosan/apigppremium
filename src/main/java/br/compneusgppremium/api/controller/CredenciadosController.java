package br.compneusgppremium.api.controller;

import br.compneusgppremium.api.controller.model.CredenciadosModel;
import br.compneusgppremium.api.message.ResponseMessage;
import br.compneusgppremium.api.repository.CredenciadosRepository;
import br.compneusgppremium.api.util.ApiError;
import com.google.gson.Gson;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


@RestController
public class CredenciadosController {

    @Autowired
    private CredenciadosRepository repository;

    private HttpServletRequest request;

    @GetMapping(path = "/api/credenciados")
    public List<CredenciadosModel> findAll() {
        var it = repository.findAll();
        var values = new ArrayList<CredenciadosModel>();
        it.forEach(e -> values.add(e));
        return values;
    }

    @GetMapping(path = "/api/credenciados/{id}")
    public ResponseEntity consultar(@PathVariable("id") Integer id) {
        return repository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(path = "/api/credenciados")
    public Object salvar(@RequestPart("files") MultipartFile[] files, @RequestPart("data") CredenciadosModel credenciados) {
        String message = "";
        try {
            List<String> fileNames = new ArrayList<>();

            Arrays.asList(files).stream().forEach(file -> {

                var ext = FilenameUtils.getExtension(file.getOriginalFilename());
                var fname = UUID.randomUUID().toString();

                if (ext != "")//if ext is there concat
                    fname += "." + ext;

                try {
                    String filename = file.getOriginalFilename(); // Give a random filename here.
                    byte[] bytes = file.getBytes();
                    //String insPathN = "/opt/wildfly/standalone/deployments/uploads/credenciados/" + fname;
// Descomentar para produção
//String insPathN = "../standalone/deployments/uploads/credenciados/" + fname;
                    //Comentar para produção
                    String insPathN = "uploads/credenciados/" + fname;
                    Files.write(Paths.get(insPathN), bytes);
                    fileNames.add(fname);
                } catch (IOException e) {
                    System.out.println(e);
                }
            });

            try {
                credenciados.setContrato_social(files[2].getOriginalFilename());
                credenciados.setFotos(this.convertToJson(fileNames));
                credenciados.setStatus("pendente");
                repository.save(credenciados);
            } catch (Exception ex) {
                ApiError apiError = new ApiError(HttpStatus.EXPECTATION_FAILED, "Algo deu errado!", ex, ex.getCause() != null ? ex.getCause().getCause().getMessage() : "Erro");
                return apiError;
            }

            message = "Uploaded the files successfully: " + fileNames;
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message, fileNames));
        } catch (Exception e) {
            message = "Falha ao fazer o upload do arquivo!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message, null));
        }


    }

    public String convertToJson(List<String> fileNames) {
        return new Gson().toJson(fileNames);
    }

    @PutMapping(path = "/api/credenciados/{id}")
    public ResponseEntity atualizar(@PathVariable("id") Integer id, @RequestBody CredenciadosModel credenciados) {
        return repository.findById(id)
                .map(record -> {
                    record.setContrato_social(credenciados.getContrato_social());
                    record.setFotos(credenciados.getFotos());
                    record.setCnpj(credenciados.getCnpj());
                    record.setNm_fantasia(credenciados.getNm_fantasia());
                    record.setNm_gerente_loja(credenciados.getNm_gerente_loja());
                    record.setEmail_loja(credenciados.getEmail_loja());
                    record.setTelefone_loja(credenciados.getTelefone_loja());
                    record.setSite(credenciados.getSite());
                    record.setLogradouro(credenciados.getLogradouro());
                    record.setNumero(credenciados.getNumero());
                    record.setComplemento(credenciados.getComplemento());
                    record.setBairro(credenciados.getBairro());
                    record.setCidade(credenciados.getCidade());
                    record.setUf(credenciados.getUf());
                    record.setCep(credenciados.getCep());
                    record.setLat(credenciados.getLat());
                    record.setLog(credenciados.getLog());
//                    record.setStatus(credenciados.getStatus());
                    CredenciadosModel updated = repository.save(record);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(path = "/api/credenciados/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        return repository.findById(id)
                .map(record -> {
                    repository.deleteById(id);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }

}
