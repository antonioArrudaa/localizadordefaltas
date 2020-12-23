package com.enel_locdefalta.MVC.SERVIDOR_e_BD;

import android.content.Context;
import android.util.Log;


import com.enel_locdefalta.MVC.CONTROLLER.AJUSTES.DOWNLOAD_ALM_E_RLG_Repository;
import com.enel_locdefalta.MVC.CONTROLLER.AJUSTES.DOWNLOAD_LDF_Repository;
import com.enel_locdefalta.MVC.CONTROLLER.Login_e_Usuario.UserColaboradorRepository;
import com.enel_locdefalta.MVC.CONTROLLER.RELATORIO.RelatorioLDfRepository;
import com.enel_locdefalta.MVC.CONTROLLER.TOKEN.TokenAcessRepository;
import com.enel_locdefalta.MVC.MODEL.AJUSTES.ConsultaLdFObjModel;
import com.enel_locdefalta.MVC.MODEL.AJUSTES.ObjModel_ALM_RLG;
import com.enel_locdefalta.MVC.MODEL.ATUALIZA_LDF.Att_LDF;
import com.enel_locdefalta.MVC.MODEL.Login.UserColaboradorModel;
import com.enel_locdefalta.MVC.MODEL.RELATORIO.RelatorioLdFObjModel;
import com.enel_locdefalta.MVC.MODEL.VerificaBR0Model;
import com.enel_locdefalta.MVC.VIEW.AJUSTES.ConfiguracoesActivity;
import com.enel_locdefalta.TOKEN.TokenAcessModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class WebClient {

    Context context;
    ConfiguracoesActivity configuracoesActivity;
    String host_desenvolvimento ="http://192.168.137.1:3335/auth/";
    //String host_servidor = "http://192.168.11.10:3334";
    //String host_digital_ocean = "http://161.35.59.213:3335";


    String URL_DOWNLOAD_ALM = "/listldfsporalm";

    String token_acessWeb;

    public WebClient(Context context) {
        this.context = context;
    }



    //BAIXA LOCALIZADORES CADASTRADOS

    public ConsultaLdFObjModel CARREGA_LDF_POR_ALM(final JSONObject data) throws IOException {
        ConsultaLdFObjModel retorno;
        TokenAcessModel tokenAcessModel;
        TokenAcessRepository tokenAcessRepository = new TokenAcessRepository(context);

        tokenAcessModel = tokenAcessRepository.CarregaDadosToken(1);
        String acessoToken = tokenAcessModel.getDadosToken();

        try {

            final RequestBody body = RequestBody
                    .create(MediaType.parse("application/json"), data.toString());
            final Request request = new Request.Builder()
                    .url(host_desenvolvimento + URL_DOWNLOAD_ALM)
                    .post(body)
                    .addHeader("Accept", "application/json").addHeader("Authorization","Bearer "+acessoToken)
                    .build();

            final OkHttpClient client = new OkHttpClient();

            final Response response = client.newCall(request).execute();

            String json = response.body().string();
            Log.i("Valores", "Json:" + json);

            Log.i("DADOS", "JSON"+data);
            Log.i("DADOS_TOKEN","DADOS DO TOKEN DE ACESSO: "+ token_acessWeb);

            retorno = parseJson_LDF_POR_ALM(json);

            return retorno;

        } catch (IOException e) {
            Log.e("TEST", "Error", e);
        }

        return null;

    }

    private ConsultaLdFObjModel parseJson_LDF_POR_ALM(String json) {
        final String CIDADE = "cidade";
        final String ALIMENTADOR = "alimentador";
        final String RELIGADOR = "religador";
        final String TOMBAMENTO = "tombamento";
        final String VALOR_DO_CURTO = "valordocurto";
        final String TIPO_DO_CURTO = "tipodocurto";
        final String LATITUDE = "latitude";
        final String LONGITUDE = "longitude";
        final String DISTANCIA = "distancia";
        final String REGISTRO_ATIVO = "registroativo";



        try {
            ConsultaLdFObjModel consultaLdFObjModel = new ConsultaLdFObjModel();

            JSONObject jsonObj = new JSONObject(json);
            JSONArray array = jsonObj.getJSONArray("results");

            if (array.length() != 0) {
                for (int i = 0; i < array.length(); i++) {
                    JSONObject objArray = array.getJSONObject(i);

                    String cidade;
                    String alimentador;
                    String religador;
                    String tombamento;
                    String valorDoCurto;
                    String tipoDoCurto;
                    String latitude;
                    String longitude;
                    String distancia;
                    String registro_ativo;


                    cidade = objArray.getString(CIDADE);
                    alimentador = objArray.getString(ALIMENTADOR);
                    religador = objArray.getString(RELIGADOR);
                    tombamento = objArray.getString(TOMBAMENTO);
                    valorDoCurto = objArray.getString(VALOR_DO_CURTO);
                    tipoDoCurto = objArray.getString(TIPO_DO_CURTO);
                    latitude = objArray.getString(LATITUDE);
                    longitude = objArray.getString(LONGITUDE);
                    distancia = objArray.getString(DISTANCIA);
                    registro_ativo = objArray.getString(REGISTRO_ATIVO);


                    ConsultaLdFObjModel guardaldFObjModelConsulta = new ConsultaLdFObjModel();

                    guardaldFObjModelConsulta.setCidade(cidade);

                    guardaldFObjModelConsulta.setCidade(cidade);
                    guardaldFObjModelConsulta.setAlimentador(alimentador);
                    guardaldFObjModelConsulta.setReligador(religador);
                    guardaldFObjModelConsulta.setTombamento(tombamento);
                    guardaldFObjModelConsulta.setValorDoCurto(valorDoCurto);
                    guardaldFObjModelConsulta.setTipoDeCurto(tipoDoCurto);
                    guardaldFObjModelConsulta.setLATITUDE(latitude);
                    guardaldFObjModelConsulta.setLONGITUDE(longitude);
                    guardaldFObjModelConsulta.setDISTANCIA(distancia);
                    guardaldFObjModelConsulta.setRegistroAtivo(Byte.parseByte(registro_ativo));


                    new DOWNLOAD_LDF_Repository(context).Salvar(guardaldFObjModelConsulta);


                    Log.println(Log.INFO, "SALVO: ", "Dados Salvo");

                }


            } else {
                consultaLdFObjModel.setMensagemERROR("VAZIO");

                //Log.println(Log.INFO, "ERROR: ", "DADOS SD");

            }

            return consultaLdFObjModel;


        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }


    //Baixar ALIMENTADORES

    public ObjModel_ALM_RLG Post_ALM(final JSONObject data) throws IOException {
        ObjModel_ALM_RLG retorno;
        Response response;

        TokenAcessModel tokenAcessModel;
        TokenAcessRepository tokenAcessRepository = new TokenAcessRepository(context);

        tokenAcessModel = tokenAcessRepository.CarregaDadosToken(1);
        String acessoToken = tokenAcessModel.getDadosToken();

        try {

            final RequestBody body = RequestBody
                    .create(MediaType.parse("application/json"), data.toString());
            final Request request = new Request.Builder()
                    .url(host_desenvolvimento + "/listaALM") //MudaR A URL DE BUSCA
                    .post(body)
                    .addHeader("AccelistaAlmpt", "application/json").addHeader("Authorization","Bearer "+acessoToken)
                    .build();

            final OkHttpClient client = new OkHttpClient();


            response = client.newCall(request).execute();


            Log.println(Log.DEBUG, "RESPOSTA CODIGO", "HTTP:" + response.code());


            String json = response.body().string();
            Log.i("Valores", "Json:" + json);
            retorno = RetornaParseJson_ALM(json);

            return retorno;

        } catch (IOException e) {
            // Log.e("RESPOSTA CODIGO", "HTTP:" + response.code());
            Log.e("TEST", "Sem Conexão", e);

        }
        return null;
    }

    private ObjModel_ALM_RLG RetornaParseJson_ALM(String json) {
        //final String TOKEN = "id";
        final String ALM = "alimentador";
        final String ERROR2 = "error";


        String ERROR=null;

        try {
            ObjModel_ALM_RLG objModel_alm_rlg = new ObjModel_ALM_RLG();

            JSONObject jsonObj = new JSONObject(json);
            JSONArray array = jsonObj.getJSONArray("results");

            //JSONObject dados = (JSONObject) array.get(0);

            JSONObject dados = array.getJSONObject(0);

            ////   if (dados.getBoolean("error")==true){
            //      ERROR = String.valueOf(dados.get("error"));
            //      Log.i("RETORNODOERROR1", "TESTE NO ERROR IF: " + ERROR);
            //   }else{
            //        ERROR = "SUCESS";
            //     Log.i("RETORNODOERROR1", "TESTE NO ERROR ELSE: " + ERROR);
            // }





            /*String errorD = String.valueOf(dados.get("error"));
            //ERROR = String.valueOf(dados.get("error"));
            if(errorD.equals("null")){
                ERROR="sucess";
            }else {
                Log.i("RETORNODOERROR2", "TESTE NO DADOS: " + errorD);
                ERROR = "Alimentador não encontrado";
            }*/

            //Log.i("RETORNODOERROR2", "TESTE NO DADOS: " + errorD);

            //   JSONArray statusError =  dados.getJSONArray("dados");
            //JSONObject valorDeDadoErrror = statusError.getJSONObject(0);


            //    retornoError = String.valueOf(valorDeDadoErrror.get("error"));

            // Log.i("OBJETOCOLABORADOR111", "TESTE NO ERROR: " + retornoError);

            /*if (statusError.equals("Senha incorreta")){
                Log.i("TESTEEEE", "TESTE NO ERROR: " + object.get("error"));
            }


            if(statusError.equals("{\"error\":\"Senha incorreta\"}")){
                Log.i("Dentro do Colaborador", "TESTE NO ERROR: " + object.get("error"));
            }else{
                Log.i("Fora do Colaborador", "FORA DO IF");
            }*/

       /*     String error1="";

            if (retornoError==null||retornoError==""){
                error1 = "autorizado";
            }else{
                error1 = retornoError;
            }



            Log.i("VERIFICA ERROR1111111", "TESTE NO ERROR: " + error1);


*/
            if (dados.getString("error").equals("Alimentador não encontrado")) {
                Log.i("Dentro do if 1", "TESTE NO ERROR: " + "AQUI");
                objModel_alm_rlg.setMsgError("Alimentador não encontrado");
                return objModel_alm_rlg;
            } else {
                String teste = dados.getString("error");
                teste = "s"+dados.getString("error");
                Log.i("Dentro do else 1", "TESTE NO ERROR: " + teste);
                //if (teste=="snull") {
                    if (array.length() != 0) {
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject objArray = array.getJSONObject(i);

                            //String token;
                            String alm;
                            String error;

                            //token = objArray.getString(TOKEN);


                            alm = objArray.getString(ALM);
                            error = objArray.getString(ERROR2);
                            ObjModel_ALM_RLG alm_rlg = new ObjModel_ALM_RLG();

                            //guardaUserColaboradorModel.setCodigo(1);
                            //guardaUserColaboradorModel.setToken(token);
                            alm_rlg.setAlimentador(alm);
                            alm_rlg.setMsgError(error);
                            try {
                                new DOWNLOAD_ALM_E_RLG_Repository(context).Salvar_ALM(alm_rlg);
                                Log.println(Log.INFO, "SALVO: ", "Dados Salvo");

                            } catch (Exception e) {
                                Log.println(Log.ERROR, "NAO SALVO: ", "Dados não Salvo");

                            }

                        }

                  //  }
                }return objModel_alm_rlg;
            }


 /*               if (array.length() != 0) {
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject objArray = array.getJSONObject(i);

                        //String token;
                        String alm;
                        String error;

                        //token = objArray.getString(TOKEN);
                        alm = objArray.getString(ALM);
                        error = objArray.getString(ERROR2);
                        ObjModel_ALM_RLG alm_rlg = new ObjModel_ALM_RLG();

                        //guardaUserColaboradorModel.setCodigo(1);
                        //guardaUserColaboradorModel.setToken(token);
                        alm_rlg.setAlimentador(alm);
                        alm_rlg.setMsgError(error);
                        try {
                            new LdfALMRepository(context).Salvar_ALM(alm_rlg);
                            Log.println(Log.INFO, "SALVO: ", "Dados Salvo");

                        } catch (Exception e) {
                            Log.println(Log.ERROR, "NAO SALVO: ", "Dados não Salvo");

                        }

                    }
                    return objModel_alm_rlg;
                } else {
                    objModel_alm_rlg.setMsgError("ERROR");
                    //userColaboradorModel.setMensagemError("Usario Nao encontrado");
                }
                return objModel_alm_rlg;
*/


        } catch (JSONException e) {
            //Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
            Log.e("TEST", "JSON", e);
            e.printStackTrace();
            return null;
        }

    }

    //BAIXA OS RELIGADORES

    public ObjModel_ALM_RLG Post_RLG(final JSONObject data) throws IOException {
        ObjModel_ALM_RLG retorno;
        Response response;

        TokenAcessModel tokenAcessModel;
        TokenAcessRepository tokenAcessRepository = new TokenAcessRepository(context);

        tokenAcessModel = tokenAcessRepository.CarregaDadosToken(1);
        String acessoToken = tokenAcessModel.getDadosToken();

        try {

            final RequestBody body = RequestBody
                    .create(MediaType.parse("application/json"), data.toString());
            final Request request = new Request.Builder()
                    .url(host_desenvolvimento + "/listRLGporalm")
                    .post(body)
                    .addHeader("Accept", "application/json").addHeader("Authorization","Bearer "+acessoToken)
                    .build();

            final OkHttpClient client = new OkHttpClient();


            response = client.newCall(request).execute();


            Log.println(Log.DEBUG, "RESPOSTA CODIGO", "HTTP:" + response.code());


            String json = response.body().string();
            Log.i("Valores", "Json:" + json);
            retorno = RetornaParseJson_RLG(json);

            return retorno;

        } catch (IOException e) {
            // Log.e("RESPOSTA CODIGO", "HTTP:" + response.code());
            Log.e("TEST", "Sem Conexão", e);

        }
        return null;
    }

    private ObjModel_ALM_RLG RetornaParseJson_RLG(String json) {
        //final String TOKEN = "id";
        final String RLG = "religador";
        final String ALM = "alimentador";


        try {
            ObjModel_ALM_RLG objModel_alm_rlg = new ObjModel_ALM_RLG();

            JSONObject jsonObj = new JSONObject(json);
            JSONArray array = jsonObj.getJSONArray("results");


            if (array.length() != 0) {
                for (int i = 0; i < array.length(); i++) {
                    JSONObject objArray = array.getJSONObject(i);

                    //String token;
                    String rlg;
                    String alm;

                    //token = objArray.getString(TOKEN);
                    rlg = objArray.getString(RLG);
                    alm = objArray.getString(ALM);

                    ObjModel_ALM_RLG alm_rlg = new ObjModel_ALM_RLG();

                    //guardaUserColaboradorModel.setCodigo(1);
                    //guardaUserColaboradorModel.setToken(token);
                    alm_rlg.setReligador(rlg);
                    alm_rlg.setAlimentador(alm);

                    try {
                        new DOWNLOAD_ALM_E_RLG_Repository(context).Salvar_RLG(alm_rlg);
                        Log.println(Log.INFO, "SALVO: ", "Dados Salvo");

                    } catch (Exception e) {
                        Log.println(Log.ERROR, "NAO SALVO: ", "Dados não Salvo");

                    }


                }
            } else {
                objModel_alm_rlg.setMsgError("ERROR");
                //userColaboradorModel.setMensagemError("Usario Nao encontrado");
            }

            return objModel_alm_rlg;


        } catch (JSONException e) {
            //Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
            Log.e("TEST", "JSON", e);
            e.printStackTrace();
            return null;
        }

    }

    //Envia Relatório para servidor

    public RelatorioLdFObjModel Post_RELATORIO(final JSONObject data) throws IOException {
        RelatorioLdFObjModel retorno;
        Response response;

        TokenAcessModel tokenAcessModel;
        TokenAcessRepository tokenAcessRepository = new TokenAcessRepository(context);

        tokenAcessModel = tokenAcessRepository.CarregaDadosToken(1);
        String acessoToken = tokenAcessModel.getDadosToken();

        try {

            final RequestBody body = RequestBody
                    .create(MediaType.parse("application/json"), data.toString());

 /*           File sourceFile = new File(ImagePath);

            Log.d("TAG", "File...::::" + sourceFile + " : " + sourceFile.exists());

            final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/*");

            String filename = ImagePath.substring(ImagePath.lastIndexOf("/") + 1);


            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("uploaded_file", filename, RequestBody.create(MEDIA_TYPE_PNG, sourceFile))
                    .addFormDataPart("result", "my_image")
                    .build();
*/

            final Request request = new Request.Builder()
                    .url(host_desenvolvimento + "/newrelatorio")
                    .post(body)
                    .addHeader("Accept", "application/json").addHeader("Authorization","Bearer "+acessoToken)
                    .build();

            final OkHttpClient client = new OkHttpClient();


            response = client.newCall(request).execute();


            Log.println(Log.DEBUG, "RESPOSTA CODIGO", "HTTP:" + response.code());


            String json = response.body().string();
            Log.i("Valores", "Json:" + json);
            retorno = RetornaParseJson_RELATORIO(json);

            //retorno = json;
            return retorno;


        } catch (IOException e) {
            // Log.e("RESPOSTA CODIGO", "HTTP:" + response.code());
            Log.e("TEST", "Sem Conexão", e);

        }
        return null;
    }

    private RelatorioLdFObjModel RetornaParseJson_RELATORIO(String json) {
        //final String TOKEN = "id";
        final String STATUSRELATORIO = "statusrelatorio";
        //final String ALM = "alimentador";


        try {
            RelatorioLdFObjModel relatorioLdFObjModel = new RelatorioLdFObjModel();

            JSONObject jsonObj = new JSONObject(json);
            JSONArray array = jsonObj.getJSONArray("results");

            if (array.length() != 0) {
                for (int i = 0; i < array.length(); i++) {
                    JSONObject objArray = array.getJSONObject(i);

                    //String token;
                    String statusrelatorio;
                    //String alm;

                    //token = objArray.getString(TOKEN);
                    statusrelatorio = objArray.getString(STATUSRELATORIO);
                   // alm = objArray.getString(ALM);

                    RelatorioLdFObjModel relatorioLdFObjModel1 = new RelatorioLdFObjModel();

                    relatorioLdFObjModel1.setStatusRelatorio(statusrelatorio);
                    //guardaUserColaboradorModel.setCodigo(1);
                    //guardaUserColaboradorModel.setToken(token);
                    //relatorioLdFObjModel1.setReligador(rlg);
                    //relatorioLdFObjModel1.setAlimentador(alm);

                    try {
                        new RelatorioLDfRepository(context).AtualizaStatusRelatorio(relatorioLdFObjModel1);
                        Log.println(Log.INFO, "SALVO: ", "Dados Salvo");

                    } catch (Exception e) {
                        Log.println(Log.ERROR, "NAO SALVO: ", "Dados não Salvo");

                    }


                }
            } else {
                relatorioLdFObjModel.setMensagemError("VAZIO");
                //objModel_alm_rlg.setMsgError("ERROR");
                //userColaboradorModel.setMensagemError("Usario Nao encontrado");
            }

            return relatorioLdFObjModel;


        } catch (JSONException e) {
            //Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
            Log.e("TEST", "JSON", e);
            e.printStackTrace();
            return null;
        }

    }


    public boolean POST_CADASTRO_COLABORADOR(final JSONObject data) throws IOException {
        UserColaboradorModel retorno = new UserColaboradorModel();
        Response response;

        TokenAcessModel tokenAcessModel;
        TokenAcessRepository tokenAcessRepository = new TokenAcessRepository(context);

        tokenAcessModel = tokenAcessRepository.CarregaDadosToken(1);
        String acessoToken = tokenAcessModel.getDadosToken();

        try {

            UserColaboradorModel guarda_estado = new UserColaboradorModel();
            final RequestBody body = RequestBody
                    .create(MediaType.parse("application/json"), data.toString());
            final Request request = new Request.Builder()
                    .url(host_desenvolvimento + "/newcolaborador")
                    .post(body)
                    .addHeader("Accept", "application/json").addHeader("Authorization","Bearer "+acessoToken)
                    .build();

            final OkHttpClient client = new OkHttpClient();


            response = client.newCall(request).execute();


            int respostaCodigo = response.code();
            Log.println(Log.DEBUG, "RESPOSTA CODIGO", "HTTP:" + response.code());

            String code = String.valueOf(respostaCodigo);

            //guarda_estado.setMensagemStatus(code);

            //String json = response.body().string();
            //Log.i("Valores", "Json:" + String.valueOf(respostaCodigo));


            return true;

        } catch (IOException e) {
            // Log.e("RESPOSTA CODIGO", "HTTP:" + response.code());
            Log.e("ERROR", "ERROR DE CONEXÃO LOGIN NA CLASSE WEB CLIENT: Sem Conexão", e);

            //;Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show()

        }
        return false;
    }






    //Atualiza Localizador de Falta

    //BUSCA O LDF PARA ATUALIZAR
    public Att_LDF POST_BUSCA_LDF(final JSONObject data) throws IOException {
        TokenAcessModel tokenAcessModel;
        TokenAcessRepository tokenAcessRepository = new TokenAcessRepository(context);

        tokenAcessModel = tokenAcessRepository.CarregaDadosToken(1);
        String acessoToken = tokenAcessModel.getDadosToken();


        Att_LDF retorno;

        Response response;
        try {

            final RequestBody body = RequestBody
                    .create(MediaType.parse("application/json"), data.toString());
            final Request request = new Request.Builder()
                    .url(host_desenvolvimento + "/ldf/searchldf")
                    .post(body)
                    .addHeader("Accept", "application/json").addHeader("Authorization","Bearer "+acessoToken)
                    .build();

            final OkHttpClient client = new OkHttpClient();


            response = client.newCall(request).execute();


            Log.println(Log.DEBUG, "RESPOSTA CODIGO", "HTTP:" + response.code());


            String json = response.body().string();
            Log.i("Valores", "Json:" + json);
            retorno = RetornoDaBuscaLDF(json);

            return retorno;

        } catch (IOException e) {
            //Uteis.AlertLogin(context,"Sem conexão com o servidor");
            retorno = new Att_LDF();

            retorno.setRetornoMensagem("0");

            //retornoCode.setRetorno(1);
            // Log.e("RESPOSTA CODIGO", "HTTP:" + response.code());
            Log.e("ERROR", "ERROR DE CONEXÃO LOGIN NA CLASSE WEB CLIENT: Sem Conexão", e);


            //;Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show()

            return retorno;

        }
        //return null;
    }

    private Att_LDF RetornoDaBuscaLDF(String json) {
        //final String ERROR = "success";

        final String ID = "id";
        final String CIDADE = "cidade";
        final String REGIAO = "regiao";
        final String ALM = "alimentador";
        final String RLG = "religador";
        final String CSI = "tombamento";
        final String VALORCURTO = "valorDoCurto";
        final String TIPOCURTO = "tipoDeCurto";
        final String LAT = "Lat";
        final String LONG = "Long";
        final String DISTANCIA = "distancia";
        final String REGISTROATIVO = "registroAtivo";
        final String DATAATUALIZACAO = "data_atualizacao";
        final String STATUSLDF = "status";
        final String SUCESS = "success";




        try {
            Att_LDF att_ldf = new Att_LDF();

            JSONObject jsonObj = new JSONObject(json);
            JSONArray array = jsonObj.getJSONArray("results");

            if (array.length() != 0) {
                for (int i = 0; i < array.length(); i++) {
                    JSONObject objArray = array.getJSONObject(i);

                    att_ldf = new Att_LDF();

                    //String token;
                    //String error;
                    String success;
                    String  id;
                    String  cidade;
                    String regiao;
                    String  alimentador;
                    String  religador;
                    String  tipoDeCurto;
                    String  tombamento;
                    String  valorDoCurto;
                    String  lat;
                    String  longi;
                    String  dist;
                    byte    registroAtivo;
                    String data_att;
                    String statusldf;
                    String retornoMensagem;


                    //token = objArray.getString(TOKEN);
                    success = objArray.getString(SUCESS);

                    if(success.equals("LDF ENCONTRADO")){

                        id = objArray.getString(ID);
                        cidade = objArray.getString(CIDADE);
                        regiao = objArray.getString(REGIAO);
                        alimentador = objArray.getString(ALM);
                        religador = objArray.getString(RLG);
                        tombamento = objArray.getString(CSI);
                        valorDoCurto = objArray.getString(VALORCURTO);
                        tipoDeCurto = objArray.getString(TIPOCURTO);
                        lat = objArray.getString(LAT);
                        longi = objArray.getString(LONG);
                        dist = objArray.getString(DISTANCIA);
                        registroAtivo = Byte.parseByte(objArray.getString(REGISTROATIVO));
                        data_att = objArray.getString(DATAATUALIZACAO);
                        statusldf = objArray.getString(STATUSLDF);


                        att_ldf.setMensagemERROR(success);
                        att_ldf.setId(id);
                        att_ldf.setCidade(cidade);
                        att_ldf.setRegião(regiao);
                        att_ldf.setAlimentador(alimentador);
                        att_ldf.setReligador(religador);
                        att_ldf.setTombamento(tombamento);
                        att_ldf.setValorDoCurto(valorDoCurto);
                        att_ldf.setTipoDeCurto(tipoDeCurto);
                        att_ldf.setLATITUDE(lat);
                        att_ldf.setLONGITUDE(longi);
                        att_ldf.setDISTANCIA(dist);
                        att_ldf.setRegistroAtivo(registroAtivo);
                        att_ldf.setDataATT(data_att);
                        att_ldf.setStatusATT(statusldf);

                        return att_ldf;
                    }else{
                        att_ldf.setMensagemERROR(success);
                    }




                    //Integer codigo;






                    //verificaBR0Model.setMensagem(success);
                    //guardaUserColaboradorModel.setToken(token);
                    //relatorioLdFObjModel1.setReligador(rlg);
                    //relatorioLdFObjModel1.setAlimentador(alm);

                    try {
                        return att_ldf;
                        //new RelatorioLDfRepository(context).SalvarRelatorio(relatorioLdFObjModel1);
                        //Log.println(Log.INFO, "SALVO: ", "Dados Salvo");

                    } catch (Exception e) {
                        Log.println(Log.ERROR, "NAO SALVO: ", "Dados não Salvo");

                    }


                }
            } else {
                //objModel_alm_rlg.setMsgError("ERROR");
                //userColaboradorModel.setMensagemError("Usario Nao encontrado");
            }

            return att_ldf;


        } catch (JSONException e) {
            //Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
            Log.e("TEST", "JSON", e);
            e.printStackTrace();
            return null;
        }


    }

    //ENVIA OS DADOS DO LDF ATUALIZADO
    public Att_LDF PUT_ATUALIZA_LDF(final JSONObject data) throws IOException {
        TokenAcessModel tokenAcessModel;

        TokenAcessRepository tokenAcessRepository = new TokenAcessRepository(context);

        Att_LDF att_ldf = new Att_LDF();

        tokenAcessModel = tokenAcessRepository.CarregaDadosToken(1);

        String acessoToken = tokenAcessModel.getDadosToken();






        String retorno;

        Response response;


        String id;
        //Json
        //JSONObject jsonObj = new JSONObject(data);


        try {

            JSONObject jsonObject = new JSONObject(String.valueOf(data));
            id = jsonObject.getString("id");

            Log.i( "RESPOSTAPUT", "HTTP:" + id);


            final RequestBody body = RequestBody
                    .create(MediaType.parse("application/json"), data.toString());
            final Request request = new Request.Builder()
                    .url(host_desenvolvimento + "/ldfatt/"+id)
                    .put(body)
                    .addHeader("Accept", "application/json").addHeader("Authorization","Bearer "+acessoToken)
                    .build();

            final OkHttpClient client = new OkHttpClient();


            response = client.newCall(request).execute();


            Log.println(Log.DEBUG, "RESPOSTA CODIGO", "HTTP:" + response.code());


            String json = response.body().string();
            Log.i("Valores", "Json:" + json);
            att_ldf = RetornoDaATUALIZADORLDF(json);

            return att_ldf;

        } catch (IOException | JSONException e) {
            //Uteis.AlertLogin(context,"Sem conexão com o servidor");


            retorno = "3";

            //retornoCode.setRetorno(1);
            // Log.e("RESPOSTA CODIGO", "HTTP:" + response.code());
            Log.e("ERROR", "ERROR DE CONEXÃO LOGIN NA CLASSE WEB CLIENT: Sem Conexão", e);


            //;Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show()

            return att_ldf;

        }
        //return null;
    }

    private Att_LDF RetornoDaATUALIZADORLDF(String json) {
        //final String ERROR = "success";

        final String STATUS = "status";
        String status="";
        try {
            Att_LDF att_ldf = new Att_LDF();

            JSONObject jsonObj = new JSONObject(json);
            JSONArray array = jsonObj.getJSONArray("results");

            if (array.length() != 0) {
                for (int i = 0; i < array.length(); i++) {
                    JSONObject objArray = array.getJSONObject(i);

                    status = objArray.getString(STATUS);

                    if (status.equals("1")) {

                        status = objArray.getString(STATUS);
                        att_ldf.setStatusATT(status);
                        return att_ldf;
                    }else{
                        att_ldf.setStatusATT(status);
                    }

                    try {
                        return att_ldf;
                        //new RelatorioLDfRepository(context).SalvarRelatorio(relatorioLdFObjModel1);
                        //Log.println(Log.INFO, "SALVO: ", "Dados Salvo");

                    } catch (Exception e) {
                        Log.println(Log.ERROR, "NAO SALVO: ", "Dados não Salvo");
                    }

                }
            } else {
                //objModel_alm_rlg.setMsgError("ERROR");
                //userColaboradorModel.setMensagemError("Usario Nao encontrado");
            }

            return att_ldf;


        } catch (JSONException e) {
            //Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
            Log.e("TEST", "JSON", e);
            e.printStackTrace();
            return null;
        }


    }




    //Verifica BR0 do colaborador

    public VerificaBR0Model POST_VERIFICA_BR0_COLABORADOR(final JSONObject data) throws IOException {
        TokenAcessModel tokenAcessModel;
        TokenAcessRepository tokenAcessRepository = new TokenAcessRepository(context);

        tokenAcessModel = tokenAcessRepository.CarregaDadosToken(1);
        String acessoToken = tokenAcessModel.getDadosToken();


        VerificaBR0Model retorno;

        Response response;
        try {

            final RequestBody body = RequestBody
                    .create(MediaType.parse("application/json"), data.toString());
            final Request request = new Request.Builder()
                    .url(host_desenvolvimento + "/verificaBR0/")
                    .post(body)
                    .addHeader("Accept", "application/json").addHeader("Authorization","Bearer "+acessoToken)
                    .build();

            final OkHttpClient client = new OkHttpClient();


            response = client.newCall(request).execute();


            Log.println(Log.DEBUG, "RESPOSTA CODIGO", "HTTP:" + response.code());


            String json = response.body().string();
            Log.i("Valores", "Json:" + json);
            retorno = RetornaVerificacaoBR0(json);

            return retorno;

        } catch (IOException e) {
            //Uteis.AlertLogin(context,"Sem conexão com o servidor");
            retorno = new VerificaBR0Model();

            retorno.setMensagem("0");

            //retornoCode.setRetorno(1);
            // Log.e("RESPOSTA CODIGO", "HTTP:" + response.code());
            Log.e("ERROR", "ERROR DE CONEXÃO LOGIN NA CLASSE WEB CLIENT: Sem Conexão", e);


            //;Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show()

            return retorno;

        }
        //return null;
    }

    private VerificaBR0Model RetornaVerificacaoBR0(String json) {
        final String ERROR = "error";
        final String SUCESS = "success";

        try {
            VerificaBR0Model verificaBR0Model = new VerificaBR0Model();

            JSONObject jsonObj = new JSONObject(json);
            JSONArray array = jsonObj.getJSONArray("results");

            if (array.length() != 0) {
                for (int i = 0; i < array.length(); i++) {
                    JSONObject objArray = array.getJSONObject(i);

                    //String token;
                    String error;
                    String success;

                    //token = objArray.getString(TOKEN);
                    error = objArray.getString(ERROR);

                    verificaBR0Model = new VerificaBR0Model();

                    verificaBR0Model.setMensagem(error);
                    //verificaBR0Model.setMensagem(success);
                    //guardaUserColaboradorModel.setToken(token);
                    //relatorioLdFObjModel1.setReligador(rlg);
                    //relatorioLdFObjModel1.setAlimentador(alm);

                    try {
                        return verificaBR0Model;
                        //new RelatorioLDfRepository(context).SalvarRelatorio(relatorioLdFObjModel1);
                        //Log.println(Log.INFO, "SALVO: ", "Dados Salvo");

                    } catch (Exception e) {
                        Log.println(Log.ERROR, "NAO SALVO: ", "Dados não Salvo");

                    }


                }
            } else {
                //objModel_alm_rlg.setMsgError("ERROR");
                //userColaboradorModel.setMensagemError("Usario Nao encontrado");
            }

            return verificaBR0Model;


        } catch (JSONException e) {
            //Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
            Log.e("TEST", "JSON", e);
            e.printStackTrace();
            return null;
        }


    }







    //Login UserColaborador

    public UserColaboradorModel LOGIN(final JSONObject data) throws IOException {
        UserColaboradorModel retorno;

        Response response;
        try {

            final RequestBody body = RequestBody
                    .create(MediaType.parse("application/json"), data.toString());
            final Request request = new Request.Builder()
                    .url(host_desenvolvimento + "/autheticadeapp")
                    .post(body)
                    .addHeader("Accept", "application/json")
                    .build();

            final OkHttpClient client = new OkHttpClient();


            response = client.newCall(request).execute();


            Log.println(Log.DEBUG, "RESPOSTA CODIGO", "HTTP:" + response.code());


            String json = response.body().string();
            Log.i("Valores", "Json:" + json);
            retorno = RetornaParseJsonLogin(json);

            return retorno;

        } catch (IOException e) {
            //Uteis.AlertLogin(context,"Sem conexão com o servidor");
            retorno = new UserColaboradorModel();

            retorno.setMensagemStatus("0");

            //retornoCode.setRetorno(1);
            // Log.e("RESPOSTA CODIGO", "HTTP:" + response.code());
            Log.e("ERROR", "ERROR DE CONEXÃO LOGIN NA CLASSE WEB CLIENT: Sem Conexão", e);


            //;Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show()

            return retorno;

        }
        //return null;
    }

    private UserColaboradorModel RetornaParseJsonLogin(String json) {
        final String ERROR = "error";
        final String TOKEN = "id";
        final String NOME = "name";
        final String SENHA = "senha";
        final String BR = "br";
        final String EMPRESA = "empresa";
        final String REGIAO = "regiao";
        final String TIPOREGISTRO = "tiporegistro";
        final String TOKEN_ACESS = "token";


        try {
            UserColaboradorModel userColaboradorModel = new UserColaboradorModel();

            String retornoError =null;

            JSONObject jsonObj = new JSONObject(json);
            JSONArray array = jsonObj.getJSONArray("results");
            JSONObject dados = (JSONObject) array.get(0);

            JSONObject object = array.getJSONObject(0);

            Log.i("OBJETOCOLABORADOR", "TESTE NO ERROR: " + object);
            Log.i("OBJETOCOLABORADOR", "TESTE NO DADOS: " + dados);

            JSONArray statusError =  dados.getJSONArray("dados");
            JSONObject valorDeDadoErrror = statusError.getJSONObject(0);


            retornoError = String.valueOf(valorDeDadoErrror.get("error"));

            Log.i("OBJETOCOLABORADOR111", "TESTE NO ERROR: " + retornoError);

            /*if (statusError.equals("Senha incorreta")){
                Log.i("TESTEEEE", "TESTE NO ERROR: " + object.get("error"));
            }


            if(statusError.equals("{\"error\":\"Senha incorreta\"}")){
                Log.i("Dentro do Colaborador", "TESTE NO ERROR: " + object.get("error"));
            }else{
                Log.i("Fora do Colaborador", "FORA DO IF");
            }*/

            String error1="";

            if (retornoError==null||retornoError==""){
                error1 = "autorizado";
            }else{
                error1 = retornoError;
            }



            Log.i("VERIFICA ERROR1111111", "TESTE NO ERROR: " + error1);



            if (error1.equals("Senha incorreta")) {
                Log.i("Dentro do if 1", "TESTE NO ERROR: " + error1);
                userColaboradorModel.setMensagemStatus(error1);
            } else {


                //JSONObject dados = array.getJSONObject(0);
                //Log.i("DADOS_COMPLETOS", "DADOS DO ARRAY: " + dados);

                //JSONArray dadosColaboradorJson = object.getJSONArray("colaborador");
                //Log.i("ARRAY", "DADOS DO ARRAY: ---------");



               // JSONObject dados = (JSONObject) object.get("dados");
                //JSONObject dados1 = (JSONObject) dadosColaborador.get(1);

                //Log.i("ARRAY", "DADOS DO ARRAYTOKEN: " + dados1.get("br"));

                JSONArray dadosColaborador = dados.getJSONArray("dados");

                for (int i = 0; i < dadosColaborador.length(); i++) {
                    JSONObject validadados = dadosColaborador.getJSONObject(i);
                    //Log.i("ARRAY", "DADOS DO ARRAYyyyyy: " + validadados);
                    String error;
                    String id;
                    String name;
                    String br;
                    String empresa;
                    String regiao;
                    String tiporegistro;

                    error = validadados.getString(ERROR);
                    id = validadados.getString(TOKEN);
                    name = validadados.getString(NOME);
                    br = validadados.getString(BR);
                    empresa = validadados.getString(EMPRESA);
                    regiao = validadados.getString(REGIAO);
                    tiporegistro = validadados.getString(TIPOREGISTRO);

                    //Log.i("ARRAY2222222", "DADOS DO ARRAYyyyyy: " + empresa);

                    //Log.i("VERIFICAERROR", "Verificando a variavel error:" + error);

                    UserColaboradorModel guardaUserColaboradorModel = new UserColaboradorModel();

                    userColaboradorModel.setMensagemStatus(error);
                    guardaUserColaboradorModel.setCodigo(1);
                    guardaUserColaboradorModel.setToken(id);
                    guardaUserColaboradorModel.setNome(name);
                    guardaUserColaboradorModel.setBR(br);
                    guardaUserColaboradorModel.setEmpresa(empresa);
                    guardaUserColaboradorModel.setRegiao(regiao);
                    guardaUserColaboradorModel.setTiporegistro(tiporegistro);

                    new UserColaboradorRepository(context).SalvarUser(guardaUserColaboradorModel);
                    Log.println(Log.INFO, "SALVO: ", "Dados Salvo");
                }



                //JSONObject dadosColaborador = (JSONObject) dados.get("colaborador");
               // Log.i("DADOS_COLABORADOR", "DADOS DO ARRAY: " + dadosColaborador);

                JSONObject dadosTOKEN = array.getJSONObject(1);
                //Log.i("DADOS_COMPLETOS", "DADOS DO ARRAY: " + dadosTOKEN);


                String dadosToken = String.valueOf(dadosTOKEN.get("token"));

                TokenAcessModel guardartokenAcessModel = new TokenAcessModel();

                guardartokenAcessModel.setDadosToken(dadosToken);


                new TokenAcessRepository(context).SalvarToken(guardartokenAcessModel);
                Log.println(Log.INFO, "TOKEN_SALVO", "Dados TOKEN Salvo");


               // TOKEN_ACESS token_acess = new TOKEN_ACESS();

               // token_acessWeb = dadosToken;
                //token_acess.setTOKEN_ACESS(dadosToken);

               // Log.i("TOKEN", "DADOS DO TOKEN: " + dadosToken);
                //Log.i("TOKEN_DEF","TOKEN SALVO: Bearer "+ token_acess.getTOKEN_ACESS());
                //Pegar os valores do colaborador
                /*if (array.getBoolean(0)){
                    Log.i("OBJETOCOLABORADOR", "DADOS DO ARRAY: " + array);
                    for(int i=0; i<array.length();i++){

                    }
                }*/



                Log.i("Dentro do if 2", "DENTRO DO ARRAY MAIOR QUE ZERO: ");
 /*               for (int i = 0; i < array.length(); i++) {
                    JSONObject objArray = array.getJSONObject(i);



                    if (objArray.getBoolean("colaborador")){
                        Log.println(Log.DEBUG, "RESPOSTA CODIGO", "DENTRO DO COLABORADOR");
                    }
*/
/*
                    String token;
                    String name;
                    String br;
                    String empresa;
                    String regiao;
                    String tiporegistro;

                    error = objArray.getString(ERROR);
                    token = objArray.getString(TOKEN);
                    name = objArray.getString(NOME);
                    br = objArray.getString(BR);
                    empresa = objArray.getString(EMPRESA);
                    regiao = objArray.getString(REGIAO);
                    tiporegistro = objArray.getString(TIPOREGISTRO);

                    Log.i("VERIFICAERROR", "Verificando a variavel error:" + error);

                    UserColaboradorModel guardaUserColaboradorModel = new UserColaboradorModel();

                    userColaboradorModel.setMensagemStatus(error);
                    guardaUserColaboradorModel.setCodigo(1);
                    guardaUserColaboradorModel.setToken(token);
                    guardaUserColaboradorModel.setNome(name);
                    guardaUserColaboradorModel.setBR(br);
                    guardaUserColaboradorModel.setEmpresa(empresa);
                    guardaUserColaboradorModel.setRegiao(regiao);
                    guardaUserColaboradorModel.setTiporegistro(tiporegistro);


                    new UserColaboradorRepository(context).SalvarUser(guardaUserColaboradorModel);
                    Log.println(Log.INFO, "SALVO: ", "Dados Salvo");
*/

//                }

            }

            //return userColaboradorModel;
            return userColaboradorModel;

        } catch (JSONException e) {
            //Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
            Log.e("ERROR NO JSON", "JSON INFORMACOES:", e);
            e.printStackTrace();
            return null;
        }

    }





    //VALIDA TOKEN DE ACESSO
    public TokenAcessModel ValidaTokenAcesso(final JSONObject data) throws IOException {
        TokenAcessModel tokenAcessModel;
        TokenAcessRepository tokenAcessRepository = new TokenAcessRepository(context);

        tokenAcessModel = tokenAcessRepository.CarregaDadosToken(1);
        String acessoToken = tokenAcessModel.getDadosToken();


        TokenAcessModel retorno;

        Response response;
        try {

            final RequestBody body = RequestBody
                    .create(MediaType.parse("application/json"), data.toString());
            final Request request = new Request.Builder()
                    .url(host_desenvolvimento + "/validatoken")
                    .post(body)
                    .addHeader("Accept", "application/json").addHeader("Authorization","Bearer "+acessoToken)
                    .build();

            final OkHttpClient client = new OkHttpClient();


            response = client.newCall(request).execute();


            Log.println(Log.DEBUG, "RESPOSTA CODIGO", "HTTP:" + response.code());

            if (response.code()==404){

                retorno = RetornaParseJsonValidaTokenAcessoCODIGO("404");

                return retorno;
            }


            String json = response.body().string();
            Log.i("Valores", "Json:" + json);
            retorno = RetornaParseJsonValidaTokenAcesso(json);

            return retorno;

        } catch (IOException e) {
            //Uteis.AlertLogin(context,"Sem conexão com o servidor");
            //retorno = new TokenAcessModel();

            //retorno.setMensagemStatus("0");

            //retornoCode.setRetorno(1);
            // Log.e("RESPOSTA CODIGO", "HTTP:" + response.code());
            Log.e("ERROR", "ERROR DE CONEXÃO LOGIN NA CLASSE WEB CLIENT: Sem Conexão", e);


            //;Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show()

            //return retorno;

        }
        return null;
    }

    private TokenAcessModel RetornaParseJsonValidaTokenAcesso(String json) {
        final String ERROR = "error";
        final String SUCESS = "success";

        try {
            TokenAcessModel tokenAcessModel = new TokenAcessModel();

            JSONObject jsonObj = new JSONObject(json);
            JSONArray array = jsonObj.getJSONArray("results");

            if (array.length() != 0) {
                for (int i = 0; i < array.length(); i++) {
                    JSONObject objArray = array.getJSONObject(i);

                    //String token;
                    String error;
                    String success;

                    //token = objArray.getString(TOKEN);
                    error = objArray.getString(ERROR);

                    tokenAcessModel = new TokenAcessModel();

                    tokenAcessModel.setError(error);
                    //verificaBR0Model.setMensagem(success);
                    //guardaUserColaboradorModel.setToken(token);
                    //relatorioLdFObjModel1.setReligador(rlg);
                    //relatorioLdFObjModel1.setAlimentador(alm);

                    try {
                        return tokenAcessModel;
                        //new RelatorioLDfRepository(context).SalvarRelatorio(relatorioLdFObjModel1);
                        //Log.println(Log.INFO, "SALVO: ", "Dados Salvo");

                    } catch (Exception e) {
                        Log.println(Log.ERROR, "NAO SALVO: ", "Dados não Salvo");

                    }


                }
            } else {
                //objModel_alm_rlg.setMsgError("ERROR");
                //userColaboradorModel.setMensagemError("Usario Nao encontrado");
            }

            return tokenAcessModel;


        } catch (JSONException e) {
            //Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
            Log.e("TEST", "JSON", e);
            e.printStackTrace();
            return null;
        }


    }

    private TokenAcessModel RetornaParseJsonValidaTokenAcessoCODIGO(String code) {
        final String ERROR = "error";
        final String CODIGO = "success";

        try {
            TokenAcessModel tokenAcessModel = new TokenAcessModel();

            if (code.equals("404")){
                tokenAcessModel.setCodiResposta("404");
                //verificaBR0Model.setMensagem(success);
                //guardaUserColaboradorModel.setToken(token);
                //relatorioLdFObjModel1.setReligador(rlg);
                //relatorioLdFObjModel1.setAlimentador(alm);

                try {
                    return tokenAcessModel;
                    //new RelatorioLDfRepository(context).SalvarRelatorio(relatorioLdFObjModel1);
                    //Log.println(Log.INFO, "SALVO: ", "Dados Salvo");

                } catch (Exception e) {
                    Log.println(Log.ERROR, "NAO SALVO: ", "Dados não Salvo");

                }
            }else{

            }



        } catch (Exception e) {
            //Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
            Log.e("TEST", "JSON", e);
            e.printStackTrace();
            return null;
        }
        return null;

    }

    //--------------END VALIDA TOKEN

    public String get() throws IOException {

        String url = "http://www.umsitequalquer.com.br/fazGet";

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(url).build();

        Response response = client.newCall(request).execute();

        String jsonDeResposta = response.body().string();

        return jsonDeResposta;
    }

}
