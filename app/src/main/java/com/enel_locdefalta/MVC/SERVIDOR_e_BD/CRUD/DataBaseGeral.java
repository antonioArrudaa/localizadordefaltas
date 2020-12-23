package com.enel_locdefalta.MVC.SERVIDOR_e_BD.CRUD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseGeral extends SQLiteOpenHelper {
    //NOME DA BASE DE DADOS
    private static final String NOME_BASE_DE_DADOS   = "Dados.db";

    //VERSÃO DO BANCO DE DADOS
    private static final int    VERSAO_BASE_DE_DADOS = 1;

    //CONSTRUTOR
    public DataBaseGeral(Context context){

        super(context,NOME_BASE_DE_DADOS,null,VERSAO_BASE_DE_DADOS);
    }

    /*NA INICIALIZAÇÃO DA CLASSE VAMOS CRIAR A TABELA QUE VAMOS USAR*/
    @Override
    public void onCreate(SQLiteDatabase db) {

        StringBuilder TableVerficaBR0 = new StringBuilder();
        TableVerficaBR0.append(" CREATE TABLE TableVerificaBR0 (");
        TableVerficaBR0.append("        id_colaborador      INTEGER NOT NULL, ");
        TableVerficaBR0.append("        ds_token          TEXT    NOT NULL)            ");

        db.execSQL(TableVerficaBR0.toString());
    }


    /*SE TROCAR A VERSÃO DO BANCO DE DADOS VOCÊ PODE EXECUTAR ALGUMA ROTINA
      COMO CRIAR COLUNAS, EXCLUIR ENTRE OUTRAS */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS userColaborador");
        onCreate(db);

    }

    /*MÉTODO QUE VAMOS USAR NA CLASSE QUE VAI EXECUTAR AS ROTINAS NO
    BANCO DE DADOS*/
    public SQLiteDatabase GetConexaoDataBase(){

        return this.getWritableDatabase();
    }
}
