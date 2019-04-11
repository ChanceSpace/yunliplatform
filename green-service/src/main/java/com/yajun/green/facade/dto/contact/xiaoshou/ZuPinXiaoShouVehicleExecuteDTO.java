package com.yajun.green.facade.dto.contact.xiaoshou;

import com.yajun.green.common.security.LoginInfo;
import com.yajun.green.domain.xiaoshou.XiaoShouType;
import com.yajun.green.domain.xiaoshou.XiaoShouContactExecuteStatus;
import com.yajun.green.domain.xiaoshou.XiaoShouVehicleModule;
import org.joda.time.LocalDate;

import java.math.BigDecimal;

/**
 * Created by LiuMengKe on 2017/12/20.
 */
public class ZuPinXiaoShouVehicleExecuteDTO {
    private String uuid;
    //交易人信息
    private String actorManName;
    private String actorManUuid;

    //车型数据和车牌号
    private String moduleName;
    private String moduleType;
    private String moduleBrand;
    private String moduleColor;
    private String moduleDianLiang;
    private String vehicleNum;
    //提车批次
    private int tiChePiCi;
    //提车日期
    private LocalDate tiCheDate;
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
    //按揭最长年限
    private Integer maxAnJieYear;
    //执行状态
    private XiaoShouContactExecuteStatus executeStatus;
    //备注
    private String comment;


    public ZuPinXiaoShouVehicleExecuteDTO() {
        tiCheDate = new LocalDate();
    }

    public ZuPinXiaoShouVehicleExecuteDTO(String uuid, String actorManName, String actorManUuid, String moduleName, String moduleType, String moduleBrand, String moduleColor, String moduleDianLiang,
                                          String vehicleNum, int tiChePiCi, LocalDate tiCheDate, XiaoShouType xiaoShouType, BigDecimal dingJin, BigDecimal salePrice, BigDecimal shouFu, BigDecimal weiKuan, Integer maxAnJieYear, XiaoShouContactExecuteStatus executeStatus, String comment) {
        this.uuid = uuid;
        this.actorManName = actorManName;
        this.actorManUuid = actorManUuid;
        this.moduleName = moduleName;
        this.moduleType = moduleType;
        this.moduleBrand = moduleBrand;
        this.moduleColor = moduleColor;
        this.moduleDianLiang = moduleDianLiang;

        this.vehicleNum = vehicleNum;
        this.tiChePiCi = tiChePiCi;
        this.tiCheDate = tiCheDate;
        this.xiaoShouType = xiaoShouType;
        this.dingJin = dingJin;
        this.salePrice = salePrice;
        this.shouFu = shouFu;
        this.weiKuan = weiKuan;
        this.maxAnJieYear = maxAnJieYear;
        this.executeStatus = executeStatus;
        this.comment = comment;
    }

    /*********************************setter getter*******************************/

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    public String getActorManName() {
        return actorManName;
    }

    public void setActorManName(String actorManName) {
        this.actorManName = actorManName;
    }

    public String getActorManUuid() {
        return actorManUuid;
    }

    public void setActorManUuid(String actorManUuid) {
        this.actorManUuid = actorManUuid;
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

    public LocalDate getTiCheDate() {
        return tiCheDate;
    }

    public void setTiCheDate(LocalDate tiCheDate) {
        this.tiCheDate = tiCheDate;
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

    public XiaoShouContactExecuteStatus getExecuteStatus() {
        return executeStatus;
    }

    public void setExecuteStatus(XiaoShouContactExecuteStatus executeStatus) {
        this.executeStatus = executeStatus;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getMaxAnJieYear() {
        return maxAnJieYear;
    }

    public void setMaxAnJieYear(Integer maxAnJieYear) {
        this.maxAnJieYear = maxAnJieYear;
    }

    public void initExecuteWithModule(XiaoShouVehicleModule module, LoginInfo info) {

        this.actorManName = info.getXingMing();
        this.actorManUuid = info.getUserUuid();

        this.moduleName = module.getModuleName();
        this.moduleType = module.getModuleType();
        this.moduleBrand = module.getModuleType();
        this.moduleColor = module.getModuleColor();

        this.xiaoShouType = module.getXiaoShouType();
        this.dingJin = module.getDingJin();
        this.salePrice = module.getSalePrice();
        this.shouFu = module.getShouFu();
        this.weiKuan = module.getWeiKuan();
        this.maxAnJieYear = module.getMaxAnJieYear();




    }
}
