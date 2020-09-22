package com.wisrc.merchandise.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class MskuSearchQuery {
    @ApiModelProperty(value = "asin")
    private String asin;

    @ApiModelProperty(value = "产品编号")
    private List skuids;

    @ApiModelProperty(value = "商品名称")
    private String mskuName;
    private List mskuPrivilege;
    private String userId;
}
