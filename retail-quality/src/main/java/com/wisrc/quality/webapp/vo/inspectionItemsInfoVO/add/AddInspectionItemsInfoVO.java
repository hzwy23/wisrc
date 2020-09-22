package com.wisrc.quality.webapp.vo.inspectionItemsInfoVO.add;

import com.wisrc.quality.webapp.vo.inspectionItemCheckDetails.add.InspectionItemCheckDetailsVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@ApiModel
public class AddInspectionItemsInfoVO {

    @NotNull(message = "检验项目[itemsCd]不能为空")
    @ApiModelProperty(value = "检验项目编码", required = true)
    private Integer itemsCd;

    @ApiModelProperty(value = "cri合计")
    private Integer criAll;

    @ApiModelProperty(value = "maj合计")
    private Integer majAll;

    @ApiModelProperty(value = "min合计")
    private Integer minAll;

    @ApiModelProperty(value = "检验结果编码")
    private Integer inspectionResultCd;

    @ApiModelProperty(value = "criAQL")
    private Double criAQL;

    @ApiModelProperty(value = "criAc")
    private Double criAc;

    @ApiModelProperty(value = "criRe")
    private Double criRe;

    @ApiModelProperty(value = "majAQL")
    private Double majAQL;

    @ApiModelProperty(value = "majAc")
    private Double majAc;

    @ApiModelProperty(value = "majRe")
    private Double majRe;
    @ApiModelProperty(value = "minAQL")
    private Double minAQL;

    @ApiModelProperty(value = "minAc")
    private Double minAc;

    @ApiModelProperty(value = "minRe")
    private Double minRe;


    @Valid
    @NotEmpty(message = "数据编码参数[dataInfoVOList]不能为空")
    @ApiModelProperty(value = "数据编码信息", required = true)
    private List<InspectionItemCheckDetailsVO> itemCheckDetailsList;


}
