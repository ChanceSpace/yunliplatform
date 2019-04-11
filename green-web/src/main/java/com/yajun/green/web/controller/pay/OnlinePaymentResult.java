package com.yajun.green.web.controller.pay;

/**
 * Created by chance on 2017/10/19.
 */
public class OnlinePaymentResult {

    private String jiaoYiNumber;

    //0-支付成功
    //1-支付等待
    //2-支付失败
    private int result;

    public String getJiaoYiNumber() {
        return jiaoYiNumber;
    }

    public void setJiaoYiNumber(String jiaoYiNumber) {
        this.jiaoYiNumber = jiaoYiNumber;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}
