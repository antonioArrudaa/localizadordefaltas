package com.enel_locdefalta.MVC.SERVIDOR_e_BD.CRUD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseAusteInicial extends SQLiteOpenHelper {
    //NOME DA BASE DE DADOS
    private static final String NOME_BASE_DE_DADOS   = "DadosIniciais.db";

    //VERSÃO DO BANCO DE DADOS
    private static final int    VERSAO_BASE_DE_DADOS = 1;

    //CONSTRUTOR
    public DataBaseAusteInicial(Context context){

        super(context,NOME_BASE_DE_DADOS,null,VERSAO_BASE_DE_DADOS);
    }

    /*NA INICIALIZAÇÃO DA CLASSE VAMOS CRIAR A TABELA QUE VAMOS USAR*/
    @Override
    public void onCreate(SQLiteDatabase db) {

        StringBuilder TableAjustesInicial = new StringBuilder();
        TableAjustesInicial.append(" CREATE TABLE TableAjustesInicial (");
        TableAjustesInicial.append("        id_ajuste      INTEGER NOT NULL, ");
        TableAjustesInicial.append("        ds_host      TEXT NOT NULL, ");
        TableAjustesInicial.append("        ds_status          INTEGER    NOT NULL)            ");

        db.execSQL(TableAjustesInicial.toString());
    }


    /*SE TROCAR A VERSÃO DO BANCO DE DADOS VOCÊ PODE EXECUTAR ALGUMA ROTINA
      COMO CRIAR COLUNAS, EXCLUIR ENTRE OUTRAS */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS TableAjustesInicial");
        onCreate(db);

    }

    /*MÉTODO QUE VAMOS USAR NA CLASSE QUE VAI EXECUTAR AS ROTINAS NO
    BANCO DE DADOS*/
    public SQLiteDatabase GetConexaoDataBase(){

        return this.getWritableDatabase();
    }
}