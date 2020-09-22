package com.wisrc.quality.webapp.vo.inspectionApply;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class InspectionProdEditVO {

    @ApiModelProperty(value = "产品验货id")
    private String inspectionProductId;
    @ApiModelProperty(value = "申请验货数量")
    @NotNull
    private Integer applyInspectionQuantity;

    public String getInspectionProductId() {
        return inspectionProductId;
    }

    public void setInspectionProductId(String inspectionProductId) {
        this.inspectionProductId = inspectionProductId;
    }
}
