package br.compneusgppremium.api.controller.dto;

import br.compneusgppremium.api.controller.model.CarcacaModel;
import br.compneusgppremium.api.controller.model.ProducaoModel;
import br.compneusgppremium.api.controller.model.RegraModel;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class ProducaoDTO {
    private Integer id;
    private CarcacaModel carcaca;
    private Double medida_pneu_raspado;
    private String dados;
    private RegraModel regra;
    private String fotos;
    private Date dt_create;
    private Date dt_update;
    private UUID uuid;
    private UsuarioResumoDTO criadoPor;

    public ProducaoDTO(Integer id, CarcacaModel carcaca, Double medida_pneu_raspado,
                       RegraModel regra, String fotos, Date dt_create,
                       String nome, String login) {
        this.id = id;
        this.carcaca = carcaca;
        this.medida_pneu_raspado = medida_pneu_raspado;
        this.regra = regra;
        this.fotos = fotos;
        this.dt_create = dt_create;
        this.criadoPor = new UsuarioResumoDTO(nome, login);
    }

    // ✅ Factory method (certifique-se de que é `public static`)
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
