package com.yajun.green.domain.contact;

/**
 * User: Jack Wang
 * Date: 17-9-4
 * Time: 下午4:46
 *
 * 单量车和整个合同结束时的费用结算
 */
public enum ZuPinContactFinishCharge {

    ZHI_CHU("支出"),
    SHOU_QU("收取");

    ZuPinContactFinishCharge(String description) {
        this.description = description;
    }

    public String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
