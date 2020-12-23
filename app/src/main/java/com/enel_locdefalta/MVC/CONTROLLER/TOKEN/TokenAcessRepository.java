package com.enel_locdefalta.MVC.CONTROLLER.TOKEN;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.enel_locdefalta.MVC.MODEL.RELATORIO.RelatorioLdFObjModel;
import com.enel_locdefalta.MVC.SERVIDOR_e_BD.CRUD.DataBaseTOKEN;
import com.enel_locdefalta.TOKEN.TokenAcessModel;

import java.util.ArrayList;
import java.util.List;

public class TokenAcessRepository {
    DataBaseTOKEN dataBaseTOKEN;

    /***
     * CONSTRUTOR
     * @param context
     */
    public TokenAcessRepository(Context context){

        dataBaseTOKEN =  new DataBaseTOKEN(context);

    }

    /***
     * SALVA UM NOVO REGISTRO NA BASE DE DADOS
     * @param tokenAcessModel
     */


    public void SalvarToken(TokenAcessModel tokenAcessModel){


        ContentValues contentValues =  new ContentValues();
        /*MONTANDO OS PARAMETROS PARA SEREM SALVOS*/
        contentValues.put("id", 1);
        contentValues.put("token",       tokenAcessModel.getDadosToken());
        /*EXECUTANDO INSERT DE UM NOVO REGISTRO*/
        dataBaseTOKEN.GetConexaoDataBase().insert("tokenAcess",null,contentValues);

    }





    /***
     * ATUALIZA UM REGISTRO JÁ EXISTENTE NA BASE
     * @param tokenAcessModel
     */

    public void Atualizar(TokenAcessModel tokenAcessModel){
        ContentValues contentValues =  new ContentValues();

        /*MONTA OS PARAMENTROS PARA REALIZAR UPDATE NOS CAMPOS*/
        contentValues.put("token",       tokenAcessModel.getDadosToken());

        /*REALIZANDO UPDATE PELA CHAVE DA TABELA*/
        dataBaseTOKEN.GetConexaoDataBase().update("tokenAcess", contentValues, "token = ?", new String[]{Integer.toString(Integer.parseInt(tokenAcessModel.getDadosToken()))});
    }

    /***
     * EXCLUI UM REGISTRO PELO CÓDIGO
     * @param codigo
     * @return
     */

    public Integer ExcluirTOKEN(int codigo){

        //EXCLUINDO  REGISTRO E RETORNANDO O NÚMERO DE LINHAS AFETADAS
        return dataBaseTOKEN.GetConexaoDataBase().delete("tokenAcess","id = ?", new String[]{Integer.toString(codigo)});
    }
    /***
     * CONSULTA O TOKEN PELO CÓDIGO
     * @param id
     * @return
     */
    public TokenAcessModel CarregaDadosToken(int id){


        Cursor cursor =  dataBaseTOKEN.GetConexaoDataBase().rawQuery("SELECT * FROM tokenAcess WHERE id ='"+id+"'",null);

        cursor.moveToFirst();

        ///CRIANDO UMA NOVA PESSOAS
        TokenAcessModel tokenAcessModel =  new TokenAcessModel();

        //ADICIONANDO OS DADOS DA PESSOA
        tokenAcessModel.setId(cursor.getString(cursor.getColumnIndex("id")));
        tokenAcessModel.setDadosToken(cursor.getString(cursor.getColumnIndex("token")));

        return tokenAcessModel;

    }


    public TokenAcessModel CarregaUsuarioToken(int id){

        TokenAcessModel usuario = new TokenAcessModel();

        Cursor cursor =  dataBaseTOKEN.GetConexaoDataBase().rawQuery("SELECT * FROM tokenAcess WHERE id ='"+id+"'",null);

        /*POSICIONA O CURSOR NO PRIMEIRO REGISTRO*/
        cursor.moveToFirst();

        ///CRIANDO UMA NOVA PESSOAS
        TokenAcessModel tokenAcessModel =  new TokenAcessModel();

        //ADICIONANDO OS DADOS DA PESSOA
        tokenAcessModel.setId(cursor.getString(cursor.getColumnIndex("id")));
        tokenAcessModel.setDadosToken(cursor.getString(cursor.getColumnIndex("token")));

        return tokenAcessModel;
    }


    /***
     * CONSULTA UMA PESSOA CADASTRADA PELO CÓDIGO
     * @param token
     * @return
     */
    public TokenAcessModel CarregaDadosUpDate(String token){


        Cursor cursor =  dataBaseTOKEN.GetConexaoDataBase().rawQuery("SELECT * FROM tokenAcess WHERE token ='"+token+"'",null);

        cursor.moveToFirst();

        ///CRIANDO UMA NOVA PESSOAS
        TokenAcessModel tokenAcessModel =  new TokenAcessModel();

        //ADICIONANDO OS DADOS DA PESSOA
        tokenAcessModel.setId(cursor.getString(cursor.getColumnIndex("id")));
        tokenAcessModel.setDadosToken(cursor.getString(cursor.getColumnIndex("token")));

        return tokenAcessModel;

    }


    public List<TokenAcessModel> SelecionarTodosOsTokens(){
        List<TokenAcessModel> pessoas = new ArrayList<TokenAcessModel>();


        //MONTA A QUERY A SER EXECUTADA
        StringBuilder stringBuilderQuery = new StringBuilder();
        stringBuilderQuery.append(" SELECT id,      ");
        stringBuilderQuery.append("        token        ");
        stringBuilderQuery.append("  FROM  tokenAcess       ");
        stringBuilderQuery.append(" ORDER BY id       ");


        //CONSULTANDO OS REGISTROS CADASTRADOS
        Cursor cursor = dataBaseTOKEN.GetConexaoDataBase().rawQuery(stringBuilderQuery.toString(), null);

        /*POSICIONA O CURSOR NO PRIMEIRO REGISTRO*/
        cursor.moveToFirst();


        TokenAcessModel historicoLdFObjModel;

        //REALIZA A LEITURA DOS REGISTROS ENQUANTO NÃO FOR O FIM DO CURSOR
        while (!cursor.isAfterLast()){

            /* CRIANDO UMA NOVA PESSOAS */
            historicoLdFObjModel =  new TokenAcessModel();

            //ADICIONANDO OS DADOS DA PESSOA
            historicoLdFObjModel.setId(String.valueOf(cursor.getInt(cursor.getColumnIndex("id"))));
            historicoLdFObjModel.setDadosToken(cursor.getString(cursor.getColumnIndex("token")));

            //ADICIONANDO UMA PESSOA NA LISTA
            pessoas.add(historicoLdFObjModel);

            //VAI PARA O PRÓXIMO REGISTRO
            cursor.moveToNext();
        }

        //RETORNANDO A LISTA DE PESSOAS
        return pessoas;

    }

}
