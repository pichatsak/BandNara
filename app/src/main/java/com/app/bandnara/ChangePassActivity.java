package com.app.bandnara;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.app.bandnara.ToolBar.BottomBar;
import com.app.bandnara.ToolBar.CloseBar;
import com.app.bandnara.models.ProvincesModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ChangePassActivity extends AppCompatActivity {
    private FrameLayout bottomMenu;// ตัวแปรปุ่มล่าง
    private LinearLayout back; // ปุ่มกลับ
    private TextInputEditText pim02;
    private TextInputEditText pim03;

    private AppCompatImageView imgcheck;
    private AppCompatButton saveData;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String passOld="";
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        CloseBar closeBar = new CloseBar(this);
        back = findViewById(R.id.back);
        pim02 = findViewById(R.id.pim_02);
        pim03 = findViewById(R.id.pim_03);


        mAuth = FirebaseAuth.getInstance();
        getUserData();
        imgcheck = findViewById(R.id.imgcheck);
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
        setCheckCountPass();

        saveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSaveData();
            }
        });
    }

    private void getUserData() {
        DocumentReference docRef = db.collection("users").document(MyApplication.getUserId());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    passOld = document.getData().get("pim02").toString();
                }
            }
        });
    }

    private void setSaveData() {
        if (pim02.getText().toString().isEmpty()) {
            Toast.makeText(ChangePassActivity.this, "กรุณากรอกรหัสผ่าน ", Toast.LENGTH_SHORT).show();
        } else if (pim02.getText().toString().length() < 8) {
            Toast.makeText(ChangePassActivity.this, "กรุณากรอกรหัสผ่านให้มากกว่า 8 ตัวอักษร ", Toast.LENGTH_SHORT).show();
        } else if (pim03.getText().toString().isEmpty()) {
            Toast.makeText(ChangePassActivity.this, "กรุณากรอกยืนยันรหัสผ่าน ", Toast.LENGTH_SHORT).show();
        } else if(!pim02.getText().toString().equals(pim03.getText().toString())){
            Toast.makeText(ChangePassActivity.this, "รหัสผ่านไม่ตรงกัน ", Toast.LENGTH_SHORT).show();
        } else {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String newPassword = pim02.getText().toString();
            if (user != null) {
                AuthCredential credential = EmailAuthProvider
                        .getCredential(user.getEmail(),passOld);
                user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            user.updatePassword(newPassword)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                db.collection("users")
                                                        .document(MyApplication.getUserId())
                                                        .update("pim02", newPassword)
                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                Toast.makeText(ChangePassActivity.this, "เปลี่ยนรหัสผ่านเรียบร้อย ", Toast.LENGTH_SHORT).show();
                                                                finish();
                                                            }
                                                        }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {

                                                        Log.w("CHKERR2", "Error adding document", e);
                                                        Toast.makeText(ChangePassActivity.this, "เกิดข้อผิดพลาด2", Toast.LENGTH_SHORT).show();

                                                    }
                                                });

                                            }
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    Log.w("CHKERR", "Error adding document", e);
                                    Toast.makeText(ChangePassActivity.this, "เกิดข้อผิดพลาด", Toast.LENGTH_SHORT).show();

                                }
                            });
                        } else {
                            // Password is incorrect
                        }
                    }
                });

            }
        }
    }

    //medthod เช็คจำนวนพาสเวิร์ด
    public void setCheckCountPass() {
        // เพิ่ม Event text change ให้กับ id:pim02 เวลาที่มีการพิมข้อความ โค้ดนี้จะทำงานทุกครั้ง
        pim02.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // รับค่าจำนวนตัวอักษรที่พิม
                int getnum = charSequence.length();
                Log.d("CHKCO", "count : " + getnum);
                // เช็ค if จำนวนตัวอักษร
                if (getnum >= 8) {
                    // เซ็ตสีให้กับ เขียว icon
                    imgcheck.setColorFilter(ContextCompat.getColor(ChangePassActivity.this, R.color.teal_501));
                } else {
                    // เซ็ตสีให้กับ เทา icon
                    imgcheck.setColorFilter(ContextCompat.getColor(ChangePassActivity.this, R.color.gray_50));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}