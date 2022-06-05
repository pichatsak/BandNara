package com.app.bandnara;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.app.bandnara.ToolBar.CloseBar;
import com.app.bandnara.keepFireStory.UsersFB;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.net.URISyntaxException;
import java.util.UUID;

public class registerActivity extends AppCompatActivity {
    private ImageView back;
    private EditText pim01;
    private TextInputEditText pim02;
    private TextInputEditText pim03;
    private EditText pim04;
    private AppCompatImageView imgcheck;
    private AppCompatButton next;
    private RelativeLayout uploadimg;
    private ImageView imgIcon;
    private ShapeableImageView imgProfile;
    private Uri UrlImg;
    private int StatusChooseImg = 0;

    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageReference = storage.getReference();

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
        uploadimg = findViewById(R.id.uploadimg);
        imgcheck = findViewById(R.id.imgcheck);
        imgIcon = findViewById(R.id.imgicon);
        imgProfile = findViewById(R.id.imgProfile);
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

        // เช็ค Event click ให้กับ id : uploadimg
        uploadimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // เรียกใช้ method ดึงรูปภาพ
                chooseImg();
            }
        });

        //ไปหน้าถัดไป
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //เด้งข้อความถ้าไม่ใส่ข้อมูลให้ครบ
                if (pim01.getText().toString().isEmpty()) {
                    Toast.makeText(registerActivity.this, "กรุณากรอกเบอร์โทรศัพท์ ", Toast.LENGTH_SHORT).show();
                } else if (pim01.getText().toString().length()!=10) {
                    Toast.makeText(registerActivity.this, "กรุณากรอกเบอร์โทรศัพท์ให้ครบ ", Toast.LENGTH_SHORT).show();
                } else if (StatusChooseImg == 0) {
                    Toast.makeText(registerActivity.this, "กรุณาอัพโหลดรูปภาพ ", Toast.LENGTH_SHORT).show();
                } else if (pim02.getText().toString().isEmpty()) {
                    Toast.makeText(registerActivity.this, "กรุณากรอกรหัสผ่าน ", Toast.LENGTH_SHORT).show();
                } else if (pim02.getText().toString().length() < 8) {
                    Toast.makeText(registerActivity.this, "กรุณากรอกรหัสผ่านให้มากกว่า 8 ตัวอักษร ", Toast.LENGTH_SHORT).show();
                } else if (pim03.getText().toString().isEmpty()) {
                    Toast.makeText(registerActivity.this, "กรุณากรอกยืนยันรหัสผ่าน ", Toast.LENGTH_SHORT).show();
                } else if (pim04.getText().toString().isEmpty()) {
                    Toast.makeText(registerActivity.this, "กรุณากรอกอีเมล ", Toast.LENGTH_SHORT).show();
                } else if (pim03.getText().toString().equals(pim02.getText().toString())) {

                    Intent next = new Intent(registerActivity.this, OTPActivity2.class);
                    next.putExtra("phone", pim01.getText().toString());
                    startActivity(next);
                    UsersFB usersFB = new UsersFB();
                    usersFB.setPim01(pim01.getText().toString());
                    usersFB.setPim02(pim02.getText().toString());
                    usersFB.setPim04(pim04.getText().toString());

                    MyApplication.setUserRegis1(usersFB);

                } else {
                    Toast.makeText(registerActivity.this, "กรุณากรอกรหัสผ่านให้ตรงกัน", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void chooseImg() {
        // สร้าง Intent เพื่อไปหน้าเลือกรูปจากตัวเครื่อง
        Intent intent = new Intent();
        // เซ็ตค่าให้หน้าเลือกรูปภาพจะเลือกได้แค่ไฟล์ที่เป็น รูปภาพเท่านั้น
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        // ไปหน้าเลือกรูปภาพ พร้อมกับรอผลตอบกลับ
        startActivityForResult(Intent.createChooser(intent, "เลือกรูปภาพ"), 1);
    }

    // method นี้ทำงานเมื่อทำการเลือกรูปภาพจากในตัวเครื่องแล้ว
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            switch (requestCode) {
                case 1:
                    if (resultCode == Activity.RESULT_OK) {
                        // เข้าเงื่อนไขนี้ก็ต่อเมื่อผู้ใช้เลือกรูปภาพสำเร็จ
                        // รับค่ารูปภาพที่เลือกแปลงเป็นตัวแปร URI
                        Uri selectedImageUri = data.getData();
                        MyApplication.setUriProfile(selectedImageUri);
                        UrlImg = selectedImageUri;
                        // เปลี่ยนค่าตัวแปร StatusChooseImg ให้เป็น 1 เพื่อเอาไว้เช็คว่าผู้ใช้ได้ทำการอัพโหลดรูปแล้วหรือยัง
                        StatusChooseImg = 1;
                        // ซ่อน ImageView Icon กล้อง
                        imgIcon.setVisibility(View.GONE);
                        // โชว์ ImageView ที่เอาไว้ใช้สำหรับแสดงรูปภาพที่เลือก
                        imgProfile.setVisibility(View.VISIBLE);
                        // set รูปภาพที่ได้มานำไปโชว์ที่ id : imgProfile
                        imgProfile.setImageURI(selectedImageUri);
                        Log.d("CHKIMG", "Selecting img success");
                        break;
                    } else if (resultCode == Activity.RESULT_CANCELED) {
                        // เข้าเงื่อนไขนี้ก็ต่อเมื่อผู้ใช้ไม่ได้เลือกรูปภาพ
                        StatusChooseImg = 0;
                        Log.e("CHKIMG", "Selecting picture cancelled");
                    }
                    break;
            }
        } catch (Exception e) {
            StatusChooseImg = 0;
            Log.e("CHKIMG", "Exception in onActivityResult : " + e.getMessage());
        }
    }

    //medthod เช็คจำนวนพาสเวิร์ด
    public void setCheckCountPass() {
        // เพิ่ม Event text change ให้กับ id:pim02 เวลาที่มีการพิมข้อความ โค้ดนี้จะทำงานทุกครั้ง
        pim02.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // รับค่าจำนวนตัวอักษรที่พิม
                int getnum = charSequence.length();
                Log.d("CHKCO", "count : " + getnum);
                // เช็ค if จำนวนตัวอักษร
                if (getnum >= 8) {
                    // เซ็ตสีให้กับ เขียว icon
                    imgcheck.setColorFilter(ContextCompat.getColor(registerActivity.this, R.color.teal_501));
                } else {
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