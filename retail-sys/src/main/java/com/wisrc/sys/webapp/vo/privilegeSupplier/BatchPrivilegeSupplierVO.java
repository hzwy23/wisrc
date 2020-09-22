package com.wisrc.sys.webapp.vo.privilegeSupplier;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@ApiModel
public class BatchPrivilegeSupplierVO {
    @NotEmpty(message = "供应商编码集合supplierIdList不能为空")
    @ApiModelProperty(value = "供应商编码集合", required = true)
    private List<String> supplierIdList;

    @NotBlank(message = "授权编码authId不能为空")
    @ApiModelProperty(value = "授权编码", required = true)
    private String authId;

}
