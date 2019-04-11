package com.yajun.green.service;

import com.yajun.green.domain.xiaoshou.XiaoShouContact;
import com.yajun.green.facade.assember.xiaoshou.XiaoShouContactWebAssember;
import com.yajun.green.facade.dto.contact.xiaoshou.ZuPinXiaoShouContactDTO;
import com.yajun.green.repository.xiaoshou.XiaoShouForCarrierDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by LiuMengKe on 2018/1/2.
 */
@Service("xiaoShouContactForCarrierService")
public class XiaoShouContactForCarrierServiceImpl implements  XiaoShouContactForCarrierService{

    @Autowired
    private XiaoShouForCarrierDao xiaoShouForCarrierDao;

    @Override
    public List<ZuPinXiaoShouContactDTO> obtainOverviewXiaoShouContact(String keyWords, String userUuid, String startTime, String endTime, String sortBy, String sortWay, String contactStatus, int startPosition, int pageSize) {
        List<XiaoShouContact> contacts = xiaoShouForCarrierDao.findOverviewZuPinContact(keyWords, userUuid, startTime, endTime, sortBy, sortWay, contactStatus, startPosition, pageSize);
        return XiaoShouContactWebAssember.toXiaoShouContactDTOList(contacts, keyWords);
    }

    @Override
    public int obtainOverviewXiaoShouContactSize(String keyWords, String userUuid, String startTime, String endTime, String sortBy, String sortWay, String contactStatus) {
        return xiaoShouForCarrierDao.findOverviewXiaoShouContactSize(keyWords, userUuid, startTime, endTime, sortBy, sortWay, contactStatus);
    }
}
