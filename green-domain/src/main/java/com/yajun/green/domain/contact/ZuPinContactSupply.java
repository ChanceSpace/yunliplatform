package com.yajun.green.domain.contact;

import com.yajun.green.common.domain.EntityBase;
import com.yajun.green.common.domain.SubEntityBase;
import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by chance on 2017/8/29.
 */
public class ZuPinContactSupply extends SubEntityBase implements Comparable<ZuPinContactSupply>{
    private String caoZuoRen;
    private String caoZuoRenUuid;
    private DateTime timestamp;

    private ZuPinContact zuPinContact;
    private List<ZuPinContactSupplyContent> zuPinContactSupplyContents;

    public ZuPinContactSupply() {
    }

    public ZuPinContactSupply(String caoZuoRen, String caoZuoRenUuid, DateTime timestamp, ZuPinContact zuPinContact, List<ZuPinContactSupplyContent> zuPinContactSupplyContents) {
        this.caoZuoRen = caoZuoRen;
        this.caoZuoRenUuid = caoZuoRenUuid;
        this.timestamp = timestamp;
        this.zuPinContact = zuPinContact;
        this.zuPinContactSupplyContents = zuPinContactSupplyContents;
    }

    /*****************************************GET/SET*******************************************/
    public String getCaoZuoRen() {
        return caoZuoRen;
    }

    public void setCaoZuoRen(String caoZuoRen) {
        this.caoZuoRen = caoZuoRen;
    }

    public String getCaoZuoRenUuid() {
        return caoZuoRenUuid;
    }

    public void setCaoZuoRenUuid(String caoZuoRenUuid) {
        this.caoZuoRenUuid = caoZuoRenUuid;
    }

    public DateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(DateTime timestamp) {
        this.timestamp = timestamp;
    }

    public ZuPinContact getZuPinContact() {
        return zuPinContact;
    }

    public void setZuPinContact(ZuPinContact zuPinContact) {
        this.zuPinContact = zuPinContact;
    }

    public List<ZuPinContactSupplyContent> getZuPinContactSupplyContents() {
        return zuPinContactSupplyContents;
    }

    public void setZuPinContactSupplyContents(List<ZuPinContactSupplyContent> zuPinContactSupplyContents) {
        this.zuPinContactSupplyContents = zuPinContactSupplyContents;
    }

    @Override
    public int compareTo(ZuPinContactSupply o) {
        return this.getUuid().compareTo(o.getUuid());
    }
}
