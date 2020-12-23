package com.enel_locdefalta.MVC.CONTROLLER.CONSULTALDF;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.enel_locdefalta.MVC.VIEW.CONSULTALDF.ConsultarActivity;
import com.enel_locdefalta.R;
import com.enel_locdefalta.MVC.MODEL.AJUSTES.ObjModel_ALM_RLG;
import com.enel_locdefalta.MVC.CONTROLLER.AJUSTES.DOWNLOAD_ALM_E_RLG_Repository;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class LinhaConsultaALMAdapter extends BaseAdapter {

    private static LayoutInflater layoutInflater = null;

    List<ObjModel_ALM_RLG> ldfModels =  new ArrayList<ObjModel_ALM_RLG>();

    //CIRANDO UM OBJETO DA NOSSA CLASSE QUE FAZ ACESSO AO BANCO DE DADOS
    DOWNLOAD_ALM_E_RLG_Repository ldfRepository;

    private ConsultarActivity consultarActivity;

    public LinhaConsultaALMAdapter(ConsultarActivity consultarActivity, List<ObjModel_ALM_RLG> ldfDadosModels ) {
        this.ldfModels       =  ldfDadosModels;
        this.consultarActivity  =  consultarActivity;
        this.layoutInflater     = (LayoutInflater) this.consultarActivity.getSystemService(LAYOUT_INFLATER_SERVICE);
        this.ldfRepository   = new DOWNLOAD_ALM_E_RLG_Repository(consultarActivity);
    }

    //RETORNA A QUANTIDADE DE REGISTROS DA LISTA
    @Override
    public int getCount(){

        return ldfModels.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    //ESSE MÉTODO SETA OS VALORES DE UM ITEM DA NOSSA LISTA DE PESSOAS PARA UMA LINHA DO NOSSO LISVIEW
    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {


        //CRIANDO UM OBJETO DO TIPO View PARA ACESSAR O NOSSO ARQUIVO DE LAYOUT activity_linha_consultar.xml
        final View viewLinhaLista = layoutInflater.inflate(R.layout.spiner_layout,null);

        //CAMPO QUE VAI MOSTRAR O ENDEREÇO DA PESSOA
        TextView textViewReligador        = (TextView) viewLinhaLista.findViewById(R.id.itemALM);

        //SETANDO A RELIGADOR NO CAMPO DA NOSSA VIEW
        textViewReligador.setText("Religador: "+ldfModels.get(position).getReligador());

        Log.println(Log.INFO,"TESTE:", ""+ viewLinhaLista);

        return viewLinhaLista;


    }

    //ATUALIZA A LISTTA DEPOIS DE EXCLUIR UM REGISTRO
    public void AtualizarLista(){

        this.ldfModels.clear();
        this.ldfModels = ldfRepository.SelecionarTodos_ALM();
        this.notifyDataSetChanged();
    }


}
