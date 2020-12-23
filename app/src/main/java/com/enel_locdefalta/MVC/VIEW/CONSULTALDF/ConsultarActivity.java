package com.enel_locdefalta.MVC.VIEW.CONSULTALDF;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import com.enel_locdefalta.MVC.CONTROLLER.AJUSTES.DOWNLOAD_ALM_E_RLG_Repository;
import com.enel_locdefalta.MVC.CONTROLLER.AJUSTES.DOWNLOAD_LDF_Repository;
import com.enel_locdefalta.MVC.CONTROLLER.CONSULTALDF.LinhaConsultarAdapter;
import com.enel_locdefalta.MVC.MODEL.AJUSTES.ConsultaLdFObjModel;
import com.enel_locdefalta.MVC.MODEL.AJUSTES.ObjModel_ALM_RLG;
import com.enel_locdefalta.R;
import com.enel_locdefalta.utils.Uteis;

import java.util.ArrayList;
import java.util.List;

public class ConsultarActivity extends Activity implements AdapterView.OnItemSelectedListener {

    Switch monofasico, bifasico, trifasico, cinco_porcento,dez_porcento;
    Spinner alimentador, religador;
    TextView resultadoDaBuscaNegativa, resultado_para_mais, resultado_para_menos, valor_de_curto_informado, View_Title_Resultado_Para_Mais,View_Title_Resultado_Para_Menos;
    View curtoinformado;
    EditText valor_de_curto;

    ImageView voltar;
    Button gerar_ldf;

    String curto = null;

    private int progressStatus = 0;
    ProgressBar progressBarCarregaLDFSpiner ,progressBarCarregaLDFLinha;

    private Handler handler = new Handler();

    int curtoConvertido;

    int intervalo1_calculo_do_resultado_do_valor_do_curto_para_mais = 0;
    int intervalo2_calculo_do_resultado_do_valor_do_curto_para_menos = 0;

    //CRIANDO UM OBJETO DO TIPO ListView PARA RECEBER OS REGISTROS DE UM ADAPTER
    ListView listViewLDF;
    Uteis l = new Uteis();

    //Variaveis para realizar a consulta;
    String CONS_VALOR_INICIAL, CONS_VALOR_FINAL, CONS_ALIMENTADOR, CONS_RELIGADOR, CONS_TIPO_DE_CURTO;

    DOWNLOAD_LDF_Repository DOWNLOADLDFRepository =  new DOWNLOAD_LDF_Repository(this);
    private List<ConsultaLdFObjModel> consultaLdFObjModels;
    private LinhaConsultarAdapter linhaConsultarAdapter;


    Uteis data = new Uteis();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_tablet);
        //setContentView(R.layout.activity_consultar_mobile);

        TextView datadispositivo = findViewById(R.id.textViewDataEnel);
        datadispositivo.setText("enel "+data.RetornaData());


        voltar = findViewById(R.id.imgView_btn_voltar);
        gerar_ldf = findViewById(R.id.btn_gera_ldf);
        gerar_ldf.setEnabled(false);

        //ProgressBar
        progressBarCarregaLDFSpiner = findViewById(R.id.progressBarCarregaLDFS);
        progressBarCarregaLDFSpiner.setVisibility(View.INVISIBLE);

        //Spinnners
        alimentador = (Spinner) findViewById(R.id.spinnerALIMENTADOR);
        selecioneAlimentador();
        religador = (Spinner) findViewById(R.id.spinnerRELIGADOR);
        selecioneReligador();

        //Swtch
        monofasico = findViewById(R.id.switchMonofasica);
        monofasico.setEnabled(false);
        bifasico = findViewById(R.id.switchBifasica);
        bifasico.setEnabled(false);
        trifasico  = findViewById(R.id.switchTrifasico);
        trifasico.setEnabled(false);

        //Margem de dados
        cinco_porcento = findViewById(R.id.switch_5_porcento);
        dez_porcento = findViewById(R.id.switch_10_porcento);

        //Text Views
        resultado_para_mais = findViewById(R.id.textView_Resultado_Para_Mais);
        resultado_para_menos = findViewById(R.id.textView_Resultado_Para_Menos);
        View_Title_Resultado_Para_Mais = findViewById(R.id.textView_title_para_mais);
        View_Title_Resultado_Para_Menos = findViewById(R.id.textView_title_para_menos);
        valor_de_curto_informado = findViewById(R.id.textView_Valor_de_Curto_Informado);
        resultadoDaBuscaNegativa = findViewById(R.id.textViewTEXTOLISTAVAZIA);
        resultadoDaBuscaNegativa.setText("");

        curtoinformado = findViewById(R.id.view_Curto_Informado);

        //EditText - campos de insersao
        valor_de_curto = findViewById(R.id.editTextValorCurto);

        curtoinformado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(valor_de_curto.getText().toString().trim().equals("")){
                    valor_de_curto.setError("Insira o valor do curto!");
                }else{
                    curto = valor_de_curto.getText().toString();
                    curtoConvertido = Integer.parseInt(curto);
                    valor_de_curto_informado.setText(""+curtoConvertido);
                    valor_de_curto.setText("");

                }
            }
        });

        cinco_porcento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cinco_porcento.isChecked()){
                    if(curto==null){
                        cinco_porcento.setChecked(false);
                        valor_de_curto.setError("Insira o valor do curto!");
                    }else {
                        dez_porcento.setChecked(false);
                        //intervalos de curtos, baseados nas variações
                        intervalo1_calculo_do_resultado_do_valor_do_curto_para_mais = CalculoPraMaisCinco(curtoConvertido);
                        intervalo2_calculo_do_resultado_do_valor_do_curto_para_menos = CalculoPraMenosCinco(curtoConvertido);
                        //----------------
                        View_Title_Resultado_Para_Mais.setText("Resultado para Mais");
                        View_Title_Resultado_Para_Menos.setText("Resultado para Menos");
                        resultado_para_mais.setText("" + CalculoPraMaisCinco(curtoConvertido));
                        resultado_para_menos.setText("" + CalculoPraMenosCinco(curtoConvertido));
                        CONS_VALOR_INICIAL = String.valueOf(intervalo2_calculo_do_resultado_do_valor_do_curto_para_menos);
                        CONS_VALOR_FINAL = String.valueOf(intervalo1_calculo_do_resultado_do_valor_do_curto_para_mais);

                        monofasico.setEnabled(true);
                        bifasico.setEnabled(true);
                        trifasico.setEnabled(true);

                    }
                }else{
                    View_Title_Resultado_Para_Mais.setText("Você desativou a opção");
                    View_Title_Resultado_Para_Menos.setText("Você desativou a opção");
                    resultado_para_menos.setText("- 0");
                    resultado_para_mais.setText("+ 0");
                    monofasico.setEnabled(false);
                    bifasico.setEnabled(false);
                    trifasico.setEnabled(false);
                    gerar_ldf.setEnabled(false);
                }
            }
        });

        dez_porcento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dez_porcento.isChecked()){
                    if (curto==null) {
                        dez_porcento.setChecked(false);
                        valor_de_curto.setError("Insira o valor do curto!");
                    }else{
                        cinco_porcento.setChecked(false);
                        //intervalos de curtos, baseados nas variações
                        intervalo1_calculo_do_resultado_do_valor_do_curto_para_mais = CalculoPraMaisDez(curtoConvertido);
                        intervalo2_calculo_do_resultado_do_valor_do_curto_para_menos = CalculoPraMenosDez(curtoConvertido);

                        View_Title_Resultado_Para_Mais.setText("Resultado para Mais");
                        View_Title_Resultado_Para_Menos.setText("Resultado para Menos");

                        CONS_VALOR_INICIAL = String.valueOf(intervalo2_calculo_do_resultado_do_valor_do_curto_para_menos);
                        CONS_VALOR_FINAL = String.valueOf(intervalo1_calculo_do_resultado_do_valor_do_curto_para_mais);

                        resultado_para_mais.setText("" + CalculoPraMaisDez(curtoConvertido));
                        resultado_para_menos.setText("" + CalculoPraMenosDez(curtoConvertido));


                        monofasico.setEnabled(true);
                        bifasico.setEnabled(true);
                        trifasico.setEnabled(true);

                    }
                }else{
                    View_Title_Resultado_Para_Mais.setText("Você desativou a opção");
                    View_Title_Resultado_Para_Menos.setText("Você desativou a opção");
                    resultado_para_menos.setText("- 0");
                    resultado_para_mais.setText("+ 0");
                    monofasico.setEnabled(false);
                    bifasico.setEnabled(false);
                    trifasico.setEnabled(false);
                    gerar_ldf.setEnabled(false);
                }
            }
        });

        monofasico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (monofasico.isChecked()){
                    bifasico.setChecked(false);
                    trifasico.setChecked(false);
                    gerar_ldf.setEnabled(true);
                    CONS_TIPO_DE_CURTO = "M";
                    if(curtoConvertido !=0 ){
                        gerar_ldf.setEnabled(true);
                    }

                }else{
                    gerar_ldf.setEnabled(false);
                }

            }
        });

        bifasico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bifasico.isChecked()){
                    monofasico.setChecked(false);
                    trifasico.setChecked(false);
                    gerar_ldf.setEnabled(true);
                    CONS_TIPO_DE_CURTO = "B";
                    if(curtoConvertido !=0 ){
                        gerar_ldf.setEnabled(true);
                    }
                    //resultadoCalculo.setText("Bifasico");
                }else{
                    gerar_ldf.setEnabled(false);
                }
            }
        });

        trifasico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (trifasico.isChecked()){
                    monofasico.setChecked(false);
                    bifasico.setChecked(false);
                    CONS_TIPO_DE_CURTO= "T";
                    gerar_ldf.setEnabled(true);
                    Log.d("DADOS",CONS_ALIMENTADOR +"/"+CONS_RELIGADOR+"/"+CONS_VALOR_INICIAL+"/"+CONS_VALOR_FINAL+"/"+CONS_TIPO_DE_CURTO );

                    if(curtoConvertido !=0 ){
                        gerar_ldf.setEnabled(true);
                    }

                }else{
                    gerar_ldf.setEnabled(false);
                }
            }
        });

        gerar_ldf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Loader loader = new Loader(ConsultarActivity.this);
                loader.execute();
            }
        });

        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Voltar(v);
            }
        });



    }

    public void selecioneAlimentador(){
        alimentador.getBaseline();
        // Spinner click listener
        alimentador.setOnItemSelectedListener(this);

        DOWNLOAD_ALM_E_RLG_Repository ldfRepository = new DOWNLOAD_ALM_E_RLG_Repository(this);

        List<ObjModel_ALM_RLG> dadosALM =  ldfRepository.SelecionarTodos_ALM();

        List<String> categories = new ArrayList<>();

        if(categories!=null) {
            categories.add("SELECIONE O ALIMENTADOR");
            for (int i = 0; i < dadosALM.size(); i++) {
                categories.add("ALM: " + dadosALM.get(i).getAlimentador());
            }
        }else{
            categories.add("NÃO ENCONTRAMOS ALIMENTADORES");
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(R.layout.spiner_layout);

        // attaching data adapter to spinner
        alimentador.setAdapter(dataAdapter);

    }

    public void selecioneReligador(){
        religador.getBaseline();
        // Spinner click listener
        religador.setOnItemSelectedListener(this);

        DOWNLOAD_ALM_E_RLG_Repository ldfRepository = new DOWNLOAD_ALM_E_RLG_Repository(this);

        List<ObjModel_ALM_RLG> dadosRLG =  ldfRepository.SelecionarTodos_RLG();

        List<String> categories = new ArrayList<>();

        int cont = dadosRLG.size();

        //categories.add("numero: "+cont);

        if(categories!=null){
            categories.add("SELECIONE O RELIGADOR");
            for (int i = 0;  i < cont; i++){
                categories.add("RLG: "+dadosRLG.get(i).getReligador());
            }
        }else{
            categories.add("NÃO ENCONTRAMOS RELIGADORES");
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        //Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(R.layout.spiner_layout);

        // attaching data adapter to spinner
        religador.setAdapter(dataAdapter);

    }


    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position>0){
            // On selecting a spinner item
            String item = parent.getItemAtPosition(position).toString();
            if (item.contains("ALM")){
                // Showing selected spinner item
                String [] textArray = item.split("ALM: ");

                CONS_ALIMENTADOR = textArray[1];

                Toast.makeText(parent.getContext(), "ALM Selecionado: " + CONS_ALIMENTADOR, Toast.LENGTH_LONG).show();
            }else{
                // Showing selected spinner item
                String [] textArray = item.split("RLG: ");

                CONS_RELIGADOR = textArray[1];
                Toast.makeText(parent.getContext(), "RLG Selecionado: " + CONS_RELIGADOR, Toast.LENGTH_LONG).show();
            }

        }
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }


    //Calculo para menos de +5%
    private int CalculoPraMaisCinco(int valorCurto){
        int resultMais;
        float porcentagemMais5 = (float) 1.05;
        resultMais = (int) (valorCurto * porcentagemMais5);
        return resultMais;
    }

    //Calculo para menos de -5%
    private int CalculoPraMenosCinco(int valorCurto){
        int resultMenos;
        float porcentagemMenos5 = (float) 0.95;
        resultMenos = (int) (valorCurto * porcentagemMenos5);
        return resultMenos;

    }

    //Calculo para menos de -10%
    private int CalculoPraMenosDez(int valorCurto){
        int resultMenos;
        float porcentagemMenos10 = (float) 0.90;
        resultMenos = (int) (valorCurto * porcentagemMenos10);
        return resultMenos;

    }

    //Calculo para menos de +10%
    private int CalculoPraMaisDez(int valorCurto){
        int resultMais;
        float porcentagemMais10 = (float) 1.10;
        resultMais = (int) (valorCurto * porcentagemMais10);
        return resultMais;

    }


    //Funções para voltar
    public void Voltar(View view) {
        finish();
    }


    public class Loader extends AsyncTask<Void, Void, Void> {
        ConsultarActivity consultarActivity;
        String ValorCurtoInicial = CONS_VALOR_INICIAL;
        String ValorCurtoFinal= CONS_VALOR_FINAL;
        String Alimentador = CONS_ALIMENTADOR;
        String Religador = CONS_RELIGADOR;
        String TipoDeCurto = CONS_TIPO_DE_CURTO;

        public Loader(ConsultarActivity consultarActivity/*, String ValorInicial,String ValorFinal,String ALM, String RLG, String TipoDeCurto*/) {
            // recupero a instancia da nossa Activity
            this.consultarActivity = consultarActivity;
        }

        // Aqui eh o que acontece antes da tarefa principal ser executado
        @Override
        protected void onPreExecute() {
            // o progressBar agora eh setado como visivel
            progressBarCarregaLDFSpiner.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
               Thread.sleep(4000); // simulando uma operacao demorada
               consultaLdFObjModels = DOWNLOADLDFRepository.GetCURTOQ(Integer.parseInt(ValorCurtoInicial),Integer.parseInt(ValorCurtoFinal)
                        ,Alimentador,Religador,TipoDeCurto);

                listViewLDF = (ListView) findViewById(R.id.listViewLDFConsulta);

                linhaConsultarAdapter = new LinhaConsultarAdapter(ConsultarActivity.this,consultaLdFObjModels);

            } catch (Exception e) {

            }
            return null;
        }

        // Aqui eh o que acontece depois da execucao do nosso bloco de codigo
        // principal
        @Override
        protected void onPostExecute(Void result) {
            if (consultaLdFObjModels.isEmpty()){

                resultadoDaBuscaNegativa.setText("Não encontramos LdFs na busca!");
                Toast.makeText(consultarActivity,"Não encontramos LdF's na busca", Toast.LENGTH_SHORT).show();

                progressBarCarregaLDFSpiner.setVisibility(View.INVISIBLE);

                listViewLDF.setVisibility(View.INVISIBLE);
            }else {
                listViewLDF.setVisibility(View.VISIBLE);
                Toast.makeText(consultarActivity,"LdF's encontrados...", Toast.LENGTH_LONG).show();
                listViewLDF.setAdapter(linhaConsultarAdapter);
                resultadoDaBuscaNegativa.setText("");

            }

             progressBarCarregaLDFSpiner.setVisibility(View.GONE);
        }

    }


}
