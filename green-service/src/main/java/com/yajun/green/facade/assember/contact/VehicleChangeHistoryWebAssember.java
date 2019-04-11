package com.yajun.green.facade.assember.contact;

import com.yajun.green.domain.contact.VehicleChangeHistory;
import com.yajun.green.domain.contact.ZuPinContact;
import com.yajun.green.domain.contact.ZuPinVehicleExecute;

import com.yajun.green.facade.dto.contact.VehicleChangeHistoryDTO;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chance on 2017/8/21.
 */
public class VehicleChangeHistoryWebAssember {
    public static VehicleChangeHistoryDTO toVehicleChangeHistoryDTO(VehicleChangeHistory vehicleChangeHistory){
        final String uuid =vehicleChangeHistory.getUuid();

        final String vehicleNumberBefore = vehicleChangeHistory.getVehicleNumberBefore();
        final String vehicleNumberNow = vehicleChangeHistory.getVehicleNumberNow();
        final ZuPinVehicleExecute zuPinVehicleExecute = vehicleChangeHistory.getZuPinVehicleExecute();
        final String caoZuoRen = vehicleChangeHistory.getCaoZuoRen();
        final String caoZuoRenUuid = vehicleChangeHistory.getCaoZuoRenUuid();
        final DateTime changeDate = vehicleChangeHistory.getChangeDate();
        final ZuPinContact zuPinContact = vehicleChangeHistory.getZuPinContact();

        final String changeDate1 = changeDate.toString();

        VehicleChangeHistoryDTO dto = new VehicleChangeHistoryDTO(uuid,vehicleNumberBefore,vehicleNumberNow,zuPinVehicleExecute,caoZuoRen,caoZuoRenUuid,changeDate1,zuPinContact);
        return dto;
    }

    public static List<VehicleChangeHistoryDTO> toVehicleChangeHistoryDTOList(List<VehicleChangeHistory> vehicleChangeHistories){
        List<VehicleChangeHistoryDTO> dtos = new ArrayList<VehicleChangeHistoryDTO>();
        if (vehicleChangeHistories != null) {
            for (VehicleChangeHistory vehicleChangeHistory : vehicleChangeHistories){
                dtos.add(toVehicleChangeHistoryDTO(vehicleChangeHistory));
            }
        }
        return dtos;
    }

    public static VehicleChangeHistory toDomain(VehicleChangeHistoryDTO vehicleChangeHistoryDTO){
        VehicleChangeHistory vehicleChangeHistory = null;
        if (vehicleChangeHistoryDTO == null) {
            return null;
        }
        String vehicleNumberBefore = vehicleChangeHistoryDTO.getVehicleNumberBefore();
        String vehicleNumberNow = vehicleChangeHistoryDTO.getVehicleNumberNow();
        ZuPinVehicleExecute zuPinVehicleExecute = vehicleChangeHistoryDTO.getZuPinVehicleExecute();
        String caoZuoRen = vehicleChangeHistoryDTO.getCaoZuoRen();
        String caoZuoRenUuid = vehicleChangeHistoryDTO.getCaoZuoRenUuid();
        String changeDate = vehicleChangeHistoryDTO.getChangeDate();
        DateTime changDate1 = new DateTime(changeDate);
        ZuPinContact zuPinContact = vehicleChangeHistoryDTO.getZuPinContact();

        vehicleChangeHistory = new VehicleChangeHistory(vehicleNumberBefore,vehicleNumberNow,zuPinVehicleExecute,caoZuoRen,caoZuoRenUuid,changDate1,zuPinContact);
        return vehicleChangeHistory;
    }
}
