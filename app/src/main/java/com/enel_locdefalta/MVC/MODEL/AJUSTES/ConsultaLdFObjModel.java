package com.enel_locdefalta.MVC.MODEL.AJUSTES;

import android.app.AlertDialog;
import android.content.Context;

import com.enel_locdefalta.R;

public class ConsultaLdFObjModel {
    private Integer codigo;
    private String  cidade;
    private String  alimentador;
    private String  religador;
    private String  tipoDeCurto;
    private String  tombamento;
    private String  valorDoCurto;
    private String  LATITUDE;
    private String  LONGITUDE;
    private String  DISTANCIA;
    private byte    registroAtivo;

    public int retornoCode;

    //private Bitmap foto;

    private String mensagemERROR;

    public String getMensagemERROR() {
        return mensagemERROR;
    }

    public void setMensagemERROR(String mensagemERROR) {
        this.mensagemERROR = mensagemERROR;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getAlimentador() {
        return alimentador;
    }

    public void setAlimentador(String alimentador) {
        this.alimentador = alimentador;
    }

    public String getReligador() {
        return religador;
    }

    public void setReligador(String religador) {
        this.religador = religador;
    }

    public String getTipoDeCurto() {
        return tipoDeCurto;
    }

    public void setTipoDeCurto(String tipoDeCurto) {
        this.tipoDeCurto = tipoDeCurto;
    }

    public String getTombamento() {
        return tombamento;
    }

    public void setTombamento(String tombamento) {
        this.tombamento = tombamento;
    }

    public String getValorDoCurto() {
        return valorDoCurto;
    }

    public void setValorDoCurto(String valorDoCurto) {
        this.valorDoCurto = valorDoCurto;
    }

    public String getLATITUDE() {
        return LATITUDE;
    }

    public void setLATITUDE(String LATITUDE) {
        this.LATITUDE = LATITUDE;
    }

    public String getLONGITUDE() {
        return LONGITUDE;
    }

    public void setLONGITUDE(String LONGITUDE) {
        this.LONGITUDE = LONGITUDE;
    }

    public String getDISTANCIA() {
        return DISTANCIA;
    }

    public void setDISTANCIA(String DISTANCIA) {
        this.DISTANCIA = DISTANCIA;
    }

    public byte getRegistroAtivo() {
        return registroAtivo;
    }

    public void setRegistroAtivo(byte registroAtivo) {
        this.registroAtivo = registroAtivo;
    }

    //public Bitmap getFoto() {
   //     return foto;
   // }

   // public void setFoto(Bitmap foto) {
     //   this.foto = foto;
   // }



}
