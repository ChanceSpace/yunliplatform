package com.yajun.green.domain.pay;

import com.yajun.green.common.web.view.SelectView;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Jack Wang
 * Date: 17-10-15
 * Time: 上午10:30
 */
public enum PayOrderStatus {
    O_CREATE("未支付"),
    /**
     * @Author Liu MengKe
     * @Description: O_ABANDON 用于合同结束未支付押金状态声明
     */
    O_ABANDON("合同结束不处理"),
    O_FINSIH("已完成");

    PayOrderStatus(String description) {
        this.description = description;
    }
    public static List<SelectView> getSelectViews(){
        List<SelectView> views = new ArrayList<>();
        PayOrderStatus[] values = PayOrderStatus.values();
        for (PayOrderStatus value : values) {
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
