package com.app.bandnara.tools;

import android.content.Context;
import android.util.Log;

import com.app.bandnara.R;

import java.io.IOException;
import java.io.InputStream;
public class Utils {
    public static String getJsonTumbonFromAssets(Context context) {
        String jsonString;
        try {
            InputStream is = context.getResources().openRawResource(R.raw.thai_tambons);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            jsonString = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return jsonString;
    }
}
