package com.app.bandnara;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.bandnara.ToolBar.CloseBar;
import com.app.bandnara.adaptor.SexAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class register1Activity extends AppCompatActivity {
    private Spinner sp_sex; //เพศ
    public TextView dateOk; // ปฏิทินวันเดือนปีเกิด
    final Calendar myCalendar = Calendar.getInstance(); //ปฏิทิน
    private ImageView back1; //ย้อนกลับ
    private EditText name; // ชื่อ
    private EditText lastname; // นามสกุล
    private EditText age; //อายุ
    private EditText numberass;  //เลขที่อยู่0
    private EditText mu;  //หมู่0
    private EditText road; //ถนน0
    private Spinner province; //จังหวัด0
    private Spinner district; //อำเภอ0
    private Spinner tambon; //ตำบอล0
    private EditText numberpri; //รหัสไปรษณีย์0
    private EditText numberass1; //เลขที่อยู่1
    private EditText mu1; //หมู่1
    private EditText road1; //ถนน1
    private Spinner jungvat; //จังหวัด1
    private Spinner oumper; //อำเภอ1
    private Spinner tumbon1; //ตำบล1
    private EditText numberpri1; //รหัสไปรษณีย์1
    private AppCompatButton register; //ลงทะเบียน
    private AppCompatButton cancle; //ยกเลิก

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);
        CloseBar closeBar = new CloseBar(this);

        sp_sex = findViewById(R.id.sp_sex);
        dateOk = findViewById(R.id.dateOk);
        back1 = findViewById(R.id.back1);
        name = findViewById(R.id.name);
        lastname = findViewById(R.id.lastname);
        age = findViewById(R.id.age);
        numberass = findViewById(R.id.numberass);
        mu = findViewById(R.id.mu);
        road = findViewById(R.id.road);
        province = findViewById(R.id.province);
        district = findViewById(R.id.district);
        tambon = findViewById(R.id.tambon);
        numberpri = findViewById(R.id.numberpri);
        numberass1 = findViewById(R.id.numberass1);
        mu1 = findViewById(R.id.mu1);
        road1 = findViewById(R.id.road1);
        jungvat = findViewById(R.id.jungvat);
        oumper = findViewById(R.id.oumper);
        tumbon1 = findViewById(R.id.tumbon1);
        numberpri1 = findViewById(R.id.numberpri);
        register = findViewById(R.id.register);
        cancle = findViewById(R.id.cancle);

        //ลิสต์อาเรย์เพศ
        List<String> list = new ArrayList<>();
        list.add("เลือกเพศ");
        list.add("ชาย");
        list.add("หญิง");

        //สปินเนอร์อะแดปเตอร์
        SexAdapter sexAdaptor = new SexAdapter(this, list);
        sp_sex.setAdapter(sexAdaptor);
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

        dateOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(register1Activity.this,R.style.DialogTheme, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });


        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(register1Activity.this,registerActivity.class);
                startActivity(back);
            }
        });


    }
//ปฏิทิน
    private void updateLabel() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        dateOk.setText(dateFormat.format(myCalendar.getTime()));
    }
}