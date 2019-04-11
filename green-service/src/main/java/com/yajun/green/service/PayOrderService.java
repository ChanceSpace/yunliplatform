package com.yajun.green.service;

import com.yajun.green.common.security.LoginInfo;
import com.yajun.green.domain.pay.PayOrderSource;
import com.yajun.green.domain.pay.PayOrderWay;
import com.yajun.green.facade.dto.contact.PayOrderManagementShowDTO;
import com.yajun.green.facade.dto.contact.ZuPinContactRentingFeeHistoryDTO;
import com.yajun.green.facade.dto.pay.PayOrderDTO;
import com.yajun.green.repository.PayOrderDao;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

/**
 * User: Jack Wang
 * Date: 17-10-15
 * Time: 上午11:14
 */
public interface PayOrderService {

    /*******************************************订单模块****************************************/
    //通过租赁承运商id查询其对应未完成账单
    PayOrderDTO obtainNotFinishPayOrderByChengYunUuid(String chengYunUuid);
    //通过购车承运商id查询其对应未完成账单
    PayOrderDTO obtainNotFinishXiaoShouOrderByChengYunUuid(String chengYunUuid);

    /*******************************************订单详情相关****************************************/

    //根据订单号查询订单详情
    PayOrderDTO obtainPayOrder(String payOrderNumber, boolean loadZuPinContactRentingFeeHistory, boolean loadCarrierRentingFeeHistory, boolean loadPayOrderFile,boolean loadXiaoShouRentingFeeHistory);

    //保存订单附件
    void savePayOrderDetailFile(String orderUuid, String payOrderNumber, List<MultipartFile> orderFiles);

    /*******************************************订单列表相关****************************************/

    //我的订单
    List<PayOrderDTO> obtainOverviewCarrierPayOrder(String keyWords, String userUuid, String userType, String startTime, String endTime,String selType, int startPosition, int pageSize);

    int obtainOverviewCarrierPayOrderSize(String keyWords, String userUuid, String userType, String startTime, String endTime, String selType);

    //根据条件查询所有订单
    List<PayOrderDTO> obtainAllPayOrder(String keyWords, String userUuid, String userType, String startTime, String endTime,String selType);
}
