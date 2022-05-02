package com.app.bandnara.tools;

import android.content.Context;

import com.app.bandnara.R;
import com.app.bandnara.RealmDB.AmphuresRm;
import com.app.bandnara.RealmDB.ProvinceRm;
import com.app.bandnara.models.AmphuresModel;
import com.app.bandnara.models.ProvincesModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;

public class AdressData {
    private Context context;
    private Realm realm = Realm.getDefaultInstance();
    public AdressData(Context context) {
        this.context=context;
    }

    public ArrayList<ProvincesModel> getProvAll(){
        ArrayList<ProvincesModel> provincesModels = new ArrayList<>();
        RealmResults<ProvinceRm> provinceRms = realm.where(ProvinceRm.class).findAll();
        provincesModels.add(new ProvincesModel("0","เลือกจังหวัด"));
        for (ProvinceRm e : provinceRms) {
            provincesModels.add(new ProvincesModel(e.getProvId(),e.getProvName()));
        }
        return provincesModels;
    }


    public ArrayList<AmphuresModel> getAmpuhr(String provId){
        ArrayList<AmphuresModel> amphuresModels = new ArrayList<>();
        RealmResults<AmphuresRm> amphuresRms = realm.where(AmphuresRm.class).equalTo("provId",provId).findAll();
        amphuresModels.add(new AmphuresModel("0","เลือกอำเภอ","0"));
        for (AmphuresRm e : amphuresRms) {
            amphuresModels.add(new AmphuresModel(e.getAmpId(),e.getAmpName(),e.getProvId()));
        }
        return amphuresModels;
    }


}
