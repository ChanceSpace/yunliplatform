package com.yajun.green.facade.dto.pay;

/**
 * Created by chance on 2017/10/18.
 */
public class PayOrderFileDTO {
    private String uuid;
    private String actorManName;
    private String actorManUuid;
    private String uploadFileName;
    private String actualFileName;
    private String uploadTime;

    public PayOrderFileDTO() {
    }

    public PayOrderFileDTO(String uuid, String actorManName, String actorManUuid, String uploadFileName, String actualFileName, String uploadTime) {
        this.uuid = uuid;
        this.actorManName = actorManName;
        this.actorManUuid = actorManUuid;
        this.uploadFileName = uploadFileName;
        this.actualFileName = actualFileName;
        this.uploadTime = uploadTime;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getActorManName() {
        return actorManName;
    }

    public void setActorManName(String actorManName) {
        this.actorManName = actorManName;
    }

    public String getActorManUuid() {
        return actorManUuid;
    }

    public void setActorManUuid(String actorManUuid) {
        this.actorManUuid = actorManUuid;
    }

    public String getUploadFileName() {
        return uploadFileName;
    }

    public void setUploadFileName(String uploadFileName) {
        this.uploadFileName = uploadFileName;
    }

    public String getActualFileName() {
        return actualFileName;
    }

    public void setActualFileName(String actualFileName) {
        this.actualFileName = actualFileName;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }
}

