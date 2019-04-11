package com.yajun.green.service;

import com.yajun.green.common.log.ApplicationLog;
import com.yajun.green.common.security.LoginInfo;
import com.yajun.green.common.utils.ContactLogUtil;
import com.yajun.green.common.utils.JodaUtils;
import com.yajun.green.domain.contact.*;
import com.yajun.green.domain.pay.PayOrderStatus;
import com.yajun.green.facade.assember.contact.ZuPinContactRentingFeeHistoryWebAssember;
import com.yajun.green.facade.dto.contact.PayOrderManagementShowDTO;
import com.yajun.green.facade.dto.contact.ZuPinContactRentingFeeHistoryDTO;
import com.yajun.green.repository.ZuPinContactDao;
import com.yajun.green.security.SecurityUtils;
import com.yajun.user.facade.dto.CompanyDTO;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Created by LiuMengKe on 2017/11/29.
 */
@Service("zuPinContactPayService")
public class ZuPinContactPayServiceImpl implements ZuPinContactPayService {

    @Autowired
    ZuPinContactDao zuPinContactDao;

    /*********************************合同账单相关*******************************/
    @Override
    public List<PayOrderManagementShowDTO> obtainAllOnCreateBill(String selectCarrierUuid) {
        //获取特定用户所有未付款账单
        List<ZuPinContactRentingFeeHistory> histories = zuPinContactDao.findSpecUserOnCreateBill(selectCarrierUuid);
        Map<String, PayOrderManagementShowDTO> orderMap = new HashMap();

        if (histories != null) {
            for (ZuPinContactRentingFeeHistory history : histories) {
                ZuPinContact contact = history.getContact();
                String contactNumber = history.getContactNumber();
                String jiaFangName = contact.getJiaFangName();
                String yiFangName = contact.getYiFangName();

                PayOrderManagementShowDTO show = orderMap.get(contactNumber);

                if (show == null) {
                    show = new PayOrderManagementShowDTO(jiaFangName, yiFangName, contactNumber);
                }
                ZuPinContactRentingFeeHistoryDTO dto = ZuPinContactRentingFeeHistoryWebAssember.toRentFeeHistoryDTO(history);


                ZuPinVehicleExecute execute = zuPinContactDao.obtainAllStatusSpecZuPinContactVehicleExcute(contact.getUuid(), String.valueOf(history.getTichePiCi()), history.getVehicleNumber());
                /*if(execute.isOver()&&execute.getZuPinContactRepayType().equals(ZuPinContactRepayType.C_BEFORE)){*/
                //遇见其他类型款项(无车牌号） 跳过 不可进行取消操作
                if (execute == null && !StringUtils.hasText(history.getVehicleNumber())) {
                    show.addHistory(dto);
                    orderMap.put(contactNumber, show);
                    continue;
                }
                //todo 针对换车 后 history 保存的还是以前的车辆 无法获取到最新的vehicleNumber 进行处理
                if (execute == null && StringUtils.hasText(history.getVehicleNumber())) {
                    //throw new RuntimeException("ZuPinContactServiceImpl.class 找不到对应execute  有车牌号"+history.getVehicleNumber()+"contactUuid"+contact.getUuid()+"tichePiCi"+history.getTichePiCi()+"vehicleNumber"+history.getVehicleNumber());
                    ContactLogUtil.addErrorLineAndLog(this.getClass(),"ZuPinContactServiceImpl.class 找不到对应execute  有车牌号"+history.getVehicleNumber()+"contactUuid"+contact.getUuid()+"tichePiCi"+history.getTichePiCi()+"vehicleNumber"+history.getVehicleNumber());
                }
                //todo 感觉逻辑有些不完整 还是需要对execute 做判断处理
                if(contact.getZuPinContactBaoYueType().equals(ZuPinContactBaoYueType.B_BAOYUE)){
                    //换车后可能导致没有execute
                    if(execute!=null){
                        if (execute.isOver()) {
                            LocalDate jieshuDate = execute.getJieshuDate();
                            //1 参数二大于参数一 0 相等 -1 参数二小于参数一
                            //jieshudate 11.10 actual 11.11 1
                            int days = JodaUtils.compareDays(jieshuDate, history.getActualFeeDate());
                            ApplicationLog.info(ZuPinContactServiceImpl.class, "结束时间=====" + jieshuDate + "缴纳时间=====" + history.getActualFeeDate() + "间隔时间=====" + days);
                            //只有租金类型才能
                            boolean isZj = history.getZuPinContactRepayLocation().equals(ZuPinContactRepayLocation.L_ZJ_SCHEDULE)||history.getZuPinContactRepayLocation().equals(ZuPinContactRepayLocation.L_ZJ_VEHICLE_SUB);
                            if (days >= 0 && isZj == true) {
                                dto.setCanBeCancel(true);
                            }
                        } else {
                            dto.setCanBeCancel(false);
                        }
                    }else {
                        dto.setCanBeCancel(false);
                    }
                }

                show.addHistory(dto);
                orderMap.put(contactNumber, show);
            }
        }
        return new ArrayList<>(orderMap.values());
    }

    @Override
    public List<CompanyDTO> obtainAllCompanyWithOnCreateBillOnLoginUser() {
        List<Object[]> users = zuPinContactDao.obtainAllCompanyWithOnCreateBillOnLoginUser();
        List<CompanyDTO> companies = new ArrayList<>();
        Map<String, String> companyMap = new HashMap<>();
        for (Object[] user : users) {
            String companyName = companyMap.get((String) user[0]);
            if (StringUtils.hasText(companyName)) {
                continue;
            } else {
                companyMap.put((String) user[0], (String) user[1]);
                CompanyDTO company = new CompanyDTO();
                company.setUuid((String) user[0]);
                company.setName((String) user[1]);
                companies.add(company);
            }

        }
        return companies;
    }

    //在支付完成以后 传入id 重新计算已经缴纳的租金和押金并保存
    @Override
    public void updateZupinContactZuJinYaJinHasPay(Set<String> zuPinContactUuids) {
        String[] contactUuids = (String[]) zuPinContactUuids.toArray(new String[zuPinContactUuids.size()]);
        List<ZuPinContact> contacts = zuPinContactDao.findByUuids(contactUuids, ZuPinContact.class);
        for (ZuPinContact contact : contacts) {
            //更新最新的已付租金和已付押金
            contact.updateZuJinHasPay();
            contact.updateYaJinHasPay();
        }
        zuPinContactDao.saveAll(contacts);
    }

    @Override
    public void saveHistoryCancel(String historyUuid, String comment) {
        LoginInfo loginInfo = SecurityUtils.currentLoginUser();
        ZuPinContactRentingFeeHistory history = (ZuPinContactRentingFeeHistory) zuPinContactDao.findByUuid(historyUuid, ZuPinContactRentingFeeHistory.class);
        StringBuilder builder = new StringBuilder();
        String username = SecurityUtils.currentLoginUser().getUsername();
        ApplicationLog.info(ZuPinContactServiceImpl.class, "用户id：" + loginInfo.getUserUuid() + "用户名" + username + "取消未付账单id:" + history.getUuid());
        if (history.getComment() != null) {
            builder.append(history.getComment()).append("  财务取消账单备注： ").append(comment);
        } else {
            builder.append(comment);
        }
        history.setComment(builder.toString());
        history.setStatus(PayOrderStatus.O_ABANDON);
        zuPinContactDao.saveOrUpdate(history);
    }

    @Override
    public boolean obtainCancelValidationOfHistory(ZuPinContactRentingFeeHistoryDTO zuPinContactRentingFeeHistoryDTO,String zuPinContactUuid) {
        ZuPinContact contact = (ZuPinContact)zuPinContactDao.findByUuid(zuPinContactUuid,ZuPinContact.class);
        String vehicleNumber = zuPinContactRentingFeeHistoryDTO.getVehicleNumber();
        int tichePiCi = zuPinContactRentingFeeHistoryDTO.getTichePiCi();

        ZuPinVehicleExecute execute = contact.getSpecNotOverExecute(vehicleNumber,tichePiCi);
        if(execute == null){
            return false;
        }

        if(contact.getZuPinContactBaoYueType().equals(ZuPinContactBaoYueType.B_BAOYUE)){
            if (execute.isOver()) {
                LocalDate jieshuDate = execute.getJieshuDate();
                //1 参数二大于参数一 0 相等 -1 参数二小于参数一
                //jieshudate 11.10 actual 11.11 1
                int days = JodaUtils.compareDays(jieshuDate, zuPinContactRentingFeeHistoryDTO.getActualFeeDate());
                ApplicationLog.info(ZuPinContactServiceImpl.class, "结束时间=====" + jieshuDate + "缴纳时间=====" + zuPinContactRentingFeeHistoryDTO.getActualFeeDate() + "间隔时间=====" + days);
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
}
