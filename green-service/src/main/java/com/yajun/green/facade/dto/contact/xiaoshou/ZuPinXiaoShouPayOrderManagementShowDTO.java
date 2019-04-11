package com.yajun.green.facade.dto.contact.xiaoshou;

import com.yajun.green.facade.dto.contact.ZuPinContactRentingFeeHistoryDTO;
import com.yajun.green.facade.dto.pay.PayItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiuMengKe on 2017/10/17.
 *  按照合同显示账单
 */
public class ZuPinXiaoShouPayOrderManagementShowDTO {

    private String jiaFang;

    private String yiFang;

    private String contactNumber;

    private List<ZuPinXiaoShouContactRentingFeeHistoryDTO> histories = new ArrayList<>();

    public ZuPinXiaoShouPayOrderManagementShowDTO(String jiaFang, String yiFang, String contactNumber) {
        this.jiaFang = jiaFang;
        this.yiFang = yiFang;
        this.contactNumber = contactNumber;
    }

    public void addHistory(ZuPinXiaoShouContactRentingFeeHistoryDTO history) {
        histories.add(history);
    }

    public void removeAlreadyTakenPayment(List<PayItem> payItems) {
        List<String> remove = new ArrayList<>();
        for (PayItem payItem : payItems) {
            remove.add(payItem.getUuid());
        }

        List<ZuPinXiaoShouContactRentingFeeHistoryDTO> histories = new ArrayList<>();
        for (ZuPinXiaoShouContactRentingFeeHistoryDTO history : this.histories) {
            if (remove.contains(history.getUuid())) {
                histories.add(history);
            }
        }

        this.histories.removeAll(histories);
    }

    /*********************************setter getter*******************************/

    public String getJiaFang() {
        return jiaFang;
    }

    public void setJiaFang(String jiaFang) {
        this.jiaFang = jiaFang;
    }

    public String getYiFang() {
        return yiFang;
    }

    public void setYiFang(String yiFang) {
        this.yiFang = yiFang;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public List<ZuPinXiaoShouContactRentingFeeHistoryDTO> getHistories() {
        return histories;
    }

    public void setHistories(List<ZuPinXiaoShouContactRentingFeeHistoryDTO> histories) {
        this.histories = histories;
    }

}
