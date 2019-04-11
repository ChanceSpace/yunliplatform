package com.yajun.green.facade.dto.contact.xiaoshou;

/**
 * Created by LiuMengKe on 2017/12/20.
 */
public class ZuPinXiaoShouContactLogDTO {

    private String uuid;
    private String happenTime;
    private String actorManUuid;
    private String actorManName;
    private String description;

    public ZuPinXiaoShouContactLogDTO() {
    }

    public ZuPinXiaoShouContactLogDTO(String uuid, String happenTime, String actorManName, String description) {
        this.uuid = uuid;
        this.happenTime = happenTime;
        this.actorManName = actorManName;
        this.description = description;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

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
}
