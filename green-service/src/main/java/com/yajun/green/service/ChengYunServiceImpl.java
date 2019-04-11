package com.yajun.green.service;

import com.yajun.green.common.log.ApplicationLog;
import com.yajun.green.common.security.LoginInfo;
import com.yajun.green.common.utils.CHListUtils;
import com.yajun.green.domain.chengyun.*;
import com.yajun.green.domain.pay.PayOrder;
import com.yajun.green.domain.pay.PayOrderFile;
import com.yajun.green.domain.pay.PayOrderSource;
import com.yajun.green.domain.pay.PayOrderWay;
import com.yajun.green.facade.assember.chengyun.CarrierRentFeeHistoryWebAssember;
import com.yajun.green.facade.dto.chengyun.CarrierRentFeeHistoryDTO;
import com.yajun.green.repository.PayOrderDao;
import com.yajun.green.security.SecurityUtils;
import com.yajun.user.facade.dto.CompanyDTO;
import com.yajun.user.service.UserDubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chance on 2017/9/12.
 */
@Service("chengYunService")
public class ChengYunServiceImpl implements ChengYunService {
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private PayOrderDao payOrderDao;
    @Autowired
    private UserDubboService userDubboService;

    /*******************************************承运商账户历史********************************/

    @Override
    public void updateCarrierPayOrderChargeHistory(PayOrder payOrder) {
        String description = payOrder.getChengYunName() + payOrder.getOrderSource().getDescription() + payOrder.getOrderNumber() + "付款";

        //一级承运商交易历史
        BigDecimal addYuEFee = payOrder.getTotalFee();
        userDubboService.saveTongBuCompanyBalance(payOrder.getOrderNumber(), payOrder.getBeloneUuid(), addYuEFee, description);

        //二级承运商交易历史,//线下支付流程，只有扣款历史,账户会扣钱
         if (payOrder.getPayWay().equals(PayOrderWay.O_OFFLINE)) {
            BigDecimal subYuEFee = payOrder.getTotalFee().negate();
            userDubboService.saveTongBuCompanyBalance(payOrder.getOrderNumber(), payOrder.getChengYunUuid(), subYuEFee, description);
         }
    }

    @Override
    public synchronized void updateCarrierBalance(String chengYunUuid, CarrierRentFeeHistoryDTO feeHistory, List<MultipartFile> files, Boolean isChongZhi) {
        CompanyDTO company = userDubboService.obtainTongBuCompanyInfoByUuid(chengYunUuid);
        LoginInfo loginInfo = SecurityUtils.currentLoginUser();

        //设置录入时间和生成订单
        CarrierRentFeeHistory feeItem = CarrierRentFeeHistoryWebAssember.toCarrierRentFeeHistoryDomain(feeHistory);
        PayOrder payOrder = null;
        if (isChongZhi) {
            payOrder = PayOrder.generatePayOrderFromChongZhi(company.getUuid(), company.getName(), loginInfo, PayOrderSource.O_CHONGZHI, PayOrderWay.O_CASH, feeHistory.getComment(), feeHistory.getFeeTotal(),feeHistory.getJiaoYiNumber(), feeItem, true);
            ApplicationLog.info(ChengYunServiceImpl.class, "余额充值操作人id" + loginInfo.getUserUuid() + "充值金额：" + feeHistory.getFeeTotal());

            //承运商余额计算
            String description = company.getName() + "余额充值";
            BigDecimal addYuEFee = feeHistory.getFeeTotal();
            userDubboService.saveTongBuCompanyBalance(payOrder.getOrderNumber(), chengYunUuid, addYuEFee, description);
        } else {
            payOrder = PayOrder.generatePayOrderFromChongZhi(company.getUuid(), company.getName(), loginInfo, PayOrderSource.O_TIXIAN, PayOrderWay.O_CASH, feeHistory.getComment(), feeHistory.getFeeTotal(),feeHistory.getJiaoYiNumber(), feeItem, false);
            ApplicationLog.info(ChengYunServiceImpl.class, "余额提现操作人id" + loginInfo.getUserUuid() + "提现金额：" + feeHistory.getFeeTotal());

            //承运商余额计算
            String description = company.getName() + "余额提现";
            BigDecimal addYuEFee = feeHistory.getFeeTotal().negate();
            userDubboService.saveTongBuCompanyBalance(payOrder.getOrderNumber(), chengYunUuid, addYuEFee, description);
        }

        //上传附件
        List<PayOrderFile> files1 = new ArrayList<>();
        if (CHListUtils.hasElement(files)) {
            for (MultipartFile file : files) {
                if ( file != null && file.getSize() >0) {
                    PayOrderFile fuJian = new PayOrderFile(file, loginInfo.getUserUuid(), loginInfo.getXingMing());
                    paymentService.uploadPayOrderFujian(payOrder.getOrderNumber(), fuJian);
                    files1.add(fuJian);
                }
            }
        }
        payOrder.setOrderFiles(files1);
        payOrderDao.saveOrUpdate(payOrder);
    }
}
