package com.enel_locdefalta.MVC.SERVIDOR_e_BD.CRUD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseUtilLDF extends SQLiteOpenHelper {

    //NOME DA BASE DE DADOS
    private static final String NOME_BASE_DE_DADOS   = "ENELTESTE.db";

    //VERSÃO DO BANCO DE DADOS
    private static final int    VERSAO_BASE_DE_DADOS = 1;

    //CONSTRUTOR
    public DatabaseUtilLDF(Context context){

        super(context,NOME_BASE_DE_DADOS,null,VERSAO_BASE_DE_DADOS);
    }

    /*NA INICIALIZAÇÃO DA CLASSE VAMOS CRIAR A TABELA QUE VAMOS USAR*/
    @Override
    public void onCreate(SQLiteDatabase db) {

        StringBuilder stringBuilderCreateTable = new StringBuilder();

        stringBuilderCreateTable.append(" CREATE TABLE testeldf (");
        stringBuilderCreateTable.append("        id_ldf      INTEGER PRIMARY KEY AUTOINCREMENT, ");
        stringBuilderCreateTable.append("        ds_cidade          TEXT    NOT NULL,            ");
        stringBuilderCreateTable.append("        ds_alimentador    TEXT    NOT NULL,            ");
        stringBuilderCreateTable.append("        fl_religador        TEXT    NOT NULL,            ");
        stringBuilderCreateTable.append("        dt_tipoDeCurto  TEXT    NOT NULL,            ");
        stringBuilderCreateTable.append("        fl_tombamento TEXT    NOT NULL,            ");
        stringBuilderCreateTable.append("        fl_valorDoCurto INT    NOT NULL,            ");
        stringBuilderCreateTable.append("        fl_LATITUDE TEXT    NOT NULL,            ");
        stringBuilderCreateTable.append("        fl_LONGITUDE TEXT    NOT NULL,            ");
        stringBuilderCreateTable.append("        fl_DISTANCIA TEXT    NOT NULL,            ");
        stringBuilderCreateTable.append("        fl_ativo       INT     NOT NULL )           ");

        db.execSQL(stringBuilderCreateTable.toString());

    }

    /*SE TROCAR A VERSÃO DO BANCO DE DADOS VOCÊ PODE EXECUTAR ALGUMA ROTINA
      COMO CRIAR COLUNAS, EXCLUIR ENTRE OUTRAS */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS testeldf");
        onCreate(db);

    }

    /*MÉTODO QUE VAMOS USAR NA CLASSE QUE VAI EXECUTAR AS ROTINAS NO
    BANCO DE DADOS*/
    public SQLiteDatabase GetConexaoDataBase(){

        return this.getWritableDatabase();
    }
}