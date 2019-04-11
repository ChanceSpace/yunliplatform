package com.yajun.green.service;

import com.yajun.green.application.ApplicationEventPublisher;
import com.yajun.green.common.exception.ApplicationException;
import com.yajun.green.common.exception.DocumentOperationException;
import com.yajun.green.common.exception.PaymentConcurrentConflictException;
import com.yajun.green.common.log.ApplicationLog;
import com.yajun.green.common.utils.CHListUtils;
import com.yajun.green.domain.contact.ZuPinContactRentingFeeHistory;
import com.yajun.green.domain.pay.*;
import com.yajun.green.facade.assember.pay.PayOrderWebAssember;
import com.yajun.green.facade.dto.pay.PayOrderDTO;
import com.yajun.green.repository.PayOrderDao;
import com.yajun.green.repository.ZuPinContactDao;
import com.yajun.user.facade.dto.CompanyDTO;
import com.yajun.user.facade.dto.UserAccountDTO;
import com.yajun.user.service.UserDubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * User: Jack Wang
 * Date: 17-10-15
 * Time: 上午11:14
 */
@Service("paymentService")
public class PaymentServiceImpl implements PaymentService {

    private final static String PAYORDER_DIR = "payorder";

    @Autowired
    private PayOrderDao payOrderDao;
    @Autowired
    private ZuPinContactDao zuPinContactDao;
    @Autowired
    private ChengYunService chengYunService;
    @Autowired
    private UserDubboService userDubboService;
    @Autowired
    ZuPinContactPayService zuPinContactPayService;

    /*******************************************账单产生相关****************************************/

    @Override
    public synchronized String updateBeginContactPayOrderOffline(String payOrderNumber, String jiaoYiNumber, String chengYunUuid, String actorUuid, PayOrderSource orderSource, PayOrderWay orderWay, String orderNote,
                                                                 BigDecimal totalFee, List<String> itemUuids, List<MultipartFile> orderFiles) {

        CompanyDTO company = userDubboService.obtainTongBuCompanyInfoByUuid(chengYunUuid);
        UserAccountDTO actor = userDubboService.obtainTongBuUserAccountInfo(actorUuid);
        String chengYunName = company.getName();
        String actorName = actor.getName();
        PayOrder payOrder = null;

        //生成订单
        if (StringUtils.hasText(payOrderNumber)) {
            payOrder = payOrderDao.findPayOrderByNumber(payOrderNumber);
            payOrder.continuePay(jiaoYiNumber, actor.getName(), actor.getUuid(), orderWay, orderNote);
        } else {
            //获取所有的数据项
            List<ZuPinContactRentingFeeHistory> items = zuPinContactDao.findContactRentingFeeHistory(itemUuids);
            //准备公用数据
            payOrder = PayOrder.generatePayOrderFromPayment(jiaoYiNumber, company.getUuid(), company.getName(), company.isFirstLevelCarry(), company.getParentUuid(), company.getParentName(), actor.getUuid(), actor.getName(),
                    orderSource, orderWay, orderNote, totalFee, items);
        }

        //上传附件
        List<PayOrderFile> files = new ArrayList<>();
        if (CHListUtils.hasElement(orderFiles)) {
            for (MultipartFile file : orderFiles) {
                if (file != null && file.getSize() > 0) {
                    PayOrderFile fuJian = new PayOrderFile(file, actorUuid, actorName);
                    uploadPayOrderFujian(payOrder.getOrderNumber(), fuJian);
                    files.add(fuJian);
                }
            }
        }
        payOrder.setOrderFiles(files);

        //保存订单
        payOrderDao.saveOrUpdate(payOrder);
        ApplicationLog.info(PayOrderServiceImpl.class, "user " + actorName + " create order for carrier " + chengYunName + " with order number " + payOrder.getOrderNumber());

        //如果是持续完成的话，直接完成
        updateContactPayOrderOffline(payOrder);
        return payOrder.getOrderNumber();
    }

    private void updateContactPayOrderOffline(PayOrder payOrder) {
        if (payOrder == null) {
            throw new ApplicationException("can't find order with order number " + payOrder);
        }
        if (payOrder.getOrderStatus().equals(PayOrderStatus.O_FINSIH)) {
            throw new PaymentConcurrentConflictException("can't update order with order number " + payOrder + " for reason, it already finished");
        }

        Set<String> contactUuids = payOrder.finishPay(null);
        zuPinContactPayService.updateZupinContactZuJinYaJinHasPay(contactUuids);

        ApplicationLog.info(PayOrderServiceImpl.class,  "finish order for carrier " + payOrder.getChengYunName() + " with order number " + payOrder.getOrderNumber());

        //产生账户历史
        chengYunService.updateCarrierPayOrderChargeHistory(payOrder);
    }

    /*******************************************线上支付****************************************/

    @Override
    public synchronized PayOrderDTO updateBeginContactPayOrderOnline(String payOrderNumber, String chengYunUuid, String actorUuid, PayOrderSource orderSource, PayOrderWay orderWay, BigDecimal totalFee, List<String> itemUuids) {
        //准备公用数据
        CompanyDTO company = userDubboService.obtainTongBuCompanyInfoByUuid(chengYunUuid);
        UserAccountDTO actor = userDubboService.obtainTongBuUserAccountInfo(actorUuid);
        String chengYunName = company.getName();
        String actorName = actor.getName();
        PayOrder payOrder = null;

        if (StringUtils.hasText(payOrderNumber)) {
            payOrder = payOrderDao.findPayOrderByNumber(payOrderNumber);
            payOrder.continuePay(null, actor.getName(), actor.getUuid(), orderWay, null);
        } else {
            //获取所有的数据项
            List<ZuPinContactRentingFeeHistory> items = zuPinContactDao.findContactRentingFeeHistory(itemUuids);
            //准备公用数据
            payOrder = PayOrder.generatePayOrderFromPayment(null, company.getUuid(), company.getName(), company.isFirstLevelCarry(), company.getParentUuid(), company.getParentName(), actor.getUuid(), actor.getName(),
                    orderSource, orderWay, null, totalFee, items);
        }

        //保存订单
        payOrderDao.saveOrUpdate(payOrder);
        ApplicationLog.info(PayOrderServiceImpl.class, "user " + actorName + " create order for carrier " + chengYunName + " with order number " + payOrder.getOrderNumber());

        return PayOrderWebAssember.toPayOrderDTO(payOrder, false, false, false,false);
    }

    @Override
    public synchronized BigDecimal updateFinishContactPayOrderOnline(String payOrderNumber, String jiaoYiNumber) {
        //订单部分的信息修改
        PayOrder payOrder = payOrderDao.findPayOrderByNumber(payOrderNumber);

        if (payOrder == null) {
            throw new ApplicationException("can't find order with order number " + payOrder);
        }
        if (payOrder.getOrderStatus().equals(PayOrderStatus.O_FINSIH)) {
            return payOrder.getTotalFee();
        }

        Set<String> contactUuids = payOrder.finishPay(jiaoYiNumber);
        zuPinContactPayService.updateZupinContactZuJinYaJinHasPay(contactUuids);

        ApplicationLog.info(PayOrderServiceImpl.class,  "finish order for carrier " + payOrder.getChengYunName() + " with order number " + payOrder.getOrderNumber());

        //产生账户历史
        chengYunService.updateCarrierPayOrderChargeHistory(payOrder);

        //账户部分的信息修改
        return payOrder.getTotalFee();
    }

    /****************************************************附件部分*****************************************************/
    @Override
    public void uploadPayOrderFujian(String orderNumber, PayOrderFile fuJian) {
        File directory = new File(ApplicationEventPublisher.applicationFileUploadPath + PAYORDER_DIR, orderNumber);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        File file = new File(directory, fuJian.getActualFileName());
        try {
            OutputStream dataOut = new FileOutputStream(file.getAbsolutePath());
            FileCopyUtils.copy(fuJian.getFile().getInputStream(), dataOut);

            ApplicationLog.info(PayOrderServiceImpl.class, "finish upload pay order fujian file");
        } catch (Exception e) {
            ApplicationLog.error(PayOrderServiceImpl.class, "finish upload poster file error for " + fuJian.getUploadFileName(), e);
            throw new DocumentOperationException("exception upload order file for " + fuJian.getUploadFileName(), e);
        }
    }

    /*****************************************取消订单******************************************/

    @Override
    public boolean cancelPayOrder(String payOrderNumber) {
        PayOrder payOrder = payOrderDao.findPayOrderByNumber(payOrderNumber);

        //如果是已支付完成，不能进行删除
        if (payOrder.alreadyPaid()) {
            return false;
        }

        //如果是未支付完成，可以进行删除
        List<ZuPinContactRentingFeeHistory> items = payOrder.getOrderItems();
        if (items != null) {
            for (ZuPinContactRentingFeeHistory item : items) {
                //取消订单相应的订单号也要设置为空
                item.setPayOrderNumber("");
            }
        }
        payOrderDao.delete(payOrder);
        return true;
    }
}
