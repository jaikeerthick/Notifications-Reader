package com.jaikeerthick.mynotifications.model;

import android.widget.ImageView;

public class Notification {

    String packageNameNoti, titleNoti, textNoti, Key, notiID;

    public Notification() {

    }

    public Notification(String packageNameNoti, String titleNoti, String textNoti, String notiID) {
        this.packageNameNoti = packageNameNoti;
        this.titleNoti = titleNoti;
        this.textNoti = textNoti;
        this.notiID = notiID;
    }

    public String getPackageNameNoti() {
        return packageNameNoti;
    }

    public void setPackageNameNoti(String packageNameNoti) {
        this.packageNameNoti = packageNameNoti;
    }

    public String getTitleNoti() {
        return titleNoti;
    }

    public void setTitleNoti(String titleNoti) {
        this.titleNoti = titleNoti;
    }

    public String getTextNoti() {
        return textNoti;
    }

    public void setTextNoti(String textNoti) {
        this.textNoti = textNoti;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getNotiID() {
        return notiID;
    }

    public void setNotiID(String notiID) {
        this.notiID = notiID;
    }
}
