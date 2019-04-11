package com.yajun.green.domain.contact;

import com.yajun.green.common.web.view.SelectView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiuMengKe on 2017/9/20.
 */
public enum ZuPinContactUserType {

    Y_COMPANY("公司"),
    Y_PERSON("个人");

    ZuPinContactUserType(String description) {
        this.description = description;
    }
    public static List<SelectView> getSelectViews(){
        List<SelectView> views = new ArrayList<>();
        ZuPinContactUserType[] types = ZuPinContactUserType.values();
        for (ZuPinContactUserType type : types) {
            views.add(new SelectView(type.name(), type.getDescription()));
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
