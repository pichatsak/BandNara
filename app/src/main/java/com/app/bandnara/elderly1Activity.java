
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
import com.app.bandnara.ToolBar.CloseBar;
import com.app.bandnara.adaptor.AmphurAdapter;
import com.app.bandnara.adaptor.ProvAdapter;
import com.app.bandnara.adaptor.SpinAdapter;
import com.app.bandnara.keepFireStory.DeformData;
import com.app.bandnara.keepFireStory.OlderData;
import com.app.bandnara.models.AmphuresModel;
import com.app.bandnara.models.NotiWebModel;
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

public class elderly1Activity extends AppCompatActivity {
    private FrameLayout bottomMenu;// ตัวแปรปุ่มล่าง
    private LinearLayout back; // ปุ่มกลับ
    private int pageCur = 1;
    final Calendar myCalendar = Calendar.getInstance(); //ปฏิทิน
    private LinearLayout page1, page2, page3,page4;
    private LinearLayout processPage1, processPage2, processPage3,processPage4;
    // Start ตัวแปร Page 1
    private RelativeLayout setType1, setType2;
    private int typeRegis = 2;
    private ImageView imgType2, imgType1;
    private LinearLayout contInPage1;
    private Spinner spinRelay, spinBeforeName, province, amphur,spinDeform;
    private List<String> listRelay = new ArrayList<>();
    private List<String> listBeforeName = new ArrayList<>();
    private List<String> listDeform = new ArrayList<>();
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
    private LinearLayout chooseCopyDeformIdCard,contShowCopyDeformIdCard;
    private ImageView showPicCopyCard,showImageCopyHome,showImageCopyDeformIdCard;
    private int statusChooseCopyCard = 0;
    private Uri imageCopyCard;
    private int statusChooseCopyHome = 0;
    private Uri imageCopyHome;
    private int statusChooseCopyDeformIdCard = 0;
    private Uri imageCopyDeformIdCard ;
    private AppCompatButton btnNextPage2;
    private EditText olderName,olderLastName,olderYear,olderIdCard,olderProfit,homeNo2,moo2,soi2,road2,district2,postCode2,phone2;
    private TextView olderBirth;
    // End ตัวแปร Page 2

    // Start ตัวแปร Page 3
    private Spinner spinBeforeName3;
    private EditText name3,lastname3,phone3;
    private List<String> listBeforeName3 = new ArrayList<>();
    private AppCompatButton btnNextPage3;
    // End ตัวแปร Page 3


    // Start ตัวแปร Page 4
    int iPosCb1 =4;
    int iPosCb2 =2;
    private List<RelativeLayout> btnCbOne = new ArrayList<RelativeLayout>(Arrays.asList(new RelativeLayout[iPosCb1]));
    private List<ImageView> imgCbOne = new ArrayList<ImageView>(Arrays.asList(new ImageView[iPosCb1]));
    private List<RelativeLayout> btnCbTwo = new ArrayList<RelativeLayout>(Arrays.asList(new RelativeLayout[iPosCb2]));
    private List<ImageView> imgCbTwo = new ArrayList<ImageView>(Arrays.asList(new ImageView[iPosCb2]));
    private int chooseCbOne =1;
    private int chooseCbTwo =1;
    private CheckBox cbAllow;
    private int statusChooseBank= 0;
    private Uri imageCopyBank;
    private LinearLayout chooseCopyBank,contShowCopyBank;
    private ImageView showImgBank;
    private boolean statusAllow = false;
    private EditText bankNo,bankName,bankOwner;
    private AppCompatButton saveFinal;
    // End ตัวแปร Page 4

    DeformData deformData = new DeformData();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageReference = storage.getReference();
    private int IMG_POS_CUR=0;
    TextView btnSeeExCopyCard,btnSeeExCopyBank;
    ProgressDialog dialogLoad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elderly1);
        CloseBar closeBar = new CloseBar(this);

        back = findViewById(R.id.back);
        page1 = findViewById(R.id.page1);
        page2 = findViewById(R.id.page2);
        page3 = findViewById(R.id.page3);
        page4 = findViewById(R.id.page4);
        processPage1 = findViewById(R.id.processPage1);
        processPage2 = findViewById(R.id.processPage2);
        processPage3 = findViewById(R.id.processPage3);
        processPage4 = findViewById(R.id.processPage4);
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
        setSpinnerPage3();
        setClickBtnPage3();
        // End method Page 3

        // Start method Page 4
        setIdPage4();
        setClickBtnPage4();
        // End method Page 4

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
                } else if (pageCur == 4) {
                    setShowPage(3);
                }
            }
        });

        btnSeeExCopyCard = findViewById(R.id.btnSeeExCopyCard);
        btnSeeExCopyBank = findViewById(R.id.btnSeeExCopyBank);
        setDialogEx();
    }

    private void setDialogEx() {
        btnSeeExCopyCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(elderly1Activity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.dialog_ex_card);
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
        btnSeeExCopyBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(elderly1Activity.this);
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

    private void setIdPage4() {
        chooseCopyBank = findViewById(R.id.chooseCopyBank);
        showImgBank = findViewById(R.id.showImgBank);
        contShowCopyBank = findViewById(R.id.contShowCopyBank);
        cbAllow = findViewById(R.id.cbAllow);
        bankNo = findViewById(R.id.bankNo);
        bankName = findViewById(R.id.bankName);
        bankOwner = findViewById(R.id.bankOwner);
        saveFinal = findViewById(R.id.saveFinal);

        for (int i = 0; i < iPosCb1; i++) {
            String getId = "btnCbOne" + (i + 1);
            btnCbOne.set(i, findViewById(getResources().getIdentifier(getId, "id", getPackageName())));
            int finalI = i;
            btnCbOne.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    chooseCbOne = finalI +1;
                    setShowCbOne(finalI);
                }
            });
        }

        for (int i = 0; i < iPosCb1; i++) {
            String getId = "imgCbOne" + (i + 1);
            imgCbOne.set(i, findViewById(getResources().getIdentifier(getId, "id", getPackageName())));
        }

        for (int i = 0; i < iPosCb2; i++) {
            String getId = "btnCbTwo" + (i + 1);
            btnCbTwo.set(i, findViewById(getResources().getIdentifier(getId, "id", getPackageName())));
            int finalI = i;
            btnCbTwo.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    chooseCbTwo = finalI +1;
                    setShowCbTwo(finalI);
                }
            });
        }

        for (int i = 0; i < iPosCb2; i++) {
            String getId = "imgCbTwo" + (i + 1);
            imgCbTwo.set(i, findViewById(getResources().getIdentifier(getId, "id", getPackageName())));
        }
    }

    private void setShowCbTwo(int finalI) {
        for (int i = 0; i < iPosCb2; i++) {
            if((finalI)==(i)){
                imgCbTwo.get(i).setVisibility(View.VISIBLE);
            }else{
                imgCbTwo.get(i).setVisibility(View.GONE);
            }
        }
    }

    private void setShowCbOne(int finalI) {
        for (int i = 0; i < iPosCb1; i++) {
            if((finalI)==(i)){
                imgCbOne.get(i).setVisibility(View.VISIBLE);
            }else{
                imgCbOne.get(i).setVisibility(View.GONE);
            }
        }
    }

    private void setClickBtnPage4() {
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
                goToChooseImg(5);
            }
        });
        saveFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSaveFinal();
            }
        });
    }


    private void setSaveFinal() {
        int cb1Get = chooseCbOne;
        int cb2Get = chooseCbTwo;
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
            deformData.setCbSt(cb1Get);
            deformData.setCbBc(cb2Get);
            deformData.setBankNo(bankNoGet);
            deformData.setBankName(bankNameGet);
            deformData.setBankOwner(bankOwnerGet);
            deformData.setDateCreate(FieldValue.serverTimestamp());
            deformData.setDateModify(FieldValue.serverTimestamp());
            deformData.setStatus("wait");
            deformData.setUserId(MyApplication.getUserId());
            dialogLoad = ProgressDialog.show(elderly1Activity.this, "","กำลังบันทึกข้อมูล...", true);
            db.collection("deform_data")
                    .add(deformData)
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
                            Toast.makeText(elderly1Activity.this, "เกิดข้อผิดผลาด", Toast.LENGTH_SHORT).show();
                            dialogLoad.dismiss();
                        }
                    });
        }
    }

    private void setSendNoti() {
        NotiWebModel notiWebModel = new NotiWebModel();
        notiWebModel.setTxtNoti("มีการขึ้นทะเบียนผู้พิการใหม่");
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

    public void setUploadImgType1(String lastId){
        StorageReference ref = storageReference.child("imgDeform/deform_"+lastId+"/idcard_" + lastId);
        ref.putFile(imageCopyCard)
                .addOnSuccessListener(
                        new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                StorageReference ref2 = storageReference.child("imgDeform/deform_"+lastId+"/home_" + lastId);
                                ref2.putFile(imageCopyHome)
                                        .addOnSuccessListener(
                                                new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                    @Override
                                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                        StorageReference ref3 = storageReference.child("imgDeform/deform_"+lastId+"/card_deform_" + lastId);
                                                        ref3.putFile(imageCopyDeformIdCard)
                                                                .addOnSuccessListener(
                                                                        new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                                            @Override
                                                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                                                StorageReference ref4 = storageReference.child("imgDeform/deform_"+lastId+"/bank_" + lastId);
                                                                                ref4.putFile(imageCopyBank)
                                                                                        .addOnSuccessListener(
                                                                                                new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                                                                    @Override
                                                                                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                                                                        Toast.makeText(elderly1Activity.this, "บันทึกข้อมูลเรียบร้อย", Toast.LENGTH_SHORT).show();
                                                                                                        dialogLoad.dismiss();
                                                                                                        finish();
                                                                                                    }
                                                                                                })
                                                                                        .addOnFailureListener(new OnFailureListener() {
                                                                                            @Override
                                                                                            public void onFailure(@NonNull Exception e) {
                                                                                                dialogLoad.dismiss();
                                                                                                Toast.makeText(elderly1Activity.this, "ไม่สามารถอัพโหลดไฟล์ 2 ได้", Toast.LENGTH_SHORT).show();
                                                                                            }
                                                                                        });
                                                                            }
                                                                        })
                                                                .addOnFailureListener(new OnFailureListener() {
                                                                    @Override
                                                                    public void onFailure(@NonNull Exception e) {
                                                                        dialogLoad.dismiss();
                                                                        Toast.makeText(elderly1Activity.this, "ไม่สามารถอัพโหลดไฟล์ 2 ได้", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                });
                                                    }
                                                })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                dialogLoad.dismiss();
                                                Toast.makeText(elderly1Activity.this, "ไม่สามารถอัพโหลดไฟล์ 2 ได้", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialogLoad.dismiss();
                        Toast.makeText(elderly1Activity.this, "ไม่สามารถอัพโหลดไฟล์ได้", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setUploadImage(String lastId) {
        if(typeRegis==1){
            setUploadImgType1(lastId);
        }else{
            setUploadImgType2(lastId);
        }
    }

    private void setUploadImgType2(String lastId) {
        StorageReference ref0 = storageReference.child("imgDeform/deform_"+lastId+"/book_" + lastId);
        ref0.putFile(imageChooseCur)
                .addOnSuccessListener(
                        new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                setUploadImgType1(lastId);
                            }
                        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialogLoad.dismiss();
                        Toast.makeText(elderly1Activity.this, "ไม่สามารถอัพโหลดไฟล์ ได้", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setClickBtnPage3() {
        btnNextPage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String beforeName3Get = listBeforeName3.get(spinBeforeName3.getSelectedItemPosition());
                String name3Get = name3.getText().toString();
                String lastname3Get = lastname3.getText().toString();
                String phone3Get = phone3.getText().toString();
                if(spinBeforeName3.getSelectedItemPosition()==0||name3Get.isEmpty()||lastname3Get.isEmpty()||phone3Get.isEmpty()){
                    Toast.makeText(elderly1Activity.this, "กรุณากรอกข้อมูลให้ครบ", Toast.LENGTH_SHORT).show();
                }else{
                    deformData.setGetBeforeName3(beforeName3Get);
                    deformData.setName3(name3Get);
                    deformData.setLastName3(lastname3Get);
                    deformData.setPhone3(phone3Get);
                    setShowPage(4);
                }
            }
        });
    }

    private void setSpinnerPage3() {
        listBeforeName3 = new ArrayList<>();
        listBeforeName3.add("เลือกคำนำหน้าชื่อ");
        listBeforeName3.add("นาย");
        listBeforeName3.add("นาง");
        listBeforeName3.add("นางสาว");
        SpinAdapter beforeNameAdapter3 = new SpinAdapter(this, listBeforeName3);
        spinBeforeName3.setAdapter(beforeNameAdapter3);
    }

    private void setIdPage3() {
        btnNextPage3 = findViewById(R.id.btnNextPage3);
        spinBeforeName3 = findViewById(R.id.spinBeforeName3);
        name3 = findViewById(R.id.name3);
        lastname3 = findViewById(R.id.lastname3);
        phone3 = findViewById(R.id.phone3);
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
                        contUpload.setVisibility(View.GONE);
                        showPicUpload.setVisibility(View.VISIBLE);
                        showPicUpload.setImageURI(selectedImageUri);
                        imageChooseCur = selectedImageUri;
                        statusChooseImg = 1;
                    }else if(IMG_POS_CUR==2){
                        contShowCopyCard.setVisibility(View.GONE);
                        showPicCopyCard.setVisibility(View.VISIBLE);
                        showPicCopyCard.setImageURI(selectedImageUri);
                        imageCopyCard = selectedImageUri;
                        statusChooseCopyCard = 1;
                    }else if(IMG_POS_CUR==3){
                        contShowCopyHome.setVisibility(View.GONE);
                        showImageCopyHome.setVisibility(View.VISIBLE);
                        showImageCopyHome.setImageURI(selectedImageUri);
                        imageCopyHome = selectedImageUri;
                        statusChooseCopyHome = 1;
                    }else if(IMG_POS_CUR==4){
                        contShowCopyDeformIdCard.setVisibility(View.GONE);
                        showImageCopyDeformIdCard.setVisibility(View.VISIBLE);
                        showImageCopyDeformIdCard.setImageURI(selectedImageUri);
                        imageCopyDeformIdCard = selectedImageUri;
                        statusChooseCopyDeformIdCard = 1;
                    }else if(IMG_POS_CUR==5){
                        contShowCopyBank.setVisibility(View.GONE);
                        showImgBank.setVisibility(View.VISIBLE);
                        showImgBank.setImageURI(selectedImageUri);
                        imageCopyBank = selectedImageUri;
                        statusChooseBank = 1;
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
                            contUpload.setVisibility(View.GONE);
                            showPicUpload.setVisibility(View.VISIBLE);
                            showPicUpload.setImageURI(selectedImageUri);
                            imageChooseCur = selectedImageUri;
                            statusChooseImg = 1;
                        }else if(IMG_POS_CUR==2){
                            contShowCopyCard.setVisibility(View.GONE);
                            showPicCopyCard.setVisibility(View.VISIBLE);
                            showPicCopyCard.setImageURI(selectedImageUri);
                            imageCopyCard = selectedImageUri;
                            statusChooseCopyCard = 1;
                        }else if(IMG_POS_CUR==3){
                            contShowCopyHome.setVisibility(View.GONE);
                            showImageCopyHome.setVisibility(View.VISIBLE);
                            showImageCopyHome.setImageURI(selectedImageUri);
                            imageCopyHome = selectedImageUri;
                            statusChooseCopyHome = 1;
                        }else if(IMG_POS_CUR==4){
                            contShowCopyDeformIdCard.setVisibility(View.GONE);
                            showImageCopyDeformIdCard.setVisibility(View.VISIBLE);
                            showImageCopyDeformIdCard.setImageURI(selectedImageUri);
                            imageCopyDeformIdCard = selectedImageUri;
                            statusChooseCopyDeformIdCard = 1;
                        }else if(IMG_POS_CUR==5){
                            contShowCopyBank.setVisibility(View.GONE);
                            showImgBank.setVisibility(View.VISIBLE);
                            showImgBank.setImageURI(selectedImageUri);
                            imageCopyBank = selectedImageUri;
                            statusChooseBank = 1;
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
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
                        olderYear.setText(getAge(year,month,day));
                    }
                };
                DatePickerDialog dialog = new DatePickerDialog(elderly1Activity.this, R.style.DialogTheme, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });
        chooseCopyCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToChooseImg(2);
            }
        });
        chooseCopyHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToChooseImg(3);
            }
        });
        chooseCopyDeformIdCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToChooseImg(4);
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
                String typeDeform = listDeform.get(spinDeform.getSelectedItemPosition());
                if(spinBeforeName2.getSelectedItemPosition()==0||olderNameGet.isEmpty()||olderLastNameGet.isEmpty()||olderBirthGet.isEmpty()||olderYearGet.isEmpty()||
                        spinCitizen.getSelectedItemPosition()==0||olderIdCardGet.isEmpty()||spinStanapap.getSelectedItemPosition()==0||olderProfitGet.isEmpty()||
                        spinWork.getSelectedItemPosition()==0||homeNo2Get.isEmpty()||moo2Get.isEmpty()||soi2Get.isEmpty()||road2Get.isEmpty()||
                        province2.getSelectedItemPosition()==0||amphur2.getSelectedItemPosition()==0||district2Get.isEmpty()||postCode2Get.isEmpty()||phone2Get.isEmpty()||
                        spinDeform.getSelectedItemPosition()==0
                ){
                    Toast.makeText(elderly1Activity.this, "กรุณากรอกข้อมูลให้ครบ", Toast.LENGTH_SHORT).show();
                }else if(statusChooseCopyCard==0){
                    Toast.makeText(elderly1Activity.this, "กรุณาอัพโหลดสำเนาบัตรประชาชน", Toast.LENGTH_SHORT).show();
                }else if(statusChooseCopyHome==0){
                    Toast.makeText(elderly1Activity.this, "กรุณาอัพโหลดสำเนาทะเบียนบ้าน", Toast.LENGTH_SHORT).show();
                }else {
                    deformData.setBeforeName2(olderBeforeNameGet);
                    deformData.setDeformName(olderNameGet);
                    deformData.setDeformLastName(olderLastNameGet);
                    deformData.setDeformBirth(olderBirthGet);
                    deformData.setDeformYear(olderYearGet);
                    deformData.setCitizen(citizenget);
                    deformData.setDeformIdCard(olderIdCardGet);
                    deformData.setStanapap(stanapapGet);
                    deformData.setDeformProfit(olderProfitGet);
                    deformData.setWork(workGet);
                    deformData.setHomeNo2(homeNo2Get);
                    deformData.setMoo2(moo2Get);
                    deformData.setTypeDefom(typeDeform);
                    deformData.setSoi2(soi2Get);
                    deformData.setRoad2(road2Get);
                    deformData.setProvince2(province2Get);
                    deformData.setAmphur2(amphur2Get);
                    deformData.setDistrict2(district2Get);
                    deformData.setPostCode2(postCode2Get);
                    deformData.setPhone2(phone2Get);
                    setShowPage(3);
                }
            }
        });
    }

    private void setSpinnerPage2() {
        listDeform = new ArrayList<>();
        listDeform.add("เลือกประเภทความพิการ");
        listDeform.add("พิการทางการมองเห็น");
        listDeform.add("พิการทางการได้ยินหรือสื่อความหมาย");
        listDeform.add("พิการทางการเคลื่อนไหวหรือทางร่างกาย");
        listDeform.add("พิการทางจิตใจ พฤติกรรม หรือ ออทิสติก");
        SpinAdapter deformAdapter = new SpinAdapter(this, listDeform);
        spinDeform.setAdapter(deformAdapter);

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

    public void getProvAllPage2() {
        AdressData adressData = new AdressData(this);
        ArrayList<ProvincesModel> provincesModels = adressData.getProvAll();
        provincesModelsList2 = provincesModels;
        ProvAdapter provAdapter = new ProvAdapter(elderly1Activity.this, provincesModels);
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
        AmphurAdapter amphurAdapter = new AmphurAdapter(elderly1Activity.this, amphuresModels);
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
        spinDeform = findViewById(R.id.spinDeform);
        chooseCopyDeformIdCard = findViewById(R.id.chooseCopyDeformIdCard);
        contShowCopyDeformIdCard = findViewById(R.id.contShowCopyDeformIdCard);
        showImageCopyDeformIdCard = findViewById(R.id.showImageCopyDeformIdCard);

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

    private void setShowPage(int i) {
        pageCur = i;
        if (pageCur == 1) {
            page1.setVisibility(View.VISIBLE);
            page2.setVisibility(View.GONE);
            page3.setVisibility(View.GONE);
            page4.setVisibility(View.GONE);
            processPage1.setVisibility(View.VISIBLE);
            processPage2.setVisibility(View.GONE);
            processPage3.setVisibility(View.GONE);
            processPage4.setVisibility(View.GONE);
        } else if (pageCur == 2) {
            page1.setVisibility(View.GONE);
            page2.setVisibility(View.VISIBLE);
            page3.setVisibility(View.GONE);
            page4.setVisibility(View.GONE);
            processPage1.setVisibility(View.GONE);
            processPage2.setVisibility(View.VISIBLE);
            processPage3.setVisibility(View.GONE);
            processPage4.setVisibility(View.GONE);
        } else if (pageCur == 3) {
            page1.setVisibility(View.GONE);
            page2.setVisibility(View.GONE);
            page3.setVisibility(View.VISIBLE);
            page4.setVisibility(View.GONE);
            processPage1.setVisibility(View.GONE);
            processPage2.setVisibility(View.GONE);
            processPage3.setVisibility(View.VISIBLE);
            processPage4.setVisibility(View.GONE);
        } else if (pageCur == 4) {
            page1.setVisibility(View.GONE);
            page2.setVisibility(View.GONE);
            page3.setVisibility(View.GONE);
            page4.setVisibility(View.VISIBLE);
            processPage1.setVisibility(View.GONE);
            processPage2.setVisibility(View.GONE);
            processPage3.setVisibility(View.GONE);
            processPage4.setVisibility(View.VISIBLE);
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

    private void setSpinnerPage1() {
        listRelay = new ArrayList<>();
        listRelay.add("เลือกความสัมพันธ์");
        listRelay.add("บุตร");
        listRelay.add("บุตรหลาน");
        listRelay.add("คู่สมรส");
        listRelay.add("น้องชาย");
        listRelay.add("น้องสาว");
        listRelay.add("พี่ชาย");
        listRelay.add("พี่สาว");
        listRelay.add("อื่นๆ");
        SpinAdapter relayAdapter = new SpinAdapter(this, listRelay);
        spinRelay.setAdapter(relayAdapter);

        listBeforeName = new ArrayList<>();
        listBeforeName.add("เลือกคำนำหน้าชื่อ");
        listBeforeName.add("นาย");
        listBeforeName.add("นาง");
        listBeforeName.add("นางสาว");
        SpinAdapter beforeNameAdapter = new SpinAdapter(this, listBeforeName);
        spinBeforeName.setAdapter(beforeNameAdapter);



        getProvAll();

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
                goToChooseImg(1);
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
            deformData.setTypeReport(typeRegis);
            deformData.setRelay(ralayGet);
            deformData.setBeforeName(beforeNameGet);
            deformData.setName(nameGet);
            deformData.setLastName(lastNameGet);
            deformData.setIdCard(idCardGet);
            deformData.setHomeNo(homeNoGet);
            deformData.setMoo(mooGet);
            deformData.setSoi(soiGet);
            deformData.setRoad(roadGet);
            deformData.setProvince(provinceGet);
            deformData.setAmphur(amphurGet);
            deformData.setDistrict(districtGet);
            deformData.setPostCode(postCodeGet);
            deformData.setPhone(phoneGet);
            setShowPage(2);
        }
    }

    private void setSavePage1FromDb() {
        DocumentReference docRef = db.collection("users").document(MyApplication.getUserId());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    deformData.setTypeReport(typeRegis);
                    deformData.setRelay("");
                    deformData.setBeforeName("");
                    deformData.setName(document.getData().get("name").toString());
                    deformData.setLastName(document.getData().get("lastname").toString());
                    deformData.setIdCard("");
                    deformData.setHomeNo(document.getData().get("numberass").toString());
                    deformData.setMoo(document.getData().get("mu").toString());
                    deformData.setSoi("");
                    deformData.setRoad(document.getData().get("road").toString());
                    deformData.setProvince(document.getData().get("province").toString());
                    deformData.setAmphur(document.getData().get("district").toString());
                    deformData.setDistrict(document.getData().get("tambon").toString());
                    deformData.setPostCode(document.getData().get("numberpri").toString());
                    deformData.setPhone(document.getData().get("pim01").toString());
                    setShowPage(2);
                } else {
                    Log.d("CHKDB", "get failed with ", task.getException());
                }
            }
        });
    }

    public void openDialogEx() {
        final Dialog dialog = new Dialog(elderly1Activity.this);
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

    public void getProvAll() {
        AdressData adressData = new AdressData(this);
        ArrayList<ProvincesModel> provincesModels = adressData.getProvAll();
        provincesModelsList = provincesModels;
        ProvAdapter provAdapter = new ProvAdapter(elderly1Activity.this, provincesModels);
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
        AmphurAdapter amphurAdapter = new AmphurAdapter(elderly1Activity.this, amphuresModels);
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

}