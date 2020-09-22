package com.wisrc.sales.webapp.vo.saleEstimate;

import com.wisrc.sales.webapp.vo.PageVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class SaleEstimatePageVo extends PageVo {
    @ApiModelProperty(value = "shopId", required = false)
    private String shopId;
    @ApiModelProperty(value = "mskuId", required = false)
    private String mskuId;
    @ApiModelProperty(value = "asinId", required = false)
    private String asinId;
    @ApiModelProperty(value = "skuId", required = false)
    private String skuId;
    @ApiModelProperty(value = "saleStatus", required = false)
    private Integer saleStatus;
    @ApiModelProperty(value = "productName", required = false)
    private String productName;
    @ApiModelProperty(value = "数据时间")
    private String asOfDate;
}
