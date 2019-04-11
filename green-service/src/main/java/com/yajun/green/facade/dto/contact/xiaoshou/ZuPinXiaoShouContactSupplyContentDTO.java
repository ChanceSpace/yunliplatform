package com.yajun.green.facade.dto.contact.xiaoshou;

/**
 * Created by LiuMengKe on 2017/12/20.
 */
public class ZuPinXiaoShouContactSupplyContentDTO {
    private String uuid;
    private String content;

    public ZuPinXiaoShouContactSupplyContentDTO() {
    }

    public ZuPinXiaoShouContactSupplyContentDTO(String uuid, String content) {
        this.uuid = uuid;
        this.content = content;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
