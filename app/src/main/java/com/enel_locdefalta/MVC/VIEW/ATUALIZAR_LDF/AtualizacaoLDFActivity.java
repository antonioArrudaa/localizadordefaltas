package com.enel_locdefalta.MVC.VIEW.ATUALIZAR_LDF;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.enel_locdefalta.MVC.CONTROLLER.ACOES.Funcoes;
import com.enel_locdefalta.MVC.MODEL.ATUALIZA_LDF.Att_LDF;
import com.enel_locdefalta.MVC.SERVIDOR_e_BD.WebClient;
import com.enel_locdefalta.R;
import com.enel_locdefalta.utils.Uteis;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class AtualizacaoLDFActivity extends FragmentActivity implements GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener, OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker mLocalizacao;
    Intent intent;

    EditText REGIAO, CIDADE, ALM, RLG, CSI, VALOR_DO_CURTO, LAT, LONGI, DISTANCIA, DATAATT;
    Switch REGISTROATIVO, TIPODOCURTO_MONO, TIPODOCURTO_BIFA, TIPODOCURTO_TRIFA;
    ImageView imageView_ATT_MAP;
    Button buttonTelaInicial, buttonAtualizaLDF;
    SupportMapFragment mapFragment;

    String JSON, stringID, stringLATITUDE, stringLONGITUDE, stringDISTANCIA_DA_SE, stringDATAATUALIZACAO, stringREGIAO, stringCIDADE,
            stringALM, stringRLG, stringCSI, stringVALOR_DO_CURTO, stringTIPO_DO_CURTO, stringSTATUSLDF, stringREGISTROATIVO;
    Funcoes funcoes = new Funcoes();

    private FusedLocationProviderClient fusedLocationClient;

    Uteis data = new Uteis();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atualizacao_ldf_tablet);

        TextView datadispositivo = findViewById(R.id.textViewDataEnel);
        datadispositivo.setText("enel "+data.RetornaData());

        GeraComponents();

        CarregaIntents();

        GeraLocalizador();

        ApresentaValores();

        GeraAcoesBotoes();

    }


    private void GeraComponents(){
        ALM = findViewById(R.id.editText_ATT_ALIMENTADOR_TABLET);
        RLG = findViewById(R.id.editText_ATT_RELIGADOR_TABLET);
        REGIAO = findViewById(R.id.editText_ATT_REGIAO_TABLET);
        CIDADE = findViewById(R.id.editText_ATT_CIDADE_TABLET);

        CSI = findViewById(R.id.editText_ATT_TOMBAMENTO_TABLET);
        VALOR_DO_CURTO = findViewById(R.id.editText_ATT_VALOR_DO_CURTO_TABLET);
        LAT = findViewById(R.id.editText_ATT_LAT_TABLET);
        LONGI = findViewById(R.id.editText_ATT_LONG_TABLET);
        DISTANCIA = findViewById(R.id.editText_ATT_DISTANCIASE_TABLET);
        DATAATT = findViewById(R.id.editText_ATT_DATA_ATT_TABLET);
        REGISTROATIVO = findViewById(R.id.switchA_ATT_MODE_REGISTRO_ATIVO);

        TIPODOCURTO_MONO = findViewById(R.id.switchA_ATT_MODE_MONOFÁSICO);
        TIPODOCURTO_BIFA = findViewById(R.id.switchA_ATT_MODE_BIFÁSICO);
        TIPODOCURTO_TRIFA = findViewById(R.id.switchA_ATT_MODE_TRIFASICO);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapATT_LDF);
        imageView_ATT_MAP = findViewById(R.id.imageView_ATT_MAP);

        buttonAtualizaLDF = findViewById(R.id.buttonATUALIZARLDF_TABLET);

        buttonTelaInicial = findViewById(R.id.buttonTelaInicial);
    }


    private void CarregaIntents(){
        intent = getIntent();

        final String id = intent.getStringExtra("ID");
        final String cidade = intent.getStringExtra("CIDADE");
        final String regiao = intent.getStringExtra("REGIAO");
        final String alimentador=  intent.getStringExtra("ALM");
        final String religador = intent.getStringExtra("RLG");
        final String tombamento = intent.getStringExtra("CSI");
        final String valorDoCurto = intent.getStringExtra("VALORDOCURTO");
        final String tipoDeCurto=  intent.getStringExtra("TIPODOCURTO");
        final String lat=  intent.getStringExtra("LAT");
        final String longi = intent.getStringExtra("LONG");
        final String distancia=  intent.getStringExtra("DISTANCIA");
        final Byte registroAtivo1 = intent.getByteExtra("REGISTROATIVO", (byte) 1);
        final String data_atualizacao=  intent.getStringExtra("DATAATT");
        final String statusldf=  intent.getStringExtra("STATUSLDF");

        stringID = id;
        stringCIDADE = cidade;
        stringREGIAO = regiao;
        stringALM = alimentador;
        stringRLG = religador;
        stringCSI = tombamento;
        stringVALOR_DO_CURTO = valorDoCurto;
        stringTIPO_DO_CURTO = tipoDeCurto;
        stringLATITUDE = lat;
        stringLONGITUDE = longi;
        stringDISTANCIA_DA_SE = distancia;
        stringREGISTROATIVO = String.valueOf(registroAtivo1);
        stringDATAATUALIZACAO = funcoes.getDataTime();
        stringSTATUSLDF = statusldf;
    }


    private String RemoveTexto(String TextoCompleto, String ParteAserRemovida,int TotalCaracters){
        String recebeTexto = TextoCompleto;
        String parteParaRemover = ParteAserRemovida;

        int removePt1 = recebeTexto.indexOf(parteParaRemover);

        String resultadoTexto = recebeTexto.substring(removePt1+TotalCaracters);
        Log.e("REMOVERTEXTO","VALOR REMOVIDO: "+ resultadoTexto);
        return resultadoTexto;

    }

    private void AtualizaLDFServidor(){

        Log.e("JSONATT","{\"id\": \""+stringID+"\",\"cidade\": \""+stringCIDADE+"\",\"regiao\": \""+stringREGIAO+"\",\"alimentador\": \""+stringALM+"\",\"religador\": \""+stringRLG+"\",\"tombamento\": \""+stringCSI+"\",\n" +
                "    \"valordocurto\": \""+stringVALOR_DO_CURTO+"\",\"tipodocurto\": \""+stringTIPO_DO_CURTO+"\",\"latitude\": \""+stringLATITUDE+"\",\"longitude\": \""+stringLONGITUDE+"\",\n" +
                "    \"distancia\": \""+stringDISTANCIA_DA_SE+"\",\"registroativo\": \""+stringREGISTROATIVO+"\",\"dataatualizacao\":\""+stringDATAATUALIZACAO+"\",\"statusldf\": \""+stringSTATUSLDF+"\"}");

        if(stringID!=null && stringCIDADE!=null && stringREGIAO!=null && stringALM!=null && stringRLG!=null && stringCSI!=null && stringVALOR_DO_CURTO!=null && stringTIPO_DO_CURTO!=null
                && stringLATITUDE!=null && stringLONGITUDE!=null && stringDISTANCIA_DA_SE!=null && stringREGISTROATIVO!=null && stringDATAATUALIZACAO!=null){



             stringCIDADE = CIDADE.getText().toString().toUpperCase();
             stringREGIAO = REGIAO.getText().toString().toUpperCase();

             String valorALM = ALM.getText().toString().toUpperCase();
             stringALM = RemoveTexto(valorALM,"ALM: ",5);
             String valorRLG = RLG.getText().toString().toUpperCase();
             stringRLG = RemoveTexto(valorRLG,"RLG: ", 5);
             String valorCSI = CSI.getText().toString().toUpperCase();
             stringCSI = RemoveTexto(valorCSI,"CSI: ", 5);

             String valorVALOR_DO_CURTO = VALOR_DO_CURTO.getText().toString().toUpperCase();
             stringVALOR_DO_CURTO = RemoveTexto(valorVALOR_DO_CURTO, "V. CURTO: ",10);

             String valorDISTANCIA_SE = DISTANCIA.getText().toString().toUpperCase();
             stringDISTANCIA_DA_SE = RemoveTexto(valorDISTANCIA_SE, "DIST. SE: ",10);
             stringSTATUSLDF = "Atualizado às "+ funcoes.getHorario();



             if (TIPODOCURTO_MONO.isChecked()){
                 stringTIPO_DO_CURTO = "M";
                 TIPODOCURTO_BIFA.setChecked(false);
                 TIPODOCURTO_TRIFA.setChecked(false);
             }else if(TIPODOCURTO_BIFA.isChecked()){
                 stringTIPO_DO_CURTO = "B";
                 TIPODOCURTO_MONO.setChecked(false);
                 TIPODOCURTO_TRIFA.setChecked(false);
             }else if (TIPODOCURTO_TRIFA.isChecked()){
                 stringTIPO_DO_CURTO = "T";
                 TIPODOCURTO_MONO.setChecked(false);
                 TIPODOCURTO_BIFA.setChecked(false);
             }

             JSON = "{\"id\": \""+stringID+"\",\"cidade\": \""+stringCIDADE+"\",\"regiao\": \""+stringREGIAO+"\",\"alimentador\": \""+stringALM+"\",\"religador\": \""+stringRLG+"\",\"tombamento\": \""+stringCSI+"\",\n" +
                    "    \"valordocurto\": \""+stringVALOR_DO_CURTO+"\",\"tipodocurto\": \""+stringTIPO_DO_CURTO+"\",\"latitude\": \""+stringLATITUDE+"\",\"longitude\": \""+stringLONGITUDE+"\",\n" +
                    "    \"distancia\": \""+stringDISTANCIA_DA_SE+"\",\"registroativo\": \""+stringREGISTROATIVO+"\",\"dataatualizacao\":\""+stringDATAATUALIZACAO+"\",\"statusldf\": \""+stringSTATUSLDF+"\"}" ;

            ATUALIZA_LFD atualiza_lfd = new ATUALIZA_LFD(AtualizacaoLDFActivity.this);
            atualiza_lfd.execute();
            Toast.makeText(this, "ATUALIZANDO..."+ JSON, Toast.LENGTH_LONG).show();
        }else{
            AlertDialog dialog = new AlertDialog.Builder(AtualizacaoLDFActivity.this)
                    .setTitle("ATENÇÃO!").setIcon(R.drawable.ic_action_icon_csi)
                    .setMessage("A CAMPOS SEM VALORES, POR FAVOR VERIFIQUEOS E TENTE NOVAMENTE!\n"+JSON)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create();
            dialog.show();
        }

    }


    private void GeraAcoesBotoes(){

        if (TIPODOCURTO_MONO.isChecked()){
            stringTIPO_DO_CURTO = "M";

            TIPODOCURTO_BIFA.setChecked(false);
            TIPODOCURTO_TRIFA.setChecked(false);
        }else if(TIPODOCURTO_BIFA.isChecked()){
            stringTIPO_DO_CURTO = "B";
            TIPODOCURTO_MONO.setChecked(false);
            TIPODOCURTO_TRIFA.setChecked(false);
        }else if (TIPODOCURTO_TRIFA.isChecked()){
            stringTIPO_DO_CURTO = "T";
            TIPODOCURTO_MONO.setChecked(false);
            TIPODOCURTO_BIFA.setChecked(false);
        }

        imageView_ATT_MAP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AtualizacaoLDFActivity.this , "PRESSIONE O MAPA PARA OBTER A LOCALIZAÇÃO ATUAL", Toast.LENGTH_SHORT).show();
            }
        });

        imageView_ATT_MAP.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ObterLocalizacaoAtual();
                return true;
            }
        });

        buttonAtualizaLDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AtualizaLDFServidor();
            }
        });

        buttonTelaInicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void ApresentaValores(){
        ALM.setText("ALM: "+ stringALM);
        RLG.setText("RLG: "+ stringRLG);
        CSI.setText("CSI: "+ stringCSI);
        VALOR_DO_CURTO.setText("V. CURTO: "+stringVALOR_DO_CURTO);

        if (stringTIPO_DO_CURTO.equals("T")){
            TIPODOCURTO_TRIFA.setChecked(true);
            TIPODOCURTO_BIFA.setChecked(false);
            TIPODOCURTO_MONO.setChecked(false);
        }else if (stringTIPO_DO_CURTO.equals("B")){
            TIPODOCURTO_BIFA.setChecked(true);
            TIPODOCURTO_TRIFA.setChecked(false);
            TIPODOCURTO_MONO.setChecked(false);
        }else{
            TIPODOCURTO_MONO.setChecked(true);
            TIPODOCURTO_BIFA.setChecked(false);
            TIPODOCURTO_TRIFA.setChecked(false);
        }


        CIDADE.setText(stringCIDADE);
        REGIAO.setText(stringREGIAO);
        LAT.setText("LATI: "+stringLONGITUDE);
        LONGI.setText("LONGI: "+ stringLATITUDE);


        DISTANCIA.setText("DIST. SE: "+stringDISTANCIA_DA_SE);
        // if (registroAtivo.equals("1")){
        REGISTROATIVO.setChecked(true);
        // }else{
        // REGISTROATIVO.setChecked(false);
        // }
        DATAATT.setText("DATA ATUAL: "+stringDATAATUALIZACAO);


    }

    private void GeraLocalizador(){
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        mapFragment.getMapAsync(this);
    }

    private void ObterLocalizacaoAtual(){
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            String lat = String.valueOf(location.getLatitude());
                            String longi = String.valueOf(location.getLongitude());


                            Toast.makeText(AtualizacaoLDFActivity.this, "LATITUDE: "+lat+"\n"+"LONGITUDE: "+longi, Toast.LENGTH_SHORT).show();
                            LAT.setText("LATI: "+lat);
                            LONGI.setText("LONGI: "+ longi);

                            stringLONGITUDE = lat;
                            stringLATITUDE = longi;

                            GeraLocalizador();
                            // Logic to handle location object
                        }
                    }
                });

    }


    @Override
    public void onInfoWindowClick(Marker marker) {
        //ApresentaInfo();
        //CriaAcoesVerdadeiras();
        Toast.makeText(this, "Clique no botão rota para ir até o religador.",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        // Retrieve the data from the marker.
        Integer clickCount = (Integer) marker.getTag();
        // Check if a click count was set, then display the click count.
        if (clickCount != null) {
            clickCount = clickCount + 1;
            marker.setTag(clickCount);

            Toast.makeText(this,marker.getTitle() + " has been clicked " + clickCount + " times.", Toast.LENGTH_SHORT).show();

        }

        return false;

        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        double LONGI = Double.parseDouble(stringLONGITUDE);
        double LATI = Double.parseDouble(stringLATITUDE);

        int num = R.drawable.pino_maps;

        // Add a marker in Sydney and move the camera
        LatLng pino = new LatLng(LONGI, LATI);
        mLocalizacao = mMap.addMarker(new MarkerOptions().position(pino)
                .title("DADOS DO RELIGADOR: ")
                .snippet("DISTÂNCIA DA SE: " + stringDISTANCIA_DA_SE + " Km")
                .icon(BitmapDescriptorFactory.fromResource(num)));
        this.mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pino,16f));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLocalizacao.setTag(0);

        this.mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);


        mMap.setOnInfoWindowClickListener(this);
    }

    private class ATUALIZA_LFD extends AsyncTask<Void, Void, Void> {
        AtualizacaoLDFActivity atualizacaoLDFActivity ;

        Att_LDF att_ldf = new Att_LDF();
        String status, success, id, cidade, regiao, alimentador, religador,  tipoDeCurto, tombamento, valorDoCurto, lat, longi;
        String retornoMensagem;

        ProgressDialog load;
        public ATUALIZA_LFD(AtualizacaoLDFActivity atualizacaoLDFActivity) {
            // recupero a instancia da nossa Activity
            this.atualizacaoLDFActivity = atualizacaoLDFActivity;
        }
        // Aqui eh o que acontece antes da tarefa principal ser executado
        @Override
        protected void onPreExecute() {
            // o progressBar agora eh setado como visivel
            load = ProgressDialog.show(AtualizacaoLDFActivity.this, " Atualizando LDF...", "Enviando atualizações para o servidor...");
            buttonAtualizaLDF.setText("ATUALIZANDO LDF...");
        }

        @Override
        protected Void doInBackground(Void... params) {
            WebClient webClient = new WebClient(getApplicationContext());

            try {
                JSONObject DADOS_DO_LDF = new JSONObject(JSON);

                att_ldf = webClient.PUT_ATUALIZA_LDF(DADOS_DO_LDF);

                Log.e("VALOR5:", "" + att_ldf.getStatusATT().toString());
                if (att_ldf.getStatusATT()!=null){
                    retornoMensagem = att_ldf.getStatusATT();
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
            if(retornoMensagem.equals("1")){
                AlertDialog dialog = new AlertDialog.Builder(AtualizacaoLDFActivity.this)
                        .setTitle("SUCESSO!").setIcon(R.drawable.ic_action_icon_sucess)
                        .setMessage("O LOCALIZADOR FOI ATUALIZADO COM SUCESSO!")
                        .setPositiveButton("FECHAR", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                finish();
                            }
                        })
                        .create();
                dialog.show();
                buttonAtualizaLDF.setText("ATUALIZADO.");
            }else {
                //intent = new Intent(HomeActivity.this, CadastroColaboradorActivity.class);
                // intent.putExtra("BR0NOVO", BR0NOVO);
                //startActivity(intent);
                Toast.makeText(AtualizacaoLDFActivity.this,"LDF NÃO ATUALIZADO.",Toast.LENGTH_LONG).show();

            }


        }

    }
}
