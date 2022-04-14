package com.app.bandnara;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
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
    private Spinner sp_sex;
    public TextView dateOk;
    final Calendar myCalendar = Calendar.getInstance();
    private ImageView back1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);
        CloseBar closeBar = new CloseBar(this);

        sp_sex = findViewById(R.id.sp_sex);
        dateOk = findViewById(R.id.dateOk);
        back1 = findViewById(R.id.back1);
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