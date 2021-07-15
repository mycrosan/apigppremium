package br.compneusgppremium.api.controller.dto;

public class TokenDto {

    private String token;
    private String tipo;
    private Boolean status;

    public TokenDto(String token, String tipo, Boolean status) {
        this.token = token;
        this.tipo = tipo;
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public String getTipo() {
        return tipo;
    }

    public Boolean getStatus() {
        return status;
    }

}
