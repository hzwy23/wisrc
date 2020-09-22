package com.wisrc.purchase.webapp.vo.inspection;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@ApiModel(description = "验货编辑表单")
public class InspectionEditVo extends InspectionVo {
    @ApiModelProperty(value = "验货单产品", required = true)
    @NotEmpty(message = "必须至少要有一个sku")
    @Valid
    private List<InspectionProductEditVo> storeSkuGroup;
}
