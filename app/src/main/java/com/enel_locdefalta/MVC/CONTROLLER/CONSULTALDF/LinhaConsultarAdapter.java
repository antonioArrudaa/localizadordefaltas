package com.enel_locdefalta.MVC.CONTROLLER.CONSULTALDF;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.enel_locdefalta.MVC.CONTROLLER.Login_e_Usuario.UserColaboradorRepository;
import com.enel_locdefalta.MVC.MODEL.Login.UserColaboradorModel;
import com.enel_locdefalta.MVC.VIEW.CONSULTALDF.ConsultarActivity;
import com.enel_locdefalta.MVC.VIEW.MAPS.MapsActivity;
import com.enel_locdefalta.R;
import com.enel_locdefalta.MVC.MODEL.AJUSTES.ConsultaLdFObjModel;
import com.enel_locdefalta.MVC.CONTROLLER.AJUSTES.DOWNLOAD_LDF_Repository;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static android.media.CamcorderProfile.get;

public class LinhaConsultarAdapter extends BaseAdapter {

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

    int contador = 0;

    //CRIANDO UMA LISTA DE ldfs
    List<ConsultaLdFObjModel> ldfModels =  new ArrayList<ConsultaLdFObjModel>();
    UserColaboradorModel userColaboradorModel = new UserColaboradorModel();

    //CIRANDO UM OBJETO DA NOSSA CLASSE QUE FAZ ACESSO AO BANCO DE DADOS
    DOWNLOAD_LDF_Repository DOWNLOADLDFRepository;
    UserColaboradorRepository userColaboradorRepository;

    //CRIANDO UM OBJETO DA NOSSA ATIVIDADE QUE CONTEM A LISTA
    private ConsultarActivity consultarActivity;
    //private MapaActivity mapaActivity;

    //CONSTRUTOR QUE VAI RECEBER A NOSSA ATIVIDADE COMO PARAMETRO E A LISTA DE PESSOAS QUE VAI RETORNAR
    //DA NOSSA BASE DE DADOS
    public LinhaConsultarAdapter(ConsultarActivity consultarActivity, List<ConsultaLdFObjModel> ldfDadosModels ) {
            this.ldfModels       =  ldfDadosModels;
            this.consultarActivity  =  consultarActivity;
            this.layoutInflater     = (LayoutInflater) this.consultarActivity.getSystemService(LAYOUT_INFLATER_SERVICE);
            this.DOWNLOADLDFRepository = new DOWNLOAD_LDF_Repository(consultarActivity);
            this.userColaboradorRepository = new UserColaboradorRepository(consultarActivity);

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
        //final View viewLinhaLista = layoutInflater.inflate(R.layout.card_retorno_consulta,null);
        final View viewLinhaLista = layoutInflater.inflate(R.layout.card_retorno_consulta_mobile,null);
        //VINCULANDO OS CAMPOS DO ARQUIVO DE LAYOUT(activity_linha_consultar.xml) AOS OBJETOS DECLARADOS.

        //CAMPO QUE VAI MOSTRAR O NOME DA CIDADE
        TextView textViewCidade          = (TextView) viewLinhaLista.findViewById(R.id.textView_card_nome_cidade);

        //CAMPO QUE VAI MOSTRAR O ENDEREÇO DO ALIMENTADOR
        TextView textViewAlimentador        = (TextView) viewLinhaLista.findViewById(R.id.textView_card_nome_alimentador);

        //CAMPO QUE VAI MOSTRAR O ENDEREÇO DO RELIGADOR
        TextView textViewReligador        = (TextView) viewLinhaLista.findViewById(R.id.textView_card_nome_religador);

        //CAMPOS QUE VAI MOSTRAR O TIPO DO CURTO
        TextView textViewTipoDeCurto            = (TextView) viewLinhaLista.findViewById(R.id.textView_card_nome_tipo_de_curto);

        //CAMPO QUE VAI MOSTRAR O CSI
        TextView textViewTombamento     = (TextView) viewLinhaLista.findViewById(R.id.textView_card_nome_tombamento);

        //CAMPO QUE VAI MOSTRAR O VALOR DO CURTO
        TextView textViewValorDoCurto      = (TextView) viewLinhaLista.findViewById(R.id.textView_card_valor_do_curto);

        TextView textViewDistancia      = (TextView) viewLinhaLista.findViewById(R.id.textView_card_distancia);

        //CRIANDO O BOTÃO PARA EDITAR UM REGISTRO CADASTRADO
        Button   buttonEditar            = (Button)   viewLinhaLista.findViewById(R.id.button_card_visualizar_no_mapa);

        Button ir_para_mapa = (Button) viewLinhaLista.findViewById(R.id.button_card_visualizar_no_mapa);

        //SETANDO A CIDADE NO CAMPO DA NOSSA VIEW
        textViewCidade.setText("Cidade: "+ ldfModels.get(position).getCidade());

        //SETANDO A ALIMENTADOR NO CAMPO DA NOSSA VIEW
        textViewAlimentador.setText("Alimentador: "+ldfModels.get(position).getAlimentador());

        //SETANDO A RELIGADOR NO CAMPO DA NOSSA VIEW
        textViewReligador.setText("Religador: "+ldfModels.get(position).getReligador());

        //SETANDO O ENDEREÇO NO CAMPO DA NOSSA VIEW
        //textViewEndereco.setText(ldfModels.get(position).getEndereco());

        //SETANDO O SEXO NO CAMPO DA NOSSA VIEW
        if(ldfModels.get(position).getTipoDeCurto().toUpperCase().equals("T"))
            textViewTipoDeCurto.setText("Tipo de curto: Trifasico");
        else if (ldfModels.get(position).getTipoDeCurto().toUpperCase().equals("B"))
            textViewTipoDeCurto.setText("Tipo de curto: Bifasico");
        else
            textViewTipoDeCurto.setText("Tipo de curto: Monofasico");

        //SETANDO A TOMBAMENTO NO CAMPO DA NOSSA VIEW
        textViewTombamento.setText("CSI: "+ldfModels.get(position).getTombamento());

        //SETANDO A VALOR DO CURTO NO CAMPO DA NOSSA VIEW
        textViewValorDoCurto.setText("Valor do curto: "+ldfModels.get(position).getValorDoCurto());

        //SETANDO A DISTANCIA NO CAMPO DA NOSSA VIEW
        textViewDistancia.setText( "Dist: "+ldfModels.get(position).getDISTANCIA()+" Km");

        ir_para_mapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent = new Intent(consultarActivity.getApplicationContext(), MapsActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //intent.putExtra("BR0COLABORADOR", userColaboradorModel.get(position).getBR());
                intent.putExtra("CIDADE",ldfModels.get(position).getCidade());
                intent.putExtra("ALM",ldfModels.get(position).getAlimentador());
                intent.putExtra("RLG",ldfModels.get(position).getReligador());
                intent.putExtra("TOMBAMENTO",ldfModels.get(position).getTombamento());
                intent.putExtra("TIPO_DE_CURTO", ldfModels.get(position).getTipoDeCurto());
                intent.putExtra("VALOR_DO_CURTO",ldfModels.get(position).getValorDoCurto());
                intent.putExtra("LAT",ldfModels.get(position).getLATITUDE());
                intent.putExtra("LONG",ldfModels.get(position).getLONGITUDE());
                intent.putExtra("DISTANCIA",ldfModels.get(position).getDISTANCIA());

                consultarActivity.getApplicationContext().startActivity(intent);

            }
        });

        return viewLinhaLista;

    }

    //ATUALIZA A LISTTA DEPOIS DE EXCLUIR UM REGISTRO
    public void AtualizarLista(){

        this.ldfModels.clear();
        this.ldfModels = DOWNLOADLDFRepository.SelecionarTodos();
        this.notifyDataSetChanged();
    }

    private void showAddItemDialog(Context c) {
        final EditText taskEditText = new EditText(c);
        AlertDialog dialog = new AlertDialog.Builder(c)
                .setTitle("Add a new task")
                .setMessage("What do you want to do next?")
                .setView(taskEditText)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String task = String.valueOf(taskEditText.getText());
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }
}
