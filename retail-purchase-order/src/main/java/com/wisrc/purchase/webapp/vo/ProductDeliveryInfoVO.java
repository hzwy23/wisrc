package com.wisrc.purchase.webapp.vo;

import com.wisrc.purchase.webapp.entity.ProductDeliveryInfoEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;

@ApiModel
public class ProductDeliveryInfoVO {
    @ApiModelProperty(value = "唯一ID", hidden = true)
    private String delivery_id;
    @ApiModelProperty(value = "订单产品ID", hidden = true)
    private String id;
    @ApiModelProperty(value = "交货日期")
    private String deliveryTime;
    @ApiModelProperty(value = "数量")
    private String number;

    public static final ProductDeliveryInfoVO toVO(ProductDeliveryInfoEntity ele) {
        ProductDeliveryInfoVO vo = new ProductDeliveryInfoVO();
        BeanUtils.copyProperties(ele, vo);
        vo.setDelivery_id(ele.getUuid());
        return vo;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDelivery_id() {
        return delivery_id;
    }

    public void setDelivery_id(String delivery_id) {
        this.delivery_id = delivery_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ProductDeliveryInfoVO{" +
                "delivery_id='" + delivery_id + '\'' +
                ", id='" + id + '\'' +
                ", deliveryTime='" + deliveryTime + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
}
