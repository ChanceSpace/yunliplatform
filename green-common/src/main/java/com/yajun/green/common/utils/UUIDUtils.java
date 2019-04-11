package com.yajun.green.common.utils;

import java.util.Date;

/**
 * User: pengjie
 * Date: 2016/5/20
 * Time: 11:48
 */
public class UUIDUtils {
    public static String generateUUID() {
        return CHDateUtils.getFullDateFormatUUID(new Date()) + "_" + CHStringUtils.getRandomString(16);
    }
    public static String generateTimeUUID(){
        return CHDateUtils.getFullTimeFormatUUID(new Date()) + "_" + CHStringUtils.getRandomString(14);
    }
    public static boolean isCurrentUuid(String uuid) {
        if (uuid.length() == 9) {
            return true;
        }
        return false;
    }
}
