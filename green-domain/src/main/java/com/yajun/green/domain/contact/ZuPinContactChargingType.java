package com.yajun.green.domain.contact;

import com.yajun.green.common.web.view.SelectView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chance on 2017/9/25.
 */
public enum ZuPinContactChargingType {
    CHARGING_AC("交流"),
    CHARGING_DC("直流");

    ZuPinContactChargingType(String description) {
        this.description = description;
    }
    public static List<SelectView> getSelectViews(){
        List<SelectView> views = new ArrayList<>();
        ZuPinContactChargingType[] values = ZuPinContactChargingType.values();
        for (ZuPinContactChargingType value : values) {
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
