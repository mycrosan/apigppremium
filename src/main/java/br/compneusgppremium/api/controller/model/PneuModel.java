package br.compneusgppremium.api.controller.model;

import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.Data;

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
    public String dados;
    @ManyToOne
    @JoinColumn(name="modelo_id")
    private ModeloModel modelo;
    @Column
    public Integer medida_id;
    @Column
    public Integer borracha_id;
    @Column
    public Integer pais_id;
    @Column
    public String fotos;
}
