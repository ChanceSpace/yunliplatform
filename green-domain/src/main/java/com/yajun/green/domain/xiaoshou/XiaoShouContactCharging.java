package com.yajun.green.domain.xiaoshou;

import com.yajun.green.common.domain.EntityBase;
import org.joda.time.DateTime;

/**
 * Created by chance on 2017/9/22.
 */
public class XiaoShouContactCharging extends EntityBase implements Comparable<XiaoShouContactCharging>{

    //充电桩地址
    private String chargingAddress;
    //充电桩状态
    private XiaoShouContactChargingStatus zuPinXiaoShouContactChargingStatus;

    private DateTime createTime;
    private DateTime finishTime;

    private String companyUuid;
    private String companyName;
    //充电桩号码
    private String chargingNumber;
    //充电桩类型
    private XiaoShouContactChargingType xiaoShouContactChargingType;
    //操作人id
    private String actorManUuid;
    //操作人名字
    private String actorManName;

    private String contactUuid;

    public XiaoShouContactCharging() {
    }

    public XiaoShouContactCharging(String chargingAddress, String yiFangUuid, String yiFangName) {
        this.chargingAddress = chargingAddress;
        this.companyUuid = yiFangUuid;
        this.companyName = yiFangName;
        this.createTime = new DateTime();
        this.finishTime = new DateTime();
        this.zuPinXiaoShouContactChargingStatus = XiaoShouContactChargingStatus.CHARGING_CREATED;
    }

    public String getChargingAddress() {
        return chargingAddress;
    }

    public void setChargingAddress(String chargingAddress) {
        this.chargingAddress = chargingAddress;
    }

    public XiaoShouContactChargingStatus getZuPinXiaoShouContactChargingStatus() {
        return zuPinXiaoShouContactChargingStatus;
    }

    public void setZuPinXiaoShouContactChargingStatus(XiaoShouContactChargingStatus zuPinXiaoShouContactChargingStatus) {
        this.zuPinXiaoShouContactChargingStatus = zuPinXiaoShouContactChargingStatus;
    }

    public String getCompanyUuid() {
        return companyUuid;
    }

    public void setCompanyUuid(String companyUuid) {
        this.companyUuid = companyUuid;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public DateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(DateTime createTime) {
        this.createTime = createTime;
    }

    public DateTime getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(DateTime finishTime) {
        this.finishTime = finishTime;
    }

    public String getChargingNumber() {
        return chargingNumber;
    }

    public void setChargingNumber(String chargingNumber) {
        this.chargingNumber = chargingNumber;
    }

    public XiaoShouContactChargingType getXiaoShouContactChargingType() {
        return xiaoShouContactChargingType;
    }

    public void setXiaoShouContactChargingType(XiaoShouContactChargingType xiaoShouContactChargingType) {
        this.xiaoShouContactChargingType = xiaoShouContactChargingType;
    }

    public String getActorManUuid() {
        return actorManUuid;
    }

    public void setActorManUuid(String actorManUuid) {
        this.actorManUuid = actorManUuid;
    }

    public String getActorManName() {
        return actorManName;
    }

    public void setActorManName(String actorManName) {
        this.actorManName = actorManName;
    }

    public String getContactUuid() {
        return contactUuid;
    }

    public void setContactUuid(String contactUuid) {
        this.contactUuid = contactUuid;
    }

    @Override
    public int compareTo(XiaoShouContactCharging o) {
        return this.getUuid().compareTo(o.getUuid());
    }


}
