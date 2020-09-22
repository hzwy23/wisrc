package com.wisrc.purchase.webapp.entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

@Api(tags = "订单附件信息")
public class OrderAttachmentInfoEntity {
    @ApiModelProperty(value = "唯一ID", hidden = true)
    private String uuid;
    @ApiModelProperty(value = "采购订单号", hidden = true)
    private String orderId;
    @ApiModelProperty(value = "上传附件url")
    private String attachmentUrl;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAttachmentUrl() {
        return attachmentUrl;
    }

    public void setAttachmentUrl(String attachmentUrl) {
        this.attachmentUrl = attachmentUrl;
    }

    @Override
    public String toString() {
        return "OrderAttachmentInfoEntity{" +
                "uuid='" + uuid + '\'' +
                ", orderId='" + orderId + '\'' +
                ", attachmentUrl='" + attachmentUrl + '\'' +
                '}';
    }
}
