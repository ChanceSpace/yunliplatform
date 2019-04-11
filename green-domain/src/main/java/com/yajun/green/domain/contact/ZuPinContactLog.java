package com.yajun.green.domain.contact;

import com.yajun.green.common.domain.EntityBase;
import org.joda.time.DateTime;

/**
 * User: Jack Wang
 * Date: 17-8-15
 * Time: 下午4:55
 */
public class ZuPinContactLog extends EntityBase {

    private DateTime happenTime;
    private String actorManUuid;
    private String actorManName;
    private String description;

    private ZuPinContact zuPinContact;

    public ZuPinContactLog() {
    }

    public ZuPinContactLog(String actorManUuid, String actorManName, String description) {
        this.happenTime = new DateTime();
        this.actorManUuid = actorManUuid;
        this.actorManName = actorManName;
        this.description = description;
    }

    public ZuPinContactLog(String actorManUuid, String actorManName, String description, ZuPinContact zuPinContact) {
        this.happenTime = new DateTime();
        this.actorManUuid = actorManUuid;
        this.actorManName = actorManName;
        this.description = description;
        this.zuPinContact = zuPinContact;
    }

    /**********************************GETTER/SETTER************************************/

    public DateTime getHappenTime() {
        return happenTime;
    }

    public void setHappenTime(DateTime happenTime) {
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

    public ZuPinContact getZuPinContact() {
        return zuPinContact;
    }

    public void setZuPinContact(ZuPinContact zuPinContact) {
        this.zuPinContact = zuPinContact;
    }
}
