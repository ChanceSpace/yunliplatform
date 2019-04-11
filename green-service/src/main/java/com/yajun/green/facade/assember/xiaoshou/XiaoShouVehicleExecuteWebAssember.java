package com.yajun.green.facade.assember.xiaoshou;

import com.yajun.green.common.security.LoginInfo;
import com.yajun.green.domain.xiaoshou.*;
import com.yajun.green.facade.dto.contact.xiaoshou.ZuPinXiaoShouVehicleExecuteDTO;
import com.yajun.green.security.SecurityUtils;
import org.joda.time.LocalDate;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Jack Wang
 */
public class XiaoShouVehicleExecuteWebAssember {

    public static ZuPinXiaoShouVehicleExecuteDTO toZuPinXiaoShouVehicleExecuteDTO(XiaoShouVehicleExecute vehicleExecute) {
        final String uuid = vehicleExecute.getUuid();
        final String moduleName = vehicleExecute.getModuleName();
        final String moduleType = vehicleExecute.getModuleType();
        final String moduleBrand = vehicleExecute.getModuleBrand();
        final String moduleColor = vehicleExecute.getModuleColor();
        final String moduleDianLiang = vehicleExecute.getModuleDianLiang();
        //交易人信息
        String actorManName = vehicleExecute.getActorManName();
        String actorManUuid = vehicleExecute.getActorManUuid();
        String vehicleNum = vehicleExecute.getVehicleNum();
        //提车批次
        int tiChePiCi = vehicleExecute.getTiChePiCi();
        //提车日期
        LocalDate tiCheDate = vehicleExecute.getTiCheDate();
        //类型 全款 按揭
        XiaoShouType xiaoShouType = vehicleExecute.getXiaoShouType();
        //定金
        BigDecimal dingJin = vehicleExecute.getDingJin();
        //单车售价 = 首付+尾款
        BigDecimal salePrice = vehicleExecute.getSalePrice();
        //首付
        BigDecimal shouFu = vehicleExecute.getShouFu();
        //尾款
        BigDecimal weiKuan = vehicleExecute.getWeiKuan();

        Integer maxAnJieYear = vehicleExecute.getMaxAnJieYear();
        //执行状态
        XiaoShouContactExecuteStatus executeStatus = vehicleExecute.getExecuteStatus();
        //备注
        String comment = vehicleExecute.getComment();
        ZuPinXiaoShouVehicleExecuteDTO dto = new ZuPinXiaoShouVehicleExecuteDTO(uuid, actorManName, actorManUuid, moduleName, moduleType, moduleBrand, moduleColor, moduleDianLiang,
                vehicleNum, tiChePiCi, tiCheDate, xiaoShouType, dingJin, salePrice, shouFu, weiKuan, maxAnJieYear, executeStatus, comment);
        dto.setComment(vehicleExecute.getComment());
        return dto;
    }

    public static List<ZuPinXiaoShouVehicleExecuteDTO> toZuPinXiaoShouVehicleExecuteDTOList(List<XiaoShouVehicleExecute> vehicleExecutes) {
        List<ZuPinXiaoShouVehicleExecuteDTO> dtos = new ArrayList<ZuPinXiaoShouVehicleExecuteDTO>();
        if (vehicleExecutes != null) {
            for (XiaoShouVehicleExecute loop : vehicleExecutes) {
                dtos.add(toZuPinXiaoShouVehicleExecuteDTO(loop));
            }
        }
        return dtos;
    }

    public static List<XiaoShouVehicleExecute> toZuPinXiaoShouVehicleExcuteList(List<ZuPinXiaoShouVehicleExecuteDTO> vehicleExecuteDTOSs) {
        List<XiaoShouVehicleExecute> executes = new ArrayList<XiaoShouVehicleExecute>();
        if (vehicleExecuteDTOSs != null) {
            for (ZuPinXiaoShouVehicleExecuteDTO loop : vehicleExecuteDTOSs) {
                executes.add(toZuPinVehicleExcute(loop));
            }
        }
        return executes;
    }

    public static XiaoShouVehicleExecute toZuPinVehicleExcute(ZuPinXiaoShouVehicleExecuteDTO dto) {
        LoginInfo loginInfo = SecurityUtils.currentLoginUser();
        //车型数据和车牌号
        String moduleName = dto.getModuleName();
        String moduleType = dto.getModuleType();
        String moduleColor = dto.getModuleColor();
        String moduleBrand = dto.getModuleBrand();
        String moduleDianLiang = dto.getModuleDianLiang();
        String vehicleNum = dto.getVehicleNum();

        //提车批次
        int tiChePiCi = dto.getTiChePiCi();
        //提车日期
        LocalDate tiCheDate = dto.getTiCheDate();
        //类型 全款 按揭
        XiaoShouType xiaoShouType = dto.getXiaoShouType();
        //定金
        BigDecimal dingJin = dto.getDingJin();
        //单车售价 = 首付+尾款
        BigDecimal salePrice = dto.getSalePrice();
        //首付
        BigDecimal shouFu = dto.getShouFu();
        //尾款
        BigDecimal weiKuan = dto.getWeiKuan();
        //执行状态
        XiaoShouContactExecuteStatus executeStatus = dto.getExecuteStatus();
        Integer maxAnjieYear = dto.getMaxAnJieYear();

        XiaoShouVehicleModule module = new XiaoShouVehicleModule(null, moduleName, moduleType, moduleBrand, moduleColor, moduleDianLiang, 0, xiaoShouType, dingJin, salePrice, shouFu, weiKuan, maxAnjieYear);
        XiaoShouVehicleExecute execute = new XiaoShouVehicleExecute(loginInfo, module, vehicleNum, tiChePiCi, tiCheDate);
        if (StringUtils.hasText(dto.getUuid())) {
            execute.setUuid(dto.getUuid());
        }
        //备注
        String comment = dto.getComment();
        execute.setComment(comment);

        return execute;
    }

}
