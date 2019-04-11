package com.yajun.green.facade.assember.pay;

import com.yajun.green.common.utils.JodaUtils;
import com.yajun.green.domain.pay.PayOrderFile;
import com.yajun.green.facade.dto.pay.PayOrderFileDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chance on 2017/10/18.
 */
public class PayOrderFileWebAssember {

    public static PayOrderFileDTO toPayOrderFileDTO(PayOrderFile payOrderFile){
        final String uuid = payOrderFile.getUuid();
        final String actorManName = payOrderFile.getActorManName();
        final String actorManUuid = payOrderFile.getActorManUuid();
        final String uploadFileName = payOrderFile.getUploadFileName();
        final String actualFileName = payOrderFile.getActualFileName();
        final String uploadTime = JodaUtils.todMYHmString(payOrderFile.getUploadTime());

        PayOrderFileDTO dto = new PayOrderFileDTO(uuid, actorManName, actorManUuid, uploadFileName, actualFileName, uploadTime);
        return dto;
    }

    public static List<PayOrderFileDTO> toPayOrderFileDTOList(List<PayOrderFile> payOrderFiles){
        List<PayOrderFileDTO> payOrderFileDTOS = new ArrayList<>();
        if (payOrderFiles !=null ) {
            for (PayOrderFile payOrderFile : payOrderFiles) {
                payOrderFileDTOS.add(toPayOrderFileDTO(payOrderFile));
            }
        }
        return payOrderFileDTOS;
    }
}
