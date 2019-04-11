package com.yajun.green.web.controller.webapi;

import com.alibaba.fastjson.JSONObject;
import com.yajun.green.common.log.ApplicationLog;
import com.yajun.green.common.web.request.HandleResponse;
import com.yajun.green.service.StaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Date;

/**
 * Created by chance on 2017/8/9.
 */
@Controller
public class ClientResourceController extends ClientBaseController {

    @Autowired
    private StaService staService;

    @RequestMapping("/boss/resource.html")
    protected void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        String json = URLDecoder.decode(readContent(request), "UTF-8") ;
        ApplicationLog.info(ClientResourceController.class, json);

        String responseJSON = "";
        try {
            if (!StringUtils.hasText(json)) {
                responseJSON = HandleResponse.generateDataNotRightResponse().toJSONString();
            } else {
                JSONObject jsonObject = JSONObject.parseObject(json);
                String businessType = jsonObject.getString(HandleResponse.BUSINESS_TYPE);
                JSONObject requestBody = jsonObject.getJSONObject(HandleResponse.REQUEST_BODAY);
                String token = jsonObject.getString(HandleResponse.TOKEN);

                /************************************系统逻辑部分**************************************************/
                //验证请求合法性
                boolean validWay = obtainResponseInRightWay(token);

                if (validWay) {

                    //数据处理逻辑
                    switch (businessType) {
                        case ClientBusiness.ZL_CONTACT_STA:
                            responseJSON = staService.obtainZuPinContactSta(token, requestBody);
                            break;
                        case ClientBusiness.XS_CONTACT_STA:
                            responseJSON = staService.obtainXiaoShouContactSta(token, requestBody);
                            break;
                        default:
                            responseJSON = HandleResponse.generateDataNotFoundResponse(jsonObject).toJSONString();
                            break;
                    }
                } else {
                    responseJSON = HandleResponse.generateFailedResponse().toJSONString();
                }

            }
        } catch (Exception e) {
            ApplicationLog.error(ClientResourceController.class, "read request content error", e);
            responseJSON = HandleResponse.generateFailedResponse().toJSONString();
        }
        //返回结果
        response.setContentType("application/json; charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(responseJSON);
        writer.flush();
        writer.close();
    }
}
