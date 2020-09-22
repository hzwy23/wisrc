package com.wisrc.order.webapp.vo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@Api(tags = "订单商品拆分数量集合")
public class SplitOrderInfoListVO {
    @ApiModelProperty(value = " 订单号")
    private String orderId;
    @ApiModelProperty(value = "拆分商品信息")
    private List<SplitOrderInfoVO> splitOrderInfoVO;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public List<SplitOrderInfoVO> getSplitOrderInfoVO() {
        return splitOrderInfoVO;
    }

    public void setSplitOrderInfoVO(List<SplitOrderInfoVO> splitOrderInfoVO) {
        this.splitOrderInfoVO = splitOrderInfoVO;
    }
}
