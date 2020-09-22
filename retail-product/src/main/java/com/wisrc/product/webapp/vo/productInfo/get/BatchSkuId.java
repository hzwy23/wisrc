package com.wisrc.product.webapp.vo.productInfo.get;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@ApiModel
public class BatchSkuId {
    @NotEmpty(message = "skuIdList不能为空")
    @ApiModelProperty(value = "skuId编码集合", required = true)
    private List<String> skuIdList;
}
