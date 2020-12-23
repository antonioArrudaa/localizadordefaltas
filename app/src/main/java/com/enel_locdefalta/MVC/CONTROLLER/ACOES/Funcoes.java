package com.enel_locdefalta.MVC.CONTROLLER.ACOES;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Funcoes extends AppCompatActivity {

    public String getDataTime(){

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public String getHorario(){
        SimpleDateFormat dateFormat_hora = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        return dateFormat_hora.format(date);
    }

    public void btnVoltar(View view, Class telaDestino){
        Intent intent = new Intent(this,telaDestino.getClass());
        startActivity(intent);
        finish();
    }

    public void FuncaoNaoDisponivel(){
        Toast.makeText(getBaseContext(), "Função não disponível!\nEm caso de dúvidas, contate o setor de desenvolvimento.",Toast.LENGTH_LONG).show();
    }





}
