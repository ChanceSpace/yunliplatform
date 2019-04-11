package com.yajun.green.repository;


import com.yajun.green.domain.contact.ZuPinContactRentingFeeHistory;
import com.yajun.green.domain.contact.ZuPinVehicleExecute;
import com.yajun.green.domain.pay.PayOrderStatus;
import org.joda.time.LocalDate;

import java.util.List;

/**
 * Created by LiuMengKe on 2017/8/9.
 */
public interface ZuPinHistoryDao extends EntityObjectDao {

    List<ZuPinContactRentingFeeHistory> obtainOverviewZuPinContactRentingFeeHistoryList(String zuPinContactUuid, String keyWords, int startPosition, int pageSize);

    int obtainOverviewZuPinContactRentingFeeHistorySize(String keyWords, String zuPinContactUuid);

    List<ZuPinVehicleExecute> obtainReachNextRentFeeDateVehicle(LocalDate willreachDate);

    List<ZuPinVehicleExecute> obtainReachNextRentFeeDateAndMoneyEmptyVehicle(LocalDate newDate);

    List<ZuPinContactRentingFeeHistory> obtainAllHistoryInSpecStatus(PayOrderStatus status);

}
