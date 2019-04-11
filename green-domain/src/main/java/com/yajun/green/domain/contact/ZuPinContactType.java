package com.yajun.green.domain.contact;



import com.yajun.green.common.exception.ApplicationException;
import com.yajun.green.common.web.view.SelectView;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Jack Wang
 * Date: 17-8-8
 * Time: 上午11:11
 */
public enum ZuPinContactType {

    C_RENT("租赁合同"),
    C_RENT_SALE("租转售合同");

    ZuPinContactType(String description) {
        this.description = description;
    }

    public static List<SelectView> getSelectViews() {
        List<SelectView> views = new ArrayList<>();

        ZuPinContactType[] values = ZuPinContactType.values();
        for (ZuPinContactType value : values) {
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
        if (type.equals(ZuPinContactType.C_RENT.name())) {
            return "A";
        } else if (type.equals(ZuPinContactType.C_RENT_SALE.name())) {
            return "B";
        }
        throw new ApplicationException("contact type not right, please check the code");
    }

}
