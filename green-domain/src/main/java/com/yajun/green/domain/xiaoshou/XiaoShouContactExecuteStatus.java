package com.yajun.green.domain.xiaoshou;

import com.yajun.green.common.web.view.SelectView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiuMengKe on 2017/9/4.
 *
 *  执行情况
 */
public enum XiaoShouContactExecuteStatus {

    E_DJ_BEGIN("定金未付"),
   /* E_DJ_FINISH(""),*/
    E_SF_BEGIN("首付未支付"),
    /*E_SF_FINISH(""),*/
    E_WK_BEGIN("尾款未支付"),
    E_FINISH("结束");
    XiaoShouContactExecuteStatus(String description) {
        this.description = description;
    }

    public static List<SelectView> getSelectViews() {
        List<SelectView> views = new ArrayList<>();
        XiaoShouContactExecuteStatus[] values = XiaoShouContactExecuteStatus.values();
        for (XiaoShouContactExecuteStatus value : values) {
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
