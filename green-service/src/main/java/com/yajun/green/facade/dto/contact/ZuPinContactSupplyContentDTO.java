package com.yajun.green.facade.dto.contact;

/**
 * Created by chance on 2017/8/29.
 */
public class ZuPinContactSupplyContentDTO {
    private String uuid;
    private String content;


    public ZuPinContactSupplyContentDTO() {
    }

    public ZuPinContactSupplyContentDTO(String uuid, String content) {
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
