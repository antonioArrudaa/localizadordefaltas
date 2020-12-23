package com.enel_locdefalta;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.ListenableWorker;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import androidx.work.impl.utils.futures.SettableFuture;

import com.enel_locdefalta.MVC.VIEW.LOGIN.LoginActivity;
import com.google.common.util.concurrent.ListenableFuture;

public class MyWorker extends Worker {
    private static final String TAG = "MYWORKER";

    public MyWorker(@NonNull Context appContext, @NonNull WorkerParameters workerParams) {
        super(appContext, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d(TAG, "Performing long running task in scheduled job");
        // TODO(developer): add long running task here.
        return Result.success();
    }
















    /*
    @Override
    public boolean onStartJob(JobParameters params) {
        NotificationManager NT = (NotificationManager) getBaseContext().getSystemService(getBaseContext().NOTIFICATION_SERVICE);



        Intent intentNotification = new Intent(getBaseContext(), LeituraNotifcationActivity.class);
        intentNotification.putExtra("MSG", "AGENDAMENTO DE ALARTE");

        int id = (int) (Math.random()*10);
        String CHANEL_ID = String.valueOf(id);
        PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(), Integer.parseInt(CHANEL_ID), intentNotification, PendingIntent.FLAG_UPDATE_CURRENT);


        Notification notificationLDF = new Notification.Builder(getBaseContext()).setSmallIcon(R.drawable.enel_logo_nova)
                .setContentTitle("TES DE AGENDAMENTO DE NOTIFICACAO").setContentText("ESTE É UM TESTE DE NOTIFICACAO AGENDADA").setContentIntent(pendingIntent).build();

        NotificationManager notificationManager = (NotificationManager) getBaseContext().getSystemService(getBaseContext().NOTIFICATION_SERVICE);


        notificationLDF.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(Integer.parseInt(CHANEL_ID), notificationLDF);

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }*/
}
