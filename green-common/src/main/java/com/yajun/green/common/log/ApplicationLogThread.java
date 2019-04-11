package com.yajun.green.common.log;

/**
 * User: Jack Wang
 * Date: 16-9-11
 */
public class ApplicationLogThread implements Runnable {

    private String user;
    private String url;

    public ApplicationLogThread(String user, String url) {
        this.user = user;
        this.url = url;
    }

    public void run() {
        try {
            ApplicationLog.info(ApplicationLogThread.class, "log url " + url + " of user " + user);
        } catch (Exception e) {
            ApplicationLog.error(ApplicationLogThread.class, "log url " + url + " of user " + user + " failed", e);
        }
    }
}
