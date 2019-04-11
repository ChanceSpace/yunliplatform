package com.yajun.green.service;

import com.yajun.green.common.log.ApplicationLog;
import com.yajun.green.common.security.LoginInfo;
import com.yajun.green.common.utils.CHListUtils;
import com.yajun.green.domain.xiaoshou.*;
import com.yajun.green.facade.dto.contact.xiaoshou.ZuPinXiaoShouVehicleExecuteDTO;

import com.yajun.green.repository.xiaoshou.XiaoShouContactDao;
import com.yajun.green.security.SecurityUtils;
import com.yajun.vms.service.VmsDubboService;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiuMengKe on 2017/11/27.
 */
@Service("xiaoShouContactVehicleGetAndChangeService")
public class XiaoShouContactVehicleGetAndChangeServiceImpl implements XiaoShouContactVehicleGetAndChangeService {
    @Autowired
    private XiaoShouContactDao xiaoShouContactDao;
    @Autowired
    private VmsDubboService vmsDubboService;
    //提车
    //提车操作  根据类型生产对应的款项
    @Override
    public void saveVehicleGet(String xiaoShouContactUuid, String tiChePiCi, ZuPinXiaoShouVehicleExecuteDTO execute, String selectModuleBrand, String[] vehicleNumbers, String[] comment) throws Exception{

        XiaoShouContact contact = (XiaoShouContact) xiaoShouContactDao.findByUuid(xiaoShouContactUuid, XiaoShouContact.class);
        //获取module
        List<XiaoShouVehicleModule> modules = contact.getOrigModules();
        //每个合同一个车型 暂时这样写
        XiaoShouVehicleModule module = modules.get(0);

        //修改车辆的状态
        vmsDubboService.saveYiBuVehicleStatusChange(vehicleNumbers, contact.getJiaFangUuid(), contact.getJiaFangName(), contact.getContactNumber(), contact.getYiFangUuid(), contact.getYiFangName(), execute.getTiCheDate().toString(), true);

        //初始化execute
        ApplicationLog.info(XiaoShouContactVehicleGetAndChangeServiceImpl.class, "提车" + execute);

        LoginInfo info = SecurityUtils.currentLoginUser();
        execute.initExecuteWithModule(module,info);

        List<XiaoShouVehicleExecute> newExecutes = null;
        List<XiaoShouContactRentingFeeHistory> newHistorys = new ArrayList<>();

        for (int i = 0; i < vehicleNumbers.length; i++) {
            if(StringUtils.hasText(vehicleNumbers[i])){
                //初始化execute
                XiaoShouVehicleExecute tempExecute = new XiaoShouVehicleExecute(info, module, vehicleNumbers[i],  Integer.valueOf(tiChePiCi), execute.getTiCheDate());
                //初始化execute的状态为定金为支付
                tempExecute.setExecuteStatus(XiaoShouContactExecuteStatus.E_DJ_BEGIN);
                tempExecute.setComment(comment[i]);
                tempExecute.setXiaoShouContact(contact);
                if(CHListUtils.hasElement(newExecutes)){
                    newExecutes.add(tempExecute);
                }else {
                    newExecutes = new ArrayList<>();
                    newExecutes.add(tempExecute);
                }

                //全款方式 生成 定金和尾款 同时execute 改变状态
                if(XiaoShouType.XS_QK.equals(contact.getContactType())){
                    //定金
                    XiaoShouContactRentingFeeHistory dingJinHistory = new XiaoShouContactRentingFeeHistory();
                    dingJinHistory.setVehicleNumber(vehicleNumbers[i]);
                    dingJinHistory.setTichePiCi(Integer.valueOf(tiChePiCi));
                    dingJinHistory.setContactNumber(contact.getContactNumber());
                    dingJinHistory.setFeeTotal(tempExecute.getDingJin());
                    dingJinHistory.setActualFeeDate(tempExecute.getTiCheDate());
                    dingJinHistory.setPayOrderStatus(XiaoShouPayOrderStatus.O_CREATE);
                    dingJinHistory.setXiaoShouContactHistoryType(XiaoShouContactHistoryType.H_DJ);
                    dingJinHistory.setJiaoYiRen(info.getXingMing());
                    dingJinHistory.setJiaoYiRenUuid(info.getUserUuid());
                    dingJinHistory.setHappendDate(new LocalDate());
                    dingJinHistory.setXiaoShouContact(contact);
                    newHistorys.add(dingJinHistory);

                    //尾款
                    XiaoShouContactRentingFeeHistory weikuanHistroy = new XiaoShouContactRentingFeeHistory();
                    weikuanHistroy.setVehicleNumber(vehicleNumbers[i]);
                    weikuanHistroy.setTichePiCi(Integer.valueOf(tiChePiCi));
                    weikuanHistroy.setContactNumber(contact.getContactNumber());
                    weikuanHistroy.setFeeTotal(tempExecute.getWeiKuan());
                    weikuanHistroy.setActualFeeDate(tempExecute.getTiCheDate());
                    weikuanHistroy.setPayOrderStatus(XiaoShouPayOrderStatus.O_CREATE);
                    weikuanHistroy.setXiaoShouContactHistoryType(XiaoShouContactHistoryType.H_WK);
                    weikuanHistroy.setJiaoYiRen(info.getXingMing());
                    weikuanHistroy.setJiaoYiRenUuid(info.getUserUuid());
                    weikuanHistroy.setHappendDate(new LocalDate());
                    weikuanHistroy.setXiaoShouContact(contact);
                    newHistorys.add(weikuanHistroy);
                }

                //按揭方式生成 定金 首付 尾款  同时execute 改变状态
                if(XiaoShouType.XS_ANJIE.equals(contact.getContactType())){
                    //定金
                    XiaoShouContactRentingFeeHistory dingJinHistory = new XiaoShouContactRentingFeeHistory();
                    dingJinHistory.setVehicleNumber(vehicleNumbers[i]);
                    dingJinHistory.setTichePiCi(Integer.valueOf(tiChePiCi));
                    dingJinHistory.setContactNumber(contact.getContactNumber());
                    dingJinHistory.setFeeTotal(tempExecute.getDingJin());
                    dingJinHistory.setActualFeeDate(tempExecute.getTiCheDate());
                    dingJinHistory.setPayOrderStatus(XiaoShouPayOrderStatus.O_CREATE);
                    dingJinHistory.setXiaoShouContactHistoryType(XiaoShouContactHistoryType.H_DJ);
                    dingJinHistory.setJiaoYiRen(info.getXingMing());
                    dingJinHistory.setJiaoYiRenUuid(info.getUserUuid());
                    dingJinHistory.setHappendDate(new LocalDate());
                    dingJinHistory.setXiaoShouContact(contact);
                    newHistorys.add(dingJinHistory);
                    //首付
                    XiaoShouContactRentingFeeHistory shofuHistory = new XiaoShouContactRentingFeeHistory();
                    shofuHistory.setVehicleNumber(vehicleNumbers[i]);
                    shofuHistory.setTichePiCi(Integer.valueOf(tiChePiCi));
                    shofuHistory.setContactNumber(contact.getContactNumber());
                    shofuHistory.setFeeTotal(tempExecute.getShouFu());
                    shofuHistory.setActualFeeDate(tempExecute.getTiCheDate());
                    shofuHistory.setPayOrderStatus(XiaoShouPayOrderStatus.O_CREATE);
                    shofuHistory.setXiaoShouContactHistoryType(XiaoShouContactHistoryType.H_SF);
                    shofuHistory.setJiaoYiRen(info.getXingMing());
                    shofuHistory.setJiaoYiRenUuid(info.getUserUuid());
                    shofuHistory.setJiaoYiRen(info.getXingMing());
                    shofuHistory.setJiaoYiRenUuid(info.getUserUuid());
                    shofuHistory.setHappendDate(new LocalDate());
                    shofuHistory.setXiaoShouContact(contact);
                    newHistorys.add(shofuHistory);
                    //尾款
                    XiaoShouContactRentingFeeHistory weikuanHistroy = new XiaoShouContactRentingFeeHistory();
                    weikuanHistroy.setVehicleNumber(vehicleNumbers[i]);
                    weikuanHistroy.setTichePiCi(Integer.valueOf(tiChePiCi));
                    weikuanHistroy.setContactNumber(contact.getContactNumber());
                    weikuanHistroy.setFeeTotal(tempExecute.getWeiKuan());
                    weikuanHistroy.setActualFeeDate(tempExecute.getTiCheDate());
                    weikuanHistroy.setPayOrderStatus(XiaoShouPayOrderStatus.O_CREATE);
                    weikuanHistroy.setXiaoShouContactHistoryType(XiaoShouContactHistoryType.H_WK);
                    weikuanHistroy.setJiaoYiRen(info.getXingMing());
                    weikuanHistroy.setJiaoYiRenUuid(info.getUserUuid());
                    weikuanHistroy.setHappendDate(new LocalDate());
                    weikuanHistroy.setXiaoShouContact(contact);
                    newHistorys.add(weikuanHistroy);
                }

                XiaoShouContactLog xiaoShouContactLog = new XiaoShouContactLog(info.getUserUuid(),info.getXingMing(),"提车，备注:车牌号" + vehicleNumbers[i] ,contact);
                xiaoShouContactDao.saveOrUpdate(xiaoShouContactLog);

            }
        }

        xiaoShouContactDao.saveAll(newExecutes);
        xiaoShouContactDao.saveAll(newHistorys);
    }


    @Override
    public boolean obtainDuplicateNotFinishVehicleOfContact(String vehicleNubmer, String xiaoShouContactUuid) {
        XiaoShouContact xiaoShouContact = (XiaoShouContact)xiaoShouContactDao.findByUuid(xiaoShouContactUuid,XiaoShouContact.class);
        List<XiaoShouVehicleExecute> executes = xiaoShouContact.getVehicleExecutes();
        for (XiaoShouVehicleExecute execute : executes) {
             if(execute.getVehicleNum().equals(vehicleNubmer)){
                return true;
            }
        }
        return false;
    }

}
