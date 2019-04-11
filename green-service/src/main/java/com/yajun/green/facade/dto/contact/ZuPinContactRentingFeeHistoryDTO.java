package com.yajun.green.facade.dto.contact;


import com.yajun.green.common.log.ApplicationLog;
import com.yajun.green.domain.contact.ZuPinContactRepayLocation;
import com.yajun.green.domain.contact.ZuPinYaJinType;
import com.yajun.green.domain.pay.PayOrderStatus;
import com.yajun.green.facade.dto.pay.PayItem;
import org.joda.time.LocalDate;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;

/**
 * User: Jack Wang
 * Date: 17-8-8
 * Time: 下午2:03
 * 合同中提车还款历史记录
 */
public class ZuPinContactRentingFeeHistoryDTO implements PayItem {

    /*********************************合同信息,车辆信息相关*******************************/
    private String uuid;
    private String vehicleNumber;
    private int tichePiCi;
    private String customerUuid;
    private String customerName;
    private String saleManName;
    private String contactNumber;
    private String jiaoYiRen;
    private String jiaoYiRenUuid;
    private String zuPinContactUuid;

    /*****************************费用记录相关字段*******************************/
    //全款分期
    private ZuPinYaJinType zuPinYaJinType;
    //缴纳类型
    private ZuPinContactRepayLocation zuPinContactRepayLocation;
    private String zuPinRepayLocationInShow;

    //录入系统时间
    private String happendDate;
    //实际缴纳费用（押金或租金)所缴纳的缴纳月 比如五月缴纳三月的租金 这个值就为三月的
    private LocalDate actualFeeDate;
    private LocalDate actualFeeDateEnd;
    private String feeTotal;
    private BigDecimal feeTotalInshow;
    //押金分期数
    private String fenQiShu;
    //押金首付
    private BigDecimal shouFu;
    //押金月供
    private BigDecimal yueGong;
    private String comment;
    private boolean tianJia;
    /*********************************支付相关字段*******************************/
    //支付状态
    private PayOrderStatus status;
    //显示支付状态
    private String payStatusName;
    //是否能够取消
    private boolean canBeCancel;

    public ZuPinContactRentingFeeHistoryDTO() {
    }

    public ZuPinContactRentingFeeHistoryDTO(String contactNumber, String vehicleNumber, String happendDate, String feeTotal, String zuPinContactUuid, int tichePiCi, String customerUuid, String customerName,String jiaoYiRen,String jiaoYiRenUuid,String comment) {
        this.contactNumber = contactNumber;
        this.vehicleNumber = vehicleNumber;
        this.happendDate = happendDate;
        this.feeTotal = feeTotal;
        this.zuPinContactUuid = zuPinContactUuid;
        this.tichePiCi = tichePiCi;
        this.customerUuid = customerUuid;
        this.customerName = customerName;
        this.jiaoYiRen = jiaoYiRen;
        this.jiaoYiRenUuid = jiaoYiRenUuid;
        this.comment = comment;
    }

    public ZuPinContactRentingFeeHistoryDTO(String uuid, String vehicleNumber, String happendDate, String feeTotal, String zuPinContactUuid, int tichePiCi, String customerUuid, String customerName,String saleManName,String contactNumber,String jiaoYiRen,String jiaoYiRenUuid,boolean tianJia,String comment) {
        this.uuid = uuid;
        this.vehicleNumber = vehicleNumber;
        this.happendDate = happendDate;
        this.feeTotal = feeTotal;
        this.zuPinContactUuid = zuPinContactUuid;
        this.tichePiCi = tichePiCi;
        this.customerUuid = customerUuid;
        this.customerName = customerName;
        this.saleManName = saleManName;
        this.contactNumber = contactNumber;
        this.jiaoYiRen = jiaoYiRen;
        this.jiaoYiRenUuid = jiaoYiRenUuid;
        this.tianJia = tianJia;
        this.comment = comment;
    }

    /****************************************支付接口****************************************/

   @Override
    public String getHappenDate() {
       return happendDate;
   }

    @Override
    public String getSource() {
        return "合同：" + contactNumber;
    }

    @Override
    public String getDetails() {
        String vehicleNumber = StringUtils.hasText(getVehicleNumber())?getVehicleNumber():"";
        String  result = new StringBuilder(vehicleNumber).toString() +getZuPinContactRepayLocation().getDescription();
        return result;
    }

    @Override
    public BigDecimal getItemFee() {
        return getFeeTotalInshow();
    }

    /***************************************GET/SET*******************************************/

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

    public String getUuid() {
        return uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getHappendDate() {
        return happendDate;
    }

    public void setHappendDate(String happendDate) {
        this.happendDate = happendDate;
    }

    public String getFeeTotal() {
        return feeTotal;
    }

    public void setFeeTotal(String feeTotal) {
        this.feeTotal = feeTotal;
    }

    public String getZuPinContactUuid() {
        return zuPinContactUuid;
    }

    public void setZuPinContactUuid(String zuPinContactUuid) {
        this.zuPinContactUuid = zuPinContactUuid;
    }

    public String getCustomerUuid() {
        return customerUuid;
    }

    public void setCustomerUuid(String customerUuid) {
        this.customerUuid = customerUuid;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getSaleManName() {
        return saleManName;
    }

    public void setSaleManName(String saleManName) {
        this.saleManName = saleManName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
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

    public String getZuPinRepayLocationInShow() {
        return zuPinContactRepayLocation.getDescription();
    }

    public void setZuPinRepayLocationInShow(String zuPinRepayLocationInShow) {
        this.zuPinRepayLocationInShow = zuPinRepayLocationInShow;
    }

    public LocalDate getActualFeeDate() {
        return actualFeeDate;
    }

    public void setActualFeeDate(LocalDate actualFeeDate) {
        this.actualFeeDate = actualFeeDate;
    }

    public String getActualFeeInShow() {
        return getActualFeeDate().toString()+"--"+getActualFeeDate().plusMonths(1).plusDays(-1);
    }

    public BigDecimal getFeeTotalInshow() {
        return new BigDecimal(feeTotal);
    }

    public void setFeeTotalInshow(BigDecimal feeTotalInshow) {
        this.feeTotalInshow = feeTotalInshow;
    }

    public PayOrderStatus getStatus() {
        return status;
    }

    public void setStatus(PayOrderStatus status) {
        this.status = status;
    }

    public String getPayStatusName() {
        return status.name();
    }

    public void setPayStatusName(String payStatusName) {
        this.payStatusName = payStatusName;
    }

    public boolean isCanBeCancel() {
        return canBeCancel;
    }

    public void setCanBeCancel(boolean canBeCancel) {
        this.canBeCancel = canBeCancel;
    }

    public LocalDate getActualFeeDateEnd() {
        return actualFeeDateEnd;
    }

    public void setActualFeeDateEnd(LocalDate actualFeeDateEnd) {
        this.actualFeeDateEnd = actualFeeDateEnd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ZuPinContactRentingFeeHistoryDTO that = (ZuPinContactRentingFeeHistoryDTO) o;

        if (uuid != null ? !uuid.equals(that.uuid) : that.uuid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return uuid != null ? uuid.hashCode() : 0;
    }
}
