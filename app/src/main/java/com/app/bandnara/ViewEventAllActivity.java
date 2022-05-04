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
import com.app.bandnara.adaptor.EventsAllAdapter;
import com.app.bandnara.adaptor.NewsAllAdapter;
import com.app.bandnara.keepFireStory.EventsModel;
import com.app.bandnara.keepFireStory.NewsModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ViewEventAllActivity extends AppCompatActivity {
    private RecyclerView viewEventAll;
    private FrameLayout bottomMenu;// ตัวแปรปุ่มล่าง
    private LinearLayout back; // ปุ่มกลับ

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ArrayList<EventsModel> eventsModelArrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event_all);
        CloseBar closeBar = new CloseBar(this);
        viewEventAll = findViewById(R.id.viewEventAll);
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
        eventsModelArrayList = new ArrayList<>();
        db.collection("events")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                EventsModel eventsModel = new EventsModel();
                                eventsModel.setEventDetail(document.getData().get("event_detail").toString());
                                eventsModel.setKeyId(document.getId());
                                eventsModel.setEventTitle(document.getData().get("event_title").toString());
                                Timestamp timestamp = (Timestamp) document.getData().get("dateUpdate");
                                eventsModel.setDateUpdate(timestamp.toDate());
                                eventsModelArrayList.add(eventsModel);
                                viewEventAll.setHasFixedSize(true);
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ViewEventAllActivity.this);
                                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                                viewEventAll.setLayoutManager(linearLayoutManager);
                                EventsAllAdapter eventsAllAdapter = new EventsAllAdapter(ViewEventAllActivity.this, eventsModelArrayList);
                                viewEventAll.setAdapter(eventsAllAdapter);
                            }
                        } else {
                            Log.d("CHK_DATA", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}