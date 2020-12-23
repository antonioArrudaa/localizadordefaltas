package com.enel_locdefalta.MVC.CONTROLLER.RELATORIO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.enel_locdefalta.MVC.SERVIDOR_e_BD.CRUD.DatabaseUtilRelatorio;
import com.enel_locdefalta.MVC.MODEL.RELATORIO.RelatorioLdFObjModel;

import java.util.ArrayList;
import java.util.List;


public class RelatorioLDfRepository {
    DatabaseUtilRelatorio databaseUtil;

    /***
     * CONSTRUTOR
     * @param context
     */
    public RelatorioLDfRepository(Context context){

        databaseUtil =  new DatabaseUtilRelatorio(context);

    }

    /***
     * SALVA UM NOVO REGISTRO NA BASE DE DADOS
     * @param historicoLdFObjModel
     */


    public void SalvarRelatorio(RelatorioLdFObjModel historicoLdFObjModel){

        ContentValues contentValues =  new ContentValues();
        /*MONTANDO OS PARAMETROS PARA SEREM SALVOS*/
        contentValues.put("ds_cidade",       historicoLdFObjModel.getCidade());
        contentValues.put("ds_alimentador",   historicoLdFObjModel.getAlimentador());
        contentValues.put("fl_religador",       historicoLdFObjModel.getReligador());
        contentValues.put("dt_tipoDeCurto", historicoLdFObjModel.getTipoDeCurto());
        contentValues.put("fl_tombamento",historicoLdFObjModel.getTombamento());
        contentValues.put("fl_valorDoCurto",historicoLdFObjModel.getValorDoCurto());
        contentValues.put("fl_LATITUDE",historicoLdFObjModel.getLATITUDE());
        contentValues.put("fl_LONGITUDE",historicoLdFObjModel.getLONGITUDE());
        contentValues.put("fl_DISTANCIA",historicoLdFObjModel.getDISTANCIA());
        contentValues.put("fl_ativo",      historicoLdFObjModel.getRegistroAtivo());
        contentValues.put("ds_detalhes", historicoLdFObjModel.getDescricaoRelatorio());
        contentValues.put("foto1", historicoLdFObjModel.getFoto1());
        contentValues.put("foto2", historicoLdFObjModel.getFoto2());
        contentValues.put("foto3", historicoLdFObjModel.getFoto3());
        contentValues.put("foto4", historicoLdFObjModel.getFoto4());
        contentValues.put("foto5", historicoLdFObjModel.getFoto5());

        contentValues.put("ds_BrColaborador",      historicoLdFObjModel.getBRColaborador());
        contentValues.put("dt_data",      historicoLdFObjModel.getData());


        contentValues.put("ds_status_relatorio",       historicoLdFObjModel.getStatusRelatorio());

        contentValues.put("ho_horario",      historicoLdFObjModel.getHorario());



        /*EXECUTANDO INSERT DE UM NOVO REGISTRO*/
        databaseUtil.GetConexaoDataBase().insert("historicoLDF",null,contentValues);

    }

    public void AtualizaStatusRelatorio(RelatorioLdFObjModel historicoLdFObjModel){

        ContentValues contentValues =  new ContentValues();

        /*MONTANDO OS PARAMETROS PARA SEREM SALVOS*/
        //contentValues.put("id_ldf", historicoLdFObjModel.getCodigo());
        contentValues.put("ds_status_relatorio",       historicoLdFObjModel.getStatusRelatorio());


         String codigo = String.valueOf(historicoLdFObjModel.getCodigo());


        /*REALIZANDO UPDATE PELA CHAVE DA TABELA*/
        databaseUtil.GetConexaoDataBase().update("historicoLDF", contentValues, "id_ldf = "+codigo, new String[]{Integer.toString(Integer.parseInt(historicoLdFObjModel.getStatusRelatorio()))});

    }




    public void SalvarStatusRelatorio(RelatorioLdFObjModel historicoLdFObjModel){

        ContentValues contentValues =  new ContentValues();
        /*MONTANDO OS PARAMETROS PARA SEREM SALVOS*/
        contentValues.put("ds_status_relatorio",       historicoLdFObjModel.getStatusRelatorio());


        /*EXECUTANDO INSERT DE UM NOVO REGISTRO*/
        databaseUtil.GetConexaoDataBase().insert("statusRelatorio",null,contentValues);

    }

    public List<RelatorioLdFObjModel> SelecionarTodosStatus(){
        List<RelatorioLdFObjModel> status = new ArrayList<RelatorioLdFObjModel>();


        //MONTA A QUERY A SER EXECUTADA
        StringBuilder stringBuilderQuery = new StringBuilder();
        stringBuilderQuery.append(" SELECT id_status,      ");
        stringBuilderQuery.append("        ds_status_relatorio        ");
        stringBuilderQuery.append("  FROM  statusRelatorio       ");
        stringBuilderQuery.append(" ORDER BY id_status       ");


        //CONSULTANDO OS REGISTROS CADASTRADOS
        Cursor cursor = databaseUtil.GetConexaoDataBase().rawQuery(stringBuilderQuery.toString(), null);

        /*POSICIONA O CURSOR NO PRIMEIRO REGISTRO*/
        cursor.moveToFirst();


        RelatorioLdFObjModel historicoStatusLdFObjModel;

        //REALIZA A LEITURA DOS REGISTROS ENQUANTO NÃO FOR O FIM DO CURSOR
        while (!cursor.isAfterLast()){

            /* CRIANDO UMA NOVA PESSOAS */
            historicoStatusLdFObjModel =  new RelatorioLdFObjModel();

            //ADICIONANDO OS DADOS DA PESSOA
            historicoStatusLdFObjModel.setCodigo(cursor.getInt(cursor.getColumnIndex("id_ldf")));
            historicoStatusLdFObjModel.setCidade(cursor.getString(cursor.getColumnIndex("ds_status_relatorio")));

            //ADICIONANDO UMA PESSOA NA LISTA
            status.add(historicoStatusLdFObjModel);

            //VAI PARA O PRÓXIMO REGISTRO
            cursor.moveToNext();
        }

        //RETORNANDO A LISTA DE PESSOAS
        return status;

    }


    public void AtualizarStatus(RelatorioLdFObjModel historicoLdFObjModel){
        ContentValues contentValues =  new ContentValues();

        /*MONTA OS PARAMENTROS PARA REALIZAR UPDATE NOS CAMPOS*/
        contentValues.put("ds_cidade",       historicoLdFObjModel.getCidade());
        contentValues.put("ds_alimentador",   historicoLdFObjModel.getAlimentador());
        contentValues.put("fl_religador",       historicoLdFObjModel.getReligador());
        contentValues.put("dt_tipoDeCurto", historicoLdFObjModel.getTipoDeCurto());
        contentValues.put("fl_tombamento",historicoLdFObjModel.getTombamento());
        contentValues.put("fl_valorDoCurto",historicoLdFObjModel.getValorDoCurto());
        contentValues.put("fl_LATITUDE",historicoLdFObjModel.getLATITUDE());
        contentValues.put("fl_LONGITUDE",historicoLdFObjModel.getLONGITUDE());
        contentValues.put("fl_DISTANCIA",historicoLdFObjModel.getDISTANCIA());
        contentValues.put("fl_ativo",      historicoLdFObjModel.getRegistroAtivo());
        contentValues.put("ds_detalhes", historicoLdFObjModel.getDescricaoRelatorio());
        contentValues.put("foto1", historicoLdFObjModel.getFoto1());
        contentValues.put("foto2", historicoLdFObjModel.getFoto2());
        contentValues.put("foto3", historicoLdFObjModel.getFoto3());
        contentValues.put("foto4", historicoLdFObjModel.getFoto4());
        contentValues.put("foto5", historicoLdFObjModel.getFoto5());

        contentValues.put("ds_BrColaborador",      historicoLdFObjModel.getBRColaborador());
        contentValues.put("dt_data",      historicoLdFObjModel.getData());
        contentValues.put("ho_horario",      historicoLdFObjModel.getHorario());


        /*REALIZANDO UPDATE PELA CHAVE DA TABELA*/
        databaseUtil.GetConexaoDataBase().update("historicoLDF", contentValues, "id_ldf = ?", new String[]{Integer.toString(historicoLdFObjModel.getCodigo())});
    }





    /***
     * ATUALIZA UM REGISTRO JÁ EXISTENTE NA BASE
     * @param historicoLdFObjModel
     */
    public void Atualizar(RelatorioLdFObjModel historicoLdFObjModel){
        ContentValues contentValues =  new ContentValues();

        /*MONTA OS PARAMENTROS PARA REALIZAR UPDATE NOS CAMPOS*/
        contentValues.put("ds_cidade",       historicoLdFObjModel.getCidade());
        contentValues.put("ds_alimentador",   historicoLdFObjModel.getAlimentador());
        contentValues.put("fl_religador",       historicoLdFObjModel.getReligador());
        contentValues.put("dt_tipoDeCurto", historicoLdFObjModel.getTipoDeCurto());
        contentValues.put("fl_tombamento",historicoLdFObjModel.getTombamento());
        contentValues.put("fl_valorDoCurto",historicoLdFObjModel.getValorDoCurto());
        contentValues.put("fl_LATITUDE",historicoLdFObjModel.getLATITUDE());
        contentValues.put("fl_LONGITUDE",historicoLdFObjModel.getLONGITUDE());
        contentValues.put("fl_DISTANCIA",historicoLdFObjModel.getDISTANCIA());
        contentValues.put("fl_ativo",      historicoLdFObjModel.getRegistroAtivo());
        contentValues.put("ds_detalhes", historicoLdFObjModel.getDescricaoRelatorio());
        contentValues.put("foto1", historicoLdFObjModel.getFoto1());
        contentValues.put("foto2", historicoLdFObjModel.getFoto2());
        contentValues.put("foto3", historicoLdFObjModel.getFoto3());
        contentValues.put("foto4", historicoLdFObjModel.getFoto4());
        contentValues.put("foto5", historicoLdFObjModel.getFoto5());

        contentValues.put("ds_BrColaborador",      historicoLdFObjModel.getBRColaborador());
        contentValues.put("dt_data",      historicoLdFObjModel.getData());
        contentValues.put("ho_horario",      historicoLdFObjModel.getHorario());


        /*REALIZANDO UPDATE PELA CHAVE DA TABELA*/
        databaseUtil.GetConexaoDataBase().update("historicoLDF", contentValues, "id_ldf = ?", new String[]{Integer.toString(historicoLdFObjModel.getCodigo())});
    }

    /***
     * EXCLUI UM REGISTRO PELO CÓDIGO
     * @param codigo
     * @return
     */
    public Integer Excluir(int codigo){

        //EXCLUINDO  REGISTRO E RETORNANDO O NÚMERO DE LINHAS AFETADAS
        return databaseUtil.GetConexaoDataBase().delete("historicoLDF","id_ldf = ?", new String[]{Integer.toString(codigo)});
    }


    public Integer ExcluirBD_COMPLETO_RELATORIOS(){

        //EXCLUINDO  REGISTRO E RETORNANDO O NÚMERO DE LINHAS AFETADAS
        return databaseUtil.GetConexaoDataBase().delete("historicoLDF",null,null);
    }


    /***
     * CONSULTA UMA PESSOA CADASTRADA PELO CÓDIGO
     * @param codigo
     * @return
     */
    public RelatorioLdFObjModel CarregaDadosUpDate(int codigo){


        Cursor cursor =  databaseUtil.GetConexaoDataBase().rawQuery("SELECT * FROM historicoLDF WHERE id_ldf= "+ codigo,null);

        cursor.moveToFirst();

        ///CRIANDO UMA NOVA PESSOAS
        RelatorioLdFObjModel historicoLdFObjModel =  new RelatorioLdFObjModel();

        //ADICIONANDO OS DADOS DA PESSOA
        historicoLdFObjModel.setCodigo(cursor.getInt(cursor.getColumnIndex("id_ldf")));
        historicoLdFObjModel.setCidade(cursor.getString(cursor.getColumnIndex("ds_cidade")));
        historicoLdFObjModel.setAlimentador(cursor.getString(cursor.getColumnIndex("ds_alimentador")));
        historicoLdFObjModel.setReligador(cursor.getString(cursor.getColumnIndex("fl_religador")));
        historicoLdFObjModel.setTipoDeCurto(cursor.getString(cursor.getColumnIndex("dt_tipoDeCurto")));
        historicoLdFObjModel.setTombamento(cursor.getString(cursor.getColumnIndex("fl_tombamento")));
        historicoLdFObjModel.setValorDoCurto(cursor.getString(cursor.getColumnIndex("fl_valorDoCurto")));
        historicoLdFObjModel.setLATITUDE(cursor.getString(cursor.getColumnIndex("fl_LATITUDE")));
        historicoLdFObjModel.setLONGITUDE(cursor.getString(cursor.getColumnIndex("fl_LONGITUDE")));
        historicoLdFObjModel.setDISTANCIA(cursor.getString(cursor.getColumnIndex("fl_DISTANCIA")));
        historicoLdFObjModel.setRegistroAtivo((byte)cursor.getShort(cursor.getColumnIndex("fl_ativo")));
        historicoLdFObjModel.setDescricaoRelatorio(cursor.getString(cursor.getColumnIndex("ds_detalhes")));
        historicoLdFObjModel.setFoto1(cursor.getBlob(cursor.getColumnIndex("foto1")));
        historicoLdFObjModel.setFoto2(cursor.getBlob(cursor.getColumnIndex("foto2")));
        historicoLdFObjModel.setFoto3(cursor.getBlob(cursor.getColumnIndex("foto3")));
        historicoLdFObjModel.setFoto4(cursor.getBlob(cursor.getColumnIndex("foto4")));
        historicoLdFObjModel.setFoto5(cursor.getBlob(cursor.getColumnIndex("foto5")));
        historicoLdFObjModel.setBRColaborador(cursor.getString(cursor.getColumnIndex("ds_BrColaborador")));
        historicoLdFObjModel.setData(cursor.getString(cursor.getColumnIndex("dt_data")));
        historicoLdFObjModel.setHorario(cursor.getString(cursor.getColumnIndex("ho_horario")));


        //RETORNANDO Os dados do LDF

        Log.println(Log.INFO,"BD:",historicoLdFObjModel.getAlimentador());
        return historicoLdFObjModel;

    }

    /***
     * CONSULTA TODAS AS PESSOAS CADASTRADAS NA BASE
     * @return
     */
    public List<RelatorioLdFObjModel> SelecionarTodos(){
        List<RelatorioLdFObjModel> pessoas = new ArrayList<RelatorioLdFObjModel>();


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
        stringBuilderQuery.append("        fl_ativo,        ");
        stringBuilderQuery.append("        ds_detalhes,");
        stringBuilderQuery.append("        foto1,");
        stringBuilderQuery.append("        foto2,");
        stringBuilderQuery.append("        foto3,");
        stringBuilderQuery.append("        foto4,");
        stringBuilderQuery.append("        foto5,");
        stringBuilderQuery.append("        ds_BrColaborador,        ");
        stringBuilderQuery.append("        dt_data,        ");
        stringBuilderQuery.append("        ds_status_relatorio,        ");
        stringBuilderQuery.append("        ho_horario        ");
        stringBuilderQuery.append("  FROM  historicoLDF       ");
        stringBuilderQuery.append(" ORDER BY dt_data       ");


        //CONSULTANDO OS REGISTROS CADASTRADOS
        Cursor cursor = databaseUtil.GetConexaoDataBase().rawQuery(stringBuilderQuery.toString(), null);

        /*POSICIONA O CURSOR NO PRIMEIRO REGISTRO*/
        cursor.moveToFirst();


        RelatorioLdFObjModel historicoLdFObjModel;

        //REALIZA A LEITURA DOS REGISTROS ENQUANTO NÃO FOR O FIM DO CURSOR
        while (!cursor.isAfterLast()){

            /* CRIANDO UMA NOVA PESSOAS */
            historicoLdFObjModel =  new RelatorioLdFObjModel();

            //ADICIONANDO OS DADOS DA PESSOA
            historicoLdFObjModel.setCodigo(cursor.getInt(cursor.getColumnIndex("id_ldf")));
            historicoLdFObjModel.setCidade(cursor.getString(cursor.getColumnIndex("ds_cidade")));
            historicoLdFObjModel.setAlimentador(cursor.getString(cursor.getColumnIndex("ds_alimentador")));
            historicoLdFObjModel.setReligador(cursor.getString(cursor.getColumnIndex("fl_religador")));
            historicoLdFObjModel.setTipoDeCurto(cursor.getString(cursor.getColumnIndex("dt_tipoDeCurto")));
            historicoLdFObjModel.setTombamento(cursor.getString(cursor.getColumnIndex("fl_tombamento")));
            historicoLdFObjModel.setValorDoCurto(cursor.getString(cursor.getColumnIndex("fl_valorDoCurto")));
            historicoLdFObjModel.setLATITUDE(cursor.getString(cursor.getColumnIndex("fl_LATITUDE")));
            historicoLdFObjModel.setLONGITUDE(cursor.getString(cursor.getColumnIndex("fl_LONGITUDE")));
            historicoLdFObjModel.setDISTANCIA(cursor.getString(cursor.getColumnIndex("fl_DISTANCIA")));
            historicoLdFObjModel.setRegistroAtivo((byte)cursor.getShort(cursor.getColumnIndex("fl_ativo")));
            historicoLdFObjModel.setDescricaoRelatorio(cursor.getString(cursor.getColumnIndex("ds_detalhes")));
            historicoLdFObjModel.setFoto1(cursor.getBlob(cursor.getColumnIndex("foto1")));
            historicoLdFObjModel.setFoto2(cursor.getBlob(cursor.getColumnIndex("foto2")));
            historicoLdFObjModel.setFoto3(cursor.getBlob(cursor.getColumnIndex("foto3")));
            historicoLdFObjModel.setFoto4(cursor.getBlob(cursor.getColumnIndex("foto4")));
            historicoLdFObjModel.setFoto5(cursor.getBlob(cursor.getColumnIndex("foto5")));

            historicoLdFObjModel.setBRColaborador(cursor.getString(cursor.getColumnIndex("ds_BrColaborador")));
            historicoLdFObjModel.setData(cursor.getString(cursor.getColumnIndex("dt_data")));
            historicoLdFObjModel.setStatusRelatorio(cursor.getString(cursor.getColumnIndex("ds_status_relatorio")));
            historicoLdFObjModel.setHorario(cursor.getString(cursor.getColumnIndex("ho_horario")));

            //ADICIONANDO UMA PESSOA NA LISTA
            pessoas.add(historicoLdFObjModel);

            //VAI PARA O PRÓXIMO REGISTRO
            cursor.moveToNext();
        }

        //RETORNANDO A LISTA DE PESSOAS
        return pessoas;

    }
}
