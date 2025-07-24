package br.compneusgppremium.api.controller;

import br.compneusgppremium.api.service.CarcacaResumoService;
import br.compneusgppremium.api.service.ProducaoResumoService;
import br.compneusgppremium.api.service.QualidadeResumoService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/resumo")
public class ResumoController {

    private final CarcacaResumoService carcacaResumoService;
    private final ProducaoResumoService producaoResumoService;
    private final QualidadeResumoService qualidadeResumoService;

    public ResumoController(
            CarcacaResumoService carcacaResumoService,
            ProducaoResumoService producaoResumoService,
            QualidadeResumoService qualidadeResumoService
    ) {
        this.carcacaResumoService = carcacaResumoService;
        this.producaoResumoService = producaoResumoService;
        this.qualidadeResumoService = qualidadeResumoService;
    }

    @GetMapping("/carcaca")
    public Map<String, Long> getResumoCarcaca() {
        return carcacaResumoService.getResumo();
    }

    @GetMapping("/producao")
    public Map<String, Long> getResumoProducao() {
        return producaoResumoService.getResumo();
    }

    @GetMapping("/qualidade")
    public Map<String, Long> getResumoQualidade() {
        return qualidadeResumoService.getResumo();
    }
}
