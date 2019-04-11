package com.yajun.green.web.utils.xiaoshou;

import java.io.Serializable;

/**
 * Created by LiuMengKe on 2017/12/1.
 */
public class XiaoShouContactSearchObject implements Serializable{
    private int current;
    private String keyWords ;
    private String userUuid ;
    private String startTime ;
    private String endTime ;
    private String sortBy ;
    private String sortWay ;
    private String contactStatus;

    public XiaoShouContactSearchObject(int current, String keyWords, String userUuid, String startTime, String endTime, String sortBy, String sortWay, String contactStatus) {
        this.current = current;
        this.keyWords = keyWords;
        this.userUuid = userUuid;
        this.startTime = startTime;
        this.endTime = endTime;
        this.sortBy = sortBy;
        this.sortWay = sortWay;
        this.contactStatus = contactStatus;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
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
