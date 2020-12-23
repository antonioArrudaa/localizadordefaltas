package com.enel_locdefalta.MVC.VIEW.HOME;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.enel_locdefalta.InfoSobreAPPActivity;
import com.enel_locdefalta.MVC.CONTROLLER.ACOES.Funcoes;
import com.enel_locdefalta.MVC.CONTROLLER.Login_e_Usuario.UserColaboradorRepository;
import com.enel_locdefalta.MVC.MODEL.ATUALIZA_LDF.Att_LDF;
import com.enel_locdefalta.MVC.MODEL.Login.UserColaboradorModel;
import com.enel_locdefalta.MVC.MODEL.VerificaBR0Model;
import com.enel_locdefalta.MVC.SERVIDOR_e_BD.VerificaRede;
import com.enel_locdefalta.MVC.SERVIDOR_e_BD.WebClient;
import com.enel_locdefalta.MVC.VIEW.AJUSTES.ConfiguracoesActivity;
import com.enel_locdefalta.MVC.VIEW.ATIVIDADEREDE.NetworkOFF;
import com.enel_locdefalta.MVC.VIEW.ATUALIZAR_LDF.AtualizacaoLDFActivity;
import com.enel_locdefalta.MVC.VIEW.CADCOLABORADOR.CadastroColaboradorActivity;
import com.enel_locdefalta.MVC.VIEW.CONSULTALDF.ConsultarActivity;
import com.enel_locdefalta.MVC.VIEW.LOGIN.LoginActivity;
import com.enel_locdefalta.MVC.VIEW.RELATORIO.ListaDeRelatoriosActivity;
import com.enel_locdefalta.R;
import com.enel_locdefalta.utils.Uteis;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class HomeActivity extends AppCompatActivity {
    Button consultar, historico, cadastroColaborador, atualizacao_ldf;
    ImageView logout,info, imageViewStatusConexaoOn,imageViewStatusConexaoOff;
    View view;
    TextView texto, SetTextViewNomeColaborador, SetTextViewExit,SetTextViewBR,SetTextViewAjustes,SetTextViewHorario,SetTextViewData,SetTextViewConexao;

    Funcoes funcoes = new Funcoes();
    VerificaRede verificaRede = new VerificaRede();
    Intent intent;

    String BR0COLABORADOR, tipoRegistro, informacaoBR, informacaoToken, informacaoNome, json, BR0NOVO, CSI;

    ProgressBar progressBarBR0,progressBarD,progressBarH;
    Uteis data = new Uteis();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_board_mobile);
        setContentView(R.layout.activity_board_tablet);

        intent = getIntent();

        BR0COLABORADOR = intent.getStringExtra("BR0");

        CarregaComponentes();

        CarregaAcoesComponentes();

        VerificaAcoes();

        Loader loader = new Loader(HomeActivity.this);
        loader.execute();

        VerificaSensores();

    }

    private void CarregaComponentes(){
        consultar = findViewById(R.id.btn_consultar);
        historico = findViewById(R.id.btn_historico);
        cadastroColaborador = findViewById(R.id.btn_cadastro_colaborador);
        cadastroColaborador.setVisibility(View.INVISIBLE);
        atualizacao_ldf = findViewById(R.id.btn_atualiza_ldf);
        info = findViewById(R.id.btn_info);
        SetTextViewExit = findViewById(R.id.SetTtextViewExite);
        SetTextViewAjustes = findViewById(R.id.SetTtextViewAjustes);
        SetTextViewNomeColaborador = findViewById(R.id.textViewNomeColaborador);
        SetTextViewNomeColaborador.setVisibility(View.INVISIBLE);
        SetTextViewData = findViewById(R.id.SetTextViewDataAcesso);
        SetTextViewData.setVisibility(View.INVISIBLE);
        SetTextViewHorario = findViewById(R.id.SetTextViewHorarioAcesso);
        SetTextViewHorario.setVisibility(View.INVISIBLE);
        SetTextViewConexao = findViewById(R.id.SetTtextViewStatusConexao);
        SetTextViewBR = findViewById(R.id.SetTextViewBRcolaborador);
        SetTextViewBR.setVisibility(View.INVISIBLE);
        TextView datadispositivo = findViewById(R.id.textViewDataEnel);
        datadispositivo.setText("enel "+data.RetornaData());


        imageViewStatusConexaoOn = findViewById(R.id.imageViewStatus_Conexao);
        imageViewStatusConexaoOff = findViewById(R.id.imageViewStatus_Conexao2);

        progressBarBR0 = (ProgressBar) findViewById(R.id.progressBarBR0L);
        progressBarBR0.setVisibility(View.INVISIBLE);

        progressBarD = (ProgressBar) findViewById(R.id.progressBarDATA);
        progressBarD.setVisibility(View.INVISIBLE);

        progressBarH = (ProgressBar) findViewById(R.id.progressBarHORA);
        progressBarH.setVisibility(View.INVISIBLE);

    }

    private void CarregaAcoesComponentes(){

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Info(v);
            }
        });

        SetTextViewExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logout(v);
            }
        });

        historico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Historico(v);
            }
        });

        consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Consultar(v);
            }
        });

        CadColaborador();

        AtualizaLDF();
    }

    private void VerificaAcoes(){
        if (verificaRede.isConnected(getBaseContext())){
            cadastroColaborador.setVisibility(View.VISIBLE);
            imageViewStatusConexaoOn.setVisibility(View.VISIBLE);
            imageViewStatusConexaoOff.setVisibility(View.INVISIBLE);

            SetTextViewConexao.setText("Online");
            SetTextViewAjustes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final View view = v;
                    final EditText confirmacao = new EditText(HomeActivity.this);
                    confirmacao.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    confirmacao.setHint(" Senha");
                    AlertDialog dialog = new AlertDialog.Builder(HomeActivity.this)
                            .setTitle("Acesso Restrito:").setIcon(R.drawable.logo_app)
                            .setMessage("A tela de ajustes é destinada a downloads de ALM e LDF's." +
                                    " Somente administradores podem ter acesso.")
                            .setView(confirmacao)
                            .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String task = String.valueOf(confirmacao.getText());

                                    if(task.equals("0")||task.equals("0")||task.equals("0")){
                                        Toast.makeText(HomeActivity.this, "Acessando...", Toast.LENGTH_SHORT).show();
                                        Ajustes(view);
                                    }else{
                                        Toast.makeText(HomeActivity.this, "Verifique as credenciais!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            })
                            .setNegativeButton("CANCELAR", null)
                            .create();
                    dialog.show();
                }
            });
        }else{
            SetTextViewConexao.setText("Offline");
            cadastroColaborador.setVisibility(View.INVISIBLE);
            imageViewStatusConexaoOn.setVisibility(View.INVISIBLE);
            imageViewStatusConexaoOff.setVisibility(View.VISIBLE);


            Toast.makeText(HomeActivity.this,"Algumas funções serão desativados por falta de internet.",Toast.LENGTH_LONG).show();
            SetTextViewAjustes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final View view = v;
                    final EditText confirmacao = new EditText(HomeActivity.this);
                    confirmacao.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    confirmacao.setHint(" Senha");
                    AlertDialog dialog = new AlertDialog.Builder(HomeActivity.this)
                            .setTitle("Acesso Restrito:").setIcon(R.drawable.logo_app)
                            .setMessage("A tela de ajustes é destinada a downloads de ALM e LDF's." +
                                    " Somente administradores podem ter acesso.")
                            .setView(confirmacao)
                            .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String task = String.valueOf(confirmacao.getText()).trim();
                                    if(task.equals("C")||task.equals("c")||task.equals("C")){
                                        Toast.makeText(HomeActivity.this, "Acessando...", Toast.LENGTH_SHORT).show();
                                        Ajustes(view);
                                    }else{
                                        Toast.makeText(HomeActivity.this, "Verifique as credenciais!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            })
                            .setNegativeButton("CANCELAR", null)
                            .create();
                    dialog.show();
                }
            });
        }

    }

    private void VerificaSensores(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            //ActivityCompat.requestPermissions((Activity) this, new String[]{
            //        Manifest.permission.ACCESS_FINE_LOCATION},
            //       221);

            ActivityCompat.requestPermissions((Activity) this, new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION},
                    221);
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
    }

    private void StatusRede(){
        intent = new Intent(HomeActivity.this, NetworkOFF.class);
        startActivity(intent);
    }

    // AÇÕES DIRETAS PARA TELAS
    public void Ajustes(View view){
        intent = new Intent(this, ConfiguracoesActivity.class);
        startActivity(intent);
    }

    private void CadColaborador(){
            cadastroColaborador.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (verificaRede.isConnected(getBaseContext())) {
                        final View view = v;
                        final EditText confirmacao = new EditText(HomeActivity.this);
                        //confirmacao.setInputType(Inpu);
                        confirmacao.setInputType(InputType.TYPE_CLASS_NUMBER);
                        confirmacao.setHint(" Digite o BR0 pra verificar");
                        AlertDialog dialog = new AlertDialog.Builder(HomeActivity.this)
                                .setTitle("Verificação de disponibilidade de BR0").setIcon(R.drawable.ic_action_br_user)
                                .setMessage("Digite o BR0 do colaborador que deseja cadastrar:")
                                .setView(confirmacao)
                                .setPositiveButton("Verficar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        String task = String.valueOf(confirmacao.getText());
                                        BR0NOVO = "BR0"+ task.trim();
                                        if(!task.equals("")){
                                            json = "{\"br\":\""+BR0NOVO+"\",\"br\":\""+BR0NOVO+"\", \"br\":\""+BR0NOVO+"\"}";

                                            VerficaBR0 loader = new VerficaBR0(HomeActivity.this);
                                            loader.execute();
                                        }else{
                                            Toast.makeText(HomeActivity.this, "Verifique as credenciais!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                })
                                .setNegativeButton("CANCELAR", null)
                                .create();
                        dialog.show();

                    }else {
                        StatusRede();
                        Toast.makeText(HomeActivity.this, "Dispositivo sem conexão de dados!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }

    private void AtualizaLDF(){
        atualizacao_ldf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verificaRede.isConnected(getBaseContext())) {
                    final View view = v;
                    final EditText confirmacao = new EditText(HomeActivity.this);
                    confirmacao.toString().toUpperCase();

                    confirmacao.setHint(" Digite o CSI para  Atualizar");
                    AlertDialog dialog = new AlertDialog.Builder(HomeActivity.this)
                            .setTitle("Atualização de LDF").setIcon(R.drawable.ic_action_icon_csi)
                            .setMessage("Digite o CSI que deseja atualizar:")
                            .setView(confirmacao)
                            .setPositiveButton("Buscar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    String task = String.valueOf(confirmacao.getText());
                                    CSI = task.toUpperCase().trim();
                                    Toast.makeText(HomeActivity.this, "CSI DIGITADO: "+CSI, Toast.LENGTH_SHORT).show();
                                    if(!task.equals("")){
                                        json = "{\"tombamento\":\""+CSI+"\"}";
                                        BuscaLDFparaATUALIZAR loader = new BuscaLDFparaATUALIZAR(HomeActivity.this);
                                        Toast.makeText(HomeActivity.this, "CIS BUSCADO: "+CSI, Toast.LENGTH_SHORT).show();
                                        loader.execute();

                                    }else{
                                        Toast.makeText(HomeActivity.this, "CSI NÃO ENCONTRADO!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            })
                            .setNegativeButton("CANCELAR", null)
                            .create();
                    dialog.show();

                }else {
                    StatusRede();
                    Toast.makeText(HomeActivity.this, "Dispositivo sem conexão de dados!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void Consultar(View view) {
        intent = new Intent(this, ConsultarActivity.class);
        intent.putExtra("BR0COLABORADOR", BR0COLABORADOR);
        startActivity(intent);
    }

    private void Historico(View view) {
        //funcoes.btnVoltar(view, CadastroRelatorioActivity.class);
        intent = new Intent(this, ListaDeRelatoriosActivity.class);
        startActivity(intent);
    }

    public void Info(View view){
        if(VerificaRede.isConnected(this)) {
            intent = new Intent(HomeActivity.this, InfoSobreAPPActivity.class);
            startActivity(intent);
            //Toast.makeText(HomeActivity.this, "Função ainda não desenvolvida", Toast.LENGTH_SHORT).show();
        }else{
            intent = new Intent(HomeActivity.this, InfoSobreAPPActivity.class);
            startActivity(intent);
            StatusRede();
        }
    }

    public void Logout(View view) {
        intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private class Loader extends AsyncTask<Void, Void, Void> {
        UserColaboradorModel userColaboradorModel1;
        UserColaboradorRepository userColaboradorRepository1 = new UserColaboradorRepository(getBaseContext());

        HomeActivity homeActivity;

        public Loader(HomeActivity homeActivity) {
            // recupero a instancia da nossa Activity
            this.homeActivity = homeActivity;
        }
        // Aqui eh o que acontece antes da tarefa principal ser executado
        @Override
        protected void onPreExecute() {
            // o progressBar agora eh setado como visivel
            SetTextViewBR.setVisibility(View.VISIBLE);
            SetTextViewData.setVisibility(View.VISIBLE);
            SetTextViewHorario.setVisibility(View.VISIBLE);
            SetTextViewNomeColaborador.setVisibility(View.VISIBLE);
            progressBarBR0.setVisibility(View.VISIBLE);
            progressBarD.setVisibility(View.VISIBLE);
            progressBarH.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {

                Thread.sleep(2000);
                userColaboradorModel1 = userColaboradorRepository1.CarregaDadosUpDate(BR0COLABORADOR);
                String br = userColaboradorModel1.getBR();
                String token = userColaboradorModel1.getToken();
                String nome = userColaboradorModel1.getNome();
                String tipoUser = userColaboradorModel1.getTiporegistro();
                informacaoBR = br;
                informacaoToken = token;
                informacaoNome = nome;
                tipoRegistro = tipoUser;

            } catch (Exception e) {
                Log.e("ERROR", "" + e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            SetTextViewBR.setText(informacaoBR);
            SetTextViewNomeColaborador.setText(informacaoNome);
            SetTextViewData.setText(funcoes.getDataTime());
            SetTextViewHorario.setText(funcoes.getHorario());

            if(tipoRegistro.equals("0")){
                SetTextViewAjustes.setVisibility(View.VISIBLE);
                atualizacao_ldf.setVisibility(View.VISIBLE);
                cadastroColaborador.setVisibility(View.VISIBLE);
                Toast.makeText(HomeActivity.this,"Administrador, você tem acesso as principais configurações.",Toast.LENGTH_LONG).show();
            }else if(tipoRegistro.equals("1")){
                SetTextViewAjustes.setVisibility(View.VISIBLE);
                atualizacao_ldf.setVisibility(View.INVISIBLE);
                cadastroColaborador.setVisibility(View.INVISIBLE);
                Toast.makeText(HomeActivity.this,"Bem vindo Colaborador!",Toast.LENGTH_LONG).show();
            }

            Toast.makeText(homeActivity,"Bem Vindo "+informacaoNome,Toast.LENGTH_LONG).show();
            progressBarBR0.setVisibility(View.GONE);
            progressBarD.setVisibility(View.GONE);
            progressBarH.setVisibility(View.GONE);
        }

    }

    private class VerficaBR0 extends AsyncTask<Void, Void, Void> {
        VerificaBR0Model verificaBR0Model;
        HomeActivity homeActivity;
        String statusBr;

        ProgressDialog load;
        public VerficaBR0(HomeActivity homeActivity) {
            // recupero a instancia da nossa Activity
            this.homeActivity = homeActivity;
        }
        // Aqui eh o que acontece antes da tarefa principal ser executado
        @Override
        protected void onPreExecute() {
            // o progressBar agora eh setado como visivel
           load = ProgressDialog.show(HomeActivity.this, " Aguarde...", "Verificando o BR0 do colaborador... " +
                    "em nosso servidor...");
        }

        @Override
        protected Void doInBackground(Void... params) {
            WebClient webClient = new WebClient(getApplicationContext());

                try {
                    JSONObject BR0 = new JSONObject(json);

                    verificaBR0Model = webClient.POST_VERIFICA_BR0_COLABORADOR(BR0);
                    Log.i("ERROR1111", "" + verificaBR0Model.getMensagem());
                    if (verificaBR0Model.getMensagem()!=null){
                        statusBr = verificaBR0Model.getMensagem();
                        Log.println(Log.INFO, "configuracaoERROR: ", statusBr);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;

        }

        @Override
        protected void onPostExecute(Void result) {

            load.dismiss();
            if(statusBr.equals("BR já existente")){
                AlertDialog dialog = new AlertDialog.Builder(HomeActivity.this)
                        .setTitle("ATENÇÃO ADMINISTRADOR").setIcon(R.drawable.ic_action_icon_sucess)
                        .setMessage("o BR0 informado já existe em nossa base de dados.")
                        .setNegativeButton("Ok", null)
                        .create();
                dialog.show();

            }else {
                intent = new Intent(HomeActivity.this, CadastroColaboradorActivity.class);
                intent.putExtra("BR0NOVO", BR0NOVO);
                startActivity(intent);
                Toast.makeText(homeActivity,"Proceguindo...",Toast.LENGTH_LONG).show();

            }
        }

    }

    private class BuscaLDFparaATUALIZAR extends AsyncTask<Void, Void, Void> {
        Att_LDF att_ldf;
        HomeActivity homeActivity;

        String success, id, cidade, regiao, alimentador, religador,  tipoDeCurto, tombamento, valorDoCurto, lat, longi;
        String  dist;
        byte    registroAtivo;
        String data_att;
        String statusldf;
        String retornoMensagem;

        ProgressDialog load;
        public BuscaLDFparaATUALIZAR(HomeActivity homeActivity) {
            // recupero a instancia da nossa Activity
            this.homeActivity = homeActivity;
        }
        // Aqui eh o que acontece antes da tarefa principal ser executado
        @Override
        protected void onPreExecute() {
            // o progressBar agora eh setado como visivel
            load = ProgressDialog.show(HomeActivity.this, " Aguarde...", "Verificando o CSI no servidor...");
        }

        @Override
        protected Void doInBackground(Void... params) {
            WebClient webClient = new WebClient(getApplicationContext());

            try {
                JSONObject CSI_DO_LDF = new JSONObject(json);

                att_ldf = webClient.POST_BUSCA_LDF(CSI_DO_LDF);
                Log.i("ERROR1111", "" + att_ldf.getMensagemERROR() + att_ldf.getAlimentador()+ att_ldf.getCidade()+ att_ldf.getRegião());
                if (att_ldf.getMensagemERROR()!=null){
                    success = att_ldf.getMensagemERROR();
                    id = att_ldf.getId();
                    cidade = att_ldf.getCidade();
                    regiao = att_ldf.getRegião();
                    alimentador = att_ldf.getAlimentador();
                    religador = att_ldf.getReligador();
                    tombamento = att_ldf.getTombamento();
                    valorDoCurto = att_ldf.getValorDoCurto();
                    tipoDeCurto = att_ldf.getTipoDeCurto();
                    lat = att_ldf.getLATITUDE();
                    longi = att_ldf.getLONGITUDE();
                    dist = att_ldf.getDISTANCIA();
                    registroAtivo = att_ldf.getRegistroAtivo();
                    data_att = att_ldf.getDataATT();
                    statusldf = att_ldf.getStatusATT();

                    Log.println(Log.INFO, "configuracaoERROR: ", success);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            load.dismiss();
            if(success.equals("LDF ENCONTRADO")){
                AlertDialog dialog = new AlertDialog.Builder(HomeActivity.this)
                        .setTitle("SUCESSO!").setIcon(R.drawable.ic_action_icon_sucess)
                        .setMessage("Os dados do LDF foram baixados para atualização. Click no botão OK para prosseguir")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                intent = new Intent(HomeActivity.this, AtualizacaoLDFActivity.class);
                                intent.putExtra("ID", id);
                                intent.putExtra("CIDADE", cidade);
                                intent.putExtra("REGIAO", regiao);
                                intent.putExtra("ALM", alimentador);
                                intent.putExtra("RLG", religador);
                                intent.putExtra("CSI", tombamento);
                                intent.putExtra("VALORDOCURTO", valorDoCurto);
                                intent.putExtra("TIPODOCURTO", tipoDeCurto);
                                intent.putExtra("LAT", lat);
                                intent.putExtra("LONG", longi);
                                intent.putExtra("DISTANCIA", dist);
                                intent.putExtra("REGISTROATIVO", registroAtivo);
                                intent.putExtra("DATAATT", data_att);
                                intent.putExtra("STATUSLDF", statusldf);

                                startActivity(intent);
                                dialog.dismiss();
                            }
                        })
                        .create();
                dialog.show();
            }else {
                Toast.makeText(homeActivity,"CSI DO LOCALIZADOR ÃO ENCONTRADO PARA ATUALIZAÇÃO.",Toast.LENGTH_LONG).show();
            }
        }
    }
}
