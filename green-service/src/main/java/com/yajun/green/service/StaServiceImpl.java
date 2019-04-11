package com.yajun.green.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yajun.green.common.security.LoginInfo;
import com.yajun.green.common.web.request.HandleResponse;
import com.yajun.green.repository.StaDao;
import com.yajun.green.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * User: Jack Wang
 * Date: 18-1-12
 * Time: 上午9:28
 */
@Service("staService")
public class StaServiceImpl implements StaService {

    @Autowired
    private StaDao staDao;

    /******************************************销售合同统计相关************************************/

    @Override
    public String obtainZuPinContactSta(String token, JSONObject requestBody) {
        LoginInfo loginInfo = SecurityUtils.currentLoginUser(token);
        boolean admin = loginInfo.isAdministrator();
        String companyUuid = loginInfo.getCompanyUuid();

        JSONObject result = new JSONObject();
        JSONObject body = new JSONObject();
        JSONArray array = new JSONArray();

        int year = requestBody.getIntValue("staYear");
        Map<String, Integer> model = staDao.findZuPinContactSta(admin, companyUuid, year);
        for (int i = 1; i <= 12; i++) {
            Integer total = model.get(String.valueOf(i));
            if (total == null) {
                array.add(0);
            } else {
                array.add(total.intValue());
            }
        }

        body.put("total", array);
        result.put(HandleResponse.RESPONSE_BODY, body);

        //返回结果
        HandleResponse.generateOKResponse(result);
        return result.toJSONString();
    }

    @Override
    public String obtainXiaoShouContactSta(String token, JSONObject requestBody) {
        LoginInfo loginInfo = SecurityUtils.currentLoginUser(token);
        boolean admin = loginInfo.isAdministrator();
        String companyUuid = loginInfo.getCompanyUuid();

        JSONObject result = new JSONObject();
        JSONObject body = new JSONObject();
        JSONArray array = new JSONArray();

        int year = requestBody.getIntValue("staYear");
        Map<String, Integer> model = staDao.findXiaoShouContactSta(admin, companyUuid, year);
        for (int i = 1; i <= 12; i++) {
            Integer total = model.get(String.valueOf(i));
            if (total == null) {
                array.add(0);
            } else {
                array.add(total.intValue());
            }
        }

        body.put("total", array);
        result.put(HandleResponse.RESPONSE_BODY, body);

        //返回结果
        HandleResponse.generateOKResponse(result);
        return result.toJSONString();
    }
}
