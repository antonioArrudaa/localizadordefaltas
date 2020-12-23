package com.enel_locdefalta.MVC.VIEW.ACESSO_OFFILINE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.enel_locdefalta.MVC.CONTROLLER.Login_e_Usuario.UserColaboradorRepository;
import com.enel_locdefalta.MVC.MODEL.Login.UserColaboradorModel;
import com.enel_locdefalta.MVC.SERVIDOR_e_BD.VerificaRede;
import com.enel_locdefalta.MVC.VIEW.LOGIN.LoginActivity;
import com.enel_locdefalta.R;
import com.enel_locdefalta.utils.Uteis;

import java.util.List;

public class AcessoOfflineActivity extends AppCompatActivity {

    ImageButton pesquisaBR;
    ImageView close;
    TextView msgError, textViewTituloModoOffiline;
    ProgressBar progressBarUser;
    ListView listViewColaborador;

    String BR0;
    UserColaboradorRepository userColaboradorRepository =  new UserColaboradorRepository(this);
    private List<UserColaboradorModel> userColaboradorModelList = null;
    private LinhaAdapterDadosColaboradores linhaAdapterDadosColaboradores;

    Uteis data = new Uteis();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acesso_offline_tablet);
        //setContentView(R.layout.activity_acesso_offline_mobile);

        TextView datadispositivo = findViewById(R.id.textViewDataEnel);
        datadispositivo.setText("enel "+data.RetornaData());


        textViewTituloModoOffiline = findViewById(R.id.textViewTituloModoOffiline);


        if(VerificaRede.isConnected(AcessoOfflineActivity.this)){
            textViewTituloModoOffiline.setText(R.string.tituloTelaModoValidaToken);

        }else{
            textViewTituloModoOffiline.setText(R.string.tituloTelaModoOffiline);
        }

        pesquisaBR = findViewById(R.id.ButtonPesquisarBRColaborador);
        close = findViewById(R.id.ButtonClose);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AcessoOfflineActivity.this,"Encerrando...",Toast.LENGTH_LONG);
                try {
                    Thread.sleep(3000);
                    finish();
                }catch (Exception e){

                }
            }
        });

        msgError = findViewById(R.id.textViewMensagemError);
        msgError.setVisibility(View.INVISIBLE);

        final EditText pegaBR = findViewById(R.id.editTextBR);

        progressBarUser = findViewById(R.id.progressBarCarregaUser);
        progressBarUser.setVisibility(View.INVISIBLE);

        pesquisaBR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (pegaBR.getText().toString().equals("")){
                    pegaBR.setError("Informe o BR0");
                }else {
                    BR0 ="BR0"+ pegaBR.getText().toString();
                    Loader loader = new Loader(AcessoOfflineActivity.this);
                    loader.execute();
                    Log.i("BRO",BR0);
                }
            }
        });

    }


    public class Loader extends AsyncTask<Void, Void, Void> {
        AcessoOfflineActivity acessoOfflineActivity;

        public Loader(AcessoOfflineActivity acessoOfflineActivity) {
            // recupero a instancia da nossa Activity
            this.acessoOfflineActivity = acessoOfflineActivity;

        }

        @Override
        protected void onPreExecute() {
            progressBarUser.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {

                Thread.sleep(1000); // simulando uma operacao demorada
                userColaboradorModelList = userColaboradorRepository.CarregaUsuario(BR0);
                listViewColaborador = findViewById(R.id.list_br_colaborador);
                linhaAdapterDadosColaboradores = new LinhaAdapterDadosColaboradores(AcessoOfflineActivity.this,userColaboradorModelList);

            } catch (Exception e) {

            }
            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            if (userColaboradorModelList ==null){
                msgError.setText("Colaborador não encontrado!");
                Toast.makeText(acessoOfflineActivity,"Não encontramos o BR0"+BR0+" informado.", Toast.LENGTH_LONG).show();

                progressBarUser.setVisibility(View.INVISIBLE);
            }else {
                listViewColaborador.setAdapter(linhaAdapterDadosColaboradores);
                msgError.setText("");
            }
            progressBarUser.setVisibility(View.GONE);
        }

    }




}
