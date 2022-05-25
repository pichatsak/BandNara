package com.app.bandnara;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.app.bandnara.ToolBar.BottomBar;
import com.app.bandnara.ToolBar.CloseBar;

public class NotifyActivity extends AppCompatActivity {

    private FrameLayout bottomMenu;// ตัวแปรปุ่มล่าง

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);
        CloseBar closeBar = new CloseBar(this);
        // เซ็ตการทำงานปุ่มเมนูล่าง
        bottomMenu = (FrameLayout)findViewById(R.id.bottomMenu);
        BottomBar bottomBar = new BottomBar(getApplicationContext(),bottomMenu);
    }
}