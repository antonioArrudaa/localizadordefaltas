package com.enel_locdefalta.MVC.SERVIDOR_e_BD.CRUD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseUtilUserColaborador extends SQLiteOpenHelper {

    //NOME DA BASE DE DADOS
    private static final String NOME_BASE_DE_DADOS   = "ENELTESTEUSERCOLABORADOR.db";

    //VERSÃO DO BANCO DE DADOS
    private static final int    VERSAO_BASE_DE_DADOS = 1;

    //CONSTRUTOR
    public DataBaseUtilUserColaborador(Context context){

        super(context,NOME_BASE_DE_DADOS,null,VERSAO_BASE_DE_DADOS);
    }

    /*NA INICIALIZAÇÃO DA CLASSE VAMOS CRIAR A TABELA QUE VAMOS USAR*/
    @Override
    public void onCreate(SQLiteDatabase db) {

        StringBuilder stringBuilderCreateTable = new StringBuilder();

        stringBuilderCreateTable.append(" CREATE TABLE userColaborador (");
        stringBuilderCreateTable.append("        id_colaborador      INTEGER NOT NULL, ");
        stringBuilderCreateTable.append("        ds_token          TEXT    NOT NULL,            ");
        stringBuilderCreateTable.append("        ds_nome    TEXT    NOT NULL,            ");
        stringBuilderCreateTable.append("        ds_br    TEXT    NOT NULL,            ");
        stringBuilderCreateTable.append("        ds_empresa    TEXT    NOT NULL,            ");
        stringBuilderCreateTable.append("        ds_regiao    TEXT    NOT NULL,            ");
        stringBuilderCreateTable.append("        ds_tiporegistro     TEXT    NOT NULL)           ");

        db.execSQL(stringBuilderCreateTable.toString());

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
