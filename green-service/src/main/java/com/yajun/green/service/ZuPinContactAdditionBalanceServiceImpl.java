package com.yajun.green.service;


import com.yajun.green.common.log.ApplicationLog;
import com.yajun.green.common.security.LoginInfo;
import com.yajun.green.domain.contact.ZuPinContact;
import com.yajun.green.domain.contact.ZuPinContactRentingFeeHistory;
import com.yajun.green.domain.contact.ZuPinContactRepayLocation;
import com.yajun.green.domain.contact.ZuPinVehicleExecute;
import com.yajun.green.domain.pay.PayOrderStatus;
import com.yajun.green.facade.dto.contact.ZuPinContactOverFormDTO;
import com.yajun.green.repository.ZuPinContactDao;
import com.yajun.green.security.SecurityUtils;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Created by LiuMengKe on 2017/12/11.
 */
@Service("zuPinContactAdditionBalanceService")
public class ZuPinContactAdditionBalanceServiceImpl implements ZuPinContactAdditionBalanceService {
    @Autowired
    ZuPinContactDao zuPinContactDao;
    @Override
    public void updateVehicleAdditionBalance(ZuPinContactOverFormDTO zuPinContactOverFormDTO,String zuPinContactUuid) {
        String[] overformcount = zuPinContactOverFormDTO.getOverformcount();
        String[] comment = zuPinContactOverFormDTO.getComment();
        String[] finishCharge = zuPinContactOverFormDTO.getFinishCharge();
        ZuPinContact contact = (ZuPinContact) zuPinContactDao.findByUuid(zuPinContactUuid, ZuPinContact.class);
        LoginInfo loginInfo = SecurityUtils.currentLoginUser();

        ZuPinVehicleExecute execute = (ZuPinVehicleExecute)zuPinContactDao.findByUuid(zuPinContactOverFormDTO.getExecuteUuid(),ZuPinVehicleExecute.class);
        if(execute == null ){
            throw  new RuntimeException("附加款项找不到对应的车辆");
        }

        //其他费用
        if (finishCharge != null && finishCharge.length > 0) {
            for (int i = 0; i < finishCharge.length; i++) {
                boolean tianjia = true;
                if ("SHOU_QU".equals(finishCharge[i])) {
                    tianjia = false;
                }
                ZuPinContactRentingFeeHistory zuPinContactRentingFeeHistory = new ZuPinContactRentingFeeHistory(contact.getContactNumber(), execute.getVehicleNum(), new LocalDate(), new BigDecimal(overformcount[i]), contact, execute.getTiChePiCi(), loginInfo.getXingMing(), loginInfo.getUserUuid(), tianjia, comment[i]);

                if (!tianjia) {
                    ApplicationLog.info(ZuPinContactServiceImpl.class, "合同 id:"+zuPinContactUuid+"单车附加款项收取" + overformcount[i]);
                    zuPinContactRentingFeeHistory.setZuPinContactRepayLocation(ZuPinContactRepayLocation.L_FJ_IN);
                    zuPinContactRentingFeeHistory.setStatus(PayOrderStatus.O_CREATE);
                } else {
                    ApplicationLog.info(ZuPinContactServiceImpl.class, "合同 id:"+zuPinContactUuid+"单车附加款项支出" + overformcount[i]);
                    zuPinContactRentingFeeHistory.setZuPinContactRepayLocation(ZuPinContactRepayLocation.L_FJ_OUT);
                    //押金等退换 线下操作已经完成 直接设置为已经支付
                    zuPinContactRentingFeeHistory.setStatus(PayOrderStatus.O_CREATE);
                    zuPinContactRentingFeeHistory.setFeeTotal(new BigDecimal(overformcount[i]).multiply(new BigDecimal(-1)));
                }

                zuPinContactRentingFeeHistory.setActualFeeDate(new LocalDate());
                zuPinContactDao.saveOrUpdate(zuPinContactRentingFeeHistory);
            }
        }
    }
}
