package com.yajun.green.service;

import com.yajun.green.domain.contact.ZuPinContactRentingFeeHistory;
import com.yajun.green.facade.dto.contact.ZuPinContactOverFormDTO;
import com.yajun.green.facade.dto.contact.ZuPinContactRentingFeeHistoryDTO;
import com.yajun.green.facade.dto.contact.ZuPinVehicleExecuteDTO;
import org.joda.time.LocalDate;
import java.util.List;

/**
 * Created by LiuMengKe on 2017/11/29.
 *
 * 合同结束 和 单车 结束租赁
 *
 */
public interface ZuPinContactAndVehicleOverService {

    /*********************************合同结束相关*******************************/
    /***********获取合同中状态为未付款状态的所有history************/
    List<ZuPinContactRentingFeeHistoryDTO> obtainNoPayHistoryList(String zuPinContactUuid);

    //获取合同结束 时应该付款却还没有生成的账单
    List<ZuPinContactRentingFeeHistoryDTO> obtainZupinContactFinishNoPayZuJinList(String zuPinContactUuid, LocalDate localDate);

    //计算最小合同中最玩的提车时间
    LocalDate obtainZuPinContactMaxTicheDate(String zuPinContactUuid);

    void updateZuPinContactFinish(List<ZuPinVehicleExecuteDTO> executeDTOS, ZuPinContactOverFormDTO zuPinContactOverFormDTO);

    //获取单车结束时这个车的账单
    List<ZuPinContactRentingFeeHistoryDTO> obtainZuPinContactSpecVehicleDTOList(String zuPinContactUuid, String executeUuid, String vehicleNum, LocalDate now);
    List<ZuPinContactRentingFeeHistory> obtainZuPinContactSpecVehicleList(String zuPinContactUuid, String executeUuid, String vehicleNum, LocalDate now);

    //单车结束
    void saveOverZuPinVehicle(String zuPinContactUuid, String tiChePiCi, String vehicleNum, String finishDate, String comment) throws Exception;



    /*********************************日租类型*******************************/
    void saveOverZuPinRiZuVehicle(String zuPinContactUuid, String tiChePiCi, String vehicleNum, String finishDate, String comment) throws Exception;
    //日租类型 生成 指定结束日期时所有的账单
    List<ZuPinContactRentingFeeHistoryDTO> obtainZupinContactRiJieFinishNoPayZuJinList(String zuPinContactUuid, LocalDate localDate);

    void updateZuPinContactRiJieFinish(ZuPinContactOverFormDTO zuPinContactOverFormDTO) throws Exception;

    //生成 日结类型 的具体的一个车的账单
    List<ZuPinContactRentingFeeHistoryDTO> obtainZuPinContactRiJieSpecVehicleHistoryDTOList(String zuPinContactUuid, String executeUuid, String vehicleNum , LocalDate now);

    List<ZuPinContactRentingFeeHistory> obtainZuPinContactRiJieSpecVehicleHistoryList(String zuPinContactUuid, String executeUuid, String vehicleNum , LocalDate now);

    void updateZuPinContactVehicleRiJieFinish(ZuPinContactOverFormDTO zuPinContactOverFormDTO);


}
