package com.yajun.green.domain.pay;

import java.math.BigDecimal;

/**
 * User: Jack Wang
 * Date: 17-10-15
 * Time: 上午11:23
 */
public interface PayOrderItem {

    BigDecimal getItemFee();

    PayOrderStatus getItemStatus();

    String getDescription();
}
