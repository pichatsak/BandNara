package com.app.bandnara;

import android.app.Application;
import android.content.Context;
import android.net.Uri;

import com.app.bandnara.RealmDB.AmphuresRm;
import com.app.bandnara.RealmDB.ProvinceRm;
import com.app.bandnara.RealmDB.TumbonRm;
import com.app.bandnara.keepFireStory.UsersFB;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MyApplication extends Application {

    private static UsersFB userRegis = new UsersFB();
    private static Context mContext;

    private static String urlStorage ="gs://bangnara-mobile-app.appspot.com";
    private static String userId="";
    @Override
    public void onCreate() {
        Realm.init(this);
        mContext = this;
        super.onCreate();

        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
    }

    public static void setSubNotiNews() {
        FirebaseMessaging.getInstance().subscribeToTopic("news");
    }

    public static void setUserRegis1(UsersFB userRegis) {
        MyApplication.userRegis.setPim01(userRegis.getPim01());
        MyApplication.userRegis.setPim02(userRegis.getPim02());
        MyApplication.userRegis.setPim04(userRegis.getPim04());
    }

    public static String getUrlStorage() {
        return urlStorage;
    }

    public static void setUrlStorage(String urlStorage) {
        MyApplication.urlStorage = urlStorage;
    }

    public static String getUserId() {
        return userId;
    }

    public static void setUserId(String userId) {
        MyApplication.userId = userId;
    }

    public static void setUserRegisId(String id) {
        MyApplication.userRegis.setID(id);
    }

    public static void setUriProfile(Uri uriProfile) {
        MyApplication.userRegis.setImageProflie(uriProfile);
    }

    public static UsersFB getUserRegis() {
        return userRegis;
    }


}
