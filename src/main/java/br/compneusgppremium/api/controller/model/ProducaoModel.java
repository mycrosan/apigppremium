package br.compneusgppremium.api.controller.model;

import br.compneusgppremium.api.util.JpaConverterJson;
import lombok.Data;

import javax.persistence.*;

@Entity(name = "producao")
@Data
public class ProducaoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private CarcacaModel carcaca;
    @Column
    private Double medida_pneu_raspado;
    @Column
    @Convert(converter = JpaConverterJson.class)
    public String dados;
    @ManyToOne
    private RegraModel regra;
}
