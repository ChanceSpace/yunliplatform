package com.yajun.green.domain.contact;

import com.yajun.green.common.web.view.SelectView;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Jack Wang
 * Date: 17-8-8
 * Time: 上午11:11
 */
public enum ZuPinContactStatus {

    S_CREATED("已创建"),
    S_PROCESSING("执行中"),
    S_ENDBUTNOTOVER("到期未结束"),
    S_FINISHED("已结束");

    ZuPinContactStatus(String description) {
        this.description = description;
    }

    public static List<SelectView> getSelectViews() {
        List<SelectView> views = new ArrayList<>();

        ZuPinContactStatus[] values = ZuPinContactStatus.values();
        for (ZuPinContactStatus value : values) {
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
