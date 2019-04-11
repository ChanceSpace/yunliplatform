package com.yajun.green.service;


import com.yajun.green.domain.contact.ZuPinContact;
import com.yajun.green.facade.dto.contact.xiaoshou.ZuPinXiaoShouContactDTO;

import java.util.List;

/**
 * Created by LiuMengKe on 2018/1/2.
 */
public interface XiaoShouContactForCarrierService {

    List<ZuPinXiaoShouContactDTO> obtainOverviewXiaoShouContact(String keyWords, String userUuid, String startTime, String endTime, String sortBy, String sortWay, String contactStatus, int startPosition, int pageSize);

    int obtainOverviewXiaoShouContactSize(String keyWords, String userUuid, String startTime, String endTime, String sortBy, String sortWay, String contactStatus);

}
