package com.app.bandnara;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
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
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.ValueCallback;
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
import com.app.bandnara.ToolBar.CloseBar;
import com.app.bandnara.adaptor.AmphurAdapter;
import com.app.bandnara.adaptor.ProvAdapter;
import com.app.bandnara.adaptor.SexAdapter;
import com.app.bandnara.adaptor.SpinAdapter;
import com.app.bandnara.keepFireStory.OlderData;
import com.app.bandnara.models.AmphuresModel;
import com.app.bandnara.models.ProvincesModel;
import com.app.bandnara.tools.AdressData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.installations.Utils;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class elderlyActivity extends AppCompatActivity {
    private FrameLayout bottomMenu;// ตัวแปรปุ่มล่าง
    private LinearLayout back; // ปุ่มกลับ
    private int pageCur = 1;
    final Calendar myCalendar = Calendar.getInstance(); //ปฏิทิน
    private LinearLayout page1, page2, page3;
    private LinearLayout processPage1, processPage2, processPage3;
    // Start ตัวแปร Page 1
    private RelativeLayout setType1, setType2;
    private int typeRegis = 2;
    private ImageView imgType2, imgType1;
    private LinearLayout contInPage1;
    private Spinner spinRelay, spinBeforeName, province, amphur;
    private List<String> listRelay = new ArrayList<>();
    private List<String> listBeforeName = new ArrayList<>();
    private ArrayList<ProvincesModel> provincesModelsList = new ArrayList<>();
    private ArrayList<AmphuresModel> amphuresModelsList = new ArrayList<>();

    private ArrayList<ProvincesModel> provincesModelsList2 = new ArrayList<>();
    private ArrayList<AmphuresModel> amphuresModelsList2 = new ArrayList<>();

    private TextView btnSeeEx;
    private ImageView showPicUpload;
    private AppCompatButton btnNextPage1;
    private LinearLayout btnChoosePic, contUpload;
    private EditText name, lastName, idCard, homeNo, moo, soi, road, district, postCode, phone;
    private Uri outputFileUri;
    private int statusChooseImg = 0;
    private Uri imageChooseCur;
    // End ตัวแปร Page 1

    // Start ตัวแปร Page 2
    private Spinner spinBeforeName2, spinCitizen, spinStanapap, spinWork;
    private List<String> listCitizen = new ArrayList<>();
    private List<String> listStanapap = new ArrayList<>();
    private List<String> listWork = new ArrayList<>();
    private Spinner province2, amphur2;
    private LinearLayout chooseCopyCard, contShowCopyCard,chooseCopyHome,contShowCopyHome;
    private ImageView showPicCopyCard,showImageCopyHome;
    private int statusChooseCopyCard = 0;
    private Uri imageCopyCard;
    private int statusChooseCopyHome = 0;
    private Uri imageCopyHome;
    private AppCompatButton btnNextPage2;
    private EditText olderName,olderLastName,olderYear,olderIdCard,olderProfit,homeNo2,moo2,soi2,road2,district2,postCode2,phone2;
    private TextView olderBirth;
    // End ตัวแปร Page 2

    // Start ตัวแปร Page 3
    private int iPosCb1=4;
    private int iPosCb2=2;
    private List<LinearLayout> cbSt = new ArrayList<LinearLayout>(Arrays.asList(new LinearLayout[iPosCb1]));
    private List<ImageView> imgCbSt = new ArrayList<ImageView>(Arrays.asList(new ImageView[iPosCb1]));
    private List<LinearLayout> cbBc = new ArrayList<LinearLayout>(Arrays.asList(new LinearLayout[iPosCb2]));
    private List<ImageView> imgCbBc = new ArrayList<ImageView>(Arrays.asList(new ImageView[iPosCb2]));
    private int chooseCbSt=1;
    private int chooseCbBc=1;
    private int statusChooseBank= 0;
    private Uri imageCopyBank;
    private LinearLayout chooseCopyBank,contShowCopyBank;
    private ImageView showImgBank;
    private CheckBox cbAllow;
    private boolean statusAllow = false;
    private EditText bankNo,bankName,bankOwner;
    private AppCompatButton saveFinal;
    // End ตัวแปร Page 3

    OlderData olderData = new OlderData();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageReference = storage.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elderly);
        CloseBar closeBar = new CloseBar(this);

        back = findViewById(R.id.back);
        page1 = findViewById(R.id.page1);
        page2 = findViewById(R.id.page2);
        page3 = findViewById(R.id.page3);
        processPage1 = findViewById(R.id.processPage1);
        processPage2 = findViewById(R.id.processPage2);
        processPage3 = findViewById(R.id.processPage3);
        // เซ็ตการทำงานปุ่มเมนูล่าง
        bottomMenu = (FrameLayout) findViewById(R.id.bottomMenu);
        BottomBar bottomBar = new BottomBar(getApplicationContext(), bottomMenu);

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

    }

    private void setSaveFinal() {
        int cb1Get = chooseCbSt;
        int cb2Get = chooseCbBc;
        String bankNoGet = bankNo.getText().toString();
        String bankNameGet = bankName.getText().toString();
        String bankOwnerGet = bankOwner.getText().toString();
        boolean statusAllowGet = statusAllow;
        if(!statusAllowGet){
            Toast.makeText(this, "กรุณากดยอมรับข้อตกลง", Toast.LENGTH_SHORT).show();
        }else if(bankNoGet.isEmpty()||bankNameGet.isEmpty()||bankOwnerGet.isEmpty()){
            Toast.makeText(this, "กรุณากรอกข้อมูลให้ครบถ้วน", Toast.LENGTH_SHORT).show();
        }else if(statusChooseBank==0){
            Toast.makeText(this, "กรุณาอัพโหลดหน้าสมุดบัญชี", Toast.LENGTH_SHORT).show();
        }else{
            olderData.setCbSt(cb1Get);
            olderData.setCbBc(cb2Get);
            olderData.setBankNo(bankNoGet);
            olderData.setBankName(bankNameGet);
            olderData.setBankOwner(bankOwnerGet);
            olderData.setDateCreate(FieldValue.serverTimestamp());
            olderData.setDateModify(FieldValue.serverTimestamp());
            olderData.setStatus("wait");
            olderData.setUserId(MyApplication.getUserId());
            db.collection("olders_data")
                    .add(olderData)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d("CHKDB", "DocumentSnapshot written with ID: " + documentReference.getId());
                            setUploadImage(documentReference.getId());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("CHKDB", "Error adding document", e);
                        }
                    });
        }
    }

    private void setUploadImage(String idKeyReport) {
        StorageReference ref = storageReference.child("imgolder/older_"+idKeyReport+"/bookpic_" + idKeyReport);
        ref.putFile(imageChooseCur)
                .addOnSuccessListener(
                        new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                StorageReference ref2 = storageReference.child("imgolder/older_"+idKeyReport+"/idcard_" + idKeyReport);
                                ref2.putFile(imageCopyCard)
                                        .addOnSuccessListener(
                                                new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                    @Override
                                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                        StorageReference ref3 = storageReference.child("imgolder/older_"+idKeyReport+"/home_" + idKeyReport);
                                                        ref3.putFile(imageCopyHome)
                                                                .addOnSuccessListener(
                                                                        new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                                            @Override
                                                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                                                StorageReference ref4 = storageReference.child("imgolder/older_"+idKeyReport+"/bank_" + idKeyReport);
                                                                                ref4.putFile(imageCopyBank)
                                                                                        .addOnSuccessListener(
                                                                                                new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                                                                    @Override
                                                                                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                                                                        Toast.makeText(elderlyActivity.this, "บันทึกข้อมูลเรียบร้อย", Toast.LENGTH_SHORT).show();
                                                                                                    }
                                                                                                })
                                                                                        .addOnFailureListener(new OnFailureListener() {
                                                                                            @Override
                                                                                            public void onFailure(@NonNull Exception e) {
                                                                                                Toast.makeText(elderlyActivity.this, "ไม่สามารถอัพโหลดไฟล์ 2 ได้", Toast.LENGTH_SHORT).show();
                                                                                            }
                                                                                        });
                                                                            }
                                                                        })
                                                                .addOnFailureListener(new OnFailureListener() {
                                                                    @Override
                                                                    public void onFailure(@NonNull Exception e) {
                                                                        Toast.makeText(elderlyActivity.this, "ไม่สามารถอัพโหลดไฟล์ 2 ได้", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                });
                                                    }
                                                })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(elderlyActivity.this, "ไม่สามารถอัพโหลดไฟล์ 2 ได้", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(elderlyActivity.this, "ไม่สามารถอัพโหลดไฟล์ 1 ได้", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void setClickBtnPage3() {
        saveFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSaveFinal();
            }
        });
        cbAllow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cbAllow.isChecked()){
                    statusAllow = true;
                }else{
                    statusAllow = false;
                }
            }
        });
        chooseCopyBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                startActivityForResult(chooserIntent, 4);
            }
        });
    }


    public void setShowCbSt(int pos){
        for (int i = 0; i < iPosCb1; i++) {
            if((pos)==(i)){
                imgCbSt.get(i).setVisibility(View.VISIBLE);
            }else{
                imgCbSt.get(i).setVisibility(View.GONE);
            }
        }
    }

    public void setShowCbBc(int pos){
        for (int i = 0; i < iPosCb2; i++) {
            if((pos)==(i)){
                imgCbBc.get(i).setVisibility(View.VISIBLE);
            }else{
                imgCbBc.get(i).setVisibility(View.GONE);
            }
        }
    }

    private void setIdPage3() {
        chooseCopyBank = findViewById(R.id.chooseCopyBank);
        showImgBank = findViewById(R.id.showImgBank);
        contShowCopyBank = findViewById(R.id.contShowCopyBank);
        cbAllow = findViewById(R.id.cbAllow);
        bankNo = findViewById(R.id.bankNo);
        bankName = findViewById(R.id.bankName);
        bankOwner = findViewById(R.id.bankOwner);
        saveFinal = findViewById(R.id.saveFinal);
        for (int i = 0; i < iPosCb1; i++) {
            String getId = "cbst" + (i + 1);
            cbSt.set(i, findViewById(getResources().getIdentifier(getId, "id", getPackageName())));
            int finalI = i;
            cbSt.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    chooseCbSt = finalI +1;
                    setShowCbSt(finalI);
                }
            });
        }

        for (int i = 0; i < iPosCb1; i++) {
            String getId = "imgCbSt" + (i + 1);
            imgCbSt.set(i, findViewById(getResources().getIdentifier(getId, "id", getPackageName())));
        }

        for (int i = 0; i < iPosCb2; i++) {
            String getId = "cbBc" + (i + 1);
            cbBc.set(i, findViewById(getResources().getIdentifier(getId, "id", getPackageName())));
            int finalI = i;
            cbBc.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    chooseCbBc = finalI +1;
                    setShowCbBc(finalI);
                }
            });
        }

        for (int i = 0; i < iPosCb2; i++) {
            String getId = "imgCbBc" + (i + 1);
            imgCbBc.set(i, findViewById(getResources().getIdentifier(getId, "id", getPackageName())));
        }
    }

    private void setIdPage2() {
        spinBeforeName2 = findViewById(R.id.spinBeforeName2);
        spinCitizen = findViewById(R.id.spinCitizen);
        spinStanapap = findViewById(R.id.spinStanapap);
        spinWork = findViewById(R.id.spinWork);
        province2 = findViewById(R.id.province2);
        amphur2 = findViewById(R.id.amphur2);
        chooseCopyCard = findViewById(R.id.chooseCopyCard);
        contShowCopyCard = findViewById(R.id.contShowCopyCard);
        showPicCopyCard = findViewById(R.id.showPicCopyCard);
        chooseCopyHome = findViewById(R.id.chooseCopyHome);
        contShowCopyHome = findViewById(R.id.contShowCopyHome);
        showImageCopyHome = findViewById(R.id.showImageCopyHome);
        btnNextPage2 = findViewById(R.id.btnNextPage2);

        olderBirth= findViewById(R.id.olderBirth);
        olderName = findViewById(R.id.olderName);
        olderLastName = findViewById(R.id.olderLastName);
        olderIdCard = findViewById(R.id.olderIdCard);
        olderYear = findViewById(R.id.olderYear);
        olderProfit = findViewById(R.id.olderProfit);
        homeNo2 = findViewById(R.id.homeNo2);
        moo2 = findViewById(R.id.moo2);
        soi2 = findViewById(R.id.soi2);
        road2 = findViewById(R.id.road2);
        district2 = findViewById(R.id.district2);
        postCode2 = findViewById(R.id.postCode2);
        phone2 = findViewById(R.id.phone2);
    }

    private void setClickBtnPage2() {
        olderBirth.setOnClickListener(new View.OnClickListener() {
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
                        olderBirth.setText(dateFormat.format(myCalendar.getTime()));
                    }
                };
                DatePickerDialog dialog = new DatePickerDialog(elderlyActivity.this, R.style.DialogTheme, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });
        chooseCopyCard.setOnClickListener(new View.OnClickListener() {
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
                final Intent chooserIntent = Intent.createChooser(galleryIntent, "เลือกรูปภาพ");
                // Add the camera options.
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[]{}));
                startActivityForResult(chooserIntent, 2);
            }
        });
        chooseCopyHome.setOnClickListener(new View.OnClickListener() {
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
                final Intent chooserIntent = Intent.createChooser(galleryIntent, "เลือกรูปภาพ");
                // Add the camera options.
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[]{}));
                startActivityForResult(chooserIntent, 3);
            }
        });

        btnNextPage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String olderBeforeNameGet = listBeforeName.get(spinBeforeName2.getSelectedItemPosition());
                String olderNameGet = olderName.getText().toString();
                String olderLastNameGet = olderLastName.getText().toString();
                String olderBirthGet = olderBirth.getText().toString();
                String olderYearGet = olderYear.getText().toString();
                String citizenget = listCitizen.get(spinCitizen.getSelectedItemPosition());
                String olderIdCardGet = olderIdCard.getText().toString();
                String stanapapGet = listStanapap.get(spinStanapap.getSelectedItemPosition());
                String olderProfitGet = olderProfit.getText().toString();
                String workGet = listWork.get(spinWork.getSelectedItemPosition());
                String homeNo2Get = homeNo2.getText().toString();
                String moo2Get = moo2.getText().toString();
                String soi2Get = soi2.getText().toString();
                String road2Get = road2.getText().toString();
                String province2Get = provincesModelsList2.get(province2.getSelectedItemPosition()).getProvName();
                String amphur2Get = amphuresModelsList2.get(amphur2.getSelectedItemPosition()).getAmpName();
                String district2Get = district2.getText().toString();
                String postCode2Get = postCode2.getText().toString();
                String phone2Get = phone2.getText().toString();
                if(spinBeforeName2.getSelectedItemPosition()==0||olderNameGet.isEmpty()||olderLastNameGet.isEmpty()||olderBirthGet.isEmpty()||olderYearGet.isEmpty()||
                        spinCitizen.getSelectedItemPosition()==0||olderIdCardGet.isEmpty()||spinStanapap.getSelectedItemPosition()==0||olderProfitGet.isEmpty()||
                        spinWork.getSelectedItemPosition()==0||homeNo2Get.isEmpty()||moo2Get.isEmpty()||soi2Get.isEmpty()||road2Get.isEmpty()||
                        province2.getSelectedItemPosition()==0||amphur2.getSelectedItemPosition()==0||district2Get.isEmpty()||postCode2Get.isEmpty()||phone2Get.isEmpty()
                ){
                    Toast.makeText(elderlyActivity.this, "กรุณากรอกข้อมูลให้ครบ", Toast.LENGTH_SHORT).show();
                }else if(statusChooseCopyCard==0){
                    Toast.makeText(elderlyActivity.this, "กรุณาอัพโหลดสำเนาบัตรประชาชน", Toast.LENGTH_SHORT).show();
                }else if(statusChooseCopyHome==0){
                    Toast.makeText(elderlyActivity.this, "กรุณาอัพโหลดสำเนาทะเบียนบ้าน", Toast.LENGTH_SHORT).show();
                }else {
                    olderData.setBeforeName2(olderBeforeNameGet);
                    olderData.setOlderName(olderNameGet);
                    olderData.setOlderLastName(olderLastNameGet);
                    olderData.setOlderBirth(olderBirthGet);
                    olderData.setOlderYear(olderYearGet);
                    olderData.setCitizen(citizenget);
                    olderData.setOlderIdCard(olderIdCardGet);
                    olderData.setStanapap(stanapapGet);
                    olderData.setOlderProfit(olderProfitGet);
                    olderData.setWork(workGet);
                    olderData.setHomeNo2(homeNo2Get);
                    olderData.setMoo2(moo2Get);
                    olderData.setSoi2(soi2Get);
                    olderData.setRoad2(road2Get);
                    olderData.setProvince2(province2Get);
                    olderData.setAmphur2(amphur2Get);
                    olderData.setDistrict2(district2Get);
                    olderData.setPostCode2(postCode2Get);
                    olderData.setPhone2(phone2Get);
                    setShowPage(3);
                }
            }
        });
    }

    private void setSpinnerPage2() {
        listBeforeName = new ArrayList<>();
        listBeforeName.add("เลือคำนำหน้าชื่อ");
        listBeforeName.add("นาย");
        listBeforeName.add("นาง");
        listBeforeName.add("นางสาว");
        SpinAdapter beforeNameAdapter = new SpinAdapter(this, listBeforeName);
        spinBeforeName2.setAdapter(beforeNameAdapter);

        listCitizen = new ArrayList<>();
        listCitizen.add("ระบุสัญชาติ");
        listCitizen.add("ไทย");
        SpinAdapter citiAdapter = new SpinAdapter(this, listCitizen);
        spinCitizen.setAdapter(citiAdapter);

        listStanapap = new ArrayList<>();
        listStanapap.add("เลือกสถานะภาพสมรส");
        listStanapap.add("โสด");
        listStanapap.add("สมรส");
        listStanapap.add("หม้าย");
        listStanapap.add("หย่าร้าง");
        listStanapap.add("แยกกันอยู่");
        SpinAdapter stanapapAdapter = new SpinAdapter(this, listStanapap);
        spinStanapap.setAdapter(stanapapAdapter);

        listWork = new ArrayList<>();
        listWork.add("ระบุอาชีพ");
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

        getProvAllPage2();
    }


    private void setSpinnerPage1() {
        listRelay = new ArrayList<>();
        listRelay.add("เลือกความสัมพันธ์");
        listRelay.add("บุตร");
        listRelay.add("บุตรหลาน");
        listRelay.add("ตู่สมรส");
        listRelay.add("น้องชาย");
        SpinAdapter relayAdapter = new SpinAdapter(this, listRelay);
        spinRelay.setAdapter(relayAdapter);

        listBeforeName = new ArrayList<>();
        listBeforeName.add("เลือคำนำหน้าชื่อ");
        listBeforeName.add("นาย");
        listBeforeName.add("นาง");
        listBeforeName.add("นางสาว");
        SpinAdapter beforeNameAdapter = new SpinAdapter(this, listBeforeName);
        spinBeforeName.setAdapter(beforeNameAdapter);
        getProvAll();

    }

    public void getProvAllPage2() {
        AdressData adressData = new AdressData(this);
        ArrayList<ProvincesModel> provincesModels = adressData.getProvAll();
        provincesModelsList2 = provincesModels;
        ProvAdapter provAdapter = new ProvAdapter(elderlyActivity.this, provincesModels);
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
        AmphurAdapter amphurAdapter = new AmphurAdapter(elderlyActivity.this, amphuresModels);
        amphur2.setAdapter(amphurAdapter);
        amphur2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    private void setSavePage1() {
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
        String ralayGet = listRelay.get(spinRelay.getSelectedItemPosition());
        String beforeNameGet = listBeforeName.get(spinBeforeName.getSelectedItemPosition());
        if (nameGet.isEmpty() || lastNameGet.isEmpty() || idCardGet.isEmpty() || homeNoGet.isEmpty() || mooGet.isEmpty() || soiGet.isEmpty() || roadGet.isEmpty() ||
                districtGet.isEmpty() || postCodeGet.isEmpty() || phoneGet.isEmpty() || provinceGet.isEmpty() || amphurGet.isEmpty() ||
                spinRelay.getSelectedItemPosition() == 0 || spinBeforeName.getSelectedItemPosition() == 0
        ) {
            Toast.makeText(this, "กรุณากรอกข้อมูลให้ครบถ้วน", Toast.LENGTH_SHORT).show();
        } else if (statusChooseImg == 0) {
            Toast.makeText(this, "กรุณาเลือกรูปภาพ", Toast.LENGTH_SHORT).show();
        } else {
            olderData.setTypeReport(typeRegis);
            olderData.setRelay(ralayGet);
            olderData.setBeforeName(beforeNameGet);
            olderData.setName(nameGet);
            olderData.setLastName(lastNameGet);
            olderData.setIdCard(idCardGet);
            olderData.setHomeNo(homeNoGet);
            olderData.setMoo(mooGet);
            olderData.setSoi(soiGet);
            olderData.setRoad(roadGet);
            olderData.setProvince(provinceGet);
            olderData.setAmphur(amphurGet);
            olderData.setDistrict(districtGet);
            olderData.setPostCode(postCodeGet);
            olderData.setPhone(phoneGet);
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

    public void setIdPage1() {
        setType1 = findViewById(R.id.setType1);
        setType2 = findViewById(R.id.setType2);
        imgType1 = findViewById(R.id.imgType1);
        imgType2 = findViewById(R.id.imgType2);
        contInPage1 = findViewById(R.id.contInPage1);
        spinRelay = findViewById(R.id.spinRelay);
        spinBeforeName = findViewById(R.id.spinBeforeName);
        btnSeeEx = findViewById(R.id.btnSeeEx);
        province = findViewById(R.id.province);
        amphur = findViewById(R.id.amphur);
        btnNextPage1 = findViewById(R.id.btnNextPage1);
        btnChoosePic = findViewById(R.id.btnChoosePic);
        contUpload = findViewById(R.id.contUpload);
        showPicUpload = findViewById(R.id.showPicUpload);

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
    }

    public void getProvAll() {
        AdressData adressData = new AdressData(this);
        ArrayList<ProvincesModel> provincesModels = adressData.getProvAll();
        provincesModelsList = provincesModels;
        ProvAdapter provAdapter = new ProvAdapter(elderlyActivity.this, provincesModels);
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
        AmphurAdapter amphurAdapter = new AmphurAdapter(elderlyActivity.this, amphuresModels);
        amphur.setAdapter(amphurAdapter);
        amphur.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void setClickBtnPage1() {
        setType1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                typeRegis = 1;
                imgType1.setVisibility(View.VISIBLE);
                imgType2.setVisibility(View.GONE);
                contInPage1.setVisibility(View.GONE);
            }
        });
        setType2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                typeRegis = 2;
                imgType1.setVisibility(View.GONE);
                imgType2.setVisibility(View.VISIBLE);
                contInPage1.setVisibility(View.VISIBLE);
            }
        });
        btnSeeEx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogEx();
            }
        });
        btnNextPage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (typeRegis == 1) {
                    setSavePage1FromDb();
                } else {
                    setSavePage1();
                }
            }
        });
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
                final Intent chooserIntent = Intent.createChooser(galleryIntent, "เลือกรูปภาพ");
                // Add the camera options.
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[]{}));
                startActivityForResult(chooserIntent, 1);

            }
        });
    }

    private void setSavePage1FromDb() {
        DocumentReference docRef = db.collection("users").document(MyApplication.getUserId());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    olderData.setTypeReport(typeRegis);
                    olderData.setRelay("");
                    olderData.setBeforeName("");
                    olderData.setName(document.getData().get("name").toString());
                    olderData.setLastName(document.getData().get("lastname").toString());
                    olderData.setIdCard("");
                    olderData.setHomeNo(document.getData().get("numberass").toString());
                    olderData.setMoo(document.getData().get("mu").toString());
                    olderData.setSoi("");
                    olderData.setRoad(document.getData().get("road").toString());
                    olderData.setProvince(document.getData().get("province").toString());
                    olderData.setAmphur(document.getData().get("district").toString());
                    olderData.setDistrict(document.getData().get("tambon").toString());
                    olderData.setPostCode(document.getData().get("numberpri").toString());
                    olderData.setPhone(document.getData().get("pim01").toString());
                    setShowPage(2);
                } else {
                    Log.d("CHKDB", "get failed with ", task.getException());
                }
            }
        });
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
            } else if (requestCode == 2) {
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
                    contShowCopyCard.setVisibility(View.GONE);
                    showPicCopyCard.setVisibility(View.VISIBLE);
                    showPicCopyCard.setImageURI(selectedImageUri);
                    imageCopyCard = selectedImageUri;
                    statusChooseCopyCard = 1;
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
                        contShowCopyCard.setVisibility(View.GONE);
                        showPicCopyCard.setVisibility(View.VISIBLE);
                        showPicCopyCard.setImageURI(selectedImageUri);
                        imageCopyCard = selectedImageUri;
                        statusChooseCopyCard = 1;
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }else if (requestCode == 3) {
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
                    contShowCopyHome.setVisibility(View.GONE);
                    showImageCopyHome.setVisibility(View.VISIBLE);
                    showImageCopyHome.setImageURI(selectedImageUri);
                    imageCopyHome = selectedImageUri;
                    statusChooseCopyHome = 1;
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
                        contShowCopyHome.setVisibility(View.GONE);
                        showImageCopyHome.setVisibility(View.VISIBLE);
                        showImageCopyHome.setImageURI(selectedImageUri);
                        imageCopyHome = selectedImageUri;
                        statusChooseCopyHome = 1;
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }else if (requestCode == 4) {
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
                    contShowCopyBank.setVisibility(View.GONE);
                    showImgBank.setVisibility(View.VISIBLE);
                    showImgBank.setImageURI(selectedImageUri);
                    imageCopyBank = selectedImageUri;
                    statusChooseBank = 1;
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
                        contShowCopyBank.setVisibility(View.GONE);
                        showImgBank.setVisibility(View.VISIBLE);
                        showImgBank.setImageURI(selectedImageUri);
                        imageCopyBank = selectedImageUri;
                        statusChooseBank = 1;
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else if (resultCode == RESULT_CANCELED) {

        } else {

        }
    }

    public void openDialogEx() {
        final Dialog dialog = new Dialog(elderlyActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_hightold);
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


}