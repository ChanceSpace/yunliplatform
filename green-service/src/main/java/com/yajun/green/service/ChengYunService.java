package com.yajun.green.service;

import com.yajun.green.domain.pay.PayOrder;
import com.yajun.green.facade.dto.chengyun.CarrierRentFeeHistoryDTO;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

/**
 * Created by chance on 2017/9/12.
 */
public interface ChengYunService {

    /*******************************************承运商账户历史********************************/

    void updateCarrierPayOrderChargeHistory(PayOrder payOrder);

    void updateCarrierBalance(String chengYunUuid, CarrierRentFeeHistoryDTO feeHistory, List<MultipartFile> files,Boolean isChongZhi);
}
