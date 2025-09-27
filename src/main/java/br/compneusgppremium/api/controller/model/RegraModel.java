package br.compneusgppremium.api.controller.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity(name = "regra")
@Schema(description = "Modelo representando regras de produção de pneus")
public class RegraModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único da regra", example = "1")
    private Integer id;
    
    @Column
    @Schema(description = "Tamanho mínimo permitido", example = "15.5")
    private Double tamanho_min;
    
    @Column
    @Schema(description = "Tamanho máximo permitido", example = "22.5")
    private Double tamanho_max;
    
    @Column
    @Schema(description = "Tempo de processamento", example = "120")
    private String tempo;
    
    @ManyToOne
    @Schema(description = "Matriz associada à regra")
    private MatrizModel matriz;
    
    @ManyToOne
    @Schema(description = "Medida do pneu")
    private MedidaModel medida;
    
    @ManyToOne
    @Schema(description = "Modelo do pneu")
    private ModeloModel modelo;
    
    @ManyToOne
    @Schema(description = "País de origem")
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
    @Column
    private Date dt_create;
    @Column
    private Date dt_update;
    @Column
    private Date dt_delete;
}
