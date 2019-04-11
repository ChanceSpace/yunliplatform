package com.yajun.green.web.resquest;

import com.alibaba.fastjson.JSONObject;

/**
 * User: Jack Wang
 * Date: 16-6-27
 * Time: 下午1:26
 */
public class HandleResponse {

    public final static String APP_TYPE = "appType";
    public final static String APP_VERSION = "appVersion";
    public final static String BUSINESS_TYPE = "businessType";
    public final static String REQUEST_BODAY = "body";

    public final static String MESSAGE_TILE = "message";
    public final static String MESSAGE_STATUS = "status";
    public final static String RESPONSE_BODY = "body";

    /*********************************************分发不同的REQUEST***************************************************/

    public static JSONObject generateDataNotRightResponse() {
        JSONObject jsonObject = new JSONObject();
        generateDataNotRightResponse(jsonObject);
        return jsonObject;
    }

    public static JSONObject generateFailedResponse() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(MESSAGE_TILE, HandleMessage.MESSAGE_FAIL.getDescription());
        jsonObject.put(MESSAGE_STATUS, HandleStatus.FAILED);
        return jsonObject;
    }

    public static JSONObject generateDataNotRightResponse(JSONObject jsonObject) {
        jsonObject.put(MESSAGE_TILE, HandleMessage.MESSAGE_DATA_ERROR.getDescription());
        jsonObject.put(MESSAGE_STATUS, HandleStatus.DATA_NOT_OK);
        return jsonObject;
    }

    public static JSONObject generateDataNotFoundResponse(JSONObject jsonObject) {
        jsonObject.put(MESSAGE_TILE, HandleMessage.MESSAGE_DATA_NOTFOUND.getDescription());
        jsonObject.put(MESSAGE_STATUS, HandleStatus.DATA_NOT_FOUND);
        return jsonObject;
    }

    public static JSONObject generateDataDuplicationResponse(JSONObject jsonObject) {
        jsonObject.put(MESSAGE_TILE, HandleMessage.MESSAGE_DATA_DUPLICATE.getDescription());
        jsonObject.put(MESSAGE_STATUS, HandleStatus.DATA_DUPLICATE);
        return jsonObject;
    }

    public static JSONObject generateOKResponse(JSONObject jsonObject) {
        jsonObject.put(MESSAGE_TILE, HandleMessage.MESSAGE_OK.getDescription());
        jsonObject.put(MESSAGE_STATUS, HandleStatus.OK);
        return jsonObject;
    }

    public static JSONObject generateFailedResponse(JSONObject jsonObject) {
        jsonObject.put(MESSAGE_TILE, HandleMessage.MESSAGE_FAIL.getDescription());
        jsonObject.put(MESSAGE_STATUS, HandleStatus.FAILED);
        return jsonObject;
    }

    public static JSONObject generateCannotDeleteResponse(JSONObject jsonObject) {
        jsonObject.put(MESSAGE_TILE, HandleMessage.MESSAGE_CANNOT_DELETE.getDescription());
        jsonObject.put(MESSAGE_STATUS, HandleStatus.CAN_NOT_DELETE);
        return jsonObject;
    }

    public static JSONObject generateNoAuthority(JSONObject jsonObject) {
        jsonObject.put(MESSAGE_TILE, HandleMessage.MESSAGE_NO_AUTHORITY.getDescription());
        jsonObject.put(MESSAGE_STATUS, HandleStatus.NO_AUTHORITY);
        return jsonObject;
    }
}
