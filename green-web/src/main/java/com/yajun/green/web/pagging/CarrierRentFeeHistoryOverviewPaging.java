package com.yajun.green.web.pagging;

import com.yajun.green.domain.chengyun.CarrierRentFeeHistory;
import com.yajun.green.facade.assember.chengyun.CarrierRentFeeHistoryWebAssember;
import com.yajun.green.facade.dto.chengyun.CarrierRentFeeHistoryDTO;
import com.yajun.green.service.ChengYunService;

import java.util.List;

/**
 * Created by chance on 2017/10/16.
 */
public class CarrierRentFeeHistoryOverviewPaging extends AbstractPaging<CarrierRentFeeHistoryDTO> {

    private ChengYunService chengYunService;

    private String keywords;
    private String userUuid;
    private String startTime;
    private String endTime;

    public CarrierRentFeeHistoryOverviewPaging(ChengYunService chengYunService) {
        this.chengYunService = chengYunService;
    }

    @Override
    public String getParameterValues() {
        return "&keywords=" + getKeywords() + "&userUuid=" + getUserUuid() + "&startTime=" + getStartTime() + "&endTime" + getEndTime();
    }

    @Override
    public List<CarrierRentFeeHistoryDTO> getItems() {
        return null;
    }

    @Override
    public long getTotalItemSize() {
        if (totalItemSize >= 0) {
            return totalItemSize;
        }
        totalItemSize = 1;
        return totalItemSize;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
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
}
