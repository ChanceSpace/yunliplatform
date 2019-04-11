package com.yajun.green.facade.dto.contact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiuMengKe on 2017/9/7.
 */
public class ZuPinContacctReetingFeeHistoryGroupDTO {

    private String groupKey;

    private List<ZuPinContactRentingFeeHistoryDTO> addZuJinFees = new ArrayList<>();
    private List<ZuPinContactRentingFeeHistoryDTO> subZuJinFees = new ArrayList<>();
    private List<ZuPinContactRentingFeeHistoryDTO> subYaJinFees = new ArrayList<>();
    private List<ZuPinContactRentingFeeHistoryDTO> addYaJinFees = new ArrayList<>();

    public void addFee(ZuPinContactRentingFeeHistoryDTO historyDTO) {
        String name = historyDTO.getZuPinContactRepayLocation().name();
        //提车或者自动还款 租金处理
        if(name.contains("ZJ_SCHEDULE")||name.contains("ZJ_VEHICLE_SUB")){
            subZuJinFees.add(historyDTO);
        }
        if(name.contains("YJ_SCHEDULE")||name.contains("YJ_VEHICLE_SUB")){
            subYaJinFees.add(historyDTO);
        }
        if("L_ZJ_MAN".equals(name)){
            addZuJinFees.add(historyDTO);
        }
        if("L_YJ_MAN".equals(name)){
            addYaJinFees.add(historyDTO);
        }
        if("L_YJ_MANSUB".equals(name)){
            addYaJinFees.add(historyDTO);
        }
        if("L_JS_IN".equals(name)){
            addZuJinFees.add(historyDTO);
        }
        if("L_FJ_IN".equals(name)){
            addZuJinFees.add(historyDTO);
        }
        if("L_JS_OUT".equals(name)){
            subZuJinFees.add(historyDTO);
        }
        if("L_FJ_OUT".equals(name)){
            subZuJinFees.add(historyDTO);
        }
    }

    public String getGroupKey() {
        return groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public List<ZuPinContactRentingFeeHistoryDTO> getAddZuJinFees() {
        return addZuJinFees;
    }

    public void setAddZuJinFees(List<ZuPinContactRentingFeeHistoryDTO> addZuJinFees) {
        this.addZuJinFees = addZuJinFees;
    }

    public List<ZuPinContactRentingFeeHistoryDTO> getSubZuJinFees() {
        return subZuJinFees;
    }

    public void setSubZuJinFees(List<ZuPinContactRentingFeeHistoryDTO> subZuJinFees) {
        this.subZuJinFees = subZuJinFees;
    }

    public List<ZuPinContactRentingFeeHistoryDTO> getSubYaJinFees() {
        return subYaJinFees;
    }

    public void setSubYaJinFees(List<ZuPinContactRentingFeeHistoryDTO> subYaJinFees) {
        this.subYaJinFees = subYaJinFees;
    }

    public List<ZuPinContactRentingFeeHistoryDTO> getAddYaJinFees() {
        return addYaJinFees;
    }

    public void setAddYaJinFees(List<ZuPinContactRentingFeeHistoryDTO> addYaJinFees) {
        this.addYaJinFees = addYaJinFees;
    }
}
