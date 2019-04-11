package com.yajun.green.domain.xiaoshou;

import java.util.Comparator;

/**
 * Created by LiuMengKe on 2017/12/28.
 */
public class XiaoShouVehicleStatusObjComparator  implements Comparator<XiaoShouContactVehicleStatusObject> {

    @Override
    public int compare(XiaoShouContactVehicleStatusObject o1, XiaoShouContactVehicleStatusObject o2) {
        if(o1.getPici().compareTo(o2.getPici())==0){
            return o1.getVehicleNumber().compareTo(o2.getVehicleNumber());
        }
        return o1.getPici().compareTo(o2.getPici());
    }
}
