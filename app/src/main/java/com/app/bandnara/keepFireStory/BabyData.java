package com.app.bandnara.keepFireStory;

import com.google.firebase.firestore.FieldValue;

public class BabyData {
    private String relayBaby;
    private String beforeName;
    private String name;
    private String lastName;
    private String idCard;
    private String homeNo;
    private String moo;
    private String soi;
    private String road;
    private String province;
    private String amphur;
    private String district;
    private String postCode;
    private String phone;
    private String birth;
    private String age;
    private String citizen;
    private String homeNo2;
    private String moo2;
    private String soi2;
    private String road2;
    private String province2;
    private String amphur2;
    private String district2;
    private String postCode2;
    private String phone2;
    private String works;
    private String edu;
    private String eduFill;

    private String childBeforeName;
    private String childName;
    private String childLastname;
    private String childIdCard;
    private String childBirth;

    private String motherBeforeName;
    private String motherName;
    private String motherLastName;
    private String motherAge;
    private String motherCitizen;
    private String motherWork;
    private String motherEdu;
    private String motherFillEdu;

    private String fatherBeforeName;
    private String fatherName;
    private String fatherLastName;
    private String fatherAge;
    private String fatherCitizen;
    private String fatherWork;
    private String fatherEdu;
    private String fatherFillEdu;
    private String fatherHave;

    private int cbBc;
    private String bankNo;
    private String bankName;
    private String bankOwner;

    private String status;

    private FieldValue dateCreate;
    private FieldValue dateModify;
    private String userId;

    public BabyData() {

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public FieldValue getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(FieldValue dateCreate) {
        this.dateCreate = dateCreate;
    }

    public FieldValue getDateModify() {
        return dateModify;
    }

    public void setDateModify(FieldValue dateModify) {
        this.dateModify = dateModify;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCbBc() {
        return cbBc;
    }

    public void setCbBc(int cbBc) {
        this.cbBc = cbBc;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankOwner() {
        return bankOwner;
    }

    public void setBankOwner(String bankOwner) {
        this.bankOwner = bankOwner;
    }

    public String getFatherHave() {
        return fatherHave;
    }

    public void setFatherHave(String fatherHave) {
        this.fatherHave = fatherHave;
    }

    public String getChildBeforeName() {
        return childBeforeName;
    }

    public void setChildBeforeName(String childBeforeName) {
        this.childBeforeName = childBeforeName;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public String getChildLastname() {
        return childLastname;
    }

    public void setChildLastname(String childLastname) {
        this.childLastname = childLastname;
    }

    public String getChildIdCard() {
        return childIdCard;
    }

    public void setChildIdCard(String childIdCard) {
        this.childIdCard = childIdCard;
    }

    public String getChildBirth() {
        return childBirth;
    }

    public void setChildBirth(String childBirth) {
        this.childBirth = childBirth;
    }

    public String getMotherBeforeName() {
        return motherBeforeName;
    }

    public void setMotherBeforeName(String motherBeforeName) {
        this.motherBeforeName = motherBeforeName;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getMotherLastName() {
        return motherLastName;
    }

    public void setMotherLastName(String motherLastName) {
        this.motherLastName = motherLastName;
    }

    public String getMotherAge() {
        return motherAge;
    }

    public void setMotherAge(String motherAge) {
        this.motherAge = motherAge;
    }

    public String getMotherCitizen() {
        return motherCitizen;
    }

    public void setMotherCitizen(String motherCitizen) {
        this.motherCitizen = motherCitizen;
    }

    public String getMotherWork() {
        return motherWork;
    }

    public void setMotherWork(String motherWork) {
        this.motherWork = motherWork;
    }

    public String getMotherEdu() {
        return motherEdu;
    }

    public void setMotherEdu(String motherEdu) {
        this.motherEdu = motherEdu;
    }

    public String getMotherFillEdu() {
        return motherFillEdu;
    }

    public void setMotherFillEdu(String motherFillEdu) {
        this.motherFillEdu = motherFillEdu;
    }

    public String getFatherBeforeName() {
        return fatherBeforeName;
    }

    public void setFatherBeforeName(String fatherBeforeName) {
        this.fatherBeforeName = fatherBeforeName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getFatherLastName() {
        return fatherLastName;
    }

    public void setFatherLastName(String fatherLastName) {
        this.fatherLastName = fatherLastName;
    }

    public String getFatherAge() {
        return fatherAge;
    }

    public void setFatherAge(String fatherAge) {
        this.fatherAge = fatherAge;
    }

    public String getFatherCitizen() {
        return fatherCitizen;
    }

    public void setFatherCitizen(String fatherCitizen) {
        this.fatherCitizen = fatherCitizen;
    }

    public String getFatherWork() {
        return fatherWork;
    }

    public void setFatherWork(String fatherWork) {
        this.fatherWork = fatherWork;
    }

    public String getFatherEdu() {
        return fatherEdu;
    }

    public void setFatherEdu(String fatherEdu) {
        this.fatherEdu = fatherEdu;
    }

    public String getFatherFillEdu() {
        return fatherFillEdu;
    }

    public void setFatherFillEdu(String fatherFillEdu) {
        this.fatherFillEdu = fatherFillEdu;
    }

    public String getWorks() {
        return works;
    }

    public void setWorks(String works) {
        this.works = works;
    }

    public String getEdu() {
        return edu;
    }

    public void setEdu(String edu) {
        this.edu = edu;
    }

    public String getEduFill() {
        return eduFill;
    }

    public void setEduFill(String eduFill) {
        this.eduFill = eduFill;
    }

    public String getRelayBaby() {
        return relayBaby;
    }

    public void setRelayBaby(String relayBaby) {
        this.relayBaby = relayBaby;
    }

    public String getBeforeName() {
        return beforeName;
    }

    public void setBeforeName(String beforeName) {
        this.beforeName = beforeName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getHomeNo() {
        return homeNo;
    }

    public void setHomeNo(String homeNo) {
        this.homeNo = homeNo;
    }

    public String getMoo() {
        return moo;
    }

    public void setMoo(String moo) {
        this.moo = moo;
    }

    public String getSoi() {
        return soi;
    }

    public void setSoi(String soi) {
        this.soi = soi;
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

    public String getAmphur() {
        return amphur;
    }

    public void setAmphur(String amphur) {
        this.amphur = amphur;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getCitizen() {
        return citizen;
    }

    public void setCitizen(String citizen) {
        this.citizen = citizen;
    }

    public String getHomeNo2() {
        return homeNo2;
    }

    public void setHomeNo2(String homeNo2) {
        this.homeNo2 = homeNo2;
    }

    public String getMoo2() {
        return moo2;
    }

    public void setMoo2(String moo2) {
        this.moo2 = moo2;
    }

    public String getSoi2() {
        return soi2;
    }

    public void setSoi2(String soi2) {
        this.soi2 = soi2;
    }

    public String getRoad2() {
        return road2;
    }

    public void setRoad2(String road2) {
        this.road2 = road2;
    }

    public String getProvince2() {
        return province2;
    }

    public void setProvince2(String province2) {
        this.province2 = province2;
    }

    public String getAmphur2() {
        return amphur2;
    }

    public void setAmphur2(String amphur2) {
        this.amphur2 = amphur2;
    }

    public String getDistrict2() {
        return district2;
    }

    public void setDistrict2(String district2) {
        this.district2 = district2;
    }

    public String getPostCode2() {
        return postCode2;
    }

    public void setPostCode2(String postCode2) {
        this.postCode2 = postCode2;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }
}
