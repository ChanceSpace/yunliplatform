package com.yajun.green.repository;

import com.yajun.green.domain.contact.ZuPinContact;
import com.yajun.green.facade.dto.contact.ZuPinContactDTO;

import java.util.List;

/**
 * Created by LiuMengKe on 2017/9/18.
 */
public interface ZuPinContactForCarrierDao {

    List<ZuPinContact> obtainZuPinContactForCarrierOverview(String keyWords, String userUuid, String startTime, String endTime, String sortBy, String sortWay, int startPosition, int pageSize,String contactStatus);

    int obtainOverviewZuPinContactForCarrierSize(String keyWords, String userUuid, String startTime, String endTime, String sortBy, String sortWay,String contactStatus);
}
