package com.yajun.green.facade.dto.contact.xiaoshou;

import com.yajun.green.domain.xiaoshou.XiaoShouType;
import com.yajun.green.facade.dto.contact.ZuPinVehicleModuleDTO;
import com.yajun.vms.facade.dto.VehicleModuleDTO;

import java.math.BigDecimal;

/**
 * Created by LiuMengKe on 2017/12/20.
 */
public class ZuPinXiaoShouVehicleModuleDTO {

    private String uuid;
    //车辆合同

    private String moduleUuid;
    private String moduleName;
    private String moduleType;
    private String moduleBrand;
    private String moduleColor;
    private String moduleDianLiang;

    /**这个车型已经提车多少台**/
    private int alreadyTiChe = 0;

    //出售数量
    private int saleNumber;

    //类型 全款 按揭
    XiaoShouType xiaoShouType;

    //定金
    private BigDecimal dingJin;

    //单车售价 = 首付+尾款
    private BigDecimal salePrice;
    //首付
    private BigDecimal shouFu;
    //尾款
    private BigDecimal weiKuan;

    private Integer maxAnJieYear;

    public ZuPinXiaoShouVehicleModuleDTO() {
    }

    public ZuPinXiaoShouVehicleModuleDTO(String uuid, String moduleUuid, String moduleName, String moduleType, String moduleBrand, String moduleColor, String moduleDianLiang,
                                         int saleNumber, XiaoShouType xiaoShouType, BigDecimal dingJin, BigDecimal salePrice, BigDecimal shouFu, BigDecimal weiKuan, Integer maxAnJieYear) {
        this.uuid = uuid;
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

    public void setSelectVehicleModuleInfo(VehicleModuleDTO selectModule) {
        this.moduleUuid = selectModule.getUuid();
        this.moduleName = selectModule.getModuleName();
        this.moduleType = selectModule.getModuleType();
        this.moduleBrand = selectModule.getModuleBrand();
        this.moduleColor = selectModule.getModuleColor();
        this.moduleDianLiang = selectModule.getModuleDianLiang();

        if (XiaoShouType.XS_QK.equals(this.getXiaoShouType())) {
            this.setShouFu(BigDecimal.ZERO);
            this.setWeiKuan(this.getSalePrice().subtract(this.getDingJin()));
            this.setMaxAnJieYear(0);
        }
        if (XiaoShouType.XS_ANJIE.equals(this.getXiaoShouType())) {
            this.setWeiKuan(this.getSalePrice().subtract(this.getDingJin()).subtract(this.getShouFu()));
        }
    }

    /*********************************setter getter*******************************/

    public int getLeftTiCheNumber() {
        return Integer.valueOf(saleNumber) - alreadyTiChe;
    }

    public int getAlreadyTiChe() {
        return alreadyTiChe;
    }

    public void setAlreadyTiChe(int alreadyTiChe) {
        this.alreadyTiChe = alreadyTiChe;
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
