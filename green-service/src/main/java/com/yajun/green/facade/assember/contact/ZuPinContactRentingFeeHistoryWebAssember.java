package com.yajun.green.facade.assember.contact;

import com.yajun.green.domain.contact.ZuPinContact;
import com.yajun.green.domain.contact.ZuPinContactRentingFeeHistory;
import com.yajun.green.domain.contact.ZuPinContactRepayLocation;
import com.yajun.green.facade.dto.contact.ZuPinContactRentingFeeHistoryDTO;
import com.yajun.green.facade.dto.pay.PayItem;
import org.joda.time.LocalDate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiuMengKe on 2017/8/15.
 */
public class ZuPinContactRentingFeeHistoryWebAssember {

    public static ZuPinContactRentingFeeHistoryDTO toRentFeeHistoryDTO(ZuPinContactRentingFeeHistory rentingFeeHistory) {
        String vehicleNumber = rentingFeeHistory.getVehicleNumber();
        LocalDate happendDate = new LocalDate(rentingFeeHistory.getHappendDate());
        BigDecimal feeTotal = rentingFeeHistory.getFeeTotal();
        int tichePiCi = rentingFeeHistory.getTichePiCi();
        ZuPinContact zuPinContact = rentingFeeHistory.getZuPinContact();
        String zuPinContactUuid = zuPinContact.getUuid();
        String customerUuid = zuPinContact.getYiFangUuid();
        String customerName = zuPinContact.getYiFangName();
        String saleManName = zuPinContact.getSaleManName();
        String contactNumber = zuPinContact.getContactNumber();
        String uuid = rentingFeeHistory.getUuid();
        String jiaoYiRen = rentingFeeHistory.getJiaoYiRen();
        String jiaoYiRenUuid = rentingFeeHistory.getJiaoYiRenUuid();
        boolean tianJia = rentingFeeHistory.isTianJia();
        String comment = rentingFeeHistory.getComment();
        ZuPinContactRepayLocation location = rentingFeeHistory.getZuPinContactRepayLocation();
        ZuPinContactRentingFeeHistoryDTO historyDTO =  new ZuPinContactRentingFeeHistoryDTO(uuid, vehicleNumber, happendDate.toString(), feeTotal.toString(), zuPinContactUuid, tichePiCi, customerUuid, customerName, saleManName, contactNumber, jiaoYiRen, jiaoYiRenUuid,tianJia,comment);
        historyDTO.setZuPinContactRepayLocation(location);
        historyDTO.setActualFeeDate(rentingFeeHistory.getActualFeeDate());
        historyDTO.setStatus(rentingFeeHistory.getStatus());
        historyDTO.setActualFeeDateEnd(rentingFeeHistory.getActualFeeDateEnd());
        return  historyDTO;
    }

    public static ZuPinContactRentingFeeHistory toRentFeeHistoryDomain(ZuPinContactRentingFeeHistoryDTO rentingFeeHistoryDTO) {
        String contactNumber = rentingFeeHistoryDTO.getContactNumber();
        String vehicleNumber = rentingFeeHistoryDTO.getVehicleNumber();
        LocalDate happendDate = new LocalDate(rentingFeeHistoryDTO.getHappendDate());
        BigDecimal feeTotal = BigDecimal.valueOf(Double.valueOf(rentingFeeHistoryDTO.getFeeTotal()));
        ZuPinContact zuPinContact = new ZuPinContact();
        zuPinContact.setUuid(rentingFeeHistoryDTO.getZuPinContactUuid());
        int tichePiCi = rentingFeeHistoryDTO.getTichePiCi();
        String jiaoYiRen = rentingFeeHistoryDTO.getJiaoYiRen();
        String jiaoYiRenUuid = rentingFeeHistoryDTO.getJiaoYiRenUuid();
        boolean tianJia = rentingFeeHistoryDTO.isTianJia();
        String commment = rentingFeeHistoryDTO.getComment();
        ZuPinContactRepayLocation location = rentingFeeHistoryDTO.getZuPinContactRepayLocation();
        ZuPinContactRentingFeeHistory history =  new ZuPinContactRentingFeeHistory(contactNumber, vehicleNumber, happendDate, feeTotal, zuPinContact, tichePiCi, jiaoYiRen, jiaoYiRenUuid,tianJia,commment);
        history.setZuPinContactRepayLocation(location);
        history.setActualFeeDate(rentingFeeHistoryDTO.getActualFeeDate());
        history.setStatus(rentingFeeHistoryDTO.getStatus());
        return history;
    }

    public static List<ZuPinContactRentingFeeHistoryDTO> toRentFeeHistoryDTOList(List<ZuPinContactRentingFeeHistory> rentingFeeHistoryList) {
        List<ZuPinContactRentingFeeHistoryDTO> zuPinContactRentingFeeHistoryDTOList = new ArrayList<>();
        for (ZuPinContactRentingFeeHistory rentingFeeHistory : rentingFeeHistoryList) {
            zuPinContactRentingFeeHistoryDTOList.add(toRentFeeHistoryDTO(rentingFeeHistory));
        }
        return zuPinContactRentingFeeHistoryDTOList;
    }

    public static List<PayItem> toPaymentRentFeeHistoryDTOList(List<ZuPinContactRentingFeeHistory> rentingFeeHistoryList) {
        List<PayItem> zuPinContactRentingFeeHistoryDTOList = new ArrayList<>();
        for (ZuPinContactRentingFeeHistory rentingFeeHistory : rentingFeeHistoryList) {
            zuPinContactRentingFeeHistoryDTOList.add(toRentFeeHistoryDTO(rentingFeeHistory));
        }
        return zuPinContactRentingFeeHistoryDTOList;
    }
}
