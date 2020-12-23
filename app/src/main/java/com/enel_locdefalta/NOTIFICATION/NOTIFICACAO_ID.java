package com.enel_locdefalta.NOTIFICATION;

import android.content.Context;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.enel_locdefalta.R;

public class NOTIFICACAO_ID {
    String CHANNEL_ID ="testeNEL";
    String CHANNEL_NAME="";
    String CHANNEL_DESC="";

    public void CreateNotification(Context context,String title, String text){
        NotificationCompat.Builder notificationLDF = new NotificationCompat.Builder(context, CHANNEL_ID).setSmallIcon(R.drawable.enel_logo_nova)
                .setContentTitle(title).setContentText(text).setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat mNoticationManager  = NotificationManagerCompat.from(context);
        mNoticationManager.notify(1,notificationLDF.build());
    }


}
