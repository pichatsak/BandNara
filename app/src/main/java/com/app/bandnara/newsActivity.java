package com.app.bandnara;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.bandnara.ToolBar.BottomBar;
import com.app.bandnara.ToolBar.CloseBar;
import com.app.bandnara.adaptor.EventsAdapter;
import com.app.bandnara.adaptor.NewsAdapter;
import com.app.bandnara.keepFireStory.EventsModel;
import com.app.bandnara.keepFireStory.NewsModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class newsActivity extends AppCompatActivity {
    private TextView goProfile;
    private FrameLayout bottomMenu;// ตัวแปรปุ่มล่าง
    private RecyclerView view_news,view_events;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private LinearLayout goNewsAll,goEventAll;
    private ArrayList<NewsModel> newsModelArrayList = new ArrayList<>();
    private ArrayList<EventsModel> eventsModelArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        CloseBar closeBar = new CloseBar(this);
        goProfile = findViewById(R.id.goProfile);
        view_news = findViewById(R.id.view_news);
        view_events = findViewById(R.id.view_events);
        goNewsAll = findViewById(R.id.goNewsAll);
        goEventAll = findViewById(R.id.goEventAll);

        // เซ็ตการทำงานปุ่มเมนูล่าง
        bottomMenu = (FrameLayout)findViewById(R.id.bottomMenu);
        BottomBar bottomBar = new BottomBar(getApplicationContext(),bottomMenu);

        goProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(newsActivity.this, informationUserActivity.class);
                startActivity(login);
            }
        });

        getDataNew();
        getDataEvent();

        goNewsAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(newsActivity.this, ViewNewsAllActivity.class);
                startActivity(intent);
            }
        });

        goEventAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(newsActivity.this, ViewEventAllActivity.class);
                startActivity(intent);
            }
        });

    }

    private void getDataEvent() {
        eventsModelArrayList = new ArrayList<>();
        db.collection("events")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("CHK_DATA", document.getId() + " => " + document.getData());
                                EventsModel eventsModel = new EventsModel();
                                eventsModel.setEventDetail(document.getData().get("event_detail").toString());
                                eventsModel.setKeyId(document.getId());
                                eventsModel.setEventTitle(document.getData().get("event_title").toString());
                                eventsModelArrayList.add(eventsModel);
                                view_events.setHasFixedSize(true);
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(newsActivity.this);
                                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                                view_events.setLayoutManager(linearLayoutManager);
                                EventsAdapter eventsAdapter = new EventsAdapter(newsActivity.this, eventsModelArrayList);
                                view_events.setAdapter(eventsAdapter);
                            }
                        } else {
                            Log.d("CHK_DATA", "Error getting documents: ", task.getException());
                        }
                    }
                });
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
                                Log.d("CHK_DATA", document.getId() + " => " + document.getData());
                                NewsModel newsModel = new NewsModel();
                                newsModel.setNewsDetail(document.getData().get("title_detail").toString());
                                newsModel.setKeyId(document.getId());
                                newsModel.setNewsTitle(document.getData().get("title_news").toString());
                                newsModelArrayList.add(newsModel);
                                view_news.setHasFixedSize(true);
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(newsActivity.this);
                                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                                view_news.setLayoutManager(linearLayoutManager);
                                NewsAdapter newsAdapter = new NewsAdapter(newsActivity.this, newsModelArrayList);
                                view_news.setAdapter(newsAdapter);
                            }
                        } else {
                            Log.d("CHK_DATA", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}