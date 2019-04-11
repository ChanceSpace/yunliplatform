package com.yajun.green.repository;

import java.util.Map;

/**
 * User: Jack Wang
 * Date: 18-1-12
 * Time: 上午9:29
 */
public interface StaDao extends EntityObjectDao {

    /******************************************租赁合同统计相关************************************/

    Map<String, Integer> findZuPinContactSta(boolean admin, String companyUuid, int year);

    /******************************************销售合同统计相关************************************/

    Map<String,Integer> findXiaoShouContactSta(boolean admin, String companyUuid, int year);
}
