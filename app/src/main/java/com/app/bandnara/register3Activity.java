package com.app.bandnara;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.app.bandnara.ToolBar.CloseBar;

public class register3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register3);
        CloseBar closeBar = new CloseBar(this);
    }
}