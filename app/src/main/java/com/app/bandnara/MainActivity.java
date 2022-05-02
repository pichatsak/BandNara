package com.app.bandnara;

import static com.google.android.gms.tasks.Tasks.await;

import static java.util.concurrent.CompletableFuture.completedFuture;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.app.bandnara.RealmDB.AmphuresRm;
import com.app.bandnara.RealmDB.ProvinceRm;
import com.app.bandnara.RealmDB.TumbonRm;
import com.app.bandnara.ToolBar.CloseBar;
import com.app.bandnara.models.ProvincesModel;
import com.app.bandnara.tools.AdressData;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {
    private Timer timer;
    Realm realm = Realm.getDefaultInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CloseBar closeBar = new CloseBar(this);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                RealmResults<ProvinceRm> provinceRms = realm.where(ProvinceRm.class).findAll();
                if(provinceRms.isEmpty()){
                    insertProvince();
                    insertAmphures();
                    Intent BangNara1 = new Intent(MainActivity.this, loginActivity.class);
                    startActivity(BangNara1);
                    finish();
                }else{
                    Intent BangNara1 = new Intent(MainActivity.this, loginActivity.class);
                    startActivity(BangNara1);
                    finish();
                }

            }
        }, 1000);

    }


    public void insertProvince() {

        InputStream inputStream = getResources().openRawResource(R.raw.thai_provinces);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        int ctr;
        try {
            ctr = inputStream.read();
            while (ctr != -1) {
                byteArrayOutputStream.write(ctr);
                ctr = inputStream.read();
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {

            JSONArray jsonArray = new JSONArray(byteArrayOutputStream.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonDetail = jsonArray.getJSONObject(i);
                realm.beginTransaction();
                ProvinceRm provinceRm = realm.createObject(ProvinceRm.class, UUID.randomUUID().toString());
                provinceRm.setProvId(jsonDetail.getString("id"));
                provinceRm.setProvName(jsonDetail.getString("name_th"));
                realm.commitTransaction();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void insertAmphures() {
        InputStream inputStream = getResources().openRawResource(R.raw.thai_amphures);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        int ctr;
        try {
            ctr = inputStream.read();
            while (ctr != -1) {
                byteArrayOutputStream.write(ctr);
                ctr = inputStream.read();
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {

            JSONArray jsonArray = new JSONArray(byteArrayOutputStream.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonDetail = jsonArray.getJSONObject(i);
                realm.beginTransaction();
                AmphuresRm amphuresRm = realm.createObject(AmphuresRm.class, UUID.randomUUID().toString());
                amphuresRm.setProvId(jsonDetail.getString("province_id"));
                amphuresRm.setAmpName(jsonDetail.getString("name_th"));
                amphuresRm.setAmpId(jsonDetail.getString("id"));
                realm.commitTransaction();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}