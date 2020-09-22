package com.wisrc.warehouse.webapp.vo.reportLossStatement.add;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@ApiModel
public class AddReportLossStatementVO {

    @NotNull(message = "仓库编码为必填参数")
    @ApiModelProperty(value = "仓库编码", required = true)
    private String warehouseId;

    @NotNull
    @Range(min = 0, max = 1, message = "产品是否贴标参数不合法")
    @ApiModelProperty(value = "产品是否贴标，0:不贴标，1：贴标", required = true)
    private Integer labelFlag;

    @NotBlank(message = "报损原因为必填参数")
    @Length(max = 500)
    @ApiModelProperty(value = "报损原因", required = true)
    private String reportLossReason;

    @ApiModelProperty(value = "附件地址")
    private String annexAddress;

    @Valid
    @NotEmpty(message = "产品清单[productInfoList]不能为空")
    @ApiModelProperty(value = "产品清单")
    private List<AddProductInfoVO> productInfoList;
}
