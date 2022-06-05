package com.app.bandnara;

import static com.google.firebase.firestore.DocumentSnapshot.ServerTimestampBehavior.ESTIMATE;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.app.bandnara.adaptor.RegisAdapter;
import com.app.bandnara.adaptor.ReportsAdapter;
import com.app.bandnara.models.ItemRegis;
import com.app.bandnara.models.ReportsModel;
import com.google.firebase.Timestamp;
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

public class ListReportActivity extends AppCompatActivity {
    private AppCompatButton btnadds;
    private int pageCur = 1;
    private LinearLayout back; // ปุ่มกลับ
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ArrayList<ReportsModel> reportsModelArrayList = new ArrayList<>();
    private ArrayList<ReportsModel> reportsModelArrayList2 = new ArrayList<>();
    private RecyclerView viewData,viewData2;
    private AppCompatButton btnWait,btnDone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_report);
        btnadds = findViewById(R.id.btnadds);
        viewData = findViewById(R.id.viewData);
        viewData2 = findViewById(R.id.viewData2);
        back = findViewById(R.id.back);
        btnWait = findViewById(R.id.btnWait);
        btnDone = findViewById(R.id.btnDone);
        btnadds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListReportActivity.this,announceActivity.class);
                startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        getDataShow();
        getDataShow2();
        setClickBtnShow();
        setShowBtnCl();
    }

    private void setClickBtnShow() {
        btnWait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pageCur =1;
                setShowBtnCl();
            }
        });
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pageCur =2;
                setShowBtnCl();
            }
        });
    }

    private void setShowBtnCl() {
        if(pageCur==1){
            btnWait.setBackgroundResource(R.drawable.design_fram1);
            btnWait.setTextColor(getResources().getColor(R.color.white));
            btnDone.setBackgroundResource(R.drawable.design_fram);
            btnDone.setTextColor(getResources().getColor(R.color.black));
            viewData.setVisibility(View.VISIBLE);
            viewData2.setVisibility(View.GONE);
        }else{
            btnWait.setBackgroundResource(R.drawable.design_fram);
            btnWait.setTextColor(getResources().getColor(R.color.black));
            btnDone.setBackgroundResource(R.drawable.design_fram1);
            btnDone.setTextColor(getResources().getColor(R.color.white));
            viewData.setVisibility(View.GONE);
            viewData2.setVisibility(View.VISIBLE);
        }
    }

    private void getDataShow() {
        db.collection("reports")
                .whereEqualTo("userId",MyApplication.getUserId())
                .whereNotEqualTo("status","3")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w("CHKDB", "Listen failed.", e);
                            return;
                        }

                        reportsModelArrayList = new ArrayList<>();

                        for (QueryDocumentSnapshot document : value) {
                            ReportsModel reportsModel = new ReportsModel();
                            reportsModel.setTypeReport(Integer.valueOf(document.getData().get("typeReport").toString()));
                            reportsModel.setTypeReportName(document.getData().get("typeReportName").toString());
                            reportsModel.setStatus(document.getData().get("status").toString());
                            reportsModel.setReasons(document.getData().get("reasons").toString());

                            DocumentSnapshot.ServerTimestampBehavior behavior = ESTIMATE;
                            Date date = document.getDate("dateTime", behavior);
                            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.US);
                            String dateShow = df.format(date);
                            reportsModel.setDateTime(dateShow);
                            reportsModelArrayList.add(reportsModel);
                        }

                        viewData.setHasFixedSize(true);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ListReportActivity.this);
                        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        viewData.setLayoutManager(linearLayoutManager);
                        ReportsAdapter reportsAdapter = new ReportsAdapter(ListReportActivity.this, reportsModelArrayList);
                        viewData.setAdapter(reportsAdapter);

                    }
                });
    }

    private void getDataShow2() {
        db.collection("reports")
                .whereEqualTo("userId",MyApplication.getUserId())
                .whereEqualTo("status","3")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w("CHKDB", "Listen failed.", e);
                            return;
                        }

                        reportsModelArrayList2 = new ArrayList<>();

                        for (QueryDocumentSnapshot document : value) {
                            ReportsModel reportsModel = new ReportsModel();
                            reportsModel.setTypeReport(Integer.valueOf(document.getData().get("typeReport").toString()));
                            reportsModel.setTypeReportName(document.getData().get("typeReportName").toString());
                            reportsModel.setStatus(document.getData().get("status").toString());
                            reportsModel.setReasons(document.getData().get("reasons").toString());
                            DocumentSnapshot.ServerTimestampBehavior behavior = ESTIMATE;
                            Date date = document.getDate("dateTime", behavior);
                            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.US);
                            String dateShow = df.format(date);
                            reportsModel.setDateTime(dateShow);
                            reportsModelArrayList2.add(reportsModel);
                        }

                        viewData2.setHasFixedSize(true);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ListReportActivity.this);
                        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        viewData2.setLayoutManager(linearLayoutManager);
                        ReportsAdapter reportsAdapter = new ReportsAdapter(ListReportActivity.this, reportsModelArrayList2);
                        viewData2.setAdapter(reportsAdapter);

                    }
                });
    }
}