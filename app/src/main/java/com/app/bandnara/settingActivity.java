package com.app.bandnara;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.app.bandnara.ToolBar.BottomBar;
import com.app.bandnara.ToolBar.CloseBar;
import com.google.firebase.auth.FirebaseAuth;

public class settingActivity extends AppCompatActivity {

    private FrameLayout bottomMenu;// ตัวแปรปุ่มล่าง
    private LinearLayout back; // ปุ่มกลับ
    private RelativeLayout goChangePass,goLogout,goChangePin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        CloseBar closeBar = new CloseBar(this);
        back = findViewById(R.id.back);
        goChangePass = findViewById(R.id.goChangePass);
        goLogout = findViewById(R.id.goLogout);
        goChangePin = findViewById(R.id.goChangePin);

        // เซ็ตการทำงานปุ่มเมนูล่าง
        bottomMenu = (FrameLayout) findViewById(R.id.bottomMenu);
        BottomBar bottomBar = new BottomBar(getApplicationContext(), bottomMenu);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        goChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(settingActivity.this, ChangePassActivity.class);
                startActivity(intent);
            }
        });

        goLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(settingActivity.this, loginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        goChangePin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(settingActivity.this, confirmPinActivity.class);
                login.putExtra("statusPin", "no");
                login.putExtra("statusSet", "change");
                startActivity(login);
            }
        });
    }
}