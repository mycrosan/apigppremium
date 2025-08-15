package br.compneusgppremium.api.controller.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "cola")
@Data
public class ColaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "producao_id", nullable = false)
    private ProducaoModel producao;

    @Column(name = "data_inicio", nullable = false, insertable = false, updatable = true)
    private LocalDateTime dataInicio;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusCola status = StatusCola.Aguardando;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private UsuarioModel usuario; // Novo campo para armazenar o usuário logado

    public enum StatusCola {
        Aguardando,
        Pronto,
        Vencido
    }
}
