package com.wisrc.purchase.webapp.vo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@Api(tags = "订单所有包含的信息")
public class OrderBasisInfoAllVO {
    @ApiModelProperty(value = "订单基本信息", position = 1)
    private OrderBasisInfoVO orderVO;
    @ApiModelProperty(value = "此订单产品相关信息", position = 2)
    private List<AddDetailsProdictAllVO> eleVOList;

    public OrderBasisInfoVO getOrderVO() {
        return orderVO;
    }

    public void setOrderVO(OrderBasisInfoVO orderVO) {
        this.orderVO = orderVO;
    }

    public List<AddDetailsProdictAllVO> getEleVOList() {
        return eleVOList;
    }

    public void setEleVOList(List<AddDetailsProdictAllVO> eleVOList) {
        this.eleVOList = eleVOList;
    }
}
