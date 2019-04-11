package com.yajun.green.service;

import com.alibaba.fastjson.JSONObject;

/**
 * User: Jack Wang
 * Date: 18-1-12
 * Time: 上午9:28
 */
public interface StaService {

    /******************************************销售合同统计相关************************************/

    String obtainZuPinContactSta(String token, JSONObject requestBody);

    String obtainXiaoShouContactSta(String token, JSONObject requestBody);
}
