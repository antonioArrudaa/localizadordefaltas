package com.enel_locdefalta;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;

import java.io.IOException;

public class ResgistroIntenService extends IntentService {
    private static final String TAG="RegIntentService";
    private static final String[] TOPICS = {"global"};

    private String token;

    public ResgistroIntenService(){
        super(TAG);
    }
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        try{
            synchronized (TAG){
                FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (task.isSuccessful()){
                            token = task.getResult().getToken();

                        }else{
                            String error = task.getException().getMessage();
                        }
                    }
                });

                sendRegistrationToServe(token);
                subScribeTopics(token);
                sharedPreferences.edit().putBoolean("enviado", true).apply();
            }
        }catch (Exception e){
            Log.d(TAG, "Flanha na geração do token ", e);
            sharedPreferences.edit().putBoolean("enviado", false).apply();
        }

        Intent registrationComplete = new Intent("RegistrationComplete");
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }


    private void sendRegistrationToServe(String token){
        //Token para ser enviado ao servidor
        Log.e(TAG, "TOKEN GERADO: "+token);
    }

    private void subScribeTopics(String token) throws IOException{
        for (String topic: TOPICS){

           // FirebaseMessaging messaging = FirebaseMessaging
             FirebaseInstanceId instanceId = FirebaseInstanceId.getInstance();
            // instanceId.

        }
    }
}
