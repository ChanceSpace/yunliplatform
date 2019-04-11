package com.yajun.green.facade.assember.xiaoshou;

import com.yajun.green.common.utils.JodaUtils;
import com.yajun.green.domain.xiaoshou.XiaoShouContactFile;
import com.yajun.green.facade.dto.contact.xiaoshou.ZuPinXiaoShouContactFileDTO;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiuMengKe on 2017/8/9.
 */
public class XiaoShouContactFileWebAssember {

    public static ZuPinXiaoShouContactFileDTO toZuPinXiaoShouContactFileDTO(XiaoShouContactFile contactFile) {
        final String uuid = contactFile.getUuid();
        final String actorManName = contactFile.getActorManName();
        final String uploadFileName = contactFile.getUploadFileName();
        final String actualFileName = contactFile.getActualFileName();
        final String uploadTime = JodaUtils.todMYHmString(contactFile.getUploadTime());

        ZuPinXiaoShouContactFileDTO dto = new ZuPinXiaoShouContactFileDTO(uuid, actorManName, uploadFileName, actualFileName, uploadTime);

        return dto;
    }

    public static List<ZuPinXiaoShouContactFileDTO> toZuPinXiaoShouContactFileDTOList(List<XiaoShouContactFile> contactFiles) {
        List<ZuPinXiaoShouContactFileDTO> dtos = new ArrayList<ZuPinXiaoShouContactFileDTO>();
        if (contactFiles != null) {
            for (XiaoShouContactFile file : contactFiles) {
                dtos.add(toZuPinXiaoShouContactFileDTO(file));
            }
        }
        return dtos;
    }


}
