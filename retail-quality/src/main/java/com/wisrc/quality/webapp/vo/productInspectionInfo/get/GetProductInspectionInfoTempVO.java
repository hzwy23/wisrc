package com.wisrc.quality.webapp.vo.productInspectionInfo.get;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class GetProductInspectionInfoTempVO {
    @ApiModelProperty(value = "采购订单号")
    private String purchaseOrderId;

    @ApiModelProperty(value = "开始日期")
    private String inspectionDateStart;

    @ApiModelProperty(value = "结束日期")
    private String inspectionDateEnd;

    @ApiModelProperty(value = "状态")
    private Integer inspectionStatusCd;

    @ApiModelProperty(value = "最终判定编码")
    private Integer finalDetermineCd;

    @ApiModelProperty(value = "单据来源类型")
    private Integer sourceTypeCd;

    @ApiModelProperty(value = "验货人")
    private String employeeId;

    @ApiModelProperty(value = "skuId")
    private String skuId;

    @ApiModelProperty(value = "供应商/产品中文名")
    private String keyWords;

}
