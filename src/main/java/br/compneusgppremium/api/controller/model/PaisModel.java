package br.compneusgppremium.api.controller.model;
import lombok.Data;

import javax.persistence.*;
@Data
@Entity(name = "pais")
public class PaisModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String descricao;
}
