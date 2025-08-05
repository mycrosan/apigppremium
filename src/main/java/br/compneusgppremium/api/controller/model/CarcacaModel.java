package br.compneusgppremium.api.controller.model;

import br.compneusgppremium.api.util.JpaConverterJson;
import lombok.Data;

import javax.persistence.*;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Entity(name = "carcaca")
@Data
public class CarcacaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String numero_etiqueta;

    @Column
    private String dot;

    @Column
    private String status;

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

    @ManyToOne
    public StatusCarcacaModel status_carcaca;

    @Column
    private Date dt_create;

    @Column
    private Date dt_update;

    @Column
    private UUID uuid;
}
