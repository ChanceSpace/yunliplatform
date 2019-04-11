package com.yajun.green.facade.assember.xiaoshou;

import com.yajun.green.common.utils.JodaUtils;
import com.yajun.green.domain.xiaoshou.XiaoShouContactLog;
import com.yajun.green.facade.dto.contact.xiaoshou.ZuPinXiaoShouContactLogDTO;
import java.util.ArrayList;
import java.util.List;


/**
 * Jack Wang
 */
public class XiaoShouContactLogWebAssember {

    public static ZuPinXiaoShouContactLogDTO toZuPinXiaoShouContactLogDTO(XiaoShouContactLog contactLog) {
        final String uuid = contactLog.getUuid();
        final String actorManName = contactLog.getActorManName();
        final String happenTime = JodaUtils.todMYHmString(contactLog.getHappenTime());
        final String description = contactLog.getDescription();

        ZuPinXiaoShouContactLogDTO dto = new ZuPinXiaoShouContactLogDTO(uuid, happenTime, actorManName, description);

        return dto;
    }

    public static List<ZuPinXiaoShouContactLogDTO> toZuPinXiaoShouContactLogDTOList(List<XiaoShouContactLog> contactLogs) {
        List<ZuPinXiaoShouContactLogDTO> dtos = new ArrayList<ZuPinXiaoShouContactLogDTO>();
        if (contactLogs != null) {
            for (XiaoShouContactLog log : contactLogs) {
                dtos.add(toZuPinXiaoShouContactLogDTO(log));
            }
        }
        return dtos;
    }
}
