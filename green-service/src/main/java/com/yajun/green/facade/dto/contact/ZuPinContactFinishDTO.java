package com.yajun.green.facade.dto.contact;

import java.math.BigDecimal;

/**
 * User: Jack Wang
 * Date: 17-9-4
 * Time: 下午4:46
 */
public class ZuPinContactFinishDTO {

    private String uuid;
    private String finishDate;
    private String finishType;
    private String finishTypeName;
    private String finishCharge;
    private String finishChargeName;
    private String finishNumber;
    private BigDecimal finishFee;
    private String finishNote;

    private String actorUuid;
    private String actorName;

    public ZuPinContactFinishDTO(String uuid, String finishDate, String finishType, String finishTypeName, String finishCharge, String finishChargeName,
                                 String finishNumber, BigDecimal finishFee, String finishNote, String actorUuid, String actorName) {
        this.uuid = uuid;
        this.finishDate = finishDate;
        this.finishType = finishType;
        this.finishTypeName = finishTypeName;
        this.finishCharge = finishCharge;
        this.finishChargeName = finishChargeName;
        this.finishNumber = finishNumber;
        this.finishFee = finishFee;
        this.finishNote = finishNote;
        this.actorUuid = actorUuid;
        this.actorName = actorName;
    }

    /***************************************GET/SETTER*********************************************/

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public String getFinishType() {
        return finishType;
    }

    public void setFinishType(String finishType) {
        this.finishType = finishType;
    }

    public String getFinishTypeName() {
        return finishTypeName;
    }

    public void setFinishTypeName(String finishTypeName) {
        this.finishTypeName = finishTypeName;
    }

    public String getFinishNumber() {
        return finishNumber;
    }

    public void setFinishNumber(String finishNumber) {
        this.finishNumber = finishNumber;
    }

    public BigDecimal getFinishFee() {
        return finishFee;
    }

    public void setFinishFee(BigDecimal finishFee) {
        this.finishFee = finishFee;
    }

    public String getFinishCharge() {
        return finishCharge;
    }

    public void setFinishCharge(String finishCharge) {
        this.finishCharge = finishCharge;
    }

    public String getFinishChargeName() {
        return finishChargeName;
    }

    public void setFinishChargeName(String finishChargeName) {
        this.finishChargeName = finishChargeName;
    }

    public String getFinishNote() {
        return finishNote;
    }

    public void setFinishNote(String finishNote) {
        this.finishNote = finishNote;
    }

    public String getActorUuid() {
        return actorUuid;
    }

    public void setActorUuid(String actorUuid) {
        this.actorUuid = actorUuid;
    }

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }
}
