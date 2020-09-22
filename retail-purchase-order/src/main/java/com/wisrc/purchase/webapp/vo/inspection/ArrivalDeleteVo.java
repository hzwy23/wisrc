package com.wisrc.purchase.webapp.vo.inspection;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@ApiModel(value = "删除到货通知新版")
public class ArrivalDeleteVo {
    @ApiModelProperty(value = "到货通知ID", required = true)
    @NotEmpty
    private List<String> arrivalId;
}
