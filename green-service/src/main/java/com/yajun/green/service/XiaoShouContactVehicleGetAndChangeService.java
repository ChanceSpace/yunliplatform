package com.yajun.green.service;

import com.yajun.green.facade.dto.contact.VehicleChangeHistoryDTO;
import com.yajun.green.facade.dto.contact.ZuPinVehicleExecuteDTO;
import com.yajun.green.facade.dto.contact.xiaoshou.ZuPinXiaoShouVehicleExecuteDTO;

/**
 * Created by LiuMengKe on 2017/11/27.
 */
public interface XiaoShouContactVehicleGetAndChangeService {

    /*********************************提车操作*******************************/

    void saveVehicleGet(String zuPinContactUuid, String tiChePiCi, ZuPinXiaoShouVehicleExecuteDTO execute, String selectModuleBrand, String[] vehicleNumbers, String[] comment) throws Exception;

    boolean obtainDuplicateNotFinishVehicleOfContact(String vehicleNubmer, String xiaoShouContactUuid);
}
