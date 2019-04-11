package com.yajun.green.repository.xiaoshou;

import com.yajun.green.domain.xiaoshou.XiaoShouContact;
import com.yajun.green.repository.EntityObjectDao;

import java.util.List;

/**
 * Created by LiuMengKe on 2018/1/2.
 */
public interface XiaoShouForCarrierDao extends EntityObjectDao {

    List<XiaoShouContact> findOverviewZuPinContact(String keyWords, String userUuid, String startTime, String endTime, String sortBy, String sortWay, String contactStatus, int startPosition, int pageSize);

    int findOverviewXiaoShouContactSize(String keyWords, String userUuid, String startTime, String endTime, String sortBy, String sortWay, String contactStatus);


}
