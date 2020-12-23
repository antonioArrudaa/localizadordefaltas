package com.enel_locdefalta.MVC.SERVIDOR_e_BD.CRUD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseUtilALMRLG extends SQLiteOpenHelper {

    //NOME DA BASE DE DADOS
    private static final String NOME_BASE_DE_DADOS   = "ENELTESTEALMRLG.db";

    //VERSÃO DO BANCO DE DADOS
    private static final int    VERSAO_BASE_DE_DADOS = 1;

    //CONSTRUTOR
    public DataBaseUtilALMRLG(Context context){

        super(context,NOME_BASE_DE_DADOS,null,VERSAO_BASE_DE_DADOS);

    }

    /*NA INICIALIZAÇÃO DA CLASSE VAMOS CRIAR A TABELA QUE VAMOS USAR*/
    @Override
    public void onCreate(SQLiteDatabase db) {

      //  StringBuilder stringBuilderCreateTable = new StringBuilder();

      //  stringBuilderCreateTable.append(" CREATE TABLE alm_rlg (");
       // stringBuilderCreateTable.append("        id      INTEGER PRIMARY KEY AUTOINCREMENT, ");
       // stringBuilderCreateTable.append("        alimentador          TEXT    ,            ");
        //stringBuilderCreateTable.append("        religador    TEXT    )            ");

        StringBuilder TABELA_ALM = new StringBuilder();

        TABELA_ALM.append(" CREATE TABLE TABLE_alm (");
        TABELA_ALM.append("        id      INTEGER PRIMARY KEY AUTOINCREMENT, ");
        TABELA_ALM.append("        alimentador          TEXT    )            ");

        StringBuilder TABELA_RLG = new StringBuilder();

        TABELA_RLG.append(" CREATE TABLE TABLE_rlg (");
        TABELA_RLG.append("        id      INTEGER PRIMARY KEY AUTOINCREMENT, ");
        TABELA_RLG.append("        religador    TEXT    )            ");


        db.execSQL(TABELA_ALM.toString());
        db.execSQL(TABELA_RLG.toString());
        //db.execSQL(stringBuilderCreateTable.toString());


    }


    /*SE TROCAR A VERSÃO DO BANCO DE DADOS VOCÊ PODE EXECUTAR ALGUMA ROTINA
      COMO CRIAR COLUNAS, EXCLUIR ENTRE OUTRAS */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS alm_rlg");
        onCreate(db);

    }

    /*MÉTODO QUE VAMOS USAR NA CLASSE QUE VAI EXECUTAR AS ROTINAS NO
    BANCO DE DADOS*/
    public SQLiteDatabase GetConexaoDataBase(){

        return this.getWritableDatabase();
    }
}
