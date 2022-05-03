package com.app.bandnara;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.app.bandnara.ToolBar.CloseBar;

public class newsActivity extends AppCompatActivity {
    private TextView goProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        CloseBar closeBar = new CloseBar(this);
        goProfile = findViewById(R.id.goProfile);

        goProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(newsActivity.this, informationUserActivity.class);
                startActivity(login);
            }
        });
    }
}