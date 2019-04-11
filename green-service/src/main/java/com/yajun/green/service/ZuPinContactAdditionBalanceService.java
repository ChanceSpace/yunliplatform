package com.yajun.green.service;

import com.yajun.green.facade.dto.contact.ZuPinContactOverFormDTO;

/**
 * Created by LiuMengKe on 2017/12/11.
 */
public interface ZuPinContactAdditionBalanceService {
    //保存 单车的 附加款项
    void updateVehicleAdditionBalance(ZuPinContactOverFormDTO zuPinContactOverFormDTO,String zuPinContactUuid);
}
