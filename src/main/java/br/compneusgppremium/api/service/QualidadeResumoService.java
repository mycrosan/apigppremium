package br.compneusgppremium.api.service;

import br.compneusgppremium.api.repository.QualidadeResumoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class QualidadeResumoService {

    private final QualidadeResumoRepository repository;

    public QualidadeResumoService(QualidadeResumoRepository repository) {
        this.repository = repository;
    }

    public Map<String, Long> getResumo() {
        final LocalDate hoje = LocalDate.now();
        final LocalDate ontem = hoje.minusDays(1);
        final LocalDate anteontem = hoje.minusDays(2);

        final LocalDate primeiroDiaMesAtual = hoje.withDayOfMonth(1);
        final LocalDate primeiroDiaMesPassado = primeiroDiaMesAtual.minusMonths(1);
        final LocalDate primeiroDiaMesRetrasado = primeiroDiaMesAtual.minusMonths(2);

        Map<String, Long> resumo = new HashMap<>();

        resumo.put("hoje", repository.countByData(toDate(hoje), toDate(hoje.plusDays(1))));
        resumo.put("ontem", repository.countByData(toDate(ontem), toDate(hoje)));
        resumo.put("anteontem", repository.countByData(toDate(anteontem), toDate(ontem)));

        resumo.put("mesAtual", repository.countByMes(toDate(primeiroDiaMesAtual), toDate(primeiroDiaMesAtual.plusMonths(1))));
        resumo.put("mesPassado", repository.countByMes(toDate(primeiroDiaMesPassado), toDate(primeiroDiaMesAtual)));
        resumo.put("mesRetrasado", repository.countByMes(toDate(primeiroDiaMesRetrasado), toDate(primeiroDiaMesPassado)));

        return resumo;
    }

    private Date toDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
