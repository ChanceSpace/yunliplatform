package com.yajun.green.service;

import com.yajun.green.domain.contact.ZuPinContact;
import com.yajun.green.domain.contact.ZuPinContactFile;
import com.yajun.green.facade.dto.contact.ZuPinContactChargingDTO;
import com.yajun.green.facade.dto.contact.ZuPinContactSupplyContentDTO;
import com.yajun.green.facade.dto.contact.ZuPinContactSupplyDTO;
import com.yajun.green.facade.dto.contact.ZuPinVehicleExecuteDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by LiuMengKe on 2017/11/27.
 *
 * 合同附加信息部分 合同补充协议 充电桩等
 *
 */
public interface ZuPinContactAdditionalInfoService {

    /*******************************************合同补充合同部分************************************/

    ZuPinContactSupplyDTO obtainZuPinContactSupplyByUuid(String contactSupplyUuid);

    String saveOrUpdateZuPinContactSupply(ZuPinContactSupplyDTO zuPinContactSupplyDTO, String zuPinContactUuid);

    void deleteZuPinContactSupply(String contactSupplyUuid);

    /*******************************************合同补充合同内容部分************************************/

    void saveOrUpdateZuPinContactSupplyContent(ZuPinContactSupplyContentDTO zuPinContactSupplyContentDTO, String zuPinContactSupplyUuid);

    void deleteZuPinContactSupplyContent(String contactSupplyContentUuid);

    /*******************************************合同充电桩部分************************************/

    List<ZuPinContactChargingDTO> obtainZuPinContactCharging(String yiFangUuid);

    ZuPinContactChargingDTO obtainZuPinContactChargingByUuid(String chargingUuid);

    void saveOrUpdateZuPinContactCharging(ZuPinContactChargingDTO zuPinContactChargingDTO, String yiFangUuid, String yiFangName);

    void deleteZuPinContactCharging(String chargingUuid);

    /*******************************************合同附件部分*************************************/

    void obtainImportContactFile(String zuPinContactUuid, MultipartFile contactFile);

    void uploadZuPinContactFile(ZuPinContact contact, ZuPinContactFile file);

}
