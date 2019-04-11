/*
* Copyright (c) 2007 IJO Technologies Ltd.
* www.ijotechnologies.com
* All rights reserved.
*
* This software is the confidential and proprietary information of
* IJO Technologies ("Confidential Information").
* You shall not disclose such Confidential Information and shall use
* it only in accordance with the terms of the license agreement you
* entered into with IJO Technologies.
*/
package com.yajun.green.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Kid Luo
 */
public abstract class ValidatorUtils {
    private static final String EMAIL_REGULAR_EXPRESSION =
            "^[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";

    private static final String IPADDRESS_REGULAR_EXPRESSION =
            "^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$";

    private static final String POSITIVE_INTEGER_REGULAR_EXPRESSION = "^[1-9]\\d*$";

    /**
     * This method is used to test whether a string is a valid email address or not
     *
     * @param emailAddress the input string for test
     * @return if the email address is valid return true,otherwise return false.
     */
    public static boolean isValidEmail(String emailAddress) {
        if (emailAddress == null) {
            return false;
        }

        Pattern p = Pattern.compile(EMAIL_REGULAR_EXPRESSION);
        Matcher m = p.matcher(emailAddress);
        return m.matches();
    }

    public static boolean isValidIpAddress(String ipAddress) {
        if (ipAddress == null) {
            return false;
        }

        Pattern p = Pattern.compile(IPADDRESS_REGULAR_EXPRESSION);
        Matcher m = p.matcher(ipAddress);
        return m.matches();
    }

    public static boolean isPositiveInteger(String number) {
        if (number == null) {
            return false;
        }

        Pattern p = Pattern.compile(POSITIVE_INTEGER_REGULAR_EXPRESSION);
        Matcher m = p.matcher(number);
        return m.matches();
    }

    public static boolean isNumeric(String str) {
        if (str == null) return false;
        String trimedStr = org.springframework.util.StringUtils.trimAllWhitespace(str);
        if (trimedStr.length() == 0) return false;
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(trimedStr);
        return isNum.matches();
    }
}