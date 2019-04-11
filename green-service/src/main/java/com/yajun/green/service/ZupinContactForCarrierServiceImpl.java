package com.yajun.green.service;

import com.yajun.green.domain.contact.ZuPinContact;
import com.yajun.green.facade.assember.contact.ZuPinContactWebAssember;
import com.yajun.green.facade.dto.contact.ZuPinContactDTO;
import com.yajun.green.repository.ZuPinContactForCarrierDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by LiuMengKe on 2017/9/18.
 */
@Service("zupinContactForCarrierService")
public class ZupinContactForCarrierServiceImpl implements ZupinContactForCarrierService {

    @Autowired
    ZuPinContactForCarrierDao zuPinContactForCarrierDao;

    @Override
    public List<ZuPinContactDTO> obtainZuPinContactForCarrierOverview(String keyWords, String userUuid, String startTime, String endTime, String sortBy, String sortWay, int startPosition, int pageSize,String contactStatus) {
        List<ZuPinContact> contacts = zuPinContactForCarrierDao.obtainZuPinContactForCarrierOverview(keyWords, userUuid, startTime, endTime, sortBy, sortWay, startPosition, pageSize,contactStatus) ;
        return ZuPinContactWebAssember.toZuPinContactDTOList(contacts, keyWords);
    }

    @Override
    public int obtainOverviewZuPinContactForCarrierSize(String keyWords, String userUuid, String startTime, String endTime, String sortBy, String sortWay,String contactStatus) {
        return zuPinContactForCarrierDao.obtainOverviewZuPinContactForCarrierSize(keyWords, userUuid, startTime, endTime, sortBy, sortWay,contactStatus);
    }

}
