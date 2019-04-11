package com.yajun.green.repository;

import com.yajun.green.domain.contact.*;


import java.util.List;

/**
 * Created by LiuMengKe on 2017/8/9.
 */
public interface ZuPinContactDao extends EntityObjectDao {

    /********************************************合同相关**************************************/

    List<ZuPinContact> obtainAllNotFinishedZupincontact();

    List<ZuPinContact> findOverviewZuPinContact(String keyWords, String userUuid, String startTime, String endTime, String sortBy, String sortWay, String contactStatus, int startPosition, int pageSize);

    int findOverviewZuPinContactSize(String keyWords, String userUuid, String startTime, String endTime, String sortBy, String sortWay,String contactStatus);

    boolean findZuPinOrderDuplicate(String zuPinNumberSuffix);

    /********************************************我的相关任务**************************************/

    ZuPinVehicleExecute obtainDuplicateVehicleFromAllContact(String vehicleNum, String tiCheDate);

    ZuPinVehicleExecute obtainSpecZuPinContactVehicleExcuteDTO(String zuPinContactUuid, String tiChePiCi, String vehicleNum);

    ZuPinVehicleExecute obtainAllStatusSpecZuPinContactVehicleExcute(String zuPinContactUuid, String tiChePiCi, String vehicleNum);

    /*******************************************扣款历史*******************************************/

    List<ZuPinContactRentingFeeHistory> findContactRentingFeeHistory(List<String> historyUuids);

    /********************************************合同账单相关*******************************/

    //特定用户所有未付款history
    List<ZuPinContactRentingFeeHistory> findSpecUserOnCreateBill(String selectCarrierUuid);

     //未付账单用户
    List<Object[]> obtainAllCompanyWithOnCreateBillOnLoginUser();

    /********************************************充电桩部分**********************************/

    List<ZuPinContactCharging> findZuPinContactCharging(String yiFangUuid);
}
