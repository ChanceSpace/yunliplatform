package com.yajun.green.common.utils;

/**
 * User: Jack Wang
 * Date: 16-4-5
 * Time: 下午2:24
 */
public class JingWeiDuUtils {

    public static double getDistance(double long1, double lat1, double long2, double lat2) {
        double a, b, R;
        R = 6378137; // 地球半径
        lat1 = lat1 * Math.PI / 180.0;
        lat2 = lat2 * Math.PI / 180.0;
        a = lat1 - lat2;
        b = (long1 - long2) * Math.PI / 180.0;
        double d;
        double sa2, sb2;
        sa2 = Math.sin(a / 2.0);
        sb2 = Math.sin(b / 2.0);
        d = 2 * R * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1) * Math.cos(lat2) * sb2 * sb2));
        return d;
    }

    public static void main(String[] args) {
        double long1 = Double.valueOf("103.996218");
        double lat1 = Double.valueOf("30.628216");

        double long2 = Double.valueOf("103.996218");
        double lat2 = Double.valueOf("30.628216");

        double distance = getDistance(long1, lat1, long2, lat2);
        System.out.println(distance);
    }
}
