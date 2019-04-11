package com.yajun.green.facade.dto.chengyun;

import com.yajun.green.facade.dto.pay.PayItem;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by chance on 2017/10/16.
 */
public class CarrierRentFeeHistoryDTO implements PayItem {

    private String uuid;
    private String userUuid;

    private String happenDate;
    private BigDecimal feeTotal;
    private String comment;

    private String jiaoYiNumber;

    public CarrierRentFeeHistoryDTO() {
    }

    public CarrierRentFeeHistoryDTO(String uuid, String happenDate, BigDecimal feeTotal, String comment) {
        this.uuid = uuid;
        this.happenDate = happenDate;
        this.feeTotal = feeTotal;
        this.comment = comment;
    }

    /****************************************支付接口****************************************/

    @Override
    public String getSource() {
        return "线下交易";
    }

    @Override
    public String getDetails() {
        return "";
    }

    @Override
    public BigDecimal getItemFee() {
        return getFeeTotal();
    }

     @Override
    public String getVehicleNumber() {
        return null;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getHappenDate() {
        return happenDate;
    }

    public void setHappenDate(String happenDate) {
        this.happenDate = happenDate;
    }

    public BigDecimal getFeeTotal() {
        return feeTotal;
    }

    public void setFeeTotal(BigDecimal feeTotal) {
        this.feeTotal = feeTotal;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getJiaoYiNumber() {
        return jiaoYiNumber;
    }

    public void setJiaoYiNumber(String jiaoYiNumber) {
        this.jiaoYiNumber = jiaoYiNumber;
    }
}
