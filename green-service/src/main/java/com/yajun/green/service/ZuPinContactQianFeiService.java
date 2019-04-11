package com.yajun.green.service;

import com.yajun.green.facade.dto.contact.ZuPinContactDTO;

import java.util.List;

/**
 * Created by LiuMengKe on 2017/11/27.
 *
 * 合同主信息部分 浏览 创建 编辑 合同 以及合同车型
 *
 */
public interface ZuPinContactQianFeiService {

    /******************************************用户欠费相关******************************************/

    //获取所有执行中和结束未还车的合同
    List<ZuPinContactDTO> obtainAllNotFinishedZuPinContact();

    ZuPinContactDTO updateQianFeiTime(String  zuPinContactUuid);

    //改变用户欠费相关
    void updateCompanyUnpay(List<ZuPinContactDTO> unpayContacts);
}
