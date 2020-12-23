package com.enel_locdefalta.MVC.VIEW.CADCOLABORADOR;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.enel_locdefalta.R;
import com.enel_locdefalta.utils.Uteis;


public class SucessoCadastroActivity extends AppCompatActivity {

    TextView  editTextBrColaborador, editTextSenhaColaborador;

    ImageView btn_voltar;
    Button ApresentarDados;
    String brColaborador, senhaColaborador;

    Intent intent;

    Uteis data = new Uteis();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sucesso_cadastro_tablet);
        //setContentView(R.layout.activity_sucesso_cadastro_mobile);

        TextView datadispositivo = findViewById(R.id.textViewDataEnel);
        datadispositivo.setText("enel "+data.RetornaData());

        GeraComponents();

        intent = getIntent();

        brColaborador = intent.getStringExtra("BR0");

        senhaColaborador = intent.getStringExtra("SENHA");

        ApresentarDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApresentarDados.setText("DADOS PARA ACESSO ABAIXO:");
                ApresentaDados();
            }
        });

        btn_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void GeraComponents(){
        btn_voltar = findViewById(R.id.imageView_bnt_sucesso_close);
        ApresentarDados = findViewById(R.id.buttonApresentaDados);

        editTextBrColaborador = findViewById(R.id.textView_sucesso_br_colaborador);
        editTextBrColaborador.setVisibility(View.INVISIBLE);

        editTextSenhaColaborador = findViewById(R.id.textView_sucesso_senha_colaborador);
        editTextSenhaColaborador.setVisibility(View.INVISIBLE);
    }

    private void ApresentaDados(){

        editTextBrColaborador.setText("LOGIN: "+brColaborador);
        editTextBrColaborador.setVisibility(View.VISIBLE);
        editTextSenhaColaborador.setText("SENHA: "+senhaColaborador);
        editTextSenhaColaborador.setVisibility(View.VISIBLE);
    }
}
