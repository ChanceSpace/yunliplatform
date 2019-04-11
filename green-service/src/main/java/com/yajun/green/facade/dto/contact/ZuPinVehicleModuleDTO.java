package com.yajun.green.facade.dto.contact;

import com.yajun.green.domain.contact.ZuPinContactBaoYueType;
import com.yajun.green.domain.contact.ZuPinContactRepayType;
import com.yajun.green.domain.contact.ZuPinYaJinType;
import com.yajun.vms.facade.dto.VehicleModuleDTO;

import java.math.BigDecimal;

/**
 * User: Jack Wang
 * Date: 17-8-11
 * Time: 下午5:23
 */
public class ZuPinVehicleModuleDTO {

    private String uuid;
    private String moduleUuid;
    private String moduleName;
    private String moduleType;
    private String moduleBrand;
    private String moduleColor;
    private String moduleDianLiang;
    private String rentNumber;
    private String rentMonth;
    /**这个车型已经提车多少台**/
    private int alreadyTiChe = 0;

    /**********************租金相关**********************/
    /**租金规则**/
    private ZuPinContactRepayType zuPinContactRepayType;
    /**租金延迟月份**/
    private Integer delayMonth;
    /**单月实际租金**/
    private String actualRentFee;

    /**********************押金相关**********************/
    /**押金类型**/
    private ZuPinYaJinType zuPinYaJinType;
    /**押金分期数**/
    private String fenQiShu;
    /**押金首付**/
    private BigDecimal shouFu;
    /**押金月供**/
    private BigDecimal yueGong;
    /**单月押金**/
    private String singleYaJin;

    private ZuPinContactBaoYueType zuPinContactBaoYueType;


    public ZuPinVehicleModuleDTO() {
        rentNumber = "1";
        rentMonth = "12";
    }

    public ZuPinVehicleModuleDTO(String moduleUuid, String moduleName, String moduleType, String moduleBrand, String moduleColor, String moduleDianLiang) {
        this.moduleUuid = moduleUuid;
        this.moduleName = moduleName;
        this.moduleType = moduleType;
        this.moduleBrand = moduleBrand;
        this.moduleColor = moduleColor;
        this.moduleDianLiang = moduleDianLiang;
    }

    public ZuPinVehicleModuleDTO(String uuid, String moduleUuid, String moduleName, String moduleType, String moduleBrand, String moduleColor, String moduleDianLiang,
                                 String rentNumber, String rentMonth, String singleYaJin,  String actualRentFee, ZuPinYaJinType zuPinYaJinType, String fenQiShu, BigDecimal shouFu, BigDecimal yueGong, ZuPinContactRepayType zuPinContactRepayType) {
        this.uuid = uuid;
        this.moduleUuid = moduleUuid;
        this.moduleName = moduleName;
        this.moduleType = moduleType;
        this.moduleBrand = moduleBrand;
        this.moduleColor = moduleColor;
        this.moduleDianLiang = moduleDianLiang;

        this.rentNumber = rentNumber;
        this.rentMonth = rentMonth;
        this.singleYaJin = singleYaJin;
        this.actualRentFee = actualRentFee;
        this.zuPinYaJinType = zuPinYaJinType;
        this.fenQiShu = fenQiShu;
        this.shouFu = shouFu;
        this.yueGong = yueGong;
        this.zuPinContactRepayType = zuPinContactRepayType;
    }

    public void setSelectVehicleModuleInfo(VehicleModuleDTO selectModule, boolean riJie) {
        this.moduleUuid = selectModule.getUuid();
        this.moduleName = selectModule.getModuleName();
        this.moduleType = selectModule.getModuleType();
        this.moduleBrand = selectModule.getModuleBrand();
        this.moduleColor = selectModule.getModuleColor();
        this.moduleDianLiang = selectModule.getModuleDianLiang();

        if (riJie) {
            this.setZuPinContactRepayType(ZuPinContactRepayType.C_AFTER);
            this.setFenQiShu("0");
            this.setYueGong(new BigDecimal(0));
            this.setShouFu(new BigDecimal(0));
            this.setDelayMonth(0);
            this.setZuPinYaJinType(ZuPinYaJinType.Y_QUANKUAN);
            this.setZuPinContactBaoYueType(ZuPinContactBaoYueType.B_RIJIE);

        } else {
            if (ZuPinYaJinType.Y_QUANKUAN.equals(this.getZuPinYaJinType())) {
                this.setFenQiShu("0");
                this.setYueGong(new BigDecimal(0));
                this.setShouFu(new BigDecimal(0));
            }

            if(ZuPinContactRepayType.C_BEFORE.equals(this.getZuPinContactRepayType())){
                this.setDelayMonth(0);
            }
        }
    }

    public ZuPinVehicleModuleDTO(String moduleName, String moduleType, String moduleBrand, String moduleColor, String moduleDianLiang,
                                 String rentNumber, String rentMonth, String actualRentFee,ZuPinYaJinType zuPinYaJinType,String fenQiShu,BigDecimal shouFu,BigDecimal yueGong, ZuPinContactRepayType zuPinContactRepayType) {
        this.moduleName = moduleName;
        this.moduleType = moduleType;
        this.moduleBrand = moduleBrand;
        this.moduleColor = moduleColor;
        this.moduleDianLiang = moduleDianLiang;

        this.rentNumber = rentNumber;
        this.rentMonth = rentMonth;
        this.actualRentFee = actualRentFee;
        this.zuPinYaJinType = zuPinYaJinType;
        this.fenQiShu = fenQiShu;
        this.yueGong = yueGong;
        this.shouFu = shouFu;
        this.zuPinContactRepayType = zuPinContactRepayType;
    }

    /********************************GET/SET****************************************/

    public int getLeftTiCheNumber() {
        return Integer.valueOf(rentNumber) - alreadyTiChe;
    }
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

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

    public String getRentNumber() {
        return rentNumber;
    }

    public void setRentNumber(String rentNumber) {
        this.rentNumber = rentNumber;
    }

    public String getRentMonth() {
        return rentMonth;
    }

    public void setRentMonth(String rentMonth) {
        this.rentMonth = rentMonth;
    }

    public String getSingleYaJin() {
        return singleYaJin;
    }

    public void setSingleYaJin(String singleYaJin) {
        this.singleYaJin = singleYaJin;
    }

    public int getAlreadyTiChe() {
        return alreadyTiChe;
    }

    public void setAlreadyTiChe(int alreadyTiChe) {
        this.alreadyTiChe = alreadyTiChe;
    }

    public String getActualRentFee() {
        return actualRentFee;
    }

    public void setActualRentFee(String actualRentFee) {
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

    public Integer getDelayMonth() {
        return delayMonth;
    }

    public void setDelayMonth(Integer delayMonth) {
        this.delayMonth = delayMonth;
    }

    public ZuPinContactBaoYueType getZuPinContactBaoYueType() {
        return zuPinContactBaoYueType;
    }

    public void setZuPinContactBaoYueType(ZuPinContactBaoYueType zuPinContactBaoYueType) {
        this.zuPinContactBaoYueType = zuPinContactBaoYueType;
    }
}
