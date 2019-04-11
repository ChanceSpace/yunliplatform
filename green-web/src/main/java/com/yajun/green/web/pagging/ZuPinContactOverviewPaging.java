package com.yajun.green.web.pagging;


import com.yajun.green.service.ZuPinContactService;
import com.yajun.green.facade.dto.contact.ZuPinContactDTO;
import com.yajun.green.web.pagging.AbstractPaging;

import java.util.List;

/**
 * Created by LiuMengKe on 2017/8/9.
 */
public class ZuPinContactOverviewPaging extends AbstractPaging<ZuPinContactDTO> {

    private ZuPinContactService zuPinContactService;

    private String keyWords;
    private String userUuid;
    private String startTime;
    private String endTime;
    private String contactStatus;

    private String sortBy;
    private String sortWay;

    public ZuPinContactOverviewPaging(ZuPinContactService zuPinContactService) {
        this.zuPinContactService = zuPinContactService;
    }

    @Override
    public List<ZuPinContactDTO> getItems() {
        return zuPinContactService.obtainOverviewZuPinContact(keyWords, userUuid, startTime, endTime, sortBy, sortWay, contactStatus, getStartPosition(), getPageSize());
    }

    @Override
    public long getTotalItemSize() {
        if (totalItemSize >= 0) {
            return totalItemSize;
        }
        totalItemSize = zuPinContactService.obtainOverviewZuPinContactSize(keyWords, userUuid, startTime, endTime, sortBy, sortWay, contactStatus);

        return totalItemSize;
    }

    @Override
    public String getParameterValues() {
        return "&keyWords=" + getKeyWords() + "&userUuid" + getUserUuid() + "&startTime=" + getStartTime() + "&endTime=" + getEndTime() +
                "&sortBy=" + getSortBy() + "&sortWay" + getSortWay() + "&contactStatus=" + getContactStatus() + "&init=true";
    }

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
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

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortWay() {
        return sortWay;
    }

    public void setSortWay(String sortWay) {
        this.sortWay = sortWay;
    }

    public String getContactStatus() {
        return contactStatus;
    }

    public void setContactStatus(String contactStatus) {
        this.contactStatus = contactStatus;
    }
}
