package com.app.bandnara;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.bandnara.ToolBar.CloseBar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.concurrent.TimeUnit;

public class inphoneActivity extends AppCompatActivity {
    AppCompatButton gochk;
    EditText phoneFill;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inphone);
        gochk = findViewById(R.id.gochk);
        phoneFill = findViewById(R.id.phoneFill);
        CloseBar closeBar = new CloseBar(this);

        gochk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPhone();
            }
        });


    }

    private void checkPhone() {
        db.collection("users")
                .whereEqualTo("pim01", phoneFill.getText().toString())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Intent intent = new Intent(inphoneActivity.this,OtpForgetPassActivity.class);
                                intent.putExtra("phoneFill",phoneFill.getText().toString());
                                startActivity(intent);
                            }
                        } else {
                            Toast.makeText(inphoneActivity.this, "ไม่พบผู้ใช้", Toast.LENGTH_SHORT).show();
                            Log.d("CHKFB", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

}