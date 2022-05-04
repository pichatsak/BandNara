package com.app.bandnara;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.bandnara.ToolBar.BottomBar;
import com.app.bandnara.ToolBar.CloseBar;

public class newsActivity extends AppCompatActivity {
    private TextView goProfile;
    FrameLayout bottomMenu;// ตัวแปรปุ่มล่าง

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        CloseBar closeBar = new CloseBar(this);
        goProfile = findViewById(R.id.goProfile);

        // เซ็ตการทำงานปุ่มเมนูล่าง
        bottomMenu = (FrameLayout)findViewById(R.id.bottomMenu);
        BottomBar bottomBar = new BottomBar(getApplicationContext(),bottomMenu);

        goProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(newsActivity.this, informationUserActivity.class);
                startActivity(login);
            }
        });


    }
}