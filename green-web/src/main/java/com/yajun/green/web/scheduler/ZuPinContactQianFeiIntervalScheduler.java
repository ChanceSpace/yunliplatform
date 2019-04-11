package com.yajun.green.web.scheduler;

import com.yajun.green.common.log.ApplicationLog;
import com.yajun.green.facade.dto.contact.ZuPinContactDTO;
import com.yajun.green.service.ZuPinContactQianFeiService;
import com.yajun.green.service.ZuPinContactService;
import com.yajun.user.service.UserDubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiuMengKe on 2017/10/20.
 *
 */
@Service("zuPinContactQianFeiIntervalScheduler")
public class ZuPinContactQianFeiIntervalScheduler {

    @Autowired
    private UserDubboService userDubboService;

    @Autowired
    private ZuPinContactQianFeiService zuPinContactQianFeiService;

    //计算还原时间
    public void resetQianFeiInfo(){
        //还原所有的时间
        userDubboService.saveTongBuRevertCompanyQianFei();
    }

    //计算欠费时间
    public void sumbitQianFeiInfo(){
        //获取所有未结束合同 包括创建状态 合同执行状态 执行完成未结束状态
        List<ZuPinContactDTO> unpayContacts = new ArrayList<>();
        List<ZuPinContactDTO> notFinishZuPinContacts = zuPinContactQianFeiService.obtainAllNotFinishedZuPinContact();

        //重置所有未结束合同的欠费状态和欠费开始时间
        for (ZuPinContactDTO zuPinContact : notFinishZuPinContacts) {
            ApplicationLog.info(ZuPinContactQianFeiIntervalScheduler.class, "============合同id=" + zuPinContact.getUuid() + ",开始重置欠费时间 和欠费金额=========");

            ZuPinContactDTO zuPinContactDTO = zuPinContactQianFeiService.updateQianFeiTime(zuPinContact.getUuid());
            if (zuPinContactDTO != null) {
                unpayContacts.add(zuPinContactDTO);
            }
        }

        //更新欠费时间
        zuPinContactQianFeiService.updateCompanyUnpay(unpayContacts);
    }

}
