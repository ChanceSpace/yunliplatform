package com.yajun.green.facade.dto.contact;

import java.util.List;

/**
 * Created by chance on 2017/8/29.
 */
public class ZuPinContactSupplyDTO {
    private String uuid;
    private String caoZuoRen;
    private String caoZuoRenUuid;
    private String timestamp;

    private List<ZuPinContactSupplyContentDTO> zuPinContactSupplyContentDTOS;

    public ZuPinContactSupplyDTO() {
    }

    public ZuPinContactSupplyDTO(String uuid, String caoZuoRen, String caoZuoRenUuid, String timestamp, List<ZuPinContactSupplyContentDTO> zuPinContactSupplyContentDTOS) {
        this.uuid = uuid;
        this.caoZuoRen = caoZuoRen;
        this.caoZuoRenUuid = caoZuoRenUuid;
        this.timestamp = timestamp;
        this.zuPinContactSupplyContentDTOS = zuPinContactSupplyContentDTOS;
    }

    /******************************************GETTER/SETTER****************************************/

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public List<ZuPinContactSupplyContentDTO> getZuPinContactSupplyContentDTOS() {
        return zuPinContactSupplyContentDTOS;
    }

    public void setZuPinContactSupplyContentDTOS(List<ZuPinContactSupplyContentDTO> zuPinContactSupplyContentDTOS) {
        this.zuPinContactSupplyContentDTOS = zuPinContactSupplyContentDTOS;
    }
}
