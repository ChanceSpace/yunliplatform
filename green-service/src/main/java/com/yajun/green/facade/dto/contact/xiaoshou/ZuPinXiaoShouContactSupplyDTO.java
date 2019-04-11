package com.yajun.green.facade.dto.contact.xiaoshou;

import com.yajun.green.facade.dto.contact.ZuPinContactSupplyContentDTO;

import java.util.List;

/**
 * Created by LiuMengKe on 2017/12/20.
 */
public class ZuPinXiaoShouContactSupplyDTO {

    private String uuid;
    private String caoZuoRen;
    private String caoZuoRenUuid;
    private String timestamp;

    private List<ZuPinXiaoShouContactSupplyContentDTO> zuPinXiaoShouContactSupplyContentDTOList;

    public ZuPinXiaoShouContactSupplyDTO() {
    }

    public ZuPinXiaoShouContactSupplyDTO(String uuid, String caoZuoRen, String caoZuoRenUuid, String timestamp, List<ZuPinXiaoShouContactSupplyContentDTO> zuPinXiaoShouContactSupplyContentDTOList) {
        this.uuid = uuid;
        this.caoZuoRen = caoZuoRen;
        this.caoZuoRenUuid = caoZuoRenUuid;
        this.timestamp = timestamp;
        this.zuPinXiaoShouContactSupplyContentDTOList = zuPinXiaoShouContactSupplyContentDTOList;
    }

    /*********************************setter getter*******************************/
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

    public List<ZuPinXiaoShouContactSupplyContentDTO> getZuPinXiaoShouContactSupplyContentDTOList() {
        return zuPinXiaoShouContactSupplyContentDTOList;
    }

    public void setZuPinXiaoShouContactSupplyContentDTOList(List<ZuPinXiaoShouContactSupplyContentDTO> zuPinXiaoShouContactSupplyContentDTOList) {
        this.zuPinXiaoShouContactSupplyContentDTOList = zuPinXiaoShouContactSupplyContentDTOList;
    }
}
