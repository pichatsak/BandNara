package com.app.bandnara;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.app.bandnara.ToolBar.BottomBar;
import com.app.bandnara.adaptor.AmphurAdapter;
import com.app.bandnara.adaptor.ProvAdapter;
import com.app.bandnara.adaptor.SpinAdapter;
import com.app.bandnara.adaptor.TombonAdapter;
import com.app.bandnara.keepFireStory.AidsData;
import com.app.bandnara.models.AmphuresModel;
import com.app.bandnara.models.NotiWebModel;
import com.app.bandnara.models.ProvincesModel;
import com.app.bandnara.models.TombonsModel;
import com.app.bandnara.tools.AdressData;
import com.app.bandnara.tools.Utils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class elderly3MainActivity extends AppCompatActivity {

    private Spinner spinBeforeName, province, amphur;
    private ArrayList<TombonsModel> tombonsMainModelsList = new ArrayList<>();

    private ArrayList<ProvincesModel> provincesModelsList = new ArrayList<>();
    private ArrayList<AmphuresModel> amphuresModelsList = new ArrayList<>();
    private List<TombonsModel> tombonsModelArrayList = new ArrayList<>();

    private ImageView showPicUpload;
    private LinearLayout btnChoosePic, contUpload;
    private EditText name, lastName, idCard, homeNo, moo, soi, road, postCode, phone;
    private Spinner district;
    private Uri outputFileUri;
    private int statusChooseImg = 0;
    private Uri imageChooseCur;
    private List<String> listBeforeName = new ArrayList<>();
    private AppCompatButton saveRegis;


    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageReference = storage.getReference();
    private FrameLayout bottomMenu;// ??????????????????????????????????????????


    ProgressDialog dialogLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elderly3_main);
        spinBeforeName = findViewById(R.id.spinBeforeName2);
        // ????????????????????????????????????????????????????????????????????????
        bottomMenu = (FrameLayout) findViewById(R.id.bottomMenu);
        BottomBar bottomBar = new BottomBar(getApplicationContext(), bottomMenu);
        spinBeforeName = findViewById(R.id.spinBeforeName);
        province = findViewById(R.id.province);
        amphur = findViewById(R.id.amphur);
        btnChoosePic = findViewById(R.id.btnChoosePic);
        contUpload = findViewById(R.id.contUpload);
        showPicUpload = findViewById(R.id.showPicUpload);
        saveRegis = findViewById(R.id.saveRegis);

        name = findViewById(R.id.name);
        lastName = findViewById(R.id.lastName);
        idCard = findViewById(R.id.idCard);
        homeNo = findViewById(R.id.homeNo);
        moo = findViewById(R.id.moo);
        soi = findViewById(R.id.soi);
        road = findViewById(R.id.road);
        district = findViewById(R.id.district);
        postCode = findViewById(R.id.postCode);
        phone = findViewById(R.id.phone);

        listBeforeName = new ArrayList<>();
        listBeforeName.add("???????????????????????????????????????????????????");
        listBeforeName.add("?????????");
        listBeforeName.add("?????????");
        listBeforeName.add("??????????????????");
        SpinAdapter beforeNameAdapter = new SpinAdapter(this, listBeforeName);
        spinBeforeName.setAdapter(beforeNameAdapter);
        getTombonMain();
        getProvAll();

        btnChoosePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Determine Uri of camera image to save.
                final File root = new File(Environment.getExternalStorageDirectory() + File.separator + "amfb" + File.separator);
                root.mkdir();
                final String fname = "img_" + System.currentTimeMillis() + ".jpg";
                final File sdImageMainDirectory = new File(root, fname);
                outputFileUri = Uri.fromFile(sdImageMainDirectory);

                // Camera.
                final List<Intent> cameraIntents = new ArrayList<Intent>();
                final Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                final PackageManager packageManager = getPackageManager();
                final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
                for (ResolveInfo res : listCam) {
                    final String packageName = res.activityInfo.packageName;
                    final Intent intent = new Intent(captureIntent);
                    intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
                    intent.setPackage(packageName);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                    cameraIntents.add(intent);
                }

                //FileSystem
                final Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.addCategory(Intent.CATEGORY_OPENABLE);
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

                // Chooser of filesystem options.
                final Intent chooserIntent = Intent.createChooser(galleryIntent, "?????????????????????????????????");
                // Add the camera options.
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[]{}));
                startActivityForResult(chooserIntent, 1);

            }
        });

        saveRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSaveData();
            }
        });
    }

    private void setSaveData() {

        String nameGet = name.getText().toString();
        String lastNameGet = lastName.getText().toString();
        String idCardGet = idCard.getText().toString();
        String homeNoGet = homeNo.getText().toString();
        String mooGet = moo.getText().toString();
        String soiGet = soi.getText().toString();
        String roadGet = road.getText().toString();
        String districtGet = tombonsModelArrayList.get(district.getSelectedItemPosition()).getName_th();
        String postCodeGet = postCode.getText().toString();
        String phoneGet = phone.getText().toString();
        String provinceGet = provincesModelsList.get(province.getSelectedItemPosition()).getProvName();
        String amphurGet = amphuresModelsList.get(amphur.getSelectedItemPosition()).getAmpName();
        String beforeNameGet = listBeforeName.get(spinBeforeName.getSelectedItemPosition());
        if (nameGet.isEmpty() || lastNameGet.isEmpty() || idCardGet.isEmpty() || homeNoGet.isEmpty() || mooGet.isEmpty() || soiGet.isEmpty() || roadGet.isEmpty() ||
                districtGet.isEmpty() || postCodeGet.isEmpty() || phoneGet.isEmpty() || provinceGet.isEmpty() || amphurGet.isEmpty() ||
                spinBeforeName.getSelectedItemPosition() == 0||idCardGet.length()!=13||phoneGet.length()!=10
        ) {
            Toast.makeText(this, "???????????????????????????????????????????????????????????????????????????", Toast.LENGTH_SHORT).show();
        } else if (statusChooseImg == 0) {
            Toast.makeText(this, "????????????????????????????????????????????????", Toast.LENGTH_SHORT).show();
        } else {
            AidsData aidsData = new AidsData();
            aidsData.setBeforeName(beforeNameGet);
            aidsData.setName(nameGet);
            aidsData.setLastName(lastNameGet);
            aidsData.setIdCard(idCardGet);
            aidsData.setHomeNo(homeNoGet);
            aidsData.setMoo(mooGet);
            aidsData.setSoi(soiGet);
            aidsData.setRoad(roadGet);
            aidsData.setProvince(provinceGet);
            aidsData.setAmphur(amphurGet);
            aidsData.setDistrict(districtGet);
            aidsData.setPostCode(postCodeGet);
            aidsData.setPhone(phoneGet);
            aidsData.setDateCreate(FieldValue.serverTimestamp());
            aidsData.setDateModify(FieldValue.serverTimestamp());
            aidsData.setStatus("wait");
            aidsData.setUserId(MyApplication.getUserId());
            dialogLoad = ProgressDialog.show(elderly3MainActivity.this, "", "???????????????????????????????????????????????????...", true);
            db.collection("aids_data")
                    .add(aidsData)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d("CHKDB", "DocumentSnapshot written with ID: " + documentReference.getId());
                            setSendNoti();
                            setUploadImage(documentReference.getId());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("CHKDB", "Error adding document", e);

                            Toast.makeText(elderly3MainActivity.this, "??????????????????????????????????????????", Toast.LENGTH_SHORT).show();
                            dialogLoad.dismiss();
                        }
                    });
        }

    }

    private void setSendNoti() {
        NotiWebModel notiWebModel = new NotiWebModel();
        notiWebModel.setTxtNoti("?????????????????????????????????????????????????????????????????????????????????????????????????????????");
        notiWebModel.setDateCreate(FieldValue.serverTimestamp());
        notiWebModel.setStatusRead("no");
        notiWebModel.setUserId(MyApplication.getUserId());
        db.collection("noti_web")
                .add(notiWebModel)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("CHKDB", "Error adding document", e);
                    }
                });
    }

    private void setUploadImage(String id) {
        StorageReference ref4 = storageReference.child("aidPic/aid_" + id);
        ref4.putFile(imageChooseCur)
                .addOnSuccessListener(
                        new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                dialogLoad.dismiss();
                                Toast.makeText(elderly3MainActivity.this, "???????????????????????????????????????????????????????????????", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialogLoad.dismiss();
                        Toast.makeText(elderly3MainActivity.this, "?????????????????????????????????????????????????????????????????????", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void getProvAll() {
        AdressData adressData = new AdressData(this);
        ArrayList<ProvincesModel> provincesModels = adressData.getProvAll();
        provincesModelsList = provincesModels;
        ProvAdapter provAdapter = new ProvAdapter(elderly3MainActivity.this, provincesModels);
        province.setAdapter(provAdapter);
        province.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                getAmphureByProvId(provincesModels.get(i).getId());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void getAmphureByProvId(String provId) {
        AdressData adressData = new AdressData(this);
        ArrayList<AmphuresModel> amphuresModels = adressData.getAmpuhr(provId);
        amphuresModelsList = amphuresModels;
        AmphurAdapter amphurAdapter = new AmphurAdapter(elderly3MainActivity.this, amphuresModels);
        amphur.setAdapter(amphurAdapter);
        amphur.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getTombonByAmphurId(amphuresModelsList.get(i).getAmpId());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void getTombonMain(){
        String jsonFileString = Utils.getJsonTumbonFromAssets(getApplicationContext());
        Gson gson = new Gson();
        Type listUserType = new TypeToken<ArrayList<TombonsModel>>() {}.getType();
        tombonsMainModelsList = gson.fromJson(jsonFileString, listUserType);
    }

    public void getTombonByAmphurId(String amphursId) {
        Log.i("CHKGSON", "choose amp : " + amphursId);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            List<TombonsModel> filteredArticleList = new ArrayList<>();
            TombonsModel tombonsModelFirst = new TombonsModel();
            tombonsModelFirst.setName_th("???????????????????????????");
            tombonsModelFirst.setId("0");
            filteredArticleList.add(tombonsModelFirst);
            if(!amphursId.equals("0")){
                filteredArticleList.addAll(tombonsMainModelsList.stream().filter(items -> items.getAmphure_id().contains(amphursId)).collect(Collectors.toList()));
            }
            tombonsModelArrayList = filteredArticleList;
            TombonAdapter tombonAdapter = new TombonAdapter(elderly3MainActivity.this, tombonsModelArrayList);
            district.setAdapter(tombonAdapter);
            district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if(i!=0){
                        postCode.setText(tombonsModelArrayList.get(i).getZip_code());
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                final boolean isCamera;
                if (data == null) {
                    isCamera = true;
                } else {
                    final String action = data.getAction();
                    if (action == null) {
                        isCamera = false;
                    } else {
                        isCamera = action.equals(MediaStore.ACTION_IMAGE_CAPTURE);
                    }
                }

                Uri selectedImageUri;
                if (isCamera) {
                    selectedImageUri = outputFileUri;
                    //Bitmap factory
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    // downsizing image as it throws OutOfMemory Exception for larger
                    // images
                    options.inSampleSize = 8;
                    final Bitmap bitmap = BitmapFactory.decodeFile(selectedImageUri.getPath(), options);
                    contUpload.setVisibility(View.GONE);
                    showPicUpload.setVisibility(View.VISIBLE);
                    showPicUpload.setImageURI(selectedImageUri);
                    imageChooseCur = selectedImageUri;
                    statusChooseImg = 1;
                } else {
                    selectedImageUri = data == null ? null : data.getData();
                    Log.d("ImageURI", selectedImageUri.getLastPathSegment());
                    // /Bitmap factory
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    // downsizing image as it throws OutOfMemory Exception for larger
                    // images
                    options.inSampleSize = 8;
                    try {//Using Input Stream to get uri did the trick
                        InputStream input = getContentResolver().openInputStream(selectedImageUri);
                        final Bitmap bitmap = BitmapFactory.decodeStream(input);
                        contUpload.setVisibility(View.GONE);
                        showPicUpload.setVisibility(View.VISIBLE);
                        showPicUpload.setImageURI(selectedImageUri);
                        imageChooseCur = selectedImageUri;
                        statusChooseImg = 1;
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}