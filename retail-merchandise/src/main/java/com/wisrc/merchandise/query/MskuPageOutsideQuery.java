package com.wisrc.merchandise.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class MskuPageOutsideQuery {
    private String shopId;
    private String mskuId;
    private String manager;
    private Integer salesStatus;
    private String findKey;
    private List storeSkuDealted;
    private List salesStatusList;
    @ApiModelProperty(value = "是否获取已删除数据", required = false)
    private Integer doesDelete;
    private List mskuPrivilege;
    private String userId;
}
