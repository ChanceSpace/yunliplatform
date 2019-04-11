package com.yajun.green.facade.assember.xiaoshou;


import com.yajun.green.domain.xiaoshou.XiaoShouContactSupply;
import com.yajun.green.domain.xiaoshou.XiaoShouContactSupplyContent;
import com.yajun.green.facade.dto.contact.xiaoshou.ZuPinXiaoShouContactSupplyContentDTO;
import com.yajun.green.repository.EntityLoadHolder;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chance on 2017/8/29.
 */
public class XiaoShouContactSupplyContentWebAssember {
    public static ZuPinXiaoShouContactSupplyContentDTO toZuPinXiaoShouContactSupplyContentDTO(XiaoShouContactSupplyContent zuPinContactSupplyContent){
       final String uuid = zuPinContactSupplyContent.getUuid();
       final String content = zuPinContactSupplyContent.getContent();

       ZuPinXiaoShouContactSupplyContentDTO dto = new ZuPinXiaoShouContactSupplyContentDTO(uuid,content);
       return dto;
    }
    public static List<ZuPinXiaoShouContactSupplyContentDTO> toZuPinXiaoShouContactSupplyContentDTOList(List<XiaoShouContactSupplyContent> zuPinContactSupplyContents){
        List<ZuPinXiaoShouContactSupplyContentDTO> dtos = new ArrayList<ZuPinXiaoShouContactSupplyContentDTO>();
        if (zuPinContactSupplyContents != null) {
            for (XiaoShouContactSupplyContent zuPinContactSupplyContent : zuPinContactSupplyContents){
                dtos.add(toZuPinXiaoShouContactSupplyContentDTO(zuPinContactSupplyContent));
            }
        }
        return dtos;
    }
    public static XiaoShouContactSupplyContent toDomain(ZuPinXiaoShouContactSupplyContentDTO zuPinContactSupplyContentDTO, String xiaoShouContactSupplyUuid){
        XiaoShouContactSupplyContent zuPinContactSupplyContent = null;
        if (zuPinContactSupplyContentDTO == null) {
            return null;
        }
        String uuid = zuPinContactSupplyContentDTO.getUuid();
        if (StringUtils.hasText(uuid)) {
            zuPinContactSupplyContent = (XiaoShouContactSupplyContent) EntityLoadHolder.getUserDao().findByUuid(uuid, XiaoShouContactSupplyContent.class);
        } else {
            zuPinContactSupplyContent = new XiaoShouContactSupplyContent();
            XiaoShouContactSupply zuPinContactSupply = new XiaoShouContactSupply();
            zuPinContactSupply.setUuid(xiaoShouContactSupplyUuid);
            String content = zuPinContactSupplyContentDTO.getContent();
            zuPinContactSupplyContent = new XiaoShouContactSupplyContent(content,zuPinContactSupply);
        }

        return zuPinContactSupplyContent;
    }
    public static List<XiaoShouContactSupplyContent> toZuPinXiaoShouContactSupplyContentList(List<ZuPinXiaoShouContactSupplyContentDTO> zuPinContactSupplyContentDTOS, String zuPinContactSupplyUuid){
        List<XiaoShouContactSupplyContent> domains = new ArrayList<XiaoShouContactSupplyContent>();
        if (zuPinContactSupplyContentDTOS != null) {
            for (ZuPinXiaoShouContactSupplyContentDTO zuPinContactSupplyContentDTO: zuPinContactSupplyContentDTOS){
                    domains.add(toDomain(zuPinContactSupplyContentDTO,zuPinContactSupplyUuid));
            }
        }
        return domains;
    }
}
