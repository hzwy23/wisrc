package com.wisrc.merchandise.dto.msku;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GetMskuFBADto {
    @ApiModelProperty(value = "商品id")
    private String id;
    @ApiModelProperty(value = "商品sku编号")
    private String mskuId;
    @ApiModelProperty(value = "店铺编号")
    private String shopId;
    @ApiModelProperty(value = "店铺名称")
    private String shopName;
    @ApiModelProperty(value = "商品名称")
    private String mskuName;
    @ApiModelProperty(value = "产品编号")
    private String skuId;
    @ApiModelProperty(value = "产品名称")
    private String productName;
    @ApiModelProperty(value = "负责人")
    private String employeeName;
    @ApiModelProperty(value = "销售状态编码")
    private Integer salesStatusCd;
    @ApiModelProperty(value = "销售状态")
    private String salesStatusDesc;
    @ApiModelProperty(value = "FBA在仓")
    private Integer fbaOnWarehouseStockNum;
    @ApiModelProperty(value = "FBA在途")
    private Integer fbaOnWayStockNum;
    @ApiModelProperty(value = "产品安全库存天数")
    private Integer safetyStockDays;
    @ApiModelProperty(value = "图片")
    private String picture;
    @ApiModelProperty(value = "asin")
    private String asin;
    @ApiModelProperty(value = "msku安全库存天数")
    private Integer mskuSafetyStockDays;
}
