package com.yajun.green.service;

import com.yajun.green.application.ApplicationEventPublisher;
import com.yajun.green.common.exception.DocumentOperationException;
import com.yajun.green.common.log.ApplicationLog;
import com.yajun.green.common.security.LoginInfo;
import com.yajun.green.domain.xiaoshou.*;

import com.yajun.green.facade.assember.xiaoshou.XiaoShouContactSupplyContentWebAssember;

import com.yajun.green.facade.assember.xiaoshou.XiaoShouContactSupplyWebAssember;
import com.yajun.green.facade.assember.xiaoshou.ZuPinXiaoShouContactChargingWebAssember;
import com.yajun.green.facade.dto.contact.xiaoshou.ZuPinXiaoShouContactChargingDTO;
import com.yajun.green.facade.dto.contact.xiaoshou.ZuPinXiaoShouContactDTO;
import com.yajun.green.facade.dto.contact.xiaoshou.ZuPinXiaoShouContactSupplyContentDTO;
import com.yajun.green.facade.dto.contact.xiaoshou.ZuPinXiaoShouContactSupplyDTO;

import com.yajun.green.repository.xiaoshou.XiaoShouContactDao;
import com.yajun.green.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Created by LiuMengKe on 2017/11/27.
 */
@Service("xiaoShouContactAdditionalInfoService")
public class XiaoShouContactAdditionalInfoServiceImpl implements  XiaoShouContactAdditionalInfoService {

    @Autowired
    private XiaoShouContactDao xiaoShouContactDao;

    /*******************************************合同补充合同部分************************************/

    @Override
    public ZuPinXiaoShouContactSupplyDTO obtainZuPinXiaoShouContactSupplyByUuid(String contactSupplyUuid) {
        XiaoShouContactSupply zuPinContactSupply = (XiaoShouContactSupply) xiaoShouContactDao.findByUuid(contactSupplyUuid, XiaoShouContactSupply.class);
        return XiaoShouContactSupplyWebAssember.toZuPinXiaoShouContactSupplyDTO(zuPinContactSupply);
    }

    @Override
    public String saveOrUpdateZuPinXiaoShouContactSupply(ZuPinXiaoShouContactSupplyDTO zuPinContactSupplyDTO, String zuPinContactUuid) {
        XiaoShouContact zuPinContact = new XiaoShouContact();
        zuPinContact.setUuid(zuPinContactUuid);
        XiaoShouContactSupply zuPinContactSupply = XiaoShouContactSupplyWebAssember.toDomain(zuPinContactSupplyDTO, zuPinContactUuid);
        xiaoShouContactDao.saveOrUpdate(zuPinContactSupply);
        return zuPinContactSupply.getUuid();
    }

    @Override
    public void deleteZuPinXiaoShouContactSupply(String contactSupplyUuid) {
        XiaoShouContactSupply zuPinContactSupply = (XiaoShouContactSupply) xiaoShouContactDao.findByUuid(contactSupplyUuid, XiaoShouContactSupply.class);
        xiaoShouContactDao.delete(zuPinContactSupply);
    }

    /*******************************************合同补充合同内容部分************************************/

    @Override
    public void saveOrUpdateZuPinXiaoShouContactSupplyContent(ZuPinXiaoShouContactSupplyContentDTO zuPinContactSupplyContentDTO, String xiaoShouContactSupplyUuid) {
        XiaoShouContactSupplyContent zuPinContactSupplyContent = XiaoShouContactSupplyContentWebAssember.toDomain(zuPinContactSupplyContentDTO, xiaoShouContactSupplyUuid);
        xiaoShouContactDao.saveOrUpdate(zuPinContactSupplyContent);
    }

    @Override
    public void deleteZuPinXiaoShouContactSupplyContent(String contactSupplyContentUuid) {
        XiaoShouContactSupplyContent zuPinContactSupplyContent = (XiaoShouContactSupplyContent) xiaoShouContactDao.findByUuid(contactSupplyContentUuid, XiaoShouContactSupplyContent.class);
        xiaoShouContactDao.delete(zuPinContactSupplyContent);
    }

    /*******************************************合同充电桩部分************************************/
    @Override
    public ZuPinXiaoShouContactChargingDTO obtainZuPinXiaoShouContactChargingByUuid(String chargingUuid) {
        XiaoShouContactCharging xiaoShouContactCharging = (XiaoShouContactCharging) xiaoShouContactDao.findByUuid(chargingUuid, XiaoShouContactCharging.class);
        return ZuPinXiaoShouContactChargingWebAssember.toZuPinXiaoShouContactChargingDTO(xiaoShouContactCharging);
    }

    @Override
    public void saveOrUpdateZuPinXiaoShouContactCharging(ZuPinXiaoShouContactChargingDTO zuPinContactChargingDTO, ZuPinXiaoShouContactDTO contactDTO) {
        String yiFangName = contactDTO.getYiFangName();
        String yiFangUuid = contactDTO.getYiFangUuid();
        XiaoShouContactCharging xiaoShouContactCharging = ZuPinXiaoShouContactChargingWebAssember.toDomain(zuPinContactChargingDTO, yiFangUuid, yiFangName);
        xiaoShouContactCharging.setContactUuid(contactDTO.getUuid());
        xiaoShouContactDao.saveOrUpdate(xiaoShouContactCharging);
    }

    @Override
    public void deleteZuPinXiaoShouContactCharging(String chargingUuid) {
        XiaoShouContactCharging xiaoShouContactCharging = (XiaoShouContactCharging) xiaoShouContactDao.findByUuid(chargingUuid, XiaoShouContactCharging.class);
        xiaoShouContactDao.delete(xiaoShouContactCharging);
    }

    /*******************************************合同附件部分*************************************/

    @Override
    public void obtainImportContactFile(String xiaoShouContactUuid, MultipartFile contactFile) {
        XiaoShouContact contact = (XiaoShouContact) xiaoShouContactDao.findByUuid(xiaoShouContactUuid, XiaoShouContact.class);

        LoginInfo loginInfo = SecurityUtils.currentLoginUser();
        XiaoShouContactFile file = new XiaoShouContactFile(contactFile, loginInfo.getUserUuid(), loginInfo.getXingMing());
        uploadZuPinXiaoShouContactFile(contact, file);

        file.setXiaoShouContact(contact);
        xiaoShouContactDao.saveOrUpdate(file);
    }

    private final static String CONTACT_PATH = "contact";

    @Override
    public void uploadZuPinXiaoShouContactFile(XiaoShouContact contact, XiaoShouContactFile file) {
        File directory = new File(ApplicationEventPublisher.applicationFileUploadPath + CONTACT_PATH + File.separatorChar + contact.getContactNumber());
        if (!directory.exists()) {
            directory.mkdirs();
        }
        File orderFile = new File(directory, file.getActualFileName());
        try {
            OutputStream dataOut = new FileOutputStream(orderFile.getAbsolutePath());
            FileCopyUtils.copy(file.getFile().getInputStream(), dataOut);

            ApplicationLog.info(XiaoShouContactAdditionalInfoServiceImpl.class, "finish upload order file for " + file.getUploadFileName());
        } catch (Exception e) {
            ApplicationLog.error(XiaoShouContactAdditionalInfoServiceImpl.class, "finish upload order file error for " + file.getUploadFileName(), e);
            throw new DocumentOperationException("exception upload order file for " + file.getUploadFileName(), e);
        }
    }
}
