package com.yajun.green.facade.dto.contact;

import com.yajun.green.common.log.ApplicationLog;
import com.yajun.green.common.security.LoginInfo;
import com.yajun.green.domain.contact.*;
import com.yajun.green.facade.show.HighLightUtils;
import com.yajun.green.security.SecurityUtils;
import org.joda.time.LocalDate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiuMengKe on 2017/8/9.
 */
public class ZuPinContactDTO {

    private String uuid;
    private String saleManUuid;
    private String saleManName;
    private String creatorUuid;
    private String creatorName;

    private String contactType;
    private String contactTypeName;
    private String contactBaoDianType;
    private String contactBaoDianName;

    //合同创建日期
    private String createTime;
    private String startDate;
    private String endDate;
    private String contactNumber;
    private String contactStatus;
    private String contactStatusName;

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

    //乙方是否欠费
    private BigDecimal origYaJinFee;
    //整个合同实际已经缴纳租金
    private BigDecimal zujinHasPay;
    //整个合同实际已经缴纳押金
    private BigDecimal yajinHasPay;

    //包月还是日结
    private ZuPinContactBaoYueType zuPinContactBaoYueType;

    //合同欠费相关
    private boolean qianFei;
    //获取该合同下所有在租车辆里面欠费时间最早的车的欠费开始时间
    private String qianFeiBeginTime;
    private BigDecimal qianFeiBalance;

    private boolean beginExecute;
    private boolean endExecute;

    //是否可以开始执行合同 必须是module 有一条数据
    private boolean canBeExecute;

    //车辆合同选择信息
    private List<ZuPinVehicleModuleDTO> contactModules = new ArrayList<>();
    private List<ZuPinVehicleExecuteDTO> executeModules = new ArrayList<>();
    private List<ZuPinContactRentingFeeHistoryDTO> rentHistorys = new ArrayList<>();

    //合同附件
    private List<ZuPinContactFileDTO> contactFiles = new ArrayList<>();
    private List<ZuPinContactLogDTO> contactLogs = new ArrayList<>();

    //合同补充协议c
    private List<ZuPinContactSupplyDTO> zuPinContactSupplyDTOS = new ArrayList<>();


    public ZuPinContactDTO() {
        LoginInfo loginInfo = SecurityUtils.currentLoginUser();
        this.jiaFangUuid = loginInfo.getCompanyUuid();
        this.jiaFangName = loginInfo.getCompanyName();
        this.jiaFangFaRen = loginInfo.getCompanyFaRen();
        this.jiaFangAddress = loginInfo.getCompanyAddress();
        this.jiaFangPostCode = loginInfo.getCompanyPostCode();
    }

    public ZuPinContactDTO(String uuid, String contactType, String contactTypeName,String saleManUuid,
                           String saleManName, String creatorUuid, String creatorName,
                           String createTime, String startDate, String endDate,
                           String contactNumber, String contactStatus, String contactStatusName,
                           String jiaFangUuid, String jiaFangName, String jiaFangFaRen, String jiaFangAddress, String jiaFangPostCode,
                           String yiFangUuid, String yiFangName, String yiFangFaRen, String yiFangAddress, String yiFangPostCode, String contactPerson, String contactPhone,
                           BigDecimal origYaJinFee, boolean beginExecute, boolean endExecute) {
        this.uuid = uuid;
        this.contactType = contactType;
        this.contactTypeName = contactTypeName;
        this.saleManUuid = saleManUuid;
        this.saleManName = saleManName;
        this.creatorUuid = creatorUuid;
        this.creatorName = creatorName;
        this.createTime = createTime;
        this.startDate = startDate;
        this.endDate = endDate;

        this.contactNumber = contactNumber;
        this.contactStatus = contactStatus;
        this.contactStatusName = contactStatusName;

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
        this.origYaJinFee = origYaJinFee;
        this.beginExecute = beginExecute;
        this.endExecute = endExecute;
    }



    //创建合同填写车型后初始化合同押金总额
    public void initOrigYaJinFee(ZuPinVehicleModuleDTO moduleDTO){
        BigDecimal orginYaJin = new BigDecimal("0");
        //分期押金=首付押金+分期押金*分期
        BigDecimal rentNumber = new BigDecimal(moduleDTO.getRentNumber());
        if (ZuPinYaJinType.Y_QUANKUAN.equals(moduleDTO.getZuPinYaJinType())) {
            orginYaJin = new BigDecimal(moduleDTO.getSingleYaJin()).multiply(rentNumber);
        }else {
            BigDecimal shouFu = moduleDTO.getShouFu();
            BigDecimal yueGong = moduleDTO.getYueGong();
            String fenQiShu = moduleDTO.getFenQiShu();
            orginYaJin = orginYaJin.add(shouFu.add(BigDecimal.valueOf(Double.valueOf(fenQiShu)).multiply(yueGong)).multiply(rentNumber));
        }
        setOrigYaJinFee(orginYaJin);
    }


    public void addContactModule(ZuPinVehicleModuleDTO contactModule) {
        contactModules.add(contactModule);
    }

    public void addExecuteModule(ZuPinVehicleExecuteDTO executeDTO) {
        executeModules.add(executeDTO);
    }

    public void addZuPinContactFile(ZuPinContactFileDTO contactFileDTO) {
        contactFiles.add(contactFileDTO);
    }

    public boolean isTiCheCanBeOver() {
        boolean condition1 = endExecute;
        boolean condition2 = contactStatus.equals(ZuPinContactStatus.S_PROCESSING.name());
        return !condition1 && condition2 ;
    }

    public boolean isContactCanBeOver(){
        boolean condition1 = contactStatus.equals(ZuPinContactStatus.S_PROCESSING.name());
        boolean condition2 = true;
        boolean condition3 = contactStatus.equals(ZuPinContactStatus.S_ENDBUTNOTOVER.name());
        return (condition1 && condition2)||(condition2&&condition3);
    }

    public void addHighLight(String keywords) {
        contactNumber = HighLightUtils.highLightWords(contactNumber, keywords);
        yiFangName = HighLightUtils.highLightWords(yiFangName, keywords);
    }

    /******************************************GETTER/SETTER****************************************/

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getContactType() {
        return contactType;
    }

    public void setContactType(String contactType) {
        this.contactType = contactType;
    }

    public String getContactTypeName() {
        return contactTypeName;
    }

    public void setContactTypeName(String contactTypeName) {
        this.contactTypeName = contactTypeName;
    }

    public String getSaleManUuid() {
        return saleManUuid;
    }

    public void setSaleManUuid(String saleManUuid) {
        this.saleManUuid = saleManUuid;
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

    public String getSaleManName() {
        return saleManName;
    }

    public void setSaleManName(String saleManName) {
        this.saleManName = saleManName;
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

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getContactStatus() {
        return contactStatus;
    }

    public void setContactStatus(String contactStatus) {
        this.contactStatus = contactStatus;
    }

    public String getContactStatusName() {
        return contactStatusName;
    }

    public void setContactStatusName(String contactStatusName) {
        this.contactStatusName = contactStatusName;
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

    public ZuPinContactUserType getYiFangType() {
        return yiFangType;
    }

    public void setYiFangType(ZuPinContactUserType yiFangType) {
        this.yiFangType = yiFangType;
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

    public boolean isQianFei() {
        return qianFei;
    }

    public void setQianFei(boolean qianFei) {
        this.qianFei = qianFei;
    }

    public String getQianFeiBeginTime() {
        return qianFeiBeginTime;
    }

    public void setQianFeiBeginTime(String qianFeiBeginTime) {
        this.qianFeiBeginTime = qianFeiBeginTime;
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

    public List<ZuPinVehicleModuleDTO> getContactModules() {
        return contactModules;
    }

    public void setContactModules(List<ZuPinVehicleModuleDTO> contactModules) {
        this.contactModules = contactModules;
    }

    public boolean isEndExecute() {
        return endExecute;
    }

    public void setEndExecute(boolean endExecute) {
        this.endExecute = endExecute;
    }

    public List<ZuPinVehicleExecuteDTO> getExecuteModules() {
        return executeModules;
    }

    public void setExecuteModules(List<ZuPinVehicleExecuteDTO> executeModules) {
        this.executeModules = executeModules;
    }

    public List<ZuPinContactRentingFeeHistoryDTO> getRentHistorys() {
        return rentHistorys;
    }

    public void setRentHistorys(List<ZuPinContactRentingFeeHistoryDTO> rentHistorys) {
        this.rentHistorys = rentHistorys;
    }

    public List<ZuPinContactFileDTO> getContactFiles() {
        return contactFiles;
    }

    public void setContactFiles(List<ZuPinContactFileDTO> contactFiles) {
        this.contactFiles = contactFiles;
    }

    public List<ZuPinContactLogDTO> getContactLogs() {
        return contactLogs;
    }

    public void setContactLogs(List<ZuPinContactLogDTO> contactLogs) {
        this.contactLogs = contactLogs;
    }

    public List<ZuPinContactSupplyDTO> getZuPinContactSupplyDTOS() {
        return zuPinContactSupplyDTOS;
    }

    public void setZuPinContactSupplyDTOS(List<ZuPinContactSupplyDTO> zuPinContactSupplyDTOS) {
        this.zuPinContactSupplyDTOS = zuPinContactSupplyDTOS;
    }

    public String getContactBaoDianType() {
        return contactBaoDianType;
    }

    public void setContactBaoDianType(String contactBaoDianType) {
        this.contactBaoDianType = contactBaoDianType;
    }

    public String getContactBaoDianName() {
        return contactBaoDianName;
    }

    public void setContactBaoDianName(String contactBaoDianName) {
        this.contactBaoDianName = contactBaoDianName;
    }



    public boolean isCanBeExecute() {
        return contactModules.size()==1?true:false;
    }

    public void setCanBeExecute(boolean canBeExecute) {
        this.canBeExecute = canBeExecute;
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

    public ZuPinContactBaoYueType getZuPinContactBaoYueType() {
        return zuPinContactBaoYueType;
    }

    public void setZuPinContactBaoYueType(ZuPinContactBaoYueType zuPinContactBaoYueType) {
        this.zuPinContactBaoYueType = zuPinContactBaoYueType;
    }

    public List<ZuPinVehicleExecuteDTO> getSpeTiChePiCi(int piChi) {
        List<ZuPinVehicleExecuteDTO> executes = new ArrayList<>();
        if (executeModules != null) {
            for (ZuPinVehicleExecuteDTO execute : executeModules) {
                if (execute.getTiChePiCi() == piChi) {
                    executes.add(execute);
                }
            }
        }
        return executes;
    }

    public List<ZuPinVehicleExecuteDTO> getSpeUseFulExecute() {
        List<ZuPinVehicleExecuteDTO> executes = new ArrayList<>();
        if (executeModules != null) {
            for (ZuPinVehicleExecuteDTO executeDTO : executeModules) {
                if (executeDTO.isOver()==false) {
                    executes.add(executeDTO);
                }
            }
        }
        return executes;
    }

    public List<ZuPinVehicleExecuteDTO> getOverButNotRevertExecute(){
        List<ZuPinVehicleExecuteDTO> executes = new ArrayList<>();
        if (executeModules != null) {
            for (ZuPinVehicleExecuteDTO executeDTO : executeModules) {
                if (executeDTO.isOverButNotRevert()) {
                    executes.add(executeDTO);
                }
            }
        }
        return executes;
    }



    //租赁计算后付差的钱
    public BigDecimal finishContactFee(String finishDate) {
        ZuPinVehicleModuleDTO module = contactModules.get(0);
        BigDecimal dalayTotal = new BigDecimal(0);
        //只有后付才会出现没付清的情况
        //3.获得当前未执行完的所有已提车辆（未结束租赁）
        ZuPinContactRepayType type = module.getZuPinContactRepayType();
        if(ZuPinContactRepayType.C_AFTER.equals(type)){
            List<ZuPinVehicleExecuteDTO> executeList = getSpeUseFulExecute();
            LocalDate jiesuanDate = new LocalDate(finishDate);
            for (ZuPinVehicleExecuteDTO execute : executeList) {

                if(execute.getTiCheDate().equals(finishDate)){
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

                if (jiangeMonth<0){
                    ApplicationLog.error(ZuPinContactDTO.class,"==============合同结算日期异常  结束时间小于实际已经交费时间==========");
                    throw new RuntimeException("合同结算日期异常  结束时间小于实际已经交费时间");
                }

                BigDecimal executeDalayZuJin = new BigDecimal(execute.getActualRentFee()).multiply(new BigDecimal(jiangeMonth));

                dalayTotal = dalayTotal.add(executeDalayZuJin);
            }
        }
        BigDecimal total = dalayTotal;
        return total;
    }



}
