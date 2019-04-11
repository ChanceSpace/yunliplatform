package com.yajun.green.domain.xiaoshou;

import com.yajun.green.common.domain.EntityBase;

import java.math.BigDecimal;

/**
 * User: Jack Wang
 * Date: 17-8-8
 * Time: 上午9:47
 *
 * 销售合同 销售相关信息封装
 * 合同类型 全款？按揭
 * 定金
 * 全款金额 全款金额
 * 按揭方式 首付金额 尾款金额
 */
public class XiaoShouVehicleModule extends EntityBase {

    //和合同一对一
    private XiaoShouContact xiaoShouContact;

    //车辆合同
    private String moduleUuid;
    private String moduleName;
    private String moduleType;
    private String moduleBrand;
    private String moduleColor;
    private String moduleDianLiang;

    //类型 全款 按揭
    private XiaoShouType xiaoShouType;
    //出售数量
    private int saleNumber;
    //定金
    private BigDecimal dingJin;
    //单车售价 = 首付+尾款
    private BigDecimal salePrice;
    //首付
    private BigDecimal shouFu;
    //尾款
    private BigDecimal weiKuan;
    //按揭最长年限
    private Integer maxAnJieYear;

    public XiaoShouVehicleModule() {
    }

    public XiaoShouVehicleModule(String moduleUuid, String moduleName, String moduleType, String moduleBrand, String moduleColor, String moduleDianLiang,
                                 int saleNumber, XiaoShouType xiaoShouType, BigDecimal dingJin, BigDecimal salePrice, BigDecimal shouFu, BigDecimal weiKuan, Integer maxAnJieYear) {
        this.moduleUuid = moduleUuid;
        this.moduleName = moduleName;
        this.moduleType = moduleType;
        this.moduleBrand = moduleBrand;
        this.moduleColor = moduleColor;
        this.moduleDianLiang = moduleDianLiang;

        this.saleNumber = saleNumber;
        this.xiaoShouType = xiaoShouType;
        this.dingJin = dingJin;
        this.salePrice = salePrice;
        this.shouFu = shouFu;
        this.weiKuan = weiKuan;
        this.maxAnJieYear = maxAnJieYear;
    }

    /*********************************setter getter*******************************/

    public XiaoShouContact getXiaoShouContact() {
        return xiaoShouContact;
    }

    public void setXiaoShouContact(XiaoShouContact xiaoShouContact) {
        this.xiaoShouContact = xiaoShouContact;
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

    public int getSaleNumber() {
        return saleNumber;
    }

    public void setSaleNumber(int saleNumber) {
        this.saleNumber = saleNumber;
    }

    public XiaoShouType getXiaoShouType() {
        return xiaoShouType;
    }

    public void setXiaoShouType(XiaoShouType xiaoShouType) {
        this.xiaoShouType = xiaoShouType;
    }

    public BigDecimal getDingJin() {
        return dingJin;
    }

    public void setDingJin(BigDecimal dingJin) {
        this.dingJin = dingJin;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
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

    public Integer getMaxAnJieYear() {
        return maxAnJieYear;
    }

    public void setMaxAnJieYear(Integer maxAnJieYear) {
        this.maxAnJieYear = maxAnJieYear;
    }

}
