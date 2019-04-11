package com.yajun.green.facade.assember.contact;

import com.yajun.green.common.security.LoginInfo;
import com.yajun.green.domain.contact.*;
import com.yajun.green.facade.dto.contact.ZuPinVehicleExecuteDTO;
import com.yajun.green.security.SecurityUtils;
import org.joda.time.LocalDate;
import org.springframework.util.StringUtils;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * Jack Wang
 */
public class ZuPinVehicleExecuteWebAssember {

    public static ZuPinVehicleExecuteDTO toZuPinVehicleExecuteDTO(ZuPinVehicleExecute vehicleExecute) {
        final String uuid = vehicleExecute.getUuid();
        final String moduleName = vehicleExecute.getModuleName();
        final String moduleType = vehicleExecute.getModuleType();
        final String moduleBrand = vehicleExecute.getModuleBrand();
        final String moduleColor = vehicleExecute.getModuleColor();
        final String moduleDianLiang = vehicleExecute.getModuleDianLiang();

        //提车合同条款
        final String rentMonth = String.valueOf(vehicleExecute.getRentMonth());

        //提车细节
        final String vehicleNum = vehicleExecute.getVehicleNum();
        final int tiChePiCi = vehicleExecute.getTiChePiCi();
        final String tiCheDate = vehicleExecute.getTiCheDate().toString();
        final String nextFeeDate = vehicleExecute.getNextFeeDate().toString();
        final String zuPinContactUuid = vehicleExecute.getZuPinContact().getUuid();

        final String saleManName = vehicleExecute.getZuPinContact().getSaleManName();
        final String actualRentFee = vehicleExecute.getActualRentFee().setScale(2, RoundingMode.HALF_UP).toString();

        //新增押金规则
        final ZuPinYaJinType zuPinYaJinType= vehicleExecute.getZuPinYaJinType();
        final String fenQiShu = vehicleExecute.getFenQiShu();
        final BigDecimal shouFu = vehicleExecute.getShouFu();
        final BigDecimal yueGong = vehicleExecute.getYueGong();
        //新增租金规则
        final ZuPinContactRepayType zuPinContactRepayType = vehicleExecute.getZuPinContactRepayType();
        final int delayMonth = vehicleExecute.getDelayMonth();

        ZuPinVehicleExecuteDTO dto = new ZuPinVehicleExecuteDTO(uuid,saleManName, moduleName, moduleType, moduleBrand, moduleColor, moduleDianLiang,
                 rentMonth, vehicleNum, tiChePiCi, tiCheDate, nextFeeDate,zuPinContactUuid,actualRentFee,zuPinYaJinType,fenQiShu,shouFu,yueGong,zuPinContactRepayType, delayMonth);

        dto.setContactNumber(vehicleExecute.getZuPinContact().getContactNumber());
        dto.setCumtomerPhoneNumber(vehicleExecute.getZuPinContact().getContactPhone());
        dto.setCustomerName(vehicleExecute.getZuPinContact().getYiFangName());
        dto.setJieshuDate(vehicleExecute.getJieshuDate().toString());
        dto.setOver(vehicleExecute.isOver());
        dto.setPiciOver(vehicleExecute.isPiciOver());
        dto.setOverButNotRevert(vehicleExecute.isOverButNotRevert());
        dto.setNextMessageDate(vehicleExecute.getNextMessageDate().toString());
        dto.setActualyajinrepaymonth(vehicleExecute.getActualyajinrepaymonth());
        dto.setActualzujinrepaymonth(vehicleExecute.getActualzujinrepaymonth());
        dto.setYajinHasPayCishu(vehicleExecute.getYajinHasPayCishu());
        dto.setNextYajinDate(vehicleExecute.getNextYajinDate());
        dto.setDelayMonth(vehicleExecute.getDelayMonth());
        dto.setSingleYaJin(vehicleExecute.getSingleYaJin());

        dto.setComment(vehicleExecute.getComment());

        return dto;
    }

    public static List<ZuPinVehicleExecuteDTO> toZuPinVehicleExecuteDTOList(List<ZuPinVehicleExecute> vehicleExecutes) {
        List<ZuPinVehicleExecuteDTO> dtos = new ArrayList<ZuPinVehicleExecuteDTO>();
        if (vehicleExecutes != null) {
            for (ZuPinVehicleExecute loop : vehicleExecutes) {
                dtos.add(toZuPinVehicleExecuteDTO(loop));
            }
        }
        return dtos;
    }

    public static List<ZuPinVehicleExecute> toZuPinVehicleExcuteList(List<ZuPinVehicleExecuteDTO> vehicleExecuteDTOSs) {
        List<ZuPinVehicleExecute> executes = new ArrayList<ZuPinVehicleExecute>();
        if (vehicleExecuteDTOSs != null) {
            for (ZuPinVehicleExecuteDTO loop : vehicleExecuteDTOSs) {
                executes.add(toZuPinVehicleExcute(loop));
            }
        }
        return  executes;
    }

    public static ZuPinVehicleExecute toZuPinVehicleExcute(ZuPinVehicleExecuteDTO dto) {
        LoginInfo loginInfo = SecurityUtils.currentLoginUser();

        String moduleName = dto.getModuleName();
        String moduleType = dto.getModuleType();
        String moduleColor = dto.getModuleColor();
        String moduleDianLiang = dto.getModuleDianLiang();
        int rentNumber = 0;
        int rentMonth = Integer.valueOf(dto.getRentMonth());
        BigDecimal actualRentFee = BigDecimal.valueOf(Double.valueOf(dto.getActualRentFee()));
        //新增押金规则
        ZuPinYaJinType zuPinYaJinType = dto.getZuPinYaJinType();
        String fenQiShu = dto.getFenQiShu();
        BigDecimal shouFu = dto.getShouFu();
        BigDecimal yueGong = dto.getYueGong();
        //新增租金规则
        ZuPinContactRepayType zuPinContactRepayType = dto.getZuPinContactRepayType();

        ZuPinVehicleModule module = new ZuPinVehicleModule(null, moduleName, moduleType, moduleColor, moduleDianLiang, rentNumber, rentMonth, actualRentFee, zuPinYaJinType, fenQiShu, shouFu, yueGong, zuPinContactRepayType);
        module.setModuleBrand(dto.getModuleBrand());
        String vehicleNumber = dto.getVehicleNum();
        int tiChePiCi = dto.getTiChePiCi();
        String tiCheDate = dto.getTiCheDate();
        String nextFeeDate = dto.getNextFeeDate();
        ZuPinVehicleExecute execute = new ZuPinVehicleExecute(loginInfo, module, vehicleNumber, tiChePiCi, tiCheDate, nextFeeDate);
        ZuPinContact contact = new ZuPinContact();
        contact.setUuid(dto.getZuPinContactUuid());
        execute.setZuPinContact(contact);
        if (StringUtils.hasText(dto.getUuid())) {
            execute.setUuid(dto.getUuid());
        }
        execute.setJieshuDate(new LocalDate(dto.getJieshuDate()));
        execute.setOver(dto.isOver());
        execute.setPiciOver(dto.isPiciOver());
        execute.setOverButNotRevert(dto.isOverButNotRevert());
        execute.setNextMessageDate(new LocalDate(dto.getNextMessageDate()));
        execute.setActualyajinrepaymonth(dto.getActualyajinrepaymonth());
        execute.setActualzujinrepaymonth(dto.getActualzujinrepaymonth());
        execute.setYajinHasPayCishu(dto.getYajinHasPayCishu());
        execute.setNextYajinDate(dto.getNextYajinDate());
        execute.setDelayMonth(dto.getDelayMonth());
        execute.setSingleYaJin(dto.getSingleYaJin());

        execute.setComment(dto.getComment());
        return execute;
    }

}
