package com.yajun.green.domain.pay;

import com.yajun.green.common.web.view.SelectView;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Jack Wang
 * Date: 17-10-15
 * Time: 上午10:30
 */
public enum PayOrderWay {
    O_OFFLINE("余额"),
    O_WEBCHART("微信"),
    O_AILI("支付宝"),
    O_YINLIAN("银联"),
    O_CASH("线下");

    PayOrderWay(String description) {
        this.description = description;
    }
    public static List<SelectView> getSelectViews(){
        List<SelectView> views = new ArrayList<>();
        PayOrderWay[] values = PayOrderWay.values();
        for (PayOrderWay value : values) {
            if (!value.equals(O_YINLIAN)){
                views.add(new SelectView(value.name(),value.getDescription()));
            }
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

    public static String getPayOrderNumber(PayOrderWay way) {
        if (way.equals(PayOrderWay.O_OFFLINE)) {
            return "01";
        } else if(way.equals(PayOrderWay.O_WEBCHART)) {
            return "02";
        } else if(way.equals(PayOrderWay.O_AILI)) {
            return "03";
        } else if(way.equals(PayOrderWay.O_YINLIAN)) {
            return "04";
        }
        return null;
    }
}
