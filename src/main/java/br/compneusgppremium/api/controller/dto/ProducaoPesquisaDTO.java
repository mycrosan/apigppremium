package br.compneusgppremium.api.controller.dto;

import br.compneusgppremium.api.controller.model.ProducaoModel;

public class ProducaoPesquisaDTO {
    public static ProducaoDTO fromModel(ProducaoModel p) {
        return new ProducaoDTO(
                p.getId(),
                p.getCarcaca(),
                p.getMedida_pneu_raspado(),
                p.getRegra(),
                p.getFotos(),
                p.getDt_create(),
                p.getCriadoPor() != null ? p.getCriadoPor().getNome() : null,
                p.getCriadoPor() != null ? p.getCriadoPor().getLogin() : null
        );
    }
}
