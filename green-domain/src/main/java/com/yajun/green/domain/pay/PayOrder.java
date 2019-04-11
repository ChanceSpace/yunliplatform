package com.yajun.green.domain.pay;

import com.yajun.green.common.domain.EntityBase;
import com.yajun.green.common.exception.PaymentConcurrentConflictException;
import com.yajun.green.common.security.LoginInfo;
import com.yajun.green.common.utils.CHDateUtils;
import com.yajun.green.common.utils.CHStringUtils;
import com.yajun.green.domain.chengyun.CarrierRentFeeHistory;
import com.yajun.green.domain.contact.ZuPinContactRentingFeeHistory;
import com.yajun.green.domain.xiaoshou.XiaoShouContactRentingFeeHistory;
import com.yajun.green.domain.xiaoshou.XiaoShouPayOrderStatus;
import org.joda.time.DateTime;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * User: Jack Wang
 * Date: 17-10-15
 * Time: 上午10:23
 */
public class PayOrder extends EntityBase {

    private final static String PAY_ORDER_SUFFIX = "YL";
    private final static String XSPAY_ORDER_SUFFIX = "XS";

    //订单来源
    private PayOrderSource orderSource;
    private BigDecimal totalFee;
    private BigDecimal totalFeeBelone;
    private BigDecimal totalFeeChengYun;

    //订单号相关
    private String orderNumber;
    private String jiaoYiNumber;

    //所属和承运商相关
    private String beloneName;
    private String beloneUuid;
    private String chengYunName;
    private String chengYunUuid;

    //时间和状态相关
    private DateTime createTime;
    private DateTime finishTime;
    private PayOrderStatus orderStatus;
    private PayOrderWay payWay;

    //操作相关
    private String actorName;
    private String actorUuid;
    private String orderNote;

    //合同款项
    private List<ZuPinContactRentingFeeHistory> orderItems;

    //销售合同款项
    private List<XiaoShouContactRentingFeeHistory> saleItems;

    //用户充值相关
    private List<CarrierRentFeeHistory> feeItems;

    //订单附件
    private List<PayOrderFile> orderFiles;

    public static PayOrder generatePayOrderFromPayment(String jiaoYiNumber, String companyUuid,  String companyName, boolean firstLevelCarry, String companyUuidAbove, String companyNameAbove, String actorUuid, String actorName,
                                                     PayOrderSource orderSource, PayOrderWay orderWay, String orderNote, BigDecimal totalFee, List<ZuPinContactRentingFeeHistory> items) {
        PayOrder order = new PayOrder();

        //2 + 12 + 2 + 4 + 4
        String timestamp = CHDateUtils.getFullDateFormatWithoutLine(new Date());
        String source = PayOrderSource.getPayOrderNumber(orderSource);
        String customer = companyUuid.substring(26);
        String random = CHStringUtils.getRandomString(4);
        String orderNumber = new StringBuffer().append(PAY_ORDER_SUFFIX).append(timestamp).append(source).append(customer).append(random).toString();
        order.setOrderNumber(orderNumber);

        order.setChengYunName(companyName);
        order.setChengYunUuid(companyUuid);
        if (firstLevelCarry) {
            order.setBeloneUuid(companyUuid);
            order.setBeloneName(companyName);
        } else {
            order.setBeloneUuid(companyUuidAbove);
            order.setBeloneName(companyNameAbove);
        }

        order.setActorName(actorName);
        order.setActorUuid(actorUuid);

        order.setOrderSource(orderSource);
        order.setPayWay(orderWay);
        order.setOrderStatus(PayOrderStatus.O_CREATE);
        order.setCreateTime(new DateTime());
        order.setFinishTime(new DateTime());
        order.setOrderNote(orderNote);

        //金额个交易相关的内容
        BigDecimal actualTotalFee = BigDecimal.valueOf(0);
        if (items != null) {
            for (ZuPinContactRentingFeeHistory item : items) {
                PayOrderStatus status = item.getStatus();
                //只有未付款的才计算进去
                if (status.equals(PayOrderStatus.O_CREATE) && !StringUtils.hasText(item.getPayOrderNumber())) {
                    BigDecimal total = item.getFeeTotal();
                    actualTotalFee = actualTotalFee.add(total);
                    item.setPayOrderNumber(orderNumber);
                } else {
                    throw new PaymentConcurrentConflictException("item not create status or item already in another pay order");
                }
            }
        }
        if (actualTotalFee.doubleValue() != totalFee.doubleValue()) {
            throw new PaymentConcurrentConflictException("send fee total is not same as actual fee total");
        }
        order.setTotalFeeBelone(totalFee);
        order.setTotalFeeChengYun(totalFee.multiply(BigDecimal.valueOf(-1)));
        order.setTotalFee(totalFee);
        order.setOrderItems(items);

        //设置订单号
        if (orderWay.equals(PayOrderWay.O_OFFLINE)) {
            order.setJiaoYiNumber(jiaoYiNumber);
        }

        return order;
    }

    //todo 销售合同构造函数
    public static PayOrder generatePayOrderFromXiaoShouPayment(String jiaoYiNumber, String companyUuid,  String companyName, boolean firstLevelCarry, String companyUuidAbove, String companyNameAbove, String actorUuid, String actorName,
                                                       PayOrderSource orderSource, PayOrderWay orderWay, String orderNote, BigDecimal totalFee, List<XiaoShouContactRentingFeeHistory> items) {
        PayOrder order = new PayOrder();

        //2 + 12 + 2 + 4 + 4
        String timestamp = CHDateUtils.getFullDateFormatWithoutLine(new Date());
        String source = PayOrderSource.getPayOrderNumber(orderSource);
        String customer = companyUuid.substring(26);
        String random = CHStringUtils.getRandomString(4);
        String orderNumber = new StringBuffer().append(XSPAY_ORDER_SUFFIX).append(timestamp).append(source).append(customer).append(random).toString();
        order.setOrderNumber(orderNumber);

        order.setChengYunName(companyName);
        order.setChengYunUuid(companyUuid);
        if (firstLevelCarry) {
            order.setBeloneUuid(companyUuid);
            order.setBeloneName(companyName);
        } else {
            order.setBeloneUuid(companyUuidAbove);
            order.setBeloneName(companyNameAbove);
        }

        order.setActorName(actorName);
        order.setActorUuid(actorUuid);

        order.setOrderSource(orderSource);
        order.setPayWay(orderWay);
        order.setOrderStatus(PayOrderStatus.O_CREATE);
        order.setCreateTime(new DateTime());
        order.setFinishTime(new DateTime());
        order.setOrderNote(orderNote);

        //金额个交易相关的内容
        BigDecimal actualTotalFee = BigDecimal.valueOf(0);
        if (items != null) {
            for (XiaoShouContactRentingFeeHistory item : items) {
                XiaoShouPayOrderStatus status = item.getPayOrderStatus();
                //只有未付款的才计算进去  线下支付histroy 中没有订单号 如果有需要在未完成订单里面现进行支付
                if (status.equals(XiaoShouPayOrderStatus.O_CREATE) && !StringUtils.hasText(item.getPayOrderNumber())) {
                    BigDecimal total = item.getFeeTotal();
                    actualTotalFee = actualTotalFee.add(total);
                    item.setPayOrderNumber(orderNumber);
                } else {
                    throw new PaymentConcurrentConflictException("item not create status or item already in another pay order");
                }
            }
        }
        if (actualTotalFee.doubleValue() != totalFee.doubleValue()) {
            throw new PaymentConcurrentConflictException("send fee total is not same as actual fee total");
        }
        order.setTotalFeeBelone(totalFee);
        order.setTotalFeeChengYun(totalFee.multiply(BigDecimal.valueOf(-1)));
        order.setTotalFee(totalFee);
        order.setSaleItems(items);

        //设置订单号
        if (orderWay.equals(PayOrderWay.O_OFFLINE)) {
            order.setJiaoYiNumber(jiaoYiNumber);
        }

        return order;
    }


    public static PayOrder generatePayOrderFromChongZhi(String companyUuid, String companyName, LoginInfo loginInfo,
                                                     PayOrderSource orderSource, PayOrderWay orderWay, String orderNote, BigDecimal totalFee,String jiaoYiNumber, CarrierRentFeeHistory item,
                                                     boolean chongZhi) {
        PayOrder order = new PayOrder();

        //2 + 12 + 2 + 4 + 4
        String timestamp = CHDateUtils.getFullDateFormatWithoutLine(new Date());
        String source = PayOrderSource.getPayOrderNumber(orderSource);
        String customer = companyUuid.substring(26);
        String random = CHStringUtils.getRandomString(4);
        String orderNumber = new StringBuffer().append(PAY_ORDER_SUFFIX).append(timestamp).append(source).append(customer).append(random).toString();
        order.setOrderNumber(orderNumber);

        order.setBeloneUuid(null);
        order.setBeloneName(null);
        order.setChengYunUuid(companyUuid);
        order.setChengYunName(companyName);
        order.setActorName(loginInfo.getXingMing());
        order.setActorUuid(loginInfo.getUserUuid());

        order.setOrderSource(orderSource);
        order.setPayWay(orderWay);
        order.setOrderStatus(PayOrderStatus.O_FINSIH);
        order.setCreateTime(new DateTime());
        order.setFinishTime(new DateTime());
        order.setOrderNote(orderNote);
        order.setJiaoYiNumber(jiaoYiNumber);

        if (chongZhi) {
            order.setTotalFeeBelone(BigDecimal.valueOf(0));
            order.setTotalFeeChengYun(totalFee);
            order.setTotalFee(totalFee);
        } else {
            order.setTotalFeeBelone(BigDecimal.valueOf(0));
            order.setTotalFeeChengYun(totalFee.negate());
            order.setTotalFee(totalFee);
        }

        List<CarrierRentFeeHistory> items = new ArrayList<>();
        items.add(item);
        order.setFeeItems(items);

        return order;
    }

    public void continuePay(String jiaoYiNumber, String actorName, String actorUuid, PayOrderWay orderWay, String orderNote) {
        this.setActorName(actorName);
        this.setActorUuid(actorUuid);

        this.setOrderNote(orderNote);
        this.setPayWay(orderWay);

        //设置订单号
        if (orderWay.equals(PayOrderWay.O_OFFLINE)) {
            this.setJiaoYiNumber(jiaoYiNumber);
        }
    }

    //租赁用
    public Set<String> finishPay(String jiaoYiNumber) {
        if (StringUtils.hasText(jiaoYiNumber)) {
            this.jiaoYiNumber = jiaoYiNumber;
        }

        this.orderStatus = PayOrderStatus.O_FINSIH;
        this.finishTime = new DateTime();

        Set<String> contactUuids = new HashSet<>();
        for (ZuPinContactRentingFeeHistory item : orderItems) {
            contactUuids.add(item.getContact().getUuid());
            item.setStatus(PayOrderStatus.O_FINSIH);
        }
        return contactUuids;
    }

    //销售用
    public List<XiaoShouContactRentingFeeHistory> finishXiaoShouHistory(String jiaoYiNumber) {
        if (StringUtils.hasText(jiaoYiNumber)) {
            this.jiaoYiNumber = jiaoYiNumber;
        }

        this.orderStatus = PayOrderStatus.O_FINSIH;
        this.finishTime = new DateTime();

        List<XiaoShouContactRentingFeeHistory> histories = new ArrayList<>();
        for (XiaoShouContactRentingFeeHistory item : saleItems) {
            histories.add(item);
            item.setPayOrderStatus(XiaoShouPayOrderStatus.O_FINSIH);
        }
        return histories;
    }

    public boolean alreadyPaid() {
        return orderStatus.equals(PayOrderStatus.O_FINSIH);
    }

    /***************************************GETTER/SETTER*********************************************/

    public PayOrderSource getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(PayOrderSource orderSource) {
        this.orderSource = orderSource;
    }

    public BigDecimal getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(BigDecimal totalFee) {
        this.totalFee = totalFee;
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

    public String getBeloneName() {
        return beloneName;
    }

    public void setBeloneName(String beloneName) {
        this.beloneName = beloneName;
    }

    public String getBeloneUuid() {
        return beloneUuid;
    }

    public void setBeloneUuid(String beloneUuid) {
        this.beloneUuid = beloneUuid;
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

    public DateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(DateTime createTime) {
        this.createTime = createTime;
    }

    public DateTime getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(DateTime finishTime) {
        this.finishTime = finishTime;
    }

    public PayOrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(PayOrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public PayOrderWay getPayWay() {
        return payWay;
    }

    public void setPayWay(PayOrderWay payWay) {
        this.payWay = payWay;
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

    public List<ZuPinContactRentingFeeHistory> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<ZuPinContactRentingFeeHistory> orderItems) {
        this.orderItems = orderItems;
    }

    public List<XiaoShouContactRentingFeeHistory> getSaleItems() {
        return saleItems;
    }

    public void setSaleItems(List<XiaoShouContactRentingFeeHistory> saleItems) {
        this.saleItems = saleItems;
    }

    public List<PayOrderFile> getOrderFiles() {
        return orderFiles;
    }

    public void setOrderFiles(List<PayOrderFile> orderFiles) {
        this.orderFiles = orderFiles;
    }

    public List<CarrierRentFeeHistory> getFeeItems() {
        return feeItems;
    }

    public void setFeeItems(List<CarrierRentFeeHistory> feeItems) {
        this.feeItems = feeItems;
    }
}
