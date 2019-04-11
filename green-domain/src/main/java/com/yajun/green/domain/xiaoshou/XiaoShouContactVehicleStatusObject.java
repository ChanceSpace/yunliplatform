package com.yajun.green.domain.xiaoshou;

import org.joda.time.LocalDate;

import java.math.BigDecimal;

/**
 * Created by LiuMengKe on 2017/12/27.
 * 销售合同车辆状态
 */
public class XiaoShouContactVehicleStatusObject {

    private Integer pici;

    private String vehicleNumber;
    //全款 按揭
    private String xiaoShouType;

    //单车售价 = 首付+尾款
    private BigDecimal salePrice;
    //定金
    private BigDecimal dingJin;
    private XiaoShouPayOrderStatus djStatus;
    //首付
    private BigDecimal shouFu;
    private XiaoShouPayOrderStatus sfStatus;
    //尾款
    private BigDecimal weiKuan;
    private XiaoShouPayOrderStatus wkStatus;
    //按揭最长年限
    private Integer maxAnJieYear;
    private LocalDate tiCheDate;

    private String comment;
    private String executeUuid;

    public XiaoShouContactVehicleStatusObject() {
    }
    public XiaoShouContactVehicleStatusObject(XiaoShouVehicleModule module) {
        this.dingJin = module.getDingJin();
        this.shouFu = module.getShouFu();
        this.weiKuan = module.getWeiKuan();
        this.salePrice = module.getSalePrice();
        this.maxAnJieYear = module.getMaxAnJieYear();
        this.xiaoShouType = module.getXiaoShouType().getDescription();
    }

    public XiaoShouContactVehicleStatusObject(String xiaoShouType, BigDecimal salePrice, BigDecimal dingJin, BigDecimal shouFu, BigDecimal weiKuan, Integer maxAnJieYear) {
        this.xiaoShouType = xiaoShouType;
        this.salePrice = salePrice;
        this.dingJin = dingJin;
        this.shouFu = shouFu;
        this.weiKuan = weiKuan;
        this.maxAnJieYear = maxAnJieYear;
    }

    /*********************************setter getter*******************************/
    public Integer getPici() {
        return pici;
    }

    public void setPici(Integer pici) {
        this.pici = pici;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getXiaoShouType() {
        return xiaoShouType;
    }

    public void setXiaoShouType(String xiaoShouType) {
        this.xiaoShouType = xiaoShouType;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public BigDecimal getDingJin() {
        return dingJin;
    }

    public void setDingJin(BigDecimal dingJin) {
        this.dingJin = dingJin;
    }

    public BigDecimal getShouFu() {
        return shouFu;
    }

    public void setShouFu(BigDecimal shouFu) {
        this.shouFu = shouFu;
    }

    public BigDecimal getWeiKuan() {
        return weiKuan;
    }

    public void setWeiKuan(BigDecimal weiKuan) {
        this.weiKuan = weiKuan;
    }

    public XiaoShouPayOrderStatus getDjStatus() {
        return djStatus;
    }

    public void setDjStatus(XiaoShouPayOrderStatus djStatus) {
        this.djStatus = djStatus;
    }

    public XiaoShouPayOrderStatus getSfStatus() {
        return sfStatus;
    }

    public void setSfStatus(XiaoShouPayOrderStatus sfStatus) {
        this.sfStatus = sfStatus;
    }

    public XiaoShouPayOrderStatus getWkStatus() {
        return wkStatus;
    }

    public void setWkStatus(XiaoShouPayOrderStatus wkStatus) {
        this.wkStatus = wkStatus;
    }

    public Integer getMaxAnJieYear() {
        return maxAnJieYear;
    }

    public void setMaxAnJieYear(Integer maxAnJieYear) {
        this.maxAnJieYear = maxAnJieYear;
    }

    public LocalDate getTiCheDate() {
        return tiCheDate;
    }

    public void setTiCheDate(LocalDate tiCheDate) {
        this.tiCheDate = tiCheDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getExecuteUuid() {
        return executeUuid;
    }

    public void setExecuteUuid(String executeUuid) {
        this.executeUuid = executeUuid;
    }
}
