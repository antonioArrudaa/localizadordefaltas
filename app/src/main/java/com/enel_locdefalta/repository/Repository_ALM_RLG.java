package com.enel_locdefalta.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;


import com.enel_locdefalta.MVC.MODEL.AJUSTES.ObjModel_ALM_RLG;
import com.enel_locdefalta.MVC.SERVIDOR_e_BD.CRUD.DataBaseUtilALMRLG;

import java.util.ArrayList;


public class Repository_ALM_RLG {
    DataBaseUtilALMRLG dataBaseUtilALMRLG;

    /***
     * CONSTRUTOR
     * @param context
     */
    public Repository_ALM_RLG(Context context){

        dataBaseUtilALMRLG =  new DataBaseUtilALMRLG(context);

    }

    public void Salvar_ALM_RLG(ObjModel_ALM_RLG ldfModel){

        ContentValues contentValues =  new ContentValues();
        /*MONTANDO OS PARAMETROS PARA SEREM SALVOS*/
        contentValues.put("alimentador",   ldfModel.getAlimentador());
        contentValues.put("religador",       ldfModel.getReligador());

        /*EXECUTANDO INSERT DE UM NOVO REGISTRO*/
        dataBaseUtilALMRLG.GetConexaoDataBase().insert("alm_rlg",null,contentValues);

    }


    //SALVA ALIMENTADOR
    public void Salvar_ALM(ObjModel_ALM_RLG ldfModel){

        ContentValues contentValues =  new ContentValues();
        /*MONTANDO OS PARAMETROS PARA SEREM SALVOS*/
        contentValues.put("alimentador",   ldfModel.getAlimentador());

        /*EXECUTANDO INSERT DE UM NOVO REGISTRO*/
        dataBaseUtilALMRLG.GetConexaoDataBase().insert("alm_rlg",null,contentValues);

    }

    //CARREGA DADOS DO ALIMENTADOR
    public ArrayList<ObjModel_ALM_RLG> SelecionarTodos_ALM(){
        ArrayList<ObjModel_ALM_RLG> alm_rlg = new ArrayList<ObjModel_ALM_RLG>();


        //MONTA A QUERY A SER EXECUTADA
        StringBuilder stringBuilderQuery = new StringBuilder();
        stringBuilderQuery.append(" SELECT id,      ");
        stringBuilderQuery.append("        alimentador,    ");
        stringBuilderQuery.append("  FROM  alm_rlg       ");
        stringBuilderQuery.append(" ORDER BY id       ");


        //CONSULTANDO OS REGISTROS CADASTRADOS
        Cursor cursor = dataBaseUtilALMRLG.GetConexaoDataBase().rawQuery(stringBuilderQuery.toString(), null);

        /*POSICIONA O CURSOR NO PRIMEIRO REGISTRO*/
        cursor.moveToFirst();


        ObjModel_ALM_RLG objModelALMRLG;

        //REALIZA A LEITURA DOS REGISTROS ENQUANTO NÃO FOR O FIM DO CURSOR
        while (!cursor.isAfterLast()){

            /* CRIANDO UMA NOVA PESSOAS */
            objModelALMRLG =  new ObjModel_ALM_RLG();

            //ADICIONANDO OS DADOS DA PESSOA
            objModelALMRLG.setCodigo(cursor.getInt(cursor.getColumnIndex("id")));
            objModelALMRLG.setAlimentador(cursor.getString(cursor.getColumnIndex("alimentador")));

            //ADICIONANDO UMA PESSOA NA LISTA
            alm_rlg.add(objModelALMRLG);

            //VAI PARA O PRÓXIMO REGISTRO
            cursor.moveToNext();
        }

        //RETORNANDO A LISTA DE PESSOAS
        return alm_rlg;

    }




    /***
     * ATUALIZA UM REGISTRO JÁ EXISTENTE NA BASE
     * @param ldfModel
     */
    public void Atualizar(ObjModel_ALM_RLG ldfModel){
        ContentValues contentValues =  new ContentValues();

        /*MONTA OS PARAMENTROS PARA REALIZAR UPDATE NOS CAMPOS*/
        contentValues.put("alimentador",   ldfModel.getAlimentador());
        contentValues.put("religador",       ldfModel.getReligador());


        /*REALIZANDO UPDATE PELA CHAVE DA TABELA*/
        dataBaseUtilALMRLG.GetConexaoDataBase().update("alm_rlg", contentValues, "id = ?", new String[]{Integer.toString(ldfModel.getCodigo())});
    }

    /***
     * EXCLUI UM REGISTRO PELO CÓDIGO
     * @param codigo
     * @return
     */
    public Integer Excluir(int codigo){

        //EXCLUINDO  REGISTRO E RETORNANDO O NÚMERO DE LINHAS AFETADAS
        return dataBaseUtilALMRLG.GetConexaoDataBase().delete("alm_rlg","id = ?", new String[]{Integer.toString(codigo)});
    }

    /***
     * CONSULTA UMA PESSOA CADASTRADA PELO CÓDIGO
     * @param codigo
     * @return
     */
    public ObjModel_ALM_RLG CarregaDadosUpDate(int codigo){


        Cursor cursor =  dataBaseUtilALMRLG.GetConexaoDataBase().rawQuery("SELECT * FROM alm_rlg WHERE id= "+ codigo,null);

        cursor.moveToFirst();

        ///CRIANDO UMA NOVA PESSOAS
        ObjModel_ALM_RLG ldfModel =  new ObjModel_ALM_RLG();

        //ADICIONANDO OS DADOS DA PESSOA
        ldfModel.setCodigo(cursor.getInt(cursor.getColumnIndex("id")));
        ldfModel.setAlimentador(cursor.getString(cursor.getColumnIndex("alimentador")));
        ldfModel.setReligador(cursor.getString(cursor.getColumnIndex("religador")));

        //RETORNANDO Os dados do LDF

        Log.println(Log.INFO,"BD:",ldfModel.getAlimentador());
        return ldfModel;

    }

    /***
     * CONSULTA TODAS AS PESSOAS CADASTRADAS NA BASE
     * @return
     */
    public ArrayList<ObjModel_ALM_RLG> SelecionarTodos_ALM_RLG(){
        ArrayList<ObjModel_ALM_RLG> alm_rlg = new ArrayList<ObjModel_ALM_RLG>();


        //MONTA A QUERY A SER EXECUTADA
        StringBuilder stringBuilderQuery = new StringBuilder();
        stringBuilderQuery.append(" SELECT id,      ");
        stringBuilderQuery.append("        alimentador,    ");
        stringBuilderQuery.append("        religador        ");
        stringBuilderQuery.append("  FROM  alm_rlg       ");
        stringBuilderQuery.append(" ORDER BY id       ");


        //CONSULTANDO OS REGISTROS CADASTRADOS
        Cursor cursor = dataBaseUtilALMRLG.GetConexaoDataBase().rawQuery(stringBuilderQuery.toString(), null);

        /*POSICIONA O CURSOR NO PRIMEIRO REGISTRO*/
        cursor.moveToFirst();


        ObjModel_ALM_RLG objModelALMRLG;

        //REALIZA A LEITURA DOS REGISTROS ENQUANTO NÃO FOR O FIM DO CURSOR
        while (!cursor.isAfterLast()){

            /* CRIANDO UMA NOVA PESSOAS */
            objModelALMRLG =  new ObjModel_ALM_RLG();

            //ADICIONANDO OS DADOS DA PESSOA
            objModelALMRLG.setCodigo(cursor.getInt(cursor.getColumnIndex("id")));
            objModelALMRLG.setAlimentador(cursor.getString(cursor.getColumnIndex("alimentador")));
            objModelALMRLG.setReligador(cursor.getString(cursor.getColumnIndex("religador")));

            //ADICIONANDO UMA PESSOA NA LISTA
            alm_rlg.add(objModelALMRLG);

            //VAI PARA O PRÓXIMO REGISTRO
            cursor.moveToNext();
        }

        //RETORNANDO A LISTA DE PESSOAS
        return alm_rlg;

    }


    public ArrayList<ObjModel_ALM_RLG> SelecionarTodosRLG(){
        ArrayList<ObjModel_ALM_RLG> rlg = new ArrayList<ObjModel_ALM_RLG>();


        //MONTA A QUERY A SER EXECUTADA
        StringBuilder stringBuilderQuery = new StringBuilder();
        stringBuilderQuery.append(" SELECT id,      ");
        stringBuilderQuery.append("        religador        ");
        stringBuilderQuery.append("  FROM  alm_rlg       ");
        stringBuilderQuery.append(" ORDER BY id       ");


        //CONSULTANDO OS REGISTROS CADASTRADOS
        Cursor cursor = dataBaseUtilALMRLG.GetConexaoDataBase().rawQuery(stringBuilderQuery.toString(), null);

        /*POSICIONA O CURSOR NO PRIMEIRO REGISTRO*/
        cursor.moveToFirst();


        ObjModel_ALM_RLG ldfModel;

        //REALIZA A LEITURA DOS REGISTROS ENQUANTO NÃO FOR O FIM DO CURSOR
        while (!cursor.isAfterLast()){

            /* CRIANDO UMA NOVA PESSOAS */
            ldfModel =  new ObjModel_ALM_RLG();

            //ADICIONANDO OS DADOS DA PESSOA
            ldfModel.setCodigo(cursor.getInt(cursor.getColumnIndex("id")));
            ldfModel.setReligador(cursor.getString(cursor.getColumnIndex("religador")));

            //ADICIONANDO UMA PESSOA NA LISTA
            rlg.add(ldfModel);

            //VAI PARA O PRÓXIMO REGISTRO
            cursor.moveToNext();
        }

        //RETORNANDO A LISTA DE PESSOAS
        return rlg;

    }

}
