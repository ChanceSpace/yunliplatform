package com.yajun.green.repository.xiaoshou;

import com.yajun.green.domain.xiaoshou.XiaoShouContactRentingFeeHistory;
import com.yajun.green.domain.xiaoshou.XiaoShouContact;
import com.yajun.green.repository.EntityObjectDao;

import java.util.List;

/**
 * Created by LiuMengKe on 2017/12/21.
 */
public interface XiaoShouContactDao extends EntityObjectDao {
    
    List<XiaoShouContact> findOverviewXiaoShouContact(String keyWords, String userUuid, String startTime, String endTime, String sortBy, String sortWay, String contactStatus, int startPosition, int pageSize);

    int findOverviewXiaoShouSize(String keyWords, String userUuid, String startTime, String endTime, String sortBy, String sortWay, String contactStatus);

    //查找重复的编号
    boolean findXiaoShouOrderDuplicate(String temp);

    //未付账单用户
    List<Object[]> obtainAllCompanyWithOnCreateBillOnLoginUser();

    //特定用户所有未付款history
    List<XiaoShouContactRentingFeeHistory> findSpecUserOnCreateBill(String selectCarrierUuid);

    /*******************************************扣款历史*******************************************/
    List<XiaoShouContactRentingFeeHistory> findXiaoShouHistory(List<String> historyUuids);

    List<XiaoShouContactRentingFeeHistory> obtainXiaoShouHistoryByVehicleNumber(String contactUuid, String vehicleNumber);
}
