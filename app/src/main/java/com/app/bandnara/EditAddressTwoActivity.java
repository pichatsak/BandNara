package com.app.bandnara;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

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

import java.util.ArrayList;

public class EditAddressTwoActivity extends AppCompatActivity {
    private EditText numberass1; //เลขที่อยู่1
    private EditText mu1; //หมู่1
    private EditText road1; //ถนน1
    private Spinner jungvat; //จังหวัด1
    private Spinner oumper; //อำเภอ1
    private EditText tumbon1; //ตำบล1
    private EditText numberpri1; //รหัสไปรษณีย์1

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private AppCompatButton saveData;
    private LinearLayout back;

    private ArrayList<ProvincesModel> provincesModelsList = new ArrayList<>();
    private ArrayList<AmphuresModel> amphuresModelsList = new ArrayList<>();

    private int posProv = 0;
    private int posAmphur = 0;

    private int firstShow = 0;
    private String amphurMain = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_address_two);
        CloseBar closeBar = new CloseBar(this);
        numberass1 = findViewById(R.id.numberass1);
        mu1 = findViewById(R.id.mu1);
        road1 = findViewById(R.id.road1);
        jungvat = findViewById(R.id.jungvat);
        oumper = findViewById(R.id.oumper);
        tumbon1 = findViewById(R.id.tumbon1);
        numberpri1 = findViewById(R.id.numberpri1);
        back = findViewById(R.id.back);
        saveData = findViewById(R.id.saveData);

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
        String getmu1 = mu1.getText().toString();
        String getroad1 = road1.getText().toString();
        String getjungvat = provincesModelsList.get(jungvat.getSelectedItemPosition()).getProvName(); //ค่อยมาเปลี่ยนตัวเก็บ String หลังจากทำอแดปเตอร์เสร็จ
        String getoumper = amphuresModelsList.get(oumper.getSelectedItemPosition()).getAmpName(); //ค่อยมาเปลี่ยนตัวเก็บ String หลังจากทำอแดปเตอร์เสร็จ
        String gettumbon1 = tumbon1.getText().toString(); //ค่อยมาเปลี่ยนตัวเก็บ String หลังจากทำอแดปเตอร์เสร็จ
        String getnumberpri1 = numberpri1.getText().toString();
        String getnumberass1 = numberass1.getText().toString();

        if (getnumberass1.isEmpty() || getmu1.isEmpty() || getroad1.isEmpty() || getjungvat.isEmpty() || getoumper.isEmpty() || gettumbon1.isEmpty() || getnumberpri1.isEmpty()) {
            Toast.makeText(EditAddressTwoActivity.this, "กรุณากรอกข้อมูลที่อยู่ปัจจุบันให้ครบ", Toast.LENGTH_LONG).show();
        }else{
            db.collection("users")
                    .document(MyApplication.getUserId())
                    .update("mu1", getmu1,
                            "road1", getroad1,
                            "jungvat", getjungvat,
                            "oumper",getoumper,
                            "tumbon1",gettumbon1,
                            "numberpri1",getnumberpri1,
                            "numberass1",getnumberass1)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(EditAddressTwoActivity.this, "บันทึกข้อมูลเรียบร้อย", Toast.LENGTH_SHORT).show();
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

                    numberass1.setText(document.getData().get("numberass1").toString());
                    mu1.setText(document.getData().get("mu1").toString());
                    road1.setText(document.getData().get("road1").toString());
                    tumbon1.setText(document.getData().get("tumbon1").toString());
                    numberpri1.setText(document.getData().get("numberpri1").toString());
                    String provinGet = document.getData().get("jungvat").toString();
                    amphurMain = document.getData().get("oumper").toString();
                    int i=0;
                    for (ProvincesModel object : provincesModelsList) {
                        if(object.getProvName().equals(provinGet)){
                            jungvat.setSelection(i);
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
        ProvAdapter provAdapter = new ProvAdapter(EditAddressTwoActivity.this,provincesModels);
        jungvat.setAdapter(provAdapter);
        jungvat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        AmphurAdapter amphurAdapter = new AmphurAdapter(EditAddressTwoActivity.this,amphuresModels);
        oumper.setAdapter(amphurAdapter);

        oumper.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                    oumper.setSelection(i);
                    break;
                }
                i++;
            }

        }
        firstShow++;
    }
}