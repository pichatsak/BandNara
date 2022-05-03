package com.app.bandnara;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.app.bandnara.ToolBar.CloseBar;
import com.app.bandnara.keepFireStory.UsersFB;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class loginActivity extends AppCompatActivity {
    private AppCompatButton login;
    boolean doubleBackToExitPressedOnce = false;
    private AppCompatButton login1; // เข้าสู่ระบบ
    private TextInputEditText loginPassword; // รหัสผ่าน
    private EditText loginPhone; //เบอร์โทร
    private FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        CloseBar closeBar = new CloseBar(this);
        login = findViewById(R.id.login);
        login1 = findViewById(R.id.login1);
        loginPassword = findViewById(R.id.loginPassword);
        loginPhone = findViewById(R.id.loginPhon);
        mAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(loginActivity.this,registerActivity.class);
                startActivity(login);
                String getloginPhone = loginPhone.getText().toString();
                String getloginPassword = loginPassword.getText().toString();
                if (getloginPhone.isEmpty()){
                    Toast.makeText(loginActivity.this, "กรุณาใส่เบอร์โทรศัพท์", Toast.LENGTH_SHORT).show();
                }
                else if (getloginPassword.isEmpty()){
                    Toast.makeText(loginActivity.this, "กรุณาใส่รหัสผ่าน", Toast.LENGTH_SHORT).show();
                }
                else {
                    SucCed();
                }

            }
        });
        //เข้าสู่ระบบ
        login1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//
//    }
//คลิก2ครั้งออกแอพ
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
//เข้าสู่ระบบ
    public void SucCed() {
        String getloginPhone = loginPhone.getText().toString();
        String getloginPassword = loginPassword.getText().toString();


    }
}