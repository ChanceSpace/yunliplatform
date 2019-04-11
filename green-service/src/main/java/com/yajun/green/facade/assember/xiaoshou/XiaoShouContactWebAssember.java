package com.yajun.green.facade.assember.xiaoshou;

import com.yajun.green.common.security.LoginInfo;
import com.yajun.green.common.utils.JodaUtils;
import com.yajun.green.domain.xiaoshou.*;
import com.yajun.green.facade.dto.contact.xiaoshou.*;
import com.yajun.green.repository.EntityLoadHolder;
import com.yajun.green.security.SecurityUtils;
import org.springframework.util.StringUtils;
import java.util.*;

/**
 * Created by LiuMengKe on 2017/12/21.
 */
public class XiaoShouContactWebAssember {

    public static XiaoShouContact toZuPinXiaoShouContactDomain(ZuPinXiaoShouContactDTO dto, String contactNumber) {
        LoginInfo loginInfo = SecurityUtils.currentLoginUser();

        XiaoShouContact contact = null;
        if (dto == null) {
            return null;
        }

        String uuid = dto.getUuid();
        if (StringUtils.hasText(uuid)) {
            contact = (XiaoShouContact) EntityLoadHolder.getUserDao().findByUuid(uuid, XiaoShouContact.class);
            XiaoShouType oldType = contact.getContactType();
            XiaoShouType newType = dto.getXiaoShouType();
            if (!oldType.equals(newType)) {
                contact.changeContactType(newType);
            }
            contact.setBaoDianType(dto.getBaoDianType());
            contact.setContactStatus(dto.getContactStatus());
            contact.setYiFangType(dto.getYiFangType());
        } else {
            //todo 补全面
            contact = new XiaoShouContact();
            String creatorUuid = loginInfo.getUserUuid();
            String creatorName = loginInfo.getXingMing();
            contact.setCreatorName(creatorName);
            contact.setCreatorUuid(creatorUuid);
            /*contact.setCreatorName("lmk");
            contact.setCreatorUuid("89757");*/
            contact.setContactNumber(contactNumber);
            XiaoShouType newType = dto.getXiaoShouType();
            contact.setContactType(newType);
            contact.setBaoDianType(dto.getBaoDianType());
            contact.setContactStatus(dto.getContactStatus());
            contact.setYiFangType(dto.getYiFangType());
        }

        contact.setSaleManName(dto.getSaleManName());
        contact.setSaleManUuid(dto.getSaleManUuid());

        contact.setJiaFangUuid(dto.getJiaFangUuid());
        contact.setJiaFangName(dto.getJiaFangName());
        contact.setJiaFangFaRen(dto.getJiaFangFaRen());
        contact.setJiaFangAddress(dto.getJiaFangAddress());
        contact.setJiaFangPostCode(dto.getJiaFangPostCode());

        contact.setYiFangUuid(dto.getYiFangUuid());
        contact.setYiFangName(dto.getYiFangName());
        contact.setYiFangFaRen(dto.getYiFangFaRen());
        contact.setYiFangAddress(dto.getYiFangAddress());
        contact.setYiFangPostCode(dto.getYiFangPostCode());
        contact.setYiFangType(dto.getYiFangType());
        contact.setContactPerson(dto.getContactPerson());
        contact.setContactPhone(dto.getContactPhone());

        contact.setZhiliuChargeNumber(dto.getZhiliuChargeNumber());
        contact.setJiaoliuChargeNumber(dto.getJiaoliuChargeNumber());

        return contact;
    }

    public static ZuPinXiaoShouContactDTO toZuPinContactDTO(XiaoShouContact contact, boolean loadSelectVehicleModule, boolean loadExeVehicleModule, boolean loadRentFeeHistory, boolean loadContactFile, boolean loadContactLog, boolean loadContactSupply, boolean loadCharge) {
        final String uuid = contact.getUuid();
        final String saleManUuid = contact.getSaleManUuid();
        final String saleManName = contact.getSaleManName();
        final String creatorUuid = contact.getCreatorUuid();
        final String creatorName = contact.getCreatorName();
        final XiaoShouType xiaoShouType = contact.getContactType();
        final XiaoShouContactBaoDianType baoDianType = contact.getBaoDianType();

        XiaoShouContactUserType yiFangType = contact.getYiFangType();
        //合同创建日期
        final String createTime = JodaUtils.toStringMyHM(contact.getCreateTime());
        final String startDate = contact.getStartDate().toString();
        final String endDate = contact.getEndDate().toString();
        final String contactNumber = contact.getContactNumber();
        final XiaoShouContactStatus contactStatus = contact.getContactStatus();

        //合同甲方乙方信息部分
        final String jiaFangUuid = contact.getJiaFangUuid();
        final String jiaFangName = contact.getJiaFangName();
        final String jiaFangFaRen = contact.getJiaFangFaRen();
        final String jiaFangAddress = contact.getJiaFangAddress();
        final String jiaFangPostCode = contact.getJiaFangPostCode();
        final String yiFangUuid = contact.getYiFangUuid();
        final String yiFangName = contact.getYiFangName();
        final String yiFangFaRen = contact.getYiFangFaRen();
        final String yiFangAddress = contact.getYiFangAddress();
        final String yiFangPostCode = contact.getYiFangPostCode();

        final String contactPerson = contact.getContactPerson();
        final String contactPhone = contact.getContactPhone();

        //
        //执行过程中车辆

        final boolean beginExecute = contact.isBeginExecute();
        final boolean endExecute = contact.isEndExecute();

        ZuPinXiaoShouContactDTO dto = new ZuPinXiaoShouContactDTO(uuid, xiaoShouType,baoDianType,contactStatus, yiFangType,  saleManUuid,  saleManName,  creatorUuid,  creatorName,  createTime,  startDate,  endDate,  contactNumber,  jiaFangUuid,  jiaFangName,  jiaFangFaRen,  jiaFangAddress,  jiaFangPostCode,  yiFangUuid,  yiFangName,  yiFangFaRen,  yiFangAddress,  yiFangPostCode,  contactPerson,  contactPhone,  beginExecute,  endExecute);
        //设置包电 延迟缴费 当前余额等

        dto.setZhiliuChargeNumber(contact.getZhiliuChargeNumber());
        dto.setJiaoliuChargeNumber(contact.getJiaoliuChargeNumber());

        dto.setYiFangType(contact.getYiFangType());

        //提车车辆模型
        Map<String, Integer> alreadyTiCheMap = new HashMap<>();
        if (loadExeVehicleModule) {
            List<XiaoShouVehicleExecute> executes = contact.getVehicleExecutes();
            if (executes != null) {
                for (XiaoShouVehicleExecute execute : executes) {
                    //转化为DTO
                    ZuPinXiaoShouVehicleExecuteDTO executeDTO = XiaoShouVehicleExecuteWebAssember.toZuPinXiaoShouVehicleExecuteDTO(execute);
                    dto.addExecuteModule(executeDTO);

                    //计算已经提取车子的
                    String moduleBrand = execute.getModuleBrand();
                    Integer exit = alreadyTiCheMap.get(moduleBrand);
                    if (exit == null) {
                        alreadyTiCheMap.put(moduleBrand, 1);
                    } else {
                        alreadyTiCheMap.put(moduleBrand, exit + 1);
                    }
                }
            }
        }

        //合同车辆模型
        if (loadSelectVehicleModule) {
            List<XiaoShouVehicleModule> origModules = contact.getOrigModules();
            if (origModules != null) {
                for (XiaoShouVehicleModule module : origModules) {
                    ZuPinXiaoShouVehicleModuleDTO already =XiaoShouVehicleModelWebAssember.toZuPinVehicleModuleDTO(module);
                    String moduleBrand = already.getModuleBrand();
                    Integer exit = alreadyTiCheMap.get(moduleBrand);
                    if (exit == null) {
                        already.setAlreadyTiChe(0);
                    } else {
                        already.setAlreadyTiChe(exit);
                    }
                    dto.addContactModule(already);
                }
            }
        }

        //合同账单历史
        if(loadRentFeeHistory){
            List<XiaoShouContactRentingFeeHistory> contactHistorys = contact.getRentingFeeHistories();
            List<ZuPinXiaoShouContactRentingFeeHistoryDTO> historys = XiaoShouContactRentingFeeHistoryWebAssember.toRentFeeHistoryDTOList(contactHistorys);
            dto.setRentingFeeHistories(historys);
        }

        //加载合同附件
        if (loadContactFile) {
            List<XiaoShouContactFile> contactFiles = contact.getContactFiles();
            List<ZuPinXiaoShouContactFileDTO> files = XiaoShouContactFileWebAssember.toZuPinXiaoShouContactFileDTOList(contactFiles);
            dto.setContactFiles(files);
        }

        //合同日志
        if (loadContactLog) {
            List<XiaoShouContactLog> contactLogs = contact.getContactLogs();
            List<ZuPinXiaoShouContactLogDTO> logs = XiaoShouContactLogWebAssember.toZuPinXiaoShouContactLogDTOList(contactLogs);
            dto.setContactLogs(logs);
        }

        //合同补充协议c
        if (loadContactSupply) {
            List<XiaoShouContactSupply> contactSupplies = contact.getZuPinContactSupplies();
            Collections.sort(contactSupplies);
            List<ZuPinXiaoShouContactSupplyDTO> zuPinContactSupplyDTOS = XiaoShouContactSupplyWebAssember.toZuPinXiaoShouContactSupplyDTOList(contactSupplies);
            dto.setZuPinContactSupplies(zuPinContactSupplyDTOS);
        }

        //合同补充协议c
        if (loadCharge) {
            List<XiaoShouContactCharging> contactSupplies = contact.getChargings();
            Collections.sort(contactSupplies);
            List<ZuPinXiaoShouContactChargingDTO> chargingDTOList = ZuPinXiaoShouContactChargingWebAssember.toZuPinXiaoShouContactChargingDTOList(contactSupplies);
            dto.setChargings(chargingDTOList);
        }

        return dto;
    }

    public static List<ZuPinXiaoShouContactDTO> toXiaoShouContactDTOList(List<XiaoShouContact> contactList, String keywords) {
        List<ZuPinXiaoShouContactDTO> dtos = new ArrayList<ZuPinXiaoShouContactDTO>();
        if (contactList != null) {
            for (XiaoShouContact contact : contactList) {
                ZuPinXiaoShouContactDTO dto = toZuPinContactDTO(contact, true, false, true,false, false, true,false);
                if (StringUtils.hasText(keywords)) {
                    dto.addHighLight(keywords);
                }
                dtos.add(dto);
            }
        }
        return dtos;
    }


}
