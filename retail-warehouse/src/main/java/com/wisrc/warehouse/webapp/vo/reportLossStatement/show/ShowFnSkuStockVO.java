package com.wisrc.warehouse.webapp.vo.reportLossStatement.show;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ShowFnSkuStockVO {
    @ApiModelProperty(value = "商品ID")
    private String skuId;
    @ApiModelProperty(value = "仓库ID")
    private String warehouseId;
    @ApiModelProperty(value = "仓库名称")
    private String warehouseName;
    private String warehousePosition;
    @ApiModelProperty(value = "入库批次")
    private String enterBatch;
    @ApiModelProperty(value = "生产批次")
    private String productionBatch;
    @ApiModelProperty(value = "在仓库存")
    private Integer onWarehouseStockNum;
    @ApiModelProperty(value = "可用库存数量")
    private Integer enableStockNum;

    private String fnSku;
    private String skuNameZh;
    private String imageUrl;

}
