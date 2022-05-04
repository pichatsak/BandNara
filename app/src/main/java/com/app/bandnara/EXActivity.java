package com.app.bandnara;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.bandnara.ToolBar.BottomBar;
import com.app.bandnara.ToolBar.CloseBar;
import com.app.bandnara.keepFireStory.ReportsData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class EXActivity extends AppCompatActivity {

    private FrameLayout bottomMenu;// ตัวแปรปุ่มล่าง
    private LinearLayout back; // ปุ่มกลับ
    private int typeReport = 0;
    private TextView tvTitle;
    private RelativeLayout chooseImg;
    private Uri UrlImg;
    private int StatusChooseImg = 0;
    private int StatusChooseLatLong = 0;
    private ImageView imgShow;
    private LinearLayout contImg;
    private RelativeLayout chooseMap;
    private Double latChoose = 0.00;
    private TextView tvShowLo;
    private Double longChoose = 0.00;
    private EditText reasons;
    private AppCompatButton sendReport;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageReference = storage.getReference();
    private boolean locationPermissionGranted = false;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exactivity);
        CloseBar closeBar = new CloseBar(this);

        back = findViewById(R.id.back);
        tvTitle = findViewById(R.id.tvTitle);
        chooseImg = findViewById(R.id.chooseImg);
        contImg = findViewById(R.id.contImg);
        imgShow = findViewById(R.id.imgShow);
        chooseMap = findViewById(R.id.chooseMap);
        tvShowLo = findViewById(R.id.tvShowLo);
        reasons = findViewById(R.id.reasons);
        sendReport = findViewById(R.id.sendReport);

        Bundle bundle = getIntent().getExtras();
        typeReport = bundle.getInt("typeReport");

        // เซ็ตการทำงานปุ่มเมนูล่าง
        bottomMenu = (FrameLayout) findViewById(R.id.bottomMenu);
        BottomBar bottomBar = new BottomBar(getApplicationContext(), bottomMenu);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        setTypeShow();
        chooseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImgPic();
            }
        });

        chooseMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseGpsMap();
            }
        });

        sendReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSendReport();
            }
        });
        getLocationPermission();
    }

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    private void setSendReport() {

        if (reasons.getText().toString().isEmpty()) {
            Toast.makeText(this, "กรุณากรอกปัญหาที่พบ", Toast.LENGTH_SHORT).show();
        } else if (StatusChooseImg == 0) {
            Toast.makeText(this, "กรุณาเลือกรูปภาพ", Toast.LENGTH_SHORT).show();
        } else if (StatusChooseLatLong == 0) {
            Toast.makeText(this, "กรุณาเลือกพิกัด", Toast.LENGTH_SHORT).show();
        } else {
            ReportsData reportsData = new ReportsData();
            reportsData.setTypeReport(typeReport);
            reportsData.setLocateLat(latChoose);
            reportsData.setLocateLong(longChoose);
            reportsData.setDateTime(FieldValue.serverTimestamp());
            reportsData.setReasons(reasons.getText().toString());
            reportsData.setTypeReportName(tvTitle.getText().toString());
            reportsData.setUserId(MyApplication.getUserId());
            db.collection("reports")
                    .add(reportsData)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {

                            Log.d("1", "DocumentSnapshot added with ID: " + documentReference.getId());
                            StorageReference ref = storageReference.child("imgReports/" + documentReference.getId());
                            ref.putFile(UrlImg)
                                    .addOnSuccessListener(
                                            new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                @Override
                                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                    Toast.makeText(EXActivity.this, "ส่งข้อมูลเรียบร้อย", Toast.LENGTH_SHORT).show();
                                                    finish();
                                                }
                                            })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                        }
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("1", "Error adding document", e);
                            Toast.makeText(EXActivity.this, "เกิดข้อผิดพลาด", Toast.LENGTH_SHORT).show();

                        }
                    });
        }
    }

    private void chooseGpsMap() {
        Intent intent = new Intent(EXActivity.this, MapMarkActivity.class);
        startActivityForResult(intent, 2);
    }

    public void chooseImgPic() {
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
        Log.d("CHKRES", "resultCode : " + requestCode);
        try {
            switch (requestCode) {
                case 1:
                    if (resultCode == Activity.RESULT_OK) {
                        // เข้าเงื่อนไขนี้ก็ต่อเมื่อผู้ใช้เลือกรูปภาพสำเร็จ
                        // รับค่ารูปภาพที่เลือกแปลงเป็นตัวแปร URI
                        Uri selectedImageUri = data.getData();
                        UrlImg = selectedImageUri;
                        // เปลี่ยนค่าตัวแปร StatusChooseImg ให้เป็น 1 เพื่อเอาไว้เช็คว่าผู้ใช้ได้ทำการอัพโหลดรูปแล้วหรือยัง
                        StatusChooseImg = 1;
                        // ซ่อน ImageView Icon กล้อง
                        chooseImg.setVisibility(View.GONE);
                        // โชว์ ImageView ที่เอาไว้ใช้สำหรับแสดงรูปภาพที่เลือก
                        contImg.setVisibility(View.VISIBLE);
                        // set รูปภาพที่ได้มานำไปโชว์ที่ id : imgProfile
                        imgShow.setImageURI(selectedImageUri);
                        Log.d("CHKIMG", "Selecting img success");
                        break;
                    } else if (resultCode == Activity.RESULT_CANCELED) {
                        // เข้าเงื่อนไขนี้ก็ต่อเมื่อผู้ใช้ไม่ได้เลือกรูปภาพ
                        StatusChooseImg = 0;
                        Log.e("CHKIMG", "Selecting picture cancelled");
                    }
                    break;
                case 2:
                    if (resultCode == Activity.RESULT_OK) {
                        Log.d("CHKRES", "RESULT_OK");
                        Double resultLat = Double.parseDouble(data.getStringExtra("latitudeGet"));
                        Double resultLong = Double.parseDouble(data.getStringExtra("longitudeGet"));
                        longChoose = resultLong;
                        latChoose = resultLat;
                        Log.d("CHKRES", "lat : " + resultLat);
                        Log.d("CHKRES", "log : " + resultLong);
                        StatusChooseLatLong = 1;
                        setShowLo();
                    } else if (resultCode == Activity.RESULT_CANCELED) {
                        Log.d("CHKRES", "RESULT_CANCELED");
                    }
                    break;
            }
        } catch (Exception e) {
            StatusChooseImg = 0;
            Log.e("CHKIMG", "Exception in onActivityResult : " + e.getMessage());
        }
    }

    public void setShowLo() {
        tvShowLo.setText("" + latChoose + " , " + longChoose);
    }

    private void setTypeShow() {
        if (typeReport == 1) {
            tvTitle.setText("ถนน - ทางเท้า");
        } else if (typeReport == 2) {
            tvTitle.setText("ขยะสิ่งปฏิกูล");
        } else if (typeReport == 3) {
            tvTitle.setText("เหตุรำคาญ");
        } else if (typeReport == 4) {
            tvTitle.setText("ไฟฟ้า - แสงสว่าง");
        } else if (typeReport == 5) {
            tvTitle.setText("อื่นๆ");
        }
    }
}