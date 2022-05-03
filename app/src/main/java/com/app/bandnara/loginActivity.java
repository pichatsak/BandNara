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
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
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
                Intent login = new Intent(loginActivity.this, registerActivity.class);
                startActivity(login);


            }
        });
        //เข้าสู่ระบบ
        login1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getloginPhone = loginPhone.getText().toString();
                String getloginPassword = loginPassword.getText().toString();
                if (getloginPhone.isEmpty()) {
                    Toast.makeText(loginActivity.this, "กรุณาใส่เบอร์โทรศัพท์", Toast.LENGTH_SHORT).show();
                } else if (getloginPassword.isEmpty()) {
                    Toast.makeText(loginActivity.this, "กรุณาใส่รหัสผ่าน", Toast.LENGTH_SHORT).show();
                } else {
                    SucCed();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Query firstQuery = db.collection("users").whereEqualTo("id", user.getUid());
            firstQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String pinget = document.getData().get("statusSetPin").toString();
                            MyApplication.setUserId(document.getId());
                            Intent login = new Intent(loginActivity.this, confirmPinActivity.class);
                            login.putExtra("statusPin", pinget);
                            startActivity(login);
                            finish();
                        }
                    } else {
                        Log.d("CHKDB", "Error getting documents: ", task.getException());
                    }
                }
            });
        }
    }

    //คลิก2ครั้งออกแอพ
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "กดอีกครั้งเพื่อออกจากแอพ", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    //เข้าสู่ระบบ
    public void SucCed() {
        String getloginPhone = loginPhone.getText().toString();
        String getloginPassword = loginPassword.getText().toString();
        Query firstQuery = db.collection("users").whereEqualTo("pim01", getloginPhone);
        firstQuery.whereEqualTo("pim02", getloginPassword);
        Log.d("CHKLOGIN", "CLICK");
        firstQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d("CHKDB", document.getId() + " => " + document.getData());
                        setLogin(document.getData().get("pim04").toString(), getloginPassword, document.getData().get("statusSetPin").toString());
                    }
                } else {
                    Log.d("CHKDB", "Error getting documents: ", task.getException());
                }
            }
        });


    }

    public void setLogin(String email, String pass, String statusPin) {
        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("CHKLOGIN", "signInWithEmail:success");
                            Intent login = new Intent(loginActivity.this, confirmPinActivity.class);
                            login.putExtra("statusPin", statusPin);
                            startActivity(login);
                            finish();
                        } else {
                            Log.w("CHKLOGIN", "signInWithEmail:failure", task.getException());
                            Toast.makeText(loginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}