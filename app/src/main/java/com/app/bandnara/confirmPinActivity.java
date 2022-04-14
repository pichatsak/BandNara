package com.app.bandnara;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.app.bandnara.ToolBar.CloseBar;

public class confirmPinActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_pin);
        CloseBar closeBar = new CloseBar(this);
    }
}