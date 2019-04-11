package com.yajun.green.web.controller.pay;

import com.yajun.green.application.ApplicationEventPublisher;
import com.yajun.green.application.ApplicationEventPublisher;
import com.yajun.green.facade.dto.pay.PayOrderDTO;

import java.math.RoundingMode;
import java.util.Date;

/**
 * Created by chance on 2017/10/19.
 */
public class PaymentManagementHandler {

    private static final String appId = "ylpt";
    private static final String appKey = "ylpt123456";

    public static String handleOnlinePaymentURL(PayOrderDTO payOrder) {
        Long timestamp = new Date().getTime();
        String payType = payOrder.getPaymentPlatformType();
        String redirect = ApplicationEventPublisher.applicationHost + "/carrier/payorderwaiting.html";

        StringBuffer buffer = new StringBuffer();
        buffer.append(ApplicationEventPublisher.applicationPaymentURL + "index/pay?")
                .append("appid=" + appId)
                .append("&appkey=" + appKey)
                .append("&timestamp=" + timestamp)
                .append("&pay_type=" + payType)
                .append("&redirect=" + redirect)
                .append("&oid=" + payOrder.getOrderNumber())
                .append("&price=" + payOrder.getTotalFee().setScale(2, RoundingMode.HALF_UP).toString());

        return buffer.toString();
    }

}
