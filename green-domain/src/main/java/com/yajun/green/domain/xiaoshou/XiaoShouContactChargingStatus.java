package com.yajun.green.domain.xiaoshou;

import com.yajun.green.common.web.view.SelectView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chance on 2017/9/22.
 */
public enum XiaoShouContactChargingStatus {
    CHARGING_CREATED("已创建"),
    CHARGING_FINISH("已处理");

    XiaoShouContactChargingStatus(String description) {
        this.description = description;
    }

    public static List<SelectView> getSelectViews(){
        List<SelectView> views = new ArrayList<>();
        XiaoShouContactChargingStatus[] values = XiaoShouContactChargingStatus.values();
        for (XiaoShouContactChargingStatus value :values) {
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
