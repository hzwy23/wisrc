package com.wisrc.purchase.webapp.vo.inspection;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@ApiModel(description = "验货保存表单")
public class InspectionSaveVo extends InspectionVo {
    @ApiModelProperty(value = "车牌号", required = false)
    private String plateNumber;

    @ApiModelProperty(value = "物流单号", required = false)
    private String logisticsId;

    @ApiModelProperty(value = "验货单产品", required = true)
    @NotEmpty(message = "必须至少填写一个sku")
    @Valid
    private List<InspectionProductSaveVo> storeSkuGroup;
}
