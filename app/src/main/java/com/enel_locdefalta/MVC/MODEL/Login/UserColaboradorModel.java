package com.enel_locdefalta.MVC.MODEL.Login;

public class UserColaboradorModel {
    int codigo;
    String BR;
    String nome;
    String empresa;
    String regiao;
    String tiporegistro;
    String token;
    String mensagemStatus;


    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getRegiao() {
        return regiao;
    }

    public void setRegiao(String regiao) {
        this.regiao = regiao;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getTiporegistro() {
        return tiporegistro;
    }

    public void setTiporegistro(String tiporegistro) {
        this.tiporegistro = tiporegistro;
    }

    public String getBR() {
        return BR;
    }

    public void setBR(String BR) {
        this.BR = BR;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMensagemStatus() {
        return mensagemStatus;
    }

    public void setMensagemStatus(String mensagemStatus) {
        this.mensagemStatus = mensagemStatus;
    }
}
