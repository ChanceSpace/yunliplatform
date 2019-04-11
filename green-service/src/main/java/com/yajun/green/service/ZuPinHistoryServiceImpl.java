package com.yajun.green.service;


import com.yajun.green.common.log.ApplicationLog;
import com.yajun.green.common.utils.CHStringUtils;
import com.yajun.green.common.utils.JodaUtils;
import com.yajun.green.domain.contact.ZuPinContactRentingFeeHistory;
import com.yajun.green.domain.contact.ZuPinVehicleExecute;
import com.yajun.green.facade.assember.contact.ZuPinContactRentingFeeHistoryWebAssember;
import com.yajun.green.facade.assember.contact.ZuPinVehicleExecuteWebAssember;
import com.yajun.green.facade.dto.contact.ZuPinContacctReetingFeeHistoryGroupDTO;
import com.yajun.green.facade.dto.contact.ZuPinContactRentingFeeHistoryDTO;
import com.yajun.green.facade.dto.contact.ZuPinVehicleExecuteDTO;
import com.yajun.green.facade.show.ZuPinContactRiJieBalanceObj;
import com.yajun.green.repository.ZuPinHistoryDao;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Created by LiuMengKe on 2017/8/9.
 */
@Service("zuPinHistoryService")
public class ZuPinHistoryServiceImpl implements ZuPinHistoryService {

    @Autowired
    private ZuPinHistoryDao zuPinHistoryDao;

    /*@Override
    public void saveZuPinContactRentingFeeHistory(ZuPinContactRentingFeeHistoryDTO zuPinContactRentingFeeHistoryDTO) {
        zuPinHistoryDao.saveOrUpdate(ZuPinContactRentingFeeHistoryWebAssember.toRentFeeHistoryDomain(zuPinContactRentingFeeHistoryDTO));
    }*/

    @Override
    public Map<String, ZuPinContacctReetingFeeHistoryGroupDTO> obtainZuPinContactMinXi(String zupinContactUuid) {
        List<ZuPinContactRentingFeeHistory> historyList = zuPinHistoryDao.obtainOverviewZuPinContactRentingFeeHistoryList(zupinContactUuid, null, 0, 0);
        List<ZuPinContactRentingFeeHistoryDTO> histories = ZuPinContactRentingFeeHistoryWebAssember.toRentFeeHistoryDTOList(historyList);
        //缴纳月份作为key 时间倒序排序
        Map<String, ZuPinContacctReetingFeeHistoryGroupDTO> feeHap = new TreeMap<>(new Comparator<String>(){
            @Override
            public int compare(String a, String b){
                return - a.compareTo(b);
            }
        });

        for (ZuPinContactRentingFeeHistoryDTO history : histories) {
            LocalDate localDate = history.getActualFeeDate();
            String yearAndMonth = JodaUtils.toStringYearMonth(localDate);
            ZuPinContacctReetingFeeHistoryGroupDTO group = feeHap.get(yearAndMonth);
            if (group == null) {
                group = new ZuPinContacctReetingFeeHistoryGroupDTO();
            }
            group.addFee(history);
            feeHap.put(yearAndMonth,group);
        }

        return feeHap;
    }

    @Override
    public Map<String, ZuPinContacctReetingFeeHistoryGroupDTO> obtainZuPinContactMinXiByVecle(String zupinContactUuid) {
        List<ZuPinContactRentingFeeHistory> historyList = zuPinHistoryDao.obtainOverviewZuPinContactRentingFeeHistoryList(zupinContactUuid, null, 0, 0);
        List<ZuPinContactRentingFeeHistoryDTO> histories = ZuPinContactRentingFeeHistoryWebAssember.toRentFeeHistoryDTOList(historyList);
        //缴纳月份作为key 时间倒序排序
        Map<String, ZuPinContacctReetingFeeHistoryGroupDTO> feeHap = new TreeMap<>(new Comparator<String>(){
            @Override
            public int compare(String a, String b){
                return - a.compareTo(b);
            }
        });
        for (ZuPinContactRentingFeeHistoryDTO history : histories) {
            String  vehicleNumber  =  history.getVehicleNumber();
            ZuPinContacctReetingFeeHistoryGroupDTO group = null;

            if(StringUtils.hasText(vehicleNumber)){
                group = feeHap.get(vehicleNumber);
            }else {
                group = feeHap.get(zupinContactUuid);
            }

            if (group == null) {
                group = new ZuPinContacctReetingFeeHistoryGroupDTO();
            }
            group.addFee(history);
            //todo 当没有vehicleNumber 的时候 放进 map 中会空指针 看前付后付有无这种情况
            if(StringUtils.hasText(vehicleNumber)){
                feeHap.put(vehicleNumber,group);
            }else {
                feeHap.put(zupinContactUuid,group);
            }
        }
        return feeHap;
    }

    @Override
    public List<ZuPinVehicleExecuteDTO> obtainReachNextRentFeeDateVehicle(LocalDate localDate, int term) {
        //当前时间加上term后 再去和后台的nextRentFee比对
        LocalDate newDate =  localDate.plusDays(term);
        ApplicationLog.info(ZuPinHistoryServiceImpl.class,term+"预计到期时间"+newDate.toString());
        List<ZuPinVehicleExecute> historyDTOList = zuPinHistoryDao.obtainReachNextRentFeeDateVehicle(newDate);
        return ZuPinVehicleExecuteWebAssember.toZuPinVehicleExecuteDTOList(historyDTOList);
    }

    @Override
    public List<ZuPinVehicleExecuteDTO> obtainReachNextRentFeeDateAndMoneyEmptyVehicle(int term) {
        //获取当前时间
        LocalDate date = new LocalDate();
        //当前时间加上term后 再去和后台的nextRentFee比对
        LocalDate newDate =  date.plusDays(term);
        ApplicationLog.info(ZuPinHistoryServiceImpl.class,term+"预计到期时间"+newDate.toString());
        List<ZuPinVehicleExecute> historyDTOList = zuPinHistoryDao.obtainReachNextRentFeeDateAndMoneyEmptyVehicle(newDate);
        return ZuPinVehicleExecuteWebAssember.toZuPinVehicleExecuteDTOList(historyDTOList);
    }


    @Override
    public ZuPinContactRiJieBalanceObj obtainZuPinContactRiJieMinXi(String zuPinContactUuid) {
        List<ZuPinContactRentingFeeHistory> historyList = zuPinHistoryDao.obtainOverviewZuPinContactRentingFeeHistoryList(zuPinContactUuid, null, 0, 0);
        List<ZuPinContactRentingFeeHistoryDTO> histories = ZuPinContactRentingFeeHistoryWebAssember.toRentFeeHistoryDTOList(historyList);
        //缴纳月份作为key 时间倒序排序
        Map<String, ZuPinContacctReetingFeeHistoryGroupDTO> feeHap = new TreeMap<>(new Comparator<String>(){
            @Override
            public int compare(String a, String b){
                return - a.compareTo(b);
            }
        });
        List<ZuPinContactRentingFeeHistoryDTO> additionHistorys = new ArrayList<>();
        for (ZuPinContactRentingFeeHistoryDTO history : histories) {
            String  vehicleNumber  =  history.getVehicleNumber();
            ZuPinContacctReetingFeeHistoryGroupDTO group = null;
            //共分为 有车牌号的情况和没有车牌号的情况
            if(StringUtils.hasText(vehicleNumber)){
                group = feeHap.get(vehicleNumber);
                if (group == null) {
                    group = new ZuPinContacctReetingFeeHistoryGroupDTO();
                }
                group.addFee(history);
                feeHap.put(vehicleNumber,group);
            }else {
                additionHistorys.add(history);
            }
        }
        ZuPinContactRiJieBalanceObj balanceObj = new ZuPinContactRiJieBalanceObj();
        balanceObj.setFeeHap(feeHap);
        balanceObj.setFuJiaList(additionHistorys);
        return balanceObj;
    }
}
