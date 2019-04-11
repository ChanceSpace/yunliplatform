package com.yajun.green.domain.contact;

import com.yajun.green.common.domain.EntityBase;
import com.yajun.green.common.security.LoginInfo;
import org.joda.time.LocalDate;
import org.springframework.util.StringUtils;
import java.math.BigDecimal;

/**
 * User: Jack Wang
 * Date: 17-8-8
 * Time: 上午11:21
 */
public class ZuPinVehicleExecute extends EntityBase {

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
    //提车结束时间 为提车时间+合同期限
    private LocalDate jieshuDate;
    //车辆租期是否到期 提前结束车辆租用 短信 换车等功能使用
    private boolean isOver;
    private boolean piciOver;
    //自动扣费结束合同但是车辆没有被归还
    private boolean overButNotRevert;

    //所属合同
    private ZuPinContact zuPinContact;
    //租车信息
    private int rentMonth;
    //新增实际租金
    private BigDecimal actualRentFee;
    //下次租金到期时间
    private LocalDate nextFeeDate;
    //下次短信提示时间
    private LocalDate nextMessageDate;
    //具体缴纳的是第那个月的租金
    private LocalDate actualzujinrepaymonth;
    //新增月租规则
    private ZuPinContactRepayType zuPinContactRepayType;

    private ZuPinContactBaoYueType zuPinContactBaoYueType;

    //延迟月份
    private Integer delayMonth;
    //备注
    private String comment;

    //押金类型
    private ZuPinYaJinType zuPinYaJinType;
    //押金分期数
    private String fenQiShu;
    //押金首付
    private BigDecimal shouFu;
    //押金月供
    private BigDecimal yueGong;
    //押金每台
    private BigDecimal singleYaJin;
    //押金已经偿还次数
    private Integer yajinHasPayCishu;
    //下次押金偿还时间
    private LocalDate nextYajinDate;
    //具体哪个月的押金
    private LocalDate actualyajinrepaymonth;


    public ZuPinVehicleExecute() {
    }

    public ZuPinVehicleExecute(LoginInfo loginInfo, ZuPinVehicleModule module, String vehicleNumber, int tiChePiCi, String tiCheDate, String nextFeeDate) {
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
        this.vehicleNum = vehicleNumber;

        //租车信息
        this.rentMonth = module.getRentMonth();

        //提车批次
        this.tiChePiCi = tiChePiCi;
        this.tiCheDate = new LocalDate(tiCheDate);
        this.nextFeeDate = new LocalDate(nextFeeDate);
        //实际租金
        this.actualRentFee = module.getActualRentFee();
        //新增押金规则
        this.zuPinYaJinType = module.getZuPinYaJinType();
        this.shouFu = module.getShouFu();
        this.yueGong = module.getYueGong();
        this.fenQiShu = module.getFenQiShu();
        //新增租金规则
        this.zuPinContactRepayType = module.getZuPinContactRepayType();
        this.singleYaJin = module.getSingleYaJin();
        this.delayMonth = module.getDelayMonth();
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

    /********************************获取当前execute 的缴费日期到 传入日期之间的 间隔月份数 不足一月按 一月算 刚好一月 按两个月算*******************************/
    public int getIntervalMonthFromActualZjDate(LocalDate jieshuDate){
        LocalDate actualFeeDate = getActualzujinrepaymonth();
        int actualFeeDateYear = actualFeeDate.getYear();
        int jieshuYear = jieshuDate.getYear();

        int actualFeeDateMonth = actualFeeDate.getMonthOfYear();
        int jieshuMonth = jieshuDate.getMonthOfYear();

        int actualDay = actualFeeDate.getDayOfMonth();
        int jieshuDay = jieshuDate.getDayOfMonth();

        //计算间隔月份数 当天按一个月计算
        int fujia = jieshuDay>=actualDay?1:0;
        int jiangeMonth = (jieshuYear-actualFeeDateYear)*12+(jieshuMonth-actualFeeDateMonth)+fujia;

        return jiangeMonth;
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

    public int getRentMonth() {
        return rentMonth;
    }

    public void setRentMonth(int rentMonth) {
        this.rentMonth = rentMonth;
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

    public LocalDate getNextFeeDate() {
        return nextFeeDate;
    }

    public void setNextFeeDate(LocalDate nextFeeDate) {
        this.nextFeeDate = nextFeeDate;
    }

    public ZuPinContact getZuPinContact() {
        return zuPinContact;
    }

    public void setZuPinContact(ZuPinContact zuPinContact) {
        this.zuPinContact = zuPinContact;
    }

    public LocalDate getJieshuDate() {
        return jieshuDate;
    }

    public void setJieshuDate(LocalDate jieshuDate) {
        this.jieshuDate = jieshuDate;
    }

    public boolean isOver() {
        return isOver;
    }

    public void setOver(boolean over) {
        isOver = over;
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

    public BigDecimal getActualRentFee() {
        return actualRentFee;
    }

    public void setActualRentFee(BigDecimal actualRentFee) {
        this.actualRentFee = actualRentFee;
    }

    public LocalDate getNextMessageDate() {
        return nextMessageDate;
    }

    public void setNextMessageDate(LocalDate nextMessageDate) {
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
