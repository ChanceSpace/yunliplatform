package com.yajun.green.facade.assember.contact;


import com.yajun.green.domain.contact.ZuPinContact;
import com.yajun.green.domain.contact.ZuPinContactRepayType;
import com.yajun.green.domain.contact.ZuPinVehicleModule;
import com.yajun.green.domain.contact.ZuPinYaJinType;

import com.yajun.green.facade.dto.contact.ZuPinVehicleModuleDTO;
import com.yajun.green.repository.EntityLoadHolder;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiuMengKe on 2017/8/9.
 */
public class ZuPinVehicleModelWebAssember {

    public static ZuPinVehicleModule toZuPinVehicleModuleDomain(ZuPinVehicleModuleDTO dto, String zuPinContactUuid) {
        ZuPinVehicleModule vehicleModule = null;
        if(dto == null) {
            return null;
        }

        String uuid = dto.getUuid();
        if (StringUtils.hasText(uuid)) {
            vehicleModule = (ZuPinVehicleModule) EntityLoadHolder.getUserDao().findByUuid(uuid, ZuPinVehicleModule.class);
        } else {
            vehicleModule = new ZuPinVehicleModule();
            ZuPinContact contact = new ZuPinContact();
            contact.setUuid(zuPinContactUuid);
            vehicleModule.setZuPinContact(contact);
        }

        vehicleModule.setModuleUuid(dto.getModuleUuid());
        vehicleModule.setModuleName(dto.getModuleName());
        vehicleModule.setModuleType(dto.getModuleType());
        vehicleModule.setModuleBrand(dto.getModuleBrand());
        vehicleModule.setModuleColor(dto.getModuleColor());
        vehicleModule.setModuleDianLiang(dto.getModuleDianLiang());
        vehicleModule.setRentNumber(Integer.valueOf(dto.getRentNumber()));
        vehicleModule.setRentMonth(Integer.valueOf(dto.getRentMonth()));
        vehicleModule.setActualRentFee(BigDecimal.valueOf(Double.valueOf(dto.getActualRentFee())));
        vehicleModule.setSingleYaJin(BigDecimal.valueOf(Double.valueOf(dto.getSingleYaJin())));
        vehicleModule.setZuPinYaJinType(dto.getZuPinYaJinType());
        vehicleModule.setShouFu(dto.getShouFu());
        vehicleModule.setFenQiShu(dto.getFenQiShu());
        vehicleModule.setYueGong(dto.getYueGong());
        vehicleModule.setZuPinContactRepayType(dto.getZuPinContactRepayType());
        vehicleModule.setDelayMonth(dto.getDelayMonth());
        vehicleModule.setZuPinContactBaoYueType(dto.getZuPinContactBaoYueType());
        return vehicleModule;
    }

    public static ZuPinVehicleModuleDTO toZuPinVehicleModuleDTO(ZuPinVehicleModule vehicleModule) {
        final String uuid = vehicleModule.getUuid();
        final String moduleUuid = vehicleModule.getModuleUuid();
        final String moduleName = vehicleModule.getModuleName();
        final String moduleType = vehicleModule.getModuleType();
        final String moduleBrand = vehicleModule.getModuleBrand();
        final String moduleColor = vehicleModule.getModuleColor();
        final String moduleDianLiang = vehicleModule.getModuleDianLiang();

        //合同创建日期
        final String rentNumber = String.valueOf(vehicleModule.getRentNumber());
        final String rentMonth = String.valueOf(vehicleModule.getRentMonth());
        final String singleYaJin = vehicleModule.getSingleYaJin().setScale(2, RoundingMode.HALF_UP).toString();
        final String actualRentFee = vehicleModule.getActualRentFee().setScale(2, RoundingMode.HALF_UP).toString();

        //新增押金规则
        final ZuPinYaJinType zuPinYaJinType = vehicleModule.getZuPinYaJinType();
        final String fenQiShu = vehicleModule.getFenQiShu();
        final BigDecimal shouFu = vehicleModule.getShouFu();
        final BigDecimal yueGong = vehicleModule.getYueGong();
        //新增租金规则
        final ZuPinContactRepayType zuPinContactRepayType = vehicleModule.getZuPinContactRepayType();

        ZuPinVehicleModuleDTO dto = new ZuPinVehicleModuleDTO(uuid, moduleUuid, moduleName, moduleType, moduleBrand, moduleColor, moduleDianLiang,
                rentNumber, rentMonth,singleYaJin,actualRentFee,zuPinYaJinType,fenQiShu,shouFu,yueGong,zuPinContactRepayType);
        dto.setDelayMonth(vehicleModule.getDelayMonth());
        dto.setZuPinContactBaoYueType(vehicleModule.getZuPinContactBaoYueType());
        return dto;
    }

    public static List<ZuPinVehicleModuleDTO> toZuPinVehicleModuleDTOList(List<ZuPinVehicleModule> vehicleModules) {
        List<ZuPinVehicleModuleDTO> dtos = new ArrayList<ZuPinVehicleModuleDTO>();
        if (vehicleModules != null) {
            for (ZuPinVehicleModule loop : vehicleModules) {
                dtos.add(toZuPinVehicleModuleDTO(loop));
            }
        }
        return dtos;
    }


}
