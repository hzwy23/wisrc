package com.wisrc.replenishment.webapp.vo;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

public class CustomsClerlanceVo {

    @Valid
    @NotEmpty
    @ApiModelProperty(value = "包含产品的商店id和产品id")
    private List<ProductDetailsEnityVO> productList;

    @NotEmpty(message = "运单号不能为空")
    private String waybillId;

    public List<ProductDetailsEnityVO> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductDetailsEnityVO> productList) {
        this.productList = productList;
    }

    public String getWaybillId() {
        return waybillId;
    }

    public void setWaybillId(String waybillId) {
        this.waybillId = waybillId;
    }
}
