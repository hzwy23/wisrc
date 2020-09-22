package com.wisrc.order.webapp.vo.msku;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class GetMskuListByIdVo {
    @ApiModelProperty(value = "商品id", required = true)
    @NotEmpty
    private List<String> ids;
}
