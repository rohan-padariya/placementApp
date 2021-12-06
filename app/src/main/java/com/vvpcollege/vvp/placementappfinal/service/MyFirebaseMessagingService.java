package com.vvpcollege.vvp.placementappfinal.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if(remoteMessage.getData() !=null){

            sendNotification(remoteMessage);

        }
    }
    private void sendNotification(RemoteMessage remoteMessage) {
        Map<String,String > data=remoteMessage.getData();

        String title=data.get("title");
        String content=data.get("content");

        NotificationManager notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID="rohan";

        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.O){

            @SuppressLint("WrongConstant") NotificationChannel notificationChannel=new NotificationChannel(NOTIFICATION_CHANNEL_ID,
                    "rohan Notification",
                    NotificationManager.IMPORTANCE_MAX);

            notificationChannel.setDescription("test app");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);

            notificationManager.createNotificationChannel(notificationChannel);

        }

        NotificationCompat.Builder builder=new NotificationCompat.Builder(this,NOTIFICATION_CHANNEL_ID);
        builder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(android.R.mipmap.sym_def_app_icon)
                .setTicker("ROHAN")
                .setContentTitle(title)
                .setContentText(content)
                .setContentInfo("info");

        notificationManager.notify(1,builder.build());
    }
}
