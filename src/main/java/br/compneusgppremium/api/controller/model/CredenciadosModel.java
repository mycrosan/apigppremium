package br.compneusgppremium.api.controller.model;

import br.compneusgppremium.api.util.JpaConverterJson;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "credenciados")
public class CredenciadosModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String contrato_social;
    @Column
    @Convert(converter = JpaConverterJson.class)
    private String fotos;
    @Column
    private String cnpj;
    @Column
    private String nm_fantasia;
    @Column
    private String nm_gerente_loja;
    @Column
    private String celular_gerente_loja;
    @Column
    private String email_loja;
    @Column
    private String telefone_loja;
    @Column
    private String site;
    @Column
    private String logradouro;
    @Column
    private String numero;
    @Column
    private String complemento;
    @Column
    private String bairro;
    @Column
    private String cidade;
    @Column
    private String uf;
    @Column
    private String cep;
    @Column
    private String lat;
    @Column
    private String log;
    @Column
    private String status;
    @Column
    @Convert(converter = JpaConverterJson.class)
    private String dados_adicionais;

}
