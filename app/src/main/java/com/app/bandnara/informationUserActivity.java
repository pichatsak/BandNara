package com.app.bandnara;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.bandnara.ToolBar.BottomBar;
import com.app.bandnara.ToolBar.CloseBar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class informationUserActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private TextView tvadr1,tvadr2,tvadr3;
    private LinearLayout back,goEditProfile,goEditAdr1,goEditAdr2;
    private FrameLayout bottomMenu;// ตัวแปรปุ่มล่าง

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_user);
        CloseBar closeBar = new CloseBar(this);
        tvadr1 = findViewById(R.id.tvadr1);
        tvadr2 = findViewById(R.id.tvadr2);
        tvadr3 = findViewById(R.id.tvadr3);
        goEditProfile = findViewById(R.id.goEditProfile);
        goEditAdr1 = findViewById(R.id.goEditAdr1);
        goEditAdr2 = findViewById(R.id.goEditAdr2);
        back = findViewById(R.id.back);
        getData();// เซ็ตการทำงานปุ่มเมนูล่าง
        bottomMenu = (FrameLayout) findViewById(R.id.bottomMenu);
        BottomBar bottomBar = new BottomBar(getApplicationContext(), bottomMenu);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        goEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(informationUserActivity.this, EditProfileActivity.class);
                startActivity(login);
            }
        });

        goEditAdr1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(informationUserActivity.this, EditAddressOneActivity.class);
                startActivity(login);
            }
        });

        goEditAdr2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(informationUserActivity.this, EditAddressTwoActivity.class);
                startActivity(login);
            }
        });

    }

    public void getData(){
        DocumentReference docRef = db.collection("users").document(MyApplication.getUserId());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    String getBt1 = "ชื่อ-สกุล : "+document.getData().get("name").toString()+" "+document.getData().get("lastname").toString();
                    String getBt2 = "เพศ : "+document.getData().get("sex").toString();
                    String getBt3 = "อายุ : "+document.getData().get("age").toString();
                    String getBt4 = "เกิดเมื่อ : "+document.getData().get("birthday").toString();
                    String getBt5 = "อีเมล : "+document.getData().get("pim04").toString();
                    String getBt6 = "เบอร์ : "+document.getData().get("pim01").toString();
                    tvadr1.setText(getBt1+"\n"+getBt2+"\n"+getBt3+"\n"+getBt4+"\n"+getBt5+"\n"+getBt6);

                    String getAdr2B1 = document.getData().get("numberass").toString()+" หมู่ "+document.getData().get("mu").toString()+" ถนน "+document.getData().get("road").toString();
                    String getAdr2B2 = "ต."+document.getData().get("tambon").toString()+" อ."+document.getData().get("district").toString();
                    String getAdr2B3 = "จ."+document.getData().get("province").toString()+" "+document.getData().get("numberpri").toString();

                    tvadr2.setText(getAdr2B1+"\n"+getAdr2B2+"\n"+getAdr2B3);

                    String getAdr3B1 = document.getData().get("numberass1").toString()+" หมู่ "+document.getData().get("mu1").toString()+" ถนน "+document.getData().get("road1").toString();
                    String getAdr3B2 = "ต."+document.getData().get("tumbon1").toString()+" อ."+document.getData().get("oumper").toString();
                    String getAdr3B3 = "จ."+document.getData().get("jungvat").toString()+" "+document.getData().get("numberpri1").toString();

                    tvadr3.setText(getAdr3B1+"\n"+getAdr3B2+"\n"+getAdr3B3);
                }
            }
        });
    }
}