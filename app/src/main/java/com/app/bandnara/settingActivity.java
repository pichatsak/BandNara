package com.app.bandnara;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
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
    private RelativeLayout goChangePass,goLogout,goChangePin,goFb,goWebs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        CloseBar closeBar = new CloseBar(this);
        back = findViewById(R.id.back);
        goChangePass = findViewById(R.id.goChangePass);
        goLogout = findViewById(R.id.goLogout);
        goChangePin = findViewById(R.id.goChangePin);
        goWebs = findViewById(R.id.goWebs);
        goFb = findViewById(R.id.goFb);

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

        goWebs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://www.naracity.go.th/web/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        goFb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://web.facebook.com/%E0%B9%80%E0%B8%97%E0%B8%A8%E0%B8%9A%E0%B8%B2%E0%B8%A5%E0%B9%80%E0%B8%A1%E0%B8%B7%E0%B8%AD%E0%B8%87%E0%B8%99%E0%B8%A3%E0%B8%B2%E0%B8%98%E0%B8%B4%E0%B8%A7%E0%B8%B2%E0%B8%AA-429862817086881";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }
}