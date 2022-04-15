package com.app.bandnara;

import android.app.Application;

import com.app.bandnara.keepFireStory.UsersFB;

public class MyApplication extends Application {

    private static UsersFB userRegis;

    @Override
    public void onCreate() {
        super.onCreate();

    }

    public static void setUserRegis1(UsersFB userRegis) {
        MyApplication.userRegis.setPim01(userRegis.getPim01());
        MyApplication.userRegis.setPim02(userRegis.getPim02());
        MyApplication.userRegis.setPim04(userRegis.getPim04());
    }

    public static UsersFB getUserRegis() {
        return userRegis;
    }
}
