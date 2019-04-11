package com.yajun.green.facade.dto.contact;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestUtils;

/**
 * 仅仅用来封装车辆结束时的信息
 * Created by LiuMengKe on 2017/9/25.
 */
public class ZuPinVehicleExecuteOverDTO {
    private String zuPinContactUuid ;
    private String tiChePiCi;
    private String vehicleNum;
    private String finishDate ;

    public String getZuPinContactUuid() {
        return zuPinContactUuid;
    }

    public void setZuPinContactUuid(String zuPinContactUuid) {
        this.zuPinContactUuid = zuPinContactUuid;
    }

    public String getTiChePiCi() {
        return tiChePiCi;
    }

    public void setTiChePiCi(String tiChePiCi) {
        this.tiChePiCi = tiChePiCi;
    }

    public String getVehicleNum() {
        return vehicleNum;
    }

    public void setVehicleNum(String vehicleNum) {
        this.vehicleNum = vehicleNum;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }
}
