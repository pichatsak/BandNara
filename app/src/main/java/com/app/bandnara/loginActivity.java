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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class loginActivity extends AppCompatActivity {
    private AppCompatButton login;
    boolean doubleBackToExitPressedOnce = false;
    private AppCompatButton login1;
    private TextInputEditText loginPassword;
    private EditText loginPhone;
    private FirebaseAuth mAuth;
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

    public void SucCed() {
        String getloginPhone = loginPhone.getText().toString();
        String getloginPassword = loginPassword.getText().toString();

        mAuth.signInWithEmailAndPassword(getloginPhone, getloginPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("1", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

//                                Intent next = new Intent(loginActivity.this, momActivity.class);
//                                startActivity(next);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("1", "signInWithEmail:failure", task.getException());
                            Toast.makeText(loginActivity.this, "รหัสสมาชิกไม่ถูกต้อง", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
}