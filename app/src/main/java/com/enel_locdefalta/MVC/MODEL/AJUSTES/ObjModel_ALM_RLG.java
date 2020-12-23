package com.enel_locdefalta.MVC.MODEL.AJUSTES;

public class ObjModel_ALM_RLG {
    private Integer codigo;
    private String  alimentador;
    private String  religador;
    private String  msgError;


    public String getMsgError() {
        return msgError;
    }

    public void setMsgError(String msgError) {
        this.msgError = msgError;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
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

}
