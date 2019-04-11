package com.yajun.green.domain.xiaoshou;

import com.yajun.green.common.domain.Document;
import org.springframework.web.multipart.MultipartFile;

/**
 * User: Jack Wang
 * Date: 17-8-15
 * Time: 上午11:40
 */
public class XiaoShouContactFile extends Document {

    private String actorManUuid;
    private String actorManName;

    private XiaoShouContact xiaoShouContact;

    public XiaoShouContactFile() {
    }

    public XiaoShouContactFile(MultipartFile file, String actorManUuid, String actorManName) {
        super(file, "");
        this.actorManUuid = actorManUuid;
        this.actorManName = actorManName;
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

    public XiaoShouContact getXiaoShouContact() {
        return xiaoShouContact;
    }

    public void setXiaoShouContact(XiaoShouContact xiaoShouContact) {
        this.xiaoShouContact = xiaoShouContact;
    }
}
