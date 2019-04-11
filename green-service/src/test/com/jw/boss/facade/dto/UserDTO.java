package com.yajun.green.facade.dto;

import java.io.Serializable;

/**
 * Created by Jack Wang
 */
public class UserDTO implements Serializable{

    private String id;

    private String name;

    public UserDTO() {
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
