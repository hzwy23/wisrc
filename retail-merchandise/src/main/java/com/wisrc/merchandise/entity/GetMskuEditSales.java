package com.wisrc.merchandise.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Date;

@Data
public class GetMskuEditSales {
    private String id;
    private String mskuId;
    private String shopId;
    @ApiModelProperty(value = "销售状态")
    private Integer salesStatusCd;
    @ApiModelProperty(value = "可售库存")
    private Integer fbaOnWarehouseStockNum;
    @ApiModelProperty(value = "启用上架时间")
    private Date storeInTime;
    private String asin;
    private String marketplaceId;
}
