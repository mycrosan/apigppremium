package br.compneusgppremium.api.controller.model;

import br.compneusgppremium.api.util.JpaConverterJson;
import lombok.Data;
import javax.persistence.*;

@Entity(name = "carcaca")
@Data
public class PneuModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String numero_etiqueta;
    @Column
    private String dot;
    @Column
    @Convert(converter = JpaConverterJson.class)
    private String dados;

    @ManyToOne
    private ModeloModel modelo;

    @ManyToOne
    public MedidaModel medida;

    @ManyToOne
    public PaisModel pais;

    @Column
    @Convert(converter = JpaConverterJson.class)
    public String fotos;
}
