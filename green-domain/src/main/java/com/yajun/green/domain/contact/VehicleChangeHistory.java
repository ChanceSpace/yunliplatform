package com.yajun.green.domain.contact;


import com.yajun.green.common.domain.EntityBase;
import org.joda.time.DateTime;

/**
 * Created by chance on 2017/8/21.
 */
public class VehicleChangeHistory extends EntityBase {

    private String vehicleNumberBefore;
    private String vehicleNumberNow;
    private String caoZuoRen;
    private String caoZuoRenUuid;
    private DateTime changeDate;

    private ZuPinContact zuPinContact;
    private ZuPinVehicleExecute zuPinVehicleExecute;

    public VehicleChangeHistory() {
    }

    public VehicleChangeHistory(String vehicleNumberBefore, String vehicleNumberNow, ZuPinVehicleExecute zuPinVehicleExecute, String caoZuoRen, String caoZuoRenUuid, DateTime changeDate, ZuPinContact zuPinContact) {
        this.vehicleNumberBefore = vehicleNumberBefore;
        this.vehicleNumberNow = vehicleNumberNow;
        this.zuPinVehicleExecute = zuPinVehicleExecute;
        this.caoZuoRen = caoZuoRen;
        this.caoZuoRenUuid = caoZuoRenUuid;
        this.changeDate = changeDate;
        this.zuPinContact = zuPinContact;
    }

    /*****************************************GET/SET*******************************************/

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

    public DateTime getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(DateTime changeDate) {
        this.changeDate = changeDate;
    }

    public ZuPinContact getZuPinContact() {
        return zuPinContact;
    }

    public void setZuPinContact(ZuPinContact zuPinContact) {
        this.zuPinContact = zuPinContact;
    }
}
