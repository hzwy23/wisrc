package com.wisrc.sys.webapp.vo.privilegeShop;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@ApiModel
public class BatchPrivilegeShopMSKUVO {
    @NotEmpty(message = "店铺编码集合storeIdList不能为空")
    @ApiModelProperty(value = "店铺编码集合", required = true)
    private List<String> commodityIdList;

    @NotBlank(message = "授权编码authId不能为空")
    @ApiModelProperty(value = "授权编码", required = true)
    private String authId;
}
