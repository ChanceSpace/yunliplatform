package com.yajun.green.domain.contact;

import com.yajun.green.common.web.view.SelectView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiuMengKe on 2017/12/4.
 */
public enum ZuPinContactBaoYueType {

    B_BAOYUE("包月方式"),
    B_RIJIE("日结方式");

    ZuPinContactBaoYueType(String description) {
        this.description = description;
    }

    public static List<SelectView> getSelectViews() {
        List<SelectView> views = new ArrayList<>();

        ZuPinContactBaoYueType[] values = ZuPinContactBaoYueType.values();
        for (ZuPinContactBaoYueType value : values) {
            views.add(new SelectView(value.name(), value.getDescription()));
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
