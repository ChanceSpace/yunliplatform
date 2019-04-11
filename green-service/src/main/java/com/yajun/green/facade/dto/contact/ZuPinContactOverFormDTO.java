package com.yajun.green.facade.dto.contact;

import org.joda.time.LocalDate;
import org.springframework.web.bind.ServletRequestUtils;

import java.util.List;

/**
 * Created by LiuMengKe on 2017/9/25.
 * 合同结束 页面封装类
 */
public class ZuPinContactOverFormDTO {

    private String method;
    private String zuPinContactUuid;

    private List<ZuPinContactOverFormTableDTO> zuPinContactOverFormTableDTOList;

    private String[] overformcount;
    private String[] finishCharge;
    private String[] comment;

    private String finishDate;
    private String finishNote ;

    private String executeUuid;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getZuPinContactUuid() {
        return zuPinContactUuid;
    }

    public void setZuPinContactUuid(String zuPinContactUuid) {
        this.zuPinContactUuid = zuPinContactUuid;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public String getFinishNote() {
        return finishNote;
    }

    public void setFinishNote(String finishNote) {
        this.finishNote = finishNote;
    }


    public String[] getOverformcount() {
        return overformcount;
    }

    public void setOverformcount(String[] overformcount) {
        this.overformcount = overformcount;
    }

    public String[] getFinishCharge() {
        return finishCharge;
    }

    public void setFinishCharge(String[] finishCharge) {
        this.finishCharge = finishCharge;
    }

    public String[] getComment() {
        return comment;
    }

    public void setComment(String[] comment) {

        this.comment = comment;
    }

    public String getExecuteUuid() {
        return executeUuid;
    }

    public void setExecuteUuid(String executeUuid) {
        this.executeUuid = executeUuid;
    }
}
