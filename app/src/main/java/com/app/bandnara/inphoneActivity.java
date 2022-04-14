package com.app.bandnara;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.app.bandnara.ToolBar.CloseBar;

public class inphoneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inphone);
        CloseBar closeBar = new CloseBar(this);
    }
}