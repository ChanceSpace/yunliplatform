package com.yajun.green.facade.dto.admin;

import com.yajun.green.facade.dto.contact.ZuPinContactChargingDTO;
import com.yajun.green.facade.show.HighLightUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Jack Wang
 * Date: 17-8-28
 * Time: 下午1:12
 */
public class UserAccountDTO {

    private String uuid;
    private String name;
    private String username;
    private String password;

    private String registerTime;
    private String userNote;
    private boolean enabled = true;
    private String mobile;
    private String companyUuid;

    public UserAccountDTO() {
    }

    public UserAccountDTO(String uuid, String name, String registerTime, String userNote, String mobile) {
        this.uuid = uuid;
        this.name = name;
        this.registerTime = registerTime;
        this.userNote = userNote;
        this.mobile = mobile;
    }

    /**************************************Getter/Setter*****************************************/

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCompanyUuid() {
        return companyUuid;
    }

    public void setCompanyUuid(String companyUuid) {
        this.companyUuid = companyUuid;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
