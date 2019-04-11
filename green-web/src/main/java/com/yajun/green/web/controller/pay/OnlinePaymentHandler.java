package com.yajun.green.web.controller.pay;

import com.alibaba.fastjson.JSONObject;
import com.yajun.green.application.ApplicationEventPublisher;
import com.yajun.green.common.log.ApplicationLog;
import com.yajun.green.common.utils.WebUtils;
import com.yajun.green.facade.dto.pay.PayOrderDTO;
import org.apache.commons.httpclient.methods.PostMethod;

import java.math.RoundingMode;
import java.util.Date;

/**
 * Created by chance on 2017/10/19.
 */
public class OnlinePaymentHandler {

    public static final String appId = "sdamokdkbqvgpitbuitomagzqo";
    public static final String appKey = "kwhyixutodnsagjvclmzrfqpeb";

    public static String handleOnlinePaymentURL(PayOrderDTO payOrder, boolean zuLin) {
        try {
            String host = ApplicationEventPublisher.applicationPaymentURL + "index/pay";
            String redirect = ApplicationEventPublisher.applicationHost;
            if (zuLin) {
                redirect = redirect + "carrier/paymentwaiting.html";
            } else {
                redirect = redirect + "carrier/xiaoshoupaymentwaiting.html";
            }

            Long timestamp = new Date().getTime();
            String payType = payOrder.getPaymentPlatformType();

            PostMethod post = new PostMethod(host);
            post.setRequestHeader("Referer", ApplicationEventPublisher.applicationPaymentURL);
            post.addParameter("appid", appId);
            post.addParameter("appkey", appKey);
            post.addParameter("timestamp", Long.valueOf(timestamp / 10).intValue() + "");
            post.addParameter("pay_type", payType);
            post.addParameter("redirect", redirect);
            post.addParameter("order", payOrder.getOrderNumber());
            post.addParameter("desc", "绿色共享运力平台订单支付");

            if (ApplicationEventPublisher.applicationLive) {
                post.addParameter("price", payOrder.getTotalFee().setScale(2, RoundingMode.HALF_UP).toString());
            } else {
                post.setParameter("price", "0.01");
            }

            String response = WebUtils.httpPostRequest(post);
            JSONObject result = JSONObject.parseObject(response);
            ApplicationLog.info(OnlinePaymentHandler.class, "online order pay for " + payOrder.getOrderNumber() + " and result is " + response);

            //可以进行支付
            if ("0".equals(result.getString("code")) && "0".equals(result.getString("status"))) {
                return result.getString("payurl");
            }
            //支付已经完成，直接
            if ("1011".equals(result.getString("code")) && "1".equals(result.getString("status"))) {
                return redirect + "?payOrderNumber=" + payOrder.getOrderNumber();
            }
        } catch (Exception e) {
            ApplicationLog.error(OnlinePaymentHandler.class, "error connect to online payment", e);
        }

        return null;
    }

    /**
     * 主要，扣款主要有三个状态，完成，等待和取消，失败
     */
    public static OnlinePaymentResult handleOnlinePaymentChecking(String payOrderNumber) {
        OnlinePaymentResult back = new OnlinePaymentResult();

        try {
            String host = ApplicationEventPublisher.applicationPaymentURL + "index/pay/search";

            PostMethod post = new PostMethod(host);
            post.setRequestHeader("Referer", ApplicationEventPublisher.applicationPaymentURL);
            post.addParameter("appid", appId);
            post.addParameter("appkey", appKey);
            post.addParameter("orderid", payOrderNumber);
            String response = WebUtils.httpPostRequest(post);
            JSONObject result = JSONObject.parseObject(response);
            ApplicationLog.info(OnlinePaymentHandler.class, "online order search for " + payOrderNumber + " and result is " + response);

            if ("0".equals(result.getString("code")) && "0".equals(result.getString("status"))) {
                back.setResult(0);
                back.setJiaoYiNumber(result.getString("transactionId"));

            } else if ("1".equals(result.getString("code")) && "0".equals(result.getString("status"))) {
                back.setResult(1);

            } else {
                back.setResult(2);
            }
        } catch (Exception e) {
            ApplicationLog.error(OnlinePaymentHandler.class, "error connect to online payment", e);
        }

        return back;
    }

    public static void main(String[] args) {
        ApplicationEventPublisher.applicationHost = "http://localhost:8080/my/";
        ApplicationEventPublisher.applicationPaymentURL = "https://pay.acgxt.com/";

//        PayOrderDTO order = new PayOrderDTO();
//        order.setPayOrderWay("O_AILI");
//        order.setOrderNumber("LK124354545904389");
//        order.setTotalFee(BigDecimal.valueOf(0.01));
//
//        String url = OnlinePaymentHandler.handleOnlinePaymentURL(order);
//        System.out.println(url);

        OnlinePaymentHandler.handleOnlinePaymentChecking("1234321");
    }
}
