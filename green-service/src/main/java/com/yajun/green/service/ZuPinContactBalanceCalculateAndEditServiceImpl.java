package com.yajun.green.service;

import com.yajun.green.common.log.ApplicationLog;
import com.yajun.green.common.security.LoginInfo;
import com.yajun.green.common.utils.CHListUtils;
import com.yajun.green.common.utils.ContactLogUtil;
import com.yajun.green.common.utils.JodaUtils;
import com.yajun.green.domain.contact.*;
import com.yajun.green.domain.pay.PayOrderStatus;
import com.yajun.green.facade.assember.contact.ZuPinVehicleExecuteWebAssember;
import com.yajun.green.facade.dto.contact.ZuPinContactOverFormDTO;
import com.yajun.green.facade.dto.contact.ZuPinVehicleExecuteDTO;
import com.yajun.green.repository.ZuPinContactDao;
import com.yajun.green.repository.ZuPinHistoryDao;
import com.yajun.green.security.SecurityUtils;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiuMengKe on 2017/11/27.
 */
@Service("zuPinContactBalanceCalculateAndEditService")
public class ZuPinContactBalanceCalculateAndEditServiceImpl implements ZuPinContactBalanceCalculateAndEditService {
    @Autowired
    ZuPinContactDao zuPinContactDao;
    @Autowired
    ZuPinHistoryDao zuPinHistoryDao;

    /*********************************合同自动扣费部分*******************************/

    //获取扫描当日 到期应该生产账单的execute
    @Override
    public List<ZuPinVehicleExecuteDTO> obtainDaoQiExecute(LocalDate localDate, int term) {
        //当前时间加上term后 再去和后台的nextRentFee比对
        LocalDate newDate =  localDate.plusDays(term);
        List<ZuPinVehicleExecute> historyDTOList = zuPinHistoryDao.obtainReachNextRentFeeDateVehicle(newDate);
        return ZuPinVehicleExecuteWebAssember.toZuPinVehicleExecuteDTOList(historyDTOList);
    }


    //自动扣费计算
    @Override
    public void saveInSchedule(String zupinContactUuid, String tichePiCi) {
        ZuPinContact contact = (ZuPinContact) zuPinContactDao.findByUuid(zupinContactUuid, ZuPinContact.class);
        //自动租金计算
        ContactLogUtil.addActionLine(this.getClass(),"开始自动租金计算");
        List<ZuPinVehicleExecute> executes = saveRepayZuJinInScheduleModel(contact, tichePiCi);
        //自动押金计算
        ContactLogUtil.addActionLine(this.getClass(),"开始自动押金计算");
        saveRepayYaJinInScheduleModel(contact, tichePiCi);
        zuPinContactDao.saveOrUpdate(contact);
        zuPinContactDao.saveAll(executes);
    }

    //配合scheduler自动扣除租金
    public List<ZuPinVehicleExecute> saveRepayZuJinInScheduleModel(ZuPinContact contact, String tichePiCi) {
        ContactLogUtil.addActionLine(this.getClass(),"批次：" + tichePiCi + "租金开始扣费");
        List<ZuPinContactRentingFeeHistory> histories = new ArrayList<>();
        List<ZuPinVehicleExecute> matchExecutes = contact.getSpeUseFulTiChePiCi(Integer.valueOf(tichePiCi));
        for (ZuPinVehicleExecute execute : matchExecutes) {
            //如果是前付方式则此次最后一次时 today = nextfeedate = jieshudate 不用给押金
            String jieshuDate = execute.getJieshuDate().toString();
            String nextFeeDate = execute.getNextFeeDate().toString();
            boolean beforeModule = ZuPinContactRepayType.C_BEFORE.equals(execute.getZuPinContactRepayType());
            boolean afterModule = ZuPinContactRepayType.C_AFTER.equals(execute.getZuPinContactRepayType());

           /* //每月实际租金 前付类型或者 当后付模式的next已经累加到delaymonth退后到的nextfeedate的时候才对账户的租金进行操作
            boolean kouchu = afterModule && execute.getNextFeeDate().toString().equals(execute.getNextYajinDate().toString());*/

            //开始提车如9-10 延期2月    初始化execute  nextfeedate 11-10 actualfeedate 9-10  当执行这个schedule的时候 当前时候》=nextfeedate 的时候就可以执行
            boolean houFuFlag = JodaUtils.compareDays(execute.getNextFeeDate(), new LocalDate()) >= 0 ? true : false;
            boolean normalHf = afterModule && houFuFlag && execute.isOver() == false;

            ContactLogUtil.addActionLine(this.getClass(),"当前模式"+"参数1"+houFuFlag+"参数2"+beforeModule);

            if (!jieshuDate.equals(nextFeeDate)) {
                //excute相关
                LocalDate oldNextFeeDate = execute.getNextFeeDate();
                ContactLogUtil.addActionLine(this.getClass(),"当前nextfeedate"+oldNextFeeDate);
                if (normalHf) {
                    LocalDate newFeeDate = oldNextFeeDate.plusMonths(1);
                    LocalDate oldMessageDate = execute.getNextMessageDate();
                    LocalDate newMessageDate = oldMessageDate.plusMonths(1);
                    execute.setNextFeeDate(newFeeDate);
                    execute.setNextMessageDate(newMessageDate);
                }

                if (beforeModule) {
                    LocalDate newFeeDate = oldNextFeeDate.plusMonths(1);
                    LocalDate oldMessageDate = new LocalDate(execute.getNextMessageDate());
                    LocalDate newMessageDate = oldMessageDate.plusMonths(1);
                    execute.setNextFeeDate(newFeeDate);
                    execute.setNextMessageDate(newMessageDate);
                    execute.setNextYajinDate(execute.getNextYajinDate().plusMonths(1));
                }

                if (beforeModule || normalHf) {
                    ZuPinContactRentingFeeHistory history = buildScheduleRentFeeHistory(contact, execute, ZuPinContactRepayLocation.L_ZJ_SCHEDULE, beforeModule, execute.getActualRentFee());
                    ContactLogUtil.addActionLine(this.getClass(),"自动租金计算 账单生成 id："+history.getUuid()+"车牌号"+execute.getVehicleNum()+"备注"+history.getComment());
                    histories.add(history);
                }
            } else {
                ContactLogUtil.addActionLine(this.getClass(),"进入当前批次结束阶段"+"延迟月份"+execute.getDelayMonth()+"  nextfeedate"+execute.getNextFeeDate());
                //到最后合同结束的时候最后扫描的时候要扣除delayMonth个月的租金
                if (ZuPinContactRepayType.C_AFTER.equals(execute.getZuPinContactRepayType())) {
                    Integer delayMonth = execute.getDelayMonth();
                    for (Integer i = 0; i < delayMonth; i++) {
                        ZuPinContactRentingFeeHistory history = buildScheduleRentFeeHistory(contact, execute, ZuPinContactRepayLocation.L_ZJ_SCHEDULE, beforeModule, execute.getActualRentFee());
                        ContactLogUtil.addActionLine(this.getClass(),"结账阶段 自动租金计算 账单生成 id："+history.getUuid()+"车牌号"+execute.getVehicleNum()+"备注"+history.getComment());
                        histories.add(history);
                    }
                }

                //到最后合同结束的时候最后扫描的时候将所有未付款的押金状态修改
                //修改合同下所有欠款账单中 未付款押金部分为合同结束未付款
                zuPinContactDao.saveAll(contact.changeContactRentFeeHistoryYajinInScheduleOver());

                //暂定合同自动结束以后车辆不还原
                execute.setOver(true);
                execute.setOverButNotRevert(true);

                //判断当前合同下是否还有未结束的车辆 全部结束以后设置整个合同的状态为结束未还车
                contact.changeContactStautsInScheduleOver();

            }
        }

        zuPinContactDao.saveAll(histories);
        return matchExecutes;
    }


    //配合scheduler自动扣除押金
    public void saveRepayYaJinInScheduleModel(ZuPinContact contact, String tichePiCi) {
        ContactLogUtil.addActionLine(this.getClass(),"批次：" + tichePiCi + "押金开始扣费");
        List<ZuPinContactRentingFeeHistory> histories = new ArrayList<>();
        List<ZuPinVehicleExecute> matchExecutes = contact.getSpeUseFulTiChePiCi(Integer.valueOf(tichePiCi));

        for (ZuPinVehicleExecute execute : matchExecutes) {
            //车辆批车相同进行押金计算
            if (String.valueOf(execute.getTiChePiCi()).equals(tichePiCi)) {
                boolean beforeModule = ZuPinContactRepayType.C_BEFORE.equals(execute.getZuPinContactRepayType());

                //准备需要的数据
                ZuPinYaJinType zuPinYaJinType = execute.getZuPinYaJinType();
                //执行操作

                if (ZuPinYaJinType.Y_FENQI.equals(zuPinYaJinType)) {
                    //提车后haspaycishu 依然为0
                    Integer oldCiShu = execute.getYajinHasPayCishu();
                    //分期数小于租赁月份数 分期押金提前还完 必须人为控制实际还款的月份数 即开始缴费月份+延迟缴费月份数+分期还款月份数小于 批次结束月份
                    Integer fqs = Integer.valueOf(execute.getFenQiShu());
                    if (oldCiShu <= (fqs - 1)) {
                        ContactLogUtil.addActionLine(this.getClass(),"自动押金计算 入参 参数一"+(fqs-1)+"参数二"+oldCiShu);
                        //每台车的月供押金
                        BigDecimal yueGong = execute.getYueGong();

                        LocalDate oldMonth = execute.getActualyajinrepaymonth();
                        LocalDate newMonth = oldMonth.plusMonths(1);
                        Integer newCishu = oldCiShu + 1;
                        execute.setYajinHasPayCishu(newCishu);
                        execute.setActualyajinrepaymonth(newMonth);
                        //todo 条件2 将会在开始缴纳分期押金 但还没开始缴纳租金时推进 nextyajinmonth 向前 当开始缴纳租金的时候 由自动缴纳租金方法推动 这样写感觉不好 不分期的 被自动缴纳租金推着走 逻辑也不分离
                        //todo nextyajindate 将会在获取 historydao obtainReachNextRentFeeDateVehicle 方法中产生影响
                        if (beforeModule || !execute.getNextFeeDate().toString().equals(execute.getNextYajinDate().toString())) {
                            execute.setNextYajinDate(execute.getNextYajinDate().plusMonths(1));
                        }
                        zuPinContactDao.saveOrUpdate(execute);

                        String comment = "押金分期 （第" + newCishu + "期）";

                        ZuPinContactRentingFeeHistory rentingFeeHistory = buildHistoryObj(execute, ZuPinContactRepayLocation.L_YJ_SCHEDULE, newMonth, contact, yueGong, comment);
                        ContactLogUtil.addActionLine(this.getClass(),"自动押金计算 账单生成 id："+rentingFeeHistory.getUuid()+"车牌号"+execute.getVehicleNum()+"备注"+rentingFeeHistory.getComment());
                        histories.add(rentingFeeHistory);
                    } else if (oldCiShu > Integer.valueOf(execute.getFenQiShu())) {
                        ApplicationLog.error(ZuPinContactBalanceCalculateAndEditServiceImpl.class, "偿还次数大于分期数" + contact.getContactNumber());
                    }
                }
            }
        }

        //保存押金历史
        zuPinContactDao.saveAll(histories);
        //更新租金押金余额
        zuPinContactDao.saveOrUpdate(contact);
    }


    private ZuPinContactRentingFeeHistory buildScheduleRentFeeHistory(ZuPinContact contact, ZuPinVehicleExecute execute, ZuPinContactRepayLocation zuPinContactRepayLocation, boolean qianfuModule, BigDecimal newactualFee) {
        ZuPinContactRentingFeeHistory rentingFeeHistory = null;
        if (ZuPinContactRepayLocation.L_ZJ_SCHEDULE.equals(zuPinContactRepayLocation)) {

            String commment = "";
            ApplicationLog.info(ZuPinContactBalanceCalculateAndEditServiceImpl.class, "合同编号" + contact.getContactNumber() + "开始自动扣除租金");
            LocalDate newMonth = null;

            //前付模式 由于前付模式提车结束时actualdate 为tichedate   每次给的是下个月的钱  备注开始的时候从下月开始算
            if (qianfuModule) {
                LocalDate lastMonth = execute.getActualzujinrepaymonth();
                newMonth = lastMonth.plusMonths(1);
                execute.setActualzujinrepaymonth(newMonth);
                commment = "租金（时间范围：" + newMonth.toString("yyyy/MM/dd") + " - " + newMonth.plusMonths(1).plusDays(-1).toString("yyyy/MM/dd") + "）";
            }

            //后付模式由于 至少延误了一个月 所有每次都是用老大actualzjmonth 计算
            if (ZuPinContactRepayType.C_AFTER.equals(execute.getZuPinContactRepayType())) {
                newMonth = execute.getActualzujinrepaymonth();
                execute.setActualzujinrepaymonth(newMonth.plusMonths(1));
                commment = "租金（时间范围：" + newMonth.toString("yyyy/MM/dd") + " - " + execute.getActualzujinrepaymonth().plusDays(-1).toString("yyyy/MM/dd") + "）";
            }

            ApplicationLog.info(ZuPinContactBalanceCalculateAndEditServiceImpl.class, execute.getVehicleNum() + "租金schedule生成:" + newactualFee);

            rentingFeeHistory = buildHistoryObj(execute, zuPinContactRepayLocation, newMonth, contact, newactualFee, commment);
        }
        return rentingFeeHistory;
    }


    /*******************************************合同提车部分************************************/
    @Override
    public List<ZuPinVehicleExecute> saveTiCheInitExecute(ZuPinContact zuPinContact, String selectModuleBrand, String[] vehicleNumber, String[] comment, ZuPinVehicleExecuteDTO executeDTO) {
        LoginInfo loginInfo = SecurityUtils.currentLoginUser();

        ZuPinContact contact = zuPinContact;
        ZuPinVehicleModule module = contact.getOrigModule(selectModuleBrand);
        int totalNeedTiChe = contact.getTotalNeedTiChe();
        int totalAlreadyTiChe = contact.getTotalAlreadyTiChe();
        int alreadyTiChePiCi = contact.getAlreadyTiChePiCi();
        int nextTiChePiCi = alreadyTiChePiCi + 1;

        //初始化execute  actualFeedate 为tichedate  前付模式nextfeedate 为tichedate+1month 后付模式 nextfeedate 为 tichedate+delaymonth
        List<ZuPinVehicleExecute> executes = new ArrayList<>();
        for (int i = 0; i < vehicleNumber.length; i++) {
            if (StringUtils.hasText(vehicleNumber[i])) {
                ApplicationLog.infoWithCurrentUser(ZuPinContactBalanceCalculateAndEditServiceImpl.class, loginInfo.getUserUuid(), "提车" + vehicleNumber[i]);
                String tiCheDate = executeDTO.getTiCheDate();

                ZuPinVehicleExecute execute = new ZuPinVehicleExecute(loginInfo, module, vehicleNumber[i], nextTiChePiCi, tiCheDate, tiCheDate);

                LocalDate tcDate = new LocalDate(tiCheDate);

                if (ZuPinContactRepayType.C_BEFORE.equals(execute.getZuPinContactRepayType())) {
                    execute.setNextFeeDate(tcDate.plusMonths(1));
                    execute.setNextMessageDate(tcDate.plusMonths(1));
                    execute.setNextYajinDate(tcDate.plusMonths(1));
                }
                if (ZuPinContactRepayType.C_AFTER.equals(execute.getZuPinContactRepayType())) {
                    execute.setNextFeeDate(tcDate.plusMonths(execute.getDelayMonth()));
                    execute.setNextMessageDate(tcDate.plusMonths(execute.getDelayMonth()));
                    execute.setNextYajinDate(tcDate.plusMonths(1));
                }

                //设置备注 以及标记符号
                execute.updateComment(new LocalDate().toString(), comment[i]);

                //设置车辆结束状态为否
                execute.setOver(false);
                execute.setOverButNotRevert(false);
                execute.setActualzujinrepaymonth(tcDate);
                execute.setActualyajinrepaymonth(tcDate);

                //设置结束时间为提车时间+租赁月份
                LocalDate newJieShuDate = execute.getTiCheDate().plusMonths(execute.getRentMonth());
                execute.setJieshuDate(newJieShuDate);

                execute.setZuPinContactBaoYueType(ZuPinContactBaoYueType.B_BAOYUE);

                execute.setYajinHasPayCishu(0);
                execute.setZuPinContact(contact);

                ContactLogUtil.addActionLine(this.getClass(),"提车初始化execute参数 Actualyajinrepaymonth "+execute.getActualyajinrepaymonth()+"  Actualyajinrepaymonth"+execute.getActualyajinrepaymonth()+" NextFeeDate"+execute.getNextFeeDate()+" vehicleNumber"+execute.getVehicleNum()+"   ActualRentFee"+execute.getActualRentFee());

                executes.add(execute);
                //加入保证后面提车日期算术正确
                contact.addZuPinVehicleExecute(execute);
            }
        }

        //计算合同结束和完成没有
        int totalTiChe = totalAlreadyTiChe + executes.size();
        contact.beginExecute();
        if (totalNeedTiChe == totalTiChe) {
            contact.finishExecute();
        }
        return executes;
    }


    //提车时偿还首月租金
    @Override
    public ZuPinContact saveRepayZuJinInVehicleGet(ZuPinContact contact, String tichePiCi, List<ZuPinVehicleExecute> executeList) {
        List<ZuPinContactRentingFeeHistory> rentingFeeHistoryList = new ArrayList<>();
        for (ZuPinVehicleExecute execute : executeList) {

            boolean qianfuModule = ZuPinContactRepayType.C_BEFORE.equals(execute.getZuPinContactRepayType());
            BigDecimal newactualFee = execute.getActualRentFee();
            String vehicleNumber = execute.getVehicleNum();
            //前付模式才生产账单
            ZuPinContactRentingFeeHistory history = buidTiCheRentFeeHistory(contact, execute, ZuPinContactRepayLocation.L_ZJ_VEHICLE_SUB, qianfuModule, newactualFee, vehicleNumber);
            if (history != null) {
                rentingFeeHistoryList.add(history);
            }

        }
        if (CHListUtils.hasElement(rentingFeeHistoryList)) {
            zuPinContactDao.saveAll(rentingFeeHistoryList);
        }
        zuPinContactDao.saveAll(executeList);
        return contact;
    }

    //提车的时候还押金
    @Override
    public ZuPinContact saveRepayYaJinInVehicleGet(ZuPinContact contact, String tichePiCi, List<ZuPinVehicleExecute> executeList) {

        List<ZuPinContactRentingFeeHistory> list = new ArrayList<>();

        for (ZuPinVehicleExecute execute : executeList) {
            if (String.valueOf(execute.getTiChePiCi()).equals(tichePiCi)) {
                ZuPinYaJinType type = execute.getZuPinYaJinType();
                //全款扣总额 分期扣首付
                BigDecimal actualFee = null;
                String comment = "";
                if (ZuPinYaJinType.Y_QUANKUAN.name().equals(type.name())) {
                    actualFee = execute.getShouFu().add(execute.getSingleYaJin());
                    comment = "押金全款";
                }
                if (ZuPinYaJinType.Y_FENQI.name().equals(type.name())) {
                    actualFee = execute.getShouFu();
                    comment = "押金首付";
                }
                String vehicleNumber = execute.getVehicleNum();
                ApplicationLog.info(ZuPinContactBalanceCalculateAndEditServiceImpl.class, "合同编号" + contact.getContactNumber() + comment);

                //全款且押金金额为0的时候 不生成押金账单
                boolean isZeroYj = actualFee.compareTo(BigDecimal.ZERO)==0;

                if(ZuPinYaJinType.Y_QUANKUAN.equals(type)&&isZeroYj == true){
                    ApplicationLog.info(ZuPinContactBalanceCalculateAndEditServiceImpl.class, vehicleNumber + "押金为0     不生成提车押金");
                }else {
                    ZuPinContactRentingFeeHistory rentingFeeHistory = buildHistoryObj(execute, ZuPinContactRepayLocation.L_YJ_VEHICLE_SUB, execute.getTiCheDate(), contact, actualFee, comment);
                    setHistoryJiaoYiRen(rentingFeeHistory);
                    list.add(rentingFeeHistory);
                    ApplicationLog.info(ZuPinContactBalanceCalculateAndEditServiceImpl.class, vehicleNumber + "提车押金生成:" + actualFee);
                }

                LoginInfo info = SecurityUtils.currentLoginUser();
                ZuPinContactLog zuPinContactLog = new ZuPinContactLog(info.getUserUuid(),info.getXingMing(),"提车，备注:车牌号" + vehicleNumber ,contact);
                zuPinContactDao.saveOrUpdate(zuPinContactLog);
            }
        }
        zuPinContactDao.saveOrUpdate(contact);
        zuPinContactDao.saveAll(list);
        return contact;
    }




    /*********************************日结方式提车*******************************/

    @Override
    public List<ZuPinVehicleExecute> saveTiCheRiJieInitExecute(ZuPinContact contact, String selectModuleBrand, String[] vehicleNumber, String[] comment, ZuPinVehicleExecuteDTO executeDTO) {
        LoginInfo loginInfo = SecurityUtils.currentLoginUser();
        ZuPinVehicleModule module = contact.getOrigModule(selectModuleBrand);
        int totalNeedTiChe = contact.getTotalNeedTiChe();
        int totalAlreadyTiChe = contact.getTotalAlreadyTiChe();
        int alreadyTiChePiCi = contact.getAlreadyTiChePiCi();
        int nextTiChePiCi = alreadyTiChePiCi + 1;

        //计算提车过程 初始化 nex nextYaJinDate actualZujinMonth actualYaJinMonth 为当前提车时间
        //初始化execute 结束时间为提车时间+delayMonth
        List<ZuPinVehicleExecute> executes = new ArrayList<>();
        for (int i = 0; i < vehicleNumber.length; i++) {
            if (StringUtils.hasText(vehicleNumber[i])) {
                ApplicationLog.infoWithCurrentUser(ZuPinContactServiceImpl.class, loginInfo.getUserUuid(), "日结提车" + vehicleNumber[i]);
                String tiCheDate = executeDTO.getTiCheDate();

                ZuPinVehicleExecute execute = new ZuPinVehicleExecute(loginInfo, module, vehicleNumber[i], nextTiChePiCi, tiCheDate, tiCheDate);
                LocalDate tcDate = new LocalDate(tiCheDate);
                //设置为日结类型
                execute.setZuPinContactBaoYueType(ZuPinContactBaoYueType.B_RIJIE);

                //初始化execute  为租金后付类型 缓期0个月 押金为 全款类型  开始缴费时间为 提车时间 结束缴费时间 待定
                execute.setNextFeeDate(tcDate);
                execute.setNextMessageDate(tcDate);
                execute.setNextYajinDate(tcDate);
                execute.setActualzujinrepaymonth(tcDate);
                execute.setActualyajinrepaymonth(tcDate);
                //设置结束时间为提车时间+租赁月份
                execute.setJieshuDate(tcDate);

                //设置车辆结束状态为否
                execute.setOver(false);
                execute.setOverButNotRevert(false);

                //设置备注 以及标记符号
                execute.updateComment(new LocalDate().toString(), comment[i]);

                execute.setYajinHasPayCishu(0);
                execute.setZuPinContact(contact);


                ContactLogUtil.addActionLine(this.getClass(),"日结方式  提车初始化execute参数 Actualyajinrepaymonth "+execute.getActualyajinrepaymonth()+"  Actualyajinrepaymonth"+execute.getActualyajinrepaymonth()+" NextFeeDate"+execute.getNextFeeDate()+" vehicleNumber"+execute.getVehicleNum()+"   ActualRentFee"+execute.getActualRentFee());

                executes.add(execute);
                //加入保证后面提车日期算术正确
                contact.addZuPinVehicleExecute(execute);
            }
        }

        //计算合同结束和完成没有
        int totalTiChe = totalAlreadyTiChe + executes.size();
        contact.beginExecute();
        if (totalNeedTiChe == totalTiChe) {
            contact.finishExecute();
        }
        return executes;
    }


    //提车的时候还押金
    @Override
    public ZuPinContact saveRepayRiJieYaJinInVehicleGet(ZuPinContact contact, String tichePiCi, List<ZuPinVehicleExecute> executeList) {

        List<ZuPinContactRentingFeeHistory> list = new ArrayList<>();

        for (ZuPinVehicleExecute execute : executeList) {
            if (String.valueOf(execute.getTiChePiCi()).equals(tichePiCi)) {
                ZuPinYaJinType type = execute.getZuPinYaJinType();
                //全款扣总额 分期扣首付
                BigDecimal actualFee = null;
                String comment = "";
                if (ZuPinYaJinType.Y_QUANKUAN.equals(type)) {
                    actualFee = execute.getShouFu().add(execute.getSingleYaJin());
                    comment = "押金全款";
                }
                if (ZuPinYaJinType.Y_FENQI.equals(type)) {
                    actualFee = execute.getShouFu();
                    comment = "押金首付";
                }
                String vehicleNumber = execute.getVehicleNum();
                ApplicationLog.info(ZuPinContactBalanceCalculateAndEditServiceImpl.class, "合同编号" + contact.getContactNumber() + comment);

                boolean isZeroYj = actualFee.compareTo(BigDecimal.ZERO)==0;
                if(ZuPinYaJinType.Y_QUANKUAN.equals(type)&&isZeroYj == true){
                    ApplicationLog.info(ZuPinContactBalanceCalculateAndEditServiceImpl.class, vehicleNumber + "押金为0     不生成提车押金");
                }else {
                    ZuPinContactRentingFeeHistory rentingFeeHistory = buildHistoryObj(execute, ZuPinContactRepayLocation.L_YJ_VEHICLE_SUB, execute.getTiCheDate(), contact, actualFee, comment);

                    //日结时将押金的开始时间设置为提车时间
                    rentingFeeHistory.setActualFeeDate(execute.getTiCheDate());
                    setHistoryJiaoYiRen(rentingFeeHistory);
                    list.add(rentingFeeHistory);
                    ApplicationLog.info(ZuPinContactBalanceCalculateAndEditServiceImpl.class, vehicleNumber + "日结方式 提车押金生成:" + actualFee);
                }


                //合同日志记录提车
                LoginInfo info = SecurityUtils.currentLoginUser();
                ZuPinContactLog zuPinContactLog = new ZuPinContactLog(info.getUserUuid(),info.getXingMing(),"提车，备注:车牌号" + vehicleNumber ,contact);
                zuPinContactDao.saveOrUpdate(zuPinContactLog);

            }
        }
        zuPinContactDao.saveOrUpdate(contact);
        zuPinContactDao.saveAll(list);
        return contact;
    }


    @Override
    public void saveZuPinContactAdditionBalance(ZuPinContactOverFormDTO zuPinContactOverFormDTO) {
        LoginInfo loginInfo = SecurityUtils.currentLoginUser();
        ZuPinContact contact = (ZuPinContact) zuPinContactDao.findByUuid(zuPinContactOverFormDTO.getZuPinContactUuid(), ZuPinContact.class);
        //金额 备注 类型
        String[] overformcount = zuPinContactOverFormDTO.getOverformcount();
        String[] comment = zuPinContactOverFormDTO.getComment();
        String[] finishCharge = zuPinContactOverFormDTO.getFinishCharge();

        //其他费用
        if (finishCharge != null && finishCharge.length > 0) {
            for (int i = 0; i < finishCharge.length; i++) {
                boolean tianjia = true;
                if ("SHOU_QU".equals(finishCharge[i])) {
                    tianjia = false;
                }
                ZuPinContactRentingFeeHistory zuPinContactRentingFeeHistory = new ZuPinContactRentingFeeHistory(contact.getContactNumber(), null, new LocalDate(), new BigDecimal(overformcount[i]), contact, 0, loginInfo.getXingMing(), loginInfo.getUserUuid(), tianjia, comment[i]);

                if (!tianjia) {
                    ApplicationLog.info(ZuPinContactServiceImpl.class, "合同附加款项收取" + overformcount[i]);
                    zuPinContactRentingFeeHistory.setZuPinContactRepayLocation(ZuPinContactRepayLocation.L_FJ_IN);
                    zuPinContactRentingFeeHistory.setStatus(PayOrderStatus.O_CREATE);
                } else {
                    ApplicationLog.info(ZuPinContactServiceImpl.class, "合同附加款项支出" + overformcount[i]);
                    zuPinContactRentingFeeHistory.setZuPinContactRepayLocation(ZuPinContactRepayLocation.L_FJ_OUT);
                    //押金等退换 线下操作已经完成 直接设置为已经支付
                    zuPinContactRentingFeeHistory.setStatus(PayOrderStatus.O_CREATE);
                    zuPinContactRentingFeeHistory.setFeeTotal(new BigDecimal(overformcount[i]).multiply(new BigDecimal(-1)));
                }
                zuPinContactRentingFeeHistory.setActualFeeDate(new LocalDate());
                zuPinContactDao.saveOrUpdate(zuPinContactRentingFeeHistory);
            }
        }
    }

    private ZuPinContactRentingFeeHistory buidTiCheRentFeeHistory(ZuPinContact contact, ZuPinVehicleExecute execute, ZuPinContactRepayLocation zuPinContactRepayLocation, boolean qianfuModule, BigDecimal newactualFee, String vehicleNumber) {
        if (qianfuModule && ZuPinContactRepayLocation.L_ZJ_VEHICLE_SUB.equals(zuPinContactRepayLocation)) {

            ApplicationLog.info(ZuPinContactBalanceCalculateAndEditServiceImpl.class, "合同编号" + contact.getContactNumber() + "提车自动扣除租金");

            String commment = "租金（时间范围：" + execute.getActualzujinrepaymonth().toString("yyyy/MM/dd") + " - " + execute.getActualzujinrepaymonth().plusMonths(1).plusDays(-1).toString("yyyy/MM/dd") + "）";

            ZuPinContactRentingFeeHistory rentingFeeHistory = buildHistoryObj(execute, zuPinContactRepayLocation, execute.getTiCheDate(), contact, newactualFee, commment);

            setHistoryJiaoYiRen(rentingFeeHistory);

            ApplicationLog.info(ZuPinContactBalanceCalculateAndEditServiceImpl.class, vehicleNumber + "提车租金生成:" + newactualFee);

            return rentingFeeHistory;
        }
        return null;
    }

    private ZuPinContactRentingFeeHistory buildHistoryObj(ZuPinVehicleExecute execute, ZuPinContactRepayLocation zuPinContactRepayLocation, LocalDate actualDate, ZuPinContact zuPinContact, BigDecimal feeTotal, String comment) {

        String contactNumber = zuPinContact.getContactNumber();
        String jiaoYiRen = "系统";
        String jiaoYiRenUuid = "sys_id";

        String vehicleNumber = execute.getVehicleNum();
        LocalDate happendDate = new LocalDate();
        Integer tichePiCi = execute.getTiChePiCi();

        ZuPinContactRentingFeeHistory rentingFeeHistory = new ZuPinContactRentingFeeHistory(contactNumber, vehicleNumber, happendDate, feeTotal, zuPinContact, tichePiCi, jiaoYiRen, jiaoYiRenUuid, false, comment);
        //提车时初始化actualFeedate 为提车时间
        rentingFeeHistory.setActualFeeDate(actualDate);
        rentingFeeHistory.setActualFeeDateEnd(actualDate.plusMonths(1).plusDays(-1));
        rentingFeeHistory.setZuPinContactRepayLocation(zuPinContactRepayLocation);

        return rentingFeeHistory;
    }


    private void setHistoryJiaoYiRen(ZuPinContactRentingFeeHistory rentingFeeHistory) {
        LoginInfo loginInfo = SecurityUtils.currentLoginUser();
        String jiaoYiRen = loginInfo.getXingMing();
        String jiaoYiRenUuid = loginInfo.getUserUuid();
        rentingFeeHistory.setJiaoYiRen(jiaoYiRen);
        rentingFeeHistory.setJiaoYiRenUuid(jiaoYiRenUuid);
    }

}
