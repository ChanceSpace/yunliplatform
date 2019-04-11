package com.yajun.green.common.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Jack Wang
 * Date: 17-11-10
 * Time: 下午2:50
 */
public class LoginInfo implements Serializable {

    private String userUuid;
    private String xingMing;
    private String username;
    private String mobile;

    private String companyUuid;
    private String companyName;
    private String companyType;
    private String companyFaRen;
    private String companyPostCode;
    private String companyAddress;
    private String companyUuidAbove;
    private String companyNameAbove;
    private String companyTypeAbove;

    private boolean firstLevelCarry;
    private boolean administrator = false;

    private List<String> roles = new ArrayList<>();

    public LoginInfo() {
    }

    public LoginInfo(String userUuid, String xingMing, String username, String mobile, String companyUuid, String companyName, String companyType,
                     String companyFaRen, String companyPostCode, String companyAddress,
                     String companyUuidAbove, String companyNameAbove, String companyTypeAbove, boolean firstLevelCarry) {
        this.userUuid = userUuid;
        this.xingMing = xingMing;
        this.username = username;
        this.mobile = mobile;

        this.companyUuid = companyUuid;
        this.companyName = companyName;
        this.companyType = companyType;
        this.companyFaRen = companyFaRen;
        this.companyPostCode = companyPostCode;
        this.companyAddress = companyAddress;

        this.companyUuidAbove = companyUuidAbove;
        this.companyNameAbove = companyNameAbove;
        this.companyTypeAbove = companyTypeAbove;
        this.firstLevelCarry = firstLevelCarry;
    }

    public void addRole(String role) {
        roles.add(role);
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    public String getXingMing() {
        return xingMing;
    }

    public void setXingMing(String xingMing) {
        this.xingMing = xingMing;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCompanyUuid() {
        return companyUuid;
    }

    public void setCompanyUuid(String companyUuid) {
        this.companyUuid = companyUuid;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public String getCompanyFaRen() {
        return companyFaRen;
    }

    public void setCompanyFaRen(String companyFaRen) {
        this.companyFaRen = companyFaRen;
    }

    public String getCompanyPostCode() {
        return companyPostCode;
    }

    public void setCompanyPostCode(String companyPostCode) {
        this.companyPostCode = companyPostCode;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyUuidAbove() {
        return companyUuidAbove;
    }

    public void setCompanyUuidAbove(String companyUuidAbove) {
        this.companyUuidAbove = companyUuidAbove;
    }

    public String getCompanyNameAbove() {
        return companyNameAbove;
    }

    public void setCompanyNameAbove(String companyNameAbove) {
        this.companyNameAbove = companyNameAbove;
    }

    public String getCompanyTypeAbove() {
        return companyTypeAbove;
    }

    public void setCompanyTypeAbove(String companyTypeAbove) {
        this.companyTypeAbove = companyTypeAbove;
    }

    public boolean getFirstLevelCarry() {
        return firstLevelCarry;
    }

    public void setFirstLevelCarry(boolean firstLevelCarry) {
        this.firstLevelCarry = firstLevelCarry;
    }

    public boolean isAdministrator() {
        return administrator;
    }

    public void setAdministrator(boolean administrator) {
        this.administrator = administrator;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
