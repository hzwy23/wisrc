package com.wisrc.replenishment.webapp.dto.WayBillInfo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Date;

@Data
public class ExcelMskuAllDto {
    @ApiModelProperty(value = "SHIPMENT ID/调拨单号")
    private String relationId;
    @ApiModelProperty(value = "下单时间")
    private Date createTime;
    @ApiModelProperty(value = "申请人")
    private String employee;
    @ApiModelProperty(value = "店铺/目的仓")
    private String relationPlace;
    @ApiModelProperty(value = "产品名称")
    private String productName;
    @ApiModelProperty(value = "库存SKU")
    private String storeSku;
    @ApiModelProperty(value = "MSKU")
    private String commodityId;
    @ApiModelProperty(value = "MSKU编号")
    private String mskuId;
    @ApiModelProperty(value = "下单数量")
    private Integer replenishmentQuantity;
    @ApiModelProperty(value = "发货数量")
    private Integer deliveryNumber;
    @ApiModelProperty(value = "箱数")
    private Integer numberOfBoxes;
    @ApiModelProperty(value = "仓库出货数据")
    private String warehouseDelivery;
}
