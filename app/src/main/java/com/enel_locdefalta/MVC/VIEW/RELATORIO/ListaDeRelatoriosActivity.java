package com.enel_locdefalta.MVC.VIEW.RELATORIO;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.enel_locdefalta.MVC.CONTROLLER.RELATORIO.LinhaConsultarHistoricoAdapter;
import com.enel_locdefalta.MVC.CONTROLLER.RELATORIO.RelatorioLDfRepository;
import com.enel_locdefalta.MVC.MODEL.RELATORIO.RelatorioLdFObjModel;
import com.enel_locdefalta.R;
import com.enel_locdefalta.utils.Uteis;

import java.util.List;

public class ListaDeRelatoriosActivity extends AppCompatActivity {

    private Handler handler = new Handler();

    //CRIANDO UM OBJETO DO TIPO ListView PARA RECEBER OS REGISTROS DE UM ADAPTER
    ListView listViewLDF;
    Uteis l = new Uteis();

    //Verifica O tipo de curto selecionado
    String recebeTipoAlimentador;

    //Variaveis para realizar a consulta;
    String CONS_VALOR_INICIAL, CONS_VALOR_FINAL, CONS_ALIMENTADOR, CONS_RELIGADOR, CONS_TIPO_DE_CURTO;

    TextView textViewMensagemBD;

    ImageView btn_voltar;

    ProgressBar progressBarRelatorio;

    Button convertDadosEmExcel;

    RelatorioLDfRepository historicoLDfRepository =  new RelatorioLDfRepository(this);
    private List<RelatorioLdFObjModel> relatorioLdFObjModels;
    private LinhaConsultarHistoricoAdapter linhaConsultarHistoricoAdapter;


    Uteis data = new Uteis();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.relatorio_activity_lista_de_relatorios_tablet);
        setContentView(R.layout.relatorio_activity_lista_de_relatorios_mobile);


        TextView datadispositivo = findViewById(R.id.textViewDataEnel);
        datadispositivo.setText("enel "+data.RetornaData());


        btn_voltar = findViewById(R.id.btn_voltar);
        btn_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Voltar(v);
            }
        });

        //Apresentando os LDFs da pesquisa
        //listViewLDF = (ListView)this.findViewById(R.id.listViewLDFHistorico);
       // this.convertDadosEmExcel = findViewById(R.id.buttonConvertDadosEmExcel);


        progressBarRelatorio = findViewById(R.id.progressBarRelatorio);
        progressBarRelatorio.setVisibility(View.INVISIBLE);

        textViewMensagemBD = findViewById(R.id.textView_title_MensagemBD);


        Loader loader = new Loader(this);
        // chamada para executar o nosso codigo
        loader.execute();


    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position>0){
            // On selecting a spinner item
            String item = parent.getItemAtPosition(position).toString();
            if (item.contains("ALM")){
                // Showing selected spinner item
                String [] textArray = item.split("ALM: ");

                CONS_ALIMENTADOR = textArray[1];

                Toast.makeText(parent.getContext(), "Alimentador Selecionado: " + CONS_ALIMENTADOR, Toast.LENGTH_LONG).show();
            }else{
                // Showing selected spinner item
                String [] textArray = item.split("RLG: ");

                CONS_RELIGADOR = textArray[1];
                Toast.makeText(parent.getContext(), "Religador Selecionado: " + CONS_RELIGADOR, Toast.LENGTH_LONG).show();
            }

        }
    }



    public void Voltar(View view) {
        finish();
    }

    private void gerarToast(final String mensagem){
        ListaDeRelatoriosActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ListaDeRelatoriosActivity.this, mensagem, Toast.LENGTH_SHORT).show();
            }
        });
    }


    public class Loader extends AsyncTask<Void, Void, Void> {
        ListaDeRelatoriosActivity listaDeRelatoriosActivity;

        public Loader(ListaDeRelatoriosActivity listaDeRelatoriosActivity) {
            // recupero a instancia da nossa Activity
            this.listaDeRelatoriosActivity = listaDeRelatoriosActivity;
        }

        // Aqui eh o que acontece antes da tarefa principal ser executado
        @Override
        protected void onPreExecute() {
            // o progressBar agora eh setado como visivel
            progressBarRelatorio.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                // aqui devera conter o bloco de codigo responsavel por carregar
                // a ListView
                Thread.sleep(4000); // simulando uma operacao demorada

//                RelatorioLDfRepository historicoLDfRepository =  new RelatorioLDfRepository(ListaDeRelatoriosActivity.this);

                relatorioLdFObjModels = historicoLDfRepository.SelecionarTodos();


                listViewLDF = (ListView) findViewById(R.id.listViewLDFHistorico);

                linhaConsultarHistoricoAdapter = new LinhaConsultarHistoricoAdapter(ListaDeRelatoriosActivity.this,relatorioLdFObjModels);


                //listViewLDF.setAdapter(new LinhaConsultarHistoricoAdapter(ListaDeRelatoriosActivity.this, ldfs));

                // instanciando o list de post
                //CarregarRelatoriosCadastrados();
                // recuperando o listview criado no xml activity_main_foto pelo seu
                // id
                //listView = (ListView) findViewById(R.id.listView);
                // intanciando o BaseAdapter
                //adapter = new ListViewAdapter(posts, main);
            } catch (Exception e) {

            }
            return null;
        }

        // Aqui eh o que acontece depois da execucao do nosso bloco de codigo
        // principal
        @Override
        protected void onPostExecute(Void result) {
            if (linhaConsultarHistoricoAdapter.isEmpty()){
                textViewMensagemBD.setText("Não encontramos relatórios cadastrados.");
                //tirando o ProgressBar da nossa tela
                progressBarRelatorio.setVisibility(View.GONE);
            }else {
                // associando o adapter ao listview criado
                listViewLDF.setAdapter(linhaConsultarHistoricoAdapter);
                //tirando o ProgressBar da nossa tela
                progressBarRelatorio.setVisibility(View.GONE);
            }
        }

    }


}
