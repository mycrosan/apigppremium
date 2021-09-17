package br.compneusgppremium.api.controller.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "regra")
public class RegraModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private Double tamanho_min;
    @Column
    private Double tamanho_max;
    @Column
    private String tempo;
    @ManyToOne
    private MatrizModel matriz;
    @ManyToOne
    private MedidaModel medida;
    @ManyToOne
    private ModeloModel modelo;
    @ManyToOne
    private PaisModel pais;
    @ManyToOne
    private CamelbackModel camelback;
    @ManyToOne
    private EspessuramentoModel espessuramento;
    @ManyToOne
    private AntiquebraModel antiquebra1;
    @ManyToOne
    private AntiquebraModel antiquebra2;
    @ManyToOne
    private AntiquebraModel antiquebra3;
}
