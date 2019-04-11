package com.yajun.green.domain.xiaoshou;

import com.yajun.green.common.exception.ApplicationException;
import com.yajun.green.common.web.view.SelectView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiuMengKe on 2017/12/18.
 */
public enum XiaoShouType {


    XS_QK("全款方式"),
    XS_ANJIE("按揭方式");

    XiaoShouType(String description) {
        this.description = description;
    }
    public static List<SelectView> getSelectViews(){
        List<SelectView> views = new ArrayList<>();
        XiaoShouType[] types = XiaoShouType.values();
        for (XiaoShouType type : types) {
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

    //全款开头A 按揭开头B
    public static String decideWhichType(XiaoShouType type) {
        if (XiaoShouType.XS_QK.equals(type)) {
            return "A";
        } else if (XiaoShouType.XS_ANJIE.equals(type)) {
            return "B";
        }
        throw new ApplicationException("contact type not right, please check the code");
    }

}
