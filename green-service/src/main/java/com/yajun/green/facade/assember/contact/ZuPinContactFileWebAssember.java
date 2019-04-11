package com.yajun.green.facade.assember.contact;

import com.yajun.green.common.utils.JodaUtils;
import com.yajun.green.domain.contact.ZuPinContactFile;
import com.yajun.green.facade.dto.contact.ZuPinContactFileDTO;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiuMengKe on 2017/8/9.
 */
public class ZuPinContactFileWebAssember {

    public static ZuPinContactFileDTO toZuPinContactFileDTO(ZuPinContactFile contactFile) {
        final String uuid = contactFile.getUuid();
        final String actorManName = contactFile.getActorManName();
        final String uploadFileName = contactFile.getUploadFileName();
        final String actualFileName = contactFile.getActualFileName();
        final String uploadTime = JodaUtils.todMYHmString(contactFile.getUploadTime());

        ZuPinContactFileDTO dto = new ZuPinContactFileDTO(uuid, actorManName, uploadFileName, actualFileName, uploadTime);

        return dto;
    }

    public static List<ZuPinContactFileDTO> toZuPinContactFileDTOList(List<ZuPinContactFile> contactFiles) {
        List<ZuPinContactFileDTO> dtos = new ArrayList<ZuPinContactFileDTO>();
        if (contactFiles != null) {
            for (ZuPinContactFile file : contactFiles) {
                dtos.add(toZuPinContactFileDTO(file));
            }
        }
        return dtos;
    }


}
