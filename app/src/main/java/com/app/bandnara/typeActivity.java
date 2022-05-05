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

public class typeActivity extends AppCompatActivity {
    private FrameLayout bottomMenu;// ตัวแปรปุ่มล่าง
    private LinearLayout back; // ปุ่มกลับ
    private RelativeLayout regisOlder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type);
        CloseBar closeBar = new CloseBar(this);
        back = findViewById(R.id.back);
        // เซ็ตการทำงานปุ่มเมนูล่าง
        bottomMenu = (FrameLayout) findViewById(R.id.bottomMenu);
        regisOlder = findViewById(R.id.regisOlder);
        BottomBar bottomBar = new BottomBar(getApplicationContext(), bottomMenu);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        regisOlder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(typeActivity.this, elderlyActivity.class);
                startActivity(intent);
            }
        });
    }
}