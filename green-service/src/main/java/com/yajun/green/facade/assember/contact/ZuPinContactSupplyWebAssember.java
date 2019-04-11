package com.yajun.green.facade.assember.contact;


import com.yajun.green.domain.contact.ZuPinContact;
import com.yajun.green.domain.contact.ZuPinContactSupply;
import com.yajun.green.domain.contact.ZuPinContactSupplyContent;

import com.yajun.green.facade.dto.contact.ZuPinContactSupplyContentDTO;
import com.yajun.green.facade.dto.contact.ZuPinContactSupplyDTO;
import com.yajun.green.repository.EntityLoadHolder;
import org.joda.time.DateTime;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by chance on 2017/8/29.
 */
public class ZuPinContactSupplyWebAssember {
    public static ZuPinContactSupplyDTO toZuPinContactSupplyDTO(ZuPinContactSupply zuPinContactSupply){
        final String uuid = zuPinContactSupply.getUuid();
        final List<ZuPinContactSupplyContent> zuPinContactSupplyContents = zuPinContactSupply.getZuPinContactSupplyContents();
        Collections.sort(zuPinContactSupplyContents);
        final List<ZuPinContactSupplyContentDTO> zuPinContactSupplyContentDTOS=ZuPinContactSupplyContentWebAssember.toZuPinContactSupplyContentDTOList(zuPinContactSupplyContents);
        final String caoZuoRen = zuPinContactSupply.getCaoZuoRen();
        final String caoZuoRenUuid = zuPinContactSupply.getCaoZuoRenUuid();
        final DateTime timestamp = zuPinContactSupply.getTimestamp();

        final String timestamp1 = timestamp.toString();
        ZuPinContactSupplyDTO dto = new ZuPinContactSupplyDTO(uuid,caoZuoRen,caoZuoRenUuid,timestamp1,zuPinContactSupplyContentDTOS);
        return dto;
    }

    public static List<ZuPinContactSupplyDTO> toZuPinContactSupplyDTOList(List<ZuPinContactSupply> zuPinContactSupplies){
        List<ZuPinContactSupplyDTO> dtos = new ArrayList<ZuPinContactSupplyDTO>();
        if (zuPinContactSupplies != null) {
            for (ZuPinContactSupply zuPinContactSupply : zuPinContactSupplies){
                dtos.add(toZuPinContactSupplyDTO(zuPinContactSupply));
            }
        }
        return dtos;
    }

    public static ZuPinContactSupply toDomain(ZuPinContactSupplyDTO zuPinContactSupplyDTO,String zuPinContactUuid){
        ZuPinContactSupply zuPinContactSupply = null;
        if (zuPinContactSupplyDTO == null) {
            return null;
        }
        String uuid = zuPinContactSupplyDTO.getUuid();
        if (StringUtils.hasText(uuid)) {
            zuPinContactSupply = (ZuPinContactSupply) EntityLoadHolder.getUserDao().findByUuid(uuid,ZuPinContactSupply.class);
        } else {
            zuPinContactSupply = new ZuPinContactSupply();
            ZuPinContact zuPinContact = new ZuPinContact();
            zuPinContact.setUuid(zuPinContactUuid);
            zuPinContactSupply.setZuPinContact(zuPinContact);
        }

        List<ZuPinContactSupplyContentDTO> zuPinContactSupplyContentDTOS =zuPinContactSupplyDTO.getZuPinContactSupplyContentDTOS();
        String caoZuoRen = zuPinContactSupplyDTO.getCaoZuoRen();
        String caoZuoRenUuid = zuPinContactSupplyDTO.getCaoZuoRenUuid();
        String timestamp = zuPinContactSupplyDTO.getTimestamp();
        DateTime timestamp1 = new DateTime(timestamp);
        String zuPinContactSupplyUuid = zuPinContactSupply.getUuid();
        List<ZuPinContactSupplyContent> zuPinContactSupplyContents = ZuPinContactSupplyContentWebAssember.toZuPinContactSupplyContentList(zuPinContactSupplyContentDTOS,zuPinContactSupplyUuid);

        zuPinContactSupply.setCaoZuoRen(caoZuoRen);
        zuPinContactSupply.setCaoZuoRenUuid(caoZuoRenUuid);
        zuPinContactSupply.setTimestamp(timestamp1);
        zuPinContactSupply.setZuPinContactSupplyContents(zuPinContactSupplyContents);
        return zuPinContactSupply;
    }
}
