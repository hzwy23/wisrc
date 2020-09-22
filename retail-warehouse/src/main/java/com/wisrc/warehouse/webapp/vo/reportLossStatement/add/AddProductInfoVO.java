package com.wisrc.warehouse.webapp.vo.reportLossStatement.add;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AddProductInfoVO {
    @NotBlank(message = "skuId为必填")
    @ApiModelProperty(value = "SKU", required = true)
    private String skuId;

    @ApiModelProperty(value = "FnSKU")
    private String fnSku;

    @NotNull(message = "报损数量不能为空")
    @Range(min = 1, message = "报损数量不合法")
    @ApiModelProperty(value = "报损数量", required = true)
    private Integer reportedLossAmount;
}
