package com.wisrc.quality.webapp.dto.inspection;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "获取验货表单所有产品")
public class GetInspectionProductDto {
    @ApiModelProperty(value = "产品验货ID")
    private String inspectionProductId;

    @ApiModelProperty(value = "SKU")
    private String skuId;

    @ApiModelProperty(value = "产品中文名")
    private String productName;

    @ApiModelProperty(value = "完工数量")
    private Integer finishQuantity;

    @ApiModelProperty(value = "提货数量")
    private Integer deliveryQuantity;

    @ApiModelProperty(value = "提备品数")
    private Integer deliverySpareQuantity;

    @ApiModelProperty(value = "运费")
    private Double freight;

    @ApiModelProperty(value = "验货数量")
    private Integer inspectionQuantity;

    @ApiModelProperty(value = "允收数")
    private Integer allowedQuantity;

    @ApiModelProperty(value = "拒收数")
    private Integer rejectionQuantity;

    @ApiModelProperty(value = "收货数")
    private Integer receiptQuantity;

    @ApiModelProperty(value = "收备品数")
    private Integer receiptSpareQuantity;

    @ApiModelProperty(value = "入库数")
    private Integer putInStorageQuantity;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "备品率(%)")
    private Double spareRate;

    @ApiModelProperty(value = "体积(m3)")
    private Double packVolume;

    @ApiModelProperty(value = "毛重(kg)")
    private Double grossWeight;
}
