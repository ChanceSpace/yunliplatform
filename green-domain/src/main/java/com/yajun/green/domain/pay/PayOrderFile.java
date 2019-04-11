package com.yajun.green.domain.pay;

import com.yajun.green.common.domain.Document;
import com.yajun.green.domain.contact.ZuPinContact;
import org.springframework.web.multipart.MultipartFile;

/**
 * User: Jack Wang
 * Date: 17-8-15
 * Time: 上午11:40
 */
public class PayOrderFile extends Document {

    private String actorManUuid;
    private String actorManName;

    private PayOrder payOrder;

    public PayOrderFile() {
    }

    public PayOrderFile(MultipartFile file, String actorManUuid, String actorManName) {
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

    public PayOrder getPayOrder() {
        return payOrder;
    }

    public void setPayOrder(PayOrder payOrder) {
        this.payOrder = payOrder;
    }
}
