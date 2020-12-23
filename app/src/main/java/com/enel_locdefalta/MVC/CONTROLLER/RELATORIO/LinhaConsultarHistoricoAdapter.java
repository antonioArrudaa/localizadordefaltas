package com.enel_locdefalta.MVC.CONTROLLER.RELATORIO;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.enel_locdefalta.MVC.VIEW.RELATORIO.ApresentaRelatorioActivity;
import com.enel_locdefalta.MVC.VIEW.RELATORIO.ListaDeRelatoriosActivity;
import com.enel_locdefalta.R;
import com.enel_locdefalta.MVC.MODEL.RELATORIO.RelatorioLdFObjModel;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class LinhaConsultarHistoricoAdapter extends BaseAdapter {
//implements OnMapReadyCallback
    private ImageButton ir_para_mapa, delete, view_info;

    Intent intent;

    private static LayoutInflater layoutInflater = null;
    LayoutInflater layoutInflater2 = null;
    private AlertDialog alerta;


    int contador = 0;

    GoogleMap googleMap;
    double latitude, longitude;
    String CSI;
    private Marker mLocalizacao;

    int imgSucess = R.drawable.ic_action_icon_sucess;



    int fundo = R.drawable.fundomap;


    //CRIANDO UMA LISTA DE PESSOAS
    List<RelatorioLdFObjModel> relatorioLdFObjModels = new ArrayList<RelatorioLdFObjModel>();

    //CIRANDO UM OBJETO DA NOSSA CLASSE QUE FAZ ACESSO AO BANCO DE DADOS
    RelatorioLDfRepository ldfRepository;

    //CRIANDO UM OBJETO DA NOSSA ATIVIDADE QUE CONTEM A LISTA
    private ListaDeRelatoriosActivity listaDeRelatoriosActivity;
    //private MapaActivity mapaActivity;

    //CONSTRUTOR QUE VAI RECEBER A NOSSA ATIVIDADE COMO PARAMETRO E A LISTA DE PESSOAS QUE VAI RETORNAR
    //DA NOSSA BASE DE DADOS
    public LinhaConsultarHistoricoAdapter(ListaDeRelatoriosActivity listaDeRelatoriosActivity, List<RelatorioLdFObjModel> historicoLdFObjModels) {
        this.relatorioLdFObjModels = historicoLdFObjModels;
        this.listaDeRelatoriosActivity = listaDeRelatoriosActivity;
        this.layoutInflater = (LayoutInflater) this.listaDeRelatoriosActivity.getSystemService(LAYOUT_INFLATER_SERVICE);
        this.ldfRepository = new RelatorioLDfRepository(listaDeRelatoriosActivity);

    }

    //RETORNA A QUANTIDADE DE REGISTROS DA LISTA
    @Override
    public int getCount() {

        return relatorioLdFObjModels.size();
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
//            final View viewLinhaLista = layoutInflater.inflate(R.layout.relatorio_card_retorno_relatorio_,null);
        //final View viewLinhaLista = layoutInflater.inflate(R.layout.relatorio_card_retorno_relatorio_mobile,null);
        final View viewLinhaLista = layoutInflater.inflate(R.layout.new_card_relatorio_card_retorno_relatorio_tablet, null);

        //VINCULANDO OS CAMPOS DO ARQUIVO DE LAYOUT(activity_linha_consultar.xml) AOS OBJETOS DECLARADOS.

        ImageView fundoMap = viewLinhaLista.findViewById(R.id.imageView34);

/*            //CAMPO QUE VAI MOSTRAR O NOME DA CIDADE
            TextView textViewCidade          = (TextView) viewLinhaLista.findViewById(R.id.card_historico_Localizacao);

            //CAMPO QUE VAI MOSTRAR O NOME DO ALIMENTADOR
            TextView textViewAlimentador        = (TextView) viewLinhaLista.findViewById(R.id.card_historico_nomeAlimentador);

            //CAMPO QUE VAI MOSTRAR O NOME DE RELIGADOR
            TextView textViewReligador        = (TextView) viewLinhaLista.findViewById(R.id.card_historico_religador);

            //CAMPOS QUE VAI MOSTRAR O TIPO DE CURTO
            TextView textViewTipoDeCurto            = (TextView) viewLinhaLista.findViewById(R.id.card_historico_tipo_de_curto);

            //CAMPO QUE VAI MOSTRAR O TOMBAMENTO/CSI
            TextView textViewTombamento     = (TextView) viewLinhaLista.findViewById(R.id.card_historico_nomeCSI);

            //CAMPO QUE VAI MOSTRAR O VALOR DO  CURTO
            TextView textViewValorDoCurto      = (TextView) viewLinhaLista.findViewById(R.id.card_historico_valor_do_curto);

            //CAMPO QUE VAI MOSTRAR O VALOR DA DISTANCIA
            TextView textViewDistancia      = (TextView) viewLinhaLista.findViewById(R.id.card_historico_distancia);

            //CAMPO QUE VAI MOSTRAR O BR0 DO COLABORADOR
            TextView textViewBRCOLABORADOR = (TextView) viewLinhaLista.findViewById(R.id.card_historico_br_colaborador);

            //CAMPO QUE VAI MOSTRAR A DATA
            TextView textViewData   = (TextView) viewLinhaLista.findViewById(R.id.card_historico_dataAtendimento);

            //CAMPO QUE VAI MOSTRAR A HORA
            TextView textViewHora = (TextView) viewLinhaLista.findViewById(R.id.card_historico_hora);
*/

        //CAMPOS DE TESTE NO NOVO VIEW CARD


        Button buttonAcesso = (Button) viewLinhaLista.findViewById(R.id.buttonAcessoAoRelatorioCompleto);
        TextView textViewCSI = (TextView) viewLinhaLista.findViewById(R.id.card_historico_nomeCSI);
        TextView textViewCidade = (TextView) viewLinhaLista.findViewById(R.id.card_historico_nome_cidade);
        TextView textViewDATA_HORA_REGISTRO = (TextView) viewLinhaLista.findViewById(R.id.textViewDATA_HORA_registro);
        TextView textViewDescricaoOcorrencia = (TextView) viewLinhaLista.findViewById(R.id.card_historico_descricao_ocorrencia);
        TextView textViewBR = (TextView) viewLinhaLista.findViewById(R.id.card_historico_br_colaborador);
        ImageView upload = (ImageView) viewLinhaLista.findViewById(R.id.imageView_upload);

        //fundoMap.setImageDrawable(FundoMap);

        //ir_para_mapa = (ImageButton) viewLinhaLista.findViewById(R.id.card_historico_imageButton_Mapa);

        //delete = (ImageButton) viewLinhaLista.findViewById(R.id.card_historico_imageButton_Delete);

        view_info = (ImageButton) viewLinhaLista.findViewById(R.id.card_historico_imageButton_View_Relatorio);

        //MapView mapView = (MapView) viewLinhaLista.findViewById(R.id.card_mapView);


        //mapView.onCreate(null);
        //mapView.getMapAsync(this);

        String verificaStatus = relatorioLdFObjModels.get(position).getStatusRelatorio();


        if (verificaStatus.equals("1")){
           upload.setImageResource(R.drawable.ic_action_icon_sucess);
        }



        latitude = Double.parseDouble(relatorioLdFObjModels.get(position).getLATITUDE());
        longitude = Double.parseDouble(relatorioLdFObjModels.get(position).getLONGITUDE());

        CSI = relatorioLdFObjModels.get(position).getTombamento();

        //SETANDO O CÓDIGO NO CAMPO DA NOSSA VIEW
        //textViewCodigo.setText(String.valueOf(ldfModels.get(position).getCodigo()));

        //SETANDO A CIDADE NO CAMPO DA NOSSA VIEW
        textViewCidade.setText(relatorioLdFObjModels.get(position).getCidade()+"/Ce");

        //SETANDO A ALIMENTADOR NO CAMPO DA NOSSA VIEW
        //textViewAlimentador.setText(relatorioLdFObjModels.get(position).getAlimentador());

        //SETANDO A RELIGADOR NO CAMPO DA NOSSA VIEW
        //textViewReligador.setText(relatorioLdFObjModels.get(position).getReligador());

        //SETANDO O ENDEREÇO NO CAMPO DA NOSSA VIEW
        //textViewEndereco.setText(ldfModels.get(position).getEndereco());

        //SETANDO O SEXO NO CAMPO DA NOSSA VIEW
        //if(relatorioLdFObjModels.get(position).getTipoDeCurto().toUpperCase().equals("T"))
        //    textViewTipoDeCurto.setText("Trifásico");
        // else if (relatorioLdFObjModels.get(position).getTipoDeCurto().toUpperCase().equals("B"))
        //    textViewTipoDeCurto.setText("Bifásico");
        //else
        //    textViewTipoDeCurto.setText("Monofásico");




        //SETANDO A TOMBAMENTO NO CAMPO DA NOSSA VIEW
        textViewCSI.setText(relatorioLdFObjModels.get(position).getTombamento());

        //SETANDO A VALOR DO CURTO NO CAMPO DA NOSSA VIEW
        //textViewValorDoCurto.setText(relatorioLdFObjModels.get(position).getValorDoCurto()+"A");

        //SETANDO A DISTANCIA NO CAMPO DA NOSSA VIEW
        //textViewDistancia.setText(relatorioLdFObjModels.get(position).getDISTANCIA()+" Km");

        textViewDescricaoOcorrencia.setText(relatorioLdFObjModels.get(position).getDescricaoRelatorio());
        textViewBR.setText(relatorioLdFObjModels.get(position).getBRColaborador());

        textViewDATA_HORA_REGISTRO.setText(relatorioLdFObjModels.get(position).getData() + " às " + relatorioLdFObjModels.get(position).getHorario());

        //textViewHora.setText(relatorioLdFObjModels.get(position).getHorario());

/*
            ir_para_mapa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(listaDeRelatoriosActivity,"Você apertou em um botão que ainda se encontra em desenvolvimento."/*: "+relatorioLdFObjModels.get(position).getTombamento()*///,Toast.LENGTH_LONG).show();

        // }
        // });

/*
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //DeleteRelatorio(listaDeRelatoriosActivity);

                    final EditText confirmacao = new EditText(listaDeRelatoriosActivity);

                    confirmacao.setHint("Digite a palavra CONFIRMAR");
                    AlertDialog dialog = new AlertDialog.Builder(listaDeRelatoriosActivity)
                            .setTitle("Tem certeza?").setIcon(R.drawable.logo_app)
                            .setMessage("Essa operação excluirá os dados do relatorio e não poderá ser desfeita. Digite CONFIRMAR e" +
                                    " toque no botão APAGAR abaixo.")
                            .setView(confirmacao)
                            .setPositiveButton("APAGAR", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String task = String.valueOf(confirmacao.getText());
                                    if(task.equals("CONFIRMAR")||task.equals("confirmar")||task.equals("Confirmar")){
                                        Toast.makeText(listaDeRelatoriosActivity, "Relatório Apagado!", Toast.LENGTH_SHORT).show();
                                        ldfRepository.Excluir(relatorioLdFObjModels.get(position).getCodigo());
                                        AtualizarLista();
                                    }else{
                                        Toast.makeText(listaDeRelatoriosActivity, "Verifique suas credenciais!", Toast.LENGTH_SHORT).show();
                                        Toast.makeText(listaDeRelatoriosActivity, "Não foi possivel apagar.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            })
                            .setNegativeButton("CANCELAR", null)
                            .create();
                    dialog.show();
                }
            });
*/
        buttonAcesso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(listaDeRelatoriosActivity, "Você apertou em um botão."/*: "+relatorioLdFObjModels.get(position).getTombamento()*/, Toast.LENGTH_LONG).show();

                intent = new Intent(listaDeRelatoriosActivity.getBaseContext(), ApresentaRelatorioActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("CITY", relatorioLdFObjModels.get(position).getCidade());
                intent.putExtra("ALM", relatorioLdFObjModels.get(position).getAlimentador());
                intent.putExtra("RLG", relatorioLdFObjModels.get(position).getReligador());
                intent.putExtra("CSI", relatorioLdFObjModels.get(position).getTombamento());
                intent.putExtra("TPCURTO", relatorioLdFObjModels.get(position).getTipoDeCurto());
                intent.putExtra("VLCURTO", relatorioLdFObjModels.get(position).getValorDoCurto());
                intent.putExtra("LAT", relatorioLdFObjModels.get(position).getLATITUDE());
                intent.putExtra("LONG", relatorioLdFObjModels.get(position).getLONGITUDE());
                intent.putExtra("DISTANCIA", relatorioLdFObjModels.get(position).getDISTANCIA());
                intent.putExtra("DESCRIPTION", relatorioLdFObjModels.get(position).getDescricaoRelatorio());
                intent.putExtra("FOTO1", relatorioLdFObjModels.get(position).getFoto1());
                intent.putExtra("FOTO2", relatorioLdFObjModels.get(position).getFoto2());
                //intent.putExtra("FOTO3", relatorioLdFObjModels.get(position).getFoto3());
                //intent.putExtra("FOTO4", relatorioLdFObjModels.get(position).getFoto4());
                //intent.putExtra("FOTO5", relatorioLdFObjModels.get(position).getFoto5());
                intent.putExtra("HORAOC", relatorioLdFObjModels.get(position).getHorario());
                intent.putExtra("DATAOC", relatorioLdFObjModels.get(position).getData());
                intent.putExtra("BR0", relatorioLdFObjModels.get(position).getBRColaborador());
                intent.putExtra("STATUSRELATORIO", relatorioLdFObjModels.get(position).getStatusRelatorio());

                listaDeRelatoriosActivity.getApplicationContext().startActivity(intent);

            }
        });


        return viewLinhaLista;


    }


    //ATUALIZA A LISTTA DEPOIS DE EXCLUIR UM REGISTRO
    public void AtualizarLista() {
        this.relatorioLdFObjModels.clear();
        this.relatorioLdFObjModels = ldfRepository.SelecionarTodos();
        this.relatorioLdFObjModels = ldfRepository.SelecionarTodosStatus();
        this.notifyDataSetChanged();
    }

    private void DeleteRelatorio(Context c) {
        final EditText confirmacao = new EditText(c);
        confirmacao.setHint("Digite a palavra C");
        AlertDialog dialog = new AlertDialog.Builder(c)
                .setTitle("Tem certeza?")
                .setMessage("Essa operação excluirá os dados do relatorio e não poderá ser desfeita. Digite CONFIRMAR e toque no botão abaixo.")
                .setView(confirmacao)
                .setPositiveButton("APAGAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String task = String.valueOf(confirmacao.getText());


                        Toast.makeText(listaDeRelatoriosActivity, "LDF Apagado!", Toast.LENGTH_SHORT).show();
                        //   ldfRepository.Excluir(relatorioLdFObjModels.get(position).getCodigo());
                        AtualizarLista();
                    }
                })
                .setNegativeButton("CANCELAR", null)
                .create();
        dialog.show();
    }

   // @Override
    /*public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        double lat = Double.parseDouble(String.valueOf(latitude));
        double longit = Double.parseDouble(String.valueOf(longitude));
        String TOMBAMENTO = CSI;

        int num = R.drawable.pino_maps;

        LatLng pino = new LatLng(longit, lat);
        mLocalizacao = this.googleMap.addMarker(new MarkerOptions().position(pino)
                .title("DADOS DO RELIGADOR: " + TOMBAMENTO)
                .icon(BitmapDescriptorFactory.fromResource(num)));
        this.googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pino,12f));

        //this.googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        if (ActivityCompat.checkSelfPermission(listaDeRelatoriosActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(listaDeRelatoriosActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
       // this.googleMap.setMyLocationEnabled(true);
       // googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lat,longit)));
    }*/

}
