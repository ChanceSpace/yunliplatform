package com.yajun.green.domain.contact;

import com.yajun.green.common.web.view.SelectView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chance on 2017/9/2.
 */
public enum ZuPinYaJinType {

    Y_QUANKUAN("全款支付"),
    Y_FENQI("分期付款");

    ZuPinYaJinType(String description) {
        this.description = description;
    }
    public static List<SelectView> getSelectViews(){
        List<SelectView> views = new ArrayList<>();
        ZuPinYaJinType[] types = ZuPinYaJinType.values();
        for (ZuPinYaJinType type : types) {
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
