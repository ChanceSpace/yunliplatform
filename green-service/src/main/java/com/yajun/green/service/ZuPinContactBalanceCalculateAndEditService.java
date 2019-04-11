package com.yajun.green.service;

import com.yajun.green.domain.contact.ZuPinContact;
import com.yajun.green.domain.contact.ZuPinVehicleExecute;
import com.yajun.green.facade.dto.contact.ZuPinContactOverFormDTO;
import com.yajun.green.facade.dto.contact.ZuPinVehicleExecuteDTO;
import org.joda.time.LocalDate;

import java.util.List;

/**
 * Created by LiuMengKe on 2017/11/27.
 * <p>
 * 提车 和 自动扣费 账单生成和处理
 */
public interface ZuPinContactBalanceCalculateAndEditService {

    int RELAY_DAY = 0;


    /*******************************************合同提车部分************************************/
    //提车时初始化 execute
    List<ZuPinVehicleExecute> saveTiCheInitExecute(ZuPinContact contact, String selectModuleBrand, String[] vehicleNumber, String[] comment, ZuPinVehicleExecuteDTO executeDTO);

    //提车缴纳租金
    public ZuPinContact saveRepayZuJinInVehicleGet(ZuPinContact contact, String tichePiCi, List<ZuPinVehicleExecute> executeList);

    public ZuPinContact saveRepayYaJinInVehicleGet(ZuPinContact contact, String tichePiCi, List<ZuPinVehicleExecute> executeList);

    //自动扣费生成
    public List<ZuPinVehicleExecuteDTO> obtainDaoQiExecute(LocalDate localDate, int term);

    public void saveInSchedule(String zupinContactUuid, String tichePiCi);




    /*******************************************日结方式提车部分*******************************/
    List<ZuPinVehicleExecute> saveTiCheRiJieInitExecute(ZuPinContact contact, String selectModuleBrand, String[] vehicleNumber, String[] comment, ZuPinVehicleExecuteDTO executeDTO);

    ZuPinContact saveRepayRiJieYaJinInVehicleGet(ZuPinContact contact, String s, List<ZuPinVehicleExecute> executeList);

    void saveZuPinContactAdditionBalance(ZuPinContactOverFormDTO zuPinContactOverFormDTO);
}
