package com.yajun.green.facade.assember.contact;

import com.yajun.green.common.utils.JodaUtils;
import com.yajun.green.domain.contact.ZuPinContactCharging;
import com.yajun.green.domain.contact.ZuPinContactChargingStatus;
import com.yajun.green.domain.contact.ZuPinContactChargingType;
import com.yajun.green.facade.dto.contact.ZuPinContactChargingDTO;
import com.yajun.green.repository.EntityLoadHolder;
import org.joda.time.DateTime;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chance on 2017/9/22.
 */
public class ZuPinContactChargingWebAssember {
    public static ZuPinContactChargingDTO toZuPinContactChargingDTO(ZuPinContactCharging zuPinContactCharging){
        final String uuid = zuPinContactCharging.getUuid();
        final String createTime = JodaUtils.todMYHmString(zuPinContactCharging.getCreateTime());
        final String finishTime = JodaUtils.todMYHmString(zuPinContactCharging.getFinishTime());
        final String chargingAddress = zuPinContactCharging.getChargingAddress();
        ZuPinContactChargingStatus status = zuPinContactCharging.getZuPinContactChargingStatus();
        final String zuPinContactChargingStatus = status.name();
        final String zuPinContactChargingStatusName = status.getDescription();
        final String chargingNumber = zuPinContactCharging.getChargingNumber();
        ZuPinContactChargingType type = zuPinContactCharging.getZuPinContactChargingType();
        final String zuPinContactChargingType = type.name();
        final String zuPinContactChargingTypeName = type.getDescription();

        ZuPinContactChargingDTO dto = new ZuPinContactChargingDTO(uuid, chargingAddress, zuPinContactChargingStatus,zuPinContactChargingStatusName, createTime, finishTime, chargingNumber, zuPinContactChargingType,zuPinContactChargingTypeName);
        dto.setActorManUuid(zuPinContactCharging.getActorManUuid());
        dto.setActorManName(zuPinContactCharging.getActorManName());
        return dto;
    }

    public static List<ZuPinContactChargingDTO> toZuPinContactChargingDTOList(List<ZuPinContactCharging> chargings){
        List<ZuPinContactChargingDTO> dtos = new ArrayList<ZuPinContactChargingDTO>();
        if (chargings != null) {
            for (ZuPinContactCharging loop : chargings) {
                dtos.add(toZuPinContactChargingDTO(loop));
            }
        }
        return dtos;
    }

    public static ZuPinContactCharging toDomain(ZuPinContactChargingDTO zuPinContactChargingDTO, String yiFangUuid, String yifangName){
        ZuPinContactCharging zuPinContactCharging = null;
        if (zuPinContactChargingDTO == null) {
            return null;
        }
        String uuid = zuPinContactChargingDTO.getUuid();
        if (StringUtils.hasText(uuid)) {
            zuPinContactCharging = (ZuPinContactCharging) EntityLoadHolder.getUserDao().findByUuid(uuid, ZuPinContactCharging.class);
            zuPinContactCharging.setFinishTime(new DateTime());
        } else {
            final String chargingAddress = zuPinContactChargingDTO.getChargingAddress();
            zuPinContactCharging = new ZuPinContactCharging(chargingAddress, yiFangUuid, yifangName);
        }
        zuPinContactCharging.setChargingAddress(zuPinContactChargingDTO.getChargingAddress());
        zuPinContactCharging.setChargingNumber(zuPinContactChargingDTO.getChargingNumber());
        zuPinContactCharging.setZuPinContactChargingType(ZuPinContactChargingType.valueOf(zuPinContactChargingDTO.getZuPinContactChargingType()));
        if (StringUtils.hasText(zuPinContactChargingDTO.getZuPinContactChargingStatus())) {
            zuPinContactCharging.setZuPinContactChargingStatus(ZuPinContactChargingStatus.valueOf(zuPinContactChargingDTO.getZuPinContactChargingStatus()));
        }
        zuPinContactCharging.setActorManUuid(zuPinContactChargingDTO.getActorManUuid());
        zuPinContactCharging.setActorManName(zuPinContactChargingDTO.getActorManName());
        return zuPinContactCharging;
    }
}
