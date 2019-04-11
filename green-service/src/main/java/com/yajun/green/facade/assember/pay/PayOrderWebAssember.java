package com.yajun.green.facade.assember.pay;

import com.yajun.green.common.utils.JodaUtils;
import com.yajun.green.domain.chengyun.CarrierRentFeeHistory;
import com.yajun.green.domain.contact.ZuPinContactRentingFeeHistory;
import com.yajun.green.domain.pay.*;
import com.yajun.green.domain.xiaoshou.XiaoShouContactRentingFeeHistory;
import com.yajun.green.facade.assember.chengyun.CarrierRentFeeHistoryWebAssember;
import com.yajun.green.facade.assember.contact.ZuPinContactRentingFeeHistoryWebAssember;
import com.yajun.green.facade.assember.xiaoshou.XiaoShouContactRentingFeeHistoryWebAssember;
import com.yajun.green.facade.dto.pay.PayItem;
import com.yajun.green.facade.dto.pay.PayOrderDTO;
import com.yajun.green.facade.dto.pay.PayOrderFileDTO;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chance on 2017/10/18.
 */
public class PayOrderWebAssember {

    public static PayOrderDTO toPayOrderDTO(PayOrder payOrder, boolean loadZuPinContactRentingFeeHistory, boolean loadCarrierFeeHistory, boolean loadPayOrderFile,boolean loadXiaoShouRentFeeHistory){
        if (payOrder == null) {
            return null;
        }

        final String uuid = payOrder.getUuid();
        PayOrderSource source = payOrder.getOrderSource();
        final String payOrderSource = source.name();
        final String payOrderSourceName = source.getDescription();
        final BigDecimal totalFee = payOrder.getTotalFee();
        final BigDecimal totalFeeBelone = payOrder.getTotalFeeBelone();
        final BigDecimal totalFeeChengYun = payOrder.getTotalFeeChengYun();

        final String orderNumber = payOrder.getOrderNumber();
        final String jiaoYiNumber = payOrder.getJiaoYiNumber();
        final String belongName = payOrder.getBeloneName();
        final String belongUuid = payOrder.getBeloneUuid();
        final String chengYunName = payOrder.getChengYunName();
        final String chengYunUuid = payOrder.getChengYunUuid();

        final String createTime = JodaUtils.toStringDMYHMS(payOrder.getCreateTime());
        final String finishTime = JodaUtils.toStringDMYHMS(payOrder.getFinishTime());
        PayOrderStatus status = payOrder.getOrderStatus();
        final String payOrderStatus = status.name();
        final String payOrderStatusName = status.getDescription();
        PayOrderWay way = payOrder.getPayWay();
        final String payOrderWay = way.name();
        final String payOrderWayName = way.getDescription();
        final String actorName = payOrder.getActorName();
        final String actorUuid = payOrder.getActorUuid();
        final String orderNote = payOrder.getOrderNote();

        PayOrderDTO dto = new PayOrderDTO(uuid, payOrderSource, payOrderSourceName, totalFee, totalFeeBelone, totalFeeChengYun, orderNumber, jiaoYiNumber, belongName, belongUuid, chengYunName, chengYunUuid,
                createTime, finishTime, payOrderStatus, payOrderStatusName, payOrderWay, payOrderWayName, actorName, actorUuid, orderNote);

        if (loadZuPinContactRentingFeeHistory) {
            List<ZuPinContactRentingFeeHistory> zuPinContactRentingFeeHistories = payOrder.getOrderItems();
            List<PayItem> items = ZuPinContactRentingFeeHistoryWebAssember.toPaymentRentFeeHistoryDTOList(zuPinContactRentingFeeHistories);
            dto.addPayItems(items);
        }

        if (loadCarrierFeeHistory) {
            List<CarrierRentFeeHistory> carrierRentFeeHistories = payOrder.getFeeItems();
            boolean chongZhi = totalFeeChengYun.doubleValue() >= 0;
            List<PayItem> items = CarrierRentFeeHistoryWebAssember.toPaymentCarrierRentFeeHistoryDTOList(carrierRentFeeHistories, chongZhi);
            dto.addPayItems(items);
        }

        if (loadPayOrderFile) {
            List<PayOrderFile> payOrderFiles = payOrder.getOrderFiles();
            List<PayOrderFileDTO> payOrderFileDTOS = PayOrderFileWebAssember.toPayOrderFileDTOList(payOrderFiles);
            dto.setPayOrderFileDTOS(payOrderFileDTOS);
        }

        if(loadXiaoShouRentFeeHistory){
            List<XiaoShouContactRentingFeeHistory> xiaoShouContactRentingFeeHistories = payOrder.getSaleItems();
            List<PayItem> items = XiaoShouContactRentingFeeHistoryWebAssember.toPaymentRentFeeHistoryDTOList(xiaoShouContactRentingFeeHistories);
            dto.addPayItems(items);
        }

        return dto;
    }

    public static List<PayOrderDTO> toPayOrderDTOList(List<PayOrder> payOrders,String keyWords){
        List<PayOrderDTO> payOrderDTOS = new ArrayList<>();
        if (payOrders != null ) {
            for (PayOrder payOrder : payOrders) {
                PayOrderDTO dto = toPayOrderDTO(payOrder, false, false, false, false);
                if (StringUtils.hasText(keyWords)) {
                    dto.addHighLight(keyWords);
                }
                payOrderDTOS.add(dto);
            }
        }
        return payOrderDTOS;
    }

}
