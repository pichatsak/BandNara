package com.app.bandnara;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.app.bandnara.ToolBar.BottomBar;
import com.app.bandnara.ToolBar.CloseBar;
import com.app.bandnara.adaptor.NewsAdapter;
import com.app.bandnara.adaptor.NewsAllAdapter;
import com.app.bandnara.keepFireStory.NewsModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;

public class ViewNewsAllActivity extends AppCompatActivity {
    private RecyclerView viewNewAll;
    private FrameLayout bottomMenu;// ตัวแปรปุ่มล่าง
    private LinearLayout back; // ปุ่มกลับ

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ArrayList<NewsModel> newsModelArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_news_all);
        CloseBar closeBar = new CloseBar(this);
        viewNewAll = findViewById(R.id.viewNewAll);
        back = findViewById(R.id.back);

        // เซ็ตการทำงานปุ่มเมนูล่าง
        bottomMenu = (FrameLayout) findViewById(R.id.bottomMenu);
        BottomBar bottomBar = new BottomBar(getApplicationContext(), bottomMenu);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        getDataNew();
    }

    private void getDataNew() {
        newsModelArrayList = new ArrayList<>();
        db.collection("news")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                NewsModel newsModel = new NewsModel();
                                newsModel.setNewsDetail(document.getData().get("title_detail").toString());
                                newsModel.setKeyId(document.getId());
                                newsModel.setNewsTitle(document.getData().get("title_news").toString());
                                Timestamp timestamp = (Timestamp) document.getData().get("dateUpdate");
                                newsModel.setDateUpdate(timestamp.toDate());
                                newsModelArrayList.add(newsModel);
                                viewNewAll.setHasFixedSize(true);
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ViewNewsAllActivity.this);
                                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                                viewNewAll.setLayoutManager(linearLayoutManager);
                                NewsAllAdapter newsAdapter = new NewsAllAdapter(ViewNewsAllActivity.this, newsModelArrayList);
                                viewNewAll.setAdapter(newsAdapter);
                            }
                        } else {
                            Log.d("CHK_DATA", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}