package com.yajun.green.facade.dto.contact;

import com.yajun.green.domain.contact.ZuPinContactChargingStatus;

/**
 * Created by chance on 2017/9/22.
 */
public class ZuPinContactChargingDTO {

    private String uuid;
    private String chargingAddress;
    private String zuPinContactChargingStatus;
    private String zuPinContactChargingStatusName;
    private String createTime;
    private String finishTime;

    private String chargingNumber;
    private String zuPinContactChargingType;
    private String zuPinContactChargingTypeName;

    private String actorManUuid;
    private String actorManName;

    public ZuPinContactChargingDTO() {
    }

    public ZuPinContactChargingDTO(String uuid, String chargingAddress, String zuPinContactChargingStatus, String zuPinContactChargingStatusName, String createTime, String finishTime, String chargingNumber, String zuPinContactChargingType, String zuPinContactChargingTypeName) {
        this.uuid = uuid;
        this.chargingAddress = chargingAddress;
        this.zuPinContactChargingStatus = zuPinContactChargingStatus;
        this.zuPinContactChargingStatusName = zuPinContactChargingStatusName;
        this.createTime = createTime;
        this.finishTime = finishTime;
        this.chargingNumber = chargingNumber;
        this.zuPinContactChargingType = zuPinContactChargingType;
        this.zuPinContactChargingTypeName = zuPinContactChargingTypeName;
    }

    public boolean isChargingCanBeEdit(){
        boolean condition = zuPinContactChargingStatus.equals(ZuPinContactChargingStatus.CHARGING_CREATED.name());
        return condition;
    }

    /*****************************************Getter/Setter******************************************/

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getChargingAddress() {
        return chargingAddress;
    }

    public void setChargingAddress(String chargingAddress) {
        this.chargingAddress = chargingAddress;
    }

    public String getZuPinContactChargingStatus() {
        return zuPinContactChargingStatus;
    }

    public void setZuPinContactChargingStatus(String zuPinContactChargingStatus) {
        this.zuPinContactChargingStatus = zuPinContactChargingStatus;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getChargingNumber() {
        return chargingNumber;
    }

    public void setChargingNumber(String chargingNumber) {
        this.chargingNumber = chargingNumber;
    }

    public String getZuPinContactChargingType() {
        return zuPinContactChargingType;
    }

    public void setZuPinContactChargingType(String zuPinContactChargingType) {
        this.zuPinContactChargingType = zuPinContactChargingType;
    }

    public String getZuPinContactChargingStatusName() {
        return zuPinContactChargingStatusName;
    }

    public void setZuPinContactChargingStatusName(String zuPinContactChargingStatusName) {
        this.zuPinContactChargingStatusName = zuPinContactChargingStatusName;
    }

    public String getZuPinContactChargingTypeName() {
        return zuPinContactChargingTypeName;
    }

    public void setZuPinContactChargingTypeName(String zuPinContactChargingTypeName) {
        this.zuPinContactChargingTypeName = zuPinContactChargingTypeName;
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
}
