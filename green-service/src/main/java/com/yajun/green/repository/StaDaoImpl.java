package com.yajun.green.repository;

import com.yajun.green.common.utils.JodaUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Jack Wang
 * Date: 18-1-12
 * Time: 上午9:30
 */
@Repository("staDao")
public class StaDaoImpl extends HibernateEntityObjectDao implements StaDao {

    /******************************************租赁合同统计相关************************************/

    @Override
    public Map<String, Integer> findZuPinContactSta(boolean admin, String companyUuid, int year) {
        Map<String, Integer> model = new HashMap<>();

        DateTime startDate = JodaUtils.parseDateTimedMyHms(year + "-01-01 00:00:00");
        DateTime endDate = JodaUtils.parseDateTimedMyHms(year + "-12-31 23:59:59");
        StringBuffer buffer = new StringBuffer();
        buffer.append("select MONTH(c.createTime) as month, count(c.uuid) as total from ZuPinContact c where c.createTime >= ? and c.createTime <= ?");
        if (!admin) {
            buffer.append(" and c.jiaFangUuid = '" + companyUuid + "'");
        }
        buffer.append(" group by MONTH(c.createTime)");
        List<Object[]> datas = getHibernateTemplate().find(buffer.toString(), new Object[]{startDate, endDate});

        if (!datas.isEmpty()) {
            for (Object[] data : datas) {
                model.put(String.valueOf((Integer)data[0]), ((Long)data[1]).intValue());
            }
        }

        return model;
    }

    /******************************************销售合同统计相关************************************/

    @Override
    public Map<String, Integer> findXiaoShouContactSta(boolean admin, String companyUuid, int year) {
        Map<String, Integer> model = new HashMap<>();

        DateTime startDate = JodaUtils.parseDateTimedMyHms(year + "-01-01 00:00:00");
        DateTime endDate = JodaUtils.parseDateTimedMyHms(year + "-12-31 23:59:59");
        StringBuffer buffer = new StringBuffer();
        buffer.append("select MONTH(c.createTime) as month, count(c.uuid) as total from XiaoShouContact c where c.createTime >= ? and c.createTime <= ?");
        if (!admin) {
            buffer.append(" and c.jiaFangUuid = '" + companyUuid + "'");
        }
        buffer.append(" group by MONTH(c.createTime)");
        List<Object[]> datas = getHibernateTemplate().find(buffer.toString(), new Object[]{startDate, endDate});

        if (!datas.isEmpty()) {
            for (Object[] data : datas) {
                model.put(String.valueOf((Integer)data[0]), ((Long)data[1]).intValue());
            }
        }

        return model;
    }
}
