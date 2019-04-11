package com.yajun.green.service;

import com.yajun.green.domain.contact.*;
import com.yajun.green.facade.assember.contact.VehicleChangeHistoryWebAssember;
import com.yajun.green.facade.assember.contact.ZuPinVehicleExecuteWebAssember;
import com.yajun.green.facade.dto.contact.VehicleChangeHistoryDTO;
import com.yajun.green.facade.dto.contact.ZuPinVehicleExecuteDTO;
import com.yajun.green.repository.ZuPinContactDao;
import com.yajun.vms.service.VmsDubboService;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by LiuMengKe on 2017/11/27.
 */
@Service("zuPinContactVehicleGetAndChangeService")
public class ZuPinContactVehicleGetAndChangeServiceImpl implements ZuPinContactVehicleGetAndChangeService {
    @Autowired
    ZuPinContactDao zuPinContactDao;
    @Autowired
    ZuPinContactBalanceCalculateAndEditService zuPinContactBalanceCalculateAndEditService;
    @Autowired
    VmsDubboService vmsDubboService;
    /************************获取指定车牌号和提车日期 查找合同期限内是否已经存在改车******/
    @Override
    public ZuPinVehicleExecuteDTO obtainDuplicateVehicleFromAllContact(String vehicleNum, String tiCheDate) {
        ZuPinVehicleExecute vehicleExecute =   zuPinContactDao.obtainDuplicateVehicleFromAllContact(vehicleNum,tiCheDate);
        if (vehicleExecute != null) {
            return ZuPinVehicleExecuteWebAssember.toZuPinVehicleExecuteDTO(vehicleExecute);
        } else {
            return null;
        }
    }

    //换车
    @Override
    public void saveVehicleChange(String zuPinContactUuid, VehicleChangeHistoryDTO vehicleChangeHistoryDTO, String vehicleNumberBefore, String comment) {
        ZuPinContact zuPinContact = (ZuPinContact) zuPinContactDao.findByUuid(zuPinContactUuid,ZuPinContact.class);
        List<ZuPinVehicleExecute> executes = zuPinContact.getVehicleExecutes();
        for (ZuPinVehicleExecute execute : executes) {
            //有可能结束一辆车，然后又提相同的车，所以要判断是否结束
            if (execute.getVehicleNum().equals(vehicleNumberBefore) && !execute.isOver()) {
                execute.setVehicleNum(vehicleChangeHistoryDTO.getVehicleNumberNow());
                execute.updateComment(new LocalDate().toString(),comment);
                zuPinContactDao.saveOrUpdate(execute);
            }
        }
        //保存修改信息
        vehicleChangeHistoryDTO.setZuPinContact(zuPinContact);
        vehicleChangeHistoryDTO.setVehicleNumberBefore(vehicleNumberBefore);
        VehicleChangeHistory vehicleChangeHistory = VehicleChangeHistoryWebAssember.toDomain(vehicleChangeHistoryDTO);
        zuPinContactDao.saveOrUpdate(vehicleChangeHistory);
        //保存操作日志
        ZuPinContactLog zuPinContactLog = new ZuPinContactLog(vehicleChangeHistoryDTO.getCaoZuoRenUuid(),vehicleChangeHistoryDTO.getCaoZuoRen(),"车辆变更，备注:车牌号" + vehicleNumberBefore + "变更为" + vehicleChangeHistoryDTO.getVehicleNumberNow(),zuPinContact);
        zuPinContactDao.saveOrUpdate(zuPinContactLog);
        //车辆信息修改
        vmsDubboService.saveYiBuVehicleChangeRevert(zuPinContact.getJiaFangUuid(), zuPinContact.getJiaFangName(), zuPinContact.getContactNumber(), zuPinContact.getYiFangUuid(), zuPinContact.getYiFangName(),
                vehicleChangeHistoryDTO.getVehicleNumberBefore(), vehicleChangeHistoryDTO.getVehicleNumberNow(), new LocalDate().toString());
    }

    //提车
    //提车操作
    @Override
    public void saveVehicleGet(String zuPinContactUuid, String tiChePiCi, ZuPinVehicleExecuteDTO execute, String selectModuleBrand, String[] vehicleNumbers, String[] comment) throws Exception{
        ZuPinContact contact = (ZuPinContact) zuPinContactDao.findByUuid(zuPinContactUuid, ZuPinContact.class);

        //包月类型提车
        if(ZuPinContactBaoYueType.B_BAOYUE.equals(contact.getZuPinContactBaoYueType())){
            //修改车辆的状态
            vmsDubboService.saveYiBuVehicleStatusChange(vehicleNumbers, contact.getJiaFangUuid(), contact.getJiaFangName(), contact.getContactNumber(), contact.getYiFangUuid(), contact.getYiFangName(), execute.getTiCheDate(), false);
            //提车 并初始化execute
            List<ZuPinVehicleExecute> executeList = zuPinContactBalanceCalculateAndEditService.saveTiCheInitExecute(contact, selectModuleBrand, vehicleNumbers,comment,execute);
            //提车时缴纳租金
            ZuPinContact zuPinContact = zuPinContactBalanceCalculateAndEditService.saveRepayZuJinInVehicleGet(contact, String.valueOf(tiChePiCi), executeList);
            //提车时还押金
            ZuPinContact zuPinContact1 = zuPinContactBalanceCalculateAndEditService.saveRepayYaJinInVehicleGet(zuPinContact,String.valueOf(tiChePiCi),executeList);

            zuPinContactDao.saveOrUpdate(zuPinContact1);
            zuPinContactDao.saveAll(executeList);
        }

        //日结类型提车
        if(ZuPinContactBaoYueType.B_RIJIE.equals(contact.getZuPinContactBaoYueType())){
            //修改车辆的状态
            vmsDubboService.saveYiBuVehicleStatusChange(vehicleNumbers, contact.getJiaFangUuid(), contact.getJiaFangName(), contact.getContactNumber(), contact.getYiFangUuid(), contact.getYiFangName(), execute.getTiCheDate(), false);
            //初始化 日结方式 execute
            List<ZuPinVehicleExecute> executeList = zuPinContactBalanceCalculateAndEditService.saveTiCheRiJieInitExecute(contact, selectModuleBrand, vehicleNumbers,comment,execute);
            //提车时还押金
            ZuPinContact zuPinContact1 = zuPinContactBalanceCalculateAndEditService.saveRepayRiJieYaJinInVehicleGet(contact,String.valueOf(tiChePiCi),executeList);
            zuPinContactDao.saveOrUpdate(zuPinContact1);
            zuPinContactDao.saveAll(executeList);
        }

    }


    @Override
    public boolean obtainDuplicateNotFinishVehicleOfContact(String vehicleNubmer, String zuPinContactUuid) {
        ZuPinContact zuPinContact = (ZuPinContact)zuPinContactDao.findByUuid(zuPinContactUuid,ZuPinContact.class);
        List<ZuPinVehicleExecute> executes = zuPinContact.getVehicleExecutes();
        for (ZuPinVehicleExecute execute : executes) {
            if(execute.isOver()){
                continue;
            }else if(execute.getVehicleNum().equals(vehicleNubmer)){
                return true;
            }
        }
        return false;
    }
}
