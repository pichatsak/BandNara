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
    private RelativeLayout regisOlder,regisDeform,regisAids,regisBaby;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type);
        CloseBar closeBar = new CloseBar(this);
        back = findViewById(R.id.back);
        // เซ็ตการทำงานปุ่มเมนูล่าง
        bottomMenu = (FrameLayout) findViewById(R.id.bottomMenu);
        regisOlder = findViewById(R.id.regisOlder);
        regisDeform = findViewById(R.id.regisDeform);
        regisBaby = findViewById(R.id.regisBaby);
        regisAids = findViewById(R.id.regisAids);
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
                Intent intent = new Intent(typeActivity.this, ListAddActivity.class);
                intent.putExtra("typeAdd",1);
                startActivity(intent);
            }
        });

        regisDeform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(typeActivity.this, ListAddActivity.class);
                intent.putExtra("typeAdd",2);
                startActivity(intent);
            }
        });

        regisBaby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(typeActivity.this, ListAddActivity.class);
                intent.putExtra("typeAdd",3);
                startActivity(intent);
            }
        });

        regisAids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(typeActivity.this, ListAddActivity.class);
                intent.putExtra("typeAdd",4);
                startActivity(intent);
            }
        });

//        regisOlder.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(typeActivity.this, elderlyActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        regisDeform.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(typeActivity.this, elderly1Activity.class);
//                startActivity(intent);
//            }
//        });
//
//        regisAids.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(typeActivity.this, elderly3MainActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        regisBaby.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(typeActivity.this, elderly2Activity.class);
//                startActivity(intent);
//            }
//        });
    }
}