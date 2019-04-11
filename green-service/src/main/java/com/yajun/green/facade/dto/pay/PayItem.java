package com.yajun.green.facade.dto.pay;

import java.math.BigDecimal;

/**
 * User: Jack Wang
 * Date: 17-10-27
 * Time: 下午3:31
 */
public interface PayItem {

    /************所有相关*************************/

    String getHappenDate();

    String getUuid();

    String getSource();

    String getDetails();

    String getComment();

    BigDecimal getItemFee();

    /************车辆扣费相关*********************/

    String getVehicleNumber();
}
