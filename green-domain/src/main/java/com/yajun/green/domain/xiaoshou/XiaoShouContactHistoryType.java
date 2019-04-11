package com.yajun.green.domain.xiaoshou;

import com.yajun.green.common.web.view.SelectView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiuMengKe on 2017/9/4.
 *
 * 账单类型
 *
 * 定金 首付 尾款
 *
 */
public enum XiaoShouContactHistoryType {

    H_DJ("定金"),
    H_SF("首付"),
    H_WK("尾款");

    XiaoShouContactHistoryType(String description) {
        this.description = description;
    }

    public static List<SelectView> getSelectViews() {
        List<SelectView> views = new ArrayList<>();
        XiaoShouContactHistoryType[] values = XiaoShouContactHistoryType.values();
        for (XiaoShouContactHistoryType value : values) {
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
