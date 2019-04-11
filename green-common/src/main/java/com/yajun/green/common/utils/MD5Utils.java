package com.yajun.green.common.utils;

import java.security.MessageDigest;

/**
 * User: Jack Wang
 * Date: 17-11-10
 * Time: 下午5:14
 */
public class MD5Utils {

    private static final String KEY_MD5 = "MD5";
    // 全局数组
    private static final String[] strDigits = { "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

    // 返回形式为数字跟字符串
    private static String byteToArrayString(byte bByte) {
        int iRet = bByte;
        if (iRet < 0) {
            iRet += 256;
        }
        int iD1 = iRet / 16;
        int iD2 = iRet % 16;
        return strDigits[iD1] + strDigits[iD2];
    }

    // 转换字节数组为16进制字串
    private static String byteToString(byte[] bByte) {
        StringBuffer sBuffer = new StringBuffer();
        for (int i = 0; i < bByte.length; i++) {
            sBuffer.append(byteToArrayString(bByte[i]));
        }
        return sBuffer.toString();
    }
    /**
     * MD5加密
     * @param strObj
     * @return
     * @throws Exception
     */
    public static String GetMD5Code(String strObj) throws Exception{
        MessageDigest md = MessageDigest.getInstance(KEY_MD5);
        // md.digest() 该函数返回值为存放哈希值结果的byte数组
        return byteToString(md.digest(strObj.getBytes()));
    }

    public static void main(String[] args) throws Exception {
        String wk0001 = GetMD5Code("cysadmin");
        System.out.println(wk0001);

        wk0001 = GetMD5Code("cdlkwl");
        System.out.println(wk0001);

        wk0001 = GetMD5Code("cdchzw");
        System.out.println(wk0001);

        wk0001 = GetMD5Code("gxycjq");
        System.out.println(wk0001);

        wk0001 = GetMD5Code("cdyldq");
        System.out.println(wk0001);
    }

}
