package br.compneusgppremium.api.controller.model;

import br.compneusgppremium.api.util.JpaConverterJson;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

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

    @Column
    @Convert(converter = JpaConverterJson.class)
    public String fotos;

    @Column
    private Date dt_create;

    @Column
    private Date dt_update;

    @Column
    private UUID uuid;
}
