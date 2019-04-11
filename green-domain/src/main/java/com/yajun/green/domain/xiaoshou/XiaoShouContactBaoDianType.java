package com.yajun.green.domain.xiaoshou;


import com.yajun.green.common.exception.ApplicationException;
import com.yajun.green.common.web.view.SelectView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiuMengKe on 2017/9/1.
 */
public enum XiaoShouContactBaoDianType {
    C_BUBAODIAN("不包含电费"),
    C_BAODIAN("包含电费");

    XiaoShouContactBaoDianType(String description) {
        this.description = description;
    }

    public static List<SelectView> getSelectViews() {
        List<SelectView> views = new ArrayList<>();

        XiaoShouContactBaoDianType[] values = XiaoShouContactBaoDianType.values();
        for (XiaoShouContactBaoDianType value : values) {
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

    public static String decideWhichType(String type) {
        if (type.equals(XiaoShouContactBaoDianType.C_BAODIAN.name())) {
            return "A";
        } else if (type.equals(XiaoShouContactBaoDianType.C_BUBAODIAN.name())) {
            return "B";
        }
        throw new ApplicationException("contact type not right, please check the code");
    }
}
