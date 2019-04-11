package com.yajun.green.domain.chengyun;

import com.yajun.green.common.domain.EntityBase;
import org.joda.time.DateTime;

import java.math.BigDecimal;

/**
 * Created by chance on 2017/10/16.
 */
public class CarrierRentFeeHistory extends EntityBase {

    private DateTime happenDate;
    private BigDecimal feeTotal;

    public CarrierRentFeeHistory() {
    }

    public CarrierRentFeeHistory(DateTime happenDate, BigDecimal feeTotal) {
        this.happenDate = happenDate;
        this.feeTotal = feeTotal;
    }

    /******************************************GETTER/SETTER**************************************************/

    public DateTime getHappenDate() {
        return happenDate;
    }

    public void setHappenDate(DateTime happenDate) {
        this.happenDate = happenDate;
    }

    public BigDecimal getFeeTotal() {
        return feeTotal;
    }

    public void setFeeTotal(BigDecimal feeTotal) {
        this.feeTotal = feeTotal;
    }
}
