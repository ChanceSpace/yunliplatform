package com.yajun.green.facade.assember.xiaoshou;



import com.yajun.green.domain.xiaoshou.XiaoShouContact;
import com.yajun.green.domain.xiaoshou.XiaoShouContactSupply;
import com.yajun.green.domain.xiaoshou.XiaoShouContactSupplyContent;
import com.yajun.green.facade.dto.contact.xiaoshou.ZuPinXiaoShouContactSupplyContentDTO;
import com.yajun.green.facade.dto.contact.xiaoshou.ZuPinXiaoShouContactSupplyDTO;
import com.yajun.green.repository.EntityLoadHolder;
import org.joda.time.DateTime;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by chance on 2017/8/29.
 */
public class XiaoShouContactSupplyWebAssember {
    public static ZuPinXiaoShouContactSupplyDTO toZuPinXiaoShouContactSupplyDTO(XiaoShouContactSupply zuPinContactSupply){
        final String uuid = zuPinContactSupply.getUuid();
        final List<XiaoShouContactSupplyContent> zuPinContactSupplyContents = zuPinContactSupply.getXiaoShouContactSupplyContents();
        Collections.sort(zuPinContactSupplyContents);
        final List<ZuPinXiaoShouContactSupplyContentDTO> zuPinContactSupplyContentDTOS=XiaoShouContactSupplyContentWebAssember.toZuPinXiaoShouContactSupplyContentDTOList(zuPinContactSupplyContents);
        final String caoZuoRen = zuPinContactSupply.getCaoZuoRen();
        final String caoZuoRenUuid = zuPinContactSupply.getCaoZuoRenUuid();
        final DateTime timestamp = zuPinContactSupply.getTimestamp();

        final String timestamp1 = timestamp.toString();
        ZuPinXiaoShouContactSupplyDTO dto = new ZuPinXiaoShouContactSupplyDTO(uuid,caoZuoRen,caoZuoRenUuid,timestamp1,zuPinContactSupplyContentDTOS);
        return dto;
    }

    public static List<ZuPinXiaoShouContactSupplyDTO> toZuPinXiaoShouContactSupplyDTOList(List<XiaoShouContactSupply> zuPinContactSupplies){
        List<ZuPinXiaoShouContactSupplyDTO> dtos = new ArrayList<ZuPinXiaoShouContactSupplyDTO>();
        if (zuPinContactSupplies != null) {
            for (XiaoShouContactSupply zuPinContactSupply : zuPinContactSupplies){
                dtos.add(toZuPinXiaoShouContactSupplyDTO(zuPinContactSupply));
            }
        }
        return dtos;
    }

    public static XiaoShouContactSupply toDomain(ZuPinXiaoShouContactSupplyDTO zuPinContactSupplyDTO, String zuPinContactUuid){
        XiaoShouContactSupply zuPinContactSupply = null;
        if (zuPinContactSupplyDTO == null) {
            return null;
        }
        String uuid = zuPinContactSupplyDTO.getUuid();
        if (StringUtils.hasText(uuid)) {
            zuPinContactSupply = (XiaoShouContactSupply) EntityLoadHolder.getUserDao().findByUuid(uuid,XiaoShouContactSupply.class);
        } else {
            zuPinContactSupply = new XiaoShouContactSupply();
            XiaoShouContact zuPinContact = new XiaoShouContact();
            zuPinContact.setUuid(zuPinContactUuid);
            zuPinContactSupply.setXiaoShouContact(zuPinContact);
        }

        List<ZuPinXiaoShouContactSupplyContentDTO> zuPinContactSupplyContentDTOS =zuPinContactSupplyDTO.getZuPinXiaoShouContactSupplyContentDTOList();
        String caoZuoRen = zuPinContactSupplyDTO.getCaoZuoRen();
        String caoZuoRenUuid = zuPinContactSupplyDTO.getCaoZuoRenUuid();
        String timestamp = zuPinContactSupplyDTO.getTimestamp();
        DateTime timestamp1 = new DateTime(timestamp);
        String zuPinContactSupplyUuid = zuPinContactSupply.getUuid();
        List<XiaoShouContactSupplyContent> zuPinContactSupplyContents = XiaoShouContactSupplyContentWebAssember.toZuPinXiaoShouContactSupplyContentList(zuPinContactSupplyContentDTOS,zuPinContactSupplyUuid);

        zuPinContactSupply.setCaoZuoRen(caoZuoRen);
        zuPinContactSupply.setCaoZuoRenUuid(caoZuoRenUuid);
        zuPinContactSupply.setTimestamp(timestamp1);
        zuPinContactSupply.setXiaoShouContactSupplyContents(zuPinContactSupplyContents);
        return zuPinContactSupply;
    }
}
