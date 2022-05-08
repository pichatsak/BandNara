package com.app.bandnara;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.bandnara.ToolBar.BottomBar;
import com.app.bandnara.ToolBar.CloseBar;
import com.app.bandnara.adaptor.LandMarkAllAdapter;
import com.app.bandnara.adaptor.NewsAllAdapter;
import com.app.bandnara.keepFireStory.EventsModel;
import com.app.bandnara.keepFireStory.LandMarkModel;
import com.app.bandnara.keepFireStory.NewsModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ViewLandMarkAllActivity extends AppCompatActivity {
    private FrameLayout bottomMenu;// ตัวแปรปุ่มล่าง
    private LinearLayout back; // ปุ่มกลับ
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RecyclerView viewLandAll;
    private ArrayList<LandMarkModel> landMarkModelArrayList = new ArrayList<>();
    private int iPos = 3;
    private List<LinearLayout> btnCate = new ArrayList<LinearLayout>(Arrays.asList(new LinearLayout[iPos]));
    private List<AppCompatImageView> imgBtn = new ArrayList<AppCompatImageView>(Arrays.asList(new AppCompatImageView[iPos]));
    private List<TextView> tvBtn = new ArrayList<TextView>(Arrays.asList(new TextView[iPos]));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_land_mark_all);
        CloseBar closeBar = new CloseBar(this);
        back = findViewById(R.id.back);
        viewLandAll = findViewById(R.id.viewLandAll);
        // เซ็ตการทำงานปุ่มเมนูล่าง
        bottomMenu = (FrameLayout) findViewById(R.id.bottomMenu);
        BottomBar bottomBar = new BottomBar(getApplicationContext(), bottomMenu);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        for (int i = 0; i < iPos; i++) {

            String getId = "btnCate" + (i + 1);
            btnCate.set(i, findViewById(getResources().getIdentifier(getId, "id", getPackageName())));

            String getIdImg = "imgBtn" + (i + 1);
            imgBtn.set(i, findViewById(getResources().getIdentifier(getIdImg, "id", getPackageName())));

            String getIdTv = "tvBtn" + (i + 1);
            tvBtn.set(i, findViewById(getResources().getIdentifier(getIdTv, "id", getPackageName())));

            int finalI = i;
            btnCate.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("CHKBTN","click : "+finalI);
                    setShowData(finalI);
                }
            });
        }
        setShowData(0);
    }

    public void setShowData(int pos){
        for (int i = 0; i < iPos; i++) {
            if(pos==i){
                btnCate.get(i).setBackgroundResource(R.drawable.design_fram1);
                imgBtn.get(i).setColorFilter(ContextCompat.getColor(this, R.color.white));
                tvBtn.get(i).setTextColor(getResources().getColor(R.color.white));
                getDataLand(i+1);
            }else{
                btnCate.get(i).setBackgroundResource(R.drawable.design_fram);
                imgBtn.get(i).setColorFilter(ContextCompat.getColor(this, R.color.Teal_50));
                tvBtn.get(i).setTextColor(getResources().getColor(R.color.Teal_50));
            }
        }

    }

    private void getDataLand(int cate) {
        landMarkModelArrayList = new ArrayList<>();
        db.collection("landmarks").whereEqualTo("landmark_cate", String.valueOf(cate))
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                LandMarkModel landMarkModel = new LandMarkModel();
                                landMarkModel.setLandDetail(document.getData().get("landmark_detail").toString());
                                landMarkModel.setKeyId(document.getId());
                                landMarkModel.setLandName(document.getData().get("landmark_name").toString());
                                Timestamp timestamp = (Timestamp) document.getData().get("dateUpdate");
                                landMarkModel.setDateUpdate(timestamp.toDate());
                                landMarkModelArrayList.add(landMarkModel);
                                viewLandAll.setHasFixedSize(true);
                                GridLayoutManager gridLayoutManager = new GridLayoutManager(ViewLandMarkAllActivity.this, 2);
                                gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                                viewLandAll.setLayoutManager(gridLayoutManager);
                                LandMarkAllAdapter landMarkAllAdapter = new LandMarkAllAdapter(ViewLandMarkAllActivity.this, landMarkModelArrayList);
                                viewLandAll.setAdapter(landMarkAllAdapter);
                            }
                        } else {
                            Log.d("CHK_DATA", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}