package com.yajun.green.facade.assember.contact;


import com.yajun.green.common.security.LoginInfo;
import com.yajun.green.common.utils.JodaUtils;
import com.yajun.green.domain.contact.*;
import com.yajun.green.facade.dto.contact.*;
import com.yajun.green.repository.EntityLoadHolder;
import com.yajun.green.security.SecurityUtils;
import org.springframework.util.StringUtils;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * Created by LiuMengKe on 2017/8/9.
 */
public class ZuPinContactWebAssember {

    public static ZuPinContact toZuPinContactDomain(ZuPinContactDTO dto, String contactNumber) {
        LoginInfo loginInfo = SecurityUtils.currentLoginUser();

        ZuPinContact contact = null;
        if (dto == null) {
            return null;
        }

        String uuid = dto.getUuid();
        if (StringUtils.hasText(uuid)) {
            contact = (ZuPinContact) EntityLoadHolder.getUserDao().findByUuid(uuid, ZuPinContact.class);
            String oldType = contact.getContactType().name();
            String newType = dto.getContactType();
            if (!oldType.equals(newType)) {
                contact.changeContactType(newType);
            }
            contact.setBaoDianType(ZuPinContactBaoDianType.valueOf(dto.getContactBaoDianType()));
            contact.setZuPinContactBaoYueType(dto.getZuPinContactBaoYueType());
        } else {
            contact = new ZuPinContact(loginInfo, dto.getContactType(), contactNumber, null, dto.getSaleManName());
            contact.setBaoDianType(ZuPinContactBaoDianType.valueOf(dto.getContactBaoDianType()));
            contact.setZuPinContactBaoYueType(dto.getZuPinContactBaoYueType());
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


        contact.setOrigYaJinFee(dto.getOrigYaJinFee());
        contact.setZujinHasPay(dto.getZujinHasPay().setScale(2, RoundingMode.HALF_UP));
        contact.setYajinHasPay(dto.getYajinHasPay().setScale(2, RoundingMode.HALF_UP));

        return contact;
    }

    public static ZuPinContactDTO toZuPinContactDTO(ZuPinContact contact, boolean loadSelectVehicleModule, boolean loadExeVehicleModule,boolean loadRentFeeHistory, boolean loadContactFile, boolean loadContactLog,boolean loadContactSupply) {
        final String uuid = contact.getUuid();
        final String saleManUuid = contact.getSaleManUuid();
        final String saleManName = contact.getSaleManName();
        final String creatorUuid = contact.getCreatorUuid();
        final String creatorName = contact.getCreatorName();
        final String contactType = contact.getContactType().name();
        final String contactTypeName = contact.getContactType().getDescription();
        final String contactBaoDianType = contact.getBaoDianType().name();
        final String contactBaoDianTypeName = contact.getBaoDianType().getDescription();
        //合同创建日期
        final String createTime = JodaUtils.toStringMyHM(contact.getCreateTime());
        final String startDate = contact.getStartDate().toString();
        final String endDate = contact.getEndDate().toString();
        final String contactNumber = contact.getContactNumber();
        final String contactStatus = contact.getContactStatus().name();
        final String contactStatusName = contact.getContactStatus().getDescription();

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

        //合同产品信息 修改为应用两位小数前台显示
        final BigDecimal origYaJunFee = contact.getOrigYaJinFee().setScale(2, RoundingMode.HALF_UP);
        //
        //执行过程中车辆

        final boolean beginExecute = contact.isBeginExecute();
        final boolean endExecute = contact.isEndExecute();

        ZuPinContactDTO dto = new ZuPinContactDTO(uuid, contactType, contactTypeName, saleManUuid, saleManName, creatorUuid, creatorName, createTime, startDate, endDate, contactNumber, contactStatus, contactStatusName,
                jiaFangUuid, jiaFangName, jiaFangFaRen, jiaFangAddress, jiaFangPostCode, yiFangUuid, yiFangName, yiFangFaRen, yiFangAddress, yiFangPostCode, contactPerson, contactPhone,
                origYaJunFee, beginExecute, endExecute);
        //设置包电 延迟缴费 当前余额等
        dto.setContactBaoDianName(contactBaoDianTypeName);
        dto.setContactBaoDianType(contactBaoDianType);

        //设置包月类型
        dto.setZuPinContactBaoYueType(contact.getZuPinContactBaoYueType());

        dto.setZujinHasPay(contact.getZujinHasPay().setScale(2, RoundingMode.HALF_UP));
        dto.setYajinHasPay(contact.getYajinHasPay().setScale(2, RoundingMode.HALF_UP));
        dto.setYiFangType(contact.getYiFangType());

        //提车车辆模型
        Map<String, Integer> alreadyTiCheMap = new HashMap<>();
        if (loadExeVehicleModule) {
            List<ZuPinVehicleExecute> executes = contact.getVehicleExecutes();
            if (executes != null) {
                for (ZuPinVehicleExecute execute : executes) {
                    //转化为DTO
                    ZuPinVehicleExecuteDTO executeDTO = ZuPinVehicleExecuteWebAssember.toZuPinVehicleExecuteDTO(execute);
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
            List<ZuPinVehicleModule> origModules = contact.getOrigModules();
            if (origModules != null) {
                for (ZuPinVehicleModule module : origModules) {
                    ZuPinVehicleModuleDTO already = ZuPinVehicleModelWebAssember.toZuPinVehicleModuleDTO(module);
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
            List<ZuPinContactRentingFeeHistory> contactHistorys = contact.getRentingFeeHistories();
            List<ZuPinContactRentingFeeHistoryDTO> historys = ZuPinContactRentingFeeHistoryWebAssember.toRentFeeHistoryDTOList(contactHistorys);
            dto.setRentHistorys(historys);
        }

        //加载合同附件
        if (loadContactFile) {
            List<ZuPinContactFile> contactFiles = contact.getContactFiles();
            List<ZuPinContactFileDTO> files = ZuPinContactFileWebAssember.toZuPinContactFileDTOList(contactFiles);
            dto.setContactFiles(files);
        }

        //合同日志
        if (loadContactLog) {
            List<ZuPinContactLog> contactLogs = contact.getContactLogs();
            List<ZuPinContactLogDTO> logs = ZuPinContactLogWebAssember.toZuPinContactLogDTOList(contactLogs);
            dto.setContactLogs(logs);
        }

        //合同补充协议c
        if (loadContactSupply) {
            List<ZuPinContactSupply> contactSupplies = contact.getZuPinContactSupplies();
            Collections.sort(contactSupplies);
            List<ZuPinContactSupplyDTO> zuPinContactSupplyDTOS = ZuPinContactSupplyWebAssember.toZuPinContactSupplyDTOList(contactSupplies);
            dto.setZuPinContactSupplyDTOS(zuPinContactSupplyDTOS);
        }

        return dto;
    }

    public static List<ZuPinContactDTO> toZuPinContactDTOList(List<ZuPinContact> contactList, String keywords) {
        List<ZuPinContactDTO> dtos = new ArrayList<ZuPinContactDTO>();
        if (contactList != null) {
            for (ZuPinContact contact : contactList) {
                ZuPinContactDTO dto = toZuPinContactDTO(contact, true, false, true,false, false, true);
                if (StringUtils.hasText(keywords)) {
                    dto.addHighLight(keywords);
                }
                dtos.add(dto);
            }
        }
        return dtos;
    }

    public static ZuPinContactFinishDTO toZuPinContactFinishDTO(ZuPinContactFinish finish) {
        if (finish == null) {
            return null;
        }

        final String uuid = finish.getUuid();
        final String finishDate = finish.getFinishDate().toString();
        final String finishType = finish.getFinishType().name();
        final String finishTypeName = finish.getFinishType().getDescription();
        final String finishCharge = finish.getFinishCharge().name();
        final String finishChargeName = finish.getFinishCharge().getDescription();
        final String finishNumber = finish.getFinishNumber();
        final BigDecimal finishFee = finish.getFinishFee();
        final String finishNote = finish.getFinishNote();
        final String actorUuid = finish.getActorUuid();
        final String actorName = finish.getActorName();

        return new ZuPinContactFinishDTO(uuid, finishDate, finishType, finishTypeName, finishCharge, finishChargeName,
                finishNumber, finishFee, finishNote, actorUuid, actorName);
    }

    public static List<ZuPinContactFinishDTO> toZuPinContactFinishDTOList(List<ZuPinContactFinish> finishes) {
        List<ZuPinContactFinishDTO> dtos = new ArrayList<ZuPinContactFinishDTO>();
        if (finishes != null) {
            for (ZuPinContactFinish finish : finishes) {
                dtos.add(toZuPinContactFinishDTO(finish));
            }
        }
        return dtos;
    }


}
