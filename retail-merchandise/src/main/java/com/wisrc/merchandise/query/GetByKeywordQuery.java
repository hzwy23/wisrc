package com.wisrc.merchandise.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class GetByKeywordQuery {
    @ApiModelProperty(value = "负责人（非同步参数）")
    private String employeeId;
    @ApiModelProperty(value = "产品编号")
    private List<String> storeSkuDealted;
    private List mskuPrivilege;
    private String userId;
}
