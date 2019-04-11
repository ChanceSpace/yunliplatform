package com.yajun.green.domain.contact;

import com.yajun.green.common.domain.EntityBase;
import com.yajun.green.common.log.ApplicationLog;
import com.yajun.green.common.security.LoginInfo;
import com.yajun.green.common.utils.ContactLogUtil;
import com.yajun.green.common.utils.JodaUtils;
import com.yajun.green.domain.pay.PayOrderStatus;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Jack Wang
 * Date: 17-8-8
 * Time: 上午9:47
 */
public class ZuPinContact extends EntityBase {

    //合同类型
    private ZuPinContactType contactType;
    //是否包电
    private ZuPinContactBaoDianType baoDianType;

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

    //状态和审核状态
    private ZuPinContactStatus contactStatus;

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
    private ZuPinContactUserType yiFangType;
    private String contactPerson;
    private String contactPhone;

    private List<ZuPinVehicleModule> origModules;
    //合同补充日志c
    private List<ZuPinContactSupply> zuPinContactSupplies;

    /*********************************合同包月*******************************/
    private ZuPinContactBaoYueType zuPinContactBaoYueType;

    /**************************************当前费用相关*****************************************/

    //合同产品信息
    private BigDecimal origYaJinFee;
    //整个合同实际0已经缴纳租金
    private BigDecimal zujinHasPay;
    //整个合同实际已经缴纳押金
    private BigDecimal yajinHasPay ;
    
    /**************************************合同欠费相关*****************************************/
    private LocalDate qianFeiBeginTime;
    private boolean qianFei;
    private BigDecimal qianFeiBalance;
    /***************************************合同执行，变化部分*************************************/
    //执行过程中车辆和状态
    //开始提车
    private boolean beginExecute;
    //结束提车
    private boolean endExecute;
    private List<ZuPinVehicleExecute> vehicleExecutes;
    //合同账单历史
    private List<ZuPinContactRentingFeeHistory> rentingFeeHistories;

    /****************************************附件和日志*******************************************/

    //合同附件
    private List<ZuPinContactFile> contactFiles;
    //合同日志
    private List<ZuPinContactLog> contactLogs;

    public ZuPinContact() {
    }

    public ZuPinContact(LoginInfo loginInfo, String contactType, String contactNumber,String saleManUuid, String saleManName) {
        this.contactType = ZuPinContactType.valueOf(contactType);
        this.saleManUuid = saleManUuid;
        this.saleManName = saleManName;

        this.creatorUuid = loginInfo.getUserUuid();
        this.creatorName = loginInfo.getXingMing();
        this.contactNumber = contactNumber;
        this.createTime = new DateTime();
        this.startDate = new LocalDate();
        this.endDate = new LocalDate();

        this.contactStatus = ZuPinContactStatus.S_CREATED;
        this.beginExecute = false;
        this.endExecute = false;

    }

    public void changeContactType(String newType) {
        String oldNumber = ZuPinContactType.decideWhichType(this.contactType.name());
        String newNumber = ZuPinContactType.decideWhichType(newType);

        this.contactNumber = this.contactNumber.replace(oldNumber, newNumber);
        this.contactType = ZuPinContactType.valueOf(newType);
    }

    /**************************************************************************************************/

    public void addZuPinVehicleExecute(ZuPinVehicleExecute execute) {
        if (vehicleExecutes == null) {
            vehicleExecutes = new ArrayList<>();
        }
        vehicleExecutes.add(execute);
    }

    public ZuPinVehicleModule getOrigModule(String selectModuleBrand) {
        ZuPinVehicleModule selected = null;
        for (ZuPinVehicleModule origModule : origModules) {

            if (origModule.getModuleBrand().equals(selectModuleBrand)) {
                selected = origModule;
                break;
            }
        }
        return selected;
    }

    public int getTotalNeedTiChe() {
        int total = 0;
        if (origModules != null) {
            for (ZuPinVehicleModule origModule : origModules) {
                total = total + origModule.getRentNumber();
            }
        }
        return total;
    }

    public int getTotalAlreadyTiChe() {
        int total = 0;
        if (vehicleExecutes != null) {
            total = vehicleExecutes.size();
        }
        return total;
    }

    public int getAlreadyTiChePiCi() {
        int piCi = 0;
        if (vehicleExecutes != null) {
            for (ZuPinVehicleExecute execute : vehicleExecutes) {
                int alreadyTiChePiCI = execute.getTiChePiCi();
                if (alreadyTiChePiCI > piCi) {
                    piCi = alreadyTiChePiCI;
                }
            }
        }
        return piCi;
    }

    public void beginExecute() {
        this.beginExecute = true;
        LocalDate start = new LocalDate();
        if (vehicleExecutes != null) {
            for (ZuPinVehicleExecute execute : vehicleExecutes) {
                LocalDate startDate = execute.getTiCheDate();
                if (startDate.compareTo(start) < 0) {
                    start = startDate;
                }
            }
        }
        this.startDate = start;
    }

    public void finishExecute() {
        this.endExecute = true;
        LocalDate end = new LocalDate();
        if (vehicleExecutes != null) {
            for (ZuPinVehicleExecute execute : vehicleExecutes) {
                LocalDate finishDate = execute.getTiCheDate().plusMonths(execute.getRentMonth());
                if (finishDate.compareTo(end) > 0) {
                    end = finishDate;
                }
            }
        }
        this.endDate = end;
    }

    public List<ZuPinVehicleExecute> getSpeTiChePiCi(int piChi) {
        List<ZuPinVehicleExecute> executes = new ArrayList<>();
        if (vehicleExecutes != null) {
            for (ZuPinVehicleExecute execute : vehicleExecutes) {
                if (execute.getTiChePiCi() == piChi) {
                    executes.add(execute);
                }
            }
        }
        return executes;
    }

    public List<ZuPinVehicleExecute> getSpeUseFulTiChePiCi(int piChi) {
        List<ZuPinVehicleExecute> executes = new ArrayList<>();
        if (vehicleExecutes != null) {
            for (ZuPinVehicleExecute execute : vehicleExecutes) {
                if (execute.getTiChePiCi() == piChi&&execute.isOver()==false) {
                    executes.add(execute);
                }
            }
        }
        return executes;
    }

    public List<ZuPinVehicleExecute> getSpeUseFulExecute() {
        List<ZuPinVehicleExecute> executes = new ArrayList<>();
        if (vehicleExecutes != null) {
            for (ZuPinVehicleExecute execute : vehicleExecutes) {
                if (execute.isOver()==false) {
                    executes.add(execute);
                }
            }
        }
        return executes;
    }

    //租赁计算后付差的钱
    public BigDecimal finishContactFee(String finishDate) {
        ZuPinVehicleModule module = origModules.get(0);
        BigDecimal dalayTotal = new BigDecimal(0);
        //只有后付才会出现没付清的情况
        //3.获得当前未执行完的所有已提车辆（未结束租赁）
        ZuPinContactRepayType type = module.getZuPinContactRepayType();
        if("C_AFTER".equals(type.name())){
            List<ZuPinVehicleExecute> executeList = getSpeUseFulExecute();
            LocalDate jiesuanDate = new LocalDate(finishDate);
            for (ZuPinVehicleExecute execute : executeList) {

                //当天提车当天结束 不计费
                if(execute.getTiCheDate().toString().equals(finishDate)){
                    dalayTotal = dalayTotal.add(new BigDecimal(0.00));
                    continue;
                }

                LocalDate actualFeeDate = execute.getActualzujinrepaymonth();

                int actualFeeDateYear = actualFeeDate.getYear();
                int jieshuYear = jiesuanDate.getYear();

                int actualFeeDateMonth = actualFeeDate.getMonthOfYear();
                int jieshuMonth = jiesuanDate.getMonthOfYear();

                int actualDay = actualFeeDate.getDayOfMonth();
                int jieshuDay = jiesuanDate.getDayOfMonth();

                //计算间隔月份数
                int fujia = jieshuDay>=actualDay?1:0;
                int jiangeMonth = (jieshuYear-actualFeeDateYear)*12+(jieshuMonth-actualFeeDateMonth)+fujia;
                BigDecimal executeDalayZuJin = execute.getActualRentFee().multiply(new BigDecimal(jiangeMonth));
                dalayTotal = dalayTotal.add(executeDalayZuJin);
            }
            
        }
        BigDecimal total = dalayTotal;
        return total;
    }

    public ZuPinContactLog finishContact(LoginInfo loginInfo, String finishDate) {
        this.contactStatus = ZuPinContactStatus.S_FINISHED;

        if (vehicleExecutes != null) {
            for (ZuPinVehicleExecute execute : vehicleExecutes) {
                execute.setOver(true);
                execute.setPiciOver(true);
                execute.setJieshuDate(new LocalDate(finishDate));
            }
        }

        ZuPinContactLog log = new ZuPinContactLog(loginInfo.getUserUuid(), loginInfo.getXingMing(), "合同于" + finishDate + "结束");
        log.setZuPinContact(this);
        return log;
    }

    public static void main(String[] args) {
        ZuPinContact contact = new ZuPinContact();

        List<ZuPinVehicleExecute> executes = new ArrayList<>();
        ZuPinVehicleExecute execute1 = new ZuPinVehicleExecute();
        execute1.setTiCheDate(new LocalDate("2017-07-01"));
        execute1.setRentMonth(12);
        ZuPinVehicleExecute execute2 = new ZuPinVehicleExecute();
        execute2.setTiCheDate(new LocalDate("2017-08-01"));
        execute2.setRentMonth(12);
        executes.add(execute1);
        executes.add(execute2);

        contact.setVehicleExecutes(executes);
        contact.finishExecute();
    }

    /**************************************费用相关****************************************/

    //获取未支付
    public List<ZuPinContactRentingFeeHistory> getUnPayBill(){
        List<ZuPinContactRentingFeeHistory> list = getRentingFeeHistories();
        List<ZuPinContactRentingFeeHistory> nopayList = new ArrayList<>();
        for (ZuPinContactRentingFeeHistory zuPinContactRentingFeeHistory : list) {
            if(PayOrderStatus.O_CREATE.equals(zuPinContactRentingFeeHistory.getStatus())){
                nopayList.add(zuPinContactRentingFeeHistory);
            }
        }
        return  nopayList;
    }

    public ZuPinVehicleExecute getSpecNotOverExecute(String vehicleNumber ,int tichePiCi){
        List<ZuPinVehicleExecute> executes = getVehicleExecutes();
        for (ZuPinVehicleExecute execute : executes) {
            if (vehicleNumber.equals(execute.getVehicleNum())&&execute.getTiChePiCi()==tichePiCi&&execute.isOver() == false){
                return execute;
            }
        }
        return null;
    }

    public BigDecimal getSpecExecuteFeeTotal(List<ZuPinVehicleExecute> executes, int piChi) {
        BigDecimal piCiFee = new BigDecimal("0");
        for (ZuPinVehicleExecute execute : executes) {
            if (execute.getTiChePiCi() == piChi) {
                piCiFee = piCiFee.add(execute.getActualRentFee());
            }
        }
        return piCiFee;
    }

    //todo haspay 是否正确
    //重新计算已经缴纳的租金
    public void updateZuJinHasPay(){
        BigDecimal newZujin = reloadZujinOrYajin(true);
        setZujinHasPay(newZujin);
    }
    public void updateYaJinHasPay(){
        BigDecimal newYajin = reloadZujinOrYajin(false);
        setYajinHasPay(newYajin);
    }


    //计算已经缴纳租金数和已经缴纳押金数
    public BigDecimal reloadZujinOrYajin(boolean zujinType){
        BigDecimal toatal = new BigDecimal(0);
        List<ZuPinContactRentingFeeHistory> rentingFeeHistories = getRentingFeeHistories();
        for (ZuPinContactRentingFeeHistory rentingFeeHistory : rentingFeeHistories) {
            if("O_FINSIH".equals(rentingFeeHistory.getStatus().name())){
                String  location = rentingFeeHistory.getZuPinContactRepayLocation().name();
                //是否是自动押金扣费 人工押金扣费  提车押金扣费的任意一种
                if(zujinType){
                    boolean iszujin = "L_ZJ_SCHEDULE".equals(location)||"L_ZJ_TIQIAN_TZ".equals(location)||"L_ZJ_VEHICLE_SUB".equals(location)
                            ||"L_JS_IN".equals(location)||"L_FJ_IN".equals(location);
                    if (iszujin){
                        toatal = toatal.add(rentingFeeHistory.getFeeTotal());
                    }
                }else {
                    boolean isyajin = "L_YJ_SCHEDULE".equals(location)||"L_YJ_VEHICLE_SUB".equals(location);
                    if (isyajin){
                        toatal = toatal.add(rentingFeeHistory.getFeeTotal());
                    }
                    if("L_YJ_MANSUB".equals(location)){
                        toatal = toatal.subtract(rentingFeeHistory.getFeeTotal());
                    }
                }
            }
        }
        return toatal;
    }

    //合同欠费相关
    public void setMinQianFeiDayTemp(LocalDate date){
        if(qianFeiBeginTime==null){
            qianFeiBeginTime = date;
            ApplicationLog.info(ZuPinContact.class, "合同::" + getUuid() + "欠费日期为" + qianFeiBeginTime);
        }else {
            //-1 结束时间小于开始时间
            int compareResult = JodaUtils.compareDays(qianFeiBeginTime,date);
            if(compareResult<0){
                qianFeiBeginTime = date;
                ApplicationLog.info(ZuPinContact.class, "合同:" + getUuid() + "欠费日期为" + qianFeiBeginTime);
            }
        }
    }

    //自动结束合同修改状态
    public void changeContactStautsInScheduleOver() {
        List<ZuPinVehicleExecute> executes = getVehicleExecutes();
        boolean statusflag = true;
        for (ZuPinVehicleExecute zuPinVehicleExecute : executes) {
            if(!zuPinVehicleExecute.isOver()){
                statusflag = false;
                break;
            }
        }
        if(statusflag){
            setContactStatus(ZuPinContactStatus.S_ENDBUTNOTOVER);
            ContactLogUtil.addActionLine(this.getClass(),"合同id"+getUuid()+" 状态设置为 自动扣费结束后未换车");
        }
    }

    public List<ZuPinContactRentingFeeHistory> changeContactRentFeeHistoryYajinInScheduleOver(){
        List<ZuPinContactRentingFeeHistory> unPayBills = getUnPayBill();
        for (ZuPinContactRentingFeeHistory history : unPayBills) {
            ZuPinContactRepayLocation location = history.getZuPinContactRepayLocation();
            //未支付押金状态处理
            if(location.contactOverCheckYaJin()){
                history.setStatus(PayOrderStatus.O_ABANDON);
            }
        }
        return unPayBills;
    }
    /***************************************合同历史部分***************************************/

    public ZuPinContactLog resetContactStatus(LoginInfo loginInfo, ZuPinContactStatus contactStatus) {
        ZuPinContactLog log = null;

        //销售从创建到合同上报过程
        if (this.contactStatus.equals(ZuPinContactStatus.S_CREATED) && contactStatus.equals(ZuPinContactStatus.S_PROCESSING)) {
            this.contactStatus = contactStatus;
            log = new ZuPinContactLog(loginInfo.getUserUuid(), loginInfo.getXingMing(), "完成合同上报，开始执行合同");
            log.setZuPinContact(this);
        }

        if (this.contactStatus.equals(ZuPinContactStatus.S_PROCESSING) && contactStatus.equals(ZuPinContactStatus.S_CREATED)) {
            this.contactStatus = contactStatus;
            log = new ZuPinContactLog(loginInfo.getUserUuid(), loginInfo.getXingMing(), "回退合同状态到创建状态");
            log.setZuPinContact(this);
        }
        return log;
    }




    /***************************************GET/SET******************************************/

    public ZuPinContactBaoYueType getZuPinContactBaoYueType() {
        return zuPinContactBaoYueType;
    }

    public void setZuPinContactBaoYueType(ZuPinContactBaoYueType zuPinContactBaoYueType) {
        this.zuPinContactBaoYueType = zuPinContactBaoYueType;
    }

    public ZuPinContactType getContactType() {
        return contactType;
    }

    public void setContactType(ZuPinContactType contactType) {
        this.contactType = contactType;
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

    public ZuPinContactStatus getContactStatus() {
        return contactStatus;
    }

    public void setContactStatus(ZuPinContactStatus contactStatus) {
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

    public BigDecimal getOrigYaJinFee() {
        return origYaJinFee;
    }

    public void setOrigYaJinFee(BigDecimal origYaJinFee) {
        this.origYaJinFee = origYaJinFee;
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

    public List<ZuPinVehicleModule> getOrigModules() {
        return origModules;
    }

    public void setOrigModules(List<ZuPinVehicleModule> origModules) {
        this.origModules = origModules;
    }

    public List<ZuPinVehicleExecute> getVehicleExecutes() {
        return vehicleExecutes;
    }

    public void setVehicleExecutes(List<ZuPinVehicleExecute> vehicleExecutes) {
        this.vehicleExecutes = vehicleExecutes;
    }

    public List<ZuPinContactRentingFeeHistory> getRentingFeeHistories() {
        return rentingFeeHistories;
    }

    public void setRentingFeeHistories(List<ZuPinContactRentingFeeHistory> rentingFeeHistories) {
        this.rentingFeeHistories = rentingFeeHistories;
    }

    public List<ZuPinContactFile> getContactFiles() {
        return contactFiles;
    }

    public void setContactFiles(List<ZuPinContactFile> contactFiles) {
        this.contactFiles = contactFiles;
    }

    public List<ZuPinContactLog> getContactLogs() {
        return contactLogs;
    }

    public void setContactLogs(List<ZuPinContactLog> contactLogs) {
        this.contactLogs = contactLogs;
    }

    public List<ZuPinContactSupply> getZuPinContactSupplies() {
        return zuPinContactSupplies;
    }

    public void setZuPinContactSupplies(List<ZuPinContactSupply> zuPinContactSupplies) {
        this.zuPinContactSupplies = zuPinContactSupplies;
    }

    public ZuPinContactBaoDianType getBaoDianType() {
        return baoDianType;
    }

    public void setBaoDianType(ZuPinContactBaoDianType baoDianType) {
        this.baoDianType = baoDianType;
    }

    public BigDecimal getZujinHasPay() {
        return zujinHasPay;
    }

    public void setZujinHasPay(BigDecimal zujinHasPay) {
        this.zujinHasPay = zujinHasPay;
    }

    public BigDecimal getYajinHasPay() {
        return yajinHasPay;
    }

    public void setYajinHasPay(BigDecimal yajinHasPay) {
        this.yajinHasPay = yajinHasPay;
    }

    public String getSaleManUuid() {
        return saleManUuid;
    }

    public void setSaleManUuid(String saleManUuid) {
        this.saleManUuid = saleManUuid;
    }

    public ZuPinContactUserType getYiFangType() {
        return yiFangType;
    }

    public void setYiFangType(ZuPinContactUserType yiFangType) {
        this.yiFangType = yiFangType;
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


}
