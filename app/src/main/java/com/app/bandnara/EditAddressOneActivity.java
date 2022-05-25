package com.app.bandnara;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.app.bandnara.ToolBar.BottomBar;
import com.app.bandnara.ToolBar.CloseBar;
import com.app.bandnara.adaptor.AmphurAdapter;
import com.app.bandnara.adaptor.ProvAdapter;
import com.app.bandnara.models.AmphuresModel;
import com.app.bandnara.models.ProvincesModel;
import com.app.bandnara.tools.AdressData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class EditAddressOneActivity extends AppCompatActivity {

    private LinearLayout back;
    private EditText numberass;  //เลขที่อยู่0
    private EditText mu;  //หมู่0
    private EditText road; //ถนน0
    private Spinner province; //จังหวัด0
    private Spinner district; //อำเภอ0
    private EditText tambon; //ตำบอล0
    private EditText numberpri; //รหัสไปรษณีย์0

    private ArrayList<ProvincesModel> provincesModelsList = new ArrayList<>();
    private ArrayList<AmphuresModel> amphuresModelsList = new ArrayList<>();

    private int posProv = 0;
    private int posAmphur = 0;

    private int firstShow = 0;
    private String amphurMain = "";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private AppCompatButton saveData;
    private FrameLayout bottomMenu;// ตัวแปรปุ่มล่าง
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_address_one);
        CloseBar closeBar = new CloseBar(this);
        back = findViewById(R.id.back);
        numberass = findViewById(R.id.numberass);
        mu = findViewById(R.id.mu);
        road = findViewById(R.id.road);
        province = findViewById(R.id.province);
        district = findViewById(R.id.district);
        tambon = findViewById(R.id.tambon);
        numberpri = findViewById(R.id.numberpri);
        saveData = findViewById(R.id.saveData);
        // เซ็ตการทำงานปุ่มเมนูล่าง
        bottomMenu = (FrameLayout) findViewById(R.id.bottomMenu);
        BottomBar bottomBar = new BottomBar(getApplicationContext(), bottomMenu);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        getProvAll();
        getData();

        saveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSaveData();
            }
        });
    }

    private void setSaveData() {
        String getnumberass = numberass.getText().toString();
        String getmu = mu.getText().toString();
        String getroad = road.getText().toString();
        String getprovince = provincesModelsList.get(province.getSelectedItemPosition()).getProvName(); //ค่อยมาเปลี่ยนตัวเก็บ String หลังจากทำอแดปเตอร์เสร็จ
        String getdistrict = amphuresModelsList.get(district.getSelectedItemPosition()).getAmpName(); //ค่อยมาเปลี่ยนตัวเก็บ String หลังจากทำอแดปเตอร์เสร็จ
        String gettambon = tambon.getText().toString(); //ค่อยมาเปลี่ยนตัวเก็บ String หลังจากทำอแดปเตอร์เสร็จ
        String getnumberpri = numberpri.getText().toString();

        if (getnumberass.isEmpty() || getmu.isEmpty() || getroad.isEmpty() || getprovince.isEmpty() || getdistrict.isEmpty() || gettambon.isEmpty() || getnumberpri.isEmpty()) {
            Toast.makeText(EditAddressOneActivity.this, "กรุณากรอกข้อมูลที่อยู่ตามบัตรประชาชนให้ครบ", Toast.LENGTH_LONG).show();
        }else{
            db.collection("users")
                    .document(MyApplication.getUserId())
                    .update("numberass", getnumberass,
                            "mu", getmu,
                            "road", getroad,
                            "province",getprovince,
                            "district",getdistrict,
                            "tambon",gettambon,
                            "numberpri",getnumberpri)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(EditAddressOneActivity.this, "บันทึกข้อมูลเรียบร้อย", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }


    public void getData(){
        DocumentReference docRef = db.collection("users").document(MyApplication.getUserId());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();

                    numberass.setText(document.getData().get("numberass").toString());
                    mu.setText(document.getData().get("mu").toString());
                    road.setText(document.getData().get("road").toString());
                    tambon.setText(document.getData().get("tambon").toString());
                    numberpri.setText(document.getData().get("numberpri").toString());
                    String provinGet = document.getData().get("province").toString();
                    amphurMain = document.getData().get("district").toString();
                    int i=0;
                    for (ProvincesModel object : provincesModelsList) {
                        if(object.getProvName().equals(provinGet)){
                            province.setSelection(i);
                            break;
                        }
                        i++;
                    }

                }
            }
        });
    }

    public void getProvAll(){
        AdressData adressData = new AdressData(this);
        ArrayList<ProvincesModel> provincesModels = adressData.getProvAll();
        provincesModelsList = provincesModels;
        ProvAdapter provAdapter = new ProvAdapter(EditAddressOneActivity.this,provincesModels);
        province.setAdapter(provAdapter);
        province.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getAmphureByProvId(provincesModels.get(i).getId());
                posProv = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    public void getAmphureByProvId(String provId){
        AdressData adressData = new AdressData(this);
        ArrayList<AmphuresModel> amphuresModels = adressData.getAmpuhr(provId);
        amphuresModelsList = amphuresModels;
        AmphurAdapter amphurAdapter = new AmphurAdapter(EditAddressOneActivity.this,amphuresModels);
        district.setAdapter(amphurAdapter);

        district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                posAmphur = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        if(firstShow==1){
            int i=0;
            for (AmphuresModel object : amphuresModelsList) {
                if(object.getAmpName().equals(amphurMain)){
                    district.setSelection(i);
                    break;
                }
                i++;
            }

        }
        firstShow++;
    }

}