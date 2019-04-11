package com.yajun.green.facade.dto.admin;

import com.yajun.green.common.log.ApplicationLog;
import com.yajun.green.common.utils.JodaUtils;
import com.yajun.green.facade.dto.contact.ZuPinContactChargingDTO;
import com.yajun.green.facade.show.HighLightUtils;
import org.joda.time.LocalDate;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Jack Wang
 * Date: 17-8-28
 * Time: 下午1:12
 */
public class CompanyDTO {

    private String uuid;
    private String name;
    private String shortName;

    private String registerTime;
    private String userNote;

    private String userType;
    private String userTypeName;
    private String serviceType;
    private String serviceTypeName;

    private String num;
    private String mobile;
    private String faRen;
    private String postCode;
    private String address;
    private String bankName;
    private String bankNum;

    private boolean firstLevelCarry = false;
    private String parentUuid = "0";
    private String parentName;

    private BigDecimal currentBalance;
    //欠费相关
    private LocalDate qianFeiBeginTime;
    //欠费金额
    private BigDecimal qianFeiBalance = BigDecimal.valueOf(0);

    public CompanyDTO() {
    }

    public CompanyDTO(String uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

    public CompanyDTO(String uuid, String name, String registerTime, String userNote,
                      String userType, String userTypeName, String serviceType, String serviceTypeName,
                      String num, String mobile, String faRen, String postCode, String address,
                      BigDecimal currentBalance, BigDecimal qianFeiBalance, String qianFeiBeginTime) {
        this.uuid = uuid;
        this.name = name;

        this.registerTime = registerTime;
        this.userNote = userNote;
        this.userType = userType;
        this.userTypeName = userTypeName;
        this.serviceType = serviceType;
        this.serviceTypeName = serviceTypeName;
        this.num = num;
        this.mobile = mobile;
        this.faRen = faRen;
        this.postCode = postCode;
        this.address = address;

        this.currentBalance = currentBalance;
        this.qianFeiBalance = qianFeiBalance;
        if (StringUtils.hasText(qianFeiBeginTime)) {
            this.qianFeiBeginTime = new LocalDate(qianFeiBeginTime);
        }
    }

    public void setMinQianFeiDayTemp(LocalDate date){
        if(qianFeiBeginTime == null){
            qianFeiBeginTime = date;
            ApplicationLog.info(CompanyDTO.class, "公司" + getName() + "最小欠费日期为" + qianFeiBeginTime);
        }else {
            //-1 结束时间小于开始时间
            int compareResult = JodaUtils.compareDays(qianFeiBeginTime, date);
            if (compareResult < 0) {
                qianFeiBeginTime = date;
                ApplicationLog.info(CompanyDTO.class, "公司" + getName() + "最小欠费日期为" + qianFeiBeginTime);
            }
        }
    }

    /**************************************Getter/Setter*****************************************/

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }

    public String getUserNote() {
        return userNote;
    }

    public void setUserNote(String userNote) {
        this.userNote = userNote;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserTypeName() {
        return userTypeName;
    }

    public void setUserTypeName(String userTypeName) {
        this.userTypeName = userTypeName;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getServiceTypeName() {
        return serviceTypeName;
    }

    public void setServiceTypeName(String serviceTypeName) {
        this.serviceTypeName = serviceTypeName;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFaRen() {
        return faRen;
    }

    public void setFaRen(String faRen) {
        this.faRen = faRen;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankNum() {
        return bankNum;
    }

    public void setBankNum(String bankNum) {
        this.bankNum = bankNum;
    }

    public String getParentUuid() {
        return parentUuid;
    }

    public void setParentUuid(String parentUuid) {
        this.parentUuid = parentUuid;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public boolean isFirstLevelCarry() {
        return firstLevelCarry;
    }

    public void setFirstLevelCarry(boolean firstLevelCarry) {
        this.firstLevelCarry = firstLevelCarry;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }

    public LocalDate getQianFeiBeginTime() {
        return qianFeiBeginTime;
    }

    public void setQianFeiBeginTime(LocalDate qianFeiBeginTime) {
        this.qianFeiBeginTime = qianFeiBeginTime;
    }

    public BigDecimal getQianFeiBalance() {
        return qianFeiBalance;
    }

    public void setQianFeiBalance(BigDecimal qianFeiBalance) {
        this.qianFeiBalance = qianFeiBalance;
    }
}
