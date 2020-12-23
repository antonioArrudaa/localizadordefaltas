package com.enel_locdefalta.MVC.VIEW.AJUSTES;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.enel_locdefalta.MVC.CONTROLLER.AJUSTES.DOWNLOAD_ALM_E_RLG_Repository;
import com.enel_locdefalta.MVC.CONTROLLER.AJUSTES.DOWNLOAD_LDF_Repository;
import com.enel_locdefalta.MVC.CONTROLLER.RELATORIO.RelatorioLDfRepository;
import com.enel_locdefalta.MVC.MODEL.AJUSTES.ConsultaLdFObjModel;
import com.enel_locdefalta.MVC.MODEL.AJUSTES.ObjModel_ALM_RLG;
import com.enel_locdefalta.MVC.MODEL.RELATORIO.RelatorioLdFObjModel;
import com.enel_locdefalta.MVC.SERVIDOR_e_BD.VerificaRede;
import com.enel_locdefalta.MVC.SERVIDOR_e_BD.WebClient;
import com.enel_locdefalta.MVC.VIEW.ATIVIDADEREDE.NetworkOFF;
import com.enel_locdefalta.R;
import com.enel_locdefalta.utils.Uteis;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.os.StrictMode.setThreadPolicy;

public class ConfiguracoesActivity extends AppCompatActivity {

    private ProgressDialog load;
    Intent intent;

    String CONS_ARQUIVOALM;

    private TextView textViewProgressoBarDownload, infoURL, textView_MESSAGEM_CONFIRMACAO, textView_MENSAGEM_RELATORIO;
    private Button buttonDownloadLOCALIZADORES, buttonDownloadALM,buttonDownloadRLG, buttonTelaInicial, buttonDELETEDADOSRELATORIOS;

    public EditText editTextNomeAlimentador;

    private ImageView btn_voltar, imageViewDELETE_DADOS,imageViewFundoDeleteDados;
    private Handler handler = new Handler();
    Uteis alert = new Uteis();

    String json;

    //CRIANDO UMA LISTA DE DADOS
    List<ConsultaLdFObjModel> LISTA_DE_DADOS =  new ArrayList<ConsultaLdFObjModel>();
    List<ObjModel_ALM_RLG> LISTA_DE_ALM = new ArrayList<ObjModel_ALM_RLG>();
    List<ObjModel_ALM_RLG> LISTA_DE_RLG = new ArrayList<ObjModel_ALM_RLG>();

    //CIRANDO UM OBJETO DA NOSSA CLASSE QUE FAZ ACESSO AO BANCO DE DADOS
    DOWNLOAD_LDF_Repository DOWNLOADLDFRepository;
    DOWNLOAD_ALM_E_RLG_Repository download_alm_e_rlg_repository;


    /*BANCO DE RELATORIOS*/
    RelatorioLDfRepository relatorioLDfRepository;
    List<RelatorioLdFObjModel> LISTA_DE_DADOS_RELATORIOS =  new ArrayList<RelatorioLdFObjModel>();

    String num;

    Uteis data = new Uteis();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_ajustes_app_enel_tablet);
        //setContentView(R.layout.new_ajustes_app_enel_mobile);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        setThreadPolicy(policy);


        TextView datadispositivo = findViewById(R.id.textViewDataEnel);
        datadispositivo.setText("enel "+data.RetornaData());


        if (VerificaRede.isConnected(ConfiguracoesActivity.this)){
            CarregaComponentes();

            CarregaAcoesComponentes();

            AcoesDadosLDF();
            AcoesDadosRELATORIOS();
        }else{
            TelaSemConexao();
        }

    }

    private void TelaSemConexao(){
        intent = new Intent(ConfiguracoesActivity.this, NetworkOFF.class);
        startActivity(intent);
        finish();
    }


    private void CarregaComponentes(){
        btn_voltar = findViewById(R.id.btn_configuracoes_voltar);
        buttonTelaInicial = findViewById(R.id.buttonTelaInicial);

        buttonDELETEDADOSRELATORIOS = findViewById(R.id.buttonDELETERELATORIOS);
        textView_MENSAGEM_RELATORIO = findViewById(R.id.textView_Mensagem_Dados_RELATORIOS);
        textView_MENSAGEM_RELATORIO.setVisibility(View.INVISIBLE);

        editTextNomeAlimentador = (EditText) findViewById(R.id.editTextNomeAlimentador);
        buttonDownloadLOCALIZADORES = findViewById(R.id.buttonDownloadLOCALIZADORES);
        buttonDownloadLOCALIZADORES.setVisibility(View.INVISIBLE);
        buttonDownloadALM = findViewById(R.id.buttonDownloadALM);
        buttonDownloadALM.setVisibility(View.VISIBLE);
        buttonDownloadRLG = findViewById(R.id.buttonDownloadRLG);
        buttonDownloadRLG.setVisibility(View.INVISIBLE);
        textView_MESSAGEM_CONFIRMACAO = findViewById(R.id.textView_Mensagem_Dados_Baixado);
        textView_MESSAGEM_CONFIRMACAO.setVisibility(View.INVISIBLE);
        imageViewDELETE_DADOS = findViewById(R.id.imageViewButton_DELETE_DADOS);
        imageViewDELETE_DADOS.setEnabled(false);
        imageViewDELETE_DADOS.setVisibility(View.INVISIBLE);
        imageViewFundoDeleteDados = findViewById(R.id.imageView31);
        imageViewFundoDeleteDados.setVisibility(View.INVISIBLE);
    }

    private void CarregaAcoesComponentes(){
        final GetJsonDowloadLDF downloadLOCALIZADORES = new GetJsonDowloadLDF();
        final GetJsonDowloadRLG dowloadRELIGADORES = new GetJsonDowloadRLG();
        final GetJsonDowloadALM dowloadALIMENTADORES = new GetJsonDowloadALM();


        buttonDownloadALM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (VerificaRede.isConnected(ConfiguracoesActivity.this)){
                    if (editTextNomeAlimentador.getText().toString().equals("")){
                        editTextNomeAlimentador.setError("INSIRA O NOME DO ALIMENTADOR.");
                    }else {
                        CONS_ARQUIVOALM = editTextNomeAlimentador.getText().toString().toUpperCase().trim();
                        json = "{\"alimentador\":\""+CONS_ARQUIVOALM+"\"}";
                        dowloadALIMENTADORES.execute();
                        //  progresso();
                        Toast.makeText(ConfiguracoesActivity.this, "Baixando Alimentador, Aguarde a conclusão...", Toast.LENGTH_LONG).show();
                        Toast.makeText(ConfiguracoesActivity.this, "Concluído!", Toast.LENGTH_LONG).show();
                        //editTextNomeAlimentador.setText("");
                        buttonDownloadRLG.setVisibility(View.VISIBLE);
                        buttonDownloadALM.setVisibility(View.INVISIBLE);
                        buttonDownloadLOCALIZADORES.setVisibility(View.INVISIBLE);
                    }
                }else{
                    alert.Alert(ConfiguracoesActivity.this,"Informe o nome do arquivo.json");
                }
            }
        });

        buttonDownloadRLG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (VerificaRede.isConnected(ConfiguracoesActivity.this)){
                    if (editTextNomeAlimentador.getText().toString().equals("")){
                        editTextNomeAlimentador.setError("INSIRA O NOME DO ALIMENTADOR.");
                    }else {
                        CONS_ARQUIVOALM = editTextNomeAlimentador.getText().toString().toUpperCase().trim();
                        json = "{\"alimentador\":\""+CONS_ARQUIVOALM+"\"}";
                        dowloadRELIGADORES.execute();
                        //  progresso();
                        Toast.makeText(ConfiguracoesActivity.this, "Baixando Religadores, Aguarde a conclusão...", Toast.LENGTH_SHORT).show();
                        buttonDownloadRLG.setVisibility(View.INVISIBLE);
                        buttonDownloadALM.setVisibility(View.INVISIBLE);
                        buttonDownloadLOCALIZADORES.setVisibility(View.VISIBLE);

                    }
                }else{
                    alert.Alert(ConfiguracoesActivity.this,"Informe o nome do arquivo.json");
                }
            }
        });

        final int num= 1;
        buttonDownloadLOCALIZADORES.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (VerificaRede.isConnected(ConfiguracoesActivity.this)){
                    if (editTextNomeAlimentador.getText().toString().equals("")){
                        editTextNomeAlimentador.setError("INSIRA O NOME DO ALIMENTADOR.");
                    }else {
                        CONS_ARQUIVOALM = editTextNomeAlimentador.getText().toString().toUpperCase().trim();
                        json = "{\"alimentador\":\""+CONS_ARQUIVOALM+"\"}";
                        downloadLOCALIZADORES.execute();
                        //  progresso();
                        Toast.makeText(ConfiguracoesActivity.this, "Baixando Localizadores, Aguarde a conclusão...", Toast.LENGTH_LONG).show();
                        Toast.makeText(ConfiguracoesActivity.this, "Concluído!", Toast.LENGTH_LONG).show();
                        editTextNomeAlimentador.setText("");
                        editTextNomeAlimentador.setEnabled(false);
                        buttonDownloadRLG.setVisibility(View.INVISIBLE);
                        buttonDownloadALM.setVisibility(View.INVISIBLE);
                        buttonDownloadLOCALIZADORES.setVisibility(View.INVISIBLE);
                        textView_MESSAGEM_CONFIRMACAO.setVisibility(View.VISIBLE);
                    }
                }else{
                    alert.Alert(ConfiguracoesActivity.this,"Informe o nome do arquivo.json");
                }
            }
        });

        imageViewDELETE_DADOS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(ConfiguracoesActivity.this);

                //ADICIONANDO UM TITULO A NOSSA MENSAGEM DE ALERTA
                alertDialog.setTitle("TEM CERTEZA?");

                //MENSAGEM A SER EXIBIDA
                alertDialog.setMessage("Colaborador, Você deseja apagar todos os dados sobre ALM, RLG e LDF's presentes neste dispositivo?");

                //Icone
                alertDialog.setIcon(R.drawable.ic_action_delete);

                //CRIA UM BOTÃO COM O TEXTO OK SEM AÇÃO
                alertDialog.setPositiveButton("Tenho Certeza", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ResetBancoLDF();
                        Toast.makeText(ConfiguracoesActivity.this, "APAGANDO...", Toast.LENGTH_SHORT).show();
                        Toast.makeText(ConfiguracoesActivity.this, "OS DADOS FORAM APAGADOS.", Toast.LENGTH_LONG).show();
                        editTextNomeAlimentador.setEnabled(true);
                        buttonDownloadALM.setVisibility(View.VISIBLE);
                    }
                });
                alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(ConfiguracoesActivity.this, "OPERAÇÃO CANCELADA.", Toast.LENGTH_LONG).show();
                    }
                });

                //MOSTRA A MENSAGEM NA TELA
                alertDialog.show();

            }
        });

        buttonDELETEDADOSRELATORIOS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(ConfiguracoesActivity.this);

                //ADICIONANDO UM TITULO A NOSSA MENSAGEM DE ALERTA
                alertDialog.setTitle("TEM CERTEZA?");

                //MENSAGEM A SER EXIBIDA
                alertDialog.setMessage("Colaborador, Você deseja apagar todos os dados sobre RELATÓRIOS presentes neste dispositivo?");

                //Icone
                alertDialog.setIcon(R.drawable.ic_action_delete);

                //CRIA UM BOTÃO COM O TEXTO OK SEM AÇÃO
                alertDialog.setPositiveButton("Sim, Tenho Certeza", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ResetBancoRELATORIOS();
                        Toast.makeText(ConfiguracoesActivity.this, "APAGANDO...", Toast.LENGTH_SHORT).show();
                        Toast.makeText(ConfiguracoesActivity.this, "RELATÓRIOS APAGADOS!", Toast.LENGTH_LONG).show();
                        buttonDELETEDADOSRELATORIOS.setVisibility(View.INVISIBLE);
                        textView_MENSAGEM_RELATORIO.setVisibility(View.VISIBLE);
                    }
                });
                alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(ConfiguracoesActivity.this, "OPERAÇÃO CANCELADA.", Toast.LENGTH_LONG).show();
                    }
                });

                //MOSTRA A MENSAGEM NA TELA
                alertDialog.show();


            }
        });

        btn_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonTelaInicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    private void AcoesDadosRELATORIOS(){
        if (CarregaDados_do_BancoRELATORIOS()==true){
            Toast.makeText(ConfiguracoesActivity.this, "RELATÓRIOS NÃO CADASTRADOS!", Toast.LENGTH_LONG).show();
            buttonDELETEDADOSRELATORIOS.setVisibility(View.INVISIBLE);
            textView_MENSAGEM_RELATORIO.setVisibility(View.VISIBLE);
        }else{
            buttonDELETEDADOSRELATORIOS.setVisibility(View.VISIBLE);
        }

    }

    private boolean CarregaDados_do_BancoRELATORIOS(){
        this.relatorioLDfRepository = new RelatorioLDfRepository(ConfiguracoesActivity.this);
        this.LISTA_DE_DADOS_RELATORIOS = relatorioLDfRepository.SelecionarTodos();
        num = String.valueOf(this.LISTA_DE_DADOS_RELATORIOS.size());

        Log.i("DADOSRELATORIOS", "JSON"+num);
        if (LISTA_DE_DADOS_RELATORIOS.isEmpty()){
            return true;
        }
        return false;
    }

    private void ResetBancoRELATORIOS(){
        this.relatorioLDfRepository = new RelatorioLDfRepository(ConfiguracoesActivity.this);
        this.relatorioLDfRepository.ExcluirBD_COMPLETO_RELATORIOS();

        this.LISTA_DE_DADOS_RELATORIOS.clear();
        CarregaDados_do_BancoRELATORIOS();
    }








    private void AcoesDadosLDF(){
        if (CarregaDados_do_BancoLDF()==true){
            alert.AlertaDialogo(ConfiguracoesActivity.this,"ATENÇÃO COLABORADOR:","Não foi possível encontrar dados neste dispositivo" +
                    " para realizar consultas offline!",R.drawable.ic_action_download,"Ok","");
            buttonDownloadALM.setVisibility(View.VISIBLE);
        }else{
            imageViewDELETE_DADOS.setVisibility(View.VISIBLE);
            imageViewDELETE_DADOS.setEnabled(true);
            imageViewFundoDeleteDados.setVisibility(View.VISIBLE);
            editTextNomeAlimentador.setEnabled(false);
            buttonDownloadRLG.setVisibility(View.INVISIBLE);
            buttonDownloadALM.setVisibility(View.INVISIBLE);
            buttonDownloadLOCALIZADORES.setVisibility(View.INVISIBLE);
            textView_MESSAGEM_CONFIRMACAO.setVisibility(View.VISIBLE);
            Toast.makeText(ConfiguracoesActivity.this, "Um total de "+ num+ " dados foram carregados." , Toast.LENGTH_LONG).show();
        }
    }

    private void ResetBancoLDF(){
        this.DOWNLOADLDFRepository = new DOWNLOAD_LDF_Repository(ConfiguracoesActivity.this);
        this.DOWNLOADLDFRepository.ExcluirBD_COMPLETO_LDF();
        this.LISTA_DE_DADOS.clear();
        this.download_alm_e_rlg_repository = new DOWNLOAD_ALM_E_RLG_Repository(ConfiguracoesActivity.this);
        this.download_alm_e_rlg_repository.ExcluirBD_COMPLETO_RLG();
        this.download_alm_e_rlg_repository.ExcluirBD_COMPLETO_ALM();
        this.LISTA_DE_ALM.clear();
        this.LISTA_DE_RLG.clear();
        CarregaDados_do_BancoLDF();
    }


    private boolean CarregaDados_do_BancoLDF(){
        this.DOWNLOADLDFRepository = new DOWNLOAD_LDF_Repository(ConfiguracoesActivity.this);
        this.LISTA_DE_DADOS = DOWNLOADLDFRepository.SelecionarTodos();
        num = String.valueOf(this.LISTA_DE_DADOS.size());

        Log.i("DADOS", "JSON"+num);
        if (LISTA_DE_DADOS.isEmpty()){
            return true;
        }
       return false;
    }



    private class GetJsonDowloadLDF extends AsyncTask<Void, Void, ConsultaLdFObjModel> {
        String retorna_error=null;
        ConsultaLdFObjModel consultaLdFObjModel;
        @Override
        protected void onPreExecute(){
            load = ProgressDialog.show(ConfiguracoesActivity.this, "Por favor Aguarde ...", "Baixando dados dos LDF's do Servidor...");
            Log.println(Log.INFO,"JSONS",json);
            //textViewProgressoBarDownload.setVisibility(View.VISIBLE);
        }

        @Override
        protected ConsultaLdFObjModel doInBackground(Void... params) {
            WebClient webClient = new WebClient(getApplicationContext());

            if(VerificaRede.isConnected(ConfiguracoesActivity.this)){
                try {
                    JSONObject ALM = new JSONObject(json);

                    consultaLdFObjModel = webClient.CARREGA_LDF_POR_ALM(ALM);

                    if (consultaLdFObjModel.getMensagemERROR()!=null){
                        return consultaLdFObjModel;
                    }else{
                        retorna_error = consultaLdFObjModel.getMensagemERROR();
                        Log.println(Log.INFO, "ERROR: ", "->"+ retorna_error);
                        return null;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
                //return util.getInformacao("https://sistemasaa.000webhostapp.com/"+nomeArquivoALM+".json");
            }
            return null;
        }

        @Override
        protected void onPostExecute(ConsultaLdFObjModel consultaLdFObjModel){

            load.dismiss();
            if (retorna_error=="VAZIO"){
                Uteis.Alert(ConfiguracoesActivity.this,"ERROR");
                textViewProgressoBarDownload.setVisibility(View.INVISIBLE);
            }else{
                alert.AlertaDialogo(ConfiguracoesActivity.this,"SUCESSO!","Os dados dos LDF's ja foram Baixados!" +
                        " para realizar consultas offline!",R.drawable.ic_action_download,"Ok","");
//                textViewProgressoBarDownload.setVisibility(View.INVISIBLE);
            }

        }
    }

    private class GetJsonDowloadALM extends AsyncTask<Void, Void, ObjModel_ALM_RLG> {
        String retorna_error=null;
        ObjModel_ALM_RLG objModel_alm_rlg;
        @Override
        protected void onPreExecute(){
            load = ProgressDialog.show(ConfiguracoesActivity.this, "Por favor Aguarde ...", "Baixando dados dos ALM's do Servidor...");
            Log.println(Log.INFO,"JSONS",json);
            //textViewProgressoBarDownload.setVisibility(View.VISIBLE);
        }

        @Override
        protected ObjModel_ALM_RLG doInBackground(Void... params) {
            WebClient webClient = new WebClient(getApplicationContext());

            if(VerificaRede.isConnected(ConfiguracoesActivity.this)){
                try {
                    JSONObject ALM = new JSONObject(json);

                    objModel_alm_rlg = webClient.Post_ALM(ALM);

                    if (objModel_alm_rlg.getMsgError()!=null){
                        retorna_error = objModel_alm_rlg.getMsgError();
                        Log.println(Log.INFO, "configuracaoERROR: ", retorna_error);
                        return objModel_alm_rlg;
                    }else{
                        retorna_error = "Sucess";

                        Log.println(Log.INFO, "ERROR: ", "ESTA->"+retorna_error);
                        return null;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
                //return util.getInformacao("https://sistemasaa.000webhostapp.com/"+nomeArquivoALM+".json");
            }
            return null;
        }

        @Override
        protected void onPostExecute(ObjModel_ALM_RLG objModel_alm_rlg){

            load.dismiss();
            if (retorna_error.equals("Alimentador não encontrado")){
                Uteis.Alert(ConfiguracoesActivity.this,"ATENÇÃO: O Alimentador não se encontra em nosso servidor!");
                //textViewProgressoBarDownload.setVisibility(View.INVISIBLE);
            }else{
                alert.AlertaDialogo(ConfiguracoesActivity.this,"SUCESSO!","Os dados do ALM ja foram Baixados!" +
                        " para realizar consultas offline!",R.drawable.ic_action_download,"Ok","");
            }

        }
    }



    private class GetJsonDowloadRLG extends AsyncTask<Void, Void, ObjModel_ALM_RLG> {
        String retorna_error=null;
        ObjModel_ALM_RLG objModel_alm_rlg;
        @Override
        protected void onPreExecute(){
            load = ProgressDialog.show(ConfiguracoesActivity.this, "Por favor Aguarde ...", "Baixando dados dos RLG's do Servidor...");
            Log.println(Log.INFO,"JSONS",json);
//            textViewProgressoBarDownload.setVisibility(View.VISIBLE);
        }

        @Override
        protected ObjModel_ALM_RLG doInBackground(Void... params) {
            WebClient webClient = new WebClient(getApplicationContext());

            if(VerificaRede.isConnected(ConfiguracoesActivity.this)){
                try {
                    JSONObject ALM = new JSONObject(json);

                    objModel_alm_rlg = webClient.Post_RLG(ALM);

                    if (objModel_alm_rlg.getMsgError()!="VAZIO"){
                        return objModel_alm_rlg;
                    }else{
                        retorna_error = objModel_alm_rlg.getMsgError();
                        Log.println(Log.INFO, "ERROR: ", retorna_error);
                        return null;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
                //return util.getInformacao("https://sistemasaa.000webhostapp.com/"+nomeArquivoALM+".json");
            }
            return null;
        }

        @Override
        protected void onPostExecute(ObjModel_ALM_RLG objModel_alm_rlg){

            load.dismiss();
            if (retorna_error=="VAZIO"){
                Uteis.Alert(ConfiguracoesActivity.this,"ERROR");
                //textViewProgressoBarDownload.setVisibility(View.INVISIBLE);
            }else{
                alert.AlertaDialogo(ConfiguracoesActivity.this,"SUCESSO!","Os dados dos RLG's ja foram Baixados!" +
                        " para realizar consultas offline!",R.drawable.ic_action_download,"Ok","");
//                textViewProgressoBarDownload.setVisibility(View.INVISIBLE);
            }

        }
    }

}
