package com.yajun.green.domain.pay;

import com.yajun.green.common.web.view.SelectView;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Jack Wang
 * Date: 17-10-15
 * Time: 上午10:30
 */
public enum PayOrderSource {
    O_CONTACT("租赁合同"),
    O_XS("销售合同"),
    O_CHONGZHI("账户充值"),
    O_TIXIAN("账户提现");

    PayOrderSource(String description) {
        this.description = description;
    }
    public static List<SelectView> getSelectViews(){
        List<SelectView> views = new ArrayList<>();
        PayOrderSource[] values = PayOrderSource.values();
        for (PayOrderSource value : values) {
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

    public static String getPayOrderNumber(PayOrderSource source) {
        if (source.equals(PayOrderSource.O_CONTACT)) {
            return "01";
        } else if (source.equals(PayOrderSource.O_CHONGZHI)) {
            return "02";
        } else if (source.equals(PayOrderSource.O_TIXIAN)){
            return "03";
        }
        else if (source.equals(PayOrderSource.O_XS)){
            return "04";
        }
        return null;
    }
}
