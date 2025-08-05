package br.compneusgppremium.api.controller.model;

import lombok.Data;
import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "cobertura")
@Data
public class CoberturaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String fotos;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dtCreate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "producao_id", nullable = false)
    private ProducaoModel producao;

}
