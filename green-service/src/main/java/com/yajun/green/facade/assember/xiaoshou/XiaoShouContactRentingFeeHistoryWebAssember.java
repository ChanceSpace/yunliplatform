package com.yajun.green.facade.assember.xiaoshou;

import com.yajun.green.domain.xiaoshou.XiaoShouContact;
import com.yajun.green.domain.xiaoshou.XiaoShouContactHistoryType;
import com.yajun.green.domain.xiaoshou.XiaoShouContactRentingFeeHistory;
import com.yajun.green.domain.xiaoshou.XiaoShouPayOrderStatus;
import com.yajun.green.facade.dto.contact.xiaoshou.ZuPinXiaoShouContactRentingFeeHistoryDTO;
import com.yajun.green.facade.dto.pay.PayItem;
import org.joda.time.LocalDate;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiuMengKe on 2017/8/15.
 */
public class XiaoShouContactRentingFeeHistoryWebAssember {

    public static ZuPinXiaoShouContactRentingFeeHistoryDTO toRentFeeHistoryDTO(XiaoShouContactRentingFeeHistory rentingFeeHistory) {
        String uuid = rentingFeeHistory.getUuid();
        String vehicleNumber = rentingFeeHistory.getVehicleNumber();
        LocalDate happendDate = new LocalDate(rentingFeeHistory.getHappendDate());
        BigDecimal feeTotal = rentingFeeHistory.getFeeTotal();
        int tichePiCi = rentingFeeHistory.getTichePiCi();
        XiaoShouContact zuPinContact = rentingFeeHistory.getXiaoShouContact();
        String contactNumber = zuPinContact.getContactNumber();
        String jiaoYiRen = rentingFeeHistory.getJiaoYiRen();
        String jiaoYiRenUuid = rentingFeeHistory.getJiaoYiRenUuid();
        String comment = rentingFeeHistory.getComment();
        XiaoShouContactHistoryType type = rentingFeeHistory.getXiaoShouContactHistoryType();
        XiaoShouContactHistoryType xiaoShouContactHistoryType = rentingFeeHistory.getXiaoShouContactHistoryType();
        LocalDate actualFeeDate = rentingFeeHistory.getActualFeeDate();
        LocalDate actualFeeDateEnd = rentingFeeHistory.getActualFeeDate();
        XiaoShouPayOrderStatus payOrderStatus = rentingFeeHistory.getPayOrderStatus();
        String  payOrderNumber = rentingFeeHistory.getPayOrderNumber();
        ZuPinXiaoShouContactRentingFeeHistoryDTO historyDTO =  new ZuPinXiaoShouContactRentingFeeHistoryDTO( uuid,vehicleNumber,  tichePiCi,  jiaoYiRen,  jiaoYiRenUuid,  contactNumber,  feeTotal, xiaoShouContactHistoryType,  actualFeeDate,  actualFeeDateEnd,  happendDate,  comment, payOrderStatus,  payOrderNumber);
        return  historyDTO;
    }

    public static XiaoShouContactRentingFeeHistory toRentFeeHistoryDomain(ZuPinXiaoShouContactRentingFeeHistoryDTO rentingFeeHistoryDTO) {
        String uuid = rentingFeeHistoryDTO.getUuid();
        String vehicleNumber = rentingFeeHistoryDTO.getVehicleNumber();
        int tichePiCi = rentingFeeHistoryDTO.getTichePiCi();
        String jiaoYiRen =rentingFeeHistoryDTO.getJiaoYiRen();
        String jiaoYiRenUuid = rentingFeeHistoryDTO.getJiaoYiRenUuid();
        String contactNumber = rentingFeeHistoryDTO.getContactNumber();
        BigDecimal feeTotal = rentingFeeHistoryDTO.getFeeTotal();
        XiaoShouContactHistoryType xiaoShouContactHistoryType = rentingFeeHistoryDTO.getXiaoShouContactHistoryType();
        LocalDate actualFeeDate = rentingFeeHistoryDTO.getActualFeeDate();
        LocalDate actualFeeDateEnd = rentingFeeHistoryDTO.getActualFeeDateEnd();
        LocalDate happendDate = rentingFeeHistoryDTO.getHappendDate();
        String comment = rentingFeeHistoryDTO.getComment();
        XiaoShouPayOrderStatus payOrderStatus = rentingFeeHistoryDTO.getPayOrderStatus();
        String payOrderNumber = rentingFeeHistoryDTO.getPayOrderNumber();
        //todo
        XiaoShouContactRentingFeeHistory history = new XiaoShouContactRentingFeeHistory(vehicleNumber,tichePiCi,jiaoYiRen,jiaoYiRenUuid,null,contactNumber,feeTotal, xiaoShouContactHistoryType,actualFeeDate,actualFeeDateEnd,happendDate,comment,payOrderStatus,payOrderNumber);
        if(StringUtils.hasText(uuid)){
            history.setUuid(uuid);
        }
        return history;
    }

    public static List<ZuPinXiaoShouContactRentingFeeHistoryDTO> toRentFeeHistoryDTOList(List<XiaoShouContactRentingFeeHistory> rentingFeeHistoryList) {
        List<ZuPinXiaoShouContactRentingFeeHistoryDTO> zuPinContactRentingFeeHistoryDTOList = new ArrayList<>();
        for (XiaoShouContactRentingFeeHistory rentingFeeHistory : rentingFeeHistoryList) {
            zuPinContactRentingFeeHistoryDTOList.add(toRentFeeHistoryDTO(rentingFeeHistory));
        }
        return zuPinContactRentingFeeHistoryDTOList;
    }

    public static List<PayItem> toPaymentRentFeeHistoryDTOList(List<XiaoShouContactRentingFeeHistory> rentingFeeHistoryList) {
        List<PayItem> zuPinContactRentingFeeHistoryDTOList = new ArrayList<>();
        for (XiaoShouContactRentingFeeHistory rentingFeeHistory : rentingFeeHistoryList) {
            zuPinContactRentingFeeHistoryDTOList.add(toRentFeeHistoryDTO(rentingFeeHistory));
        }
        return zuPinContactRentingFeeHistoryDTOList;
    }
}
