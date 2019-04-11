package com.yajun.green.service;

import com.yajun.green.facade.dto.contact.PayOrderManagementShowDTO;
import com.yajun.green.facade.dto.contact.ZuPinContactRentingFeeHistoryDTO;
import com.yajun.user.facade.dto.CompanyDTO;

import java.util.List;
import java.util.Set;

/**
 * Created by LiuMengKe on 2017/11/29.
 */
public interface ZuPinContactPayService {

    /*********************************待支付页面*******************************/

    //待支付页面获取所有待支付账单
    public List<PayOrderManagementShowDTO> obtainAllOnCreateBill(String selectCarrierUuid);

    //待支付中取消账单
    void saveHistoryCancel(String historyUuid, String comment);
    //校验是否能够被取消
    boolean obtainCancelValidationOfHistory(ZuPinContactRentingFeeHistoryDTO zuPinContactRentingFeeHistoryDTO,String zuPinContactUuid);

    /**********获取当前登陆用户所在公司的所有有未支付账单的承运商        待支付页面使用***********/

    List<CompanyDTO> obtainAllCompanyWithOnCreateBillOnLoginUser();

    /*****传入合同ids 重新计算已经缴纳的租金和押金并保存 支付成功后回调使用******/
    void updateZupinContactZuJinYaJinHasPay(Set<String> zuPinContactUuids);
}
