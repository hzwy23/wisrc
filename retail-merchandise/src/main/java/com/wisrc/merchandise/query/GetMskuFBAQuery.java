package com.wisrc.merchandise.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class GetMskuFBAQuery {
    @ApiModelProperty(value = "店铺编号")
    private String shopId;
    @ApiModelProperty(value = "销售状态")
    private Integer salesStatusCd;
    @ApiModelProperty(value = "配送方式")
    private Integer deliveryMode;
    @ApiModelProperty(value = "关键字(Msku/产品编号/品名)")
    private String findKey;
    @ApiModelProperty(value = "产品编号集合")
    private List<String> storeSkuDealted;
    private List mskuPrivilege;
    private String userId;
}
