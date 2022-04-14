package com.app.bandnara;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.app.bandnara.ToolBar.CloseBar;
import com.google.android.material.textfield.TextInputEditText;

import java.net.URISyntaxException;

public class registerActivity extends AppCompatActivity {
    private ImageView back;
    private EditText pim01;
    private TextInputEditText pim02;
    private TextInputEditText pim03;
    private EditText pim04;
    private AppCompatButton next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        CloseBar closeBar = new CloseBar(this);
        back = findViewById(R.id.back);
        pim01 = findViewById(R.id.pim_01);
        pim02 = findViewById(R.id.pim_02);
        pim03 = findViewById(R.id.pim_03);
        pim04 = findViewById(R.id.pim_04);
        next = findViewById(R.id.next);
        //กลับหน้าก่อนหน้านี้
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        //ไปหน้าถัดไป
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //เด้งข้อความถ้าไม่ใส่ข้อมูลให้ครบ
                if (pim01.getText().toString().isEmpty()) {
                    Toast.makeText(registerActivity.this, "กรุณากรอกเบอร์โทรศัพท์ ", Toast.LENGTH_SHORT).show();
                } else if (pim02.getText().toString().isEmpty()) {
                    Toast.makeText(registerActivity.this, "กรุณากรอกรหัสผ่าน ", Toast.LENGTH_SHORT).show();
                } else if (pim03.getText().toString().isEmpty()) {
                    Toast.makeText(registerActivity.this, "กรุณากรอกยืนยันรหัสผ่าน ", Toast.LENGTH_SHORT).show();
                } else if (pim04.getText().toString().isEmpty()) {
                    Toast.makeText(registerActivity.this, "กรุณากรอกอีเมล ", Toast.LENGTH_SHORT).show();
                } else if (pim03.getText().toString().equals(pim02.getText().toString())) {
                    Intent next = new Intent(registerActivity.this, OTPActivity2.class);
                    next.putExtra("phone",pim01.getText().toString());
                    startActivity(next);

                } else {
                    Toast.makeText(registerActivity.this, "กรุณากรอกรหัสผ่านให้ตรงกัน", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}