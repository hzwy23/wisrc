package com.wisrc.product.webapp.vo.customLabel;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CustomLabelVO {

//    @NotNull(message = "销售信息[productSales]不能为空")
//    @Range(min = 1001, message = "自定义申报标签编码从1001开始")
//    @ApiModelProperty(value = "自定义申报标签编码", required = true)
//    private Integer labelCd;

    @NotBlank(message = "产品申报标签描述[labelDesc]不能为空")
    @ApiModelProperty(value = "自定义申报标签描述", required = true)
    private String labelDesc;

    private String labelText;
}
