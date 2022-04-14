package com.app.bandnara;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.app.bandnara.ToolBar.CloseBar;

public class loginActivity extends AppCompatActivity {
    private AppCompatButton login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        CloseBar closeBar = new CloseBar(this);
        login = findViewById(R.id.login);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(loginActivity.this,registerActivity.class);
                startActivity(login);

            }
        });
    }
}