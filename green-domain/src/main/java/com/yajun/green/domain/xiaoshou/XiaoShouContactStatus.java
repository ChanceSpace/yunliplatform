package com.yajun.green.domain.xiaoshou;

import com.yajun.green.common.web.view.SelectView;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Jack Wang
 * Date: 17-8-8
 * Time: 上午11:11
 *  创建合同    执行中   结束
 */
public enum XiaoShouContactStatus {

    S_CREATED("已创建"),
    S_PROCESSING("执行中"),
    S_FINISHED("已结束");

    XiaoShouContactStatus(String description) {
        this.description = description;
    }

    public static List<SelectView> getSelectViews() {
        List<SelectView> views = new ArrayList<>();

        XiaoShouContactStatus[] values = XiaoShouContactStatus.values();
        for (XiaoShouContactStatus value : values) {
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
