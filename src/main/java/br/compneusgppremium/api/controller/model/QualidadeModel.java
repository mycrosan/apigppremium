package br.compneusgppremium.api.controller.model;

import br.compneusgppremium.api.util.JpaConverterJson;
import lombok.Data;
import org.springframework.stereotype.Service;

import javax.persistence.*;

@Entity(name = "controle_qualidade")
@Data
@Service
public class QualidadeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private ProducaoModel producao;
    @Column
    private String observacao;
    @ManyToOne
    private ClassificacaoModel tipo_classificacao;
    @Column
    @Convert(converter = JpaConverterJson.class)
    public String fotos;
    @ManyToOne
    private ObservacaoModel tipo_observacao;
}
