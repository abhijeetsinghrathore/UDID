package com.example.abhijeetsingh.udid;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onNewToken(String newToken) {
        super.onNewToken(newToken);
        Log.e("newToken",newToken);
        storeToken(newToken);

    }

    private void storeToken(String token){
        SharedPrefManager.getInstance(getApplicationContext()).storeToken(token);

    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        //To show notification when app is running in foreground. Otherwise Firebase notifications are
        //received only when app is running in background.
        notifyUser(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());
    }

    public void notifyUser(String title,String notification){
        MyNotificationManager myNotificationManager= new MyNotificationManager(getApplicationContext());

        //Intent : To set which Activity will open up on clicking the notification.
        Intent i=new Intent(getApplicationContext(),Profile_GenerateQR.class);
        myNotificationManager.showNotification(title,notification,i);

    }

}

