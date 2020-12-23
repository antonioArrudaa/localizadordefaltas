package com.enel_locdefalta.MVC.CONTROLLER.AJUSTES;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.enel_locdefalta.MVC.MODEL.AJUSTES.AjusteInicialModel;
import com.enel_locdefalta.MVC.SERVIDOR_e_BD.CRUD.DataBaseAusteInicial;

import java.util.ArrayList;

public class AJUSTES_INICIAL_CONTROLLER {
    DataBaseAusteInicial dataBaseAusteInicial;

    /***
     * CONSTRUTOR
     * @param context
     */
    public AJUSTES_INICIAL_CONTROLLER(Context context) {

        dataBaseAusteInicial = new DataBaseAusteInicial(context);

    }

    /***
     * SALVA UM NOVO REGISTRO NA BASE DE DADOS
     * @param ajusteInicialModel
     */


    public void Salvar_AjustesIniciais(AjusteInicialModel ajusteInicialModel) {

        ContentValues contentValues = new ContentValues();
        /*MONTANDO OS PARAMETROS PARA SEREM SALVOS*/


        contentValues.put("id", ajusteInicialModel.getId());
        contentValues.put("host", ajusteInicialModel.getHost());
        contentValues.put("status", ajusteInicialModel.getStatus());

        /*EXECUTANDO INSERT DE UM NOVO REGISTRO*/
        dataBaseAusteInicial.GetConexaoDataBase().insert("TableAjustesInicial", null, contentValues);

    }


    /***
     * CONSULTA TODAS AS PESSOAS CADASTRADAS NA BASE
     * @return
     */
    public ArrayList<AjusteInicialModel> SelecionarTodos_ALM() {
        ArrayList<AjusteInicialModel> lista_alm = new ArrayList<AjusteInicialModel>();


        //MONTA A QUERY A SER EXECUTADA
        StringBuilder stringBuilderQuery = new StringBuilder();
        stringBuilderQuery.append(" SELECT id_ajuste,      ");
        stringBuilderQuery.append("        ds_host ,   ");
        stringBuilderQuery.append("        ds_status");
        stringBuilderQuery.append("  FROM  TableAjustesInicial       ");
        stringBuilderQuery.append(" ORDER BY id_ajuste       ");


        //CONSULTANDO OS REGISTROS CADASTRADOS
        Cursor cursor = dataBaseAusteInicial.GetConexaoDataBase().rawQuery(stringBuilderQuery.toString(), null);

        /*POSICIONA O CURSOR NO PRIMEIRO REGISTRO*/
        cursor.moveToFirst();


        AjusteInicialModel ajusteInicialModel;

        //REALIZA A LEITURA DOS REGISTROS ENQUANTO NÃO FOR O FIM DO CURSOR
        while (!cursor.isAfterLast()) {

            /* CRIANDO UMA NOVA PESSOAS */
            ajusteInicialModel = new AjusteInicialModel();

            //ADICIONANDO OS DADOS
            ajusteInicialModel.setId(cursor.getInt(cursor.getColumnIndex("id_ajuste")));
            ajusteInicialModel.setHost(cursor.getString(cursor.getColumnIndex("ds_host")));
            ajusteInicialModel.setStatus(cursor.getString(cursor.getColumnIndex("ds_status")));

            //ADICIONANDO UM DADO NA LISTA
            lista_alm.add(ajusteInicialModel);

            //VAI PARA O PRÓXIMO REGISTRO
            cursor.moveToNext();
        }

        //RETORNANDO A LISTA DE DADOS
        return lista_alm;

    }

    /***
     * EXCLUI UM REGISTRO PELO CÓDIGO
     * @param codigo
     * @return
     */
    public Integer ExcluirAjustes(int codigo) {

        //EXCLUINDO  REGISTRO E RETORNANDO O NÚMERO DE LINHAS AFETADAS
        return dataBaseAusteInicial.GetConexaoDataBase().delete("TableAjustesInicial", "id_ajuste = ?", new String[]{Integer.toString(codigo)});
    }

    /***
     * EXCLUI UM REGISTRO PELO CÓDIGO
     * @param
     * @return
     */
    public Integer ExcluirBD_COMPLETO_AJUSTES_INICIAIS() {

        //EXCLUINDO  REGISTRO E RETORNANDO O NÚMERO DE LINHAS AFETADAS
        return dataBaseAusteInicial.GetConexaoDataBase().delete("TableAjustesInicial", null, null);
    }


    /***
     * CONSULTA UMA PESSOA CADASTRADA PELO CÓDIGO
     * @param codigo
     * @return
     */
    public AjusteInicialModel CarregaDadosUNICO(int codigo) {


        Cursor cursor = dataBaseAusteInicial.GetConexaoDataBase().rawQuery("SELECT * FROM TableAjustesInicial WHERE id_ajuste= " + codigo, null);

        cursor.moveToFirst();

        ///CRIANDO UMA NOVA PESSOAS
        AjusteInicialModel ajusteInicialModel = new AjusteInicialModel();

        //ADICIONANDO OS DADOS DA PESSOA
        ajusteInicialModel.setId(cursor.getInt(cursor.getColumnIndex("id_ajuste")));
        ajusteInicialModel.setHost(cursor.getString(cursor.getColumnIndex("ds_host")));
        ajusteInicialModel.setStatus(cursor.getString(cursor.getColumnIndex("ds_status")));

        //RETORNANDO Os dados do LDF

        Log.println(Log.INFO, "BD:", ajusteInicialModel.getHost());
        return ajusteInicialModel;

    }
}