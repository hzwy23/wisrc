package com.wisrc.quality.webapp.vo.inspectionItemCheckDetails.add;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
@ApiModel
public class InspectionItemCheckDetailsVO {

    @ApiModelProperty(value = "测试内容")
    private String inspectionContent;

    @ApiModelProperty(value = "测试数量")
    private Integer inspectionQuantity;

    @ApiModelProperty(value = "问题描述")
    private String problemDescription;

    @ApiModelProperty(value = "测试结果/数据")
    private String inspectionResults;


    @Range(min = 0, message = "cri参数[min]的值大于零")
    @ApiModelProperty(value = "cri")
    private Integer cri;

    @Range(min = 0, message = "maj参数[min]的值大于零")
    @ApiModelProperty(value = "maj")
    private Integer maj;

    @Range(min = 0, message = "min参数[min]的值大于零")
    @ApiModelProperty(value = "min")
    private Integer min;

    @ApiModelProperty(value = "判定编码")
    private Integer judgmentCd;
}
