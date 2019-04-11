package com.yajun.green.domain.contact;

import com.yajun.green.common.domain.EntityBase;
import com.yajun.green.domain.pay.PayOrderStatus;
import org.joda.time.LocalDate;

import java.math.BigDecimal;

/**
 * User: Jack Wang
 * Date: 17-8-8
 * Time: 下午2:03
 * 合同中提车还款历史记录
 */
public class ZuPinContactRentingFeeHistory extends EntityBase {

    /*********************************合同信息,车辆信息相关*******************************/
    private String vehicleNumber;
    private int tichePiCi;
    private String jiaoYiRen;
    private String jiaoYiRenUuid;
    private ZuPinContact zuPinContact;
    //押金类型 全款分期
    private ZuPinYaJinType zuPinYaJinType;
    private String contactNumber;

    /*****************************费用记录相关字段*******************************/
    //费用合计
    private BigDecimal feeTotal;
    //押金相关仅用于计算 数据库无字段对应
    //押金分期数
    private String fenQiShu;
    //押金首付
    private BigDecimal shouFu;
    //押金月供
    private BigDecimal yueGong;
    //扣费类型 自动租金扣费 自动押金扣费等
    private ZuPinContactRepayLocation zuPinContactRepayLocation;

    //所缴纳的月份的开始时间
    private LocalDate actualFeeDate;
    //所缴纳的月份的结束时间
    private LocalDate actualFeeDateEnd;
    //实际录入系统的时间
    private LocalDate happendDate;

    //true 充值 false 扣钱
    private boolean tianJia;
    //备注
    private String comment;

    /*********************************支付相关字段*******************************/
    //支付状态
    private PayOrderStatus status;
    //支付订单号
    private String payOrderNumber;

    public ZuPinContactRentingFeeHistory() {
    }

    public ZuPinContactRentingFeeHistory(String contactNumber, String vehicleNumber, LocalDate happendDate, BigDecimal feeTotal, ZuPinContact zuPinContact, int tichePiCi,
                                         String jiaoYiRen, String jiaoYiRenUuid, boolean tianJia, String comment) {
        this.contactNumber = contactNumber;
        this.vehicleNumber = vehicleNumber;
        this.happendDate = happendDate;
        this.feeTotal = feeTotal;
        this.zuPinContact = zuPinContact;
        this.tichePiCi = tichePiCi;
        this.jiaoYiRen = jiaoYiRen;
        this.jiaoYiRenUuid = jiaoYiRenUuid;
        this.tianJia = tianJia;
        this.comment = comment;
        this.status = PayOrderStatus.O_CREATE;
    }

    /*********************************初始化账单为未支付状态*******************************/
    public void initHistoryStatus(){
        setStatus(PayOrderStatus.O_CREATE);
    }
    /**
     * **************************************GET/SET******************************************
     */


    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public LocalDate getHappendDate() {
        return happendDate;
    }

    public void setHappendDate(LocalDate happendDate) {
        this.happendDate = happendDate;
    }

    public BigDecimal getFeeTotal() {
        return feeTotal;
    }

    public void setFeeTotal(BigDecimal feeTotal) {
        this.feeTotal = feeTotal;
    }

    public ZuPinContact getContact() {
        return zuPinContact;
    }

    public void setContact(ZuPinContact zuPinContact) {
        this.zuPinContact = zuPinContact;
    }

    public ZuPinContact getZuPinContact() {
        return zuPinContact;
    }

    public void setZuPinContact(ZuPinContact zuPinContact) {
        this.zuPinContact = zuPinContact;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isTianJia() {
        return tianJia;
    }

    public void setTianJia(boolean tianJia) {
        this.tianJia = tianJia;
    }

    public ZuPinYaJinType getZuPinYaJinType() {
        return zuPinYaJinType;
    }

    public void setZuPinYaJinType(ZuPinYaJinType zuPinYaJinType) {
        this.zuPinYaJinType = zuPinYaJinType;
    }

    public String getFenQiShu() {
        return fenQiShu;
    }

    public void setFenQiShu(String fenQiShu) {
        this.fenQiShu = fenQiShu;
    }

    public BigDecimal getShouFu() {
        return shouFu;
    }

    public void setShouFu(BigDecimal shouFu) {
        this.shouFu = shouFu;
    }

    public BigDecimal getYueGong() {
        return yueGong;
    }

    public void setYueGong(BigDecimal yueGong) {
        this.yueGong = yueGong;
    }

    public ZuPinContactRepayLocation getZuPinContactRepayLocation() {
        return zuPinContactRepayLocation;
    }

    public void setZuPinContactRepayLocation(ZuPinContactRepayLocation zuPinContactRepayLocation) {
        this.zuPinContactRepayLocation = zuPinContactRepayLocation;
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

    public PayOrderStatus getStatus() {
        return status;
    }

    public void setStatus(PayOrderStatus status) {
        this.status = status;
    }

    public String getPayOrderNumber() {
        return payOrderNumber;
    }

    public void setPayOrderNumber(String payOrderNumber) {
        this.payOrderNumber = payOrderNumber;
    }
}
