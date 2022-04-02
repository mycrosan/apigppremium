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
    private String dot;
    @Column
    private String status;
    @Column
    @Convert(converter = JpaConverterJson.class)
    private String dados;
    @ManyToOne
    private ModeloModel modelo;
    @ManyToOne
    public MedidaModel medida;
    @ManyToOne
    public PaisModel pais;
    @Column
    @Convert(converter = JpaConverterJson.class)
    public String fotos;
}
