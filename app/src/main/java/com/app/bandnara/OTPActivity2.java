package com.app.bandnara;

import static android.content.ContentValues.TAG;
import static android.os.Build.VERSION_CODES.O;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.bandnara.PhoneEditNumber.GenericKeyEvent;
import com.app.bandnara.PhoneEditNumber.GenericTextWatcher;
import com.app.bandnara.ToolBar.CloseBar;
import com.app.bandnara.keepFireStory.UsersFB;
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
import com.google.firebase.auth.UserProfileChangeRequest;

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
    private String verId = "";
    private ImageView back;
    private FirebaseAuth mAuth;
    // [END declare_auth]
    private static final String TAG = "PhoneAuthActivity";
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpactivity2);
        showphone = findViewById(R.id.showphone);
        mAuth = FirebaseAuth.getInstance();
        OTP1 = findViewById(R.id.OTP1);
        OTP2 = findViewById(R.id.OTP2);
        OTP3 = findViewById(R.id.OTP3);
        OTP4 = findViewById(R.id.OTP4);
        OTP5 = findViewById(R.id.OTP5);
        OTP6 = findViewById(R.id.OTP6);
        nextOTP = findViewById(R.id.nextOTP);
        resendOTP = findViewById(R.id.resendOTP);
        back = findViewById(R.id.back);
        CloseBar closeBar = new CloseBar(this);
//        //??????????????????????????????????????????????????????????????????
        Bundle bundle = getIntent().getExtras();
        String getphone = "+66"+bundle.getString("phone");
        showphone.setText(getphone);

        //????????????????????????????????????????????????????????????????????????????????????
        attachTextWatchers();
        //??????????????????OTP

        // [START phone_auth_callbacks]
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                Log.d(TAG, "onVerificationCompleted:" + credential);

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(TAG, "onVerificationFailed", e);

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                }

                // Show a message and update the UI
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:" + verificationId);

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;
            }
        };
        // [END phone_auth_callbacks]


        //?????????????????????????????????OTP
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

                    Toast.makeText(OTPActivity2.this, "????????????????????????????????????????????? OTP ?????????????????? ", Toast.LENGTH_SHORT).show();
                } else {


                    String code = getOTP1 + getOTP2 + getOTP3 + getOTP4 + getOTP5 + getOTP6;
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);

                    signInWithPhoneAuthCredential(credential);
                }

            }
        });
        //??????????????? OTP ????????????????????????

        resendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                attachTextWatchers();
                resendVerificationCode(getphone,mResendToken);

            }
        });
        startPhoneNumberVerification(getphone);
    }

    // [START resend_verification]
    private void resendVerificationCode(String phoneNumber,PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .setForceResendingToken(token)     // ForceResendingToken from callbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    // [END resend_verification]

    private void startPhoneNumberVerification(String phoneNumber) {
        // [START start_phone_auth]
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
        // [END start_phone_auth]
    }

    //???????????????????????????????????????????????????OTP
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("numphone", "signInWithCredential:success");
                            FirebaseUser user = task.getResult().getUser();
                            // getemail_and_password();
                            MyApplication.setUserRegisId(user.getUid());
                            //??????????????????????????????????????????????????????
                            user.updateEmail(MyApplication.getUserRegis().getPim04())
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                //????????????????????????????????????????????????????????????
                                                user.updatePassword(MyApplication.getUserRegis().getPim02())
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    Intent next = new Intent(OTPActivity2.this, register1Activity.class);
                                                                    startActivity(next);
                                                                    finish();
                                                                }
                                                            }
                                                        });
                                            }
                                        }
                                    });
                            // Update UI
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w("numphone", "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(OTPActivity2.this, "????????????????????? OTP ??????????????????????????????", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

    }
//    public void getemail_and_password(){
//        mAuth.createUserWithEmailAndPassword(MyApplication.getUserRegis().getPim04(), MyApplication.getUserRegis().getPim02())
//                .addOnCompleteListener(OTPActivity2.this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d("1", "createUserWithEmail:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
//
//                            Intent next = new Intent(OTPActivity2.this, register1Activity.class);
//                            startActivity(next);
//                            finish();

//
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w("1", "createUserWithEmail:failure", task.getException());
//                            Toast.makeText(OTPActivity2.this, "??????????????????????????????????????????", Toast.LENGTH_SHORT).show();
//
//                        }
//                    }
//                });
//    }

    //?????????????????????????????? OTP ????????????????????????
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
