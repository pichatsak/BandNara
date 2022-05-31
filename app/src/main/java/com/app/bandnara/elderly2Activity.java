package com.app.bandnara;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.DatePickerDialog;
import android.app.Dialog;
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
import android.view.Window;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.bandnara.ToolBar.BottomBar;
import com.app.bandnara.adaptor.AmphurAdapter;
import com.app.bandnara.adaptor.ProvAdapter;
import com.app.bandnara.adaptor.SpinAdapter;
import com.app.bandnara.keepFireStory.BabyData;
import com.app.bandnara.models.AmphuresModel;
import com.app.bandnara.models.NotiWebModel;
import com.app.bandnara.models.ProvincesModel;
import com.app.bandnara.tools.AdressData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class elderly2Activity extends AppCompatActivity {
    private int pageCur = 1;

    private Uri outputFileUri;
    private LinearLayout back; // ปุ่มกลับ
    private LinearLayout page1, page2, page3, page4;
    private LinearLayout processPage1, processPage2, processPage3, processPage4;
    // Start ตัวแปร Page 1
    private Spinner spinRelayBaby, spinCitizen;
    private List<String> listCitizen = new ArrayList<>();
    private List<String> listRelayBaby = new ArrayList<>();

    private List<String> listWork = new ArrayList<>();
    private List<String> listEdu = new ArrayList<>();
    private EditText fillRelayBay, fillEdu;
    private TextView birth;
    private List<String> listBeforeName = new ArrayList<>();
    final Calendar myCalendar = Calendar.getInstance(); //ปฏิทิน
    private int posAmphur = 0;
    private EditText name, lastName, idCard, homeNo, moo, soi, road, district, postCode, phone, age;

    private EditText homeNo2, moo2, soi2, road2, district2, postCode2, phone2;
    private Spinner spinRelay, spinBeforeName, province, amphur, spinDeform, province2, amphur2, spinWork, spinEdu;
    private CheckBox cbchk;
    private ArrayList<ProvincesModel> provincesModelsList = new ArrayList<>();
    private ArrayList<AmphuresModel> amphuresModelsList = new ArrayList<>();

    private ArrayList<ProvincesModel> provincesModelsList2 = new ArrayList<>();
    private ArrayList<AmphuresModel> amphuresModelsList2 = new ArrayList<>();
    private AppCompatButton btnNextPage2;
    // End ตัวแปร Page 1

    // Start ตัวแปร Page 2
    private Spinner spinBeforeNameChild;
    private List<String> listBeforeChild = new ArrayList<>();

    private EditText nameChild,lastnameChild,idCardChild;
    private TextView birthChild;
    final Calendar myCalendarbirthChild = Calendar.getInstance(); //ปฏิทิน

    private Spinner spinBeforeNameMother,spinMotherCitizen,spinWorkMother,spinEduMother;
    private EditText motherName,motherLastName,motherAge,fillEduMother;
    private List<String> listBeforeNameMother = new ArrayList<>();
    private List<String> listCitizenMother = new ArrayList<>();
    private List<String> listWorkMother = new ArrayList<>();
    private List<String> listEduMother = new ArrayList<>();

    private Spinner spinBeforeNameFather,spinFatherCitizen,spinWorkFather,spinEduFather;
    private EditText fatherName,fatherLastName,fatherAge,fillEduFather;
    private List<String> listBeforeNameFather = new ArrayList<>();
    private List<String> listCitizenFather = new ArrayList<>();
    private List<String> listWorkFather = new ArrayList<>();
    private List<String> listEduFather = new ArrayList<>();
    private LinearLayout contFathers;
    private int haveFather=1;
    private CheckBox chkboxFather;

    private AppCompatButton btnNextPage3;
    // End ตัวแปร Page 2

    // Start ตัวแปร Page 3
    private int chooseCb=1;
    private EditText bankNo,bankName,bankOwner;
    private int iPosCb=3;
    private List<RelativeLayout> cbBank = new ArrayList<RelativeLayout>(Arrays.asList(new RelativeLayout[iPosCb]));
    private List<ImageView> imgCb = new ArrayList<ImageView>(Arrays.asList(new ImageView[iPosCb]));
    private int statusChooseCopyBank = 0;
    private Uri imageCopyBank ;
    private LinearLayout chooseCopyBank,contShowCopyBank;
    private ImageView showPicCopyBank;

    private int statusChooseCopyFamily = 0;
    private Uri imageCopyFamily ;
    private LinearLayout chooseCopyFamily,contShowCopyFamily;
    private ImageView showPicCopyFamily;

    private int statusChooseCopyFamily2 = 0;
    private Uri imageCopyFamily2 ;
    private LinearLayout chooseCopyFamily2,contShowCopyFamily2;
    private ImageView showPicCopyFamily2;

    private int statusChooseCopyParent = 0;
    private Uri imageCopyParent ;
    private LinearLayout chooseCopyParent,contShowCopyParent;
    private ImageView showPicCopyParent;

    private int statusChooseCopyChild = 0;
    private Uri imageCopyChild ;
    private LinearLayout chooseCopyChild,contShowCopyChild;
    private ImageView showPicCopyChild;
    private AppCompatButton saveDataToDb;
    private CheckBox cbAllow;
    private boolean allowStatus=false;
    // End ตัวแปร Page 3

    private int IMG_POS_CUR=0;
    private BabyData babyData = new BabyData();

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageReference = storage.getReference();


    private FrameLayout bottomMenu;// ตัวแปรปุ่มล่าง

    ProgressDialog dialogLoad;
    TextView btnSeeExCopyBank;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elderly2);
        // เซ็ตการทำงานปุ่มเมนูล่าง
        bottomMenu = (FrameLayout) findViewById(R.id.bottomMenu);
        BottomBar bottomBar = new BottomBar(getApplicationContext(), bottomMenu);

        back = findViewById(R.id.back);
        page1 = findViewById(R.id.page1);
        page2 = findViewById(R.id.page2);
        page3 = findViewById(R.id.page3);
        processPage1 = findViewById(R.id.processPage1);
        processPage2 = findViewById(R.id.processPage2);
        processPage3 = findViewById(R.id.processPage3);

        // Start method Page 1
        setIdPage1();
        setSpinnerPage1();
        setClickBtnPage1();
        // End method Page 1

        // Start method Page 2
        setIdPage2();
        setSpinnerPage2();
        setClickBtnPage2();
        // End method Page 2

        // Start method Page 3
        setIdPage3();
        setClickBtnPage3();
        // End method Page 3

        setShowPage(1);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pageCur == 1) {
                    finish();
                } else if (pageCur == 2) {
                    setShowPage(1);
                } else if (pageCur == 3) {
                    setShowPage(2);
                }
            }
        });
        btnSeeExCopyBank = findViewById(R.id.btnSeeExCopyBank);
        setDialogEx();
    }

    private void setDialogEx() {
        btnSeeExCopyBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(elderly2Activity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.dialog_ex_bank);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                dialog.show();

                int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
                int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.90);

                dialog.getWindow().setLayout(width, height);

                AppCompatButton okbtn = dialog.findViewById(R.id.okbtn);
                okbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    private void setClickBtnPage3() {
        cbAllow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cbAllow.isChecked()){
                    allowStatus = true;
                }else{
                    allowStatus = false;
                }
            }
        });
        chooseCopyBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToChooseImg(1);
            }
        });
        chooseCopyFamily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToChooseImg(2);
            }
        });
        chooseCopyFamily2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToChooseImg(3);
            }
        });
        chooseCopyParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToChooseImg(4);
            }
        });
        chooseCopyChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToChooseImg(5);
            }
        });

        saveDataToDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDataToDbFinal();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == IMG_POS_CUR) {
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
                    if(IMG_POS_CUR==1){
                        contShowCopyBank.setVisibility(View.GONE);
                        showPicCopyBank.setVisibility(View.VISIBLE);
                        showPicCopyBank.setImageURI(selectedImageUri);
                        imageCopyBank = selectedImageUri;
                        statusChooseCopyBank = 1;
                    }else if(IMG_POS_CUR==2){
                        contShowCopyFamily.setVisibility(View.GONE);
                        showPicCopyFamily.setVisibility(View.VISIBLE);
                        showPicCopyFamily.setImageURI(selectedImageUri);
                        imageCopyFamily = selectedImageUri;
                        statusChooseCopyFamily = 1;
                    }else if(IMG_POS_CUR==3){
                        contShowCopyFamily2.setVisibility(View.GONE);
                        showPicCopyFamily2.setVisibility(View.VISIBLE);
                        showPicCopyFamily2.setImageURI(selectedImageUri);
                        imageCopyFamily2 = selectedImageUri;
                        statusChooseCopyFamily2 = 1;
                    }else if(IMG_POS_CUR==4){
                        contShowCopyParent.setVisibility(View.GONE);
                        showPicCopyParent.setVisibility(View.VISIBLE);
                        showPicCopyParent.setImageURI(selectedImageUri);
                        imageCopyParent = selectedImageUri;
                        statusChooseCopyParent = 1;
                    }else if(IMG_POS_CUR==5){
                        contShowCopyChild.setVisibility(View.GONE);
                        showPicCopyChild.setVisibility(View.VISIBLE);
                        showPicCopyChild.setImageURI(selectedImageUri);
                        imageCopyChild = selectedImageUri;
                        statusChooseCopyChild = 1;
                    }

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
                        if(IMG_POS_CUR==1){
                            contShowCopyBank.setVisibility(View.GONE);
                            showPicCopyBank.setVisibility(View.VISIBLE);
                            showPicCopyBank.setImageURI(selectedImageUri);
                            imageCopyBank = selectedImageUri;
                            statusChooseCopyBank = 1;
                        }else if(IMG_POS_CUR==2){
                            contShowCopyFamily.setVisibility(View.GONE);
                            showPicCopyFamily.setVisibility(View.VISIBLE);
                            showPicCopyFamily.setImageURI(selectedImageUri);
                            imageCopyFamily = selectedImageUri;
                            statusChooseCopyFamily = 1;
                        }else if(IMG_POS_CUR==3){
                            contShowCopyFamily2.setVisibility(View.GONE);
                            showPicCopyFamily2.setVisibility(View.VISIBLE);
                            showPicCopyFamily2.setImageURI(selectedImageUri);
                            imageCopyFamily2 = selectedImageUri;
                            statusChooseCopyFamily2 = 1;
                        }else if(IMG_POS_CUR==4){
                            contShowCopyParent.setVisibility(View.GONE);
                            showPicCopyParent.setVisibility(View.VISIBLE);
                            showPicCopyParent.setImageURI(selectedImageUri);
                            imageCopyParent = selectedImageUri;
                            statusChooseCopyParent = 1;
                        }else if(IMG_POS_CUR==5){
                            contShowCopyChild.setVisibility(View.GONE);
                            showPicCopyChild.setVisibility(View.VISIBLE);
                            showPicCopyChild.setImageURI(selectedImageUri);
                            imageCopyChild = selectedImageUri;
                            statusChooseCopyChild = 1;
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void goToChooseImg(int posImg){
        // Determine Uri of camera image to save.
        IMG_POS_CUR = posImg;
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
        final Intent chooserIntent = Intent.createChooser(galleryIntent, "เลือกรูปภาพ");
        // Add the camera options.
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[]{}));
        startActivityForResult(chooserIntent, posImg);
    }

    private void setShowCb(int finalI) {
        for (int i = 0; i < iPosCb; i++) {
            if((finalI)==(i)){
                imgCb.get(i).setVisibility(View.VISIBLE);
            }else{
                imgCb.get(i).setVisibility(View.GONE);
            }
        }
    }

    private void setIdPage3() {
        bankNo = findViewById(R.id.bankNo);
        bankName = findViewById(R.id.bankName);
        cbAllow = findViewById(R.id.cbAllow);

        chooseCopyBank = findViewById(R.id.chooseCopyBank);
        contShowCopyBank = findViewById(R.id.contShowCopyBank);
        showPicCopyBank = findViewById(R.id.showPicCopyBank);

        chooseCopyFamily = findViewById(R.id.chooseCopyFamily);
        contShowCopyFamily = findViewById(R.id.contShowCopyFamily);
        showPicCopyFamily = findViewById(R.id.showPicCopyFamily);

        chooseCopyFamily2 = findViewById(R.id.chooseCopyFamily2);
        contShowCopyFamily2 = findViewById(R.id.contShowCopyFamily2);
        showPicCopyFamily2 = findViewById(R.id.showPicCopyFamily2);

        chooseCopyParent = findViewById(R.id.chooseCopyParent);
        contShowCopyParent = findViewById(R.id.contShowCopyParent);
        showPicCopyParent = findViewById(R.id.showPicCopyParent);

        chooseCopyChild= findViewById(R.id.chooseCopyChild);
        contShowCopyChild = findViewById(R.id.contShowCopyChild);
        showPicCopyChild = findViewById(R.id.showPicCopyChild);
        saveDataToDb = findViewById(R.id.saveDataToDb);

        for (int i = 0; i < iPosCb; i++) {
            String getId = "cbbank" + (i + 1);
            String getIdImg = "imgcb" + (i + 1);
            imgCb.set(i, findViewById(getResources().getIdentifier(getIdImg, "id", getPackageName())));
            cbBank.set(i, findViewById(getResources().getIdentifier(getId, "id", getPackageName())));

            int finalI = i;
            cbBank.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    chooseCb = finalI +1;
                    setShowCb(finalI);
                }
            });
        }
    }
    private void saveDataToDbFinal() {
        String bankNoGet = bankNo.getText().toString();
        String bankNameGet = bankName.getText().toString();
        if(statusChooseCopyParent==0||statusChooseCopyChild==0||statusChooseCopyFamily==0||statusChooseCopyFamily2==0||statusChooseCopyBank==0){
            Toast.makeText(this, "กรุณาอัพโหลดไฟล์ให้ครบถ้วน", Toast.LENGTH_SHORT).show();
        }else if(bankNoGet.isEmpty()||bankNameGet.isEmpty()){
            Toast.makeText(this, "กรุณากรอกข้อมูลให้ครบถ้วน", Toast.LENGTH_SHORT).show();
        }else if(!allowStatus){
            Toast.makeText(this, "กรุณากดยอมรับ", Toast.LENGTH_SHORT).show();
        }else{
            babyData.setBankNo(bankNoGet);
            babyData.setBankName(bankNameGet);
            babyData.setBankOwner(""+chooseCb);
            babyData.setCbBc(chooseCb);
            babyData.setStatus("wait");
            babyData.setDateCreate(FieldValue.serverTimestamp());
            babyData.setDateModify(FieldValue.serverTimestamp());
            babyData.setUserId(MyApplication.getUserId());
            dialogLoad = ProgressDialog.show(elderly2Activity.this, "","กำลังบันทึกข้อมูล...", true);
            db.collection("baby_data")
                    .add(babyData)
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
                            Toast.makeText(elderly2Activity.this, "เกิดข้อผิดผลาด", Toast.LENGTH_SHORT).show();
                            dialogLoad.dismiss();
                        }
                    });
        }
    }

    private void setSendNoti() {
        NotiWebModel notiWebModel = new NotiWebModel();
        notiWebModel.setTxtNoti("มีการขึ้นทะเบียนเด็กแรกเกิดใหม่");
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
        StorageReference ref1 = storageReference.child("imgBaby/baby_"+id+"/parent_" + id);
        ref1.putFile(imageCopyParent)
                .addOnSuccessListener(
                        new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                StorageReference ref2 = storageReference.child("imgBaby/baby_"+id+"/child_" + id);
                                ref2.putFile(imageCopyChild)
                                        .addOnSuccessListener(
                                                new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                    @Override
                                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                        StorageReference ref3 = storageReference.child("imgBaby/baby_"+id+"/dr02_1_" + id);
                                                        ref3.putFile(imageCopyFamily)
                                                                .addOnSuccessListener(
                                                                        new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                                            @Override
                                                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                                                StorageReference ref4 = storageReference.child("imgBaby/baby_"+id+"/dr02_2_" + id);
                                                                                ref4.putFile(imageCopyFamily2)
                                                                                        .addOnSuccessListener(
                                                                                                new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                                                                    @Override
                                                                                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                                                                        StorageReference ref5 = storageReference.child("imgBaby/baby_"+id+"/bank_" + id);
                                                                                                        ref5.putFile(imageCopyBank)
                                                                                                                .addOnSuccessListener(
                                                                                                                        new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                                                                                            @Override
                                                                                                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                                                                                                dialogLoad.dismiss();
                                                                                                                                Toast.makeText(elderly2Activity.this, "บันทึกข้อมูลเรียบร้อย", Toast.LENGTH_SHORT).show();
                                                                                                                                finish();
                                                                                                                            }
                                                                                                                        })
                                                                                                                .addOnFailureListener(new OnFailureListener() {
                                                                                                                    @Override
                                                                                                                    public void onFailure(@NonNull Exception e) {
                                                                                                                        dialogLoad.dismiss();
                                                                                                                        Toast.makeText(elderly2Activity.this, "ไม่สามารถอัพโหลดไฟล์ได้", Toast.LENGTH_SHORT).show();
                                                                                                                    }
                                                                                                                });
                                                                                                    }
                                                                                                })
                                                                                        .addOnFailureListener(new OnFailureListener() {
                                                                                            @Override
                                                                                            public void onFailure(@NonNull Exception e) {
                                                                                                dialogLoad.dismiss();
                                                                                                Toast.makeText(elderly2Activity.this, "ไม่สามารถอัพโหลดไฟล์ได้", Toast.LENGTH_SHORT).show();
                                                                                            }
                                                                                        });
                                                                            }
                                                                        })
                                                                .addOnFailureListener(new OnFailureListener() {
                                                                    @Override
                                                                    public void onFailure(@NonNull Exception e) {
                                                                        dialogLoad.dismiss();
                                                                        Toast.makeText(elderly2Activity.this, "ไม่สามารถอัพโหลดไฟล์ได้", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                });
                                                    }
                                                })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                dialogLoad.dismiss();
                                                Toast.makeText(elderly2Activity.this, "ไม่สามารถอัพโหลดไฟล์ได้", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialogLoad.dismiss();
                        Toast.makeText(elderly2Activity.this, "ไม่สามารถอัพโหลดไฟล์ได้", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public String getAge(int year, int month, int day) {
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month-1, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = "";
        if(ageInt<=0){
            ageS = "0";
        }else{
            ageS = ageInt.toString();
        }
        return ageS;
    }

    private void setClickBtnPage2() {
        birthChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        myCalendarbirthChild.set(Calendar.YEAR, year);
                        myCalendarbirthChild.set(Calendar.MONTH, month);
                        myCalendarbirthChild.set(Calendar.DAY_OF_MONTH, day);
                        String myFormat = "dd/MM/yyyy";
                        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
                        birthChild.setText(dateFormat.format(myCalendarbirthChild.getTime()));
                        age.setText(getAge(year,month,day));
                    }
                };
                DatePickerDialog dialog = new DatePickerDialog(elderly2Activity.this, R.style.DialogTheme, date, myCalendarbirthChild.get(Calendar.YEAR), myCalendarbirthChild.get(Calendar.MONTH), myCalendarbirthChild.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });

        btnNextPage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDataToPro2();
            }
        });

        chkboxFather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(chkboxFather.isChecked()){
                    contFathers.setVisibility(View.GONE);
                    haveFather =0;
                }else{
                    contFathers.setVisibility(View.VISIBLE);
                    haveFather = 1;
                }
            }
        });


    }



    private void saveDataToPro2() {
        String childBeforeNameGet = listBeforeChild.get(spinBeforeNameChild.getSelectedItemPosition());
        String childNameGet = nameChild.getText().toString();
        String childLastnameGet = lastnameChild.getText().toString();
        String childIdCardGet = idCardChild.getText().toString();
        String childBirthGet = birthChild.getText().toString();

        String motherBeforeNameGet = listBeforeNameMother.get(spinBeforeNameMother.getSelectedItemPosition());
        String motherNameGet = motherName.getText().toString();
        String motherLastNameGet = motherLastName.getText().toString();
        String motherAgeGet = motherAge.getText().toString();
        String motherCitizenGet = listCitizenMother.get(spinMotherCitizen.getSelectedItemPosition());
        String motherWorkGet = listWorkMother.get(spinWorkMother.getSelectedItemPosition());
        String motherEduGet = listEduMother.get(spinEduMother.getSelectedItemPosition());
        String motherFillEduGet = fillEduMother.getText().toString();

        String fatherBeforeNameGet = listBeforeNameMother.get(spinBeforeNameMother.getSelectedItemPosition());
        String fatherNameGet = fatherName.getText().toString();
        String fatherLastNameGet = fatherLastName.getText().toString();
        String fatherAgeGet = fatherAge.getText().toString();
        String fatherCitizenGet = listCitizenMother.get(spinMotherCitizen.getSelectedItemPosition());
        String fatherWorkGet = listWorkMother.get(spinWorkMother.getSelectedItemPosition());
        String fatherEduGet = listEduMother.get(spinEduMother.getSelectedItemPosition());
        String fatherFillEduGet = fillEduMother.getText().toString();



        if(spinBeforeNameChild.getSelectedItemPosition()==0||childNameGet.isEmpty()||childLastnameGet.isEmpty()||childIdCardGet.isEmpty()||childBirthGet.isEmpty()){
            Toast.makeText(this, "กรุณากรอกข้อมูลเด็กให้ครบถ้วน", Toast.LENGTH_SHORT).show();
        }else if(spinBeforeNameMother.getSelectedItemPosition()==0||motherNameGet.isEmpty()||motherLastNameGet.isEmpty()||motherAgeGet.isEmpty()||
                spinMotherCitizen.getSelectedItemPosition()==0||spinWorkMother.getSelectedItemPosition()==0||spinEduMother.getSelectedItemPosition()==0
        ){
            Toast.makeText(this, "กรุณากรอกข้อมูลมารดาให้ครบถ้วน", Toast.LENGTH_SHORT).show();
        }else{
            if(haveFather==1){
                if(spinBeforeNameMother.getSelectedItemPosition()==0||fatherNameGet.isEmpty()||fatherLastNameGet.isEmpty()||fatherAgeGet.isEmpty()||
                        spinMotherCitizen.getSelectedItemPosition()==0||spinWorkMother.getSelectedItemPosition()==0||spinEduMother.getSelectedItemPosition()==0
                ){
                    Toast.makeText(this, "กรุณากรอกข้อมูลบิดาให้ครบถ้วน", Toast.LENGTH_SHORT).show();
                }else{
                    babyData.setChildBeforeName(childBeforeNameGet);
                    babyData.setChildName(childNameGet);
                    babyData.setChildLastname(childLastnameGet);
                    babyData.setChildIdCard(childIdCardGet);
                    babyData.setChildBirth(childBirthGet);

                    babyData.setMotherBeforeName(motherBeforeNameGet);
                    babyData.setMotherName(motherNameGet);
                    babyData.setMotherLastName(motherLastNameGet);
                    babyData.setMotherAge(motherAgeGet);
                    babyData.setMotherCitizen(motherCitizenGet);
                    babyData.setMotherWork(motherWorkGet);
                    babyData.setMotherEdu(motherEduGet);
                    babyData.setMotherFillEdu(motherFillEduGet);

                    babyData.setFatherBeforeName(fatherBeforeNameGet);
                    babyData.setFatherName(fatherNameGet);
                    babyData.setFatherLastName(fatherLastNameGet);
                    babyData.setFatherAge(fatherAgeGet);
                    babyData.setFatherCitizen(fatherCitizenGet);
                    babyData.setFatherWork(fatherWorkGet);
                    babyData.setFatherEdu(fatherEduGet);
                    babyData.setFatherFillEdu(fatherFillEduGet);
                    babyData.setFatherHave("yes");

                    setShowPage(3);
                }
            }else{
                babyData.setChildBeforeName(childBeforeNameGet);
                babyData.setChildName(childNameGet);
                babyData.setChildLastname(childLastnameGet);
                babyData.setChildIdCard(childIdCardGet);
                babyData.setChildBirth(childBirthGet);

                babyData.setMotherBeforeName(motherBeforeNameGet);
                babyData.setMotherName(motherNameGet);
                babyData.setMotherLastName(motherLastNameGet);
                babyData.setMotherAge(motherAgeGet);
                babyData.setMotherCitizen(motherCitizenGet);
                babyData.setMotherWork(motherWorkGet);
                babyData.setMotherEdu(motherEduGet);
                babyData.setMotherFillEdu(motherFillEduGet);
                babyData.setFatherHave("no");
                setShowPage(3);
            }
        }
    }

    private void setIdPage2() {
        spinBeforeNameChild  = findViewById(R.id.spinBeforeNameChild);
        nameChild = findViewById(R.id.nameChild);
        lastnameChild = findViewById(R.id.lastnameChild);
        idCardChild = findViewById(R.id.idCardChild);
        birthChild = findViewById(R.id.birthChild);

        spinBeforeNameMother = findViewById(R.id.spinBeforeNameMother);
        spinMotherCitizen = findViewById(R.id.spinMotherCitizen);
        spinWorkMother = findViewById(R.id.spinWorkMother);
        spinEduMother = findViewById(R.id.spinEduMother);
        motherName = findViewById(R.id.motherName);
        motherLastName = findViewById(R.id.motherLastName);
        motherAge = findViewById(R.id.motherAge);
        fillEduMother = findViewById(R.id.fillEduMother);

        spinBeforeNameFather = findViewById(R.id.spinBeforeNameFather);
        spinFatherCitizen = findViewById(R.id.spinFatherCitizen);
        spinWorkFather = findViewById(R.id.spinWorkFather);
        spinEduFather = findViewById(R.id.spinEduFather);
        fatherName = findViewById(R.id.fatherName);
        fatherLastName = findViewById(R.id.fatherLastName);
        fatherAge = findViewById(R.id.fatherAge);
        fillEduFather = findViewById(R.id.fillEduFather);
        btnNextPage3 = findViewById(R.id.btnNextPage3);
        chkboxFather = findViewById(R.id.chkboxFather);
        contFathers = findViewById(R.id.contFathers);
    }

    private void setSpinnerPage2() {
        listBeforeChild = new ArrayList<>();
        listBeforeChild.add("เลือกคำนำหน้าชื่อ");
        listBeforeChild.add("เด็กชาย");
        listBeforeChild.add("เด็กหญิง");
        SpinAdapter beforeNameChildAdapter = new SpinAdapter(this, listBeforeChild);
        spinBeforeNameChild.setAdapter(beforeNameChildAdapter);

        listBeforeNameMother = new ArrayList<>();
        listBeforeNameMother.add("เลือกคำนำหน้าชื่อ");
        listBeforeNameMother.add("เด็กหญิง");
        listBeforeNameMother.add("นาง");
        listBeforeNameMother.add("นางสาว");
        SpinAdapter beforeNameMotherAdapter = new SpinAdapter(this, listBeforeNameMother);
        spinBeforeNameMother.setAdapter(beforeNameMotherAdapter);

        listCitizenMother = new ArrayList<>();
        listCitizenMother.add("ระบุสัญชาติ");
        listCitizenMother.add("ไทย");
        SpinAdapter citiMotherAdapter = new SpinAdapter(this, listCitizenMother);
        spinMotherCitizen.setAdapter(citiMotherAdapter);

        listWorkMother = new ArrayList<>();
        listWorkMother.add("ระบุอาชีพ");
        listWorkMother.add("ไม่ได้ประกอบอาชีพ");
        listWorkMother.add("ประกอบวิชาชีพเฉพาะ");
        listWorkMother.add("ประกอบวิชาชีพอิสระ");
        listWorkMother.add("ผู้ประกอบการ/เจ้าของกิจการ");
        listWorkMother.add("ผู้รับจ้างทั่วไป/ผู้ใช้แรงงาน");
        listWorkMother.add("พนักงานและลูกจ้างในบริษัท");
        listWorkMother.add("พนักงานและลูกจ้างในสถาบันการเงิน");
        listWorkMother.add("พนักงานและลูกจ้างในหน่วยรัฐวิสาหกิจ");
        listWorkMother.add("อื่นๆ");
        SpinAdapter workMotherAdapter = new SpinAdapter(this, listWorkMother);
        spinWorkMother.setAdapter(workMotherAdapter);

        listEduMother = new ArrayList<>();
        listEduMother.add("ระบุการศึกษา");
        listEduMother.add("ไม่ได้รับการศึกษา");
        listEduMother.add("กำลังศึกษา");
        listEduMother.add("จบการศึกษา(สูงสุด)");

        SpinAdapter eduAdapter = new SpinAdapter(this, listEduMother);
        spinEduMother.setAdapter(eduAdapter);
        spinEduMother.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 2 || i == 3) {
                    fillEduMother.setVisibility(View.VISIBLE);
                }else{
                    fillEduMother.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ///// Father /////

        listBeforeNameFather = new ArrayList<>();
        listBeforeNameFather.add("เลือกคำนำหน้าชื่อ");
        listBeforeNameFather.add("เด็กชาย");
        listBeforeNameFather.add("นาย");
        SpinAdapter beforeNameFatherAdapter = new SpinAdapter(this, listBeforeNameFather);
        spinBeforeNameFather.setAdapter(beforeNameFatherAdapter);

        listCitizenFather = new ArrayList<>();
        listCitizenFather.add("ระบุสัญชาติ");
        listCitizenFather.add("ไทย");
        SpinAdapter citiFatherAdapter = new SpinAdapter(this, listCitizenFather);
        spinFatherCitizen.setAdapter(citiFatherAdapter);

        listWorkFather = new ArrayList<>();
        listWorkFather.add("ระบุอาชีพ");
        listWorkFather.add("ไม่ได้ประกอบอาชีพ");
        listWorkFather.add("ประกอบวิชาชีพเฉพาะ");
        listWorkFather.add("ประกอบวิชาชีพอิสระ");
        listWorkFather.add("ผู้ประกอบการ/เจ้าของกิจการ");
        listWorkFather.add("ผู้รับจ้างทั่วไป/ผู้ใช้แรงงาน");
        listWorkFather.add("พนักงานและลูกจ้างในบริษัท");
        listWorkFather.add("พนักงานและลูกจ้างในสถาบันการเงิน");
        listWorkFather.add("พนักงานและลูกจ้างในหน่วยรัฐวิสาหกิจ");
        listWorkFather.add("อื่นๆ");
        SpinAdapter workFatherAdapter = new SpinAdapter(this, listWorkFather);
        spinWorkFather.setAdapter(workFatherAdapter);

        listEduFather = new ArrayList<>();
        listEduFather.add("ระบุการศึกษา");
        listEduFather.add("ไม่ได้รับการศึกษา");
        listEduFather.add("กำลังศึกษา");
        listEduFather.add("จบการศึกษา(สูงสุด)");

        SpinAdapter eduAdapterFather = new SpinAdapter(this, listEduFather);
        spinEduFather.setAdapter(eduAdapterFather);
        spinEduFather.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 2 || i == 3) {
                    fillEduFather.setVisibility(View.VISIBLE);
                }else{
                    fillEduFather.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setIdPage1() {
        fillRelayBay = findViewById(R.id.fillRelayBay);
        spinRelayBaby = findViewById(R.id.spinRelayBaby);
        spinBeforeName = findViewById(R.id.spinBeforeName);
        spinCitizen = findViewById(R.id.spinCitizen);
        birth = findViewById(R.id.birth);
        age = findViewById(R.id.age);

        name = findViewById(R.id.name);
        lastName = findViewById(R.id.lastname);
        idCard = findViewById(R.id.idCard);
        homeNo = findViewById(R.id.homeNo);
        moo = findViewById(R.id.moo);
        soi = findViewById(R.id.soi);
        road = findViewById(R.id.road);
        province = findViewById(R.id.province);
        amphur = findViewById(R.id.amphur);
        district = findViewById(R.id.district);
        postCode = findViewById(R.id.postCode);
        phone = findViewById(R.id.phone);

        homeNo2 = findViewById(R.id.homeNo2);
        moo2 = findViewById(R.id.moo2);
        soi2 = findViewById(R.id.soi2);
        road2 = findViewById(R.id.road2);
        province2 = findViewById(R.id.province2);
        amphur2 = findViewById(R.id.amphur2);
        district2 = findViewById(R.id.district2);
        postCode2 = findViewById(R.id.postCode2);
        phone2 = findViewById(R.id.phone2);

        cbchk = findViewById(R.id.cbchk);
        spinWork = findViewById(R.id.spinWork);
        spinEdu = findViewById(R.id.spinEdu);
        fillEdu = findViewById(R.id.fillEdu);
        btnNextPage2 = findViewById(R.id.btnNextPage2);
    }

    private void setSpinnerPage1() {

        listRelayBaby = new ArrayList<>();
        listRelayBaby.add("เลือกความสัมพันธ์");
        listRelayBaby.add("บิดา");
        listRelayBaby.add("มารดา");
        listRelayBaby.add("ผู้ปกครอง");
        SpinAdapter relayBabyAdapter = new SpinAdapter(this, listRelayBaby);
        spinRelayBaby.setAdapter(relayBabyAdapter);
        spinRelayBaby.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 3) {
                    fillRelayBay.setVisibility(View.VISIBLE);
                } else {
                    fillRelayBay.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        listBeforeName = new ArrayList<>();
        listBeforeName.add("เลือกคำนำหน้าชื่อ");
        listBeforeName.add("นาย");
        listBeforeName.add("นาง");
        listBeforeName.add("นางสาว");
        SpinAdapter beforeNameAdapter = new SpinAdapter(this, listBeforeName);
        spinBeforeName.setAdapter(beforeNameAdapter);

        listCitizen = new ArrayList<>();
        listCitizen.add("ระบุสัญชาติ");
        listCitizen.add("ไทย");
        SpinAdapter citiAdapter = new SpinAdapter(this, listCitizen);
        spinCitizen.setAdapter(citiAdapter);
        getProvAll();
        getProvAll2();

        listWork = new ArrayList<>();
        listWork.add("ระบุอาชีพ");
        listWork.add("ไม่ได้ประกอบอาชีพ");
        listWork.add("ประกอบวิชาชีพเฉพาะ");
        listWork.add("ประกอบวิชาชีพอิสระ");
        listWork.add("ผู้ประกอบการ/เจ้าของกิจการ");
        listWork.add("ผู้รับจ้างทั่วไป/ผู้ใช้แรงงาน");
        listWork.add("พนักงานและลูกจ้างในบริษัท");
        listWork.add("พนักงานและลูกจ้างในสถาบันการเงิน");
        listWork.add("พนักงานและลูกจ้างในหน่วยรัฐวิสาหกิจ");
        listWork.add("อื่นๆ");
        SpinAdapter workAdapter = new SpinAdapter(this, listWork);
        spinWork.setAdapter(workAdapter);

        listEdu = new ArrayList<>();
        listEdu.add("ระบุการศึกษา");
        listEdu.add("ไม่ได้รับการศึกษา");
        listEdu.add("กำลังศึกษา");
        listEdu.add("จบการศึกษา(สูงสุด)");

        SpinAdapter eduAdapter = new SpinAdapter(this, listEdu);
        spinEdu.setAdapter(eduAdapter);
        spinEdu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 2 || i == 3) {
                    fillEdu.setVisibility(View.VISIBLE);
                }else if(i==1){
                    fillEdu.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void getProvAll() {
        AdressData adressData = new AdressData(this);
        ArrayList<ProvincesModel> provincesModels = adressData.getProvAll();
        provincesModelsList = provincesModels;
        ProvAdapter provAdapter = new ProvAdapter(elderly2Activity.this, provincesModels);
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
        AmphurAdapter amphurAdapter = new AmphurAdapter(elderly2Activity.this, amphuresModels);
        amphur.setAdapter(amphurAdapter);
        amphur.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                posAmphur = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void getProvAll2() {
        AdressData adressData = new AdressData(this);
        ArrayList<ProvincesModel> provincesModels = adressData.getProvAll();
        provincesModelsList2 = provincesModels;
        ProvAdapter provAdapter = new ProvAdapter(elderly2Activity.this, provincesModels);
        province2.setAdapter(provAdapter);
        province2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getAmphureByProvId2(provincesModels.get(i).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void getAmphureByProvId2(String provId) {
        AdressData adressData = new AdressData(this);
        ArrayList<AmphuresModel> amphuresModels = adressData.getAmpuhr(provId);
        amphuresModelsList2 = amphuresModels;
        AmphurAdapter amphurAdapter = new AmphurAdapter(elderly2Activity.this, amphuresModels);
        amphur2.setAdapter(amphurAdapter);
        if (cbchk.isChecked()) {
            amphur2.setSelection(posAmphur);
        }
    }

    private void setClickBtnPage1() {
        birth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, month);
                        myCalendar.set(Calendar.DAY_OF_MONTH, day);
                        String myFormat = "dd/MM/yyyy";
                        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
                        birth.setText(dateFormat.format(myCalendar.getTime()));
                    }
                };
                DatePickerDialog dialog = new DatePickerDialog(elderly2Activity.this, R.style.DialogTheme, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });

        cbchk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cbchk.isChecked()) {
                    setSendDataRepleate();
                } else {
                    homeNo2.setText("");
                    moo2.setText("");
                    soi2.setText("");
                    road2.setText("");
                    district2.setText("");
                    postCode2.setText("");
                    phone2.setText("");
                    province2.setSelection(0);
                    amphur2.setSelection(0);
                }
            }
        });

        btnNextPage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDataToPro1();
            }
        });
    }

    private void saveDataToPro1() {
        String nameGet = name.getText().toString();
        String lastNameGet = lastName.getText().toString();
        String idCardGet = idCard.getText().toString();
        String homeNoGet = homeNo.getText().toString();
        String mooGet = moo.getText().toString();
        String soiGet = soi.getText().toString();
        String roadGet = road.getText().toString();
        String districtGet = district.getText().toString();
        String postCodeGet = postCode.getText().toString();
        String phoneGet = phone.getText().toString();
        String provinceGet = provincesModelsList.get(province.getSelectedItemPosition()).getProvName();
        String amphurGet = amphuresModelsList.get(amphur.getSelectedItemPosition()).getAmpName();
        String ageGet = age.getText().toString();
        String birthGet = birth.getText().toString();
        String citizenGet = listCitizen.get(spinCitizen.getSelectedItemPosition());
        String workGet = listWork.get(spinWork.getSelectedItemPosition());
        String eduGet = listEdu.get(spinEdu.getSelectedItemPosition());
        String workFillGet = fillEdu.getText().toString();
        String homeNo2Get = homeNo2.getText().toString();
        String moo2Get = moo2.getText().toString();
        String soi2Get = soi2.getText().toString();
        String road2Get = road2.getText().toString();
        String province2Get = provincesModelsList2.get(province2.getSelectedItemPosition()).getProvName();
        String amphur2Get = amphuresModelsList2.get(amphur2.getSelectedItemPosition()).getAmpName();
        String district2Get = district2.getText().toString();
        String postCode2Get = postCode2.getText().toString();
        String phone2Get = phone2.getText().toString();
        String relayBabyGet = listRelayBaby.get(spinRelayBaby.getSelectedItemPosition());
        String beforeNameGet = listBeforeName.get(spinBeforeName.getSelectedItemPosition());
        if (spinRelayBaby.getSelectedItemPosition() == 0 || spinBeforeName.getSelectedItemPosition() == 0 || nameGet.isEmpty() || lastNameGet.isEmpty() ||
                idCardGet.isEmpty() || homeNoGet.isEmpty() || mooGet.isEmpty() || soiGet.isEmpty() || roadGet.isEmpty() || districtGet.isEmpty() ||
                postCodeGet.isEmpty() || phoneGet.isEmpty() || provinceGet.isEmpty() || amphurGet.isEmpty() || ageGet.isEmpty() || birthGet.isEmpty() || spinCitizen.getSelectedItemPosition() == 0 ||
                spinWork.getSelectedItemPosition() == 0 || spinEdu.getSelectedItemPosition() == 0 || homeNo2Get.isEmpty() || moo2Get.isEmpty() || soi2Get.isEmpty() || road2Get.isEmpty() ||
                province2.getSelectedItemPosition() == 0 || amphur2.getSelectedItemPosition() == 0 || district2Get.isEmpty() || postCode2Get.isEmpty() || phone2Get.isEmpty()
        ) {
            Toast.makeText(this, "กรุณากรอกข้อมูลให้ครบถ้วน", Toast.LENGTH_SHORT).show();
        } else {
            babyData.setAge(ageGet);
            babyData.setBirth(birthGet);
            babyData.setWorks(workGet);
            babyData.setEdu(eduGet);
            babyData.setEduFill(workFillGet);
            babyData.setRelayBaby(relayBabyGet);
            babyData.setBeforeName(beforeNameGet);
            babyData.setName(nameGet);
            babyData.setLastName(lastNameGet);
            babyData.setIdCard(idCardGet);
            babyData.setHomeNo(homeNoGet);
            babyData.setMoo(mooGet);
            babyData.setSoi(soiGet);
            babyData.setRoad(roadGet);
            babyData.setProvince(provinceGet);
            babyData.setAmphur(amphurGet);
            babyData.setDistrict(districtGet);
            babyData.setPostCode(postCodeGet);
            babyData.setPhone(phoneGet);
            babyData.setCitizen(citizenGet);
            babyData.setHomeNo2(homeNo2Get);
            babyData.setMoo2(moo2Get);
            babyData.setSoi2(soi2Get);
            babyData.setRoad2(road2Get);
            babyData.setProvince2(province2Get);
            babyData.setAmphur2(amphur2Get);
            babyData.setDistrict2(district2Get);
            babyData.setPostCode2(postCode2Get);
            babyData.setPhone2(phone2Get);
            setShowPage(2);
        }
    }

    private void setShowPage(int i) {
        pageCur = i;
        if (pageCur == 1) {
            page1.setVisibility(View.VISIBLE);
            page2.setVisibility(View.GONE);
            page3.setVisibility(View.GONE);
            processPage1.setVisibility(View.VISIBLE);
            processPage2.setVisibility(View.GONE);
            processPage3.setVisibility(View.GONE);
        } else if (pageCur == 2) {
            page1.setVisibility(View.GONE);
            page2.setVisibility(View.VISIBLE);
            page3.setVisibility(View.GONE);
            processPage1.setVisibility(View.GONE);
            processPage2.setVisibility(View.VISIBLE);
            processPage3.setVisibility(View.GONE);
        } else if (pageCur == 3) {
            page1.setVisibility(View.GONE);
            page2.setVisibility(View.GONE);
            page3.setVisibility(View.VISIBLE);
            processPage1.setVisibility(View.GONE);
            processPage2.setVisibility(View.GONE);
            processPage3.setVisibility(View.VISIBLE);
        }
    }

    private void setSendDataRepleate() {
        String homeNoGet = homeNo.getText().toString();
        String mooGet = moo.getText().toString();
        String soiGet = soi.getText().toString();
        String roadGet = road.getText().toString();
        String districtGet = district.getText().toString();
        String postCodeGet = postCode.getText().toString();
        String phoneGet = phone.getText().toString();
        String provinceGet = provincesModelsList.get(province.getSelectedItemPosition()).getProvName();
        String amphurGet = amphuresModelsList.get(amphur.getSelectedItemPosition()).getAmpName();
        if (homeNoGet.isEmpty() || mooGet.isEmpty() || soiGet.isEmpty() || roadGet.isEmpty() ||
                districtGet.isEmpty() || postCodeGet.isEmpty() || phoneGet.isEmpty() || provinceGet.isEmpty() || amphurGet.isEmpty()
        ) {
            Toast.makeText(this, "กรุณากรอกข้อมูลให้ครบถ้วน", Toast.LENGTH_SHORT).show();
        } else {
            homeNo2.setText(homeNoGet);
            moo2.setText(mooGet);
            soi2.setText(soiGet);
            road2.setText(roadGet);
            district2.setText(districtGet);
            postCode2.setText(postCodeGet);
            phone2.setText(phoneGet);
            province2.setSelection(province.getSelectedItemPosition());
        }
    }


}