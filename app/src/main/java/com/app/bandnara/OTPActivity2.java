package com.app.bandnara;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.bandnara.PhoneEditNumber.GenericKeyEvent;
import com.app.bandnara.PhoneEditNumber.GenericTextWatcher;
import com.app.bandnara.ToolBar.CloseBar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OTPActivity2 extends AppCompatActivity {
    private TextView showphone;
    private EditText OTP1;
    private EditText OTP2;
    private EditText OTP3;
    private EditText OTP4;
    private EditText OTP5;
    private EditText OTP6;
    private TextView resendOTP;
    private AppCompatButton nextOTP;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    String verId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpactivity2);
        showphone = findViewById(R.id.showphone);
        OTP1 = findViewById(R.id.OTP1);
        OTP2 = findViewById(R.id.OTP2);
        OTP3 = findViewById(R.id.OTP3);
        OTP4 = findViewById(R.id.OTP4);
        OTP5 = findViewById(R.id.OTP5);
        OTP6 = findViewById(R.id.OTP6);
        nextOTP = findViewById(R.id.nextOTP);
        resendOTP = findViewById(R.id.resendOTP);
        CloseBar closeBar = new CloseBar(this);
//        //ส่งเบอร์โทรศัพท์มาโชว์
        Bundle bundle = getIntent().getExtras();
        String getphone = bundle.getString("phone");
        showphone.setText(getphone);
        //ส่งเลขOTP
        attachTextWatchers();
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(auth)
                        .setPhoneNumber("+66"+getphone)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                verId = verificationId;
                            }

                            @Override
                            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                                Log.d("CHKERR", "no");
                                // Sign in with the credential
                                // ...
                            }

                            @Override
                            public void onVerificationFailed(FirebaseException e) {
                                Log.d("CHKERR", e.getMessage());
                                // ...
                            }
                        })          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

        //กดยืนยันเลขOTP
        nextOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getOTP1 = OTP1.getText().toString();
                String getOTP2 = OTP2.getText().toString();
                String getOTP3 = OTP3.getText().toString();
                String getOTP4 = OTP4.getText().toString();
                String getOTP5 = OTP5.getText().toString();
                String getOTP6 = OTP6.getText().toString();


                if (getOTP1.isEmpty() || getOTP2.isEmpty() || getOTP3.isEmpty() || getOTP4.isEmpty() || getOTP5.isEmpty() || getOTP6.isEmpty()) {

                    Toast.makeText(OTPActivity2.this, "กรุณาใส่หมายเลข OTP ให้ครบ ", Toast.LENGTH_SHORT).show();
                } else {
                    String code = getOTP1 + getOTP2 + getOTP3 + getOTP4 + getOTP5 + getOTP6;
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verId, code);
                    signInWithPhoneAuthCredential(credential);
                }

            }
        });
        //กดส่ง OTP อีกครั้ง

        resendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                attachTextWatchers();
                PhoneAuthOptions options =
                        PhoneAuthOptions.newBuilder(auth)
                                .setPhoneNumber("+66"+getphone)       // Phone number to verify
                                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                                .setActivity(OTPActivity2.this)
                                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                    @Override
                                    public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                        verId = verificationId;
                                    }

                                    @Override
                                    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                                        Log.d("CHKERR", "no");
                                        // Sign in with the credential
                                        // ...
                                    }

                                    @Override
                                    public void onVerificationFailed(FirebaseException e) {
                                        Log.d("CHKERR", e.getMessage());
                                        // ...
                                    }
                                })          // OnVerificationStateChangedCallbacks
                                .build();
                PhoneAuthProvider.verifyPhoneNumber(options);

            }
        });


    }

    //ยืนยันOTP
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("numphone", "signInWithCredential:success");
                            FirebaseUser user = task.getResult().getUser();
                            Intent next = new Intent(OTPActivity2.this, register1Activity.class);
                            startActivity(next);
                            finish();
                            // Update UI
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w("numphone", "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(OTPActivity2.this, "หมายเลข OTP ไม่ถูกต้อง ", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void attachTextWatchers() {
        OTP1.addTextChangedListener(new GenericTextWatcher(OTP1, OTP2));
        OTP2.addTextChangedListener(new GenericTextWatcher(OTP2, OTP3));
        OTP3.addTextChangedListener(new GenericTextWatcher(OTP3, OTP4));
        OTP4.addTextChangedListener(new GenericTextWatcher(OTP4, OTP5));
        OTP5.addTextChangedListener(new GenericTextWatcher(OTP5, OTP6));
        OTP6.addTextChangedListener(new GenericTextWatcher(OTP6, null));

        OTP2.setOnKeyListener(new GenericKeyEvent(OTP2, OTP1));
        OTP3.setOnKeyListener(new GenericKeyEvent(OTP3, OTP2));
        OTP4.setOnKeyListener(new GenericKeyEvent(OTP4, OTP3));
        OTP5.setOnKeyListener(new GenericKeyEvent(OTP5, OTP4));
        OTP6.setOnKeyListener(new GenericKeyEvent(OTP6, OTP5));
    }
}
