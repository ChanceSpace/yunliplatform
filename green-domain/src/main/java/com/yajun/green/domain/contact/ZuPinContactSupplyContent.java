package com.yajun.green.domain.contact;


import com.yajun.green.common.domain.SubEntityBase;

/**
 * Created by chance on 2017/8/29.
 */
public class ZuPinContactSupplyContent extends SubEntityBase implements Comparable<ZuPinContactSupplyContent>{

    private String content;
    private ZuPinContactSupply zuPinContactSupply;

    public ZuPinContactSupplyContent() {
    }

    public ZuPinContactSupplyContent(String content, ZuPinContactSupply zuPinContactSupply) {
        this.content = content;
        this.zuPinContactSupply = zuPinContactSupply;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ZuPinContactSupply getZuPinContactSupply() {
        return zuPinContactSupply;
    }

    public void setZuPinContactSupply(ZuPinContactSupply zuPinContactSupply) {
        this.zuPinContactSupply = zuPinContactSupply;
    }

    @Override
    public int compareTo(ZuPinContactSupplyContent o) {
        return this.getUuid().compareTo(o.getUuid());
    }
}
