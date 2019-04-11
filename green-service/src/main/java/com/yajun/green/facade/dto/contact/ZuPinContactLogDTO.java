package com.yajun.green.facade.dto.contact;

import com.yajun.green.domain.contact.ZuPinContact;

/**
 * User: Jack Wang
 * Date: 17-8-15
 * Time: 下午4:55
 */
public class ZuPinContactLogDTO  {

    private String uuid;
    private String happenTime;
    private String actorManUuid;
    private String actorManName;
    private String description;

    public ZuPinContactLogDTO() {
    }

    public ZuPinContactLogDTO(String uuid, String happenTime, String actorManName, String description) {
        this.uuid = uuid;
        this.happenTime = happenTime;
        this.actorManName = actorManName;
        this.description = description;
    }

    /**********************************GETTER/SETTER************************************/

    public String getHappenTime() {
        return happenTime;
    }

    public void setHappenTime(String happenTime) {
        this.happenTime = happenTime;
    }

    public String getActorManUuid() {
        return actorManUuid;
    }

    public void setActorManUuid(String actorManUuid) {
        this.actorManUuid = actorManUuid;
    }

    public String getActorManName() {
        return actorManName;
    }

    public void setActorManName(String actorManName) {
        this.actorManName = actorManName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    private ZuPinContact zuPinContact;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public ZuPinContact getZuPinContact() {
        return zuPinContact;
    }

    public void setZuPinContact(ZuPinContact zuPinContact) {
        this.zuPinContact = zuPinContact;
    }
}
