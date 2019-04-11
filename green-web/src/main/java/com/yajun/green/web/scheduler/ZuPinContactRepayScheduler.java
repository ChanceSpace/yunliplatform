package com.yajun.green.web.scheduler;

import com.yajun.green.common.utils.CHListUtils;
import com.yajun.green.facade.dto.contact.ZuPinVehicleExecuteDTO;
import com.yajun.green.service.ZuPinContactBalanceCalculateAndEditService;
import com.yajun.green.common.utils.ContactLogUtil;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by LiuMengKe on 2017/8/31.
 * 定时自动扣款
 */
@Service("zuPinContactRepayScheduler")
public class ZuPinContactRepayScheduler {


   @Autowired
   private ZuPinContactBalanceCalculateAndEditService zuPinContactBalanceCalculateAndEditService;

    public void repaySchedule() {
            ContactLogUtil.addLineAndLog(this.getClass(),new DateTime()+"项目开始扣费");
            LocalDate date = new LocalDate();
            //获取所有即将到期 或者已经到期的记录的对应的销售  并且自动执行的不需要管余额是否充足
            List<ZuPinVehicleExecuteDTO> list = zuPinContactBalanceCalculateAndEditService.obtainDaoQiExecute(date,ZuPinContactBalanceCalculateAndEditService.RELAY_DAY);

            Map<String, List<Integer>> map = new HashMap<>();
            for (ZuPinVehicleExecuteDTO zuPinVehicleExecuteDTO : list) {
                Integer tiChePiCi = zuPinVehicleExecuteDTO.getTiChePiCi();
                String zuPinContactUuid = zuPinVehicleExecuteDTO.getZuPinContactUuid();
                List<Integer> tiChePiCis = map.get(zuPinContactUuid);
                if (!CHListUtils.hasElement(tiChePiCis)) {
                    tiChePiCis = new ArrayList<>();
                    tiChePiCis.add(tiChePiCi);
                    map.put(zuPinContactUuid, tiChePiCis);
                } else {
                    if (!tiChePiCis.contains(tiChePiCi)) {
                        tiChePiCis.add(tiChePiCi);
                        map.put(zuPinContactUuid, tiChePiCis);
                    }
                }
            }

            Set<Map.Entry<String, List<Integer>>> entrySet = map.entrySet();
            Iterator<Map.Entry<String, List<Integer>>> iterator = entrySet.iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, List<Integer>> next = iterator.next();
                String zupinContactUuid = next.getKey();
                List<Integer> piciList = next.getValue();
                for (Integer pici : piciList) {
                    ContactLogUtil.addShortLine(this.getClass(),"合同  "+zupinContactUuid+" 批次"+pici+"开始扣费");
                    zuPinContactBalanceCalculateAndEditService.saveInSchedule(zupinContactUuid, String.valueOf(pici));
                    ContactLogUtil.addShortLine(this.getClass(),"合同  "+zupinContactUuid+" 批次"+pici+"结束扣费");
                }
            }

            ContactLogUtil.addLineAndLog(this.getClass(),new DateTime()+"项目结束扣费");

    }



}
