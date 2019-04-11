package com.yajun.green.web.pagging;

import com.yajun.green.facade.dto.pay.PayOrderDTO;
import com.yajun.green.service.PayOrderService;

import java.util.List;

/**
 * Created by chance on 2017/10/19.
 */
public class PayOrderCarrierOverviewPaging extends AbstractPaging<PayOrderDTO> {

    private PayOrderService payOrderService;

    private String userUuid;
    private String startTime;
    private String endTime;
    private String keyWords;
    private String userType;
    private String selPayType;

    public PayOrderCarrierOverviewPaging(PayOrderService payOrderService) {
        this.payOrderService = payOrderService;
    }

    @Override
    public String getParameterValues() {
        return "&userUuid=" + getUserUuid() + "&startTime=" + getStartTime() + "&endTime=" + getEndTime() + "&keyWords=" + getKeyWords() + "&userType" + getUserType() + "&selPayType" + getSelPayType();
    }

    @Override
    public List<PayOrderDTO> getItems() {
        return payOrderService.obtainOverviewCarrierPayOrder(keyWords, userUuid, userType, startTime, endTime,selPayType, getStartPosition(), getPageSize());
    }

    @Override
    public long getTotalItemSize() {
        if (totalItemSize >= 0) {
            return totalItemSize;
        }
        totalItemSize = payOrderService.obtainOverviewCarrierPayOrderSize(keyWords, userUuid, userType, startTime, endTime,selPayType);
        return totalItemSize;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getSelPayType() {
        return selPayType;
    }

    public void setSelPayType(String selPayType) {
        this.selPayType = selPayType;
    }
}
