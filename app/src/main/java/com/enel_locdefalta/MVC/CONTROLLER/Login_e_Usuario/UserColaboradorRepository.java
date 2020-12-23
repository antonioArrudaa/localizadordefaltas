package com.enel_locdefalta.MVC.CONTROLLER.Login_e_Usuario;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.enel_locdefalta.MVC.MODEL.AJUSTES.ConsultaLdFObjModel;
import com.enel_locdefalta.MVC.SERVIDOR_e_BD.CRUD.DataBaseUtilUserColaborador;
import com.enel_locdefalta.MVC.MODEL.Login.UserColaboradorModel;

import java.util.ArrayList;
import java.util.List;

public class UserColaboradorRepository {
    DataBaseUtilUserColaborador dataBaseUtilUserColaborador;

    /***
     * CONSTRUTOR
     * @param context
     */
    public UserColaboradorRepository(Context context){

        dataBaseUtilUserColaborador =  new DataBaseUtilUserColaborador(context);

    }

    /***
     * SALVA UM NOVO REGISTRO NA BASE DE DADOS
     * @param userColaboradorModel
     */


    public void SalvarUser(UserColaboradorModel userColaboradorModel){



        ContentValues contentValues =  new ContentValues();
        /*MONTANDO OS PARAMETROS PARA SEREM SALVOS*/
        contentValues.put("id_colaborador", 1);
        contentValues.put("ds_token",       userColaboradorModel.getToken());
        contentValues.put("ds_nome",   userColaboradorModel.getNome());
        contentValues.put("ds_br",       userColaboradorModel.getBR());
        contentValues.put("ds_empresa",       userColaboradorModel.getEmpresa());
        contentValues.put("ds_regiao",       userColaboradorModel.getRegiao());
        contentValues.put("ds_tiporegistro",       userColaboradorModel.getTiporegistro());

        /*EXECUTANDO INSERT DE UM NOVO REGISTRO*/
        dataBaseUtilUserColaborador.GetConexaoDataBase().insert("userColaborador",null,contentValues);

    }





    /***
     * ATUALIZA UM REGISTRO JÁ EXISTENTE NA BASE
     * @param userColaboradorModel
     */
    public void Atualizar(UserColaboradorModel userColaboradorModel){
        ContentValues contentValues =  new ContentValues();

        /*MONTA OS PARAMENTROS PARA REALIZAR UPDATE NOS CAMPOS*/
        contentValues.put("ds_token",       userColaboradorModel.getToken());
        contentValues.put("ds_nome",   userColaboradorModel.getNome());
        contentValues.put("ds_br",       userColaboradorModel.getBR());
        contentValues.put("ds_empresa",       userColaboradorModel.getEmpresa());
        contentValues.put("ds_regiao",       userColaboradorModel.getRegiao());
        contentValues.put("ds_tiporegistro",       userColaboradorModel.getTiporegistro());

        /*REALIZANDO UPDATE PELA CHAVE DA TABELA*/
        dataBaseUtilUserColaborador.GetConexaoDataBase().update("userColaborador", contentValues, "ds_token = ?", new String[]{Integer.toString(Integer.parseInt(userColaboradorModel.getToken()))});
    }

    /***
     * EXCLUI UM REGISTRO PELO CÓDIGO
     * @param codigo
     * @return
     */
    public Integer Excluir(int codigo){

        //EXCLUINDO  REGISTRO E RETORNANDO O NÚMERO DE LINHAS AFETADAS
        return dataBaseUtilUserColaborador.GetConexaoDataBase().delete("userColaborador","id_colaborador = ?", new String[]{Integer.toString(codigo)});
    }

    /***
     * CONSULTA UMA PESSOA CADASTRADA PELO CÓDIGO
     * @param br
     * @return
     */
    public UserColaboradorModel CarregaDadosUpDate(String br){


        Cursor cursor =  dataBaseUtilUserColaborador.GetConexaoDataBase().rawQuery("SELECT * FROM userColaborador WHERE ds_br ='"+br+"'",null);

        cursor.moveToFirst();

        ///CRIANDO UMA NOVA PESSOAS
        UserColaboradorModel userColaboradorModel =  new UserColaboradorModel();

        //ADICIONANDO OS DADOS DA PESSOA
        userColaboradorModel.setCodigo(cursor.getInt(cursor.getColumnIndex("id_colaborador")));
        userColaboradorModel.setToken(cursor.getString(cursor.getColumnIndex("ds_token")));
        userColaboradorModel.setNome(cursor.getString(cursor.getColumnIndex("ds_nome")));
        userColaboradorModel.setBR(cursor.getString(cursor.getColumnIndex("ds_br")));
        userColaboradorModel.setEmpresa(cursor.getString(cursor.getColumnIndex("ds_empresa")));
        userColaboradorModel.setRegiao(cursor.getString(cursor.getColumnIndex("ds_regiao")));
        userColaboradorModel.setTiporegistro(cursor.getString(cursor.getColumnIndex("ds_tiporegistro")));

        return userColaboradorModel;

    }


    public List<UserColaboradorModel> CarregaUsuario(String BR0){

        List<UserColaboradorModel> usuario = new ArrayList<UserColaboradorModel>();

        Cursor cursor =  dataBaseUtilUserColaborador.GetConexaoDataBase().rawQuery("SELECT * FROM userColaborador WHERE ds_br ='"+BR0+"'",null);

        /*POSICIONA O CURSOR NO PRIMEIRO REGISTRO*/
        cursor.moveToFirst();

        UserColaboradorModel userColaboradorModel = new UserColaboradorModel();

        //ADICIONANDO OS DADOS DA PESSOA
        userColaboradorModel.setCodigo(cursor.getInt(cursor.getColumnIndex("id_colaborador")));
        userColaboradorModel.setToken(cursor.getString(cursor.getColumnIndex("ds_token")));
        userColaboradorModel.setNome(cursor.getString(cursor.getColumnIndex("ds_nome")));
        userColaboradorModel.setBR(cursor.getString(cursor.getColumnIndex("ds_br")));
        userColaboradorModel.setEmpresa(cursor.getString(cursor.getColumnIndex("ds_empresa")));
        userColaboradorModel.setRegiao(cursor.getString(cursor.getColumnIndex("ds_regiao")));
        userColaboradorModel.setTiporegistro(cursor.getString(cursor.getColumnIndex("ds_tiporegistro")));

        usuario.add(userColaboradorModel);

        //RETORNANDO A LISTA DE PESSOAS
        return usuario;
    }

    public List<UserColaboradorModel> CarregaDadosUpDateList(String BR0){

        List<UserColaboradorModel> usuario = new ArrayList<UserColaboradorModel>();

        Cursor cursor =  dataBaseUtilUserColaborador.GetConexaoDataBase().rawQuery("SELECT * FROM userColaborador WHERE ds_br ='"+BR0+"'",null);

        cursor.moveToFirst();

        ///CRIANDO UMA NOVA PESSOAS
        UserColaboradorModel userColaboradorModel =  new UserColaboradorModel();

        //ADICIONANDO OS DADOS DA PESSOA
        userColaboradorModel.setCodigo(cursor.getInt(cursor.getColumnIndex("id_colaborador")));
        userColaboradorModel.setToken(cursor.getString(cursor.getColumnIndex("ds_token")));
        userColaboradorModel.setNome(cursor.getString(cursor.getColumnIndex("ds_nome")));
        userColaboradorModel.setBR(cursor.getString(cursor.getColumnIndex("ds_br")));
        userColaboradorModel.setEmpresa(cursor.getString(cursor.getColumnIndex("ds_empresa")));
        userColaboradorModel.setRegiao(cursor.getString(cursor.getColumnIndex("ds_regiao")));
        userColaboradorModel.setTiporegistro(cursor.getString(cursor.getColumnIndex("ds_tiporegistro")));

        usuario.add(userColaboradorModel);

        //RETORNANDO A LISTA DE PESSOAS
        return usuario;
    }


    public UserColaboradorModel CarregaDadosColaborador(){


        Cursor cursor =  dataBaseUtilUserColaborador.GetConexaoDataBase().rawQuery("SELECT * FROM userColaborador",null);

        cursor.moveToFirst();

        ///CRIANDO UMA NOVA PESSOAS
        UserColaboradorModel userColaboradorModel =  new UserColaboradorModel();

        //ADICIONANDO OS DADOS DA PESSOA
        userColaboradorModel.setCodigo(cursor.getInt(cursor.getColumnIndex("id_colaborador")));
        userColaboradorModel.setToken(cursor.getString(cursor.getColumnIndex("ds_token")));
        userColaboradorModel.setNome(cursor.getString(cursor.getColumnIndex("ds_nome")));
        userColaboradorModel.setBR(cursor.getString(cursor.getColumnIndex("ds_br")));
        userColaboradorModel.setEmpresa(cursor.getString(cursor.getColumnIndex("ds_empresa")));
        userColaboradorModel.setRegiao(cursor.getString(cursor.getColumnIndex("ds_regiao")));
        userColaboradorModel.setTiporegistro(cursor.getString(cursor.getColumnIndex("ds_tiporegistro")));

        return userColaboradorModel;

    }

    /***
     * CONSULTA TODAS AS PESSOAS CADASTRADAS NA BASE
     * @return
     */
    public UserColaboradorModel SelecionaColaborador(){
        UserColaboradorModel  colaborador = new UserColaboradorModel();
       // List<> pessoas = new ArrayList<ConsultaLdFObjModel>();


        //MONTA A QUERY A SER EXECUTADA
        StringBuilder stringBuilderQuery = new StringBuilder();
        stringBuilderQuery.append(" SELECT id_colaborador,      ");
        stringBuilderQuery.append("        ds_token,        ");
        stringBuilderQuery.append("        ds_nome,    ");
        stringBuilderQuery.append("        ds_br,        ");
        stringBuilderQuery.append("        ds_empresa,  ");
        stringBuilderQuery.append("        ds_regiao, ");
        stringBuilderQuery.append("        ds_tiporegistro ");
        stringBuilderQuery.append("  FROM  userColaborador       ");


        //CONSULTANDO OS REGISTROS CADASTRADOS
        Cursor cursor = dataBaseUtilUserColaborador.GetConexaoDataBase().rawQuery(stringBuilderQuery.toString(), null);

        /*POSICIONA O CURSOR NO PRIMEIRO REGISTRO*/
        cursor.moveToFirst();

        UserColaboradorModel userColaboradorModel;

        /* CRIANDO UMA NOVA PESSOAS */
        userColaboradorModel =  new UserColaboradorModel();

        //ADICIONANDO OS DADOS DA PESSOA

        userColaboradorModel.setCodigo(cursor.getInt(cursor.getColumnIndex("id_ldf")));
        userColaboradorModel.setToken(cursor.getString(cursor.getColumnIndex("ds_cidade")));
        userColaboradorModel.setNome(cursor.getString(cursor.getColumnIndex("ds_alimentador")));
        userColaboradorModel.setBR(cursor.getString(cursor.getColumnIndex("fl_religador")));
        userColaboradorModel.setEmpresa(cursor.getString(cursor.getColumnIndex("dt_tipoDeCurto")));
        userColaboradorModel.setRegiao(cursor.getString(cursor.getColumnIndex("fl_tombamento")));
        userColaboradorModel.setTiporegistro(cursor.getString(cursor.getColumnIndex("fl_valorDoCurto")));

        return userColaboradorModel;



    }


    /***
     * CONSULTA UMA PESSOA CADASTRADA PELO CÓDIGO
     * @param br
     * @return
     */
    public UserColaboradorModel CarregaDadosColaboradoes(String id){


        Cursor cursor =  dataBaseUtilUserColaborador.GetConexaoDataBase().rawQuery("SELECT * FROM userColaborador WHERE id_colaborador ='"+id+"'",null);

        cursor.moveToFirst();

        ///CRIANDO UMA NOVA PESSOAS
        UserColaboradorModel userColaboradorModel =  new UserColaboradorModel();

        //ADICIONANDO OS DADOS DA PESSOA
        userColaboradorModel.setCodigo(cursor.getInt(cursor.getColumnIndex("id_colaborador")));
        userColaboradorModel.setToken(cursor.getString(cursor.getColumnIndex("ds_token")));
        userColaboradorModel.setNome(cursor.getString(cursor.getColumnIndex("ds_nome")));
        userColaboradorModel.setBR(cursor.getString(cursor.getColumnIndex("ds_br")));
        userColaboradorModel.setEmpresa(cursor.getString(cursor.getColumnIndex("ds_empresa")));
        userColaboradorModel.setRegiao(cursor.getString(cursor.getColumnIndex("ds_regiao")));
        userColaboradorModel.setTiporegistro(cursor.getString(cursor.getColumnIndex("ds_tiporegistro")));

        return userColaboradorModel;

    }

}
