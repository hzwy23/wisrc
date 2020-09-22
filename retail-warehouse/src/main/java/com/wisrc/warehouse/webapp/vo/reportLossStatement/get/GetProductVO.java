package com.wisrc.warehouse.webapp.vo.reportLossStatement.get;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class GetProductVO {

    @NotBlank(message = "仓库编码为必填参数")
    @ApiModelProperty(value = "仓库编码", required = true)
    private String warehouseId;

    @NotNull
    @Range(min = 0, max = 1, message = "产品是否贴标参数不合法")
    @ApiModelProperty(value = "产品是否贴标，0:不贴标，1：贴标", required = true)
    private Integer labelFlag;

    @ApiModelProperty(value = "SKU")
    private String skuId;

    @ApiModelProperty(value = "FnSKU")
    private String fnSku;
}
