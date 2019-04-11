package com.yajun.green.common.domain;

import com.yajun.green.common.utils.UUIDUtils;

import java.io.Serializable;

/**
 * User: Jack Wang
 * Date: 14-4-9
 */
public class EntityBase implements Serializable {

    private String uuid;

    public EntityBase() {
        this.uuid = UUIDUtils.generateUUID();
    }

    /**
     * *******************************************************GETTER/SETTER****************************************
     */

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public static void main(String[] args) {
        System.out.println(UUIDUtils.generateUUID());
        System.out.println(UUIDUtils.generateUUID().length());
    }
}
