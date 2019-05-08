package com.example.abhijeetsingh.udid;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

public class MyNotificationManager {

    private Context ctx;
    public static final int NOTIFICATION_ID=0;  //This can be any number. It is for keeping a track of notifications.


    public MyNotificationManager(Context context){
        ctx=context;
    }

    public void showNotification(String title, String notification, Intent intent){
        PendingIntent pendingIntent= PendingIntent.getActivity(
                ctx,
                NOTIFICATION_ID,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        NotificationCompat.Builder builder;
        builder = new NotificationCompat.Builder(ctx, "mychannelid");

        Notification mNotification=builder.setSmallIcon(R.mipmap.ic_launcher_round)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setContentTitle(title)
                .setContentText(notification)
                .setLargeIcon(BitmapFactory.decodeResource(ctx.getResources(), R.mipmap.ic_launcher_round))
                .build();

        mNotification.flags |= Notification.FLAG_AUTO_CANCEL;

        NotificationManager notificationManager=(NotificationManager)ctx.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID,mNotification);

    }
}

