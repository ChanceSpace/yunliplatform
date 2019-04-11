package com.yajun.green.domain.contact;

import com.yajun.green.common.domain.EntityBase;
import com.yajun.green.common.domain.SubEntityBase;
import com.yajun.green.common.security.LoginInfo;
import org.joda.time.DateTime;

/**
 * Created by chance on 2017/9/22.
 */
public class ZuPinContactCharging  extends EntityBase implements Comparable<ZuPinContactCharging>{

    //充电桩地址
    private String chargingAddress;
    //充电桩状态
    private ZuPinContactChargingStatus zuPinContactChargingStatus;

    private DateTime createTime;
    private DateTime finishTime;

    private String companyUuid;
    private String companyName;
    //充电桩号码
    private String chargingNumber;
    //充电桩类型
    private ZuPinContactChargingType zuPinContactChargingType;
    //操作人id
    private String actorManUuid;
    //操作人名字
    private String actorManName;

    public ZuPinContactCharging() {
    }

    public ZuPinContactCharging(String chargingAddress, String yiFangUuid, String yiFangName) {
        this.chargingAddress = chargingAddress;
        this.companyUuid = yiFangUuid;
        this.companyName = yiFangName;
        this.createTime = new DateTime();
        this.finishTime = new DateTime();
        this.zuPinContactChargingStatus = ZuPinContactChargingStatus.CHARGING_CREATED;
    }

    public String getChargingAddress() {
        return chargingAddress;
    }

    public void setChargingAddress(String chargingAddress) {
        this.chargingAddress = chargingAddress;
    }

    public ZuPinContactChargingStatus getZuPinContactChargingStatus() {
        return zuPinContactChargingStatus;
    }

    public void setZuPinContactChargingStatus(ZuPinContactChargingStatus zuPinContactChargingStatus) {
        this.zuPinContactChargingStatus = zuPinContactChargingStatus;
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

    public ZuPinContactChargingType getZuPinContactChargingType() {
        return zuPinContactChargingType;
    }

    public void setZuPinContactChargingType(ZuPinContactChargingType zuPinContactChargingType) {
        this.zuPinContactChargingType = zuPinContactChargingType;
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


    @Override
    public int compareTo(ZuPinContactCharging o) {
        return this.getUuid().compareTo(o.getUuid());
    }
}
