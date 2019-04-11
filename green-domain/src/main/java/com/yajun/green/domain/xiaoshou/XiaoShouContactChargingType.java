package com.yajun.green.domain.xiaoshou;

import com.yajun.green.common.web.view.SelectView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chance on 2017/9/25.
 */
public enum XiaoShouContactChargingType {
    CHARGING_AC("交流"),
    CHARGING_DC("直流");

    XiaoShouContactChargingType(String description) {
        this.description = description;
    }
    public static List<SelectView> getSelectViews(){
        List<SelectView> views = new ArrayList<>();
        XiaoShouContactChargingType[] values = XiaoShouContactChargingType.values();
        for (XiaoShouContactChargingType value : values) {
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
