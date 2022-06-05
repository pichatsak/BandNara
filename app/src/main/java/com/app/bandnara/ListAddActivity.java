package com.app.bandnara;

import static com.google.firebase.firestore.DocumentSnapshot.ServerTimestampBehavior.ESTIMATE;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.bandnara.adaptor.NewsAllAdapter;
import com.app.bandnara.adaptor.RegisAdapter;
import com.app.bandnara.keepFireStory.NewsModel;
import com.app.bandnara.keepFireStory.OlderData;
import com.app.bandnara.models.ItemRegis;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ListAddActivity extends AppCompatActivity {
    private AppCompatButton btnadds;
    private LinearLayout back; // ปุ่มกลับ
    private TextView tvTitle;
    private RecyclerView viewData;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ArrayList<ItemRegis> itemRegisArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_add);
        btnadds = findViewById(R.id.btnadds);
        tvTitle = findViewById(R.id.tvTitle);
        viewData = findViewById(R.id.viewData);
        back = findViewById(R.id.back);
        Bundle bundle = getIntent().getExtras();
        int getTypes = bundle.getInt("typeAdd");
        if (getTypes == 1) {
            tvTitle.setText("ขึ้นทะเบียนผู้สูงอายุ");
            btnadds.setText("เพิ่มรายการขึ้นทะเบียนผู้สูงอายุ");
            getDataType1();
            btnadds.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ListAddActivity.this, elderlyActivity.class);
                    startActivity(intent);
                }
            });
        } else if (getTypes == 2) {
            tvTitle.setText("ขึ้นทะเบียนผู้พิการ");
            btnadds.setText("เพิ่มรายการขึ้นทะเบียนผู้พิการ");
            getDataType2();
            btnadds.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ListAddActivity.this, elderly1Activity.class);
                    startActivity(intent);
                }
            });
        } else if (getTypes == 3) {
            tvTitle.setText("ขึ้นทะเบียนเงินหนุนเด็กแรกเกิด");
            btnadds.setText("เพิ่มรายการขึ้นทะเบียนเงินหนุนเด็กแรกเกิด");
            getDataType3();
            btnadds.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ListAddActivity.this, elderly2Activity.class);
                    startActivity(intent);
                }
            });
        } else if (getTypes == 4) {
            tvTitle.setText("ขึ้นทะเบียนผู้ป่วยโรคเอดส์");
            btnadds.setText("เพิ่มรายการขึ้นทะเบียนผู้ป่วยโรคเอดส์");
            getDataType4();
            btnadds.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ListAddActivity.this, elderly3MainActivity.class);
                    startActivity(intent);
                }
            });
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void getDataType4() {

        db.collection("aids_data")
                .whereEqualTo("userId", MyApplication.getUserId())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w("CHKDB", "Listen failed.", e);
                            return;
                        }

                        itemRegisArrayList = new ArrayList<>();

                        for (QueryDocumentSnapshot document : value) {
                            ItemRegis itemRegis = new ItemRegis();
                            itemRegis.setKeyId(document.getId());
                            String getNameOld = document.getData().get("beforeName").toString() + " " +document.getData().get("name").toString() + " " + document.getData().get("lastName").toString();
                            itemRegis.setNameRegis(getNameOld);
                            itemRegis.setAgeRegis("");
                            itemRegis.setTypeRegis(0);
                            itemRegis.setTypeData(4);
                            itemRegis.setStatusRegis(document.getData().get("status").toString());

                            DocumentSnapshot.ServerTimestampBehavior behavior = ESTIMATE;
                            Date date = document.getDate("dateCreate", behavior);
                            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
                            String dateShow = df.format(date);

                            itemRegis.setDateRegis(dateShow);
                            itemRegisArrayList.add(itemRegis);
                        }

                        viewData.setHasFixedSize(true);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ListAddActivity.this);
                        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        viewData.setLayoutManager(linearLayoutManager);
                        RegisAdapter regisAdapter = new RegisAdapter(ListAddActivity.this, itemRegisArrayList);
                        viewData.setAdapter(regisAdapter);


                    }
                });
    }

    public void getDataType3() {
        db.collection("baby_data")
                .whereEqualTo("userId", MyApplication.getUserId())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w("CHKDB", "Listen failed.", e);
                            return;
                        }
                        itemRegisArrayList = new ArrayList<>();

                        for (QueryDocumentSnapshot document : value) {
                            ItemRegis itemRegis = new ItemRegis();
                            itemRegis.setKeyId(document.getId());
                            String getNameOld = document.getData().get("childBeforeName").toString() + " " + document.getData().get("childName").toString()+" "+ document.getData().get("childLastname").toString();
                            itemRegis.setNameRegis(getNameOld);
                            itemRegis.setAgeRegis("");
                            itemRegis.setTypeRegis(0);
                            itemRegis.setTypeData(3);
                            itemRegis.setStatusRegis(document.getData().get("status").toString());

                            DocumentSnapshot.ServerTimestampBehavior behavior = ESTIMATE;
                            Date date = document.getDate("dateCreate", behavior);
                            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
                            String dateShow = df.format(date);

                            itemRegis.setDateRegis(dateShow);
                            itemRegisArrayList.add(itemRegis);
                        }
                        viewData.setHasFixedSize(true);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ListAddActivity.this);
                        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        viewData.setLayoutManager(linearLayoutManager);
                        RegisAdapter regisAdapter = new RegisAdapter(ListAddActivity.this, itemRegisArrayList);
                        viewData.setAdapter(regisAdapter);
                    }
                });

    }

    public void getDataType2() {
        db.collection("deform_data")
                .whereEqualTo("userId", MyApplication.getUserId())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w("CHKDB", "Listen failed.", e);
                            return;
                        }
                        itemRegisArrayList = new ArrayList<>();
                        for (QueryDocumentSnapshot document : value) {
                            ItemRegis itemRegis = new ItemRegis();
                            itemRegis.setKeyId(document.getId());
                            String getNameOld = document.getData().get("beforeName2").toString()+" "+document.getData().get("deformName").toString() + " " + document.getData().get("deformLastName").toString();
                            itemRegis.setNameRegis(getNameOld);
                            itemRegis.setAgeRegis(document.getData().get("deformYear").toString());
                            itemRegis.setTypeRegis(Integer.parseInt(document.getData().get("typeReport").toString()));
                            itemRegis.setTypeData(2);
                            itemRegis.setStatusRegis(document.getData().get("status").toString());

                            DocumentSnapshot.ServerTimestampBehavior behavior = ESTIMATE;
                            Date date = document.getDate("dateCreate", behavior);
                            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
                            String dateShow = df.format(date);

                            itemRegis.setDateRegis(dateShow);
                            itemRegisArrayList.add(itemRegis);
                        }

                        viewData.setHasFixedSize(true);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ListAddActivity.this);
                        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        viewData.setLayoutManager(linearLayoutManager);
                        RegisAdapter regisAdapter = new RegisAdapter(ListAddActivity.this, itemRegisArrayList);
                        viewData.setAdapter(regisAdapter);
                    }
                });

    }

    public void getDataType1() {
        db.collection("olders_data")
                .whereEqualTo("userId", MyApplication.getUserId())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w("CHKDB", "Listen failed.", e);
                            return;
                        }
                        itemRegisArrayList = new ArrayList<>();
                        for (QueryDocumentSnapshot document : value) {
                            ItemRegis itemRegis = new ItemRegis();
                            itemRegis.setKeyId(document.getId());
                            String getNameOld = document.getData().get("beforeName2").toString() + " " + document.getData().get("olderName").toString() + " " + document.getData().get("olderLastName").toString();
                            itemRegis.setNameRegis(getNameOld);
                            itemRegis.setTypeData(1);
                            itemRegis.setAgeRegis(document.getData().get("olderYear").toString());
                            itemRegis.setTypeRegis(Integer.parseInt(document.getData().get("typeReport").toString()));
                            itemRegis.setStatusRegis(document.getData().get("status").toString());

                            DocumentSnapshot.ServerTimestampBehavior behavior = ESTIMATE;
                            Date date = document.getDate("dateCreate", behavior);
                            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
                            String dateShow = df.format(date);

                            itemRegis.setDateRegis(dateShow);
                            itemRegisArrayList.add(itemRegis);
                        }

                        viewData.setHasFixedSize(true);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ListAddActivity.this);
                        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        viewData.setLayoutManager(linearLayoutManager);
                        RegisAdapter regisAdapter = new RegisAdapter(ListAddActivity.this, itemRegisArrayList);
                        viewData.setAdapter(regisAdapter);

                    }
                });
    }
}