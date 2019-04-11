package com.yajun.green.service;

import com.yajun.green.facade.dto.contact.VehicleChangeHistoryDTO;
import com.yajun.green.facade.dto.contact.ZuPinVehicleExecuteDTO;

/**
 * Created by LiuMengKe on 2017/11/27.
 */
public interface ZuPinContactVehicleGetAndChangeService {

    //相同车辆校验
    ZuPinVehicleExecuteDTO obtainDuplicateVehicleFromAllContact(String vehicleNum, String tiCheDate);

    /*******************************************换车操作*********************************************/
    void saveVehicleChange(String zuPinContactUuid, VehicleChangeHistoryDTO vehicleChangeHistoryDTO, String vehicleNumberBefore, String comment);

    /*********************************提车操作*******************************/
    void saveVehicleGet(String zuPinContactUuid,String tiChePiCi,ZuPinVehicleExecuteDTO execute,String selectModuleBrand, String[] vehicleNumbers, String[] comment) throws Exception;

    boolean obtainDuplicateNotFinishVehicleOfContact(String vehicleNubmer,String zuPinContactUuid);
}
