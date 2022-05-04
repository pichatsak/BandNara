package com.app.bandnara;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.bandnara.ToolBar.CloseBar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class confirmPinActivity extends AppCompatActivity {
    private String statusPin;
    private int iPos = 10;
    private int iPosPin = 6;
    private String numCur = "";
    private RelativeLayout del;
    private List<RelativeLayout> btnNum = new ArrayList<RelativeLayout>(Arrays.asList(new RelativeLayout[iPos]));
    private List<TextView> pinTv = new ArrayList<TextView>(Arrays.asList(new TextView[iPosPin]));
    private String pageStatus = "";
    private TextView tv_sub_title;
    private int pageCur = 1;
    private String numPin1 = "";
    private String pinHave = "";
    private String statusSet = "";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private LinearLayout back; // ปุ่มกลับ

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_pin);
        tv_sub_title = findViewById(R.id.tv_sub_title);
        back = findViewById(R.id.back);
        CloseBar closeBar = new CloseBar(this);
        Bundle bundle = getIntent().getExtras();
        statusPin = bundle.getString("statusPin");
        statusSet = bundle.getString("statusSet");
        if (statusPin.equals("no")) {
            if(statusSet.equals("new")){
                pageStatus = "setting";
            }else{
                pageStatus = "change";
            }
        } else {
            pageStatus = "access";
            tv_sub_title.setText("");
            setWaitAccess();
        }

        del = findViewById(R.id.del);

        setIdBtnNum();
        setIdPinNum();

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDelNum();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void setWaitAccess() {
        DocumentReference docRef = db.collection("users").document(MyApplication.getUserId());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    pinHave = document.getData().get("pin").toString();
                }
            }
        });
    }

    public void setIdPinNum() {
        for (int i = 0; i < iPosPin; i++) {
            String getId = "pin" + (i + 1);
            pinTv.set(i, findViewById(getResources().getIdentifier(getId, "id", getPackageName())));
        }
    }

    public void setIdBtnNum() {
        for (int i = 0; i < iPos; i++) {
            String getId = "num" + (i + 1);
            btnNum.set(i, findViewById(getResources().getIdentifier(getId, "id", getPackageName())));
            int finalI = i;
            btnNum.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setClickNum(finalI + 1);
                }
            });
        }
    }

    private void setDelNum() {
        if (numCur != null && numCur.length() > 0) {
            numCur = numCur.substring(0, numCur.length() - 1);
            Log.d("CHKNUM", numCur);
            setShowPin();
        }
    }

    private void setShowPin() {
        int numChkGet = numCur.length();
        for (int i = 0; i < iPosPin; i++) {
            if (i <= (numChkGet - 1)) {
                pinTv.get(i).setBackgroundResource(R.drawable.design_circle_active);
            } else {
                pinTv.get(i).setBackgroundResource(R.drawable.design_circle);
            }
        }
    }

    private void setClickNum(int finalI) {
        int numAll = numCur.length();
        if (numAll <= 6) {
            if (finalI == 10) {
                numCur += String.valueOf(0);
            } else {
                numCur += String.valueOf(finalI);
            }

            if (numCur.length() == 6) {
                goCheck();
            } else {
                Log.d("CHKNUM", numCur);
            }
            setShowPin();
        } else {
            Log.d("CHKNUM", numCur + " is full");
            setShowPin();
        }


    }

    public void setNextPage() {
        pageCur = 2;
        tv_sub_title.setText("ตั้งค่ารหัส PIN อีกครั้ง");
        numCur = "";
        setShowPin();
    }

    private void goCheck() {
        if (pageStatus.equals("setting") || pageStatus.equals("change")) {
            if (pageCur == 1) {
                numPin1 = numCur;
                setNextPage();
            } else {
                if (numPin1.equals(numCur)) {
                    db.collection("users")
                            .document(MyApplication.getUserId())
                            .update("pin", numPin1,
                                    "statusSetPin", "yes")
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    if(pageStatus.equals("change")){
                                        Toast.makeText(confirmPinActivity.this, "เปลี่ยน Pin เรียบร้อย", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Intent login = new Intent(confirmPinActivity.this, newsActivity.class);
                                        startActivity(login);
                                    }
                                    finish();

                                }
                            });
                } else {
                    Toast.makeText(confirmPinActivity.this, "รหัสไม่ตรงกัน", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            if(pinHave.equals(numCur)){
                Intent login = new Intent(confirmPinActivity.this, newsActivity.class);
                startActivity(login);
                finish();
            }else{
                Toast.makeText(confirmPinActivity.this, "รหัสไม่ตรงกัน", Toast.LENGTH_SHORT).show();
            }
        }
    }
}