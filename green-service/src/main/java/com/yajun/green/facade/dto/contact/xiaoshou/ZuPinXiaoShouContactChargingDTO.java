package com.yajun.green.facade.dto.contact.xiaoshou;

import com.yajun.green.domain.contact.ZuPinContactChargingStatus;

/**
 * Created by chance on 2017/9/22.
 */
public class ZuPinXiaoShouContactChargingDTO {

    private String uuid;
    private String chargingAddress;
    private String xiaoShouContactChargingStatus;
    private String xiaoShouContactChargingStatusName;
    private String createTime;
    private String finishTime;

    private String chargingNumber;
    private String xiaoShouContactChargingType;
    private String xiaoShouContactChargingTypeName;

    private String actorManUuid;
    private String actorManName;

    private String contactUuid;

    public ZuPinXiaoShouContactChargingDTO() {
    }

    public ZuPinXiaoShouContactChargingDTO(String uuid, String chargingAddress, String xiaoShouContactChargingStatus, String xiaoShouContactChargingStatusName, String createTime, String finishTime, String chargingNumber, String xiaoShouContactChargingType, String xiaoShouContactChargingTypeName,String contactUuid) {
        this.uuid = uuid;
        this.chargingAddress = chargingAddress;
        this.xiaoShouContactChargingStatus = xiaoShouContactChargingStatus;
        this.xiaoShouContactChargingStatusName = xiaoShouContactChargingStatusName;
        this.createTime = createTime;
        this.finishTime = finishTime;
        this.chargingNumber = chargingNumber;
        this.xiaoShouContactChargingType = xiaoShouContactChargingType;
        this.xiaoShouContactChargingTypeName = xiaoShouContactChargingTypeName;
        this.contactUuid = contactUuid;
    }

    public boolean isChargingCanBeEdit(){
        boolean condition = xiaoShouContactChargingStatus.equals(ZuPinContactChargingStatus.CHARGING_CREATED.name());
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

    public String getXiaoShouContactChargingStatus() {
        return xiaoShouContactChargingStatus;
    }

    public void setXiaoShouContactChargingStatus(String xiaoShouContactChargingStatus) {
        this.xiaoShouContactChargingStatus = xiaoShouContactChargingStatus;
    }

    public String getXiaoShouContactChargingStatusName() {
        return xiaoShouContactChargingStatusName;
    }

    public void setXiaoShouContactChargingStatusName(String xiaoShouContactChargingStatusName) {
        this.xiaoShouContactChargingStatusName = xiaoShouContactChargingStatusName;
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

    public String getXiaoShouContactChargingType() {
        return xiaoShouContactChargingType;
    }

    public void setXiaoShouContactChargingType(String xiaoShouContactChargingType) {
        this.xiaoShouContactChargingType = xiaoShouContactChargingType;
    }

    public String getXiaoShouContactChargingTypeName() {
        return xiaoShouContactChargingTypeName;
    }

    public void setXiaoShouContactChargingTypeName(String xiaoShouContactChargingTypeName) {
        this.xiaoShouContactChargingTypeName = xiaoShouContactChargingTypeName;
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
}
