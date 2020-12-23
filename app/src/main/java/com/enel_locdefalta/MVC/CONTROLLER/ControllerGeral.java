package com.enel_locdefalta.MVC.CONTROLLER;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.enel_locdefalta.MVC.MODEL.Login.UserColaboradorModel;
import com.enel_locdefalta.MVC.MODEL.VerificaBR0Model;
import com.enel_locdefalta.MVC.SERVIDOR_e_BD.CRUD.DataBaseGeral;
import com.enel_locdefalta.MVC.SERVIDOR_e_BD.CRUD.DataBaseUtilUserColaborador;

public class ControllerGeral {
    DataBaseGeral dataBaseGeral;

   // TableVerificaBR0



    /***
     * CONSTRUTOR
     * @param context
     */
    public ControllerGeral(Context context){

        dataBaseGeral =  new DataBaseGeral(context);

    }


    public void SalvaVerfificacaoColaborador(VerificaBR0Model verificaBR0Model){



        ContentValues contentValues =  new ContentValues();
        /*MONTANDO OS PARAMETROS PARA SEREM SALVOS*/
        contentValues.put("id_colaborador", 1);
        contentValues.put("ds_token",       verificaBR0Model.getMensagem());
        /*EXECUTANDO INSERT DE UM NOVO REGISTRO*/
        dataBaseGeral.GetConexaoDataBase().insert("TableVerificaBR0",null,contentValues);

    }



    public VerificaBR0Model CarregaDadosUpDate(){


        Cursor cursor =  dataBaseGeral.GetConexaoDataBase().rawQuery("SELECT * FROM TableVerificaBR0",null);

        cursor.moveToFirst();

        ///CRIANDO UMA NOVA PESSOAS
        VerificaBR0Model verificaBR0Model =  new VerificaBR0Model();

        //ADICIONANDO OS DADOS DA PESSOA
        verificaBR0Model.setMensagem(cursor.getString(cursor.getColumnIndex("ds_token")));

        return verificaBR0Model;

    }



}
