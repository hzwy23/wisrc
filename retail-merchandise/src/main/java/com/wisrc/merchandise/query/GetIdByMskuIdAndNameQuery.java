package com.wisrc.merchandise.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class GetIdByMskuIdAndNameQuery {
    @ApiModelProperty(value = "商品编号", required = false)
    private String mskuId;

    @ApiModelProperty(value = "商品名称", required = false)
    private String mskuName;
    private List mskuPrivilege;
    private String userId;
}
