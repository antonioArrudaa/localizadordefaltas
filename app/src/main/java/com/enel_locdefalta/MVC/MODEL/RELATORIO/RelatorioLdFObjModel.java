package com.enel_locdefalta.MVC.MODEL.RELATORIO;

public class RelatorioLdFObjModel {
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

    private String DescricaoRelatorio;



    private byte[] foto1;
    private byte[] foto2;
    private byte[] foto3;
    private byte[] foto4;
    private byte[] foto5;

    private String BRColaborador;
    private String Horario;
    private String Data;

    private String MensagemError;


    private String statusRelatorio;


    public String getStatusRelatorio() {
        return statusRelatorio;
    }

    public void setStatusRelatorio(String statusRelatorio) {
        this.statusRelatorio = statusRelatorio;
    }

    public String getMensagemError() {
        return MensagemError;
    }

    public void setMensagemError(String mensagemError) {
        MensagemError = mensagemError;
    }

    public String getDescricaoRelatorio() {
        return DescricaoRelatorio;
    }

    public void setDescricaoRelatorio(String descricaoRelatorio) {
        DescricaoRelatorio = descricaoRelatorio;
    }

    public String getBRColaborador() {
        return BRColaborador;
    }

    public void setBRColaborador(String BRColaborador) {
        this.BRColaborador = BRColaborador;
    }

    public String getHorario() {
        return Horario;
    }

    public void setHorario(String horario) {
        Horario = horario;
    }

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }
    //private Bitmap foto;

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

    public byte[] getFoto1() {
        return foto1;
    }

    public void setFoto1(byte[] foto1) {
        this.foto1 = foto1;
    }

    public byte[] getFoto2() {
        return foto2;
    }

    public void setFoto2(byte[] foto2) {
        this.foto2 = foto2;
    }

    public byte[] getFoto3() {
        return foto3;
    }

    public void setFoto3(byte[] foto3) {
        this.foto3 = foto3;
    }

    public byte[] getFoto4() {
        return foto4;
    }

    public void setFoto4(byte[] foto4) {
        this.foto4 = foto4;
    }

    public byte[] getFoto5() {
        return foto5;
    }

    public void setFoto5(byte[] foto5) {
        this.foto5 = foto5;
    }


    //public Bitmap getFoto() {
   //     return foto;
   // }

   // public void setFoto(Bitmap foto) {
     //   this.foto = foto;
   // }
}
