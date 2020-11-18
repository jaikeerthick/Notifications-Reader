package com.jaikeerthick.mynotifications;

import android.app.Notification;
import android.content.Context;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

public class MyNotificationListener extends NotificationListenerService {

    Context context ;
    static MyListener myListener ;
    String titleData = "", textData = "" ;


    @Override
    public void onCreate() {
        super .onCreate() ;
        context = getApplicationContext() ;

    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {

        if ((sbn.getNotification().flags & Notification.FLAG_GROUP_SUMMARY) != 0) {

            //Ignore duplicate notifications which we might receive in apps like whatsapp, gmail, etc...

        }else {

            String pack = sbn.getPackageName();
            Bundle extras = sbn.getNotification().extras;
            if (extras.getString("android.title") != null) {
                titleData = extras.getString("android.title");
            } else {
                titleData = "No Title";
            }
            if (extras.getCharSequence("android.text") != null) {
                textData = extras.getCharSequence("android.text").toString();
            } else {
                textData = "No data";
            }


            if (!(pack.equals("android")))
                myListener.setValue(sbn.getPackageName(), titleData, textData, String.valueOf(sbn.getTag()));
        }

    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {

    }

    @Override
    public void onNotificationRankingUpdate(RankingMap rankingMap) {
        super.onNotificationRankingUpdate(rankingMap);
    }

    public void setListener (MyListener myListener) {
        MyNotificationListener. myListener = myListener ;
    }
}
