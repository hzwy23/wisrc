package com.wisrc.order.webapp.dto.saleNvoice;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OrderCommodityDto {
    @ApiModelProperty(value = "图片")
    private String picture;

    @ApiModelProperty(value = "商品编号")
    private String mskuId;

    @ApiModelProperty(value = "商品名称")
    private String mskuName;

    @ApiModelProperty(value = "库存SKU")
    private String skuId;

    @ApiModelProperty(value = "产品中文名")
    private String productName;

    @ApiModelProperty(value = "状态")
    private String statusName;

    @ApiModelProperty(value = "属性")
    private String attributeDesc;

    @ApiModelProperty(value = "数量")
    private Integer quantity;

    @ApiModelProperty(value = "重量（kg）")
    private String weight;

    @ApiModelProperty(value = "仓库")
    private String warehouseName;

//    @ApiModelProperty(value = "单价")

//    @ApiModelProperty(value = "可用库存")

}
