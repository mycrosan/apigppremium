package br.compneusgppremium.api.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "modelo")
@Data
public class ModeloModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    public String descricao;
    @ManyToOne(fetch = FetchType.EAGER)
    public MarcaModel marca;

}
