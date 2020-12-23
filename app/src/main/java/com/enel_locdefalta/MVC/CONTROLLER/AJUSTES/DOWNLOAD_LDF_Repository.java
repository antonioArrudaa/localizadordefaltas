package com.enel_locdefalta.MVC.CONTROLLER.AJUSTES;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;


import com.enel_locdefalta.MVC.MODEL.AJUSTES.ConsultaLdFObjModel;
import com.enel_locdefalta.MVC.SERVIDOR_e_BD.CRUD.DatabaseUtilLDF;

import java.util.ArrayList;
import java.util.List;


public class DOWNLOAD_LDF_Repository {
    DatabaseUtilLDF databaseUtilLDF;

    /***
     * CONSTRUTOR
     * @param context
     */
    public DOWNLOAD_LDF_Repository(Context context){

        databaseUtilLDF =  new DatabaseUtilLDF(context);

    }

    /***
     * SALVA UM NOVO REGISTRO NA BASE DE DADOS
     * @param ldfModel
     */


    public void Salvar(ConsultaLdFObjModel ldfModel){

        ContentValues contentValues =  new ContentValues();
        /*MONTANDO OS PARAMETROS PARA SEREM SALVOS*/
        contentValues.put("ds_cidade",       ldfModel.getCidade());
        contentValues.put("ds_alimentador",   ldfModel.getAlimentador());
        contentValues.put("fl_religador",       ldfModel.getReligador());
        contentValues.put("dt_tipoDeCurto", ldfModel.getTipoDeCurto());
        contentValues.put("fl_tombamento",ldfModel.getTombamento());
        contentValues.put("fl_valorDoCurto",ldfModel.getValorDoCurto());
        contentValues.put("fl_LATITUDE",ldfModel.getLATITUDE());
        contentValues.put("fl_LONGITUDE",ldfModel.getLONGITUDE());
        contentValues.put("fl_DISTANCIA",ldfModel.getDISTANCIA());
        contentValues.put("fl_ativo",      ldfModel.getRegistroAtivo());

        /*EXECUTANDO INSERT DE UM NOVO REGISTRO*/
        databaseUtilLDF.GetConexaoDataBase().insert("testeldf",null,contentValues);

    }


    /***
     * ATUALIZA UM REGISTRO JÁ EXISTENTE NA BASE
     * @param ldfModel
     */
    public void Atualizar(ConsultaLdFObjModel ldfModel){
        ContentValues contentValues =  new ContentValues();

        /*MONTA OS PARAMENTROS PARA REALIZAR UPDATE NOS CAMPOS*/
        contentValues.put("ds_cidade",       ldfModel.getCidade());
        contentValues.put("ds_alimentador",   ldfModel.getAlimentador());
        contentValues.put("fl_religador",       ldfModel.getReligador());
        contentValues.put("dt_tipoDeCurto", ldfModel.getTipoDeCurto());
        contentValues.put("fl_tombamento",ldfModel.getTombamento());
        contentValues.put("fl_valorDoCurto",ldfModel.getValorDoCurto());
        contentValues.put("fl_LATITUDE",ldfModel.getLATITUDE());
        contentValues.put("fl_LONGITUDE",ldfModel.getLONGITUDE());
        contentValues.put("fl_DISTANCIA",ldfModel.getDISTANCIA());
        contentValues.put("fl_ativo",      ldfModel.getRegistroAtivo());


        /*REALIZANDO UPDATE PELA CHAVE DA TABELA*/
        databaseUtilLDF.GetConexaoDataBase().update("testeldf", contentValues, "id_ldf = ?", new String[]{Integer.toString(ldfModel.getCodigo())});
    }

    /***
     * EXCLUI UM REGISTRO PELO CÓDIGO
     * @param codigo
     * @return
     */
    public Integer Excluir(int codigo){

        //EXCLUINDO  REGISTRO E RETORNANDO O NÚMERO DE LINHAS AFETADAS
        return databaseUtilLDF.GetConexaoDataBase().delete("testeldf","id_ldf = ?", new String[]{Integer.toString(codigo)});
    }

    /***
     * EXCLUI UM REGISTRO PELO CÓDIGO
     * @param
     * @return
     */
    public Integer ExcluirBD_COMPLETO_LDF(){

        //EXCLUINDO  REGISTRO E RETORNANDO O NÚMERO DE LINHAS AFETADAS
        return databaseUtilLDF.GetConexaoDataBase().delete("testeldf",null,null);
    }


    /***
     * EXCLUI UM REGISTRO PELO CÓDIGO
     * @param codigo
     * @return
     */
    public Integer ExcluirLDF(int codigo){

        //EXCLUINDO  REGISTRO E RETORNANDO O NÚMERO DE LINHAS AFETADAS
        return databaseUtilLDF.GetConexaoDataBase().delete("testeldf","id_ldf = ?", new String[]{Integer.toString(codigo)});
    }
    /***
     * CONSULTA UMA PESSOA CADASTRADA PELO CÓDIGO
     * @param codigo
     * @return
     */
    public ConsultaLdFObjModel CarregaDadosUpDate(int codigo){


        Cursor cursor =  databaseUtilLDF.GetConexaoDataBase().rawQuery("SELECT * FROM testeldf WHERE id_ldf= "+ codigo,null);

        cursor.moveToFirst();

        ///CRIANDO UMA NOVO
        ConsultaLdFObjModel ldfModel =  new ConsultaLdFObjModel();

        //ADICIONANDO OS DADOS DA PESSOA
        ldfModel.setCodigo(cursor.getInt(cursor.getColumnIndex("id_ldf")));
        ldfModel.setCidade(cursor.getString(cursor.getColumnIndex("ds_cidade")));
        ldfModel.setAlimentador(cursor.getString(cursor.getColumnIndex("ds_alimentador")));
        ldfModel.setReligador(cursor.getString(cursor.getColumnIndex("fl_religador")));
        ldfModel.setTipoDeCurto(cursor.getString(cursor.getColumnIndex("dt_tipoDeCurto")));
        ldfModel.setTombamento(cursor.getString(cursor.getColumnIndex("fl_tombamento")));
        ldfModel.setValorDoCurto(cursor.getString(cursor.getColumnIndex("fl_valorDoCurto")));
        ldfModel.setLATITUDE(cursor.getString(cursor.getColumnIndex("fl_LATITUDE")));
        ldfModel.setLONGITUDE(cursor.getString(cursor.getColumnIndex("fl_LONGITUDE")));
        ldfModel.setDISTANCIA(cursor.getString(cursor.getColumnIndex("fl_DISTANCIA")));
        ldfModel.setRegistroAtivo((byte)cursor.getShort(cursor.getColumnIndex("fl_ativo")));

        //RETORNANDO Os dados do LDF

        Log.println(Log.INFO,"BD:",ldfModel.getAlimentador());
        return ldfModel;

    }

    /***
     * CONSULTA TODAS AS NOVO CADASTRADAS NA BASE
     * @return
     */
    public List<ConsultaLdFObjModel> SelecionarTodos(){
        List<ConsultaLdFObjModel> ldf = new ArrayList<ConsultaLdFObjModel>();


        //MONTA A QUERY A SER EXECUTADA
        StringBuilder stringBuilderQuery = new StringBuilder();
        stringBuilderQuery.append(" SELECT id_ldf,      ");
        stringBuilderQuery.append("        ds_cidade,        ");
        stringBuilderQuery.append("        ds_alimentador,    ");
        stringBuilderQuery.append("        fl_religador,        ");
        stringBuilderQuery.append("        dt_tipoDeCurto,  ");
        stringBuilderQuery.append("        fl_tombamento, ");
        stringBuilderQuery.append("        fl_valorDoCurto, ");
        stringBuilderQuery.append("        fl_LATITUDE, ");
        stringBuilderQuery.append("        fl_LONGITUDE, ");
        stringBuilderQuery.append("        fl_DISTANCIA, ");
        stringBuilderQuery.append("        fl_ativo        ");
        stringBuilderQuery.append("  FROM  testeldf       ");
        stringBuilderQuery.append(" ORDER BY id_ldf       ");


        //CONSULTANDO OS REGISTROS CADASTRADOS
        Cursor cursor = databaseUtilLDF.GetConexaoDataBase().rawQuery(stringBuilderQuery.toString(), null);

        /*POSICIONA O CURSOR NO PRIMEIRO REGISTRO*/
        cursor.moveToFirst();


        ConsultaLdFObjModel ldfModel;

        //REALIZA A LEITURA DOS REGISTROS ENQUANTO NÃO FOR O FIM DO CURSOR
        while (!cursor.isAfterLast()){

            /* CRIANDO UMA NOVO */
            ldfModel =  new ConsultaLdFObjModel();

            //ADICIONANDO OS DADOS DA PESSOA
            ldfModel.setCodigo(cursor.getInt(cursor.getColumnIndex("id_ldf")));
            ldfModel.setCidade(cursor.getString(cursor.getColumnIndex("ds_cidade")));
            ldfModel.setAlimentador(cursor.getString(cursor.getColumnIndex("ds_alimentador")));
            ldfModel.setReligador(cursor.getString(cursor.getColumnIndex("fl_religador")));
            ldfModel.setTipoDeCurto(cursor.getString(cursor.getColumnIndex("dt_tipoDeCurto")));
            ldfModel.setTombamento(cursor.getString(cursor.getColumnIndex("fl_tombamento")));
            ldfModel.setValorDoCurto(cursor.getString(cursor.getColumnIndex("fl_valorDoCurto")));
            ldfModel.setLATITUDE(cursor.getString(cursor.getColumnIndex("fl_LATITUDE")));
            ldfModel.setLONGITUDE(cursor.getString(cursor.getColumnIndex("fl_LONGITUDE")));
            ldfModel.setDISTANCIA(cursor.getString(cursor.getColumnIndex("fl_DISTANCIA")));
            ldfModel.setRegistroAtivo((byte)cursor.getShort(cursor.getColumnIndex("fl_ativo")));

            //ADICIONANDO UM
            ldf.add(ldfModel);

            //VAI PARA O PRÓXIMO REGISTRO
            cursor.moveToNext();
        }

        //RETORNANDO A LISTA
        return ldf;

    }


    /***
     * CONSULTA UM CADASTRADO PELO CÓDIGO
     * @param intervaloFinal
     * @param intervaloFinal
     * @return
     */

    public List<ConsultaLdFObjModel> GetCURTOQ(int intervaloInicial, int intervaloFinal, String alimentador, String religador, String tipoDeCurto){

        List<ConsultaLdFObjModel> ldf = new ArrayList<ConsultaLdFObjModel>();

        Cursor cursor =  databaseUtilLDF.GetConexaoDataBase().rawQuery("SELECT * FROM testeldf WHERE (fl_valorDoCurto BETWEEN '"+intervaloInicial+"' AND '"+intervaloFinal+"') AND ds_alimentador='"+alimentador+"' AND fl_religador='"+religador+"' AND dt_tipoDeCurto='"+tipoDeCurto+"'",null);

        /*POSICIONA O CURSOR NO PRIMEIRO REGISTRO*/
        cursor.moveToFirst();


        ConsultaLdFObjModel ldfModel;

        //REALIZA A LEITURA DOS REGISTROS ENQUANTO NÃO FOR O FIM DO CURSOR
        while (!cursor.isAfterLast()){

            /* CRIANDO UM*/
            ldfModel =  new ConsultaLdFObjModel();

            //ADICIONANDO OS DADOS
            ldfModel.setCodigo(cursor.getInt(cursor.getColumnIndex("id_ldf")));
            ldfModel.setCidade(cursor.getString(cursor.getColumnIndex("ds_cidade")));
            ldfModel.setAlimentador(cursor.getString(cursor.getColumnIndex("ds_alimentador")));
            ldfModel.setReligador(cursor.getString(cursor.getColumnIndex("fl_religador")));
            ldfModel.setTipoDeCurto(cursor.getString(cursor.getColumnIndex("dt_tipoDeCurto")));
            ldfModel.setTombamento(cursor.getString(cursor.getColumnIndex("fl_tombamento")));
            ldfModel.setValorDoCurto(cursor.getString(cursor.getColumnIndex("fl_valorDoCurto")));
            ldfModel.setLATITUDE(cursor.getString(cursor.getColumnIndex("fl_LATITUDE")));
            ldfModel.setLONGITUDE(cursor.getString(cursor.getColumnIndex("fl_LONGITUDE")));
            ldfModel.setDISTANCIA(cursor.getString(cursor.getColumnIndex("fl_DISTANCIA")));
            ldfModel.setRegistroAtivo((byte)cursor.getShort(cursor.getColumnIndex("fl_ativo")));

            //ADICIONANDO UM
            ldf.add(ldfModel);

            //VAI PARA O PRÓXIMO REGISTRO
            cursor.moveToNext();
        }

        //RETORNANDO A LISTA
        return ldf;
    }



}
