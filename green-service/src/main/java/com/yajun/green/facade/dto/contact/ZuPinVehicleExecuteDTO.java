package com.yajun.green.facade.dto.contact;

import com.yajun.green.domain.contact.ZuPinContactRepayType;
import com.yajun.green.domain.contact.ZuPinYaJinType;
import org.joda.time.LocalDate;

import java.math.BigDecimal;

/**
 * User: Jack Wang
 * Date: 17-8-14
 * Time: 上午8:59
 */
public class ZuPinVehicleExecuteDTO {

    private String uuid;

    private String moduleName;
    private String moduleType;
    private String moduleBrand;
    private String moduleColor;
    private String moduleDianLiang;

    private String vehicleNum;
    private int tiChePiCi;
    private boolean isOver;
    private boolean zupinover;
    private boolean overButNotRevert;
    private boolean piciOver;


    private String tiCheDate;
    private String jieshuDate;
    private String jieshuDateInShow;
    private String rentMonth;
    private String comment;

    private String zuPinContactUuid;

    private String contactNumber;
    private String customerName;
    private String cumtomerPhoneNumber;

    private String saleManName;

    /**********************租金相关**********************/
    //租金规则
    private ZuPinContactRepayType zuPinContactRepayType;
    //新增实际租金
    private String actualRentFee;
    //页面上展示的押金到期时间 比 nextFeeDate 少一天
    private String nextFeeDateInShow;
    //延迟月份
    private Integer delayMonth;
    //下次缴费时间
    private String nextFeeDate;
    //下次短息提示时间
    private String nextMessageDate;
    //具体缴纳的是第那个月的租金
    private LocalDate actualzujinrepaymonth;

    /**********************押金相关**********************/
    //新增押金规则
    private ZuPinYaJinType zuPinYaJinType;
    private BigDecimal singleYaJin;
    private String fenQiShu;
    private BigDecimal shouFu;
    private BigDecimal yueGong;
    //下次押金缴纳时间
    private LocalDate nextYajinDate;
    //具体哪个月的押金
    private LocalDate actualyajinrepaymonth;
    //押金已经偿还次数
    private Integer yajinHasPayCishu;


    public ZuPinVehicleExecuteDTO() {
        this.tiCheDate = new LocalDate().toString();
        this.nextFeeDate = new LocalDate().toString();
        this.nextYajinDate = new LocalDate();
    }

    public ZuPinVehicleExecuteDTO(String uuid, String saleManName,
                                  String moduleName, String moduleType, String moduleBrand, String moduleColor, String moduleDianLiang,
                                  String rentMonth, String vehicleNum, int tiChePiCi, String tiCheDate, String nextFeeDate, String zuPinContactUuid,String actualRentFee,
                                  ZuPinYaJinType zuPinYaJinType,String fenQiShu,BigDecimal shouFu,BigDecimal yueGong, ZuPinContactRepayType zuPinContactRepayType, int delayMonth) {
        this.uuid = uuid;

        this.saleManName = saleManName;
        this.moduleName = moduleName;
        this.moduleType = moduleType;
        this.moduleBrand = moduleBrand;
        this.moduleColor = moduleColor;
        this.moduleDianLiang = moduleDianLiang;
        this.rentMonth = rentMonth;
        this.vehicleNum = vehicleNum;
        this.tiChePiCi = tiChePiCi;
        this.tiCheDate = tiCheDate;
        this.nextFeeDate = nextFeeDate;
        this.zuPinContactUuid = zuPinContactUuid;
        this.actualRentFee = actualRentFee;
        this.zuPinYaJinType = zuPinYaJinType;
        this.fenQiShu = fenQiShu;
        this.shouFu = shouFu;
        this.yueGong = yueGong;
        this.zuPinContactRepayType = zuPinContactRepayType;
        this.delayMonth = delayMonth;
    }

    /**************************************GERTTER*******************************************/

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getSaleManName() {
        return saleManName;
    }

    public void setSaleManName(String saleManName) {
        this.saleManName = saleManName;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getModuleType() {
        return moduleType;
    }

    public void setModuleType(String moduleType) {
        this.moduleType = moduleType;
    }

    public String getModuleBrand() {
        return moduleBrand;
    }

    public void setModuleBrand(String moduleBrand) {
        this.moduleBrand = moduleBrand;
    }

    public String getModuleColor() {
        return moduleColor;
    }

    public void setModuleColor(String moduleColor) {
        this.moduleColor = moduleColor;
    }

    public String getModuleDianLiang() {
        return moduleDianLiang;
    }

    public void setModuleDianLiang(String moduleDianLiang) {
        this.moduleDianLiang = moduleDianLiang;
    }

    public String getRentMonth() {
        return rentMonth;
    }

    public void setRentMonth(String rentMonth) {
        this.rentMonth = rentMonth;
    }

    public String getVehicleNum() {
        return vehicleNum;
    }

    public void setVehicleNum(String vehicleNum) {
        this.vehicleNum = vehicleNum;
    }

    public int getTiChePiCi() {
        return tiChePiCi;
    }

    public void setTiChePiCi(int tiChePiCi) {
        this.tiChePiCi = tiChePiCi;
    }

    public String getTiCheDate() {
        return tiCheDate;
    }

    public void setTiCheDate(String tiCheDate) {
        this.tiCheDate = tiCheDate;
    }

    public String getNextFeeDate() {
        return nextFeeDate;
    }

    public void setNextFeeDate(String nextFeeDate) {
        this.nextFeeDate = nextFeeDate;
    }

    public String getZuPinContactUuid() {
        return zuPinContactUuid;
    }

    public void setZuPinContactUuid(String zuPinContactUuid) {
        this.zuPinContactUuid = zuPinContactUuid;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCumtomerPhoneNumber() {
        return cumtomerPhoneNumber;
    }

    public void setCumtomerPhoneNumber(String cumtomerPhoneNumber) {
        this.cumtomerPhoneNumber = cumtomerPhoneNumber;
    }

    public String getJieshuDate() {
        return jieshuDate;
    }

    public void setJieshuDate(String jieshuDate) {
        this.jieshuDate = jieshuDate;
    }

    public String getJieshuDateInShow() {
         return new LocalDate(this.jieshuDate).plusDays(-1).toString();
    }

    public void setJieshuDateInShow(String jieshuDateInShow) {
        this.jieshuDateInShow = jieshuDateInShow;
    }

    public boolean isOver() {
        return isOver;
    }

    public void setOver(boolean over) {
        isOver = over;
    }

    public boolean isZupinover() {
        return isOver;
    }

    public void setZupinover(boolean zupinover) {
        this.zupinover = zupinover;
    }

    public boolean isPiciOver() {
        return piciOver;
    }

    public void setPiciOver(boolean piciOver) {
        this.piciOver = piciOver;
    }

    public boolean isOverButNotRevert() {
        return overButNotRevert;
    }

    public void setOverButNotRevert(boolean overButNotRevert) {
        this.overButNotRevert = overButNotRevert;
    }

    public String getActualRentFee() {
        return actualRentFee;
    }

    public void setActualRentFee(String actualRentFee) {
        this.actualRentFee = actualRentFee;
    }

    public String getNextMessageDate() {
        return nextMessageDate;
    }

    public void setNextMessageDate(String nextMessageDate) {
        this.nextMessageDate = nextMessageDate;
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

    public ZuPinContactRepayType getZuPinContactRepayType() {
        return zuPinContactRepayType;
    }

    public void setZuPinContactRepayType(ZuPinContactRepayType zuPinContactRepayType) {
        this.zuPinContactRepayType = zuPinContactRepayType;
    }

    public String getNextFeeDateInShow() {
        return new LocalDate(this.nextFeeDate).plusDays(-1).toString();
    }

    public void setNextFeeDateInShow(String nextFeeDateInShow) {
        this.nextFeeDateInShow = nextFeeDateInShow;
    }

    public LocalDate getActualzujinrepaymonth() {
        return actualzujinrepaymonth;
    }

    public void setActualzujinrepaymonth(LocalDate actualzujinrepaymonth) {
        this.actualzujinrepaymonth = actualzujinrepaymonth;
    }

    public LocalDate getActualyajinrepaymonth() {
        return actualyajinrepaymonth;
    }

    public void setActualyajinrepaymonth(LocalDate actualyajinrepaymonth) {
        this.actualyajinrepaymonth = actualyajinrepaymonth;
    }

    public Integer getYajinHasPayCishu() {
        return yajinHasPayCishu;
    }

    public void setYajinHasPayCishu(Integer yajinHasPayCishu) {
        this.yajinHasPayCishu = yajinHasPayCishu;
    }

    public Integer getDelayMonth() {
        return delayMonth;
    }

    public void setDelayMonth(Integer delayMonth) {
        this.delayMonth = delayMonth;
    }

    public LocalDate getNextYajinDate() {
        return nextYajinDate;
    }

    public void setNextYajinDate(LocalDate nextYajinDate) {
        this.nextYajinDate = nextYajinDate;
    }

    public BigDecimal getSingleYaJin() {
        return singleYaJin;
    }

    public void setSingleYaJin(BigDecimal singleYaJin) {
        this.singleYaJin = singleYaJin;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
