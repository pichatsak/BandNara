package com.app.bandnara;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.bandnara.ToolBar.BottomBar;
import com.app.bandnara.ToolBar.CloseBar;
import com.app.bandnara.adaptor.EventsAdapter;
import com.app.bandnara.adaptor.NotiAdapter;
import com.app.bandnara.keepFireStory.EventsModel;
import com.app.bandnara.models.NotiUserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class NotifyActivity extends AppCompatActivity {

    private FrameLayout bottomMenu;// ตัวแปรปุ่มล่าง
    private TextView contno;
    private RecyclerView viewnoti;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ArrayList<NotiUserModel> notiUserModelArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);
        CloseBar closeBar = new CloseBar(this);
        contno = findViewById(R.id.contno);
        viewnoti = findViewById(R.id.viewnoti);
        // เซ็ตการทำงานปุ่มเมนูล่าง
        bottomMenu = (FrameLayout)findViewById(R.id.bottomMenu);
        BottomBar bottomBar = new BottomBar(getApplicationContext(),bottomMenu);

        getData();
    }

    private void getData() {
        notiUserModelArrayList = new ArrayList<>();
        db.collection("noti_user")
                .whereEqualTo("userId",MyApplication.getUserId())
                .orderBy("dateCreate", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int sizeGet = task.getResult().size();
                            if(sizeGet>0){
                                contno.setVisibility(View.GONE);
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    NotiUserModel notiUserModel = new NotiUserModel();
                                    notiUserModel.setTxtNoti(document.getData().get("txtNoti").toString());
                                    Timestamp timestamp = (Timestamp) document.getData().get("dateCreate");
                                    notiUserModel.setDate(timestamp.toDate());
                                    notiUserModelArrayList.add(notiUserModel);
                                    String getst =document.getData().get("statusRead").toString();
                                    if(getst.equals("no")){
                                        db.collection("noti_user")
                                                .document(document.getId())
                                                .update("statusRead", "yes");
                                    }
                                }
                                viewnoti.setHasFixedSize(true);
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(NotifyActivity.this);
                                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                                viewnoti.setLayoutManager(linearLayoutManager);
                                NotiAdapter notiAdapter = new NotiAdapter(NotifyActivity.this, notiUserModelArrayList);
                                viewnoti.setAdapter(notiAdapter);
                            }else{
                                contno.setVisibility(View.VISIBLE);
                            }

                        } else {
                            Log.d("CHK_DATA", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}