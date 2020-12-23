package com.enel_locdefalta.MVC.VIEW.MAPS;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.enel_locdefalta.MVC.VIEW.RELATORIO.CadastroRelatorioActivity;
import com.enel_locdefalta.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MapsActivity extends FragmentActivity implements GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener, OnMapReadyCallback {

    private GoogleMap mMap;

    private FloatingActionButton geraRelatorio;
    private ImageView ImgViewVolta, ImgFundoAlerta, ImgSetaObterRota;
    private TextView textViewTexto_ObterRota, textViewALM, textViewRLG, textViewCSI, textViewTipoDoCurto, textViewValorDoCurto;

    Intent intent;

    String cidade, alimentador, religador, tombamento, tipo_de_curto, valor_do_curto, latitude, longitude, distancia;

    private Marker mLocalizacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_maps_mobile);
        setContentView(R.layout.activity_maps_tablet);
        intent = getIntent();

        final String CITY = intent.getStringExtra("CIDADE");
        final String ALM = intent.getStringExtra("ALM");
        final String RLG = intent.getStringExtra("RLG");
        final String TOMBAMENTO = intent.getStringExtra("TOMBAMENTO");
        final String TIPO_DE_CURTO = intent.getStringExtra("TIPO_DE_CURTO");
        final String VALOR_DO_CURTO = intent.getStringExtra("VALOR_DO_CURTO");
        final String LAT = intent.getStringExtra("LAT");
        final String LONG = intent.getStringExtra("LONG");
        final String DISTANCIA = intent.getStringExtra("DISTANCIA");
        final String BR0COLABORADOR = intent.getStringExtra("BR0COLABORADOR");

        cidade = CITY;
        alimentador = ALM;
        religador = RLG;
        tombamento = TOMBAMENTO;
        tipo_de_curto = TIPO_DE_CURTO;
        valor_do_curto = VALOR_DO_CURTO;
        latitude = LAT;
        longitude = LONG;
        distancia = BR0COLABORADOR;//DISTANCIA;

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //TextViews
        textViewALM = findViewById(R.id.textView_maps_ALM);
        textViewRLG = findViewById(R.id.textView_maps_RLG);
        textViewCSI = findViewById(R.id.textView_maps_CSI);
        textViewTipoDoCurto = findViewById(R.id.textView_maps_TipoDeCurto);
        textViewValorDoCurto = findViewById(R.id.textView_maps_ValorDoCurto);

        textViewTexto_ObterRota = findViewById(R.id.textView_maps_texto_mostra_rota);

        geraRelatorio = findViewById(R.id.floatingActionCadastroRelatorio);
        ImgViewVolta = findViewById(R.id.imageViewButonVoltar);
        ImgFundoAlerta = findViewById(R.id.imageView_maps_fundo_alerta);
        ImgSetaObterRota = findViewById(R.id.imageView_maps_seta_mostra_rota);

        //this.SalvarHistorio();

        this.CriaEventos();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        double lat = Double.parseDouble(latitude);
        double lon = Double.parseDouble(longitude);

        int num = R.drawable.pino_maps;

        // Add a marker in Sydney and move the camera
        LatLng pino = new LatLng(lon, lat);
        mLocalizacao = mMap.addMarker(new MarkerOptions().position(pino)
                .title("DADOS DO RELIGADOR: " + religador)
                .snippet("ALM: " + alimentador + "  |  " +
                        "CSI: " + tombamento + "  |  " +
                        "CURTO: " + tipo_de_curto + "  |  " +
                        "VALOR: " + valor_do_curto + "  |  " +
                        "DIST: " + distancia + " Km")
                .icon(BitmapDescriptorFactory.fromResource(num)));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(pino));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pino,16f));

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
        mMap.setMyLocationEnabled(true);
        //mMap.setMaxZoomPreference(16);
        mLocalizacao.setTag(0);

        // Set a listener for marker click.
        //mMap.setOnMarkerClickListener(this);
        mMap.setOnInfoWindowClickListener(this);
    }


    @Override
    public boolean onMarkerClick(final Marker marker) {
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
    public void onInfoWindowClick(Marker marker) {
        ApresentaInfo();
        CriaAcoesVerdadeiras();
        Toast.makeText(this, "Clique no botão rota para ir até o religador.",
                Toast.LENGTH_SHORT).show();
    }

    private void CriaAcoesFalsas(){
        ImgFundoAlerta.setVisibility(View.INVISIBLE);
        ImgSetaObterRota.setVisibility(View.INVISIBLE);
        textViewTexto_ObterRota.setVisibility(View.INVISIBLE);
    }

    private void CriaAcoesVerdadeiras(){
        ImgFundoAlerta.setVisibility(View.VISIBLE);
        ImgSetaObterRota.setVisibility(View.VISIBLE);
        textViewTexto_ObterRota.setVisibility(View.VISIBLE);
    }

    private void CriaEventos(){
        //Ação cadastrar relatorio
        geraRelatorio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MapsActivity.this, CadastroRelatorioActivity.class);
                intent.putExtra("CIDADE",cidade);
                intent.putExtra("ALM",alimentador);
                intent.putExtra("RLG", religador);
                intent.putExtra("TOMBAMENTO", tombamento);
                intent.putExtra("TIPO_DE_CURTO", tipo_de_curto);
                intent.putExtra("VALOR_DO_CURTO", valor_do_curto);
                intent.putExtra("LAT", latitude);
                intent.putExtra("LONG", longitude);
                intent.putExtra("DISTANCIA", distancia);
                //intent.putExtra("BROCOLABORADOR",colaborador);
                startActivity(intent);
                finish();
            }
        });

        //Ação voltar tela consulta
        ImgViewVolta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void ApresentaInfo(){
        textViewCSI.setText("CSI: "+tombamento);
        textViewRLG.setText("RLG: " +religador);
        textViewALM.setText("ALM: "+alimentador);
        if(tipo_de_curto.equals("M")){
            textViewTipoDoCurto.setText("T. DO CURTO: M");
        }else if (tipo_de_curto.equals("B")){
            textViewTipoDoCurto.setText("T. DO CURTO: B");
        }else{
            textViewTipoDoCurto.setText("T. DO CURTO: T");
        }
        textViewValorDoCurto.setText("V. DO CURTO: "+valor_do_curto+"A");
    }

}