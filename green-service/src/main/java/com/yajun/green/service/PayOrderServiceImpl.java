package com.yajun.green.service;

import com.yajun.green.application.ApplicationEventPublisher;
import com.yajun.green.common.exception.DocumentOperationException;
import com.yajun.green.common.log.ApplicationLog;
import com.yajun.green.common.security.LoginInfo;
import com.yajun.green.common.utils.CHListUtils;
import com.yajun.green.domain.pay.*;
import com.yajun.green.facade.assember.pay.PayOrderWebAssember;
import com.yajun.green.facade.dto.pay.PayOrderDTO;
import com.yajun.green.repository.PayOrderDao;
import com.yajun.green.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * User: Jack Wang
 * Date: 17-10-15
 * Time: 上午11:14
 */
@Service("payOrderService")
public class PayOrderServiceImpl implements PayOrderService {

    private final static String PAYORDER_DIR = "payorder";

    @Autowired
    private PayOrderDao payOrderDao;

    /****************************************************附件部分*****************************************************/

    private void uploadPayOrderFujian(String orderNumber, PayOrderFile fuJian) {
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

    /*******************************************订单模块****************************************/

    @Override
    public PayOrderDTO obtainNotFinishPayOrderByChengYunUuid(String chengYunUuid) {
        if (StringUtils.hasText(chengYunUuid)) {
            PayOrder payOrder = payOrderDao.findNotFinishPayOrderByChengYunUuid(chengYunUuid);
            return PayOrderWebAssember.toPayOrderDTO(payOrder, true, false, false,false);
        }
        return null;
    }
    @Override
    public PayOrderDTO obtainNotFinishXiaoShouOrderByChengYunUuid(String chengYunUuid) {
        if (StringUtils.hasText(chengYunUuid)) {
            PayOrder payOrder = payOrderDao.findNotFinishXiaoShouPayOrderByChengYunUuid(chengYunUuid);
            return PayOrderWebAssember.toPayOrderDTO(payOrder, false, false, false, true);
        }
        return null;
    }
    /*******************************************订单详情相关****************************************/

    @Override
    public PayOrderDTO obtainPayOrder(String payOrderNumber, boolean loadZuPinContactRentingFeeHistory, boolean loadCarrierRentingFeeHistory, boolean loadPayOrderFile,boolean loadXiaoShouRentingFeeHistory) {
        //根据订单号查询支付订单信息
        PayOrder payOrder = payOrderDao.findPayOrderByNumber(payOrderNumber);
        return PayOrderWebAssember.toPayOrderDTO(payOrder, loadZuPinContactRentingFeeHistory, loadCarrierRentingFeeHistory, loadPayOrderFile, loadXiaoShouRentingFeeHistory);
    }

    @Override
    public void savePayOrderDetailFile(String orderUuid, String payOrderNumber, List<MultipartFile> orderFiles) {
        LoginInfo loginInfo = SecurityUtils.currentLoginUser();
        PayOrder payOrder = (PayOrder) payOrderDao.findByUuid(orderUuid, PayOrder.class);

        //上传附件
        if (CHListUtils.hasElement(orderFiles)) {
            for (MultipartFile file : orderFiles) {
                if (file != null && file.getSize() > 0) {
                    PayOrderFile fuJian = new PayOrderFile(file, loginInfo.getUserUuid(), loginInfo.getXingMing());
                    uploadPayOrderFujian(payOrderNumber, fuJian);
                    fuJian.setPayOrder(payOrder);
                    payOrderDao.saveOrUpdate(fuJian);
                }
            }
        }

    }

    @Override
    public List<PayOrderDTO> obtainOverviewCarrierPayOrder(String keyWords, String userUuid, String userType, String startTime, String endTime,String selType, int startPosition, int pageSize) {
        List<PayOrder> payOrders = payOrderDao.findOverviewCarrierPayOrder(keyWords,userUuid, userType, startTime, endTime,selType ,startPosition, pageSize);
        return PayOrderWebAssember.toPayOrderDTOList(payOrders,keyWords);
    }

    @Override
    public int obtainOverviewCarrierPayOrderSize(String keyWords, String userUuid, String userType, String startTime, String endTime,String selType) {
        return payOrderDao.findOverviewCarrierPayOrderSize(keyWords,userUuid,userType,startTime,endTime,selType);
    }

    @Override
    public List<PayOrderDTO> obtainAllPayOrder(String keyWords, String userUuid, String userType, String startTime, String endTime, String selType) {
        List<PayOrder> payOrders = payOrderDao.findAllPayOrder(keyWords,userUuid, userType, startTime, endTime,selType );
        return PayOrderWebAssember.toPayOrderDTOList(payOrders,keyWords);
    }

}
