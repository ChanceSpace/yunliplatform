package com.yajun.green.domain.xiaoshou;

import com.yajun.green.common.domain.EntityBase;
import com.yajun.green.common.security.LoginInfo;
import org.joda.time.LocalDate;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;

/**
 * User: Jack Wang
 * Date: 17-8-8
 * Time: 上午11:21
 *
 *  销售合同 提车后 记录莫批次某辆车 的执行情况
 *  在合同开始执行后 自动产生 对应数目（车的数量 * 每辆的定金 ）的定金
 *  提车的时候产生 （首付-定金） 每辆
 *  完成的时候产生尾款 那么多的账单
 *  缴纳完成后该批次结束
 *
 */
public class XiaoShouVehicleExecute extends EntityBase {

    //交易人信息
    private String actorManName;
    private String actorManUuid;

    //车型数据和车牌号
    private String moduleName;
    private String moduleType;
    private String moduleBrand;
    private String moduleColor;
    private String moduleDianLiang;

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

    private String vehicleNum;
    //提车批次
    private int tiChePiCi;
    //提车日期
    private LocalDate tiCheDate;
    //所属合同
    private XiaoShouContact xiaoShouContact;
    //类型 全款 按揭
    XiaoShouType xiaoShouType;

    //执行状态
    private XiaoShouContactExecuteStatus executeStatus;

    //备注
    private String comment;

    public XiaoShouVehicleExecute() {
    }

    public XiaoShouVehicleExecute(LoginInfo loginInfo, XiaoShouVehicleModule module, String vehicleNumber, int tiChePiCi, LocalDate tiCheDate) {
        if (loginInfo != null) {
            this.actorManUuid = loginInfo.getUserUuid();
            this.actorManName = loginInfo.getXingMing();
        } else {
            this.actorManUuid = "测试交易人";
            this.actorManName = "testId";
        }
        this.moduleName = module.getModuleName();
        this.moduleType = module.getModuleType();
        this.moduleBrand = module.getModuleBrand();
        this.moduleColor = module.getModuleColor();
        this.moduleDianLiang = module.getModuleDianLiang();
        this.dingJin = module.getDingJin();
        this.salePrice = module.getSalePrice();
        this.shouFu = module.getShouFu();
        this.weiKuan = module.getWeiKuan();
        this.maxAnJieYear = module.getMaxAnJieYear();
        this.xiaoShouType = module.getXiaoShouType();

        this.vehicleNum = vehicleNumber;
        this.tiCheDate = tiCheDate;
        this.tiChePiCi = tiChePiCi;
    }

    /**********************************设置备注******************************/

    public void updateComment(String date,String incomment){
        if(StringUtils.hasText(incomment)){
            StringBuilder builder = new StringBuilder();
            if(StringUtils.hasText(this.comment)){
                builder.append(this.comment);
            }
            builder.append(date+": "+incomment);
            setComment(builder.toString()+"@x@x@");
        }
    }

    /**************************************GERTTER*******************************************/

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

    public XiaoShouContact getXiaoShouContact() {
        return xiaoShouContact;
    }

    public void setXiaoShouContact(XiaoShouContact xiaoShouContact) {
        this.xiaoShouContact = xiaoShouContact;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public XiaoShouContactExecuteStatus getExecuteStatus() {
        return executeStatus;
    }

    public void setExecuteStatus(XiaoShouContactExecuteStatus executeStatus) {
        this.executeStatus = executeStatus;
    }

    public Integer getMaxAnJieYear() {
        return maxAnJieYear;
    }

    public void setMaxAnJieYear(Integer maxAnJieYear) {
        this.maxAnJieYear = maxAnJieYear;
    }
}
