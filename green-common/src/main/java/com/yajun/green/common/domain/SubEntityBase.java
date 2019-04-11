package com.yajun.green.common.domain;

import com.yajun.green.common.utils.UUIDUtils;

/**
 * Created by LiuMengKe on 2017/9/15.
 */
public class SubEntityBase extends EntityBase {

    private String uuid;

    public SubEntityBase() {
        this.uuid = UUIDUtils.generateTimeUUID();
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
}
