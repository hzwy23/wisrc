package com.wisrc.purchase.webapp.vo.supplierDateOffer;

import com.wisrc.purchase.webapp.vo.PageVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class SupplierDateOfferPageVo extends PageVo {
    @ApiModelProperty(value = "状态")
    private int statusCd;
    @ApiModelProperty(value = "采购员ID")
    private String employeeId;
    @ApiModelProperty(value = "库存SKU")
    private String skuId;
    @ApiModelProperty(value = "产品名称")
    private String productName;
    @ApiModelProperty(value = "供应商名称")
    private String supplierName;
}
