package com.yajun.green.service;


import com.yajun.green.facade.dto.contact.*;
import com.yajun.green.facade.show.ZuPinContactRiJieBalanceObj;
import org.joda.time.LocalDate;
import java.util.List;
import java.util.Map;


/**
 * Created by LiuMengKe on 2017/8/9.
 */
public interface ZuPinHistoryService {

    /**************************************批量保存合同提车信息********************************/

    /*public void saveZuPinContactRentingFeeHistory(ZuPinContactRentingFeeHistoryDTO zuPinContactRentingFeeHistoryDTO);*/

    /*********************************按照月份获取明细*******************************/

    Map<String, ZuPinContacctReetingFeeHistoryGroupDTO> obtainZuPinContactMinXi(String zupinContactUuid);

    /*************************************获取所有即将到期的车辆 term 表示距离到期时间的天数****************************/

    List<ZuPinVehicleExecuteDTO> obtainReachNextRentFeeDateVehicle(LocalDate localDate, int term);

    List<ZuPinVehicleExecuteDTO> obtainReachNextRentFeeDateAndMoneyEmptyVehicle(int term);
    
    /*********************************按照车辆获取明细*******************************/
    Map<String, ZuPinContacctReetingFeeHistoryGroupDTO> obtainZuPinContactMinXiByVecle(String zupinContactUuid);

    /*********************************获取日结合同账单明细*******************************/
    ZuPinContactRiJieBalanceObj obtainZuPinContactRiJieMinXi(String zuPinContactUuid);
}
