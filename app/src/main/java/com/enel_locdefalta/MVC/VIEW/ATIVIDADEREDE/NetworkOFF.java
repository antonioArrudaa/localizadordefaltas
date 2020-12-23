package com.enel_locdefalta.MVC.VIEW.ATIVIDADEREDE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.enel_locdefalta.R;


public class NetworkOFF extends AppCompatActivity {

    Button voltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_activity_network_off_tablet);
        //setContentView(R.layout.new_activity_network_off_mobile);
        voltar = findViewById(R.id.button_voltar);

        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Voltar(v);
            }
        });

    }

    public void Voltar(View view){
       finish();
    }

}
