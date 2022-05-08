package com.app.bandnara;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.bandnara.ToolBar.BottomBar;
import com.app.bandnara.ToolBar.CloseBar;
import com.app.bandnara.adaptor.CommentsAdapter;
import com.app.bandnara.adaptor.EventsAllAdapter;
import com.app.bandnara.keepFireStory.CommentModel;
import com.app.bandnara.tools.DateTool;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ViewLandMarkActivity extends AppCompatActivity {
    private FrameLayout bottomMenu;// ตัวแปรปุ่มล่าง
    private LinearLayout back; // ปุ่มกลับ
    private TextView tvTitle;
    private String keyId = "";
    private TextView titleShow;
    private ImageView imgShow;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();
    private TextView showDetail,tvLike;
    private LinearLayout btnLike,btnComment,btnGps;
    private DateTool dateTool = new DateTool();
    private ArrayList<CommentModel> commentModelList = new ArrayList<>();
    private RecyclerView viewComment;
    private String nameUserGet="";
    private String latLand="",lngLand="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_land_mark);
        CloseBar closeBar = new CloseBar(this);
        Bundle bundle = getIntent().getExtras();
        keyId = bundle.getString("keyId");
        tvTitle = findViewById(R.id.tvTitle);
        back = findViewById(R.id.back);
        tvTitle = findViewById(R.id.tvTitle);
        titleShow = findViewById(R.id.titleShow);
        imgShow = findViewById(R.id.imgShow);
        showDetail = findViewById(R.id.showDetail);
        btnLike = findViewById(R.id.btnLike);
        viewComment = findViewById(R.id.viewComment);
        tvLike = findViewById(R.id.tvLike);
        btnComment = findViewById(R.id.btnComment);
        btnGps = findViewById(R.id.btnGps);

        // เซ็ตการทำงานปุ่มเมนูล่าง
        bottomMenu = (FrameLayout) findViewById(R.id.bottomMenu);
        BottomBar bottomBar = new BottomBar(getApplicationContext(), bottomMenu);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        getDataShow();

        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLikeLand();
            }
        });

        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogComment();
            }
        });

        btnGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setGpsGo();
            }
        });
    }

    private void setGpsGo() {
        Uri gmmIntentUri = Uri.parse("google.navigation:q="+latLand+","+lngLand+"");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }

    private void openDialogComment() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_comment, null);
        dialogBuilder.setView(dialogView);

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        LinearLayout close = dialogView.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        EditText txtComment = dialogView.findViewById(R.id.txtComment);
        AppCompatButton saveComment = dialogView.findViewById(R.id.saveComment);
        saveComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtComment.getText().toString().isEmpty()){
                    Toast.makeText(ViewLandMarkActivity.this, "กรุณากรอกข้อความ", Toast.LENGTH_SHORT).show();
                }else{
                    setSaveComment(txtComment.getText().toString(),alertDialog);
                }
            }
        });
    }

    private void setSaveComment(String txtFill, AlertDialog alertDialog) {
        CommentModel commentModel = new CommentModel();
        commentModel.setCommentId(UUID.randomUUID().toString());
        commentModel.setTxtComment(txtFill);
        commentModel.setUserId(MyApplication.getUserId());
        commentModel.setLandMarkKey(keyId);
        commentModel.setDateCreate(dateTool.getDateCur());
        commentModel.setNameUser(nameUserGet);
        db.collection("landmarks")
                .document(keyId)
                .update("commentsAll", FieldValue.arrayUnion(commentModel))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        alertDialog.dismiss();
                        Toast.makeText(ViewLandMarkActivity.this, "แสดงความคิดเห็นเรียบร้อย", Toast.LENGTH_SHORT).show();
                        getNewUpdateComment();
                    }
                });
    }

    private void setLikeLand() {
        String userLike = MyApplication.getUserId();
        db.collection("landmarks")
                .document(keyId)
                .update("likeAll", FieldValue.arrayUnion(userLike))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        setLiked();
                    }
                });
    }

    private void setLiked() {
        tvLike.setText("ถูกใจแล้ว");
    }

    public void getNewUpdateComment(){
        DocumentReference docRef = db.collection("landmarks").document(keyId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        try {
                            ArrayList<Map<String, Object>> arrayInTheDocument = (ArrayList<Map<String, Object>> ) document.getData().get("commentsAll");
                            Log.d("CHKCOMM","size : "+arrayInTheDocument.size());
                            commentModelList = new ArrayList<>();
                            for(int i=0; i < arrayInTheDocument.size(); i++) {
                                CommentModel commentModel = new CommentModel();
                                commentModel.setCommentId(arrayInTheDocument.get(i).get("commentId").toString());
                                commentModel.setNameUser(arrayInTheDocument.get(i).get("nameUser").toString());
                                commentModel.setTxtComment(arrayInTheDocument.get(i).get("txtComment").toString());
                                commentModel.setUserId(arrayInTheDocument.get(i).get("userId").toString());
                                commentModel.setDateCreate(Long.parseLong(arrayInTheDocument.get(i).get("dateCreate").toString()));
                                commentModelList.add(commentModel);
                            }
                            if(arrayInTheDocument.size()>0){
                                setListShowComment();
                            }else{

                            }
                        } catch (NullPointerException e) {
                            Log.d("CHKCOMM","Null pointer exception");
                        }

                    }
                } else {
                    Log.d("CHKDATA", "get failed with ", task.getException());
                }
            }
        });
    }

    private void getDataShow(){
        DocumentReference docRef = db.collection("landmarks").document(keyId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("CHKDATA", "DocumentSnapshot data: " + document.getData());
                        String titleGet = document.getData().get("landmark_name").toString();
                        tvTitle.setText(titleGet);
                        titleShow.setText(titleGet);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            showDetail.setText(Html.fromHtml(document.getData().get("landmark_detail").toString(),Html.FROM_HTML_MODE_COMPACT));
                        } else {
                            showDetail.setText(Html.fromHtml(document.getData().get("landmark_detail").toString()));
                        }
                        latLand = document.getData().get("lat").toString();
                        lngLand = document.getData().get("lng").toString();
                        List<String> group = (List<String>) document.getData().get("likeAll");
                        if(group.contains(MyApplication.getUserId())){
                            Log.d("CHKLIKE","YES");
                            setLiked();
                        }else{
                            Log.d("CHKLIKE","NO");
                        }

                        try {
                            ArrayList<Map<String, Object>> arrayInTheDocument = (ArrayList<Map<String, Object>> ) document.getData().get("commentsAll");
                            Log.d("CHKCOMM","size : "+arrayInTheDocument.size());
                            commentModelList = new ArrayList<>();
                            for(int i=0; i < arrayInTheDocument.size(); i++) {
                                CommentModel commentModel = new CommentModel();
                                commentModel.setCommentId(arrayInTheDocument.get(i).get("commentId").toString());
                                commentModel.setNameUser(arrayInTheDocument.get(i).get("nameUser").toString());
                                commentModel.setTxtComment(arrayInTheDocument.get(i).get("txtComment").toString());
                                commentModel.setUserId(arrayInTheDocument.get(i).get("userId").toString());
                                commentModel.setDateCreate(Long.parseLong(arrayInTheDocument.get(i).get("dateCreate").toString()));
                                commentModelList.add(commentModel);
                            }
                            if(arrayInTheDocument.size()>0){
                                setListShowComment();
                            }else{

                            }
                        } catch (NullPointerException e) {
                            Log.d("CHKCOMM","Null pointer exception");
                        }



                        storageRef.child("landmarkPic/land_"+document.getId()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(ViewLandMarkActivity.this)
                                        .load(uri)
                                        .into(imgShow);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle any errors
                            }
                        });

                        DocumentReference docRef = db.collection("users").document(MyApplication.getUserId());
                        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    nameUserGet = document.getData().get("name").toString()+" "+document.getData().get("lastname").toString();
                                }
                            }
                        });
                    }
                } else {
                    Log.d("CHKDATA", "get failed with ", task.getException());
                }
            }
        });
    }

    private void setListShowComment() {

        viewComment.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ViewLandMarkActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        viewComment.setLayoutManager(linearLayoutManager);
        CommentsAdapter commentsAdapter = new CommentsAdapter(ViewLandMarkActivity.this, commentModelList);
        viewComment.setAdapter(commentsAdapter);
    }

}