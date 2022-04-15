package com.app.bandnara;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
    private AppCompatImageView imgcheck;
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
        imgcheck = findViewById(R.id.imgcheck);
        next = findViewById(R.id.next);
        //กลับหน้าก่อนหน้านี้
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // เรียกใช้ medthod เช็คจำนวนพาสเวิร์ด
        setCheckCountPass();


        //ไปหน้าถัดไป
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //เด้งข้อความถ้าไม่ใส่ข้อมูลให้ครบ
                if (pim01.getText().toString().isEmpty()) {
                    Toast.makeText(registerActivity.this, "กรุณากรอกเบอร์โทรศัพท์ ", Toast.LENGTH_SHORT).show();
                } else if (pim02.getText().toString().isEmpty()) {
                    Toast.makeText(registerActivity.this, "กรุณากรอกรหัสผ่าน ", Toast.LENGTH_SHORT).show();
                } else if (pim02.getText().toString().length()<8) {
                    Toast.makeText(registerActivity.this, "กรุณากรอกรหัสผ่านให้มากกว่า 8 ตัวอักษร ", Toast.LENGTH_SHORT).show();
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

    //medthod เช็คจำนวนพาสเวิร์ด
    public void setCheckCountPass(){
        // เพิ่ม Event text change ให้กับ id:pim02 เวลาที่มีการพิมข้อความ โค้ดนี้จะทำงานทุกครั้ง
        pim02.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // รับค่าจำนวนตัวอักษรที่พิม
                int getnum = charSequence.length();
                Log.d("CHKCO","count : "+getnum);
                // เช็ค if จำนวนตัวอักษร
                if(getnum>=8){
                    // เซ็ตสีให้กับ เขียว icon
                    imgcheck.setColorFilter(ContextCompat.getColor(registerActivity.this, R.color.teal_501));
                }else{
                    // เซ็ตสีให้กับ เทา icon
                    imgcheck.setColorFilter(ContextCompat.getColor(registerActivity.this, R.color.gray_50));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

}