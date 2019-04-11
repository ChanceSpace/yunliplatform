package com.yajun.green.facade.assember.xiaoshou;


import com.yajun.green.domain.xiaoshou.XiaoShouContact;
import com.yajun.green.domain.xiaoshou.XiaoShouType;
import com.yajun.green.domain.xiaoshou.XiaoShouVehicleModule;
import com.yajun.green.facade.dto.contact.xiaoshou.ZuPinXiaoShouVehicleModuleDTO;
import com.yajun.green.repository.EntityLoadHolder;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiuMengKe on 2017/8/9.
 */
public class XiaoShouVehicleModelWebAssember {

    public static XiaoShouVehicleModule toZuPinXiaoShouVehicleModuleDomain(ZuPinXiaoShouVehicleModuleDTO dto, String zuPinContactUuid) {
        XiaoShouVehicleModule vehicleModule = null;
        if(dto == null) {
            return null;
        }
        String uuid = dto.getUuid();
        if (StringUtils.hasText(uuid)) {
            vehicleModule = (XiaoShouVehicleModule) EntityLoadHolder.getUserDao().findByUuid(uuid, XiaoShouVehicleModule.class);
        } else {
            vehicleModule = new XiaoShouVehicleModule();
            XiaoShouContact contact = new XiaoShouContact();
            contact.setUuid(zuPinContactUuid);
            vehicleModule.setXiaoShouContact(contact);
        }
        vehicleModule.setModuleUuid(dto.getModuleUuid());
        vehicleModule.setModuleName(dto.getModuleName());
        vehicleModule.setModuleType(dto.getModuleType());
        vehicleModule.setModuleBrand(dto.getModuleBrand());
        vehicleModule.setModuleColor(dto.getModuleColor());
        vehicleModule.setModuleDianLiang(dto.getModuleDianLiang());

        vehicleModule.setSaleNumber(dto.getSaleNumber());
        vehicleModule.setXiaoShouType(dto.getXiaoShouType());
        vehicleModule.setDingJin(dto.getDingJin());
        vehicleModule.setSalePrice(dto.getSalePrice());
        vehicleModule.setShouFu(dto.getShouFu());
        vehicleModule.setWeiKuan(dto.getWeiKuan());
        vehicleModule.setMaxAnJieYear(dto.getMaxAnJieYear());
        return vehicleModule;
    }

    public static ZuPinXiaoShouVehicleModuleDTO toZuPinVehicleModuleDTO(XiaoShouVehicleModule vehicleModule) {
        final String uuid = vehicleModule.getUuid();
        final String moduleUuid = vehicleModule.getModuleUuid();
        final String moduleName = vehicleModule.getModuleName();
        final String moduleType = vehicleModule.getModuleType();
        final String moduleBrand = vehicleModule.getModuleBrand();
        final String moduleColor = vehicleModule.getModuleColor();
        final String moduleDianLiang = vehicleModule.getModuleDianLiang();

        int saleNumber = vehicleModule.getSaleNumber();
        XiaoShouType xiaoShouType = vehicleModule.getXiaoShouType();
        BigDecimal dingJin = vehicleModule.getDingJin();
        BigDecimal salePrice = vehicleModule.getSalePrice();
        BigDecimal shouFu = vehicleModule.getShouFu();
        BigDecimal weiKuan = vehicleModule.getWeiKuan();
        Integer maxAnJieYear = vehicleModule.getMaxAnJieYear();
        ZuPinXiaoShouVehicleModuleDTO dto = new ZuPinXiaoShouVehicleModuleDTO(uuid, moduleUuid, moduleName, moduleType, moduleBrand, moduleColor, moduleDianLiang, saleNumber, xiaoShouType, dingJin, salePrice, shouFu, weiKuan, maxAnJieYear);
        return dto;
    }

    public static List<ZuPinXiaoShouVehicleModuleDTO> toZuPinVehicleModuleDTOList(List<XiaoShouVehicleModule> vehicleModules) {
        List<ZuPinXiaoShouVehicleModuleDTO> dtos = new ArrayList<ZuPinXiaoShouVehicleModuleDTO>();
        if (vehicleModules != null) {
            for (XiaoShouVehicleModule loop : vehicleModules) {
                dtos.add(toZuPinVehicleModuleDTO(loop));
            }
        }
        return dtos;
    }


}
