package com.yajun.green.service;


import com.yajun.green.common.log.ApplicationLog;
import com.yajun.green.facade.dto.contact.ZuPinVehicleExecuteDTO;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by LiuMengKe on 2017/8/23.
 * 线程帮助类用来获取即将到期或者已经到期的车辆
 */
@Service("smsautoscanvehicleexecuteImpl")
public class SMSAutoScanVehicleExecuteImpl {

    @Autowired
    private ZuPinHistoryService zuPinHistoryService;


    /**
     * @Author Liu MengKe
     * @Description: 根据制定时间间隔 当天+制定时间间隔 等于下次还款时间的所有记录
     */
    public  List<ZuPinVehicleExecuteDTO> getReachNextRentFeeVehicle(LocalDate localDate, int term){
        List<ZuPinVehicleExecuteDTO> historyDTOList =  zuPinHistoryService.obtainReachNextRentFeeDateVehicle(localDate,term);
        ApplicationLog.info(SMSAutoScanVehicleExecuteImpl.class,"测试获取间隔天数"+term+"数量=========="+historyDTOList.size());
        return  historyDTOList;
    }

    /******获取短信提醒记录******/
    public  List<ZuPinVehicleExecuteDTO> getMessageWarnInngExecuteList(int term){
        List<ZuPinVehicleExecuteDTO> historyDTOList =  zuPinHistoryService.obtainReachNextRentFeeDateAndMoneyEmptyVehicle(term);
        ApplicationLog.info(SMSAutoScanVehicleExecuteImpl.class,"测试获取间隔天数"+term+"数量=========="+historyDTOList.size());
        return  historyDTOList;
    }
}
