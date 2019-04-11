package com.yajun.green.facade.dto.contact;

/**
 * User: Jack Wang
 * Date: 17-8-15
 * Time: 下午3:07
 */
public class ZuPinContactFileDTO {

    private String uuid;
    private String actorManName;
    private String uploadFileName;
    private String actualFileName;
    private String uploadTime;

    public ZuPinContactFileDTO() {
    }

    public ZuPinContactFileDTO(String uuid, String actorManName, String uploadFileName, String actualFileName, String uploadTime) {
        this.uuid = uuid;
        this.actorManName = actorManName;
        this.uploadFileName = uploadFileName;
        this.actualFileName = actualFileName;
        this.uploadTime = uploadTime;
    }

    /*******************************************GETTER/SETTER***********************************************/

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
