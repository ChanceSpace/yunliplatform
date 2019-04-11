package com.yajun.green.facade.dto.contact;

import com.yajun.green.domain.contact.ZuPinContact;
import com.yajun.green.domain.contact.ZuPinVehicleExecute;

/**
 * Created by chance on 2017/8/21.
 */
public class VehicleChangeHistoryDTO {
    private String uuid;

    private String vehicleNumberBefore;
    private String vehicleNumberNow;
    private ZuPinVehicleExecute zuPinVehicleExecute;
    private String caoZuoRen;
    private String caoZuoRenUuid;
    private String changeDate;
    private ZuPinContact zuPinContact;

    public VehicleChangeHistoryDTO() {
    }

    public VehicleChangeHistoryDTO(String uuid, String vehicleNumberBefore, String vehicleNumberNow, ZuPinVehicleExecute zuPinVehicleExecute, String caoZuoRen, String caoZuoRenUuid, String changeDate, ZuPinContact zuPinContact) {
        this.uuid = uuid;
        this.vehicleNumberBefore = vehicleNumberBefore;
        this.vehicleNumberNow = vehicleNumberNow;
        this.zuPinVehicleExecute = zuPinVehicleExecute;
        this.caoZuoRen = caoZuoRen;
        this.caoZuoRenUuid = caoZuoRenUuid;
        this.changeDate = changeDate;
        this.zuPinContact = zuPinContact;
    }

    /******************************************GETTER/SETTER****************************************/
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getVehicleNumberBefore() {
        return vehicleNumberBefore;
    }

    public void setVehicleNumberBefore(String vehicleNumberBefore) {
        this.vehicleNumberBefore = vehicleNumberBefore;
    }

    public String getVehicleNumberNow() {
        return vehicleNumberNow;
    }

    public void setVehicleNumberNow(String vehicleNumberNow) {
        this.vehicleNumberNow = vehicleNumberNow;
    }

    public ZuPinVehicleExecute getZuPinVehicleExecute() {
        return zuPinVehicleExecute;
    }

    public void setZuPinVehicleExecute(ZuPinVehicleExecute zuPinVehicleExecute) {
        this.zuPinVehicleExecute = zuPinVehicleExecute;
    }

    public String getCaoZuoRen() {
        return caoZuoRen;
    }

    public void setCaoZuoRen(String caoZuoRen) {
        this.caoZuoRen = caoZuoRen;
    }

    public String getCaoZuoRenUuid() {
        return caoZuoRenUuid;
    }

    public void setCaoZuoRenUuid(String caoZuoRenUuid) {
        this.caoZuoRenUuid = caoZuoRenUuid;
    }

    public String getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(String changeDate) {
        this.changeDate = changeDate;
    }

    public ZuPinContact getZuPinContact() {
        return zuPinContact;
    }

    public void setZuPinContact(ZuPinContact zuPinContact) {
        this.zuPinContact = zuPinContact;
    }
}
