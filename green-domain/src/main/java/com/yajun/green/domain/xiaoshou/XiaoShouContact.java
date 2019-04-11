package com.yajun.green.domain.xiaoshou;

import com.yajun.green.common.domain.EntityBase;
import com.yajun.green.common.security.LoginInfo;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by LiuMengKe on 2017/12/19.
 *
 * 销售合同
 *
 */
public class XiaoShouContact extends EntityBase {
    //合同类型
    private XiaoShouType contactType;
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
    private DateTime createTime;
    private LocalDate startDate;
    private LocalDate endDate;
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

    //直流充电桩数量
    private int zhiliuChargeNumber;
    //交流充电桩数量
    private int jiaoliuChargeNumber;

    private List<XiaoShouVehicleModule> origModules;
     //合同执行情况
    private List<XiaoShouVehicleExecute> vehicleExecutes;
    //合同补充日志c
    private List<XiaoShouContactSupply> zuPinContactSupplies;
    //充电桩
    private List<XiaoShouContactCharging> chargings;
    //合同账单历史
    private List<XiaoShouContactRentingFeeHistory> rentingFeeHistories;

    /**************************************合同欠费相关*****************************************/
    private LocalDate qianFeiBeginTime;
    private boolean qianFei;
    //计算欠费的时候临时用一下
    private BigDecimal qianFeiBalance;

    /***************************************合同执行，变化部分*************************************/
    //执行过程中车辆和状态
    //开始提车
    private boolean beginExecute;
    //结束提车
    private boolean endExecute;

    /****************************************附件和日志*******************************************/
    //合同附件
    private List<XiaoShouContactFile> contactFiles;
    //合同日志
    private List<XiaoShouContactLog> contactLogs;

    public XiaoShouContact() {
        this.createTime = new DateTime();
        this.startDate = new LocalDate();
        this.endDate = new LocalDate();
        this.contactStatus = XiaoShouContactStatus.S_CREATED;
        this.beginExecute = false;
        this.endExecute = false;
    }

    public XiaoShouVehicleExecute getSpecExecuteByVehicleNumber(String vehicleNumber){
        for (XiaoShouVehicleExecute vehicleExecute : vehicleExecutes) {
            if(vehicleExecute.getVehicleNum().equals(vehicleNumber)){
                return  vehicleExecute;
            }
        }
        return null;
    }

    public XiaoShouContactLog resetContactStatus(LoginInfo loginInfo, XiaoShouContactStatus xiaoShouContactStatus) {
        XiaoShouContactLog log = null;

        //销售从创建到合同上报过程
        if (this.contactStatus.equals(XiaoShouContactStatus.S_CREATED) && xiaoShouContactStatus.equals(XiaoShouContactStatus.S_PROCESSING)) {
            this.contactStatus = xiaoShouContactStatus;
            log = new XiaoShouContactLog(loginInfo.getUserUuid(), loginInfo.getXingMing(), "完成合同上报，开始执行合同");
            log.setXiaoShouContact(this);
        }

        if (this.contactStatus.equals(XiaoShouContactStatus.S_PROCESSING) && xiaoShouContactStatus.equals(XiaoShouContactStatus.S_CREATED)) {
            this.contactStatus = xiaoShouContactStatus;
            log = new XiaoShouContactLog(loginInfo.getUserUuid(), loginInfo.getXingMing(), "回退合同状态到创建状态");
            log.setXiaoShouContact(this);
        }
        return log;
    }

    //判断销售合同中的款项是否完成
    public boolean judgeSaleItemsComplete() {
        //判断提车是否完成
        boolean finishTiChe = false;
        int totalTiCheNumber = 0;
        for (XiaoShouVehicleModule module : origModules) {
            totalTiCheNumber = totalTiCheNumber + module.getSaleNumber();
        }
        if (vehicleExecutes != null && vehicleExecutes.size() == totalTiCheNumber) {
            finishTiChe = true;
        }

        //判断付款是否完成
        boolean finishPay = true;
        for (XiaoShouContactRentingFeeHistory history : rentingFeeHistories) {
            if(XiaoShouPayOrderStatus.O_CREATE.equals(history.getPayOrderStatus())){
                finishPay = false;
            }
        }

        return finishTiChe && finishPay;
    }

    /*********************************setter getter*******************************/

    public XiaoShouType getContactType() {
        return contactType;
    }

    public void setContactType(XiaoShouType contactType) {
        this.contactType = contactType;
    }

    public XiaoShouContactBaoDianType getBaoDianType() {
        return baoDianType;
    }

    public void setBaoDianType(XiaoShouContactBaoDianType baoDianType) {
        this.baoDianType = baoDianType;
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

    public DateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(DateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public XiaoShouContactStatus getContactStatus() {
        return contactStatus;
    }

    public void setContactStatus(XiaoShouContactStatus contactStatus) {
        this.contactStatus = contactStatus;
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

    public XiaoShouContactUserType getYiFangType() {
        return yiFangType;
    }

    public void setYiFangType(XiaoShouContactUserType yiFangType) {
        this.yiFangType = yiFangType;
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

    public List<XiaoShouVehicleModule> getOrigModules() {
        return origModules;
    }

    public void setOrigModules(List<XiaoShouVehicleModule> origModules) {
        this.origModules = origModules;
    }

    public List<XiaoShouContactSupply> getZuPinContactSupplies() {
        return zuPinContactSupplies;
    }

    public void setZuPinContactSupplies(List<XiaoShouContactSupply> zuPinContactSupplies) {
        this.zuPinContactSupplies = zuPinContactSupplies;
    }

    public LocalDate getQianFeiBeginTime() {
        return qianFeiBeginTime;
    }

    public void setQianFeiBeginTime(LocalDate qianFeiBeginTime) {
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

    public List<XiaoShouVehicleExecute> getVehicleExecutes() {
        return vehicleExecutes;
    }

    public void setVehicleExecutes(List<XiaoShouVehicleExecute> vehicleExecutes) {
        this.vehicleExecutes = vehicleExecutes;
    }

    public List<XiaoShouContactRentingFeeHistory> getRentingFeeHistories() {
        return rentingFeeHistories;
    }

    public void setRentingFeeHistories(List<XiaoShouContactRentingFeeHistory> rentingFeeHistories) {
        this.rentingFeeHistories = rentingFeeHistories;
    }

    public List<XiaoShouContactFile> getContactFiles() {
        return contactFiles;
    }

    public void setContactFiles(List<XiaoShouContactFile> contactFiles) {
        this.contactFiles = contactFiles;
    }

    public List<XiaoShouContactLog> getContactLogs() {
        return contactLogs;
    }

    public void setContactLogs(List<XiaoShouContactLog> contactLogs) {
        this.contactLogs = contactLogs;
    }

    public void changeContactType(XiaoShouType newType) {
        setContactType(newType);
    }

    public int getZhiliuChargeNumber() {
        return zhiliuChargeNumber;
    }

    public void setZhiliuChargeNumber(int zhiliuChargeNumber) {
        this.zhiliuChargeNumber = zhiliuChargeNumber;
    }

    public int getJiaoliuChargeNumber() {
        return jiaoliuChargeNumber;
    }

    public void setJiaoliuChargeNumber(int jiaoliuChargeNumber) {
        this.jiaoliuChargeNumber = jiaoliuChargeNumber;
    }

    public List<XiaoShouContactCharging> getChargings() {
        return chargings;
    }

    public void setChargings(List<XiaoShouContactCharging> chargings) {
        this.chargings = chargings;
    }
}
