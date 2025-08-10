package br.compneusgppremium.api.controller.dto;

import br.compneusgppremium.api.controller.model.ColaModel;
import br.compneusgppremium.api.controller.model.ProducaoModel;

public class ColaComStatusDTO {
    private ColaModel cola;
    private ProducaoModel producao;
    private boolean colaValida;
    private String mensagem;

    public ColaComStatusDTO(ColaModel cola, ProducaoModel producao, boolean colaValida, String mensagem) {
        this.cola = cola;
        this.producao = producao;
        this.colaValida = colaValida;
        this.mensagem = mensagem;
    }

    // getters e setters (ou use lombok @Data)

    public ColaModel getCola() { return cola; }
    public void setCola(ColaModel cola) { this.cola = cola; }
    public ProducaoModel getProducao() { return producao; }
    public void setProducao(ProducaoModel producao) { this.producao = producao; }
    public boolean isColaValida() { return colaValida; }
    public void setColaValida(boolean colaValida) { this.colaValida = colaValida; }
    public String getMensagem() { return mensagem; }
    public void setMensagem(String mensagem) { this.mensagem = mensagem; }
}
