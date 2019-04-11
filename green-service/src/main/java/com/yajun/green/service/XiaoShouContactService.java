package com.yajun.green.service;

import com.yajun.green.domain.xiaoshou.XiaoShouContactVehicleStatusObject;
import com.yajun.green.facade.dto.contact.ZuPinContactDTO;
import com.yajun.green.facade.dto.contact.xiaoshou.ZuPinXiaoShouContactDTO;
import com.yajun.green.facade.dto.contact.xiaoshou.ZuPinXiaoShouContactRentingFeeHistoryDTO;
import com.yajun.green.facade.dto.contact.xiaoshou.ZuPinXiaoShouVehicleModuleDTO;

import java.util.List;
import java.util.Map;

/**
 * Created by LiuMengKe on 2017/12/21.
 */
public interface XiaoShouContactService {

    List<ZuPinXiaoShouContactDTO> obtainOverviewZuPinContact(String keyWords, String userUuid, String startTime, String endTime, String sortBy, String sortWay, String contactStatus, int startPosition, int pageSize);

    int obtainOverviewZuPinContactSize(String keyWords, String userUuid, String startTime, String endTime, String sortBy, String sortWay, String contactStatus);

    ZuPinXiaoShouContactDTO obtainZuPinXiaoShouContactByUuid(String zuPinContactUuid, boolean loadSelectVehicleModule, boolean loadExeVehicleModule, boolean loadRentFeeHistory, boolean loadContactFile, boolean loadContactLog, boolean loadContactSupply, boolean loadCharge);

    String saveOrUpdateZuPiXiaoShounContact(ZuPinXiaoShouContactDTO dto);

    //保存车型
    String saveOrUpdateXiaoShouVehicleModule(ZuPinXiaoShouVehicleModuleDTO module, String zuPinContactUuid);

    void deleteXiaoShouVehicleModule(String contactVehicleUuid);

    //合同详情页面 车辆状态
    Map<String,XiaoShouContactVehicleStatusObject> obtainXiaoShouContactVehicleStatusList(String contactUuid);

    void changeXiaoShouContactCheck(String xiaoShouContactUuid, String changeStatus);

    //删除合同
    void deleteXiaoShouContact(String xiaoShouContactUuid);

    void saveOrUpdateXiaoShouHistory(ZuPinXiaoShouContactRentingFeeHistoryDTO historyDTO,String contactUuid);
}
