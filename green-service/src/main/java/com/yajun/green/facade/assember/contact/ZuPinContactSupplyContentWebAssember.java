package com.yajun.green.facade.assember.contact;


import com.yajun.green.domain.contact.ZuPinContactSupply;
import com.yajun.green.domain.contact.ZuPinContactSupplyContent;

import com.yajun.green.facade.dto.contact.ZuPinContactSupplyContentDTO;
import com.yajun.green.repository.EntityLoadHolder;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chance on 2017/8/29.
 */
public class ZuPinContactSupplyContentWebAssember {
    public static ZuPinContactSupplyContentDTO toZuPinContactSupplyContentDTO(ZuPinContactSupplyContent zuPinContactSupplyContent){
       final String uuid = zuPinContactSupplyContent.getUuid();
       final String content = zuPinContactSupplyContent.getContent();

       ZuPinContactSupplyContentDTO dto = new ZuPinContactSupplyContentDTO(uuid,content);
       return dto;
    }
    public static List<ZuPinContactSupplyContentDTO> toZuPinContactSupplyContentDTOList(List<ZuPinContactSupplyContent> zuPinContactSupplyContents){
        List<ZuPinContactSupplyContentDTO> dtos = new ArrayList<ZuPinContactSupplyContentDTO>();
        if (zuPinContactSupplyContents != null) {
            for (ZuPinContactSupplyContent zuPinContactSupplyContent : zuPinContactSupplyContents){
                dtos.add(toZuPinContactSupplyContentDTO(zuPinContactSupplyContent));
            }
        }
        return dtos;
    }
    public static ZuPinContactSupplyContent toDomain(ZuPinContactSupplyContentDTO zuPinContactSupplyContentDTO,String zuPinContactSupplyUuid){
        ZuPinContactSupplyContent zuPinContactSupplyContent = null;
        if (zuPinContactSupplyContentDTO == null) {
            return null;
        }
        String uuid = zuPinContactSupplyContentDTO.getUuid();
        if (StringUtils.hasText(uuid)) {
            zuPinContactSupplyContent = (ZuPinContactSupplyContent) EntityLoadHolder.getUserDao().findByUuid(uuid, ZuPinContactSupplyContent.class);
        } else {
            zuPinContactSupplyContent = new ZuPinContactSupplyContent();
            ZuPinContactSupply zuPinContactSupply = new ZuPinContactSupply();
            zuPinContactSupply.setUuid(zuPinContactSupplyUuid);
            String content = zuPinContactSupplyContentDTO.getContent();
            zuPinContactSupplyContent = new ZuPinContactSupplyContent(content,zuPinContactSupply);
        }

        return zuPinContactSupplyContent;
    }
    public static List<ZuPinContactSupplyContent> toZuPinContactSupplyContentList(List<ZuPinContactSupplyContentDTO> zuPinContactSupplyContentDTOS,String zuPinContactSupplyUuid){
        List<ZuPinContactSupplyContent> domains = new ArrayList<ZuPinContactSupplyContent>();
        if (zuPinContactSupplyContentDTOS != null) {
            for (ZuPinContactSupplyContentDTO zuPinContactSupplyContentDTO: zuPinContactSupplyContentDTOS){
                    domains.add(toDomain(zuPinContactSupplyContentDTO,zuPinContactSupplyUuid));
            }
        }
        return domains;
    }
}
