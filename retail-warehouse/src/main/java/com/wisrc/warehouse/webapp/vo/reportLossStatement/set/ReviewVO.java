package com.wisrc.warehouse.webapp.vo.reportLossStatement.set;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * 审核
 */
@Data
public class ReviewVO {
    @NotNull
    @ApiModelProperty(value = "报损单号")
    private String reportLossStatementId;

    @NotNull
    @Range(min = 0, max = 1, message = "审核编码， 0 通过，1 不通过")
    @ApiModelProperty(value = "审核编码， 0 通过，1 不通过", required = true)
    private Integer reviewCd;

    @ApiModelProperty(value = "不同意原因")
    private String disagreeReason;
}
