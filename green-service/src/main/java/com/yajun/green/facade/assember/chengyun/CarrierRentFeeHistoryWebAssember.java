package com.yajun.green.facade.assember.chengyun;

import com.yajun.green.common.utils.JodaUtils;
import com.yajun.green.domain.chengyun.CarrierRentFeeHistory;
import com.yajun.green.facade.dto.chengyun.CarrierRentFeeHistoryDTO;
import com.yajun.green.facade.dto.pay.PayItem;
import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chance on 2017/10/16.
 */
public class CarrierRentFeeHistoryWebAssember {

    public static CarrierRentFeeHistory toCarrierRentFeeHistoryDomain(CarrierRentFeeHistoryDTO carrierRentFeeHistoryDTO){
        DateTime happenDate = new DateTime(carrierRentFeeHistoryDTO.getHappenDate());
        BigDecimal feeTotal = carrierRentFeeHistoryDTO.getFeeTotal();
        return new CarrierRentFeeHistory(happenDate, feeTotal);
    }

    public static CarrierRentFeeHistoryDTO toCarrierRentFeeHistoryDTO(CarrierRentFeeHistory carrierRentFeeHistory, boolean chongZhi){
        final String happenDate = JodaUtils.todMYHmString(carrierRentFeeHistory.getHappenDate());
        final BigDecimal feeTotal = carrierRentFeeHistory.getFeeTotal();
        final String uuid = carrierRentFeeHistory.getUuid();
        String comment = chongZhi ? "线下账户充值" : "线下账户提现";

        return new CarrierRentFeeHistoryDTO(uuid, happenDate, feeTotal, comment);
    }

    public static List<PayItem> toPaymentCarrierRentFeeHistoryDTOList(List<CarrierRentFeeHistory> carrierRentFeeHistoryList, boolean chongZhi){
        List<PayItem> carrierRentFeeHistoryDTOList = new ArrayList<>();
        if (carrierRentFeeHistoryList != null) {
            for (CarrierRentFeeHistory carrierRentFeeHistory : carrierRentFeeHistoryList) {
                carrierRentFeeHistoryDTOList.add(toCarrierRentFeeHistoryDTO(carrierRentFeeHistory, chongZhi));
            }
        }
        return carrierRentFeeHistoryDTOList;
    }
}
