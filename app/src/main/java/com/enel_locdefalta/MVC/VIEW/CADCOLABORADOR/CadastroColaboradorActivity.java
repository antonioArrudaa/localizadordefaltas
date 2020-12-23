package com.enel_locdefalta.MVC.VIEW.CADCOLABORADOR;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.enel_locdefalta.MVC.MODEL.Login.UserColaboradorModel;
import com.enel_locdefalta.MVC.SERVIDOR_e_BD.VerificaRede;
import com.enel_locdefalta.MVC.SERVIDOR_e_BD.WebClient;
import com.enel_locdefalta.R;
import com.enel_locdefalta.utils.Uteis;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import static android.os.StrictMode.setThreadPolicy;


public class CadastroColaboradorActivity extends AppCompatActivity {
    Intent intent;

    ImageView btn_voltar, imageViewVISUALIZARSENHA;

    EditText editTextNomeColaborador, editTextBrColaborador, editTextEmpresaColaborador, editTextCidadeColaborador, editTextSenhaColaborador,
            editTextConfirmaSenhaColaborador, editTextChaveDispositivoColaborador, editTextTipoRegistro,editTextUF;

    Button buttonObterChave, buttonSalvar, buttonTELAinicial;

    ProgressDialog load;
    ProgressBar progressBarUpLoadDeDados;

    String nomeColaborador, empresaColaborador, cidadeColaborador, senhaColaborador,
            chaveDispositivoColaborador, tipoRegistroColaborador, UF, json, senha, confirmesenha, BR0NOVO;

    Switch SwiASwitchENEL, SwiASwitchPARCEIRA, SwiASwitchCOLABORADOR, SwiASwitchADM;

    Uteis data = new Uteis();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_colaborador_tablet);
        //setContentView(R.layout.activity_cadastro_colaborador_mobile);
        TextView datadispositivo = findViewById(R.id.textViewDataEnel);
        datadispositivo.setText("enel "+data.RetornaData());

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        setThreadPolicy(policy);

        intent = getIntent();

        BR0NOVO = intent.getStringExtra("BR0NOVO");

        final GetJsonCadastroColaborador upload = new GetJsonCadastroColaborador();

        GeraComponents();

        SwiASwitchENEL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SwiASwitchENEL.isChecked()) {
                    SwiASwitchPARCEIRA.setChecked(false);
                    empresaColaborador = "ENEL";
                    Toast.makeText(CadastroColaboradorActivity.this, "Empresa do Colaborador: "+ empresaColaborador, Toast.LENGTH_LONG).show();
                }
            }
        });
        SwiASwitchPARCEIRA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SwiASwitchPARCEIRA.isChecked()) {
                    SwiASwitchENEL.setChecked(false);
                    empresaColaborador = "PARCEIRA";
                    Toast.makeText(CadastroColaboradorActivity.this, "Empresa do Colaborador: "+ empresaColaborador, Toast.LENGTH_LONG).show();
                }
            }
        });

        SwiASwitchCOLABORADOR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SwiASwitchCOLABORADOR.isChecked()) {
                    SwiASwitchADM.setChecked(false);
                    tipoRegistroColaborador = "1";
                    Toast.makeText(CadastroColaboradorActivity.this, "Tipo de usuario: "+ tipoRegistroColaborador, Toast.LENGTH_LONG).show();
                }
            }
        });
        SwiASwitchADM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SwiASwitchADM.isChecked()) {
                    SwiASwitchCOLABORADOR.setChecked(false);
                    tipoRegistroColaborador = "0";
                    Toast.makeText(CadastroColaboradorActivity.this, "Tipo de usuario: "+ tipoRegistroColaborador, Toast.LENGTH_LONG).show();
                }
            }
        });


        buttonSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                senha  = editTextSenhaColaborador.getText().toString();
                confirmesenha = editTextConfirmaSenhaColaborador.getText().toString();
                nomeColaborador = editTextNomeColaborador.getText().toString();
                UF = editTextUF.getText().toString();
                cidadeColaborador = editTextCidadeColaborador.getText().toString() + UF;
                senhaColaborador = editTextSenhaColaborador.getText().toString();
                if (editTextNomeColaborador.getText().toString().equals("") || editTextCidadeColaborador.getText().toString().equals("") ){
                    Toast.makeText(CadastroColaboradorActivity.this, "CAMPOS VAZIOS", Toast.LENGTH_LONG).show();
                }else{
                    if (!VerificaSenha()) {
                        Uteis.AlertDialogo(CadastroColaboradorActivity.this, "ERROR", "A senha númerica é diferente.");
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            Toast.makeText(CadastroColaboradorActivity.this, "SENHAS DIFERENTES!", Toast.LENGTH_LONG).show();
                        }
                    } else {


                        json = "{\"name\":\"" + nomeColaborador + "\",\"br\":\"" + BR0NOVO + "\"" +
                                ",\"empresa\":\"" + empresaColaborador + "\"" +
                                ",\"regiao\":\"" + cidadeColaborador + "\"" +
                                ",\"senha\":\"" + senhaColaborador + "\"" +
                                ",\"chave\":\"" + chaveDispositivoColaborador + "\"" +
                                ",\"tiporegistro\":\"" + tipoRegistroColaborador + "\"}";
                        upload.execute();
                    }
                }

                Log.e("JSON", "DADOS JSON: " + json);
            }
        });

        btn_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }



    private String ContaCaracter(EditText editText, int numero){
        InputFilter[] editFilters = editText.getFilters();
        InputFilter[] newFilters = new InputFilter[editFilters.length + 1];
        System.arraycopy(editFilters, 0, newFilters, 0, editFilters.length);
        newFilters[editFilters.length] = new InputFilter.LengthFilter(numero);
        editText.setFilters(newFilters);

        return editText.toString();
    }


    private boolean VerificaSenha(){
        if (!senha.equals(confirmesenha)){
            return false;
        }else{
            return true;
        }
    }

    private void GeraComponents() {
        btn_voltar = findViewById(R.id.imgViewC_btn_voltar);
        editTextNomeColaborador = findViewById(R.id.editTextNomeColaborador_TABLET);
        editTextBrColaborador = findViewById(R.id.editTextBrColaborador_TABLET);
        editTextBrColaborador.setText(BR0NOVO);
        editTextEmpresaColaborador = findViewById(R.id.editTextEmpresaColaborador_TABLET);
        editTextCidadeColaborador = findViewById(R.id.editTextRegiaoColaborador_TABLET);
        editTextSenhaColaborador = findViewById(R.id.editTextSenhaColaborador_TABLET);
        editTextConfirmaSenhaColaborador = findViewById(R.id.editTextConfirmaSenhaColaborador_TABLET);
        editTextUF = findViewById(R.id.editTextUF_TABLET);

        UF = ContaCaracter(editTextUF, 2);
        editTextChaveDispositivoColaborador = findViewById(R.id.editTextConfirmaChaveColaborador_TABLET);
        editTextTipoRegistro = findViewById(R.id.editTextTipoDeRegistroColaborador_TABLET);

        buttonSalvar = findViewById(R.id.buttonCADASTRA_TABLET);

        progressBarUpLoadDeDados = findViewById(R.id.progressBarEnvioDeDados);
        progressBarUpLoadDeDados.setVisibility(View.INVISIBLE);
        imageViewVISUALIZARSENHA = findViewById(R.id.imageViewVISUALIZARSENHA);

        //Swtch
        SwiASwitchADM = findViewById(R.id.switchADM);

        SwiASwitchCOLABORADOR = findViewById(R.id.switchCOLABORADOR);
        SwiASwitchCOLABORADOR.setEnabled(true);
        SwiASwitchCOLABORADOR.setChecked(false);

        SwiASwitchENEL = findViewById(R.id.switchENEL);
        SwiASwitchENEL.setEnabled(true);
        SwiASwitchENEL.setChecked(false);


        SwiASwitchPARCEIRA = findViewById(R.id.switchPARCEIRA);
        //SwiASwitchPARCEIRA.setEnabled(false);

        //Buuton
        buttonTELAinicial = findViewById(R.id.buttonTelaInicial);

        buttonTELAinicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonObterChave = findViewById(R.id.buttonObterChave);

        buttonObterChave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (task.isSuccessful()){
                            String tokek = task.getResult().getToken();
                            chaveDispositivoColaborador = tokek;
                            editTextChaveDispositivoColaborador.setText(chaveDispositivoColaborador);
                            //textView.setText(tokek);
                            Log.e("TOKENFIREBASE", "Token desta dispositivo: "+tokek);
                            Toast.makeText(CadastroColaboradorActivity.this, "Chave Obtida Com Sucesso!", Toast.LENGTH_LONG);
                        }else{
                            //textView.setText(task.getException().getMessage());
                            Toast.makeText(CadastroColaboradorActivity.this, "Chave Não Obtida. Entre em contato com Suporte do APP.", Toast.LENGTH_SHORT);
                        }
                    }
                });
            }
        });

    }


    private class GetJsonCadastroColaborador extends AsyncTask<Void, Void, Void> {
        String retorno;
        boolean estado = false;

        @Override
        protected void onPreExecute() {
            progressBarUpLoadDeDados.setVisibility(View.VISIBLE);

            load = ProgressDialog.show(CadastroColaboradorActivity.this, " Aguarde...", "Realizando cadastro do colaborador... " +
                    "em nosso servidor...");

            Log.println(Log.INFO, "JSONS", json);
        }

        @Override
        protected Void doInBackground(Void... params) {
            WebClient webClient = new WebClient(getApplicationContext());

            if (VerificaRede.isConnected(CadastroColaboradorActivity.this)) {
                try {
                    JSONObject dados = new JSONObject(json);
                    Thread.sleep(2000);
                    estado = webClient.POST_CADASTRO_COLABORADOR(dados);


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
        protected void onPostExecute(Void result) {

            load.dismiss();

            progressBarUpLoadDeDados.setVisibility(View.GONE);
            if (estado == true) {
                Uteis.AlertLogin(CadastroColaboradorActivity.this, "COLCABORADOR CADASTRADO COM SUCESSO!");
                Intent intent = new Intent(CadastroColaboradorActivity.this, SucessoCadastroActivity.class);
                intent.putExtra("NOME", nomeColaborador);
                intent.putExtra("BR0", BR0NOVO);
                intent.putExtra("EMPRESA", empresaColaborador);
                intent.putExtra("REGIÃO", cidadeColaborador);
                intent.putExtra("SENHA", senhaColaborador);
                intent.putExtra("TIPOREGISTRO", tipoRegistroColaborador);
                startActivity(intent);
                finish();
                Toast.makeText(CadastroColaboradorActivity.this, "Mensagem de retorno: " + retorno, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(CadastroColaboradorActivity.this, "Tudo errado: " + retorno, Toast.LENGTH_LONG).show();


            }

        }
    }

}
