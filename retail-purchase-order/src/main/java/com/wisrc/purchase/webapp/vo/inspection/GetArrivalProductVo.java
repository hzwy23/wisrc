package com.wisrc.purchase.webapp.vo.inspection;

import com.wisrc.purchase.webapp.vo.PageVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GetArrivalProductVo extends PageVo {
    @ApiModelProperty(value = "采购订单号", required = false)
    private String orderId;

    @ApiModelProperty(value = "供应商名称", required = false)
    private String supplierName;

    @ApiModelProperty(value = "SKU", required = false)
    private String skuId;

    @ApiModelProperty(value = "产品中文名", required = false)
    private String productNameCN;
    @ApiModelProperty(value = "到货单状态", required = false)
    private Integer statusCd;
}
