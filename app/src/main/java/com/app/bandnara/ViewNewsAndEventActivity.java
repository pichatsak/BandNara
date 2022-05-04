package com.app.bandnara;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.bandnara.ToolBar.BottomBar;
import com.app.bandnara.ToolBar.CloseBar;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ViewNewsAndEventActivity extends AppCompatActivity {
    private FrameLayout bottomMenu;// ตัวแปรปุ่มล่าง
    private LinearLayout back; // ปุ่มกลับ
    private TextView tvTitle;
    private String keyId = "";
    private String typeData = "";
    private ImageView imgShow;
    private TextView titleShow;
    private TextView showDetail;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_news_and_event);
        CloseBar closeBar = new CloseBar(this);
        Bundle bundle = getIntent().getExtras();
        keyId = bundle.getString("keyId");
        typeData = bundle.getString("type");
        tvTitle = findViewById(R.id.tvTitle);
        back = findViewById(R.id.back);
        imgShow = findViewById(R.id.imgShow);
        titleShow = findViewById(R.id.titleShow);
        showDetail = findViewById(R.id.showDetail);

        // เซ็ตการทำงานปุ่มเมนูล่าง
        bottomMenu = (FrameLayout) findViewById(R.id.bottomMenu);
        BottomBar bottomBar = new BottomBar(getApplicationContext(), bottomMenu);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if(typeData.equals("news")){
            getDataShow();
        }else{
            getDataShowEvent();
        }

    }

    private void getDataShowEvent() {
        DocumentReference docRef = db.collection("events").document(keyId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("CHKDATA", "DocumentSnapshot data: " + document.getData());
                        String titleGet = document.getData().get("event_title").toString();
                        tvTitle.setText(titleGet);
                        titleShow.setText(titleGet);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            showDetail.setText(Html.fromHtml(document.getData().get("event_detail").toString(),Html.FROM_HTML_MODE_COMPACT));
                        } else {
                            showDetail.setText(Html.fromHtml(document.getData().get("event_detail").toString()));
                        }

                        storageRef.child("eventsPic/events_"+document.getId()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(ViewNewsAndEventActivity.this)
                                        .load(uri)
                                        .into(imgShow);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle any errors
                            }
                        });
                    }
                } else {
                    Log.d("CHKDATA", "get failed with ", task.getException());
                }
            }
        });
    }

    public void getDataShow(){
        DocumentReference docRef = db.collection("news").document(keyId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("CHKDATA", "DocumentSnapshot data: " + document.getData());
                        String titleGet = document.getData().get("title_news").toString();
                        tvTitle.setText(titleGet);
                        titleShow.setText(titleGet);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            showDetail.setText(Html.fromHtml(document.getData().get("title_detail").toString(),Html.FROM_HTML_MODE_COMPACT));
                        } else {
                            showDetail.setText(Html.fromHtml(document.getData().get("title_detail").toString()));
                        }
                        storageRef.child("newsPic/news_"+document.getId()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(ViewNewsAndEventActivity.this)
                                        .load(uri)
                                        .into(imgShow);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle any errors
                            }
                        });
                    }
                } else {
                    Log.d("CHKDATA", "get failed with ", task.getException());
                }
            }
        });
    }
}