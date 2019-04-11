package com.yajun.green.repository;

/**
 * User: Jack Wang
 * Date: 14-4-9
 * Time: 下午1:21
 */
public class EntityLoadHolder {

    private static UserDao userDao;
    private static ZuPinContactDao zuPinContactDao;

    public static UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
    	EntityLoadHolder.userDao = userDao;
    }

    public static ZuPinContactDao getZuPinContactDao() {
        return zuPinContactDao;
    }

    public void setZuPinContactDao(ZuPinContactDao zuPinContactDao) {
    	EntityLoadHolder.zuPinContactDao = zuPinContactDao;
    }
}
