package com.yajun.green.service;

import com.yajun.green.common.log.ApplicationLog;
import com.yajun.green.common.security.LoginInfo;
import com.yajun.green.common.utils.CHStringUtils;
import com.yajun.green.domain.xiaoshou.*;
import com.yajun.green.facade.assember.xiaoshou.XiaoShouContactRentingFeeHistoryWebAssember;
import com.yajun.green.facade.assember.xiaoshou.XiaoShouContactWebAssember;
import com.yajun.green.facade.assember.xiaoshou.XiaoShouVehicleModelWebAssember;
import com.yajun.green.facade.dto.contact.xiaoshou.ZuPinXiaoShouContactDTO;
import com.yajun.green.facade.dto.contact.xiaoshou.ZuPinXiaoShouContactRentingFeeHistoryDTO;
import com.yajun.green.facade.dto.contact.xiaoshou.ZuPinXiaoShouVehicleModuleDTO;
import com.yajun.green.repository.xiaoshou.XiaoShouContactDao;
import com.yajun.green.security.SecurityUtils;
import com.yajun.user.facade.dto.CompanyDTO;
import com.yajun.user.service.UserDubboService;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by LiuMengKe on 2017/12/21.
 */
@Service("xiaoShouContactService")
public class XiaoShouContactServiceImpl implements XiaoShouContactService {

    @Autowired
    private XiaoShouContactDao xiaoShouContactDao;
    @Autowired
    private UserDubboService userDubboService;

    /*******************************************合同浏览********************************/

    @Override
    public List<ZuPinXiaoShouContactDTO> obtainOverviewZuPinContact(String keyWords, String userUuid, String startTime, String endTime, String sortBy, String sortWay, String contactStatus, int startPosition, int pageSize) {
        List<XiaoShouContact> contacts = xiaoShouContactDao.findOverviewXiaoShouContact(keyWords, userUuid, startTime, endTime, sortBy, sortWay, contactStatus, startPosition, pageSize);
        return XiaoShouContactWebAssember.toXiaoShouContactDTOList(contacts, keyWords);
    }

    @Override
    public int obtainOverviewZuPinContactSize(String keyWords, String userUuid, String startTime, String endTime, String sortBy, String sortWay, String contactStatus) {
        return xiaoShouContactDao.findOverviewXiaoShouSize(keyWords, userUuid, startTime, endTime, sortBy, sortWay, contactStatus);
    }

    @Override
    public ZuPinXiaoShouContactDTO obtainZuPinXiaoShouContactByUuid(String zuPinContactUuid, boolean loadSelectVehicleModule, boolean loadExeVehicleModule, boolean loadRentFeeHistory, boolean loadContactFile, boolean loadContactLog, boolean loadContactSupply, boolean loadCharge) {
        XiaoShouContact xiaoShouContact = (XiaoShouContact)xiaoShouContactDao.findByUuid(zuPinContactUuid,XiaoShouContact.class);
        return XiaoShouContactWebAssember.toZuPinContactDTO(xiaoShouContact,loadSelectVehicleModule,loadExeVehicleModule,loadRentFeeHistory,loadContactFile,loadContactLog,loadContactSupply,loadCharge);
    }

    @Override
    public String saveOrUpdateZuPiXiaoShounContact(ZuPinXiaoShouContactDTO dto) {
        String typeNumber = XiaoShouType.decideWhichType(dto.getXiaoShouType());
        String timeNumber = new LocalDate().toString("yyyyMM");

        String suffixNumber = "";
        String orderNumber = "";
        String temp = "";

        boolean duplicated = true;

        //通过当前登录用户的公司简称构造合同编号
        String companyUuid = SecurityUtils.currentLoginUser().getCompanyUuid();
        CompanyDTO companyDTO = userDubboService.obtainTongBuCompanyInfoByUuid(companyUuid);

        while (duplicated) {
            suffixNumber = CHStringUtils.getRandomString(6);
            temp = timeNumber + suffixNumber;
            duplicated = xiaoShouContactDao.findXiaoShouOrderDuplicate(temp);
            orderNumber = companyDTO.getShortName() + typeNumber + timeNumber + suffixNumber;
            ApplicationLog.info(XiaoShouContactServiceImpl.class, "check order number " + orderNumber + " with duplicate " + duplicated);
        }

        XiaoShouContact contact = XiaoShouContactWebAssember.toZuPinXiaoShouContactDomain(dto, orderNumber);
        xiaoShouContactDao.saveOrUpdate(contact);

        return contact.getUuid();
    }

    @Override
    public String saveOrUpdateXiaoShouVehicleModule(ZuPinXiaoShouVehicleModuleDTO module, String zuPinContactUuid) {
        XiaoShouVehicleModule vehicleModule = XiaoShouVehicleModelWebAssember.toZuPinXiaoShouVehicleModuleDomain(module, zuPinContactUuid);
        xiaoShouContactDao.saveOrUpdate(vehicleModule);
        return vehicleModule.getUuid();
    }

    @Override
    public void deleteXiaoShouVehicleModule(String contactVehicleUuid) {
        XiaoShouVehicleModule vehicleModule = (XiaoShouVehicleModule) xiaoShouContactDao.findByUuid(contactVehicleUuid, XiaoShouVehicleModule.class);
        xiaoShouContactDao.delete(vehicleModule);
    }

    @Override
    public Map<String,XiaoShouContactVehicleStatusObject> obtainXiaoShouContactVehicleStatusList(String contactUuid) {
        Map<String,XiaoShouContactVehicleStatusObject> vehicleMap =  new TreeMap<>(new Comparator<String>(){
            @Override
            public int compare(String a, String b){
                return  a.compareTo(b);
            }
        });
        XiaoShouContact xiaoShouContact = (XiaoShouContact)xiaoShouContactDao.findByUuid(contactUuid,XiaoShouContact.class);
        List<XiaoShouVehicleModule> modules = xiaoShouContact.getOrigModules();
        List<XiaoShouContactRentingFeeHistory> histories = xiaoShouContact.getRentingFeeHistories();
        for (XiaoShouContactRentingFeeHistory history : histories) {
            String vehicleNumber = history.getVehicleNumber();
            int pici = history.getTichePiCi();
            //排除批次为0
            if(pici!=0){
                XiaoShouContactVehicleStatusObject vehicleStatusObject = vehicleMap.get(pici+vehicleNumber);
                if(vehicleStatusObject==null){
                    vehicleStatusObject = new XiaoShouContactVehicleStatusObject(modules.get(0));
                    vehicleStatusObject.setPici(history.getTichePiCi());
                    vehicleStatusObject.setTiCheDate(history.getActualFeeDate());
                    vehicleStatusObject.setVehicleNumber(vehicleNumber);
                    //由于现在销售合同的所有款项 都是在提车的时候自动生成 所以history 没有什么特别的备注 只显示execute里面的备注即可
                    XiaoShouVehicleExecute execute = xiaoShouContact.getSpecExecuteByVehicleNumber(vehicleNumber);
                    vehicleStatusObject.setComment(execute.getComment());
                    vehicleStatusObject.setExecuteUuid(execute.getUuid());
                }
                XiaoShouContactHistoryType type = history.getXiaoShouContactHistoryType();
                XiaoShouPayOrderStatus status = history.getPayOrderStatus();

                if(XiaoShouContactHistoryType.H_DJ.equals(type)){
                    vehicleStatusObject.setDjStatus(status);
                }
                if(XiaoShouContactHistoryType.H_SF.equals(type)){
                    vehicleStatusObject.setSfStatus(status);
                }
                if(XiaoShouContactHistoryType.H_WK.equals(type)){
                    vehicleStatusObject.setWkStatus(status);
                }
                vehicleMap.put(pici+vehicleNumber,vehicleStatusObject);
            }
        }

        return vehicleMap;
    }

    @Override
    public void changeXiaoShouContactCheck(String xiaoShouContactUuid, String changeStatus) {
        XiaoShouContact contact = (XiaoShouContact) xiaoShouContactDao.findByUuid(xiaoShouContactUuid, XiaoShouContact.class);

        LoginInfo loginInfo = SecurityUtils.currentLoginUser();
        XiaoShouContactLog log = contact.resetContactStatus(loginInfo, XiaoShouContactStatus.valueOf(changeStatus));
        xiaoShouContactDao.saveOrUpdate(log);
    }

    @Override
    public void deleteXiaoShouContact(String xiaoShouContactUuid) {
        XiaoShouContact contact = (XiaoShouContact) xiaoShouContactDao.findByUuid(xiaoShouContactUuid, XiaoShouContact.class);
        xiaoShouContactDao.delete(contact);
    }

    @Override
    public void saveOrUpdateXiaoShouHistory(ZuPinXiaoShouContactRentingFeeHistoryDTO historyDTO,String contactUuid) {
        XiaoShouContactRentingFeeHistory history = XiaoShouContactRentingFeeHistoryWebAssember.toRentFeeHistoryDomain(historyDTO);
        XiaoShouContact xiaoShouContact = (XiaoShouContact)xiaoShouContactDao.findByUuid(contactUuid,XiaoShouContact.class);
        history.setContactNumber(xiaoShouContact.getContactNumber());
        xiaoShouContactDao.saveOrUpdate(history);
    }
}
