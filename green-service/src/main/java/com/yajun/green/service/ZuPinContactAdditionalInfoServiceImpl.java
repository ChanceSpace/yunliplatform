package com.yajun.green.service;

import com.yajun.green.application.ApplicationEventPublisher;
import com.yajun.green.common.exception.DocumentOperationException;
import com.yajun.green.common.log.ApplicationLog;
import com.yajun.green.common.security.LoginInfo;
import com.yajun.green.domain.contact.*;
import com.yajun.green.facade.assember.contact.ZuPinContactChargingWebAssember;
import com.yajun.green.facade.assember.contact.ZuPinContactSupplyContentWebAssember;
import com.yajun.green.facade.assember.contact.ZuPinContactSupplyWebAssember;
import com.yajun.green.facade.dto.contact.ZuPinContactChargingDTO;
import com.yajun.green.facade.dto.contact.ZuPinContactSupplyContentDTO;
import com.yajun.green.facade.dto.contact.ZuPinContactSupplyDTO;
import com.yajun.green.repository.ZuPinContactDao;
import com.yajun.green.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by LiuMengKe on 2017/11/27.
 */
@Service("zuPinContactAdditionalInfoService")
public class ZuPinContactAdditionalInfoServiceImpl implements  ZuPinContactAdditionalInfoService {

    @Autowired
    private ZuPinContactDao zuPinContactDao;

    /*******************************************合同补充合同部分************************************/

    @Override
    public ZuPinContactSupplyDTO obtainZuPinContactSupplyByUuid(String contactSupplyUuid) {
        ZuPinContactSupply zuPinContactSupply = (ZuPinContactSupply) zuPinContactDao.findByUuid(contactSupplyUuid, ZuPinContactSupply.class);
        return ZuPinContactSupplyWebAssember.toZuPinContactSupplyDTO(zuPinContactSupply);
    }

    @Override
    public String saveOrUpdateZuPinContactSupply(ZuPinContactSupplyDTO zuPinContactSupplyDTO, String zuPinContactUuid) {
        ZuPinContact zuPinContact = new ZuPinContact();
        zuPinContact.setUuid(zuPinContactUuid);
        ZuPinContactSupply zuPinContactSupply = ZuPinContactSupplyWebAssember.toDomain(zuPinContactSupplyDTO, zuPinContactUuid);
        zuPinContactDao.saveOrUpdate(zuPinContactSupply);
        return zuPinContactSupply.getUuid();
    }

    @Override
    public void deleteZuPinContactSupply(String contactSupplyUuid) {
        ZuPinContactSupply zuPinContactSupply = (ZuPinContactSupply) zuPinContactDao.findByUuid(contactSupplyUuid, ZuPinContactSupply.class);
        zuPinContactDao.delete(zuPinContactSupply);
    }

    /*******************************************合同补充合同内容部分************************************/

    @Override
    public void saveOrUpdateZuPinContactSupplyContent(ZuPinContactSupplyContentDTO zuPinContactSupplyContentDTO, String zuPinContactSupplyUuid) {
        ZuPinContactSupplyContent zuPinContactSupplyContent = ZuPinContactSupplyContentWebAssember.toDomain(zuPinContactSupplyContentDTO, zuPinContactSupplyUuid);
        zuPinContactDao.saveOrUpdate(zuPinContactSupplyContent);
    }

    @Override
    public void deleteZuPinContactSupplyContent(String contactSupplyContentUuid) {
        ZuPinContactSupplyContent zuPinContactSupplyContent = (ZuPinContactSupplyContent) zuPinContactDao.findByUuid(contactSupplyContentUuid, ZuPinContactSupplyContent.class);
        zuPinContactDao.delete(zuPinContactSupplyContent);
    }

    /*******************************************合同充电桩部分************************************/

    @Override
    public List<ZuPinContactChargingDTO> obtainZuPinContactCharging(String yiFangUuid) {
        List<ZuPinContactCharging> chargings = zuPinContactDao.findZuPinContactCharging(yiFangUuid);
        return ZuPinContactChargingWebAssember.toZuPinContactChargingDTOList(chargings);
    }

    @Override
    public ZuPinContactChargingDTO obtainZuPinContactChargingByUuid(String chargingUuid) {
        ZuPinContactCharging zuPinContactCharging = (ZuPinContactCharging) zuPinContactDao.findByUuid(chargingUuid, ZuPinContactCharging.class);
        return ZuPinContactChargingWebAssember.toZuPinContactChargingDTO(zuPinContactCharging);
    }

    @Override
    public void saveOrUpdateZuPinContactCharging(ZuPinContactChargingDTO zuPinContactChargingDTO, String yiFangUuid, String yiFangName) {
        ZuPinContactCharging zuPinContactCharging = ZuPinContactChargingWebAssember.toDomain(zuPinContactChargingDTO, yiFangUuid, yiFangName);
        zuPinContactDao.saveOrUpdate(zuPinContactCharging);
    }

    @Override
    public void deleteZuPinContactCharging(String chargingUuid) {
        ZuPinContactCharging zuPinContactCharging = (ZuPinContactCharging) zuPinContactDao.findByUuid(chargingUuid, ZuPinContactCharging.class);
        zuPinContactDao.delete(zuPinContactCharging);
    }

    /*******************************************合同附件部分*************************************/

    @Override
    public void obtainImportContactFile(String zuPinContactUuid, MultipartFile contactFile) {
        ZuPinContact contact = (ZuPinContact) zuPinContactDao.findByUuid(zuPinContactUuid, ZuPinContact.class);

        LoginInfo loginInfo = SecurityUtils.currentLoginUser();
        ZuPinContactFile file = new ZuPinContactFile(contactFile, loginInfo.getUserUuid(), loginInfo.getXingMing());
        uploadZuPinContactFile(contact, file);

        file.setZuPinContact(contact);
        zuPinContactDao.saveOrUpdate(file);
    }

    private final static String CONTACT_PATH = "contact";

    @Override
    public void uploadZuPinContactFile(ZuPinContact contact, ZuPinContactFile file) {
        File directory = new File(ApplicationEventPublisher.applicationFileUploadPath + CONTACT_PATH + File.separatorChar + contact.getContactNumber());
        if (!directory.exists()) {
            directory.mkdirs();
        }
        File orderFile = new File(directory, file.getActualFileName());
        try {
            OutputStream dataOut = new FileOutputStream(orderFile.getAbsolutePath());
            FileCopyUtils.copy(file.getFile().getInputStream(), dataOut);

            ApplicationLog.info(ZuPinContactAdditionalInfoServiceImpl.class, "finish upload order file for " + file.getUploadFileName());
        } catch (Exception e) {
            ApplicationLog.error(ZuPinContactAdditionalInfoServiceImpl.class, "finish upload order file error for " + file.getUploadFileName(), e);
            throw new DocumentOperationException("exception upload order file for " + file.getUploadFileName(), e);
        }
    }

}
