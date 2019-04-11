package com.yajun.green.service;

import com.yajun.green.common.log.ApplicationLog;
import com.yajun.green.common.utils.JodaUtils;
import com.yajun.green.domain.contact.ZuPinContact;
import com.yajun.green.domain.contact.ZuPinContactRentingFeeHistory;
import com.yajun.green.facade.assember.contact.ZuPinContactWebAssember;
import com.yajun.green.facade.dto.contact.ZuPinContactDTO;
import com.yajun.green.repository.ZuPinContactDao;
import com.yajun.user.facade.dto.CompanyDTO;
import com.yajun.user.service.UserDubboService;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LiuMengKe on 2017/11/27.
 */
@Service("zuPinContactQianFeiService")
public class ZuPinContactQianFeiServiceImpl implements ZuPinContactQianFeiService {

    @Autowired
    private ZuPinContactDao zuPinContactDao;
    @Autowired
    private UserDubboService userDubboService;

    /******************************************用户欠费相关******************************************/

    @Override
    public List<ZuPinContactDTO> obtainAllNotFinishedZuPinContact() {
        return ZuPinContactWebAssember.toZuPinContactDTOList(zuPinContactDao.obtainAllNotFinishedZupincontact(), "");
    }

    @Override
    public ZuPinContactDTO updateQianFeiTime(String zuPinContactUuid) {
        ZuPinContact zuPinContact =(ZuPinContact) zuPinContactDao.findByUuid(zuPinContactUuid,ZuPinContact.class);
        List<ZuPinContactRentingFeeHistory> histories = zuPinContact.getUnPayBill();
        BigDecimal updateAmount = new BigDecimal(0);
        zuPinContact.setQianFei(false);
        zuPinContact.setQianFeiBeginTime(null);
        zuPinContact.setQianFeiBalance(updateAmount);
        //当前已经欠费
        if(histories.size()>0){
            for (ZuPinContactRentingFeeHistory history : histories) {
                try {
                    zuPinContact.setQianFei(true);
                    //合同下各个历史欠费金额求和
                    updateAmount = updateAmount.add(history.getFeeTotal());
                    //比较合同欠费时间和当前历史发送时间 取最小的作为合同欠费时间
                    zuPinContact.setMinQianFeiDayTemp(history.getHappendDate());
                    zuPinContact.setQianFeiBalance(updateAmount);
                    ApplicationLog.info(ZuPinContactServiceImpl.class,"合同重置后欠款开始时间"+zuPinContact.getQianFeiBeginTime());
                    ApplicationLog.info(ZuPinContactServiceImpl.class,"合同欠费金额增加"+history.getFeeTotal());
                }catch (Exception e){
                    ApplicationLog.error(ZuPinContactServiceImpl.class,"自动重置欠费时间异常"+history.getUuid()+"异常信息"+e.getMessage());
                }
                zuPinContactDao.saveOrUpdate(zuPinContact);
            }
            ZuPinContactDTO zuPinContactDTO =  ZuPinContactWebAssember.toZuPinContactDTO(zuPinContact,false,false,false,false,false,false);
            if(zuPinContact.getQianFeiBeginTime()!=null){
                zuPinContactDTO.setQianFeiBeginTime(zuPinContact.getQianFeiBeginTime().toString());
            }
            zuPinContactDTO.setQianFei(zuPinContact.isQianFei());
            zuPinContactDTO.setQianFeiBalance(zuPinContact.getQianFeiBalance());
            return zuPinContactDTO;
        }else {
            //当前未欠费
            ApplicationLog.info(ZuPinContactServiceImpl.class,"合同"+zuPinContact.getUuid()+"未扫描到欠费账单欠费时间归零");
            zuPinContact.setQianFei(false);
            zuPinContact.setQianFeiBeginTime(null);
            zuPinContact.setQianFeiBalance(new BigDecimal(0));
            zuPinContactDao.saveOrUpdate(zuPinContact);
        }

        return null;
    }

    @Override
    public void updateCompanyUnpay(List<ZuPinContactDTO> unpayContacts) {
        Map<String, CompanyDTO> companies = new HashMap<>();

        //开始计算公司欠费的时间
        for (ZuPinContactDTO contact : unpayContacts) {
            String yifangUuid = contact.getYiFangUuid();
            CompanyDTO exist = companies.get(yifangUuid);
            if (exist == null) {
                exist = new CompanyDTO();
                exist.setUuid(yifangUuid);
                exist.setName(contact.getYiFangName());
            }

            ApplicationLog.info(ZuPinContactServiceImpl.class, "修改前欠费开始时间" + exist.getQianFeiBeginTime());
            ApplicationLog.info(ZuPinContactServiceImpl.class, "修改前欠费总额" + exist.getQianFeiBalance());
            exist.setQianFeiBalance(exist.getQianFeiBalance().add(contact.getQianFeiBalance()));

            if (exist.getQianFeiBeginTime() == null) {
                exist.setQianFeiBeginTime(contact.getQianFeiBeginTime());
                ApplicationLog.info(CompanyDTO.class, "公司" + exist.getName() + "最小欠费日期为" + exist.getQianFeiBeginTime());
            } else {
                //-1 结束时间小于开始时间
                int compareResult = JodaUtils.compareDays(new LocalDate(exist.getQianFeiBeginTime()), new LocalDate(contact.getQianFeiBeginTime()));
                if (compareResult < 0) {
                    exist.setQianFeiBeginTime(contact.getQianFeiBeginTime());
                    ApplicationLog.info(CompanyDTO.class, "公司" + exist.getName() + "最小欠费日期为" + exist.getQianFeiBeginTime());
                }
            }

            ApplicationLog.info(ZuPinContactServiceImpl.class, "修改后公司欠费开始时间" + exist.getQianFeiBeginTime());
            ApplicationLog.info(ZuPinContactServiceImpl.class, "修改后欠费总额" + exist.getQianFeiBalance());

            companies.put(yifangUuid, exist);
        }

        //开始更新公司欠费
        Collection<CompanyDTO> values = companies.values();
        for (CompanyDTO loop : values) {
            userDubboService.saveTongBuCompanyQianFei(loop.getUuid(), loop.getQianFeiBalance(), loop.getQianFeiBeginTime().toString());
        }
    }

}
