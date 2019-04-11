package com.yajun.green.domain.xiaoshou;

import com.yajun.green.common.web.view.SelectView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiuMengKe on 2017/12/19.
 */
public enum XiaoShouPayOrderStatus {

    O_CREATE("未支付"),
    O_TK("已经退款"),
    O_FINSIH("已完成");

    XiaoShouPayOrderStatus(String description) {
        this.description = description;
    }
    public static List<SelectView> getSelectViews(){
        List<SelectView> views = new ArrayList<>();
        XiaoShouPayOrderStatus[] values = XiaoShouPayOrderStatus.values();
        for (XiaoShouPayOrderStatus value : values) {
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
