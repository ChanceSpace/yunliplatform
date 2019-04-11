package com.yajun.green.domain.xiaoshou;


import com.yajun.green.common.domain.SubEntityBase;

/**
 * Created by chance on 2017/8/29.
 */
public class XiaoShouContactSupplyContent extends SubEntityBase implements Comparable<XiaoShouContactSupplyContent>{

    private String content;
    private XiaoShouContactSupply xiaoShouContactSupply;

    public XiaoShouContactSupplyContent() {
    }

    public XiaoShouContactSupplyContent(String content, XiaoShouContactSupply xiaoShouContactSupply) {
        this.content = content;
        this.xiaoShouContactSupply = xiaoShouContactSupply;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public XiaoShouContactSupply getXiaoShouContactSupply() {
        return xiaoShouContactSupply;
    }

    public void setXiaoShouContactSupply(XiaoShouContactSupply xiaoShouContactSupply) {
        this.xiaoShouContactSupply = xiaoShouContactSupply;
    }

    @Override
    public int compareTo(XiaoShouContactSupplyContent o) {
        return this.getUuid().compareTo(o.getUuid());
    }
}
