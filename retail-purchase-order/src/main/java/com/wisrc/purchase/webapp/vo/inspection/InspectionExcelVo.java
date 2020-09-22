package com.wisrc.purchase.webapp.vo.inspection;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@ApiModel(description = "导出验货申请单/提货单信息")
public class InspectionExcelVo {
    @ApiModelProperty(value = "导出数据Id", required = true)
    @NotEmpty(message = "请选择至少勾选一条")
    private List<String> arrivalProductId;
}
