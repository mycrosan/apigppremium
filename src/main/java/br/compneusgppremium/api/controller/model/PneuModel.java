package br.compneusgppremium.api.controller.model;

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
    public String data;
    @Column
    public String model_id;
    @Column
    public String medidia_id;
    @Column
    public String borracha_id;
    @Column
    public String pais_id;
    @Column
    public String fotos;
}
