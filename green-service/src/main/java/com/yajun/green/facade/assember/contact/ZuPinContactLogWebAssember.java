package com.yajun.green.facade.assember.contact;

import com.yajun.green.common.utils.JodaUtils;
import com.yajun.green.domain.contact.ZuPinContact;
import com.yajun.green.domain.contact.ZuPinContactLog;

import com.yajun.green.facade.dto.contact.ZuPinContactLogDTO;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Jack Wang
 */
public class ZuPinContactLogWebAssember {

    public static ZuPinContactLogDTO toZuPinContactLogDTO(ZuPinContactLog contactLog) {
        final String uuid = contactLog.getUuid();
        final String actorManName = contactLog.getActorManName();
        final String happenTime = JodaUtils.todMYHmString(contactLog.getHappenTime());
        final String description = contactLog.getDescription();

        ZuPinContactLogDTO dto = new ZuPinContactLogDTO(uuid, happenTime, actorManName, description);

        return dto;
    }

    public static List<ZuPinContactLogDTO> toZuPinContactLogDTOList(List<ZuPinContactLog> contactLogs) {
        List<ZuPinContactLogDTO> dtos = new ArrayList<ZuPinContactLogDTO>();
        if (contactLogs != null) {
            for (ZuPinContactLog log : contactLogs) {
                dtos.add(toZuPinContactLogDTO(log));
            }
        }
        return dtos;
    }
}
