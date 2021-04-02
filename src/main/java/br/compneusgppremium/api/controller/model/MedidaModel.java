package br.compneusgppremium.api.controller.model;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "medida")
@Data
public class MedidaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    @Column
    public String descricao;
}
