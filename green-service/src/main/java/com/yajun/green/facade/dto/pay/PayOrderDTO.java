package com.yajun.green.facade.dto.pay;

import com.yajun.green.domain.pay.PayOrderWay;
import com.yajun.green.facade.dto.contact.ZuPinContactRentingFeeHistoryDTO;
import com.yajun.green.facade.show.HighLightUtils;
import org.joda.time.DateTime;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chance on 2017/10/18.
 */
public class PayOrderDTO {

    private String uuid;
    //订单来源
    private String payOrderSource;
    private String payOrderSourceName;
    private BigDecimal totalFee;
    private BigDecimal totalFeeBelone;
    private BigDecimal totalFeeChengYun;

    //订单号相关
    private String orderNumber;
    private String orderNumberShow;
    private String jiaoYiNumber;

    //承运商相关
    private String belongName;
    private String belongUuid;
    private String chengYunName;
    private String chengYunUuid;

    //时间和状态相关
    private String createTime;
    private String finishTime;
    private String payOrderStatus;
    private String payOrderStatusName;
    private String payOrderWay;
    private String payOrderWayName;

    //操作相关
    private String actorName;
    private String actorUuid;
    private String orderNote;

    //款项
    private List<PayItem> payItems = new ArrayList<>();

    //订单附件
    private List<PayOrderFileDTO> payOrderFileDTOS;

    public PayOrderDTO() {
    }

    public PayOrderDTO(String uuid, String payOrderSource, String payOrderSourceName, BigDecimal totalFee, BigDecimal totalFeeBelone, BigDecimal totalFeeChengYun, String orderNumber, String jiaoYiNumber, String belongName, String beloneUuid, String chengYunName, String chengYunUuid,
                       String createTime, String finishTime, String payOrderStatus, String payOrderStatusName, String payOrderWay, String payOrderWayName, String actorName, String actorUuid, String orderNote) {
        this.uuid = uuid;
        this.payOrderSource = payOrderSource;
        this.payOrderSourceName = payOrderSourceName;
        this.totalFee = totalFee;
        this.orderNumber = orderNumber;
        this.orderNumberShow = orderNumber;
        this.jiaoYiNumber = jiaoYiNumber;
        this.belongName = belongName;
        this.belongUuid = beloneUuid;
        this.chengYunName = chengYunName;
        this.chengYunUuid = chengYunUuid;

        this.createTime = createTime;
        this.finishTime = finishTime;
        this.payOrderStatus = payOrderStatus;
        this.payOrderStatusName = payOrderStatusName;
        this.payOrderWay = payOrderWay;
        this.payOrderWayName = payOrderWayName;
        this.actorName = actorName;
        this.actorUuid = actorUuid;
        this.orderNote = orderNote;

        this.totalFeeBelone = totalFeeBelone;
        this.totalFeeChengYun = totalFeeChengYun;
    }

    public String getPaymentPlatformType() {
        if (PayOrderWay.O_AILI.name().equals(payOrderWay)) {
            return "alipay";
        } else if (PayOrderWay.O_WEBCHART.name().equals(payOrderWay)) {
            return "wxpay";
        } else if (PayOrderWay.O_YINLIAN.name().equals(payOrderWay)) {
            return "bankpay";
        }
        return "wrong";
    }

    public void addHighLight(String keywords){
        chengYunName = HighLightUtils.highLightWords(chengYunName, keywords);
        orderNumberShow = HighLightUtils.highLightWords(orderNumberShow, keywords);
    }

    public void addPayItems(List<PayItem> payItems) {
        this.payItems.addAll(payItems);
    }

    /************************************GETTER/SETTER********************************************/

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPayOrderSource() {
        return payOrderSource;
    }

    public void setPayOrderSource(String payOrderSource) {
        this.payOrderSource = payOrderSource;
    }

    public String getPayOrderSourceName() {
        return payOrderSourceName;
    }

    public void setPayOrderSourceName(String payOrderSourceName) {
        this.payOrderSourceName = payOrderSourceName;
    }

    public BigDecimal getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(BigDecimal totalFee) {
        this.totalFee = totalFee;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getJiaoYiNumber() {
        return jiaoYiNumber;
    }

    public void setJiaoYiNumber(String jiaoYiNumber) {
        this.jiaoYiNumber = jiaoYiNumber;
    }

    public String getBelongName() {
        return belongName;
    }

    public void setBelongName(String belongName) {
        this.belongName = belongName;
    }

    public String getBelongUuid() {
        return belongUuid;
    }

    public void setBelongUuid(String belongUuid) {
        this.belongUuid = belongUuid;
    }

    public String getChengYunName() {
        return chengYunName;
    }

    public void setChengYunName(String chengYunName) {
        this.chengYunName = chengYunName;
    }

    public String getChengYunUuid() {
        return chengYunUuid;
    }

    public void setChengYunUuid(String chengYunUuid) {
        this.chengYunUuid = chengYunUuid;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getPayOrderStatus() {
        return payOrderStatus;
    }

    public void setPayOrderStatus(String payOrderStatus) {
        this.payOrderStatus = payOrderStatus;
    }

    public String getPayOrderStatusName() {
        return payOrderStatusName;
    }

    public void setPayOrderStatusName(String payOrderStatusName) {
        this.payOrderStatusName = payOrderStatusName;
    }

    public String getPayOrderWay() {
        return payOrderWay;
    }

    public void setPayOrderWay(String payOrderWay) {
        this.payOrderWay = payOrderWay;
    }

    public String getPayOrderWayName() {
        return payOrderWayName;
    }

    public void setPayOrderWayName(String payOrderWayName) {
        this.payOrderWayName = payOrderWayName;
    }

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public String getActorUuid() {
        return actorUuid;
    }

    public void setActorUuid(String actorUuid) {
        this.actorUuid = actorUuid;
    }

    public String getOrderNote() {
        return orderNote;
    }

    public void setOrderNote(String orderNote) {
        this.orderNote = orderNote;
    }

    public List<PayItem> getPayItems() {
        return payItems;
    }

    public void setPayItems(List<PayItem> payItems) {
        this.payItems = payItems;
    }

    public List<PayOrderFileDTO> getPayOrderFileDTOS() {
        return payOrderFileDTOS;
    }

    public void setPayOrderFileDTOS(List<PayOrderFileDTO> payOrderFileDTOS) {
        this.payOrderFileDTOS = payOrderFileDTOS;
    }

    public String getOrderNumberShow() {
        return orderNumberShow;
    }

    public void setOrderNumberShow(String orderNumberShow) {
        this.orderNumberShow = orderNumberShow;
    }

    public BigDecimal getTotalFeeBelone() {
        return totalFeeBelone;
    }

    public void setTotalFeeBelone(BigDecimal totalFeeBelone) {
        this.totalFeeBelone = totalFeeBelone;
    }

    public BigDecimal getTotalFeeChengYun() {
        return totalFeeChengYun;
    }

    public void setTotalFeeChengYun(BigDecimal totalFeeChengYun) {
        this.totalFeeChengYun = totalFeeChengYun;
    }
}
