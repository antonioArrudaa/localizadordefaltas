package com.enel_locdefalta.TOKEN;

public class TokenAcessModel {
    String DadosToken;
    String id;
    String error;
    String codiResposta;


    public String getCodiResposta() {
        return codiResposta;
    }

    public void setCodiResposta(String codiResposta) {
        this.codiResposta = codiResposta;
    }

    public String getDadosToken() {
        return DadosToken;
    }

    public void setDadosToken(String dadosToken) {
        DadosToken = dadosToken;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
