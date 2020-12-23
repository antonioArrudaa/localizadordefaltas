package com.enel_locdefalta.MVC.VIEW.RELATORIO;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.enel_locdefalta.MVC.MODEL.RELATORIO.RelatorioLdFObjModel;
import com.enel_locdefalta.MVC.SERVIDOR_e_BD.VerificaRede;
import com.enel_locdefalta.MVC.SERVIDOR_e_BD.WebClient;
import com.enel_locdefalta.R;
import com.enel_locdefalta.utils.Uteis;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class ApresentaRelatorioActivity extends AppCompatActivity implements OnMapReadyCallback {
    //Importantes
    Intent intent;
    Bitmap raw;
    //ImageView's
    ImageView imageViewcClose, imageViewFoto1,imageViewFoto2,imageViewFoto3,imageViewFoto4,imageViewFoto5, ButtonUpLoad;

    //TextView's
    TextView textViewNome_cidade, textViewNome_titulo_csi, textViewNome_alm, textViewNome_rlg, textViewNome_tipo_de_curto,
            textViewNome_valor_de_curto, textViewDescricao_ocorrencia, textViewBrColaborador, textViewNomeData;

    //Declaracoes de tipos
    byte[] fotoArray1,fotoArray2,fotoArray3,fotoArray4,fotoArray5;

    String titulo,cidade,regiao, alimentador,religador,tipoDeCurto,valorDeCurto,descricaoOcorrencia,brColaborador,dataOcorrencia,hora,lat, longi,dist, statusRelatorio;

    private ProgressDialog load;

    String json;

    GoogleMap googleMap;
    double latitude, longitude;
    String CSI;
    private Marker mLocalizacao;


    Button buttonALM, buttonRLG, buttonV_Curto, buttonCSI, buttonT_Curto, buttonBR, buttonUpLoadRelatorio;

    Uteis data = new Uteis();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.relatorio_activity_apresenta_relatorio);
        //setContentView(R.layout.new_relatorio_activity_apresenta_relatorio_mobile);
        setContentView(R.layout.new_relatorio_activity_apresenta_relatorio_tablet);
        final GetJsonRelatorio upload = new GetJsonRelatorio();
        //ImageView's
        TextView datadispositivo = findViewById(R.id.textViewDataEnel);
        datadispositivo.setText("enel "+data.RetornaData());

        CarregaComponentes();

        imageViewcClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Recuperando os dados da intent
        intent = getIntent();

        final String TITLE = intent.getStringExtra("CSI");
        final String CITY = intent.getStringExtra("CITY");
        //final String REGIAO = intent.getStringExtra("REGIAO");
        final String ALM = intent.getStringExtra("ALM");
        final String RLG = intent.getStringExtra("RLG");
        final String CSI = intent.getStringExtra("CSI");

        final String TPCURTO = intent.getStringExtra("TPCURTO");
        final String VLCURTO = intent.getStringExtra("VLCURTO");
        final String LAT = intent.getStringExtra("LAT");
        final String LONG = intent.getStringExtra("LONG");
        final String DISTANCIA = intent.getStringExtra("DISTANCIA");
        final String DESCRIPTION = intent.getStringExtra("DESCRIPTION");
        final byte[] FOTO1 = intent.getByteArrayExtra("FOTO1");
        final byte[] FOTO2 = intent.getByteArrayExtra("FOTO2");
        final byte[] FOTO3 = intent.getByteArrayExtra("FOTO3");
        final byte[] FOTO4 = intent.getByteArrayExtra("FOTO4");
        final byte[] FOTO5 = intent.getByteArrayExtra("FOTO5");
        final String BRCOLABORADOR = intent.getStringExtra("BR0");
        final String DATAOC = intent.getStringExtra("DATAOC");
        final String HORAOC = intent.getStringExtra("HORAOC");


        final String STATUS_RELATORIO = intent.getStringExtra("STATUSRELATORIO");

        titulo = TITLE;
        cidade =  CITY;
        regiao = "REGIAO";
        alimentador = ALM;
        religador = RLG;
        tipoDeCurto = TPCURTO;
        this.CSI = CSI;
        valorDeCurto = VLCURTO;
        lat = LAT;
        longi = LONG;
        dist = DISTANCIA;
        descricaoOcorrencia = DESCRIPTION;
        brColaborador = BRCOLABORADOR;
        dataOcorrencia = DATAOC;
        hora = HORAOC;

        statusRelatorio = STATUS_RELATORIO;

        fotoArray1 = FOTO1;
        fotoArray2 = FOTO2;
        fotoArray3 = FOTO3;
        fotoArray4 = FOTO4;
        fotoArray5 = FOTO5;

        //Apresentando dados
        textViewNome_titulo_csi.setText("OCORRÊNCIA "+titulo);
        textViewNome_cidade.setText(cidade+"/Ce");
        buttonALM.setText("ALM: "+alimentador);
        buttonRLG.setText("RLG: "+religador);
        buttonCSI.setText("CSI: "+CSI);
        buttonBR.setText("RESP: BR0"+brColaborador);

        if(tipoDeCurto.equals("M")){
            buttonT_Curto.setText("T.CURTO: Monofásico");
        }else if(tipoDeCurto.equals("B")){
            buttonT_Curto.setText("T.CURTO: Bifásico");
        }else{
            buttonT_Curto.setText("T.CURTO: Trifásico");
        }
        buttonV_Curto.setText("V.CURTO: "+valorDeCurto+"A");
        textViewDescricao_ocorrencia.setText(descricaoOcorrencia);
        textViewNomeData.setText(dataOcorrencia+" às "+ hora);

        if(FOTO1!=null){
            raw  = BitmapFactory.decodeByteArray(FOTO1,0,FOTO1.length);
            imageViewFoto1.setImageBitmap(raw);
        }
        if (FOTO2!=null){
            raw  = BitmapFactory.decodeByteArray(FOTO2,0,FOTO2.length);
            imageViewFoto2.setImageBitmap(raw);
        }
        if (FOTO3!=null){
            raw  = BitmapFactory.decodeByteArray(FOTO3,0,FOTO3.length);
            imageViewFoto3.setImageBitmap(raw);
        }
        if (FOTO4!=null){
            raw  = BitmapFactory.decodeByteArray(FOTO4,0,FOTO4.length);
            imageViewFoto4.setImageBitmap(raw);
        }
        if (FOTO5!=null){
            raw  = BitmapFactory.decodeByteArray(FOTO5,0,FOTO5.length);
            imageViewFoto5.setImageBitmap(raw);
        }else{
            Toast.makeText(this,"Algumas imagens não carregaran",Toast.LENGTH_LONG).show();
        }

        if (statusRelatorio.equals("1")) {
            buttonUpLoadRelatorio.setCompoundDrawables(null, getDrawable(R.drawable.ic_action_icon_sucess), null, null);
            Toast.makeText(ApresentaRelatorioActivity.this, "ESTE RELATÓRIO JA FOI ENVIADO AO SERVIDOR", Toast.LENGTH_SHORT).show();

            buttonUpLoadRelatorio.setEnabled(false);
        }else{
            buttonUpLoadRelatorio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(ApresentaRelatorioActivity.this, "Enviando relatório ao Servidor", Toast.LENGTH_LONG).show();
                    json = "{\n" +
                            "   \"cidade\": \"" + cidade + "\",\n" +
                            "   \"regiao\": \"" + regiao + "\",\n" +
                            "   \"alimentador\": \"" + alimentador + "\",\n" +
                            "   \"religador\": \"" + religador + "\",\n" +
                            "   \"tombamento\": \"" + titulo + "\",\n" +
                            "   \"valordocurto\": \"" + valorDeCurto + "\",\n" +
                            "   \"tipodocurto\": \"" + tipoDeCurto + "\",\n" +
                            "   \"latitude\": \"" + lat + "\",\n" +
                            "   \"longitude\": \"" + longi + "\",\n" +
                            "   \"distancia\": \"" + dist + "\",\n" +
                            "   \"descricao\": \"" + descricaoOcorrencia + "\",\n" +
                            "   \"br\": \"" + brColaborador + "\",\n" +
                            "   \"data\": \"" + dataOcorrencia + "\",\n" +
                            "   \"hora\": \"" + hora + "\",\n" +
                            "   \"foto1\": \"foto1\",\n" +
                            "   \"foto2\": \"foto2\",\n" +
                            "   \"foto3\": \"foto3\",\n" +
                            "   \"foto4\": \"foto4\",\n" +
                            "   \"foto5\": \"foto5\",\n" +
                            "   \"registroativo\": \"1\"\n" +
                            "}";

                    upload.execute();
                }
            });

        }


    }

    private void CarregaComponentes(){
        textViewNome_titulo_csi = findViewById(R.id.card_historico_nomeCSI2);
        textViewNome_cidade = findViewById(R.id.card_historico_nome_cidade2);
        textViewNomeData = findViewById(R.id.textViewDATA_HORA_registro2);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this);

        textViewDescricao_ocorrencia = findViewById(R.id.textView_NEW_apresenta_relatorio_DESCRICAO_OCORRENCIA);

        buttonALM = findViewById(R.id.button_NEW_card_ALIMENTADOR);
        buttonRLG = findViewById(R.id.button_NEW_card_RELIGADOR);
        buttonCSI = findViewById(R.id.button_NEW_card_CSI);
        buttonV_Curto = findViewById(R.id.button_NEW_card_V_CURTO);
        buttonT_Curto = findViewById(R.id.button_NEW_card_T_CURTO);
        buttonBR = findViewById(R.id.button_NEW_card_BR);
        buttonUpLoadRelatorio = findViewById(R.id.button_UpLoadRelatorio);


        imageViewFoto1 = findViewById(R.id.imageView_NEW_IMAGEM1);
        imageViewFoto2 = findViewById(R.id.imageView_NEW_IMAGEM2);
        imageViewFoto3 = findViewById(R.id.imageView_NEW_IMAGEM3);
        imageViewFoto4 = findViewById(R.id.imageView_NEW_IMAGEM4);
        //imageViewFoto5 = findViewById(R.id.imageView_NEW_IMAGEM5);

        imageViewcClose = findViewById(R.id.imageViewVoltar);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        double lat1 = Double.parseDouble(String.valueOf(lat));
        double longit1 = Double.parseDouble(String.valueOf(longi));
        String TOMBAMENTO = CSI;

        int num = R.drawable.pino_maps;

        LatLng pino = new LatLng(longit1, lat1);
        mLocalizacao = this.googleMap.addMarker(new MarkerOptions().position(pino)
                .title("DISTÂNCIA DA SE: " + dist+"Km")
                .icon(BitmapDescriptorFactory.fromResource(num)));
        this.googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pino,16f));

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
    }

    private class GetJsonRelatorio extends AsyncTask<Void, Void, RelatorioLdFObjModel> {
        String retornoErro=null;
        RelatorioLdFObjModel userColaboradorModel;

        @Override
        protected void onPreExecute(){

            load = ProgressDialog.show(ApresentaRelatorioActivity.this, " Aguarde ...", "Enviando relatório" +
                    " para nosso servidor...");
        }

        @Override
        protected RelatorioLdFObjModel doInBackground(Void... params) {
            String retorna_error=null;
            RelatorioLdFObjModel relatorioLdFObjModel;

            WebClient webClient = new WebClient(getApplicationContext());

            if(VerificaRede.isConnected(ApresentaRelatorioActivity.this)){
                try {
                    JSONObject relatorio = new JSONObject(json);
                    Thread.sleep(2000);
                    relatorioLdFObjModel = webClient.Post_RELATORIO(relatorio);

                    //Log.println(Log.INFO, "RELATORIOERROR: ", relatorioLdFObjModel.getMensagemError());
                    //Log.e("RELATORIOERROR", relatorioLdFObjModel.getMensagemError().toString());
                    if (relatorioLdFObjModel.getMensagemError()!=null){
                        retornoErro = relatorioLdFObjModel.getMensagemError();
                        return relatorioLdFObjModel;
                    }else{
                        retornoErro = "Sucess";

                        return null;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }
            return null;
        }

        @Override
        protected void onPostExecute(RelatorioLdFObjModel relatorioLdFObjModel){
            load.dismiss();
            if (retornoErro.equals("VAZIO")){
                Toast.makeText(ApresentaRelatorioActivity.this, "ERROR AO ENVIAR O RELATÓRIO", Toast.LENGTH_SHORT).show();
            }else{

                Toast.makeText(ApresentaRelatorioActivity.this, "SUCESSO", Toast.LENGTH_SHORT).show();
                Uteis.Alert(ApresentaRelatorioActivity.this,"SUCESSO: O Relatório Foi Enviado!");
            }

        }
    }
}
