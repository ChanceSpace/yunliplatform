package com.yajun.green.web.utils.xiaoshou;

/**
 * Created by LiuMengKe on 2017/8/10.
 * 合同生成 条例二 参数封装类
 */
public class XiaoShouContactPdfParagrah2Object {
    /******一台车押金******/
    private String yajinOne;
    /*******一台车租金 每月*****/
    private String zujinOne;
    /******租期多少个月******/
    private String qixian;
    /*******数量*****/
    private String taishu;
    /*******总押金*****/
    private String yajinTotal;
    /******总押金 汉字******/
    private String yajinTotalFanti;
    /******首月租金合计******/
    private String shouyuezujinHeji;
    /******首月租金合计 汉字******/
    private String shouyuezujinHejiFanti;

    public XiaoShouContactPdfParagrah2Object() {
    }

    public XiaoShouContactPdfParagrah2Object(String yajinOne, String zujinOne, String qixian, String taishu, String yajinTotal, String yajinTotalFanti, String shouyuezujinHeji, String shouyuezujinHejiFanti) {
        this.yajinOne = yajinOne;
        this.zujinOne = zujinOne;
        this.qixian = qixian;
        this.taishu = taishu;
        this.yajinTotal = yajinTotal;
        this.yajinTotalFanti = yajinTotalFanti;
        this.shouyuezujinHeji = shouyuezujinHeji;
        this.shouyuezujinHejiFanti = shouyuezujinHejiFanti;
    }

    public String getYajinOne() {
        return yajinOne;
    }

    public void setYajinOne(String yajinOne) {
        this.yajinOne = yajinOne;
    }

    public String getZujinOne() {
        return zujinOne;
    }

    public void setZujinOne(String zujinOne) {
        this.zujinOne = zujinOne;
    }

    public String getQixian() {
        return qixian;
    }

    public void setQixian(String qixian) {
        this.qixian = qixian;
    }

    public String getTaishu() {
        return taishu;
    }

    public void setTaishu(String taishu) {
        this.taishu = taishu;
    }

    public String getYajinTotal() {
        return yajinTotal;
    }

    public void setYajinTotal(String yajinTotal) {
        this.yajinTotal = yajinTotal;
    }

    public String getYajinTotalFanti() {
        return yajinTotalFanti;
    }

    public void setYajinTotalFanti(String yajinTotalFanti) {
        this.yajinTotalFanti = yajinTotalFanti;
    }

    public String getShouyuezujinHeji() {
        return shouyuezujinHeji;
    }

    public void setShouyuezujinHeji(String shouyuezujinHeji) {
        this.shouyuezujinHeji = shouyuezujinHeji;
    }

    public String getShouyuezujinHejiFanti() {
        return shouyuezujinHejiFanti;
    }

    public void setShouyuezujinHejiFanti(String shouyuezujinHejiFanti) {
        this.shouyuezujinHejiFanti = shouyuezujinHejiFanti;
    }
}
