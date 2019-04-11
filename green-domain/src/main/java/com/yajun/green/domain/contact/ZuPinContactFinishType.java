package com.yajun.green.domain.contact;

/**
 * User: Jack Wang
 * Date: 17-9-4
 * Time: 下午4:46
 *
 * 单量车和整个合同结束时的费用结算
 */
public enum ZuPinContactFinishType {

    VEHICLE_FINISH("单台车辆结束"),
    CONTACT_FINISH("合同结束");

    ZuPinContactFinishType(String description) {
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
