package com.wisrc.sys.webapp.vo.privilegeWarehouse;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@ApiModel
public class BatchPrivilegeWarehouseVO {
    @NotEmpty(message = "仓库编码集合repositoryIdList不能为空")
    @ApiModelProperty(value = "仓库编码集合", required = true)
    private List<String> repositoryIdList;

    @NotBlank(message = "授权编码authId不能为空")
    @ApiModelProperty(value = "授权编码", required = true)
    private String authId;

}
