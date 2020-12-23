package com.enel_locdefalta.utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.widget.ProgressBar;


import com.enel_locdefalta.MVC.VIEW.LOGIN.LoginActivity;
import com.enel_locdefalta.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Uteis {

    private ProgressDialog progressDialog;

    private ProgressBar progressBar;

    private int progressStatus = 0;

    private Handler handler = new Handler();

    public static void Alert(Context context, String mensagem){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        //ADICIONANDO UM TITULO A NOSSA MENSAGEM DE ALERTA
        alertDialog.setTitle(R.string.app_name);

        //MENSAGEM A SER EXIBIDA
        alertDialog.setMessage(mensagem);



        //CRIA UM BOTÃO COM O TEXTO OK SEM AÇÃO
        alertDialog.setPositiveButton("OK", null);

        //MOSTRA A MENSAGEM NA TELA
        alertDialog.show();

    }

    public static void AlertaDialogo(Context context, String Titulo, String Mensagem, int Icone, String TextoPositiveButton, String TextoNegativoButton){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        //ADICIONANDO UM TITULO A NOSSA MENSAGEM DE ALERTA
        alertDialog.setTitle(Titulo);

        //MENSAGEM A SER EXIBIDA
        alertDialog.setMessage(Mensagem);

        //Icone
        alertDialog.setIcon(Icone);

        //CRIA UM BOTÃO COM O TEXTO OK SEM AÇÃO
        alertDialog.setPositiveButton(TextoPositiveButton, null);
        alertDialog.setNegativeButton(TextoNegativoButton,null);

        //MOSTRA A MENSAGEM NA TELA
        alertDialog.show();

    }

    public static void AlertDialogo(Context context, String titulo,String mensagem){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        //ADICIONANDO UM TITULO A NOSSA MENSAGEM DE ALERTA
        alertDialog.setTitle(R.string.app_name +": "+titulo);


        //MENSAGEM A SER EXIBIDA
        alertDialog.setMessage(mensagem);



        //CRIA UM BOTÃO COM O TEXTO OK SEM AÇÃO
        alertDialog.setPositiveButton("OK", null);

        //MOSTRA A MENSAGEM NA TELA
        alertDialog.show();

    }


    public static void AlertLogin(final Context context, String mensagem){

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        //ADICIONANDO UM TITULO A NOSSA MENSAGEM DE ALERTA
        alertDialog.setTitle(R.string.app_name);

        //MENSAGEM A SER EXIBIDA
        alertDialog.setMessage(mensagem);



        //CRIA UM BOTÃO COM O TEXTO OK SEM AÇÃO
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // intent = new Intent(context, LoginActivity.class);
                Intent intent = new Intent(context.getApplicationContext(), LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                alertDialog.getContext().startActivity(intent);

            }
        });



        //MOSTRA A MENSAGEM NA TELA
        alertDialog.show();

    }


    private void progresso(){
        new Thread(new Runnable() {
            public void run() {
                while (progressStatus < 100) {
                    progressStatus += 1;
                    // Update the progress bar and display the
                    //current value in the text view
                    handler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(progressStatus);
                            //textView.setText(progressStatus+"/"+progressBar.getMax()+"%");
                        }
                    });
                    try {
                        // Sleep for 200  milliseconds.
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }


    public String RetornaData(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
        Date data = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        Date data_atual = cal.getTime();

        String dataAtual = dateFormat.format(data_atual);

        return dataAtual;
    }

    public String RetornaHora(String estiloHora, Context context, String mensagemCasoError){

        String horaAtual = "";
        if(estiloHora.equals("HH:mm")) {

            SimpleDateFormat dateFormat_hora = new SimpleDateFormat("HH:mm");
            Date data = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(data);
            Date data_atual = cal.getTime();
            horaAtual = dateFormat_hora.format(data_atual);
        }else{
            Alert(context, mensagemCasoError);
        }

        return horaAtual;
    }




    public static void AlertSemConexao(final Context context, String mensagem) {

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        //ADICIONANDO UM TITULO A NOSSA MENSAGEM DE ALERTA
        alertDialog.setTitle(R.string.app_name);

        //MENSAGEM A SER EXIBIDA
        alertDialog.setMessage(mensagem);


        //CRIA UM BOTÃO COM O TEXTO OK SEM AÇÃO
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // intent = new Intent(context, LoginActivity.class);
                try {
                    this.finalize();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }

            }
        });
    }

}
