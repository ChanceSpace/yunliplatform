package com.yajun.green.facade.assember.xiaoshou;

import com.yajun.green.common.utils.JodaUtils;

import com.yajun.green.domain.xiaoshou.XiaoShouContactCharging;
import com.yajun.green.domain.xiaoshou.XiaoShouContactChargingStatus;
import com.yajun.green.domain.xiaoshou.XiaoShouContactChargingType;
import com.yajun.green.facade.dto.contact.xiaoshou.ZuPinXiaoShouContactChargingDTO;
import com.yajun.green.repository.EntityLoadHolder;
import org.joda.time.DateTime;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chance on 2017/9/22.
 */
public class ZuPinXiaoShouContactChargingWebAssember {
    public static ZuPinXiaoShouContactChargingDTO toZuPinXiaoShouContactChargingDTO(XiaoShouContactCharging zuPinContactCharging){
        final String uuid = zuPinContactCharging.getUuid();
        final String createTime = JodaUtils.todMYHmString(zuPinContactCharging.getCreateTime());
        final String finishTime = JodaUtils.todMYHmString(zuPinContactCharging.getFinishTime());
        final String chargingAddress = zuPinContactCharging.getChargingAddress();
        XiaoShouContactChargingStatus status = zuPinContactCharging.getZuPinXiaoShouContactChargingStatus();
        final String zuPinContactChargingStatus = status.name();
        final String zuPinContactChargingStatusName = status.getDescription();
        final String chargingNumber = zuPinContactCharging.getChargingNumber();
        XiaoShouContactChargingType type = zuPinContactCharging.getXiaoShouContactChargingType();
        final String zuPinContactChargingType = type.name();
        final String zuPinContactChargingTypeName = type.getDescription();
        //todo 缺少contactUuid
        final String contactUuid = zuPinContactCharging.getContactUuid();
        ZuPinXiaoShouContactChargingDTO dto = new ZuPinXiaoShouContactChargingDTO(uuid, chargingAddress, zuPinContactChargingStatus,zuPinContactChargingStatusName, createTime, finishTime, chargingNumber, zuPinContactChargingType,zuPinContactChargingTypeName,contactUuid);

        dto.setActorManUuid(zuPinContactCharging.getActorManUuid());
        dto.setActorManName(zuPinContactCharging.getActorManName());
        return dto;
    }

    public static List<ZuPinXiaoShouContactChargingDTO> toZuPinXiaoShouContactChargingDTOList(List<XiaoShouContactCharging> chargings){
        List<ZuPinXiaoShouContactChargingDTO> dtos = new ArrayList<ZuPinXiaoShouContactChargingDTO>();
        if (chargings != null) {
            for (XiaoShouContactCharging loop : chargings) {
                dtos.add(toZuPinXiaoShouContactChargingDTO(loop));
            }
        }
        return dtos;
    }

    public static XiaoShouContactCharging toDomain(ZuPinXiaoShouContactChargingDTO zuPinContactChargingDTO, String yiFangUuid, String yifangName){
        XiaoShouContactCharging zuPinContactCharging = null;
        if (zuPinContactChargingDTO == null) {
            return null;
        }
        String uuid = zuPinContactChargingDTO.getUuid();
        if (StringUtils.hasText(uuid)) {
            zuPinContactCharging = (XiaoShouContactCharging) EntityLoadHolder.getUserDao().findByUuid(uuid, XiaoShouContactCharging.class);
            zuPinContactCharging.setFinishTime(new DateTime());
        } else {
            final String chargingAddress = zuPinContactChargingDTO.getChargingAddress();
            zuPinContactCharging = new XiaoShouContactCharging(chargingAddress, yiFangUuid, yifangName);
        }
        zuPinContactCharging.setChargingAddress(zuPinContactChargingDTO.getChargingAddress());
        zuPinContactCharging.setChargingNumber(zuPinContactChargingDTO.getChargingNumber());
        zuPinContactCharging.setXiaoShouContactChargingType(XiaoShouContactChargingType.valueOf(zuPinContactChargingDTO.getXiaoShouContactChargingType()));
        if (StringUtils.hasText(zuPinContactChargingDTO.getXiaoShouContactChargingStatus())) {
            zuPinContactCharging.setZuPinXiaoShouContactChargingStatus(XiaoShouContactChargingStatus.valueOf(zuPinContactChargingDTO.getXiaoShouContactChargingStatus()));
        }
        zuPinContactCharging.setActorManUuid(zuPinContactChargingDTO.getActorManUuid());
        zuPinContactCharging.setActorManName(zuPinContactChargingDTO.getActorManName());
        return zuPinContactCharging;
    }
}
