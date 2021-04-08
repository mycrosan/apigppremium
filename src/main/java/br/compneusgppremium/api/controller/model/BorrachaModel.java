package br.compneusgppremium.api.controller.model;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "borracha")
@Data
public class BorrachaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    @Column
    public String camelback;
    @Column
    public String anti_quebra;
    @Column
    public String espessuramento;
}
