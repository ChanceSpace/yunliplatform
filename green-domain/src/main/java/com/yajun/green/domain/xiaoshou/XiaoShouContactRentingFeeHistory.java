package com.yajun.green.domain.xiaoshou;

import com.yajun.green.common.domain.EntityBase;
import org.joda.time.LocalDate;

import java.math.BigDecimal;

/**
 * User: Jack Wang
 * Date: 17-8-8
 * Time: 下午2:03
 * 合同中提车还款历史记录
 */
public class XiaoShouContactRentingFeeHistory extends EntityBase {

    /*********************************合同信息,车辆信息相关*******************************/
    private String vehicleNumber;
    private int tichePiCi;
    private String jiaoYiRen;
    private String jiaoYiRenUuid;
    private XiaoShouContact xiaoShouContact;

    private String contactNumber;

    /*****************************费用记录相关字段*******************************/
    //费用合计
    private BigDecimal feeTotal;

    //费用类型 定金首付尾款
    private XiaoShouContactHistoryType xiaoShouContactHistoryType;

    //记录费用的开始时间 于execute 里面的ticheDate 保持一致
    private LocalDate actualFeeDate;
    //记录费用的结束时间 支付完成后更新 记录支付完成后的时间
    private LocalDate actualFeeDateEnd;
    //实际录入系统的时间
    private LocalDate happendDate;
    //备注
    private String comment;

    /*********************************支付相关字段*******************************/
    //支付状态
    private XiaoShouPayOrderStatus payOrderStatus;
    //支付订单号
    private String payOrderNumber;

    public XiaoShouContactRentingFeeHistory() {
    }

    public XiaoShouContactRentingFeeHistory(String vehicleNumber, int tichePiCi, String jiaoYiRen, String jiaoYiRenUuid, XiaoShouContact xiaoShouContact, String contactNumber, BigDecimal feeTotal, XiaoShouContactHistoryType xiaoShouContactHistoryType, LocalDate actualFeeDate, LocalDate actualFeeDateEnd, LocalDate happendDate, String comment, XiaoShouPayOrderStatus payOrderStatus, String payOrderNumber) {
        this.vehicleNumber = vehicleNumber;
        this.tichePiCi = tichePiCi;
        this.jiaoYiRen = jiaoYiRen;
        this.jiaoYiRenUuid = jiaoYiRenUuid;
        this.xiaoShouContact = xiaoShouContact;
        this.contactNumber = contactNumber;
        this.feeTotal = feeTotal;
        this.xiaoShouContactHistoryType = xiaoShouContactHistoryType;
        this.actualFeeDate = actualFeeDate;
        this.actualFeeDateEnd = actualFeeDateEnd;
        this.happendDate = happendDate;
        this.comment = comment;
        this.payOrderStatus = payOrderStatus;
        this.payOrderNumber = payOrderNumber;
    }

    /*********************************setter getter*******************************/
    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public int getTichePiCi() {
        return tichePiCi;
    }

    public void setTichePiCi(int tichePiCi) {
        this.tichePiCi = tichePiCi;
    }

    public String getJiaoYiRen() {
        return jiaoYiRen;
    }

    public void setJiaoYiRen(String jiaoYiRen) {
        this.jiaoYiRen = jiaoYiRen;
    }

    public String getJiaoYiRenUuid() {
        return jiaoYiRenUuid;
    }

    public void setJiaoYiRenUuid(String jiaoYiRenUuid) {
        this.jiaoYiRenUuid = jiaoYiRenUuid;
    }

    public XiaoShouContact getXiaoShouContact() {
        return xiaoShouContact;
    }

    public void setXiaoShouContact(XiaoShouContact xiaoShouContact) {
        this.xiaoShouContact = xiaoShouContact;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public BigDecimal getFeeTotal() {
        return feeTotal;
    }

    public void setFeeTotal(BigDecimal feeTotal) {
        this.feeTotal = feeTotal;
    }

    public XiaoShouContactHistoryType getXiaoShouContactHistoryType() {
        return xiaoShouContactHistoryType;
    }

    public void setXiaoShouContactHistoryType(XiaoShouContactHistoryType xiaoShouContactHistoryType) {
        this.xiaoShouContactHistoryType = xiaoShouContactHistoryType;
    }

    public LocalDate getActualFeeDate() {
        return actualFeeDate;
    }

    public void setActualFeeDate(LocalDate actualFeeDate) {
        this.actualFeeDate = actualFeeDate;
    }

    public LocalDate getActualFeeDateEnd() {
        return actualFeeDateEnd;
    }

    public void setActualFeeDateEnd(LocalDate actualFeeDateEnd) {
        this.actualFeeDateEnd = actualFeeDateEnd;
    }

    public LocalDate getHappendDate() {
        return happendDate;
    }

    public void setHappendDate(LocalDate happendDate) {
        this.happendDate = happendDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public XiaoShouPayOrderStatus getPayOrderStatus() {
        return payOrderStatus;
    }

    public void setPayOrderStatus(XiaoShouPayOrderStatus payOrderStatus) {
        this.payOrderStatus = payOrderStatus;
    }

    public String getPayOrderNumber() {
        return payOrderNumber;
    }

    public void setPayOrderNumber(String payOrderNumber) {
        this.payOrderNumber = payOrderNumber;
    }
}
