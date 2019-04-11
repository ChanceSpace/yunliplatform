package com.yajun.green.domain.xiaoshou;

import com.yajun.green.common.domain.SubEntityBase;
import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by chance on 2017/8/29.
 */
public class XiaoShouContactSupply extends SubEntityBase implements Comparable<XiaoShouContactSupply>{
    private String caoZuoRen;
    private String caoZuoRenUuid;
    private DateTime timestamp;

    private XiaoShouContact xiaoShouContact;
    private List<XiaoShouContactSupplyContent> xiaoShouContactSupplyContents;

    public XiaoShouContactSupply() {
    }

    public XiaoShouContactSupply(String caoZuoRen, String caoZuoRenUuid, DateTime timestamp, XiaoShouContact xiaoShouContact, List<XiaoShouContactSupplyContent> xiaoShouContactSupplyContents) {
        this.caoZuoRen = caoZuoRen;
        this.caoZuoRenUuid = caoZuoRenUuid;
        this.timestamp = timestamp;
        this.xiaoShouContact = xiaoShouContact;
        this.xiaoShouContactSupplyContents = xiaoShouContactSupplyContents;
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

    public XiaoShouContact getXiaoShouContact() {
        return xiaoShouContact;
    }

    public void setXiaoShouContact(XiaoShouContact xiaoShouContact) {
        this.xiaoShouContact = xiaoShouContact;
    }

    public List<XiaoShouContactSupplyContent> getXiaoShouContactSupplyContents() {
        return xiaoShouContactSupplyContents;
    }

    public void setXiaoShouContactSupplyContents(List<XiaoShouContactSupplyContent> xiaoShouContactSupplyContents) {
        this.xiaoShouContactSupplyContents = xiaoShouContactSupplyContents;
    }

    @Override
    public int compareTo(XiaoShouContactSupply o) {
        return this.getUuid().compareTo(o.getUuid());
    }
}
