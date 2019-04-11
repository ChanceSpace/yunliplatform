package com.yajun.green.facade.show;

import com.yajun.green.facade.dto.contact.ZuPinContacctReetingFeeHistoryGroupDTO;
import com.yajun.green.facade.dto.contact.ZuPinContactRentingFeeHistoryDTO;

import java.util.*;

/**
 * Created by LiuMengKe on 2017/12/14.
 *
 * 日结类型合同 账单封装对象
 */
public class ZuPinContactRiJieBalanceObj {

    //封装 车辆车牌号 为 key  的历史记录
    private Map<String, ZuPinContacctReetingFeeHistoryGroupDTO> feeHap;
    //封装 合同结束没有车牌号码的历史记录
    private List<ZuPinContactRentingFeeHistoryDTO> fuJiaList;

    public Map<String, ZuPinContacctReetingFeeHistoryGroupDTO> getFeeHap() {
        return feeHap;
    }

    public void setFeeHap(Map<String, ZuPinContacctReetingFeeHistoryGroupDTO> feeHap) {
        this.feeHap = feeHap;
    }

    public List<ZuPinContactRentingFeeHistoryDTO> getFuJiaList() {
        return fuJiaList;
    }

    public void setFuJiaList(List<ZuPinContactRentingFeeHistoryDTO> fuJiaList) {
        this.fuJiaList = fuJiaList;
    }
}
