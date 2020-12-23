package com.enel_locdefalta.MVC.VIEW.LOGIN;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.enel_locdefalta.MVC.MODEL.Login.UserColaboradorModel;
import com.enel_locdefalta.MVC.SERVIDOR_e_BD.VerificaRede;
import com.enel_locdefalta.MVC.SERVIDOR_e_BD.WebClient;
import com.enel_locdefalta.MVC.VIEW.ACESSO_OFFILINE.AcessoOfflineActivity;
import com.enel_locdefalta.MVC.VIEW.HOME.HomeActivity;
import com.enel_locdefalta.R;
import com.enel_locdefalta.utils.Uteis;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import static android.os.StrictMode.setThreadPolicy;

public class LoginActivity extends AppCompatActivity {

    EditText user, password;
    Button acessar, acessarOffline;

    private ProgressDialog load;

    String CONS_BR;
    String CONS_SENHA;
    String NOME_COLABORADOR;
    String json, login;

    Intent intent;

    String CHANNEL_ID ="testeNEL";
    String CHANNEL_NAME="NOME DO TITULO";
    String CHANNEL_DESC="TEXTO DE DESCRICAO";

    FirebaseAuth mAuth;

    Uteis data = new Uteis();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.new_activity_login_novo_mobile);
        setContentView(R.layout.new_activity_login_novo_tablet);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        setThreadPolicy(policy);

        TextView datadispositivo = findViewById(R.id.textViewDataEnel);
        datadispositivo.setText("enel "+data.RetornaData());

        mAuth = FirebaseAuth.getInstance();

        final GetJsonLogin download = new GetJsonLogin();

         user =  findViewById(R.id.user_colaborador);
         password = findViewById(R.id.editTextSenha);

         user.setAutofillHints(View.AUTOFILL_HINT_USERNAME);

        acessar = findViewById(R.id.btn_acessar);

        acessar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CONS_BR = "BR0" + user.getText().toString().trim();
                CONS_SENHA = password.getText().toString().trim();

                if (user.getText().toString().trim().equals("")){
                    user.setError("Digite seu BR!");
                    gerarToast("Verfique os campos com alerta...");
                }else {
                    login = user.getText().toString().toUpperCase();
                    json = "{\"br\":\""+CONS_BR+"\",\"senha\":\""+CONS_SENHA+"\"}" ;
                    download.execute();

                    Toast.makeText(LoginActivity.this, "Aguarde a Verificação...", Toast.LENGTH_LONG).show();

                }
            }
        });
        acessarOffline = findViewById(R.id.buttonACESSOOFF);

        if(VerificaRede.isConnected(LoginActivity.this)){
            acessarOffline.setVisibility(View.INVISIBLE);
        }else{
            acessarOffline.setText(R.string.btnTextoUsarModoOffiline);
            acessarOffline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent = new Intent(LoginActivity.this, AcessoOfflineActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
            user.setEnabled(false);
            password.setEnabled(false);
            acessar.setEnabled(false);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription(CHANNEL_DESC);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel);
        }

        final TextView textView = findViewById(R.id.textViewDataEnel);

        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if (task.isSuccessful()){
                    String tokek = task.getResult().getToken();
                    Log.e("TOKENFIREBASE", "Token deste dispositivo: "+tokek);
                }else{
                    Log.e("TOKENFIREBASEERROR", "Erro no Token deste dispositivo");
                }
            }
        });

    }

    private class GetJsonLogin extends AsyncTask<Void, Void, UserColaboradorModel> {
        String retornoErro;
        UserColaboradorModel userColaboradorModel;
        String verificaCode;
        @Override
        protected void onPreExecute(){

            load = ProgressDialog.show(LoginActivity.this, " Aguarde...", "Verificando se o "+CONS_BR+", está " +
                    "em nosso servidor...");
        }

        @Override
        protected UserColaboradorModel doInBackground(Void... params) {
            WebClient webClient = new WebClient(getApplicationContext());

            if(VerificaRede.isConnected(LoginActivity.this)){
                try {
                    JSONObject login = new JSONObject(json);
                    Thread.sleep(2000);
                    userColaboradorModel = webClient.LOGIN(login);
                    if (userColaboradorModel.getMensagemStatus()!=null){
                        verificaCode = userColaboradorModel.getMensagemStatus();
                        Log.i("INFO","RETORNO ERROR: "+ verificaCode);
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
        protected void onPostExecute(UserColaboradorModel consultaLdFObjModel){

            load.dismiss();
            if (verificaCode.equals("0")){
                Toast.makeText(LoginActivity.this,"ATENÇÃO: O SERVIDOR ESTÁ OFFLINE. Nº do Error " + verificaCode, Toast.LENGTH_LONG).show();
                finish();
            }else if(verificaCode.equals("Senha incorreta")){
                Uteis.AlertLogin(LoginActivity.this,"Sua senha ou BR0 estão incorretos!");

                Toast.makeText(LoginActivity.this,"Senha Incorreta: Retorno:" + retornoErro, Toast.LENGTH_LONG).show();

            }else if(verificaCode.equals("O BR0 informado não existe!")){
                Toast.makeText(LoginActivity.this,"BR0 Não Existe: Retorno:" + retornoErro, Toast.LENGTH_LONG).show();
            }else{
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    intent.putExtra("BR0", CONS_BR);
                    intent.putExtra("NOME_COLABORADOR", NOME_COLABORADOR);
                    startActivity(intent);
                    finish();
            }
        }
    }

    private void gerarToast(String texto){
        Toast.makeText(this, texto, Toast.LENGTH_SHORT).show();
    }

}
