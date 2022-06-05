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
import android.widget.Toast;

import com.app.bandnara.ToolBar.CloseBar;
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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class settingnewpasswordActivity extends AppCompatActivity {
    private AppCompatButton btnSetPass;
    private TextInputEditText pim02;
    private TextInputEditText pim03;
    private AppCompatImageView imgcheck;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String emailOld="";
    private String passOld="";
    private String userIdGet="";
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settingnewpassword);
        btnSetPass = findViewById(R.id.btnSetPass);
        pim02 = findViewById(R.id.pim_02);
        pim03 = findViewById(R.id.pim_03);
        imgcheck = findViewById(R.id.imgcheck);
        mAuth = FirebaseAuth.getInstance();
        setCheckCountPass();
        getUserData();
        CloseBar closeBar = new CloseBar(this);

        btnSetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSaveData();
            }
        });
    }

    private void getUserData() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            db.collection("users")
                    .whereEqualTo("id", user.getUid())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d("CHKDB", document.getId() + " => " + document.getData());
                                    userIdGet = document.getId();
                                    emailOld = document.getData().get("pim04").toString();
                                    passOld = document.getData().get("pim02").toString();
                                }
                            } else {
                                Log.d("CHKDB", "Error getting documents: ", task.getException());
                            }
                        }
                    });
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FirebaseAuth.getInstance().signOut();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FirebaseAuth.getInstance().signOut();
    }

    private void setSaveData() {
        if (pim02.getText().toString().isEmpty()) {
            Toast.makeText(settingnewpasswordActivity.this, "กรุณากรอกรหัสผ่าน ", Toast.LENGTH_SHORT).show();
        } else if (pim02.getText().toString().length() < 8) {
            Toast.makeText(settingnewpasswordActivity.this, "กรุณากรอกรหัสผ่านให้มากกว่า 8 ตัวอักษร ", Toast.LENGTH_SHORT).show();
        } else if (pim03.getText().toString().isEmpty()) {
            Toast.makeText(settingnewpasswordActivity.this, "กรุณากรอกยืนยันรหัสผ่าน ", Toast.LENGTH_SHORT).show();
        } else if(!pim02.getText().toString().equals(pim03.getText().toString())){
            Toast.makeText(settingnewpasswordActivity.this, "รหัสผ่านไม่ตรงกัน ", Toast.LENGTH_SHORT).show();
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
                                                        .document(userIdGet)
                                                        .update("pim02", newPassword)
                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                Toast.makeText(settingnewpasswordActivity.this, "เปลี่ยนรหัสผ่านเรียบร้อย ", Toast.LENGTH_SHORT).show();
                                                                FirebaseAuth.getInstance().signOut();
                                                                Intent logingo = new Intent(settingnewpasswordActivity.this,loginActivity.class);
                                                                startActivity(logingo);
                                                                finish();
                                                            }
                                                        }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {

                                                        Log.w("CHKERR2", "Error adding document", e);
                                                        Toast.makeText(settingnewpasswordActivity.this, "เกิดข้อผิดพลาด2", Toast.LENGTH_SHORT).show();

                                                    }
                                                });

                                            }
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    Log.w("CHKERR", "Error adding document", e);
                                    Toast.makeText(settingnewpasswordActivity.this, "เกิดข้อผิดพลาด", Toast.LENGTH_SHORT).show();

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
                    imgcheck.setColorFilter(ContextCompat.getColor(settingnewpasswordActivity.this, R.color.teal_501));
                } else {
                    // เซ็ตสีให้กับ เทา icon
                    imgcheck.setColorFilter(ContextCompat.getColor(settingnewpasswordActivity.this, R.color.gray_50));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}