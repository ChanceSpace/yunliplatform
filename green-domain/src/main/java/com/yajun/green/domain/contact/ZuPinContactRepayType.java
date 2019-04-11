package com.yajun.green.domain.contact;

import com.yajun.green.common.web.view.SelectView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiuMengKe on 2017/9/4.
 *   合同还款方式
 */
public enum ZuPinContactRepayType {

    C_BEFORE("先付租金"),
    C_AFTER("缓付租金");

    ZuPinContactRepayType(String description) {
        this.description = description;
    }

    public static List<SelectView> getSelectViews() {
        List<SelectView> views = new ArrayList<>();

        ZuPinContactRepayType[] values = ZuPinContactRepayType.values();
        for (ZuPinContactRepayType value : values) {
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
