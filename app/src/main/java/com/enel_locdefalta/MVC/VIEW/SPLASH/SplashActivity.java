package com.enel_locdefalta.MVC.VIEW.SPLASH;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.enel_locdefalta.MVC.CONTROLLER.RELATORIO.RelatorioLDfRepository;
import com.enel_locdefalta.MVC.CONTROLLER.TOKEN.TokenAcessRepository;
import com.enel_locdefalta.MVC.MODEL.RELATORIO.RelatorioLdFObjModel;
import com.enel_locdefalta.MVC.SERVIDOR_e_BD.VerificaRede;
import com.enel_locdefalta.MVC.SERVIDOR_e_BD.WebClient;
import com.enel_locdefalta.MVC.VIEW.ACESSO_OFFILINE.AcessoOfflineActivity;
import com.enel_locdefalta.MVC.VIEW.AJUSTES.ConfiguracoesActivity;
import com.enel_locdefalta.MVC.VIEW.LOGIN.LoginActivity;
import com.enel_locdefalta.R;
import com.enel_locdefalta.TOKEN.TokenAcessModel;
import com.enel_locdefalta.utils.Uteis;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 4000;
    TokenAcessModel tokenAcessModel;

    private Handler handler = new Handler();
    ProgressBar progressBar;
    private int progressStatus = 0;

    TokenAcessRepository tokenAcessRepository;
    List<TokenAcessModel> tokenAcessModels =  new ArrayList<TokenAcessModel>();
    ProgressDialog load;
    String token, json;


    Uteis data = new Uteis();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_mobile);
        //setContentView(R.layout.activity_splash_tablet);

        TextView datadispositivo = findViewById(R.id.textViewDataEnel);
        datadispositivo.setText("enel "+data.RetornaData());

        progressBar = findViewById(R.id.progressBar);
        progresso();

        if(VerificaRede.isConnected(SplashActivity.this)){
            TokenAtivo();
        }else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent login  = new Intent(SplashActivity.this,  LoginActivity.class);
                    startActivity(login);
                    finish();
                }
            },SPLASH_TIME_OUT);
        }
    }



    private boolean CarregaDados_do_BancoTOKEN(){
        this.tokenAcessRepository = new TokenAcessRepository(SplashActivity.this);

        tokenAcessModels = tokenAcessRepository.SelecionarTodosOsTokens();
        String num = String.valueOf(tokenAcessModels.size());
        //Log.i("DADOSTOKEN", "JSON"+num);
        if (tokenAcessModels.isEmpty()){
            return true;
        }
        return false;
    }



    public void ResetToken(){
        this.tokenAcessRepository = new TokenAcessRepository(SplashActivity.this);
        this.tokenAcessRepository.ExcluirTOKEN(1);
    }


    private void TokenAtivo(){

        if (CarregaDados_do_BancoTOKEN()==true){
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }else{
            final SplashActivity.GetValidaToken download = new SplashActivity.GetValidaToken();
            TokenAcessModel tokenAcessModel;
            this.tokenAcessRepository = new TokenAcessRepository(SplashActivity.this);
            tokenAcessModel = tokenAcessRepository.CarregaUsuarioToken(1);

            String token = tokenAcessModel.getDadosToken();

            Log.d("TOKENRETORNADO",  token);
            json = "{\"token\":\""+token+"\"}" ;
            download.execute();
        }


    }

    private void progresso(){
        new Thread(new Runnable() {
            public void run() {
                while (progressStatus < 100) {
                    progressStatus += 1;
                    handler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(progressStatus);
                        }
                    });
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private class GetValidaToken extends AsyncTask<Void, Void, TokenAcessModel> {
        String retornoErro;
        String nomeT;
        TokenAcessModel tokenAcessModel;
        String verificaCode;

        @Override
        protected void onPreExecute(){
            load = ProgressDialog.show(SplashActivity.this, " Aguarde...", "Verificando seu token de acesso...");
            //Log.println(Log.INFO,"JSONS",json);
        }

        @Override
        protected TokenAcessModel doInBackground(Void... params) {

            WebClient webClient = new WebClient(getApplicationContext());

            if(VerificaRede.isConnected(SplashActivity.this)){
                try {
                    JSONObject validaToken = new JSONObject(json);
                    Thread.sleep(2000);
                    tokenAcessModel = webClient.ValidaTokenAcesso(validaToken);

                    if (tokenAcessModel.getError()!=null){
                        verificaCode = tokenAcessModel.getError();
                        //Log.i("INFO","RETORNO ERROR: "+ verificaCode);
                    }else{
                        verificaCode = "Nada";
                        //Log.i("INFO","RETORNO ERROR: "+ verificaCode);
                    }

                } catch (JSONException e) {
                    Log.e("ERROR", "JSONException: ->", e);
                } catch (IOException e) {
                    Log.e("ERROR", "Exception: ->", e);
                } catch (InterruptedException e) {
                    Log.e("ERROR", "Interruped Excption: ->", e);
                }

            }
            return null;
        }

        @Override
        protected void onPostExecute(TokenAcessModel tokenAcessModel){

            load.dismiss();

            if (verificaCode.equals("Token invalido") || verificaCode==null){
                ResetToken();
                Toast.makeText(SplashActivity.this,"Faça o login novamente: " + retornoErro, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }else{
                Intent intent = new Intent(SplashActivity.this, AcessoOfflineActivity.class);
                startActivity(intent);
                finish();
            }

        }
    }
}
