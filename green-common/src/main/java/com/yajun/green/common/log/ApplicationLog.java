package com.yajun.green.common.log;

import org.apache.log4j.Logger;

/**
 * User: Jack Wang
 * Date: 16-9-11
 */
public class ApplicationLog {

    private static Logger logger = Logger.getLogger(ApplicationLog.class);

    public static void debug(Class zClass, String message) {
        logger.debug(zClass.getName() + " " + message);
    }

    public static void info(Class zClass, String message) {
        logger.info(zClass.getName() + " " + message);
    }

    public static void infoWithCurrentUser(Class zClass, String currentUserId, String message) {
        logger.info(zClass.getName() + " " + message + " by user " + currentUserId);
    }

    public static void error(Class zClass, Throwable e) {
        logger.error(e);
    }

    public static void error(Class zClass, String message) {
        logger.error(zClass.getName() + " " + message);
    }

    public static void error(Class zClass, String message, Throwable e) {
        logger.info(zClass.getName() + " " + message, e);
    }

}
