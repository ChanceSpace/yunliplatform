package com.yajun.green.web.controller.webapi;

import com.alibaba.fastjson.JSONObject;
import com.yajun.green.common.log.ApplicationLog;
import com.yajun.green.common.web.request.HandleResponse;
import com.yajun.green.security.SecurityUtils;
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
public class ClientBaseController {

    /*********************************************读取REQUEST中的信息***************************************************/

    /**
     * 读取文件
     */
    public static String readContent(HttpServletRequest request) {
        try {
            request.setCharacterEncoding("UTF-8");
            int contentLen = request.getContentLength();
            InputStream is = request.getInputStream();

            if (contentLen > 0) {
                int readLen = 0;
                int readLengthThisTime = 0;
                byte[] message = new byte[contentLen];

                while (readLen != contentLen) {
                    readLengthThisTime = is.read(message, readLen, contentLen - readLen);
                    if (readLengthThisTime == -1) {
                        break;
                    }
                    readLen += readLengthThisTime;
                }
                String json = new String(message);
                return json;
            }

        } catch (Exception e) {
            ApplicationLog.error(ClientBaseController.class, "read request content error", e);
        }

        return "";
    }

    private final static long TIME_VALIDATE_BETWEEN = 10000;

    /**
     * 当前时间和上报时间相比较，如果在5S之内，证明有效
     */
    public static boolean obtainResponseInRightWay(String token) {
        return SecurityUtils.currentLoginUser(token) != null;
    }
}
