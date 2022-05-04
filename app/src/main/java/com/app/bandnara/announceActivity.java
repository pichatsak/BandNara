package com.app.bandnara;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.app.bandnara.ToolBar.BottomBar;
import com.app.bandnara.ToolBar.CloseBar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class announceActivity extends AppCompatActivity {

    private FrameLayout bottomMenu;// ตัวแปรปุ่มล่าง
    private int iPos = 5;
    private LinearLayout back; // ปุ่มกลับ
    private List<LinearLayout> btnReport = new ArrayList<LinearLayout>(Arrays.asList(new LinearLayout[iPos]));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announce);
        CloseBar closeBar = new CloseBar(this);

        back = findViewById(R.id.back);

        // เซ็ตการทำงานปุ่มเมนูล่าง
        bottomMenu = (FrameLayout) findViewById(R.id.bottomMenu);
        BottomBar bottomBar = new BottomBar(getApplicationContext(), bottomMenu);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        setIdBtnReport();
    }

    public void setIdBtnReport() {
        for (int i = 0; i < iPos; i++) {
            String getId = "btnReport" + (i + 1);
            btnReport.set(i, findViewById(getResources().getIdentifier(getId, "id", getPackageName())));
            int finalI = i;
            btnReport.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(announceActivity.this, EXActivity.class);
                    intent.putExtra("typeReport",finalI+1);
                    startActivity(intent);
                }
            });
        }
    }
}