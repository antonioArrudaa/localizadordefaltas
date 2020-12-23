package com.enel_locdefalta.MVC.VIEW.AJUSTES;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.enel_locdefalta.MVC.CONTROLLER.AJUSTES.AJUSTES_INICIAL_CONTROLLER;
import com.enel_locdefalta.MVC.MODEL.AJUSTES.AjusteInicialModel;
import com.enel_locdefalta.MVC.VIEW.LOGIN.LoginActivity;
import com.enel_locdefalta.R;
import com.enel_locdefalta.utils.Uteis;

import java.util.ArrayList;
import java.util.List;

import static android.os.StrictMode.setThreadPolicy;

public class ConfiguracaoInicial extends AppCompatActivity {

    private ProgressDialog load;
    Intent intent;

    String CONS_ARQUIVOALM;

    private ImageView imageViewCLose;
    private Button buttonSalvarAjustesIniciais;

    public EditText editTextTextHost;


    private Handler handler = new Handler();
    Uteis alert = new Uteis();

    String json;

    //CRIANDO UMA LISTA DE DADOS
    List<AjusteInicialModel> CONFIG_INICIAL =  new ArrayList<AjusteInicialModel>();

    //CIRANDO UM OBJETO DA NOSSA CLASSE QUE FAZ ACESSO AO BANCO DE DADOS
    AJUSTES_INICIAL_CONTROLLER ajustes_inicial_controller;
    AjusteInicialModel ajusteInicialModel = new AjusteInicialModel();

    String num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracao_inicial);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        setThreadPolicy(policy);


            CarregaComponentes();

            CarregaAcoesComponentes();

    }


    private void CarregaComponentes(){
        imageViewCLose = findViewById(R.id.imageViewCLose);
        buttonSalvarAjustesIniciais = findViewById(R.id.buttonSalvarAjustesIniciais);

        editTextTextHost = (EditText) findViewById(R.id.editTextTextHost);
    }

    private void CarregaAcoesComponentes() {
      //  final ConfiguracoesActivity.GetJsonDowloadLDF downloadLOCALIZADORES = new ConfiguracoesActivity.GetJsonDowloadLDF();
       // final ConfiguracoesActivity.GetJsonDowloadRLG dowloadRELIGADORES = new ConfiguracoesActivity.GetJsonDowloadRLG();
       // final ConfiguracaoInicial.ArmazenaConfigInicial amazenaConfiguracoes = new ConfiguracaoInicial.ArmazenaConfigInicial();


        buttonSalvarAjustesIniciais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextTextHost.getText().toString().equals("")) {
                    editTextTextHost.setError("INSIRA O HOST.");
                } else {
                    json = editTextTextHost.toString().trim();
                    ajusteInicialModel.setHost(json);
                    ajusteInicialModel.setStatus("1");
                    ajusteInicialModel.setId(1);

                    new AJUSTES_INICIAL_CONTROLLER(ConfiguracaoInicial.this).Salvar_AjustesIniciais(ajusteInicialModel);

                    intent = new Intent(ConfiguracaoInicial.this, LoginActivity.class);
                    finish();
                    //amazenaConfiguracoes.execute();
                }
            }
        });

        imageViewCLose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


}