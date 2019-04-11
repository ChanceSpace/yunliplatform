package com.yajun.green.web.controller.webapi;

import com.alibaba.fastjson.JSONObject;
import com.yajun.green.common.utils.WebUtils;
import com.yajun.green.common.web.request.HandleResponse;
import junit.framework.TestCase;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/database.xml", "/applicationContext.xml"})
public class ClientInterfaceTest extends TestCase {

    private static final String host = "http://182.140.140.6/my/boss/resource.html";
//    private static final String host = "http://localhost:8080/my/boss/resource.html";

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testObtainZuPinContactSta() throws Exception {
        JSONObject request = new JSONObject();
        request.put(HandleResponse.APP_TYPE, "WEB");
        request.put(HandleResponse.APP_VERSION, "1.0");
        request.put(HandleResponse.TOKEN,"cysadmin|25d55ad283aa400af464c76d713c07ad");
        request.put(HandleResponse.BUSINESS_TYPE, ClientBusiness.ZL_CONTACT_STA);

        PostMethod postMethod = new PostMethod(host);
        JSONObject body = new JSONObject();
        body.put("staYear", 2018);

        request.put(HandleResponse.REQUEST_BODAY, body);

        System.out.println(request.toJSONString());
        RequestEntity entity = new StringRequestEntity(request.toJSONString(), "text/xml", "UTF-8");
        postMethod.setRequestEntity(entity);

        String result = WebUtils.httpPostRequest(postMethod);
        System.out.println(result);
    }

    @Test
    public void testObtainXiaoShouContactSta() throws Exception {
        JSONObject request = new JSONObject();
        request.put(HandleResponse.APP_TYPE, "WEB");
        request.put(HandleResponse.APP_VERSION, "1.0");
        request.put(HandleResponse.TOKEN,"wk0001|25d55ad283aa400af464c76d713c07ad");
        request.put(HandleResponse.BUSINESS_TYPE, ClientBusiness.XS_CONTACT_STA);

        PostMethod postMethod = new PostMethod(host);
        JSONObject body = new JSONObject();
        body.put("staYear", 2018);

        request.put(HandleResponse.REQUEST_BODAY, body);

        System.out.println(request.toJSONString());
        RequestEntity entity = new StringRequestEntity(request.toJSONString(), "text/xml", "UTF-8");
        postMethod.setRequestEntity(entity);

        String result = WebUtils.httpPostRequest(postMethod);
        System.out.println(result);
    }

}
