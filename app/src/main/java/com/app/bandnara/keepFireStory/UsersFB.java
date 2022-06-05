package com.app.bandnara.keepFireStory;

import android.net.Uri;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;

import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Calendar;
import java.util.Date;

public class UsersFB {
    private String ID;
    private String name; // ชื่อ
    private String lastname; // นามสกุล
    private String age; //อายุ
    private String sex; //เพศ
    private String birthday; // ปฏิทินวันเดือนปีเกิด
    private String pim01; //เบอร์โทรศัพท์
    private String pim02; // รหัสผ่าน
    private String pim04; // อีเมล
    private String imgicon; //รูปภาพ
    private String numberass;  //เลขที่อยู่0
    private String mu;  //หมู่0
    private String road; //ถนน0
    private String province; //จังหวัด0
    private String district; //อำเภอ0
    private String tambon; //ตำบอล0
    private String numberpri; //รหัสไปรษณีย์0
    private String numberass1; //เลขที่อยู่1
    private String mu1; //หมู่1
    private String road1; //ถนน1
    private String jungvat; //จังหวัด1
    private String oumper; //อำเภอ1
    private String tumbon1; //ตำบล1
    private String numberpri1; //รหัสไปรษณีย์1
    private String soi;
    private String soi2;
    private Uri imageProflie;
    private String statusSetPin;
    private String pin;
    private FieldValue time;
    private String tokenMs;
    public UsersFB() {

    }

    public String getSoi() {
        return soi;
    }

    public void setSoi(String soi) {
        this.soi = soi;
    }

    public String getSoi2() {
        return soi2;
    }

    public void setSoi2(String soi2) {
        this.soi2 = soi2;
    }

    public String getTokenMs() {
        return tokenMs;
    }

    public void setTokenMs(String tokenMs) {
        this.tokenMs = tokenMs;
    }

    public FieldValue getTime() {
        return time;
    }

    public void setTime(FieldValue time) {
        this.time = time;
    }

    public String getStatusSetPin() {
        return statusSetPin;
    }

    public void setStatusSetPin(String statusSetPin) {
        this.statusSetPin = statusSetPin;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public Uri getImageProflie() {
        return imageProflie;
    }

    public void setImageProflie(Uri imageProflie) {
        this.imageProflie = imageProflie;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPim01() {
        return pim01;
    }

    public void setPim01(String pim01) {
        this.pim01 = pim01;
    }

    public String getPim02() {
        return pim02;
    }

    public void setPim02(String pim02) {
        this.pim02 = pim02;
    }

    public String getPim04() {
        return pim04;
    }

    public void setPim04(String pim04) {
        this.pim04 = pim04;
    }

    public String getImgicon() {
        return imgicon;
    }

    public void setImgicon(String imgicon) {
        this.imgicon = imgicon;
    }

    public String getNumberass() {
        return numberass;
    }

    public void setNumberass(String numberass) {
        this.numberass = numberass;
    }

    public String getMu() {
        return mu;
    }

    public void setMu(String mu) {
        this.mu = mu;
    }

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getTambon() {
        return tambon;
    }

    public void setTambon(String tambon) {
        this.tambon = tambon;
    }

    public String getNumberpri() {
        return numberpri;
    }

    public void setNumberpri(String numberpri) {
        this.numberpri = numberpri;
    }

    public String getNumberass1() {
        return numberass1;
    }

    public void setNumberass1(String numberass1) {
        this.numberass1 = numberass1;
    }

    public String getMu1() {
        return mu1;
    }

    public void setMu1(String mu1) {
        this.mu1 = mu1;
    }

    public String getRoad1() {
        return road1;
    }

    public void setRoad1(String road1) {
        this.road1 = road1;
    }

    public String getJungvat() {
        return jungvat;
    }

    public void setJungvat(String jungvat) {
        this.jungvat = jungvat;
    }

    public String getOumper() {
        return oumper;
    }

    public void setOumper(String oumper) {
        this.oumper = oumper;
    }

    public String getTumbon1() {
        return tumbon1;
    }

    public void setTumbon1(String tumbon1) {
        this.tumbon1 = tumbon1;
    }

    public String getNumberpri1() {
        return numberpri1;
    }

    public void setNumberpri1(String numberpri1) {
        this.numberpri1 = numberpri1;
    }
}
