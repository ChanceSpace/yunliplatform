package com.yajun.green.domain.contact;

import com.yajun.green.common.web.view.SelectView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chance on 2017/9/22.
 */
public enum ZuPinContactChargingStatus {
    CHARGING_CREATED("已创建"),
    CHARGING_FINISH("已处理");

    ZuPinContactChargingStatus(String description) {
        this.description = description;
    }

    public static List<SelectView> getSelectViews(){
        List<SelectView> views = new ArrayList<>();
        ZuPinContactChargingStatus[] values = ZuPinContactChargingStatus.values();
        for (ZuPinContactChargingStatus value :values) {
            views.add(new SelectView(value.name(),value.getDescription()));
        }
        return views;
    }

    public String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
