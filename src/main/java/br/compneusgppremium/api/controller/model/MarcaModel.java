package br.compneusgppremium.api.controller.model;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "marca")
@Data
public class MarcaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    @Column
    public String descricao;
}
