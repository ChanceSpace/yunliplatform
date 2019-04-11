package com.yajun.green.service;

import com.yajun.green.common.log.ApplicationLog;
import com.yajun.green.common.security.LoginInfo;
import com.yajun.green.common.utils.CHListUtils;
import com.yajun.green.common.utils.ContactLogUtil;
import com.yajun.green.common.utils.JodaUtils;
import com.yajun.green.domain.contact.*;
import com.yajun.green.domain.pay.PayOrderStatus;
import com.yajun.green.facade.assember.contact.ZuPinContactRentingFeeHistoryWebAssember;
import com.yajun.green.facade.dto.contact.ZuPinContactOverFormDTO;
import com.yajun.green.facade.dto.contact.ZuPinContactRentingFeeHistoryDTO;
import com.yajun.green.facade.dto.contact.ZuPinVehicleExecuteDTO;
import com.yajun.green.repository.ZuPinContactDao;
import com.yajun.green.security.SecurityUtils;
import com.yajun.vms.service.VmsDubboService;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiuMengKe on 2017/11/29.
 */
@Service("zuPinContactAndVehicleOverService")
public class ZuPinContactAndVehicleOverServiceImpl implements ZuPinContactAndVehicleOverService {
    @Autowired
    private ZuPinContactDao zuPinContactDao;
    @Autowired
    private VmsDubboService vmsDubboService;
    /*********************************合同 车辆修改 1.车辆提前结束租赁 2.换车*******************************/
    @Override
    public void saveOverZuPinVehicle(String zuPinContactUuid, String tiChePiCi, String vehicleNum, String finishDate, String comment) throws Exception {
        ContactLogUtil.addLineAndLog(this.getClass(), "单车结束租赁    合同id:" + zuPinContactUuid + "   批次" + tiChePiCi + " 车牌号" + vehicleNum + "开始结束车辆");
        ZuPinContact zuPinContact = (ZuPinContact) zuPinContactDao.findByUuid(zuPinContactUuid, ZuPinContact.class);
        ZuPinVehicleExecute execute = zuPinContactDao.obtainSpecZuPinContactVehicleExcuteDTO(zuPinContactUuid, tiChePiCi, vehicleNum);
        execute.setOver(true);
        execute.setJieshuDate(new LocalDate(finishDate));
        ZuPinContact contact1 = (ZuPinContact) zuPinContactDao.findByUuid(zuPinContactUuid, ZuPinContact.class);

        //修改车辆状态
        vmsDubboService.saveYiBuVehicleOverRevert(vehicleNum, contact1.getJiaFangUuid(),contact1.getJiaFangName(),  contact1.getYiFangUuid(), contact1.getYiFangName(), finishDate);

        //后付类型进行租金交款处理
        saveTiqianJieShuZuLin(execute, new LocalDate(finishDate), zuPinContactUuid);

        execute.updateComment(new LocalDate().toString(), comment);
        execute.setPiciOver(true);
        execute.setOver(true);
        execute.setJieshuDate(new LocalDate(finishDate));

        //保存租赁状态变更
        zuPinContactDao.saveOrUpdate(execute);

        //修改未支付押金状态
        //修改合同下所有欠款账单中 当前结束车辆的 押金全部为 合同结束未付款状态
        List<ZuPinContactRentingFeeHistory> histories = zuPinContact.getUnPayBill();
        for (ZuPinContactRentingFeeHistory history : histories) {
            ZuPinContactRepayLocation location = history.getZuPinContactRepayLocation();
            //未支付押金状态处理
            if (location.contactOverCheckYaJin() && history.getVehicleNumber().equals(vehicleNum)) {
                history.setStatus(PayOrderStatus.O_ABANDON);
                ContactLogUtil.addShortLine(this.getClass(), "单车结束租赁 history uuid" + history.getUuid() + "车牌号" + history.getVehicleNumber() + "置未不处理");
            }
        }
        zuPinContactDao.saveAll(histories);

        //合同记录中记录车辆停止租赁信息
        LoginInfo loginInfo = SecurityUtils.currentLoginUser();
        String description = "车辆" + vehicleNum + "选择日期" + finishDate + "变更为停止租赁状态";
        ZuPinContact contact = new ZuPinContact();
        contact.setUuid(zuPinContactUuid);
        ZuPinContactLog log = new ZuPinContactLog(loginInfo.getUserUuid(), loginInfo.getXingMing(), description, contact);
        zuPinContactDao.saveOrUpdate(log);

        ContactLogUtil.addLineAndLog(this.getClass(), "单车结束租赁   合同id:" + zuPinContactUuid + "   批次" + tiChePiCi + " 车牌号" + vehicleNum + "结束车辆完成");
    }

    @Override
    public List<ZuPinContactRentingFeeHistoryDTO> obtainNoPayHistoryList(String zuPinContactUuid) {
        ZuPinContact contact = (ZuPinContact) zuPinContactDao.findByUuid(zuPinContactUuid, ZuPinContact.class);
        List<ZuPinContactRentingFeeHistory> rentingFeeHistories = contact.getUnPayBill();
        return ZuPinContactRentingFeeHistoryWebAssember.toRentFeeHistoryDTOList(rentingFeeHistories);
    }


    /**
     * @Description: 合同结束 根据默认时间 或者 页面选择时间 生成后付模式没有被自动扫描到的账单
     */
    @Override
    public List<ZuPinContactRentingFeeHistoryDTO> obtainZupinContactFinishNoPayZuJinList(String zuPinContactUuid, LocalDate finishDate) {
        List<ZuPinContactRentingFeeHistory> rentingFeeHistories = obtainZupinContactFinishNoPayZuJinHistoryList(zuPinContactUuid, finishDate);
        return ZuPinContactRentingFeeHistoryWebAssember.toRentFeeHistoryDTOList(rentingFeeHistories);
    }

    @Override
    public LocalDate obtainZuPinContactMaxTicheDate(String zuPinContactUuid) {
        ZuPinContact zuPinContact = (ZuPinContact) zuPinContactDao.findByUuid(zuPinContactUuid, ZuPinContact.class);
        List<ZuPinVehicleExecute> executes = zuPinContact.getSpeUseFulExecute();

        LocalDate maxDate = null;
        //取所有未结束的excute 中 最大提车日期
        for (ZuPinVehicleExecute execute : executes) {
            LocalDate ticheDate = execute.getTiCheDate();
            if (maxDate == null) {
                maxDate = ticheDate;
            } else {
                maxDate = JodaUtils.compareDays(maxDate, ticheDate) >= 0 ? ticheDate : maxDate;
            }
        }

        return maxDate;
    }

    //结束整个合同
    @Override
    public void updateZuPinContactFinish(List<ZuPinVehicleExecuteDTO> executeDTOS, ZuPinContactOverFormDTO zuPinContactOverFormDTO) {
        LoginInfo loginInfo = SecurityUtils.currentLoginUser();

        String zuPinContactUuid = zuPinContactOverFormDTO.getZuPinContactUuid();

        ContactLogUtil.addLineAndLog(this.getClass(), "合同 id:" + zuPinContactUuid + "合同结束 开始");

        String finishDate = zuPinContactOverFormDTO.getFinishDate();
        ZuPinContact contact = (ZuPinContact) zuPinContactDao.findByUuid(zuPinContactUuid, ZuPinContact.class);

        for (ZuPinVehicleExecuteDTO executeDTO : executeDTOS) {
            ApplicationLog.infoWithCurrentUser(ZuPinContactServiceImpl.class, loginInfo.getUserUuid(), "合同结束还原车辆" + executeDTO.getVehicleNum() + "为初始状态");
            vmsDubboService.saveYiBuVehicleOverRevert(executeDTO.getVehicleNum(), contact.getJiaFangUuid(), contact.getJiaFangName(), contact.getYiFangUuid(), contact.getYiFangName(), finishDate);
        }
        //金额 备注 类型
        String[] overformcount = zuPinContactOverFormDTO.getOverformcount();
        String[] comment = zuPinContactOverFormDTO.getComment();
        String[] finishCharge = zuPinContactOverFormDTO.getFinishCharge();

        ApplicationLog.infoWithCurrentUser(ZuPinContactServiceImpl.class, loginInfo.getUserUuid(), "合同结束 当前合同押金已经支付" + contact.getYajinHasPay());


        //生成 后付模式时 还未自动生成的订单
        List<ZuPinContactRentingFeeHistory> unCreateBills = obtainZupinContactFinishNoPayZuJinHistoryList(zuPinContactUuid, new LocalDate(finishDate));
        zuPinContactDao.saveAll(unCreateBills);

        //已经缴纳押金部分退款 历史记录
        if (contact.getYajinHasPay() != null && contact.getYajinHasPay().compareTo(new BigDecimal(0)) > 0) {
            ZuPinContactRentingFeeHistory zujinTuiHuan = new ZuPinContactRentingFeeHistory(contact.getContactNumber(), null, new LocalDate(), contact.getYajinHasPay().multiply(new BigDecimal(-1)), contact, 0, loginInfo.getXingMing(), loginInfo.getUserUuid(), false, "合同结束后还押金");
            zujinTuiHuan.setZuPinContactRepayLocation(ZuPinContactRepayLocation.L_JS_OUT);
            zujinTuiHuan.setActualFeeDate(new LocalDate());
            zujinTuiHuan.setStatus(PayOrderStatus.O_CREATE);
            ApplicationLog.info(ZuPinContactServiceImpl.class, "合同id："+zuPinContactUuid+"生成 结算押金退款" + contact.getYajinHasPay());
            zuPinContactDao.saveOrUpdate(zujinTuiHuan);
        }


        //修改合同下所有欠款账单中 未付款押金部分为合同结束未付款
        List<ZuPinContactRentingFeeHistory> histories = contact.getUnPayBill();
        for (ZuPinContactRentingFeeHistory history : histories) {
            ZuPinContactRepayLocation location = history.getZuPinContactRepayLocation();
            //未支付押金状态处理
            if (location.contactOverCheckYaJin()) {
                history.setStatus(PayOrderStatus.O_ABANDON);
                ContactLogUtil.addActionLine(this.getClass(),"history  uuid:"+history.getUuid()+"车牌号"+history.getVehicleNumber()+"置为不处理");
            }
        }
        zuPinContactDao.saveAll(histories);

        //其他费用
        if (finishCharge != null && finishCharge.length > 0) {
            for (int i = 0; i < finishCharge.length; i++) {
                boolean tianjia = true;
                if ("SHOU_QU".equals(finishCharge[i])) {
                    tianjia = false;
                }
                ZuPinContactRentingFeeHistory zuPinContactRentingFeeHistory = new ZuPinContactRentingFeeHistory(contact.getContactNumber(), null, new LocalDate(), new BigDecimal(overformcount[i]), contact, 0, loginInfo.getXingMing(), loginInfo.getUserUuid(), tianjia, comment[i]);

                if (!tianjia) {
                    ApplicationLog.info(ZuPinContactServiceImpl.class, "合同结算其他费用项收取" + overformcount[i]);
                    zuPinContactRentingFeeHistory.setZuPinContactRepayLocation(ZuPinContactRepayLocation.L_JS_IN);
                    zuPinContactRentingFeeHistory.setStatus(PayOrderStatus.O_CREATE);
                } else {
                    ApplicationLog.info(ZuPinContactServiceImpl.class, "合同结算其他费用项支出" + overformcount[i]);
                    zuPinContactRentingFeeHistory.setZuPinContactRepayLocation(ZuPinContactRepayLocation.L_JS_OUT);
                    //押金等退换 线下操作已经完成 直接设置为已经支付
                    zuPinContactRentingFeeHistory.setStatus(PayOrderStatus.O_CREATE);
                    zuPinContactRentingFeeHistory.setFeeTotal(new BigDecimal(overformcount[i]).multiply(new BigDecimal(-1)));
                }
                zuPinContactRentingFeeHistory.setActualFeeDate(new LocalDate(finishDate));
                zuPinContactDao.saveOrUpdate(zuPinContactRentingFeeHistory);
            }
        }

        ApplicationLog.infoWithCurrentUser(ZuPinContactServiceImpl.class, loginInfo.getUserUuid(), "合同结束 缴纳其他费用" + finishCharge);
        //批次结束等
        ZuPinContactLog log = contact.finishContact(loginInfo, finishDate);
        zuPinContactDao.saveOrUpdate(log);

        contact.setEndDate(new LocalDate(finishDate));
        contact.setEndExecute(true);
        zuPinContactDao.saveOrUpdate(contact);

        ContactLogUtil.addLineAndLog(this.getClass(), "合同 id:" + zuPinContactUuid + "合同结束 完成");
    }


    @Override
    public List<ZuPinContactRentingFeeHistoryDTO> obtainZuPinContactSpecVehicleDTOList(String zuPinContactUuid, String executeUuid, String vehicleNum, LocalDate finishDate) {
        List<ZuPinContactRentingFeeHistory> histories = obtainZuPinContactSpecVehicleList(zuPinContactUuid, executeUuid, vehicleNum, finishDate);
        if(CHListUtils.hasElement(histories)){
            return ZuPinContactRentingFeeHistoryWebAssember.toRentFeeHistoryDTOList(histories);
        }
        return null;
    }

    //根据合同id excuteid 和 vehicleNumber 结合结束时间 计算单车结束的时候应该付多少钱
    @Override
    public List<ZuPinContactRentingFeeHistory> obtainZuPinContactSpecVehicleList(String zuPinContactUuid, String executeUuid, String vehicleNum, LocalDate finishDate) {
        LoginInfo loginInfo = SecurityUtils.currentLoginUser();
        ZuPinContact zuPinContact = (ZuPinContact) zuPinContactDao.findByUuid(zuPinContactUuid, ZuPinContact.class);
        ZuPinVehicleModule module = zuPinContact.getOrigModules().get(0);
        List<ZuPinContactRentingFeeHistory> rentingFeeHistories = new ArrayList<>();

        ZuPinVehicleExecute execute = (ZuPinVehicleExecute)zuPinContactDao.findByUuid(executeUuid,ZuPinVehicleExecute.class);

        // 1.遍历所有的execute   找到所有 execute actualzujinmonth < jieshu date
        // 2.根据结束时间 和 excute actual 时间间隔 循环生成 rentfeehistory
        // 3.根据车牌号生成 账单页面 不保存
        ZuPinContactRepayType type = module.getZuPinContactRepayType();
        if (ZuPinContactRepayType.C_AFTER.equals(type)) {
            List<ZuPinVehicleExecute> executeList = zuPinContact.getSpeUseFulExecute();
            LocalDate jiesuanDate = new LocalDate(finishDate);
                //提车当日结束不计算
                if (execute.getTiCheDate().toString().equals(jiesuanDate.toString())) {
                    return rentingFeeHistories;
                }
                /*LocalDate actualFeeDate = execute.getActualzujinrepaymonth();
                int actualFeeDateYear = actualFeeDate.getYear();
                int jieshuYear = jiesuanDate.getYear();

                int actualFeeDateMonth = actualFeeDate.getMonthOfYear();
                int jieshuMonth = jiesuanDate.getMonthOfYear();

                int actualDay = actualFeeDate.getDayOfMonth();
                int jieshuDay = jiesuanDate.getDayOfMonth();

                //计算间隔月份数
                int fujia = jieshuDay >= actualDay ? 1 : 0;
                int jiangeMonth = (jieshuYear - actualFeeDateYear) * 12 + (jieshuMonth - actualFeeDateMonth) + fujia;*/

                int jiangeMonth = execute.getIntervalMonthFromActualZjDate(jiesuanDate);

                if (jiangeMonth < 0) {
                    //todo 抛出异常
                    ApplicationLog.error(ZuPinContactServiceImpl.class, "结束时间错误 结束时间小于 当前批次已经缴纳月份时间  合同id：" + zuPinContactUuid + "批次：" + execute.getTiChePiCi());
                }

                for (Integer i = 0; i < jiangeMonth; i++) {
                    System.out.println("批次：" + execute.getTiChePiCi() + "车牌号" + execute.getVehicleNum());
                    String commment = "租金（时间范围：" + execute.getActualzujinrepaymonth().plusMonths(i).toString("yyyy/MM/dd") + " - " + execute.getActualzujinrepaymonth().plusMonths(i + 1).plusDays(-1).toString("yyyy/MM/dd") + "）";
                    ApplicationLog.info(ZuPinContactServiceImpl.class, commment);
                    ZuPinContactRentingFeeHistory zuPinContactRentingFeeHistory = new ZuPinContactRentingFeeHistory(zuPinContact.getContactNumber(), execute.getVehicleNum(), new LocalDate(), execute.getActualRentFee(), zuPinContact, 0, loginInfo.getXingMing(), loginInfo.getUserUuid(), false, commment);
                    zuPinContactRentingFeeHistory.setZuPinContactRepayLocation(ZuPinContactRepayLocation.L_JS_IN);
                    zuPinContactRentingFeeHistory.setStatus(PayOrderStatus.O_CREATE);
                    ApplicationLog.info(ZuPinContactServiceImpl.class, "before============" + execute.getActualzujinrepaymonth());
                    zuPinContactRentingFeeHistory.setActualFeeDate(execute.getActualzujinrepaymonth().plusMonths(i));
                    ApplicationLog.info(ZuPinContactServiceImpl.class, "after============" + execute.getActualzujinrepaymonth());
                    zuPinContactRentingFeeHistory.setZuPinYaJinType(execute.getZuPinYaJinType());
                    zuPinContactRentingFeeHistory.setYueGong(execute.getYueGong());
                    zuPinContactRentingFeeHistory.setTichePiCi(execute.getTiChePiCi());
                    rentingFeeHistories.add(zuPinContactRentingFeeHistory);
                }
        } else {
            LocalDate jiesuanDate = new LocalDate(finishDate);
            //为了防止前付模式 在断电后出错没有通过schedule计算出来
            LocalDate nextFeeDate = execute.getNextFeeDate();
            int intervaldays = JodaUtils.compareDays(jiesuanDate, nextFeeDate);
            int month = execute.getRentMonth();
            LocalDate jieshuDate = execute.getTiCheDate().plusMonths(month);
            boolean contactOver = jieshuDate.toString().equals(nextFeeDate.toString());
            if (intervaldays <= 0 && contactOver == false) {
                throw new RuntimeException("合同id：" + zuPinContactUuid + "结束异常" + "前付模式下提前结束合同 结束时间小于实际缴纳月份时间");
            }

        }
        return rentingFeeHistories;
    }

    //根据合同id  结合结束时间 计算合同结束的时候应该付多少钱
    private List<ZuPinContactRentingFeeHistory> obtainZupinContactFinishNoPayZuJinHistoryList(String zuPinContactUuid, LocalDate finishDate) {
        LoginInfo loginInfo = SecurityUtils.currentLoginUser();

        ZuPinContact zuPinContact = (ZuPinContact) zuPinContactDao.findByUuid(zuPinContactUuid, ZuPinContact.class);
        ZuPinVehicleModule module = zuPinContact.getOrigModules().get(0);
        List<ZuPinContactRentingFeeHistory> rentingFeeHistories = new ArrayList<>();
        // 1.遍历所有的execute   找到所有 execute actualzujinmonth < jieshu date
        // 2.根据结束时间 和 excute actual 时间间隔 循环生成 rentfeehistory
        // 3.根据车牌号生成 账单页面 不保存
        ZuPinContactRepayType type = module.getZuPinContactRepayType();
        if (ZuPinContactRepayType.C_AFTER.equals(type)) {
            List<ZuPinVehicleExecute> executeList = zuPinContact.getSpeUseFulExecute();
            LocalDate jiesuanDate = new LocalDate(finishDate);
            for (ZuPinVehicleExecute execute : executeList) {
                //提车当日结束不计算
                if (execute.getTiCheDate().toString().equals(jiesuanDate.toString())) {
                    continue;
                }
                /*LocalDate actualFeeDate = execute.getActualzujinrepaymonth();
                int actualFeeDateYear = actualFeeDate.getYear();
                int jieshuYear = jiesuanDate.getYear();

                int actualFeeDateMonth = actualFeeDate.getMonthOfYear();
                int jieshuMonth = jiesuanDate.getMonthOfYear();

                int actualDay = actualFeeDate.getDayOfMonth();
                int jieshuDay = jiesuanDate.getDayOfMonth();

                //计算间隔月份数
                int fujia = jieshuDay >= actualDay ? 1 : 0;
                int jiangeMonth = (jieshuYear - actualFeeDateYear) * 12 + (jieshuMonth - actualFeeDateMonth) + fujia;*/

                int jiangeMonth = execute.getIntervalMonthFromActualZjDate(jiesuanDate);
                //小的情况 分为当月和不是当月
                if (jiangeMonth <0) {
                    //todo 抛出异常
                    ApplicationLog.error(ZuPinContactServiceImpl.class, "结束时间错误 结束时间小于 当前批次已经缴纳月份时间  合同id：" + zuPinContactUuid + "批次：" + execute.getTiChePiCi());
                }

                for (Integer i = 0; i < jiangeMonth; i++) {
                    System.out.println("批次：" + execute.getTiChePiCi() + "车牌号" + execute.getVehicleNum());
                    String commment = "租金（时间范围：" + execute.getActualzujinrepaymonth().plusMonths(i).toString("yyyy/MM/dd") + " - " + execute.getActualzujinrepaymonth().plusMonths(i + 1).plusDays(-1).toString("yyyy/MM/dd") + "）";
                    ApplicationLog.info(ZuPinContactServiceImpl.class, commment);
                    ZuPinContactRentingFeeHistory zuPinContactRentingFeeHistory = new ZuPinContactRentingFeeHistory(zuPinContact.getContactNumber(), execute.getVehicleNum(), new LocalDate(), execute.getActualRentFee(), zuPinContact, 0, loginInfo.getXingMing(), loginInfo.getUserUuid(), false, commment);
                    zuPinContactRentingFeeHistory.setZuPinContactRepayLocation(ZuPinContactRepayLocation.L_JS_IN);
                    zuPinContactRentingFeeHistory.setStatus(PayOrderStatus.O_CREATE);
                    ApplicationLog.info(ZuPinContactServiceImpl.class, "before============" + execute.getActualzujinrepaymonth());
                    zuPinContactRentingFeeHistory.setActualFeeDate(execute.getActualzujinrepaymonth().plusMonths(i));
                    ApplicationLog.info(ZuPinContactServiceImpl.class, "after============" + execute.getActualzujinrepaymonth());
                    zuPinContactRentingFeeHistory.setZuPinYaJinType(execute.getZuPinYaJinType());
                    zuPinContactRentingFeeHistory.setYueGong(execute.getYueGong());
                    zuPinContactRentingFeeHistory.setTichePiCi(execute.getTiChePiCi());
                    rentingFeeHistories.add(zuPinContactRentingFeeHistory);
                }

            }
        } else {
            List<ZuPinVehicleExecute> executeList = zuPinContact.getSpeUseFulExecute();
            LocalDate jiesuanDate = new LocalDate(finishDate);
            for (ZuPinVehicleExecute execute : executeList) {
                //为了防止前付模式 在断电后出错没有通过schedule计算出来
                LocalDate nextFeeDate = execute.getNextFeeDate();
                int intervaldays = JodaUtils.compareDays(jiesuanDate, nextFeeDate);
                int month = execute.getRentMonth();
                LocalDate jieshuDate = execute.getTiCheDate().plusMonths(month);
                boolean contactOver = jieshuDate.toString().equals(nextFeeDate.toString());
                if (intervaldays <= 0 && contactOver == false) {
                    throw new RuntimeException("合同id：" + zuPinContactUuid + "结束异常" + "前付模式下提前结束合同 结束时间小于实际缴纳月份时间");
                }
            }
        }
        return rentingFeeHistories;
    }


    public List<ZuPinContactRentingFeeHistory> obtainRiJieFinishNoPayZuJinList(String zuPinContactUuid, LocalDate finishDate) throws Exception{
        //1.获取所有没结束的execute 2.生成history 结束时间 是finishDate
        LoginInfo loginInfo = SecurityUtils.currentLoginUser();
        ZuPinContact zuPinContact = (ZuPinContact) zuPinContactDao.findByUuid(zuPinContactUuid, ZuPinContact.class);
        List<ZuPinVehicleExecute> notOverExecutes = zuPinContact.getSpeUseFulExecute();
        List<ZuPinContactRentingFeeHistory> histories= new ArrayList<>();
        for (ZuPinVehicleExecute execute : notOverExecutes) {
            LocalDate ticheDate = execute.getTiCheDate();
            int dayInteval = JodaUtils.calculateDays(ticheDate,finishDate)+1;
            BigDecimal feetotal = execute.getActualRentFee().multiply(new BigDecimal(dayInteval));
            ZuPinContactRentingFeeHistory history = new ZuPinContactRentingFeeHistory(zuPinContact.getContactNumber(),execute.getVehicleNum(),ticheDate,feetotal,zuPinContact,execute.getTiChePiCi(),loginInfo.getXingMing(),loginInfo.getUserUuid(),false,"");

            history.setComment("日结车辆提车时间："+execute.getTiCheDate()+"     合同结束时间"+finishDate+"        每天租金"+execute.getActualRentFee()+"      共租赁"+dayInteval+"天"+"       合计 ："+feetotal+"元");

            //现在使用 actualdate 作为 提车的开始时间
            history.setActualFeeDate(execute.getTiCheDate());
            history.setActualFeeDateEnd(finishDate);
            history.setZuPinYaJinType(ZuPinYaJinType.Y_QUANKUAN);
            history.setShouFu(execute.getShouFu());
            history.setYueGong(new BigDecimal(0));
            history.setFenQiShu("0");
            history.setZuPinContactRepayLocation(ZuPinContactRepayLocation.L_ZJ_SCHEDULE);

            histories.add(history);

        }
        return histories;
    }

    //单车类型提前还车
    public void saveTiqianJieShuZuLin(ZuPinVehicleExecute zuPinVehicleExecute, LocalDate finishDate, String zuPinContactUuid) {
        boolean afterModule = ZuPinContactRepayType.C_AFTER.equals(zuPinVehicleExecute.getZuPinContactRepayType());
        ContactLogUtil.addLineAndLog(this.getClass(),"单车提前结束租赁 开始"+"车牌号 ："+zuPinVehicleExecute.getVehicleNum()+"    合同id "+zuPinContactUuid+"   后付模式"+afterModule);
        //后付方式
        if (afterModule) {
            ZuPinContact contact = (ZuPinContact) zuPinContactDao.findByUuid(zuPinContactUuid, ZuPinContact.class);

            List<ZuPinContactRentingFeeHistory> list = new ArrayList<>();
            String vehicleNumber = zuPinVehicleExecute.getVehicleNum();

            String comment = "提前结束租赁，当月租金扣款";
            //触发提前还款时的已交租金月份
            LocalDate orginActualDate = zuPinVehicleExecute.getActualzujinrepaymonth();

            //提车当日结束不计算
            if (!zuPinVehicleExecute.getTiCheDate().toString().equals(finishDate.toString())) {

                int jiangeMonth = zuPinVehicleExecute.getIntervalMonthFromActualZjDate(finishDate);

                if (jiangeMonth < 0) {
                    ApplicationLog.error(ZuPinContactServiceImpl.class, "结束时间错误 结束时间小于 当前批次已经缴纳月份时间  合同id：" + zuPinContactUuid + "批次：" + zuPinVehicleExecute.getTiChePiCi());
                    throw new RuntimeException("结束时间错误 结束时间小于 当前批次已经缴纳月份时间  合同id：" + zuPinContactUuid + "批次：" + zuPinVehicleExecute.getTiChePiCi());
                }

                BigDecimal actualFee = zuPinVehicleExecute.getActualRentFee();
                //循环中每次操作 actual 直到actualfeedate>nowDate
                for (int i = 0; i < jiangeMonth; i++) {
                    ApplicationLog.info(ZuPinContactAndVehicleOverServiceImpl.class, comment + orginActualDate.plusMonths(i) + "月欠款" + actualFee);
                    zuPinVehicleExecute.setActualzujinrepaymonth(orginActualDate.plusMonths(1));
                    zuPinVehicleExecute.setNextFeeDate(finishDate);
                    ZuPinContactRentingFeeHistory rentingFeeHistory = buildHistoryObj(zuPinVehicleExecute, ZuPinContactRepayLocation.L_ZJ_SCHEDULE, orginActualDate.plusMonths(i), contact, actualFee, comment);
                    setHistoryJiaoYiRen(rentingFeeHistory);
                    ApplicationLog.info(ZuPinContactAndVehicleOverServiceImpl.class, "单车结束租赁 租金生成   车牌号"+vehicleNumber + "租赁费用:" + actualFee);
                    list.add(rentingFeeHistory);
                }

                zuPinContactDao.saveOrUpdate(contact);
                zuPinContactDao.saveAll(list);
            }

        } else {
            //为了防止前付模式 在断电schedule自动扣费未生成账单
            LocalDate nextFeeDate = zuPinVehicleExecute.getNextFeeDate();
            ApplicationLog.info(ZuPinContactServiceImpl.class, nextFeeDate.toString());
            int intervaldays = JodaUtils.compareDays(finishDate, nextFeeDate);

            int month = zuPinVehicleExecute.getRentMonth();
            LocalDate jieshuDate = zuPinVehicleExecute.getTiCheDate().plusMonths(month);
            boolean contactOver = jieshuDate.toString().equals(nextFeeDate.toString());

            if (intervaldays <= 0 && contactOver == false) {
                ApplicationLog.error(ZuPinContactServiceImpl.class, "合同id：" + zuPinContactUuid + "结束异常" + "前付模式下提前结束 车牌号" + zuPinVehicleExecute.getVehicleNum() + " 结束时间小于实际缴纳月份时间");
                throw new RuntimeException("合同id：" + zuPinContactUuid + "结束异常" + "前付模式下提前结束 车牌号" + zuPinVehicleExecute.getVehicleNum() + " 结束时间小于实际缴纳月份时间");
            }
        }
        ContactLogUtil.addLineAndLog(this.getClass(),"单车提前结束租赁 开始"+"车牌号 ："+zuPinVehicleExecute.getVehicleNum()+"    合同id "+zuPinContactUuid);
    }


    @Override
    public void saveOverZuPinRiZuVehicle(String zuPinContactUuid, String tiChePiCi, String vehicleNum, String finishDate, String comment) throws Exception {
        ZuPinContact zuPinContact = (ZuPinContact) zuPinContactDao.findByUuid(zuPinContactUuid, ZuPinContact.class);
        ZuPinVehicleExecute execute = zuPinContactDao.obtainSpecZuPinContactVehicleExcuteDTO(zuPinContactUuid, tiChePiCi, vehicleNum);
        execute.setOver(true);
        execute.setJieshuDate(new LocalDate(finishDate));
        ZuPinContact contact1 = (ZuPinContact) zuPinContactDao.findByUuid(zuPinContactUuid, ZuPinContact.class);
        //修改车辆状态
        vmsDubboService.saveYiBuVehicleOverRevert(vehicleNum, contact1.getJiaFangUuid(), contact1.getJiaFangName(),contact1.getYiFangUuid(), contact1.getYiFangName(), finishDate);
        //后付类型进行租金交款处理
        jieShuRiZuZuLin(execute, new LocalDate(finishDate), zuPinContactUuid);

        execute.updateComment(new LocalDate().toString(), comment);
        execute.setPiciOver(true);
        execute.setOver(true);
        execute.setJieshuDate(new LocalDate(finishDate));

        //保存租赁状态变更
        zuPinContactDao.saveOrUpdate(execute);

        //修改未支付押金状态
        //修改合同下所有欠款账单中 当前结束车辆的 押金全部为 合同结束未付款状态
        List<ZuPinContactRentingFeeHistory> histories = zuPinContact.getUnPayBill();
        for (ZuPinContactRentingFeeHistory history : histories) {
            ZuPinContactRepayLocation location = history.getZuPinContactRepayLocation();
            //未支付押金状态处理
            if (location.contactOverCheckYaJin() && history.getVehicleNumber().equals(vehicleNum)) {
                history.setStatus(PayOrderStatus.O_ABANDON);
                ContactLogUtil.addShortLine(this.getClass(),"日结方式   单车结束租赁 history uuid"+history.getUuid()+"车牌号"+history.getVehicleNumber()+"置未不处理");
            }
        }
        zuPinContactDao.saveAll(histories);

        //合同记录中记录车辆停止租赁信息
        LoginInfo loginInfo = SecurityUtils.currentLoginUser();
        String description = "车辆" + vehicleNum + "选择日期" + finishDate + "变更为停止租赁状态";
        ZuPinContact contact = new ZuPinContact();
        contact.setUuid(zuPinContactUuid);
        ZuPinContactLog log = new ZuPinContactLog(loginInfo.getUserUuid(), loginInfo.getXingMing(), description, contact);
        zuPinContactDao.saveOrUpdate(log);
    }

    @Override
    public void updateZuPinContactRiJieFinish(ZuPinContactOverFormDTO zuPinContactOverFormDTO) throws Exception{

        LoginInfo loginInfo = SecurityUtils.currentLoginUser();

        String zuPinContactUuid = zuPinContactOverFormDTO.getZuPinContactUuid();
        String finishDate = zuPinContactOverFormDTO.getFinishDate();
        ContactLogUtil.addLineAndLog(this.getClass(),"日结合同 合同id"+zuPinContactUuid+"开始结束合同");
        ZuPinContact contact = (ZuPinContact) zuPinContactDao.findByUuid(zuPinContactUuid, ZuPinContact.class);

        List<ZuPinVehicleExecute> executes = contact.getSpeUseFulExecute();

        for (ZuPinVehicleExecute execute : executes) {
            ApplicationLog.infoWithCurrentUser(ZuPinContactServiceImpl.class, loginInfo.getUserUuid(), "合同结束还原车辆" + execute.getVehicleNum() + "为初始状态");
            vmsDubboService.saveYiBuVehicleOverRevert(execute.getVehicleNum(), contact.getJiaFangUuid(), contact.getJiaFangName(), contact.getYiFangUuid(), contact.getYiFangName(), finishDate);
        }
        //金额 备注 类型
        String[] overformcount = zuPinContactOverFormDTO.getOverformcount();
        String[] comment = zuPinContactOverFormDTO.getComment();
        String[] finishCharge = zuPinContactOverFormDTO.getFinishCharge();

        ApplicationLog.infoWithCurrentUser(ZuPinContactServiceImpl.class, loginInfo.getUserUuid(), "合同结束 当前合同押金已经支付" + contact.getYajinHasPay());


        //生成 后付模式时 还未自动生成的订单
        List<ZuPinContactRentingFeeHistory> unCreateBills = obtainRiJieFinishNoPayZuJinList(zuPinContactUuid, new LocalDate(finishDate));
        zuPinContactDao.saveAll(unCreateBills);

        //已经缴纳押金部分退款 历史记录
        if (contact.getYajinHasPay() != null && contact.getYajinHasPay().compareTo(new BigDecimal(0)) > 0) {
            ZuPinContactRentingFeeHistory zujinTuiHuan = new ZuPinContactRentingFeeHistory(contact.getContactNumber(), null, new LocalDate(), contact.getYajinHasPay().multiply(new BigDecimal(-1)), contact, 0, loginInfo.getXingMing(), loginInfo.getUserUuid(), false, "合同结束后还押金");
            zujinTuiHuan.setZuPinContactRepayLocation(ZuPinContactRepayLocation.L_JS_OUT);
            zujinTuiHuan.setActualFeeDate(new LocalDate());
            zujinTuiHuan.setStatus(PayOrderStatus.O_CREATE);
            ApplicationLog.info(ZuPinContactServiceImpl.class, "日结合同 合同结算押金退款" + contact.getYajinHasPay());
            zuPinContactDao.saveOrUpdate(zujinTuiHuan);
        }


        //修改合同下所有欠款账单中 未付款押金部分为合同结束未付款
        List<ZuPinContactRentingFeeHistory> histories = contact.getUnPayBill();
        for (ZuPinContactRentingFeeHistory history : histories) {
            ZuPinContactRepayLocation location = history.getZuPinContactRepayLocation();
            //未支付押金状态处理
            if (location.contactOverCheckYaJin()) {
                history.setStatus(PayOrderStatus.O_ABANDON);
            }
        }
        zuPinContactDao.saveAll(histories);

        //其他费用
        if (finishCharge != null && finishCharge.length > 0) {
            for (int i = 0; i < finishCharge.length; i++) {
                boolean tianjia = true;
                if ("SHOU_QU".equals(finishCharge[i])) {
                    tianjia = false;
                }
                ZuPinContactRentingFeeHistory zuPinContactRentingFeeHistory = new ZuPinContactRentingFeeHistory(contact.getContactNumber(), null, new LocalDate(), new BigDecimal(overformcount[i]), contact, 0, loginInfo.getXingMing(), loginInfo.getUserUuid(), tianjia, comment[i]);

                if (!tianjia) {
                    ApplicationLog.info(ZuPinContactServiceImpl.class, "日结合同 合同结束 合同结算其他费用项收取" + overformcount[i]);
                    zuPinContactRentingFeeHistory.setZuPinContactRepayLocation(ZuPinContactRepayLocation.L_JS_IN);
                    zuPinContactRentingFeeHistory.setStatus(PayOrderStatus.O_CREATE);
                } else {
                    ApplicationLog.info(ZuPinContactServiceImpl.class, "日结合同 合同结束 合同结算其他费用项支出" + overformcount[i]);
                    zuPinContactRentingFeeHistory.setZuPinContactRepayLocation(ZuPinContactRepayLocation.L_JS_OUT);
                    //押金等退换 线下操作已经完成 直接设置为已经支付
                    zuPinContactRentingFeeHistory.setStatus(PayOrderStatus.O_CREATE);
                    zuPinContactRentingFeeHistory.setFeeTotal(new BigDecimal(overformcount[i]).multiply(new BigDecimal(-1)));
                }
                zuPinContactRentingFeeHistory.setActualFeeDate(new LocalDate(finishDate));
                zuPinContactDao.saveOrUpdate(zuPinContactRentingFeeHistory);
            }
        }

        ApplicationLog.infoWithCurrentUser(ZuPinContactServiceImpl.class, loginInfo.getUserUuid(), "合同结束 缴纳其他费用" + finishCharge);
        //批次结束等
        ZuPinContactLog log = contact.finishContact(loginInfo, finishDate);
        zuPinContactDao.saveOrUpdate(log);

        contact.setEndDate(new LocalDate(finishDate));
        contact.setEndExecute(true);
        zuPinContactDao.saveOrUpdate(contact);

        ContactLogUtil.addLineAndLog(this.getClass(),"日结合同 合同id"+zuPinContactUuid+"结束合同完成");
    }

    @Override
    public List<ZuPinContactRentingFeeHistoryDTO> obtainZuPinContactRiJieSpecVehicleHistoryDTOList(String zuPinContactUuid, String executeUuid, String vehicleNum, LocalDate finishDate) {
        List<ZuPinContactRentingFeeHistory> histories= obtainZuPinContactRiJieSpecVehicleHistoryList(zuPinContactUuid,executeUuid,vehicleNum,finishDate);
        if(CHListUtils.hasElement(histories)){
            return  ZuPinContactRentingFeeHistoryWebAssember.toRentFeeHistoryDTOList(histories);
        }else {
            return null;
        }
    }

    @Override
    public List<ZuPinContactRentingFeeHistory> obtainZuPinContactRiJieSpecVehicleHistoryList(String zuPinContactUuid, String executeUuid, String vehicleNum, LocalDate finishDate) {
        //获取未结束的execute 里面 的 execute 并生成 rentfeehistroy
        LoginInfo loginInfo = SecurityUtils.currentLoginUser();
        ZuPinContact zuPinContact = (ZuPinContact) zuPinContactDao.findByUuid(zuPinContactUuid, ZuPinContact.class);

        List<ZuPinContactRentingFeeHistory> histories= new ArrayList<>();
        ZuPinVehicleExecute execute = (ZuPinVehicleExecute)zuPinContactDao.findByUuid(executeUuid,ZuPinVehicleExecute.class);
        LocalDate ticheDate = execute.getTiCheDate();
        try {
            int dayInteval = JodaUtils.calculateDays(ticheDate,finishDate)+1;
            BigDecimal feetotal = execute.getActualRentFee().multiply(new BigDecimal(dayInteval));
            ZuPinContactRentingFeeHistory history = new ZuPinContactRentingFeeHistory(zuPinContact.getContactNumber(),execute.getVehicleNum(),ticheDate,feetotal,zuPinContact,execute.getTiChePiCi(),loginInfo.getXingMing(),loginInfo.getUserUuid(),false,"");
            history.setComment("日结车辆提车时间："+execute.getTiCheDate()+"     合同结束时间"+finishDate+"        每天租金"+execute.getActualRentFee()+"      共租赁"+dayInteval+"天"+"       合计 ："+feetotal+"元");
            //现在使用 actualdate 作为 提车的开始时间
            history.setActualFeeDate(execute.getTiCheDate());
            history.setActualFeeDateEnd(finishDate);
            history.setZuPinYaJinType(ZuPinYaJinType.Y_QUANKUAN);
            history.setShouFu(execute.getShouFu());
            history.setYueGong(new BigDecimal(0));
            history.setFenQiShu("0");
            history.setZuPinContactRepayLocation(ZuPinContactRepayLocation.L_ZJ_SCHEDULE);
            histories.add(history);
            return histories;
        }catch (Exception e){
            ApplicationLog.info(ZuPinContactAndVehicleOverServiceImpl.class,e.getMessage());
        }
        return  null;
    }

    //根据结束日期将所有 未结束的 车辆 计算 将要产生的 款项
    @Override
    public List<ZuPinContactRentingFeeHistoryDTO> obtainZupinContactRiJieFinishNoPayZuJinList(String zuPinContactUuid, LocalDate finishDate) {
        try {
            List<ZuPinContactRentingFeeHistory> rentingFeeHistories = obtainRiJieFinishNoPayZuJinList(zuPinContactUuid, finishDate);
            return ZuPinContactRentingFeeHistoryWebAssember.toRentFeeHistoryDTOList(rentingFeeHistories);
        }catch (Exception e){
            ApplicationLog.info(ZuPinContactAndVehicleOverServiceImpl.class,e.getMessage());
        }
        return null;
    }


    @Override
    public void updateZuPinContactVehicleRiJieFinish(ZuPinContactOverFormDTO zuPinContactOverFormDTO) {
        LoginInfo loginInfo = SecurityUtils.currentLoginUser();

        String zuPinContactUuid = zuPinContactOverFormDTO.getZuPinContactUuid();
        String finishDate = zuPinContactOverFormDTO.getFinishDate();
        ZuPinContact contact = (ZuPinContact) zuPinContactDao.findByUuid(zuPinContactUuid, ZuPinContact.class);

        String  executeUuid = zuPinContactOverFormDTO.getExecuteUuid();
        ZuPinVehicleExecute execute= (ZuPinVehicleExecute)zuPinContactDao.findByUuid(executeUuid,ZuPinVehicleExecute.class);

        vmsDubboService.saveYiBuVehicleOverRevert(execute.getVehicleNum(), contact.getJiaFangUuid(), contact.getJiaFangName(), contact.getYiFangUuid(), contact.getYiFangName(), finishDate);

        //金额 备注 类型
        String[] overformcount = zuPinContactOverFormDTO.getOverformcount();
        String[] comment = zuPinContactOverFormDTO.getComment();
        String[] finishCharge = zuPinContactOverFormDTO.getFinishCharge();

        ApplicationLog.infoWithCurrentUser(ZuPinContactServiceImpl.class, loginInfo.getUserUuid(), "合同结束 当前合同押金已经支付" + contact.getYajinHasPay());


        //生成 后付模式时 还未自动生成的订单
        List<ZuPinContactRentingFeeHistory> unCreateBills = obtainZuPinContactRiJieSpecVehicleHistoryList(zuPinContactUuid,executeUuid,execute.getVehicleNum(), new LocalDate(finishDate));
        zuPinContactDao.saveAll(unCreateBills);


        //当前车辆的押金设置为 未处理
        List<ZuPinContactRentingFeeHistory> histories = contact.getUnPayBill();
        for (ZuPinContactRentingFeeHistory history : histories) {
            ZuPinContactRepayLocation location = history.getZuPinContactRepayLocation();
            //未支付押金状态处理
            int tichePiCi = history.getTichePiCi();
            String vehicleNumber = history.getVehicleNumber();
            boolean piciResult = execute.getTiChePiCi()==tichePiCi?true:false;
            boolean vehicleNumberResult = execute.getVehicleNum().equals(vehicleNumber)?true:false;
            if (location.contactOverCheckYaJin()&&piciResult&&vehicleNumberResult) {
                history.setStatus(PayOrderStatus.O_ABANDON);
                break;
            }
        }
        zuPinContactDao.saveAll(histories);

        //其他费用
        if (finishCharge != null && finishCharge.length > 0) {
            for (int i = 0; i < finishCharge.length; i++) {
                boolean tianjia = true;
                if ("SHOU_QU".equals(finishCharge[i])) {
                    tianjia = false;
                }
                ZuPinContactRentingFeeHistory zuPinContactRentingFeeHistory = new ZuPinContactRentingFeeHistory(contact.getContactNumber(), null, new LocalDate(), new BigDecimal(overformcount[i]), contact, 0, loginInfo.getXingMing(), loginInfo.getUserUuid(), tianjia, comment[i]);

                if (!tianjia) {
                    ApplicationLog.info(ZuPinContactServiceImpl.class, "合同结算其他费用项收取" + overformcount[i]);
                    zuPinContactRentingFeeHistory.setZuPinContactRepayLocation(ZuPinContactRepayLocation.L_JS_IN);
                    zuPinContactRentingFeeHistory.setStatus(PayOrderStatus.O_CREATE);
                } else {
                    ApplicationLog.info(ZuPinContactServiceImpl.class, "合同结算其他费用项支出" + overformcount[i]);
                    zuPinContactRentingFeeHistory.setZuPinContactRepayLocation(ZuPinContactRepayLocation.L_JS_OUT);
                    //押金等退换 线下操作已经完成 直接设置为已经支付
                    zuPinContactRentingFeeHistory.setStatus(PayOrderStatus.O_CREATE);
                    zuPinContactRentingFeeHistory.setFeeTotal(new BigDecimal(overformcount[i]).multiply(new BigDecimal(-1)));
                }
                zuPinContactRentingFeeHistory.setVehicleNumber(execute.getVehicleNum());
                zuPinContactRentingFeeHistory.setActualFeeDate(new LocalDate(finishDate));
                zuPinContactDao.saveOrUpdate(zuPinContactRentingFeeHistory);
            }
        }

        ApplicationLog.infoWithCurrentUser(ZuPinContactServiceImpl.class, loginInfo.getUserUuid(), "合同结束 缴纳其他费用" + finishCharge);

        execute.setOver(true);
        execute.setJieshuDate(new LocalDate(finishDate));
        zuPinContactDao.saveOrUpdate(execute);

        ZuPinContactLog log = new ZuPinContactLog(loginInfo.getUserUuid(), loginInfo.getXingMing(), "车辆"+execute.getVehicleNum() + "结束租赁"+"  租赁时间范围："+execute.getTiCheDate()+"/"+execute.getJieshuDate()+"  录入时间"+new LocalDate());
        log.setZuPinContact(contact);
        zuPinContactDao.saveOrUpdate(log);

    }

    private void jieShuRiZuZuLin(ZuPinVehicleExecute zuPinVehicleExecute, LocalDate finishDate, String zuPinContactUuid) throws Exception{
        //后付方式
        ZuPinContact contact = (ZuPinContact) zuPinContactDao.findByUuid(zuPinContactUuid, ZuPinContact.class);

        List<ZuPinContactRentingFeeHistory> list = new ArrayList<>();
        String vehicleNumber = zuPinVehicleExecute.getVehicleNum();

        String comment = "提前结束租赁，当月租金扣款";

        //提车当日结束不计算
        if (!zuPinVehicleExecute.getTiCheDate().toString().equals(finishDate.toString())) {

            int jiangeDays = JodaUtils.calculateDays(zuPinVehicleExecute.getTiCheDate(),finishDate)+1;
            ContactLogUtil.addActionLine(this.getClass(),"日结 单车结束 结束时间"+finishDate+"开始时间 "+zuPinVehicleExecute.getTiCheDate());

            if (jiangeDays < 0) {
                ApplicationLog.error(ZuPinContactServiceImpl.class, "结束时间错误 结束时间小于 当前批次已经缴纳月份时间  合同id：" + zuPinContactUuid + "批次：" + zuPinVehicleExecute.getTiChePiCi());
                throw new RuntimeException("结束时间错误 结束时间小于 当前批次已经缴纳月份时间  合同id：" + zuPinContactUuid + "批次：" + zuPinVehicleExecute.getTiChePiCi());
            }

            BigDecimal actualFee = zuPinVehicleExecute.getActualRentFee().multiply(new BigDecimal(jiangeDays));

            ZuPinContactRentingFeeHistory rentingFeeHistory = buildHistoryObj(zuPinVehicleExecute, ZuPinContactRepayLocation.L_ZJ_SCHEDULE, finishDate, contact, actualFee, comment);

            setHistoryJiaoYiRen(rentingFeeHistory);

            rentingFeeHistory.setHappendDate(zuPinVehicleExecute.getTiCheDate());
            rentingFeeHistory.setActualFeeDateEnd(finishDate);

            ApplicationLog.info(ZuPinContactAndVehicleOverServiceImpl.class, "日结方式  车辆提前结束租赁 租金生成 车牌号"+vehicleNumber + " 费用:" + actualFee);
            list.add(rentingFeeHistory);

            zuPinContactDao.saveOrUpdate(contact);
            zuPinContactDao.saveAll(list);
        }


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
