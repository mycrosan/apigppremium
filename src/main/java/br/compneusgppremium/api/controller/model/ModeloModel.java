package br.compneusgppremium.api.controller.model;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "modelo")
@Data
public class ModeloModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    @Column
    public String descricao;
}
