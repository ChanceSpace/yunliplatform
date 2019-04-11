package com.yajun.green.domain.contact;

import com.yajun.green.common.domain.EntityBase;

import java.math.BigDecimal;

/**
 * User: Jack Wang
 * Date: 17-8-8
 * Time: 上午9:47
 */
public class ZuPinVehicleModule extends EntityBase {

    //车辆合同
    private String moduleUuid;
    private String moduleName;
    private String moduleType;
    private String moduleBrand;
    private String moduleColor;
    private String moduleDianLiang;

    //租用数量
    private int rentNumber;
    private int rentMonth;

    private ZuPinContact zuPinContact;

    /***************************押金相关**************************/
    //押金类型
    private ZuPinYaJinType zuPinYaJinType;
    private BigDecimal singleYaJin;
    //押金分期数
    private String fenQiShu;
    //押金首付
    private BigDecimal shouFu;
    //押金月供
    private BigDecimal yueGong;

    /***************************租金相关*******************************/
    //月租规则
    private ZuPinContactRepayType zuPinContactRepayType;
    private ZuPinContactBaoYueType zuPinContactBaoYueType;
    //实际租金
    private BigDecimal actualRentFee;
    private Integer delayMonth;

    public ZuPinVehicleModule() {
    }

    public ZuPinVehicleModule(String moduleUuid, String moduleName, String moduleType,String moduleColor, String moduleDianLiang, int rentNumber, int rentMonth,BigDecimal actualRentFee, ZuPinYaJinType zuPinYaJinType, String fenQiShu, BigDecimal shouFu, BigDecimal yueGong, ZuPinContactRepayType zuPinContactRepayType) {
        this.moduleUuid = moduleUuid;
        this.moduleName = moduleName;
        this.moduleType = moduleType;
        this.moduleColor = moduleColor;
        this.moduleDianLiang = moduleDianLiang;
        this.rentNumber = rentNumber;
        this.rentMonth = rentMonth;
        this.actualRentFee = actualRentFee;
        this.zuPinYaJinType = zuPinYaJinType;
        this.fenQiShu = fenQiShu;
        this.shouFu = shouFu;
        this.yueGong = yueGong;
        this.zuPinContactRepayType = zuPinContactRepayType;
    }

    /******************************GETTER/SETTER**********************************/

    public String getModuleUuid() {
        return moduleUuid;
    }

    public void setModuleUuid(String moduleUuid) {
        this.moduleUuid = moduleUuid;
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

    public int getRentNumber() {
        return rentNumber;
    }

    public void setRentNumber(int rentNumber) {
        this.rentNumber = rentNumber;
    }

    public int getRentMonth() {
        return rentMonth;
    }

    public void setRentMonth(int rentMonth) {
        this.rentMonth = rentMonth;
    }

    public ZuPinContact getZuPinContact() {
        return zuPinContact;
    }

    public void setZuPinContact(ZuPinContact zuPinContact) {
        this.zuPinContact = zuPinContact;
    }

    public BigDecimal getSingleYaJin() {
        return singleYaJin;
    }

    public void setSingleYaJin(BigDecimal singleYaJin) {
        this.singleYaJin = singleYaJin;
    }

    public BigDecimal getActualRentFee() {
        return actualRentFee;
    }

    public void setActualRentFee(BigDecimal actualRentFee) {
        this.actualRentFee = actualRentFee;
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

    public ZuPinContactBaoYueType getZuPinContactBaoYueType() {
        return zuPinContactBaoYueType;
    }

    public void setZuPinContactBaoYueType(ZuPinContactBaoYueType zuPinContactBaoYueType) {
        this.zuPinContactBaoYueType = zuPinContactBaoYueType;
    }

    public Integer getDelayMonth() {
        return delayMonth;
    }

    public void setDelayMonth(Integer delayMonth) {
        this.delayMonth = delayMonth;
    }
}
