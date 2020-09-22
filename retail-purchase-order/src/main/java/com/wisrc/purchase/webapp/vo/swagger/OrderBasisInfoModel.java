package com.wisrc.purchase.webapp.vo.swagger;

import com.wisrc.purchase.webapp.vo.OrderBasisInfoVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class OrderBasisInfoModel {
    @ApiModelProperty(value = "返回状态吗", position = 1)
    private int code;

    @ApiModelProperty(value = "返回消息", position = 2)
    private int msg;

    @ApiModelProperty(value = "订单基本信息字段", position = 3)
    private OrderBasisInfoVO orderBasisInfoVO;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getMsg() {
        return msg;
    }

    public void setMsg(int msg) {
        this.msg = msg;
    }

    public OrderBasisInfoVO getOrderBasisInfoVO() {
        return orderBasisInfoVO;
    }

    public void setOrderBasisInfoVO(OrderBasisInfoVO orderBasisInfoVO) {
        this.orderBasisInfoVO = orderBasisInfoVO;
    }
}
