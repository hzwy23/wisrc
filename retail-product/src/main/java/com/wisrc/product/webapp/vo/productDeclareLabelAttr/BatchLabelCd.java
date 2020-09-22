package com.wisrc.product.webapp.vo.productDeclareLabelAttr;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@ApiModel
public class BatchLabelCd {
    @NotEmpty(message = "LabelCdList不能为空")
    @ApiModelProperty(value = "LabelCdList标签编码集合", required = true)
    List<Integer> LabelCdList;

}
