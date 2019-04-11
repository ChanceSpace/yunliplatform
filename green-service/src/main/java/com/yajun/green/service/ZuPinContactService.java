package com.yajun.green.service;

import com.yajun.green.domain.contact.ZuPinContact;
import com.yajun.green.domain.contact.ZuPinVehicleExecute;
import com.yajun.green.facade.dto.admin.CompanyDTO;
import com.yajun.green.facade.dto.contact.*;
import org.joda.time.LocalDate;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/**
 * Created by LiuMengKe on 2017/8/9.
 * <p>
 * 合同 基本信息 查看 创建
 */
public interface ZuPinContactService {


    /*******************************************合同浏览********************************/

    List<ZuPinContactDTO> obtainOverviewZuPinContact(String keyWords, String userUuid, String startTime, String endTime, String sortBy, String sortWay, String contactStatus, int startPosition, int pageSize);

    int obtainOverviewZuPinContactSize(String keyWords, String userUuid, String startTime, String endTime, String sortBy, String sortWay, String contactStatus);

    /*******************************************合同创建**********************************/

    ZuPinContactDTO obtainZuPinContactByUuid(String zuPinContactUuid, boolean loadSelectVehicleModule, boolean loadExeVehicleModule, boolean loadRentFeeHistory, boolean loadContactFile, boolean loadContactLog, boolean loadContactSupply);

    String saveOrUpdateZuPinContact(ZuPinContactDTO dto);

    /*******************************************合同状态************************************/

    void changeZuPinContactCheck(String zuPinContactUuid, String changeStatus);

    void deleteZuPinContact(String zuPinContactUuid);

    /*******************************************合同车型部分************************************/

    void saveOrUpdateZuPinVehicleModule(ZuPinVehicleModuleDTO module, String zuPinContactUuid);

    void deleteZuPinContactVehicleModule(String contactVehicleUuid);


    /*****************************************获取特定的executeDTO*******************************/

    ZuPinVehicleExecuteDTO obtainSpecZuPinContactVehicleExcuteDTO(String zuPinContactUuid, String tiChePiCi, String vehicleNum);

    /*****************************************获取特定的historyDTO*******************************/

    ZuPinContactRentingFeeHistoryDTO obtainZuPinContactRentingFeeHistoryDTOByUuid(String historyUuid);
}
