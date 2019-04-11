package com.yajun.green.service;

import com.yajun.green.domain.xiaoshou.XiaoShouContact;
import com.yajun.green.domain.xiaoshou.XiaoShouContactFile;
import com.yajun.green.facade.dto.contact.xiaoshou.ZuPinXiaoShouContactChargingDTO;
import com.yajun.green.facade.dto.contact.xiaoshou.ZuPinXiaoShouContactDTO;
import com.yajun.green.facade.dto.contact.xiaoshou.ZuPinXiaoShouContactSupplyContentDTO;
import com.yajun.green.facade.dto.contact.xiaoshou.ZuPinXiaoShouContactSupplyDTO;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by LiuMengKe on 2017/11/27.
 *
 * 合同附加信息部分 合同补充协议 充电桩等
 *
 */
public interface XiaoShouContactAdditionalInfoService {

    /*******************************************合同补充合同部分************************************/

    ZuPinXiaoShouContactSupplyDTO obtainZuPinXiaoShouContactSupplyByUuid(String contactSupplyUuid);

    String saveOrUpdateZuPinXiaoShouContactSupply(ZuPinXiaoShouContactSupplyDTO zuPinContactSupplyDTO, String zuPinContactUuid);

    void deleteZuPinXiaoShouContactSupply(String contactSupplyUuid);

    /*******************************************合同补充合同内容部分************************************/

    void saveOrUpdateZuPinXiaoShouContactSupplyContent(ZuPinXiaoShouContactSupplyContentDTO zuPinContactSupplyContentDTO, String xiaoShouContactSupplyUuid);

    void deleteZuPinXiaoShouContactSupplyContent(String contactSupplyContentUuid);

    /*******************************************合同充电桩部分************************************/
    ZuPinXiaoShouContactChargingDTO obtainZuPinXiaoShouContactChargingByUuid(String chargingUuid);

    void saveOrUpdateZuPinXiaoShouContactCharging(ZuPinXiaoShouContactChargingDTO zuPinContactChargingDTO, ZuPinXiaoShouContactDTO contactDTO);

    void deleteZuPinXiaoShouContactCharging(String chargingUuid);

    /*******************************************合同附件部分*************************************/

    void obtainImportContactFile(String zuPinContactUuid, MultipartFile contactFile);

    void uploadZuPinXiaoShouContactFile(XiaoShouContact contact, XiaoShouContactFile file);

}
