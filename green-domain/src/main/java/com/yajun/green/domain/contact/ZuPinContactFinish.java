package com.yajun.green.domain.contact;

import com.yajun.green.common.domain.EntityBase;

import org.joda.time.LocalDate;

import java.math.BigDecimal;

/**
 * User: Jack Wang
 * Date: 17-9-4
 * Time: 下午4:46
 *
 * 单量车和整个合同结束时的费用结算
 */
public class ZuPinContactFinish extends EntityBase {

    private LocalDate finishDate;
    private ZuPinContactFinishType finishType;
    private ZuPinContactFinishCharge finishCharge;
    private String finishNumber;
    private BigDecimal finishFee;
    private String finishNote;

    private String actorUuid;
    private String actorName;

    private ZuPinContact zuPinContact;
    private ZuPinVehicleExecute vehicleExecute;

    /***************************************GET/SETTER*********************************************/

    public LocalDate getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(LocalDate finishDate) {
        this.finishDate = finishDate;
    }

    public ZuPinContactFinishType getFinishType() {
        return finishType;
    }

    public void setFinishType(ZuPinContactFinishType finishType) {
        this.finishType = finishType;
    }

    public ZuPinContactFinishCharge getFinishCharge() {
        return finishCharge;
    }

    public void setFinishCharge(ZuPinContactFinishCharge finishCharge) {
        this.finishCharge = finishCharge;
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

    public ZuPinContact getZuPinContact() {
        return zuPinContact;
    }

    public void setZuPinContact(ZuPinContact zuPinContact) {
        this.zuPinContact = zuPinContact;
    }

    public ZuPinVehicleExecute getVehicleExecute() {
        return vehicleExecute;
    }

    public void setVehicleExecute(ZuPinVehicleExecute vehicleExecute) {
        this.vehicleExecute = vehicleExecute;
    }
}
