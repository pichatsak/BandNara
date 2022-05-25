package com.app.bandnara;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.bandnara.ToolBar.BottomBar;
import com.app.bandnara.ToolBar.CloseBar;
import com.app.bandnara.adaptor.SexAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EditProfileActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private EditText name; // ชื่อ
    private EditText lastname; // นามสกุล
    private EditText age; //อายุ
    public TextView dateOk; // ปฏิทินวันเดือนปีเกิด
    private Spinner sp_sex; //เพศ
    private List<String> list = new ArrayList<>();
    private Calendar myCalendar =null; //ปฏิทิน
    private AppCompatButton saveData;
    private LinearLayout back;
    private FrameLayout bottomMenu;// ตัวแปรปุ่มล่าง
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        CloseBar closeBar = new CloseBar(this);
        name = findViewById(R.id.name);
        lastname = findViewById(R.id.lastname);
        age = findViewById(R.id.age);
        sp_sex = findViewById(R.id.sp_sex);
        dateOk = findViewById(R.id.dateOk);
        saveData = findViewById(R.id.saveData);
        back = findViewById(R.id.back);

        // เซ็ตการทำงานปุ่มเมนูล่าง
        bottomMenu = (FrameLayout) findViewById(R.id.bottomMenu);
        BottomBar bottomBar = new BottomBar(getApplicationContext(), bottomMenu);

        //ลิสต์อาเรย์เพศ
        list.add("เลือกเพศ");
        list.add("ชาย");
        list.add("หญิง");

        //สปินเนอร์อะแดปเตอร์
        SexAdapter sexAdaptor = new SexAdapter(this, list);
        sp_sex.setAdapter(sexAdaptor);

        getData();

        saveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSaveData();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void setSaveData() {
        String getname = name.getText().toString();
        String getlastname = lastname.getText().toString();
        String getdateOk = dateOk.getText().toString();
        String getage = age.getText().toString();
        String getsp_sex = list.get(sp_sex.getSelectedItemPosition());
        if (getname.isEmpty() || getlastname.isEmpty() || getdateOk.isEmpty() || getage.isEmpty() || getsp_sex.isEmpty()) {
            Toast.makeText(EditProfileActivity.this, "กรุณากรอกข้อมูลส่วนตัวให้ครบ", Toast.LENGTH_SHORT).show();
        }else{
            db.collection("users")
                    .document(MyApplication.getUserId())
                    .update("name", getname,
                            "lastname", getlastname,
                            "age", getage,
                            "sex",getsp_sex,
                            "birthday",getdateOk)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(EditProfileActivity.this, "บันทึกข้อมูลเรียบร้อย", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    //ปฏิทิน
    private void updateLabel() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        dateOk.setText(dateFormat.format(myCalendar.getTime()));
    }


    public void setDateShow(){
        //ปฏิทิน
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);
                updateLabel();
            }
        };
        //คลิกปฏิทิน
        dateOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(EditProfileActivity.this, R.style.DialogTheme, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });
    }

    public void getData(){
        DocumentReference docRef = db.collection("users").document(MyApplication.getUserId());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();

                    name.setText(document.getData().get("name").toString());
                    lastname.setText(document.getData().get("lastname").toString());
                    age.setText(document.getData().get("age").toString());
                    dateOk.setText(document.getData().get("birthday").toString());
                    String sexGet = document.getData().get("sex").toString();
                    int i=0;
                    for (Object object : list) {
                        if(object.equals(sexGet)){
                            sp_sex.setSelection(i);
                        }
                        i++;
                    }
                    myCalendar = Calendar.getInstance();
                    String dtStart = document.getData().get("birthday").toString();
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                    try {
                        Date date = format.parse(dtStart);
                        myCalendar.setTime(date);
                        setDateShow();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }
}