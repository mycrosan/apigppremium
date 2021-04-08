package br.compneusgppremium.api.controller.model;

import br.compneusgppremium.api.util.JpaConverterJson;
import lombok.Data;
import org.springframework.boot.jackson.JsonObjectSerializer;

import javax.persistence.*;

@Entity(name = "pneu")
@Data
public class PneuModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    @Column
    public String numero_etiqueta;
    @Column
    public String dot;
    @Column
    @Convert(converter = JpaConverterJson.class)
    public String dados;

    @ManyToOne
    @JoinColumn(name="modelo_id")
    private ModeloModel modelo;

    @ManyToOne
    public MedidaModel medida;

    @ManyToOne(fetch = FetchType.EAGER)
    public BorrachaModel borracha;

    @ManyToOne
    public PaisModel pais;

    @Column
    @Convert(converter = JpaConverterJson.class)
    public String fotos;
}
