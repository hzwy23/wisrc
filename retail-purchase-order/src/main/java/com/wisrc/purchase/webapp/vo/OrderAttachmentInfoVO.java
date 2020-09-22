package com.wisrc.purchase.webapp.vo;

import com.wisrc.purchase.webapp.entity.OrderAttachmentInfoEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;

@ApiModel
public class OrderAttachmentInfoVO {
    @ApiModelProperty(value = "唯一ID", hidden = true)
    private String uuid;
    @ApiModelProperty(value = "采购订单号", hidden = true)
    private String orderId;
    @ApiModelProperty(value = "上传附件url")
    private String attachmentUrl;

    public static final OrderAttachmentInfoVO toVO(OrderAttachmentInfoEntity ele) {
        OrderAttachmentInfoVO vo = new OrderAttachmentInfoVO();
        BeanUtils.copyProperties(ele, vo);
        return vo;
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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "OrderAttachmentInfoVO{" +
                "uuid='" + uuid + '\'' +
                ", orderId='" + orderId + '\'' +
                ", attachmentUrl='" + attachmentUrl + '\'' +
                '}';
    }
}
