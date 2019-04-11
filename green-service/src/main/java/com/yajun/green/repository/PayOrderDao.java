package com.yajun.green.repository;

import com.yajun.green.domain.pay.PayOrder;

import java.util.List;

/**
 * User: Jack Wang
 * Date: 17-10-15
 * Time: 下午1:30
 */
public interface PayOrderDao extends EntityObjectDao {

    PayOrder findNotFinishPayOrderByChengYunUuid(String chengYunUuid);

    PayOrder findPayOrderByNumber(String payOrderNumber);

    /*******************************************订单列表相关****************************************/

    //我的订单
    List<PayOrder> findOverviewCarrierPayOrder(String keyWords, String userUuid, String userType, String startTime, String endTime,String selType, int startPosition, int pageSize);

    int findOverviewCarrierPayOrderSize(String keyWords, String userUuid, String userType, String startTime, String endTime,String selType);
    //根据条件查询所有订单
    List<PayOrder> findAllPayOrder(String keyWords, String userUuid, String userType, String startTime, String endTime,String selType);

    //获取未完成销售payorder
    PayOrder findNotFinishXiaoShouPayOrderByChengYunUuid(String chengYunUuid);
}
