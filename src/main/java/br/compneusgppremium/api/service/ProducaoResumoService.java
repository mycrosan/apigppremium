package br.compneusgppremium.api.service;

import br.compneusgppremium.api.repository.ProducaoResumoRepository;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.*;

@Service
public class ProducaoResumoService {

    private final ProducaoResumoRepository producaoRepository;

    public ProducaoResumoService(ProducaoResumoRepository producaoRepository) {
        this.producaoRepository = producaoRepository;
    }

    public Map<String, Long> getResumo() {
        Map<String, Long> resumo = new HashMap<>();
        LocalDate hoje = LocalDate.now();
        LocalDateTime agora = LocalDateTime.now();

        resumo.put("hoje", count(hoje.atStartOfDay(), agora));
        resumo.put("ontem", count(hoje.minusDays(1).atStartOfDay(), hoje.minusDays(1).atTime(LocalTime.MAX)));
        resumo.put("anteontem", count(hoje.minusDays(2).atStartOfDay(), hoje.minusDays(2).atTime(LocalTime.MAX)));

        resumo.put("mesAtual", count(hoje.withDayOfMonth(1).atStartOfDay(), agora));
        LocalDate primeiroMesPassado = hoje.withDayOfMonth(1).minusMonths(1);
        LocalDate ultimoMesPassado = hoje.withDayOfMonth(1).minusDays(1);
        resumo.put("mesPassado", count(primeiroMesPassado.atStartOfDay(), ultimoMesPassado.atTime(LocalTime.MAX)));

        LocalDate primeiroMesRetrasado = primeiroMesPassado.minusMonths(1);
        LocalDate ultimoMesRetrasado = primeiroMesPassado.minusDays(1);
        resumo.put("mesRetrasado", count(primeiroMesRetrasado.atStartOfDay(), ultimoMesRetrasado.atTime(LocalTime.MAX)));

        return resumo;
    }

    private Long count(LocalDateTime start, LocalDateTime end) {
        Date inicio = Date.from(start.atZone(ZoneId.systemDefault()).toInstant());
        Date fim = Date.from(end.atZone(ZoneId.systemDefault()).toInstant());
        return producaoRepository.countByDateRange(inicio, fim);
    }
}
