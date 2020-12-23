package com.enel_locdefalta.MVC.VIEW.RELATORIO;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.enel_locdefalta.MVC.CONTROLLER.ACOES.Funcoes;
import com.enel_locdefalta.MVC.CONTROLLER.Login_e_Usuario.UserColaboradorRepository;
import com.enel_locdefalta.MVC.CONTROLLER.RELATORIO.RelatorioLDfRepository;
import com.enel_locdefalta.MVC.MODEL.Login.UserColaboradorModel;
import com.enel_locdefalta.MVC.MODEL.RELATORIO.RelatorioLdFObjModel;
import com.enel_locdefalta.MVC.VIEW.CONSULTALDF.ConsultarActivity;
import com.enel_locdefalta.R;
import com.enel_locdefalta.utils.Uteis;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CadastroRelatorioActivity extends AppCompatActivity {
    Uteis funcoesEspeciais =  new Uteis();
    Funcoes funcoes;

    Button btn_new_register, btn_inicio;
    ImageView btn_voltar, capture_imageView1, capture_imageView2, capture_imageView3, capture_imageView4, capture_imageView5;
    private static final int CAMERA = 1;
    private String Hora,Data;

    FloatingActionButton salvarDadosRelatorio;

    String cidade, alimentador, religador, tombamento, valor_do_curto, latitude, longitude, distancia,BR0Colaborador, statusRelatorio;

    String tipo_de_curto ="";

    TextView cidadeV, alimentadorV, religadorV, tombamentoV, tipo_de_curtoV, valor_do_curtoV, hora, data, BRO, textView_Texto_Salvar;

    EditText CapturaDescricao;

    ImageButton inseri_texto;

    Intent intent;

    String DescricaoRelatorio = "";

    String captura_da_Foto="";


    private final int GALERIA_IMAGENS = 1;
    private final int PERMISSAO_REQUEST = 2;
    private final int TIRAR_FOTO = 3;

    byte[] imageByte1, imageByte2,imageByte3,imageByte4,imageByte5;

    UserColaboradorModel userColaboradorModel = new UserColaboradorModel();
    UserColaboradorRepository userColaboradorRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.relatorio_activity_cadastro_relatorio_mobile);
        setContentView(R.layout.relatorio_activity_cadastro_relatorio_tablet);
        userColaboradorRepository = new UserColaboradorRepository(this);

        userColaboradorModel = userColaboradorRepository.CarregaDadosColaboradoes("1");


        TextView datadispositivo = findViewById(R.id.textViewDataEnel);
        datadispositivo.setText("enel "+funcoesEspeciais.RetornaData());

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

        cidade =  CITY;
        alimentador = ALM;
        religador =RLG;
        tombamento = TOMBAMENTO;
        tipo_de_curto = TIPO_DE_CURTO;
        valor_do_curto = VALOR_DO_CURTO;
        latitude = LAT;
        longitude = LONG;
        distancia = DISTANCIA;
        statusRelatorio = "0";
        Hora =  funcoesEspeciais.RetornaHora("HH:mm",this,"Erro ao inserir formato da hora");
        Data = funcoesEspeciais.RetornaData();
        BR0Colaborador = "teste"+userColaboradorModel.getBR();//"BR0069963473";//BRO.getText().toString();


        cidadeV = findViewById(R.id.relatorio_consulta_CIDADE);
        cidadeV.setText(cidade);
        alimentadorV = findViewById(R.id.relatorio_consulta_ALM);
        alimentadorV.setText(alimentador);
        religadorV = findViewById(R.id.relatorio_consulta_RLG);
        religadorV.setText(religador);
        tombamentoV = findViewById(R.id.relatorio_consulta_TOMBAMENTO);
        tombamentoV.setText(tombamento);
        tipo_de_curtoV = findViewById(R.id.relatorio_consulta_TIPO_DE_CURTO);

        VerificaTipo();

        valor_do_curtoV = findViewById(R.id.relatorio_consulta_VALOR_DE_CURTO);
        valor_do_curtoV.setText(valor_do_curto+"A");


        hora = findViewById(R.id.relatorio_consulta_Hora);
        hora.setText(Hora);
        data = findViewById(R.id.relatorio_consulta_Data);
        data.setText(Data);
        BRO = findViewById(R.id.relatorio_consulta_BR_Colaborador);
        BRO.setText(BR0Colaborador);

        //INSERIR O FIND BRO


        CapturaDescricao = (EditText) findViewById(R.id.relatorio_consulta_descricao);

        textView_Texto_Salvar = findViewById(R.id.textView_Texto_Salvar);

        btn_voltar = findViewById(R.id.btn_voltar);

        btn_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Voltar(v);
            }
        });


        inseri_texto = findViewById(R.id.imageButton_Inseri_texto);

        inseri_texto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CapturaDescricao.getText().toString().equals("")){
                    CapturaDescricao.setError("Colaborador, Insira a descrição da ocorrência!");
                    Uteis.Alert(CadastroRelatorioActivity.this,"Ocorrência vazia!");
                    //CapturaDescricao.setFocusable(true);
                }
                DescricaoRelatorio = CapturaDescricao.getText().toString();
                Uteis.Alert(CadastroRelatorioActivity.this,DescricaoRelatorio);
            }
        });


        salvarDadosRelatorio = findViewById(R.id.fab_save_tool_files);
        salvarDadosRelatorio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(CapturaDescricao.getText().toString().equals("")){
                    CapturaDescricao.setError("Insira detalhes da ocorrência");
                }
                if(DescricaoRelatorio!=null&& !DescricaoRelatorio.equals("")){
                    Snackbar.make(view, "Salvando Relatorio...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                    SalvarHistorio();
                    LimpaCampos(view);
                    Snackbar.make(view, "Relatório Salvo! -> valor Status: " +statusRelatorio, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    //EncerraTarefa(view);


                }
            }
        });

        btn_inicio = findViewById(R.id.buttonINICIO);

        btn_inicio.setVisibility(View.INVISIBLE);
        btn_inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSAO_REQUEST);
            }
        }
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSAO_REQUEST);
            }
        }

        this.CarregaComponents_e_Acoes();

    }

    private void CarregaComponents_e_Acoes(){
        //imagens de captura
        capture_imageView1 = findViewById(R.id.Capture_imageView1);
        capture_imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CadastroRelatorioActivity.this,"Teste1", Toast.LENGTH_LONG).show();
                AbrirCaptura();
                captura_da_Foto = "FOTO1";
            }
        });
        capture_imageView2 = findViewById(R.id.Capture_imageView2);
        capture_imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CadastroRelatorioActivity.this,"Teste2", Toast.LENGTH_LONG).show();
                captura_da_Foto = "FOTO2";
                AbrirCaptura();
            }
        });
        capture_imageView3 = findViewById(R.id.Capture_imageView3);
        capture_imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CadastroRelatorioActivity.this,"Teste3", Toast.LENGTH_LONG).show();
                captura_da_Foto = "FOTO3";
                AbrirCaptura();
              //  AbrirCaptura3();
            }
        });
        capture_imageView4 = findViewById(R.id.Capture_imageView4);
        capture_imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CadastroRelatorioActivity.this,"Teste4", Toast.LENGTH_LONG).show();
                captura_da_Foto = "FOTO4";
                AbrirCaptura();
               // AbrirCaptura4();
            }
        });
        capture_imageView5 = findViewById(R.id.Capture_imageView5);
        capture_imageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CadastroRelatorioActivity.this,"Teste5", Toast.LENGTH_LONG).show();
                captura_da_Foto = "FOTO5";
                AbrirCaptura();
                //AbrirCaptura5();
            }
        });

        //Texto de campus


    }

    private void VerificaTipo(){
        if(tipo_de_curto.equals("M")){
            tipo_de_curtoV.setText(tipo_de_curto+"onofásico");
        }else if (tipo_de_curto.equals("B")){
            tipo_de_curtoV.setText(tipo_de_curto+"ifásico");
        }else if (tipo_de_curto.equals("T")){
            tipo_de_curtoV.setText(tipo_de_curto+"rifásico");
        }else{
            tipo_de_curtoV.setText("nulo");
        }
    }

    @Override
    protected void onActivityResult ( int requestCode ,int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == GALERIA_IMAGENS) {
            Uri selectedImage = data.getData();
            String[] filePath = { MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage,filePath, null,null,null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePath[0]);
            String picturePath  = c.getString(columnIndex);
            c.close();

            //Pega imagem da galeria


        }
        if(requestCode == TIRAR_FOTO && resultCode == RESULT_OK){
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(arquivoFoto)));

            if(captura_da_Foto.equals("FOTO1")){
                //Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                exibirImagem();
                //Toast.makeText(this,"Apresentando Foto 1", Toast.LENGTH_LONG).show();
                //capture_imageView1.setImageBitmap(thumbnail);
            }else if(captura_da_Foto.equals("FOTO2")){
                exibirImagem();
                //Toast.makeText(this,"Apresentando Foto 2", Toast.LENGTH_LONG).show();
            }else if(captura_da_Foto.equals("FOTO3")){
                exibirImagem();
                //Toast.makeText(this,"Apresentando Foto 3", Toast.LENGTH_LONG).show();
            }else if(captura_da_Foto.equals("FOTO4")){
                exibirImagem();
                //Toast.makeText(this,"Apresentando Foto 4", Toast.LENGTH_LONG).show();
            }else if(captura_da_Foto.equals("FOTO5")){
                exibirImagem();
                //Toast.makeText(this,"Apresentando Foto 5", Toast.LENGTH_LONG).show();
            }
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode , String permissions [], int []grantResults) {
        if( requestCode == PERMISSAO_REQUEST ) {
            if ( grantResults.length>0 && grantResults [0] == PackageManager.PERMISSION_GRANTED) {
                // A permissão foi concedida. Pode continuar
                Toast.makeText(this,"Autorizando Recursos...", Toast.LENGTH_LONG).show();
            }else{
                // A permissão foi negada. Precisa ver o que deve ser desabilitado
                //finish();
                Toast.makeText(this,"Recursos Não Autorizados!", Toast.LENGTH_LONG).show();
            }
            return;
        }
    }

    private File arquivoFoto = null;

    private File criaArquivo() throws IOException{
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmm").format(new Date());

        File pasta = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        File imagem = new File(pasta.getPath()+File.separator+"JPG_ENVIDENCIA_DO_"+alimentador+"_"+religador+"_"+tombamento+"_"+timeStamp+".jpg");

        Toast.makeText(this, "LIGANDO CAMERA DE CAPTURA DE EVIDÊNCIA...", Toast.LENGTH_LONG).show();

        return imagem;
    }

    private void AbrirCaptura() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePictureIntent.resolveActivity (getPackageManager()) != null) {
            try{
                arquivoFoto = criaArquivo();
            }catch (Exception e){
                //ERROR
            }
            if (arquivoFoto!=null){
                Uri phothoURI = FileProvider.getUriForFile(getBaseContext(), getBaseContext().getApplicationContext()
                        .getPackageName()+".provider",arquivoFoto);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, phothoURI);
                startActivityForResult ( takePictureIntent, TIRAR_FOTO);
            }
        }
    }

    private void exibirImagem(){

        int targetW = capture_imageView1.getWidth();
        int targetH = capture_imageView1.getHeight();

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(arquivoFoto.getAbsolutePath(),bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int scala = Math.min(photoW/targetW, photoH/targetH);
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scala;
        //Bundle extras = data.getExtras();

        Bitmap imagem = BitmapFactory.decodeFile(arquivoFoto.getAbsolutePath(),bmOptions);
        //capture_imageView1.setImageBitmap(imagem);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        imagem.compress(Bitmap.CompressFormat.JPEG,100,stream);
        //imagem.compress(Bitmap.CompressFormat.JPEG,100, stream);

        if(captura_da_Foto.equals("FOTO1")){

            imageByte1 = stream.toByteArray();
            Toast.makeText(this,"Foto 1 da Evidência Salva!", Toast.LENGTH_LONG).show();
            capture_imageView1.setImageBitmap(imagem);
            //capture_imageView1.setImageBitmap(thumbnail);
        }else if(captura_da_Foto.equals("FOTO2")){

            imageByte2 = stream.toByteArray();
            Toast.makeText(this,"Foto 2 da Evidência Salva!", Toast.LENGTH_LONG).show();
            capture_imageView2.setImageBitmap(imagem);
        }else if(captura_da_Foto.equals("FOTO3")){

            imageByte3 = stream.toByteArray();
            Toast.makeText(this,"Foto 3 da Evidência Salva!", Toast.LENGTH_LONG).show();
            capture_imageView3.setImageBitmap(imagem);
        }else if(captura_da_Foto.equals("FOTO4")){

            imageByte4 = stream.toByteArray();
            Toast.makeText(this,"Foto 4 da Evidência Salva!", Toast.LENGTH_LONG).show();
            capture_imageView4.setImageBitmap(imagem);
        }else if(captura_da_Foto.equals("FOTO5")){

            imageByte5 = stream.toByteArray();
            Toast.makeText(this,"Foto 5 da Evidência Salva!", Toast.LENGTH_LONG).show();
            capture_imageView5.setImageBitmap(imagem);
        }

    }

    protected void SalvarHistorio(){

        /*CRIANDO UM OBJETO LDF*/
        RelatorioLdFObjModel relatorioLdFObjModel = new RelatorioLdFObjModel();

        relatorioLdFObjModel.setBRColaborador(BR0Colaborador);
        relatorioLdFObjModel.setCidade(cidade);
        relatorioLdFObjModel.setAlimentador(alimentador);
        relatorioLdFObjModel.setReligador(religador);
        relatorioLdFObjModel.setTombamento(tombamento);
        relatorioLdFObjModel.setTipoDeCurto(tipo_de_curto);
        relatorioLdFObjModel.setValorDoCurto(valor_do_curto);
        relatorioLdFObjModel.setLATITUDE(latitude);
        relatorioLdFObjModel.setLONGITUDE(longitude);
        relatorioLdFObjModel.setDISTANCIA(distancia);
        if(imageByte1!=null) {
            relatorioLdFObjModel.setFoto1(imageByte1);
        }else{
            Toast.makeText(this,"FALHA AO SALVAR FOTO 1",Toast.LENGTH_LONG).show();
        }
        if(imageByte2!=null) {
            relatorioLdFObjModel.setFoto2(imageByte2);
        }else{
            Toast.makeText(this,"FALHA AO SALVAR FOTO 1 2",Toast.LENGTH_LONG).show();
        }
        if(imageByte3!=null) {
            relatorioLdFObjModel.setFoto3(imageByte3);
        }else{
            Toast.makeText(this,"FALHA AO SALVAR FOTO 3",Toast.LENGTH_LONG).show();
        }
        if(imageByte4!=null) {
            relatorioLdFObjModel.setFoto4(imageByte4);
        }else{
            Toast.makeText(this,"FALHA AO SALVAR FOTO 4",Toast.LENGTH_LONG).show();
        }
        if(imageByte5!=null) {
            relatorioLdFObjModel.setFoto5(imageByte5);
        }else{
            Toast.makeText(this,"FALHA AO SALVAR FOTO 5",Toast.LENGTH_LONG).show();
        }

        relatorioLdFObjModel.setDescricaoRelatorio(DescricaoRelatorio);
        relatorioLdFObjModel.setBRColaborador(BR0Colaborador);
        relatorioLdFObjModel.setData(Data);
        relatorioLdFObjModel.setStatusRelatorio(statusRelatorio);
        relatorioLdFObjModel.setHorario(Hora);



        new RelatorioLDfRepository(this).SalvarRelatorio(relatorioLdFObjModel);



    }



    private void LimpaCampos(View v){
        CapturaDescricao.setEnabled(false);
        inseri_texto.setEnabled(false);
        salvarDadosRelatorio.setEnabled(false);
        textView_Texto_Salvar.setText("SALVO!");
        btn_inicio.setVisibility(View.VISIBLE);


        //NovaConsulta(v);
    }

    private void Voltar(View view) {
        finish();
    }

    private void NovaConsulta(View view) {
        intent = new Intent(this, ConsultarActivity.class);
        startActivity(intent);
        finish();
    }

}
