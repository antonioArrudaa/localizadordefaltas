package com.enel_locdefalta.MVC.VIEW.ACESSO_OFFILINE;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.enel_locdefalta.MVC.CONTROLLER.Login_e_Usuario.UserColaboradorRepository;
import com.enel_locdefalta.MVC.MODEL.Login.UserColaboradorModel;
import com.enel_locdefalta.MVC.VIEW.HOME.HomeActivity;
import com.enel_locdefalta.R;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class LinhaAdapterDadosColaboradores extends BaseAdapter {

    private Context mContext;
    private Activity mActivity;

    private RelativeLayout mRelativeLayout;
    private Button mButton;

    private PopupWindow mPopupWindow;
    Intent intent;

    //CRIANDO UM OBJETO LayoutInflater PARA FAZER LINK A NOSSA VIEW(activity_linha_consultar.xml)
    private static LayoutInflater layoutInflater = null;
    LayoutInflater layoutInflater2 = null;
    private AlertDialog alerta;

    //CRIANDO UMA LISTA DE PESSOAS
    List<UserColaboradorModel> userColaboradorModelsAdpter =  new ArrayList<UserColaboradorModel>();

    //CIRANDO UM OBJETO DA NOSSA CLASSE QUE FAZ ACESSO AO BANCO DE DADOS
    UserColaboradorRepository userColaboradorRepository;

    //CRIANDO UM OBJETO DA NOSSA ATIVIDADE QUE CONTEM A LISTA
    private AcessoOfflineActivity acessoOfflineActivity;

    public LinhaAdapterDadosColaboradores(AcessoOfflineActivity acessoOfflineActivity, List<UserColaboradorModel> userColaboradorModelsAdpter ) {
        this.userColaboradorModelsAdpter       =  userColaboradorModelsAdpter;
        this.acessoOfflineActivity  =  acessoOfflineActivity;
        this.layoutInflater     = (LayoutInflater) this.acessoOfflineActivity.getSystemService(LAYOUT_INFLATER_SERVICE);
        this.userColaboradorRepository = new UserColaboradorRepository(acessoOfflineActivity);
    }

    @Override
    public int getCount(){
        return userColaboradorModelsAdpter.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {


        //CRIANDO UM OBJETO DO TIPO View PARA ACESSAR O NOSSO ARQUIVO DE LAYOUT activity_linha_consultar.xml
        final View viewLinhaLista = layoutInflater.inflate(R.layout.card_retorno_br_colaborador,null);

        //CAMPO QUE VAI MOSTRAR O NOME DA PESSOA
        TextView textViewBR          = (TextView) viewLinhaLista.findViewById(R.id.card_retorno_nomeColaborador);

        //CAMPO QUE VAI MOSTRAR O BR0
        TextView textViewNome        = (TextView) viewLinhaLista.findViewById(R.id.card_retorno_brColaborador);


        ImageButton ir_para_conta = (ImageButton) viewLinhaLista.findViewById(R.id.card_retorno_imageButton_View_BRCOLABORADOR);

        //SETANDO O BR NO CAMPO
        textViewBR.setText(""+userColaboradorModelsAdpter.get(position).getBR());

        //SETANDO O NOME NO CAMPO
        textViewNome.setText(""+userColaboradorModelsAdpter.get(position).getNome());

        ir_para_conta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(acessoOfflineActivity.getApplicationContext(), HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("BR0",userColaboradorModelsAdpter.get(position).getBR());

                acessoOfflineActivity.getApplicationContext().startActivity(intent);

            }
        });

        return viewLinhaLista;

    }

}