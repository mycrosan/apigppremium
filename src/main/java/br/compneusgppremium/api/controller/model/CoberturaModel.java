package br.compneusgppremium.api.controller.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "cobertura")
@Data
public class CoberturaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "json")
    private String fotos; // pode mapear como String, depois converter

    @Column(name = "dt_create", insertable = false, updatable = false)
    private LocalDateTime dtCreate;

    @Column(name = "dt_update")
    private LocalDateTime dtUpdate;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioModel usuario;

    @ManyToOne
    @JoinColumn(name = "cola_id", nullable = false)
    private ColaModel cola;
}
