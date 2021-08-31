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
    private String anti_quebra_1;
    @Column
    private String anti_quebra_2;
    @Column
    private String anti_quebra_3;
    @Column
    private String espessuramento;
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
}
