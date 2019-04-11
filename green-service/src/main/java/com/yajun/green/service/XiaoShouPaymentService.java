package com.yajun.green.service;

import com.yajun.green.domain.pay.PayOrderFile;
import com.yajun.green.domain.pay.PayOrderSource;
import com.yajun.green.domain.pay.PayOrderWay;
import com.yajun.green.facade.dto.pay.PayOrderDTO;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

/**
 * User: Jack Wang
 * Date: 17-10-15
 * Time: 上午11:14
 */
public interface XiaoShouPaymentService {
    /*******************************************线下支付****************************************/

    //创建订单和完成支付同时进行
    String updateBeginXiaoShouContactPayOrderOffline(String payOrderNumber, String jiaoYiNumber, String chengYunUuid, String actorUuid, PayOrderSource orderSource, PayOrderWay orderWay, String orderNote,
                                                     BigDecimal totalFee, List<String> itemUuids, List<MultipartFile> orderFiles);

    /*******************************************线上支付****************************************/
    //创建订单和完成支付同时进行
    PayOrderDTO updateBeginXiaoShouContactPayOrderOnline(String payOrderNumber, String chengYunUuid, String actorUuid, PayOrderSource orderSource, PayOrderWay orderWay,
                                                         BigDecimal totalFee, List<String> itemUuids);
    //线上支付  订单支付完成
    BigDecimal updateFinishContactPayOrderOnline(String payOrderNumber, String jiaoYiNumber);

    /*****************************************附件上传*******************************************/
    void uploadPayOrderFujian(String orderNumber, PayOrderFile fuJian);

}
