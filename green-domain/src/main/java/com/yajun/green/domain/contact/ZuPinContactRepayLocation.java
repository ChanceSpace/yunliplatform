package com.yajun.green.domain.contact;

import com.yajun.green.common.web.view.SelectView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiuMengKe on 2017/9/4.
 */
public enum ZuPinContactRepayLocation {

    L_YJ_SCHEDULE("自动押金扣费"),
    L_ZJ_SCHEDULE("自动租金扣费"),
    L_YJ_MAN("押金充值"),
    L_YJ_MANSUB("押金扣费"),
    L_ZJ_MAN("租金充值"),
    L_YJ_VEHICLE_SUB("提车押金扣费"),
    L_ZJ_VEHICLE_SUB("提车租金扣费"),
    L_ZJ_TIQIAN_TZ("提前结束租赁，租金扣费"),
    L_Yj_TOTAL("押金合计"),
    L_ZJ_TOTAL("租金合计"),
    L_JS_IN("合同结束收取"),
    L_JS_OUT("合同结束支出"),
    L_FJ_IN("附加收取"),
    L_FJ_OUT("附加支出");

    ZuPinContactRepayLocation(String description) {
        this.description = description;
    }

    public static List<SelectView> getSelectViews() {
        List<SelectView> views = new ArrayList<>();
        ZuPinContactRepayLocation[] values = ZuPinContactRepayLocation.values();
        for (ZuPinContactRepayLocation value : values) {
            views.add(new SelectView(value.name(), value.getDescription()));
        }
        return views;
    }

    public String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    //合同结束时判断是否是下面在下面指定的类型
    public boolean contactOverCheckYaJin(){
        //判断是否是 押金自动扣款 提车押金扣款
        String[] wantType = {"L_YJ_SCHEDULE","L_YJ_VEHICLE_SUB"};
        for (String s : wantType) {
            if(s.equals(this.name())){
                return true;
            }
        }
        return false;
    }


}
