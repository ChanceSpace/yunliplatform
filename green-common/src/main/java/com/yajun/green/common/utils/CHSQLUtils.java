package com.yajun.green.common.utils;

import java.util.List;
import java.util.Set;

/**
 * User: Jack Wang
 * Date: 16-1-30
 * Time: 下午1:23
 */
public class CHSQLUtils {

    public static String convertListToSQLIn(List<String> dates) {
        StringBuffer buffer = new StringBuffer();
        int size = dates.size();
        for (int i = 0; i < size; i++) {
            String date = dates.get(i);
            if (i ==size - 1) {
                buffer.append("'" + date + "'");
            } else {
                buffer.append("'" + date + "',");
            }
        }
        return buffer.toString();
    }

    public static String convertListToSQLIn(Set<String> dates) {
        StringBuffer buffer = new StringBuffer();
        for (String date : dates) {
            buffer.append("'" + date + "',");
        }
        String sql = buffer.toString();
        return sql.substring(0, sql.length()-1);
    }

    public static String convertArrayToSQLIn(String[] dates) {
        StringBuffer buffer = new StringBuffer();
        int size = dates.length;
        for (int i = 0; i < size; i++) {
            String date = dates[i];
            if (i ==size - 1) {
                buffer.append("'" + date + "'");
            } else {
                buffer.append("'" + date + "',");
            }
        }
        return buffer.toString();
    }
}
