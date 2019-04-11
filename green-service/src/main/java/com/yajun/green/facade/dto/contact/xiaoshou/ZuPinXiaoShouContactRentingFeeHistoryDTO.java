package com.yajun.green.facade.dto.contact.xiaoshou;

import com.yajun.green.common.security.LoginInfo;
import com.yajun.green.domain.xiaoshou.XiaoShouContactHistoryType;
import com.yajun.green.domain.xiaoshou.XiaoShouPayOrderStatus;
import com.yajun.green.facade.dto.pay.PayItem;
import com.yajun.green.security.SecurityUtils;
import org.joda.time.LocalDate;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;

/**
 * Created by LiuMengKe on 2017/12/20.
 */
public class ZuPinXiaoShouContactRentingFeeHistoryDTO implements PayItem{
    /*********************************合同信息,车辆信息相关*******************************/
    private String uuid;
    private String vehicleNumber;
    private int tichePiCi;
    private String jiaoYiRen;
    private String jiaoYiRenUuid;
    private String contactNumber;

    /*****************************费用记录相关字段*******************************/
    //费用合计
    private BigDecimal feeTotal;

    //费用类型 定金首付尾款
    private XiaoShouContactHistoryType xiaoShouContactHistoryType;

    //所缴纳的月份的开始时间
    private LocalDate actualFeeDate;
    //所缴纳的月份的结束时间
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


    public ZuPinXiaoShouContactRentingFeeHistoryDTO() {
        LoginInfo info = SecurityUtils.currentLoginUser();
        this.jiaoYiRen = info.getXingMing();
        this.jiaoYiRenUuid = info.getUserUuid();
        this.payOrderStatus = XiaoShouPayOrderStatus.O_CREATE;
        this.happendDate = new LocalDate();
    }

    public ZuPinXiaoShouContactRentingFeeHistoryDTO(String uuid, String vehicleNumber, int tichePiCi, String jiaoYiRen, String jiaoYiRenUuid, String contactNumber, BigDecimal feeTotal, XiaoShouContactHistoryType xiaoShouContactHistoryType, LocalDate actualFeeDate, LocalDate actualFeeDateEnd, LocalDate happendDate, String comment, XiaoShouPayOrderStatus payOrderStatus, String payOrderNumber) {
        this.uuid = uuid;
        this.vehicleNumber = vehicleNumber;
        this.tichePiCi = tichePiCi;
        this.jiaoYiRen = jiaoYiRen;
        this.jiaoYiRenUuid = jiaoYiRenUuid;
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

    @Override
    public String getHappenDate() {
         return happendDate.toString();
    }

    @Override
    public String getSource() {
        return "合同：" + contactNumber;
    }

    @Override
    public String getDetails() {
        String vehicleNumber = StringUtils.hasText(getVehicleNumber())?getVehicleNumber():"";
        String  result = new StringBuilder(vehicleNumber).toString() + getXiaoShouContactHistoryType().getDescription();
        return result;
    }

    @Override
    public BigDecimal getItemFee() {
        return feeTotal;
    }

    /*********************************setter getter*******************************/

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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
