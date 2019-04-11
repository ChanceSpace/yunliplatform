package com.yajun.green.web.utils;

/**
 * Created by LiuMengKe on 2017/8/10.
 * 合同开头法人 等信息
 */
public class ContactParagrah1Object {

    private String jiaFangcompanyName;
    private String jiaFangFaRen;
    private String jiaFangAddress;
    private String jiaFangPostCode;

    /******公司名字******/
    private String companyName;
    /******法人******/
    private String faren;
    /******地址******/
    private String address;
    /******邮编******/
    private String postcode;

    public ContactParagrah1Object() {
    }

    public ContactParagrah1Object(String jiaFangcompanyName, String jiaFangFaRen, String jiaFangAddress, String jiaFangPostCode, String companyName, String faren, String address, String postcode) {
        this.jiaFangcompanyName = jiaFangcompanyName;
        this.jiaFangFaRen = jiaFangFaRen;
        this.jiaFangAddress = jiaFangAddress;
        this.jiaFangPostCode = jiaFangPostCode;
        this.companyName = companyName;
        this.faren = faren;
        this.address = address;
        this.postcode = postcode;
    }

    public String getFaren() {
        return faren;
    }

    public void setFaren(String faren) {
        this.faren = faren;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getJiaFangcompanyName() {
        return jiaFangcompanyName;
    }

    public void setJiaFangcompanyName(String jiaFangcompanyName) {
        this.jiaFangcompanyName = jiaFangcompanyName;
    }

    public String getJiaFangFaRen() {
        return jiaFangFaRen;
    }

    public void setJiaFangFaRen(String jiaFangFaRen) {
        this.jiaFangFaRen = jiaFangFaRen;
    }

    public String getJiaFangAddress() {
        return jiaFangAddress;
    }

    public void setJiaFangAddress(String jiaFangAddress) {
        this.jiaFangAddress = jiaFangAddress;
    }

    public String getJiaFangPostCode() {
        return jiaFangPostCode;
    }

    public void setJiaFangPostCode(String jiaFangPostCode) {
        this.jiaFangPostCode = jiaFangPostCode;
    }
}
