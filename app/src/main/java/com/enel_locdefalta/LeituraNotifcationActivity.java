package com.enel_locdefalta;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class LeituraNotifcationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leitura_notifcation);

        TextView textView = findViewById(R.id.textViewTESTENOTIFICACAO);

        textView.setText("Notificação de teste");

    }
}