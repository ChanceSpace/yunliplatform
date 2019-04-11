package com.yajun.green.facade.dto.contact.xiaoshou;

import com.yajun.green.common.security.LoginInfo;
import com.yajun.green.domain.xiaoshou.*;
import com.yajun.green.facade.show.HighLightUtils;
import com.yajun.green.security.SecurityUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiuMengKe on 2017/12/18.
 */
public class ZuPinXiaoShouContactDTO {

    private String uuid;
    //合同类型
    private XiaoShouType xiaoShouType;
    //是否包电
    private XiaoShouContactBaoDianType baoDianType;
    //状态和审核状态
    private XiaoShouContactStatus contactStatus;
    //乙方类型个人公司
    private XiaoShouContactUserType yiFangType;

    //销售信息和创建者信息
    private String saleManUuid;
    private String saleManName;
    private String creatorUuid;
    private String creatorName;
    //合同创建日期
    private String createTime;
    private String startDate;
    private String endDate;
    private String contactNumber;

    /***************************************合同原始信息，不变部分*************************************/
    //合同甲方乙方信息部分
    private String jiaFangUuid;
    private String jiaFangName;
    private String jiaFangFaRen;
    private String jiaFangAddress;
    private String jiaFangPostCode;
    private String yiFangUuid;
    private String yiFangName;
    private String yiFangFaRen;
    private String yiFangAddress;
    private String yiFangPostCode;

    private String contactPerson;
    private String contactPhone;

    private List<ZuPinXiaoShouVehicleModuleDTO> origModules = new ArrayList<>();;
    //合同补充日志c
    private List<ZuPinXiaoShouContactSupplyDTO> zuPinContactSupplies= new ArrayList<>();;
    //合同执行情况
    private List<ZuPinXiaoShouVehicleExecuteDTO> vehicleExecutes= new ArrayList<>();;
    //合同账单历史
    private List<ZuPinXiaoShouContactRentingFeeHistoryDTO> rentingFeeHistories= new ArrayList<>();;

    //是否可以开始执行合同 必须是module 有一条数据
    private boolean canBeExecute;



    /**************************************当前费用相关*****************************************/

    /**************************************合同欠费相关*****************************************/
    private String  qianFeiBeginTime;
    private boolean qianFei;
    //计算欠费的时候临时用一下
    private BigDecimal qianFeiBalance;

    /***************************************合同执行，变化部分*************************************/
    //执行过程中车辆和状态
    //开始提车
    private boolean beginExecute;
    //结束提车
    private boolean endExecute;



    //直流充电桩数量
    private Integer zhiliuChargeNumber;
    //交流充电桩数量
    private Integer jiaoliuChargeNumber;
    //充电桩
    List<ZuPinXiaoShouContactChargingDTO> chargings;



    /****************************************附件和日志*******************************************/
    //合同附件
    private List<ZuPinXiaoShouContactFileDTO> contactFiles;
    //合同日志
    private List<ZuPinXiaoShouContactLogDTO> contactLogs;

    public ZuPinXiaoShouContactDTO() {
        LoginInfo loginInfo = SecurityUtils.currentLoginUser();
        this.jiaFangUuid = loginInfo.getCompanyUuid();
        this.jiaFangName = loginInfo.getCompanyName();
        this.jiaFangFaRen = loginInfo.getCompanyFaRen();
        this.jiaFangAddress = loginInfo.getCompanyAddress();
        this.jiaFangPostCode = loginInfo.getCompanyPostCode();
        this.contactStatus = XiaoShouContactStatus.S_CREATED;
    }

    public ZuPinXiaoShouContactDTO(String uuid, XiaoShouType xiaoShouType, XiaoShouContactBaoDianType baoDianType, XiaoShouContactStatus contactStatus, XiaoShouContactUserType yiFangType, String saleManUuid, String saleManName, String creatorUuid, String creatorName, String createTime, String  startDate, String endDate, String contactNumber, String jiaFangUuid, String jiaFangName, String jiaFangFaRen, String jiaFangAddress, String jiaFangPostCode, String yiFangUuid, String yiFangName, String yiFangFaRen, String yiFangAddress, String yiFangPostCode, String contactPerson, String contactPhone, boolean beginExecute, boolean endExecute) {
        this.uuid = uuid;
        this.xiaoShouType = xiaoShouType;
        this.baoDianType = baoDianType;
        this.contactStatus = contactStatus;
        this.yiFangType = yiFangType;
        this.saleManUuid = saleManUuid;
        this.saleManName = saleManName;
        this.creatorUuid = creatorUuid;
        this.creatorName = creatorName;
        this.createTime = createTime;
        this.startDate=startDate;
        this.endDate = endDate;
        this.contactNumber = contactNumber;
        this.jiaFangUuid = jiaFangUuid;
        this.jiaFangName = jiaFangName;
        this.jiaFangFaRen = jiaFangFaRen;
        this.jiaFangAddress = jiaFangAddress;
        this.jiaFangPostCode = jiaFangPostCode;
        this.yiFangUuid = yiFangUuid;
        this.yiFangName = yiFangName;
        this.yiFangFaRen = yiFangFaRen;
        this.yiFangAddress = yiFangAddress;
        this.yiFangPostCode = yiFangPostCode;
        this.contactPerson = contactPerson;
        this.contactPhone = contactPhone;
        this.beginExecute = beginExecute;
        this.endExecute = endExecute;
    }




    public void addHighLight(String keywords) {
        contactNumber = HighLightUtils.highLightWords(contactNumber, keywords);
        yiFangName = HighLightUtils.highLightWords(yiFangName, keywords);
    }

    public void addContactModule(ZuPinXiaoShouVehicleModuleDTO contactModule) {
        origModules.add(contactModule);
    }

    public void addExecuteModule(ZuPinXiaoShouVehicleExecuteDTO executeDTO) {
        vehicleExecutes.add(executeDTO);
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /*********************************setter getter*******************************/

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    public XiaoShouType getXiaoShouType() {
        return xiaoShouType;
    }

    public void setXiaoShouType(XiaoShouType xiaoShouType) {
        this.xiaoShouType = xiaoShouType;
    }

    public XiaoShouContactBaoDianType getBaoDianType() {
        return baoDianType;
    }

    public void setBaoDianType(XiaoShouContactBaoDianType baoDianType) {
        this.baoDianType = baoDianType;
    }

    public XiaoShouContactStatus getContactStatus() {
        return contactStatus;
    }

    public void setContactStatus(XiaoShouContactStatus contactStatus) {
        this.contactStatus = contactStatus;
    }

    public XiaoShouContactUserType getYiFangType() {
        return yiFangType;
    }

    public void setYiFangType(XiaoShouContactUserType yiFangType) {
        this.yiFangType = yiFangType;
    }

    public String getSaleManUuid() {
        return saleManUuid;
    }

    public void setSaleManUuid(String saleManUuid) {
        this.saleManUuid = saleManUuid;
    }

    public String getSaleManName() {
        return saleManName;
    }

    public void setSaleManName(String saleManName) {
        this.saleManName = saleManName;
    }

    public String getCreatorUuid() {
        return creatorUuid;
    }

    public void setCreatorUuid(String creatorUuid) {
        this.creatorUuid = creatorUuid;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }



    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getJiaFangUuid() {
        return jiaFangUuid;
    }

    public void setJiaFangUuid(String jiaFangUuid) {
        this.jiaFangUuid = jiaFangUuid;
    }

    public String getJiaFangName() {
        return jiaFangName;
    }

    public void setJiaFangName(String jiaFangName) {
        this.jiaFangName = jiaFangName;
    }

    public String getJiaFangFaRen() {
        return jiaFangFaRen;
    }

    public void setJiaFangFaRen(String jiaFangFaRen) {
        this.jiaFangFaRen = jiaFangFaRen;
    }

    public String getJiaFangAddress() {
        return jiaFangAddress;
    }

    public void setJiaFangAddress(String jiaFangAddress) {
        this.jiaFangAddress = jiaFangAddress;
    }

    public String getJiaFangPostCode() {
        return jiaFangPostCode;
    }

    public void setJiaFangPostCode(String jiaFangPostCode) {
        this.jiaFangPostCode = jiaFangPostCode;
    }

    public String getYiFangUuid() {
        return yiFangUuid;
    }

    public void setYiFangUuid(String yiFangUuid) {
        this.yiFangUuid = yiFangUuid;
    }

    public String getYiFangName() {
        return yiFangName;
    }

    public void setYiFangName(String yiFangName) {
        this.yiFangName = yiFangName;
    }

    public String getYiFangFaRen() {
        return yiFangFaRen;
    }

    public void setYiFangFaRen(String yiFangFaRen) {
        this.yiFangFaRen = yiFangFaRen;
    }

    public String getYiFangAddress() {
        return yiFangAddress;
    }

    public void setYiFangAddress(String yiFangAddress) {
        this.yiFangAddress = yiFangAddress;
    }

    public String getYiFangPostCode() {
        return yiFangPostCode;
    }

    public void setYiFangPostCode(String yiFangPostCode) {
        this.yiFangPostCode = yiFangPostCode;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public List<ZuPinXiaoShouVehicleModuleDTO> getOrigModules() {
        return origModules;
    }

    public void setOrigModules(List<ZuPinXiaoShouVehicleModuleDTO> origModules) {
        this.origModules = origModules;
    }

    public List<ZuPinXiaoShouContactSupplyDTO> getZuPinContactSupplies() {
        return zuPinContactSupplies;
    }

    public void setZuPinContactSupplies(List<ZuPinXiaoShouContactSupplyDTO> zuPinContactSupplies) {
        this.zuPinContactSupplies = zuPinContactSupplies;
    }

    public List<ZuPinXiaoShouVehicleExecuteDTO> getVehicleExecutes() {
        return vehicleExecutes;
    }

    public void setVehicleExecutes(List<ZuPinXiaoShouVehicleExecuteDTO> vehicleExecutes) {
        this.vehicleExecutes = vehicleExecutes;
    }

    public List<ZuPinXiaoShouContactRentingFeeHistoryDTO> getRentingFeeHistories() {
        return rentingFeeHistories;
    }

    public void setRentingFeeHistories(List<ZuPinXiaoShouContactRentingFeeHistoryDTO> rentingFeeHistories) {
        this.rentingFeeHistories = rentingFeeHistories;
    }

    public String getQianFeiBeginTime() {
        return qianFeiBeginTime;
    }

    public void setQianFeiBeginTime(String qianFeiBeginTime) {
        this.qianFeiBeginTime = qianFeiBeginTime;
    }

    public boolean isQianFei() {
        return qianFei;
    }

    public void setQianFei(boolean qianFei) {
        this.qianFei = qianFei;
    }

    public BigDecimal getQianFeiBalance() {
        return qianFeiBalance;
    }

    public void setQianFeiBalance(BigDecimal qianFeiBalance) {
        this.qianFeiBalance = qianFeiBalance;
    }

    public boolean isBeginExecute() {
        return beginExecute;
    }

    public void setBeginExecute(boolean beginExecute) {
        this.beginExecute = beginExecute;
    }

    public boolean isEndExecute() {
        return endExecute;
    }

    public void setEndExecute(boolean endExecute) {
        this.endExecute = endExecute;
    }

    public List<ZuPinXiaoShouContactFileDTO> getContactFiles() {
        return contactFiles;
    }

    public void setContactFiles(List<ZuPinXiaoShouContactFileDTO> contactFiles) {
        this.contactFiles = contactFiles;
    }

    public List<ZuPinXiaoShouContactLogDTO> getContactLogs() {
        return contactLogs;
    }

    public void setContactLogs(List<ZuPinXiaoShouContactLogDTO> contactLogs) {
        this.contactLogs = contactLogs;
    }


    public boolean isCanBeExecute() {
        return origModules.size()==1?true:false;
    }

    public void setCanBeExecute(boolean canBeExecute) {
        this.canBeExecute = canBeExecute;
    }

    public Integer getZhiliuChargeNumber() {
        return zhiliuChargeNumber;
    }

    public void setZhiliuChargeNumber(Integer zhiliuChargeNumber) {
        this.zhiliuChargeNumber = zhiliuChargeNumber;
    }

    public Integer getJiaoliuChargeNumber() {
        return jiaoliuChargeNumber;
    }

    public void setJiaoliuChargeNumber(Integer jiaoliuChargeNumber) {
        this.jiaoliuChargeNumber = jiaoliuChargeNumber;
    }

    public List<ZuPinXiaoShouContactChargingDTO> getChargings() {
        return chargings;
    }

    public void setChargings(List<ZuPinXiaoShouContactChargingDTO> chargings) {
        this.chargings = chargings;
    }
}
