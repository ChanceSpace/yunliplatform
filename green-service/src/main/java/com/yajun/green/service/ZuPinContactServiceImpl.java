package com.yajun.green.service;

import com.yajun.green.common.log.ApplicationLog;
import com.yajun.green.common.security.LoginInfo;
import com.yajun.green.common.utils.CHStringUtils;
import com.yajun.green.domain.contact.*;
import com.yajun.green.facade.assember.contact.*;
import com.yajun.green.facade.dto.contact.*;
import com.yajun.green.repository.ZuPinContactDao;
import com.yajun.green.security.SecurityUtils;
import com.yajun.user.facade.dto.CompanyDTO;
import com.yajun.user.service.UserDubboService;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.*;

/**
 * Created by LiuMengKe on 2017/8/9.
 */
@Service("zuPinContactService")
public class ZuPinContactServiceImpl implements ZuPinContactService {

    @Autowired
    private UserDubboService userDubboService;
    @Autowired
    private ZuPinContactDao zuPinContactDao;

    /*******************************************合同浏览********************************/

    @Override
    public List<ZuPinContactDTO> obtainOverviewZuPinContact(String keyWords, String userUuid, String startTime, String endTime, String sortBy, String sortWay, String contactStatus, int startPosition, int pageSize) {
        List<ZuPinContact> contacts = zuPinContactDao.findOverviewZuPinContact(keyWords, userUuid, startTime, endTime, sortBy, sortWay, contactStatus, startPosition, pageSize);
        return ZuPinContactWebAssember.toZuPinContactDTOList(contacts, keyWords);
    }

    @Override
    public int obtainOverviewZuPinContactSize(String keyWords, String userUuid, String startTime, String endTime, String sortBy, String sortWay, String contactStatus) {
        return zuPinContactDao.findOverviewZuPinContactSize(keyWords, userUuid, startTime, endTime, sortBy, sortWay, contactStatus);
    }

    /*******************************************合同创建**********************************/

    @Override
    public ZuPinContactDTO obtainZuPinContactByUuid(String zuPinContactUuid, boolean loadSelectVehicleModule, boolean loadExeVehicleModule, boolean loadRentFeeHistory, boolean loadContactFile, boolean loadContactLog, boolean loadContactSupply) {
        ZuPinContact contact = (ZuPinContact) zuPinContactDao.findByUuid(zuPinContactUuid, ZuPinContact.class);
        return ZuPinContactWebAssember.toZuPinContactDTO(contact, loadSelectVehicleModule, loadExeVehicleModule, loadRentFeeHistory, loadContactFile, loadContactLog, loadContactSupply);
    }

    @Override
    public synchronized String saveOrUpdateZuPinContact(ZuPinContactDTO dto) {
        String orderNumber = "";
        if (!StringUtils.hasText(dto.getUuid())) {
            String typeNumber = ZuPinContactType.decideWhichType(dto.getContactType());
            String timeNumber = new LocalDate().toString("yyyyMM");

            String suffixNumber = "";
            String temp = "";
            boolean duplicated = true;
            while (duplicated) {
                suffixNumber = CHStringUtils.getRandomString(6);
                temp = timeNumber + suffixNumber;
                duplicated = zuPinContactDao.findZuPinOrderDuplicate(temp);

                String companyUuid = SecurityUtils.currentLoginUser().getCompanyUuid();
                CompanyDTO company = userDubboService.obtainTongBuCompanyInfoByUuid(companyUuid);
                orderNumber = company.getShortName() + typeNumber + timeNumber + suffixNumber;
                ApplicationLog.info(ZuPinContactServiceImpl.class, "check order number " + orderNumber + " with duplicate " + duplicated);
            }
        }

        ZuPinContact contact = ZuPinContactWebAssember.toZuPinContactDomain(dto, orderNumber);
        zuPinContactDao.saveOrUpdate(contact);

        LoginInfo loginInfo = SecurityUtils.currentLoginUser();
        ApplicationLog.infoWithCurrentUser(ZuPinContactServiceImpl.class, loginInfo.getUserUuid(), "change zu ping details");
        return contact.getUuid();
    }

    /*******************************************合同状态************************************/

    @Override
    public void changeZuPinContactCheck(String zuPinContactUuid, String changeStatus) {
        ZuPinContact contact = (ZuPinContact) zuPinContactDao.findByUuid(zuPinContactUuid, ZuPinContact.class);

        LoginInfo loginInfo = SecurityUtils.currentLoginUser();
        ZuPinContactLog log = contact.resetContactStatus(loginInfo, ZuPinContactStatus.valueOf(changeStatus));
        zuPinContactDao.saveOrUpdate(log);
    }

    @Override
    public void deleteZuPinContact(String zuPinContactUuid) {
        ZuPinContact contact = (ZuPinContact) zuPinContactDao.findByUuid(zuPinContactUuid, ZuPinContact.class);
        zuPinContactDao.delete(contact);
    }

    /*******************************************合同车辆部分************************************/

    @Override
    public void saveOrUpdateZuPinVehicleModule(ZuPinVehicleModuleDTO module, String zuPinContactUuid) {
        ZuPinVehicleModule vehicleModule = ZuPinVehicleModelWebAssember.toZuPinVehicleModuleDomain(module, zuPinContactUuid);
        zuPinContactDao.saveOrUpdate(vehicleModule);
    }

    @Override
    public void deleteZuPinContactVehicleModule(String contactVehicleUuid) {
        ZuPinVehicleModule vehicleModule = (ZuPinVehicleModule) zuPinContactDao.findByUuid(contactVehicleUuid, ZuPinVehicleModule.class);
        zuPinContactDao.delete(vehicleModule);
    }

    /*****************************************获取特定的executeDTO*******************************/

    @Override
    public ZuPinVehicleExecuteDTO obtainSpecZuPinContactVehicleExcuteDTO(String zuPinContactUuid, String tiChePiCi, String vehicleNum) {
        ZuPinVehicleExecute execute = zuPinContactDao.obtainSpecZuPinContactVehicleExcuteDTO(zuPinContactUuid, tiChePiCi, vehicleNum);
        return ZuPinVehicleExecuteWebAssember.toZuPinVehicleExecuteDTO(execute);
    }

    /*****************************************获取特定的historyDTO*******************************/

    @Override
    public ZuPinContactRentingFeeHistoryDTO obtainZuPinContactRentingFeeHistoryDTOByUuid(String historyUuid) {
        ZuPinContactRentingFeeHistory history = (ZuPinContactRentingFeeHistory) zuPinContactDao.findByUuid(historyUuid, ZuPinContactRentingFeeHistory.class);
        if (history != null) {
            return ZuPinContactRentingFeeHistoryWebAssember.toRentFeeHistoryDTO(history);
        } else {
            return null;
        }
    }
}
