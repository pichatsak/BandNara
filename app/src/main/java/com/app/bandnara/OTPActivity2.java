package com.app.bandnara;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.app.bandnara.ToolBar.CloseBar;

public class OTPActivity2 extends AppCompatActivity {
    private TextView showphone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpactivity2);
        showphone = findViewById(R.id.showphone);

        CloseBar closeBar= new CloseBar(this);
        //ส่งเบอร์โทรศัพท์มาโชว์
        Bundle bundle = getIntent().getExtras();
        String getphone = bundle.getString("phone");
        showphone.setText(getphone);

    }
}