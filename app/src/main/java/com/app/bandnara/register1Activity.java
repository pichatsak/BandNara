package com.app.bandnara;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.bandnara.ToolBar.CloseBar;
import com.app.bandnara.adaptor.AmphurAdapter;
import com.app.bandnara.adaptor.ProvAdapter;
import com.app.bandnara.adaptor.SexAdapter;
import com.app.bandnara.adaptor.TombonAdapter;
import com.app.bandnara.keepFireStory.UsersFB;
import com.app.bandnara.models.AmphuresModel;
import com.app.bandnara.models.ProvincesModel;
import com.app.bandnara.models.TombonsModel;
import com.app.bandnara.tools.AdressData;
import com.app.bandnara.tools.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;

public class register1Activity extends AppCompatActivity {
    private Spinner sp_sex; //เพศ
    public TextView dateOk; // ปฏิทินวันเดือนปีเกิด
    final Calendar myCalendar = Calendar.getInstance(); //ปฏิทิน
    private ArrayList<TombonsModel> tombonsMainModelsList = new ArrayList<>();
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
    private EditText soi;
    private EditText soi2;
    private Spinner jungvat; //จังหวัด1
    private Spinner oumper; //อำเภอ1
    private Spinner tumbon1; //ตำบล1
    private EditText numberpri1; //รหัสไปรษณีย์1
    private AppCompatButton register; //ลงทะเบียน
    private AppCompatButton cancle; //ยกเลิก
    private CheckBox tig; //ติกถูกให้ข้อมูลตามบัตร ปชช ตรงกับ ที่อยู่ปัจจุบัน
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;
    private ArrayList<ProvincesModel> provincesModelsList = new ArrayList<>();
    private ArrayList<AmphuresModel> amphuresModelsList = new ArrayList<>();
    private List<TombonsModel> tombonsModelArrayList = new ArrayList<>();
    private ArrayList<AmphuresModel> amphuresModelsList2 = new ArrayList<>();
    private List<TombonsModel> tombonsModelArrayList2 = new ArrayList<>();
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private int posProv = 0;
    private int posAmphur = 0;
    private StorageReference storageReference = storage.getReference();
    private List<String> list = new ArrayList<>();
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
        numberpri1 = findViewById(R.id.numberpri1);
        register = findViewById(R.id.register);
        cancle = findViewById(R.id.cancle);
        tig = findViewById(R.id.tig);
        soi = findViewById(R.id.soi);
        soi2 = findViewById(R.id.soi2);
        mAuth = FirebaseAuth.getInstance();
        //ลิสต์อาเรย์เพศ
        list.add("เลือกเพศ");
        list.add("ชาย");
        list.add("หญิง");
        getTombonMain();
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
                age.setText(getAge(year,month,day));
                updateLabel();
            }
        };

        //คลิกปฏิทิน
        dateOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(register1Activity.this, R.style.DialogTheme, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });

        //ย้อนกลับ
        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(register1Activity.this, registerActivity.class);
                startActivity(back);
            }
        });

        //คลิกลงทะเบียน
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getname = name.getText().toString();
                String getlastname = lastname.getText().toString();
                String getdateOk = dateOk.getText().toString();
                String getage = age.getText().toString();
                String getsp_sex = list.get(sp_sex.getSelectedItemPosition());
                String getnumberass = numberass.getText().toString();
                String getmu = mu.getText().toString();
                String getroad = road.getText().toString();
                String getprovince = provincesModelsList.get(province.getSelectedItemPosition()).getProvName(); //ค่อยมาเปลี่ยนตัวเก็บ String หลังจากทำอแดปเตอร์เสร็จ
                String getdistrict = amphuresModelsList2.get(district.getSelectedItemPosition()).getAmpName(); //ค่อยมาเปลี่ยนตัวเก็บ String หลังจากทำอแดปเตอร์เสร็จ
                String gettambon = tombonsModelArrayList.get(tambon.getSelectedItemPosition()).getName_th();  //ค่อยมาเปลี่ยนตัวเก็บ String หลังจากทำอแดปเตอร์เสร็จ
                String getnumberpri = numberpri.getText().toString();
                String getnumberass1 = numberass1.getText().toString();
                String getmu1 = mu1.getText().toString();
                String getroad1 = road1.getText().toString();
                String getjungvat = provincesModelsList.get(jungvat.getSelectedItemPosition()).getProvName(); //ค่อยมาเปลี่ยนตัวเก็บ String หลังจากทำอแดปเตอร์เสร็จ
                String getoumper = amphuresModelsList2.get(oumper.getSelectedItemPosition()).getAmpName(); //ค่อยมาเปลี่ยนตัวเก็บ String หลังจากทำอแดปเตอร์เสร็จ
                String gettumbon1 = tombonsModelArrayList2.get(tumbon1.getSelectedItemPosition()).getName_th();  //ค่อยมาเปลี่ยนตัวเก็บ String หลังจากทำอแดปเตอร์เสร็จ
                String getnumberpri1 = numberpri1.getText().toString();

                if (getname.isEmpty() || getlastname.isEmpty() || getdateOk.isEmpty() || getage.isEmpty() || getsp_sex.isEmpty()) {
                    Toast.makeText(register1Activity.this, "กรุณากรอกข้อมูลส่วนตัวให้ครบ", Toast.LENGTH_SHORT).show();
                } else if (getnumberass.isEmpty() || getmu.isEmpty() || getroad.isEmpty() || getprovince.isEmpty() || getdistrict.isEmpty() || tambon.getSelectedItemPosition()==0 || getnumberpri.isEmpty()) {
                    Toast.makeText(register1Activity.this, "กรุณากรอกข้อมูลที่อยู่ตามบัตรประชาชนให้ครบ", Toast.LENGTH_LONG).show();
                } else if (getnumberass1.isEmpty() || getmu1.isEmpty() || getroad1.isEmpty() || getjungvat.isEmpty() || getoumper.isEmpty() || tumbon1.getSelectedItemPosition()==0 || getnumberpri1.isEmpty()) {
                    Toast.makeText(register1Activity.this, "กรุณากรอกข้อมูลที่อยู่ปัจจุบันให้ครบ", Toast.LENGTH_LONG).show();
                } else {
                    keepFirebase();
                }

            }
        });

        //ติกถูกให้ข้อมูลตามบัตร ปชช ตรงกับ ที่อยู่ปัจจุบัน
        tig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCbTig();
            }
        });

        getProvAll();
        getProvAll2();
    }

    private void getTombonMain() {
        String jsonFileString = Utils.getJsonTumbonFromAssets(getApplicationContext());
        Gson gson = new Gson();
        Type listUserType = new TypeToken<ArrayList<TombonsModel>>() {}.getType();
        tombonsMainModelsList = gson.fromJson(jsonFileString, listUserType);
    }

    public String getAge(int year, int month, int day) {
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month-1, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = "";
        if(ageInt<=0){
            ageS = "0";
        }else{
            ageS = ageInt.toString();
        }
        return ageS;
    }

    public void setCbTig(){

        if(tig.isChecked()){
            String getnumberass = numberass.getText().toString();
            String getmu = mu.getText().toString();
            String getroad = road.getText().toString();
            String getprovince = provincesModelsList.get(province.getSelectedItemPosition()).getProvName(); //ค่อยมาเปลี่ยนตัวเก็บ String หลังจากทำอแดปเตอร์เสร็จ
            String getdistrict = amphuresModelsList.get(district.getSelectedItemPosition()).getAmpName(); //ค่อยมาเปลี่ยนตัวเก็บ String หลังจากทำอแดปเตอร์เสร็จ
            String gettambon = tombonsModelArrayList.get(tambon.getSelectedItemPosition()).getName_th();  //ค่อยมาเปลี่ยนตัวเก็บ String หลังจากทำอแดปเตอร์เสร็จ
            String getnumberpri = numberpri.getText().toString();
            String getSoi = soi.getText().toString();

            if (getnumberass.isEmpty() || getmu.isEmpty() || getroad.isEmpty() || getprovince.isEmpty() || getdistrict.isEmpty()|| getSoi.isEmpty() || gettambon.isEmpty() || getnumberpri.isEmpty()) {
                Toast.makeText(register1Activity.this, "กรุณากรอกข้อมูลที่อยู่ตามบัตรประชาชนให้ครบ", Toast.LENGTH_LONG).show();
            }
            else {
                //ส่งข้อมูลที่อยู่ตาม บัตร ปชช ไปที่ ที่อยู่ ปัจจุบัน
                numberass1.setText(getnumberass);
                mu1.setText(getmu);
                road1.setText(getroad);
                jungvat.setSelection(posProv);
                numberpri1.setText(getnumberpri);
                soi2.setText(getSoi);
            }
        }else{
            numberass1.setText("");
            mu1.setText("");
            road1.setText("");
            jungvat.setSelection(0);
            oumper.setSelection(0);
            tumbon1.setSelection(0);
            numberpri1.setText("");
        }


    }

    public void getProvAll(){
        AdressData adressData = new AdressData(this);
        ArrayList<ProvincesModel> provincesModels = adressData.getProvAll();
        provincesModelsList = provincesModels;
        ProvAdapter provAdapter = new ProvAdapter(register1Activity.this,provincesModels);
        province.setAdapter(provAdapter);
        province.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getAmphureByProvId(provincesModels.get(i).getId());
                posProv = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void getProvAll2(){
        AdressData adressData = new AdressData(this);
        ArrayList<ProvincesModel> provincesModels = adressData.getProvAll();
        ProvAdapter provAdapter = new ProvAdapter(register1Activity.this,provincesModels);
        jungvat.setAdapter(provAdapter);
        jungvat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getAmphureByProvId2(provincesModels.get(i).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void getAmphureByProvId(String provId){
        AdressData adressData = new AdressData(this);
        ArrayList<AmphuresModel> amphuresModels = adressData.getAmpuhr(provId);
        amphuresModelsList = amphuresModels;
        AmphurAdapter amphurAdapter = new AmphurAdapter(register1Activity.this,amphuresModels);
        district.setAdapter(amphurAdapter);
        district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                posAmphur = i;
                getTombonByAmphurId(amphuresModelsList.get(i).getAmpId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    public void getTombonByAmphurId(String amphursId) {
        Log.i("CHKGSON", "choose amp : " + amphursId);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            List<TombonsModel> filteredArticleList = new ArrayList<>();
            TombonsModel tombonsModelFirst = new TombonsModel();
            tombonsModelFirst.setName_th("เลือกตำบล");
            tombonsModelFirst.setId("0");
            filteredArticleList.add(tombonsModelFirst);
            if(!amphursId.equals("0")){
                filteredArticleList.addAll(tombonsMainModelsList.stream().filter(items -> items.getAmphure_id().contains(amphursId)).collect(Collectors.toList()));
            }
            tombonsModelArrayList = filteredArticleList;
            TombonAdapter tombonAdapter = new TombonAdapter(register1Activity.this, tombonsModelArrayList);
            tambon.setAdapter(tombonAdapter);
            tambon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if(i!=0){
                        numberpri.setText(tombonsModelArrayList.get(i).getZip_code());
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }

    }

    public void getAmphureByProvId2(String provId){
        AdressData adressData = new AdressData(this);
        ArrayList<AmphuresModel> amphuresModels = adressData.getAmpuhr(provId);
        amphuresModelsList2 = amphuresModels;
        AmphurAdapter amphurAdapter = new AmphurAdapter(register1Activity.this,amphuresModels);
        oumper.setAdapter(amphurAdapter);

        oumper.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getTombonByAmphurId2(amphuresModelsList2.get(i).getAmpId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        if(tig.isChecked()){
            oumper.setSelection(posAmphur);
        }

    }


    public void getTombonByAmphurId2(String amphursId) {
        Log.i("CHKGSON", "choose amp : " + amphursId);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            List<TombonsModel> filteredArticleList = new ArrayList<>();
            TombonsModel tombonsModelFirst = new TombonsModel();
            tombonsModelFirst.setName_th("เลือกตำบล");
            tombonsModelFirst.setId("0");
            filteredArticleList.add(tombonsModelFirst);
            if(!amphursId.equals("0")){
                filteredArticleList.addAll(tombonsMainModelsList.stream().filter(items -> items.getAmphure_id().contains(amphursId)).collect(Collectors.toList()));
            }
            tombonsModelArrayList2 = filteredArticleList;
            TombonAdapter tombonAdapter = new TombonAdapter(register1Activity.this, tombonsModelArrayList2);
            tumbon1.setAdapter(tombonAdapter);
            tumbon1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if(i!=0){
                        numberpri1.setText(tombonsModelArrayList2.get(i).getZip_code());
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            if (tig.isChecked()) {
                tumbon1.setSelection(tambon.getSelectedItemPosition());
            }
        }

    }


    //ปฏิทิน
    private void updateLabel() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        dateOk.setText(dateFormat.format(myCalendar.getTime()));
    }

    //ไฟล์เบสเก็บข้อมูล
    public void keepFirebase() {
        String getname = name.getText().toString();
        String getlastname = lastname.getText().toString();
        String getdateOk = dateOk.getText().toString();
        String getage = age.getText().toString();
        String getsp_sex = list.get(sp_sex.getSelectedItemPosition()); //ไม่ได้
        String getnumberass = numberass.getText().toString();
        String getmu = mu.getText().toString();
        String getroad = road.getText().toString();
        String getprovince = provincesModelsList.get(province.getSelectedItemPosition()).getProvName(); //ค่อยมาเปลี่ยนตัวเก็บ String หลังจากทำอแดปเตอร์เสร็จ
        String getdistrict = amphuresModelsList.get(district.getSelectedItemPosition()).getAmpName(); //ค่อยมาเปลี่ยนตัวเก็บ String หลังจากทำอแดปเตอร์เสร็จ
        String gettambon = tombonsModelArrayList.get(tambon.getSelectedItemPosition()).getName_th();  //ค่อยมาเปลี่ยนตัวเก็บ String หลังจากทำอแดปเตอร์เสร็จ
        String getnumberpri = numberpri.getText().toString();
        String getnumberass1 = numberass1.getText().toString();
        String getmu1 = mu1.getText().toString();
        String getroad1 = road1.getText().toString();
        String getjungvat = provincesModelsList.get(jungvat.getSelectedItemPosition()).getProvName(); //ค่อยมาเปลี่ยนตัวเก็บ String หลังจากทำอแดปเตอร์เสร็จ
        String getoumper = amphuresModelsList2.get(oumper.getSelectedItemPosition()).getAmpName(); //ค่อยมาเปลี่ยนตัวเก็บ String หลังจากทำอแดปเตอร์เสร็จ
        String gettumbon1 = tombonsModelArrayList2.get(tumbon1.getSelectedItemPosition()).getName_th();  //ค่อยมาเปลี่ยนตัวเก็บ String หลังจากทำอแดปเตอร์เสร็จ
        String getnumberpri1 = numberpri1.getText().toString();
        String getSoi = soi.getText().toString();
        String getSoi2 = soi2.getText().toString();


        UsersFB usersFB = new UsersFB();

        usersFB.setPim01(MyApplication.getUserRegis().getPim01());
        usersFB.setPim02(MyApplication.getUserRegis().getPim02());
        usersFB.setPim04(MyApplication.getUserRegis().getPim04());
        usersFB.setName(getname);
        usersFB.setLastname(getlastname);
        usersFB.setBirthday(getdateOk);
        usersFB.setAge(getage);
        usersFB.setSex(getsp_sex);
        usersFB.setNumberass(getnumberass);
        usersFB.setMu(getmu);
        usersFB.setRoad(getroad);
        usersFB.setProvince(getprovince);
        usersFB.setDistrict(getdistrict);
        usersFB.setTambon(gettambon);
        usersFB.setNumberpri(getnumberpri);
        usersFB.setNumberass1(getnumberass1);
        usersFB.setMu1(getmu1);
        usersFB.setRoad1(getroad1);
        usersFB.setJungvat(getjungvat);
        usersFB.setOumper(getoumper);
        usersFB.setTumbon1(gettumbon1);
        usersFB.setNumberpri1(getnumberpri1);
        usersFB.setID(MyApplication.getUserRegis().getID());
        usersFB.setPin("");
        usersFB.setStatusSetPin("no");
        usersFB.setTime(FieldValue.serverTimestamp());
        usersFB.setSoi(getSoi);
        usersFB.setSoi2(getSoi2);

        //เก็บในไฟล์สโตร์
        db.collection("users")
                .add(usersFB)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                        Log.d("1", "DocumentSnapshot added with ID: " + documentReference.getId());
                        StorageReference ref = storageReference.child("imgprofile/" + documentReference.getId());
                        ref.putFile(MyApplication.getUserRegis().getImageProflie())
                                .addOnSuccessListener(
                                        new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                Toast.makeText(register1Activity.this, "ลงทะเบียนสำเร็จ", Toast.LENGTH_SHORT).show();
                                                Intent login = new Intent(register1Activity.this, loginActivity.class);
                                                startActivity(login);
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
                        Toast.makeText(register1Activity.this, "เกิดข้อผิดพลาด", Toast.LENGTH_SHORT).show();

                    }
                });
    }
}