package com.wisrc.product.webapp.vo.productInfo.add;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;


/**
 * 产品定义信息
 */
@Data
@Valid
public class ProductDefineVO {

    @ApiModelProperty(value = "产品SKU")
    private String skuId;

    @NotBlank(message = "参数[skuNameZh]不能为空")
    @ApiModelProperty(value = "产品中文名", required = true)
    private String skuNameZh;


    @NotBlank(message = "参数[imageClassifyCd]不能为空")
    @ApiModelProperty(value = "分类id", required = true)
    private String classifyCd;

    @ApiModelProperty(hidden = true)
    private String createUser;

    @ApiModelProperty(value = "是否加工，0：不需要，1：需要", required = true)
    private Integer machineFlag;

    @ApiModelProperty(value = "是否需要包材，0：不需要，1：需要", required = true)
    private Integer packingFlag;

    @NotBlank(message = "参数[skuNameEn]不能为空")
    @ApiModelProperty(value = "产品英文名", required = true)
    private String skuNameEn;

    @NotBlank(message = "参数[skuShortName]不能为空")
    @Size(min = 1, max = 5, message = "产品简写由一到五位大写字母或数字组成")
    @ApiModelProperty(value = "产品简写", required = true)
    private String skuShortName;

    @ApiModelProperty(value = "分类编码集合 （无用数据 ，为配合前段而使用）")
    private List<String> classifyCdArray;


    //============================

    @NotNull(message = "参数[typeCd]不能为空")
    @ApiModelProperty(value = "类型(1:产品,2:包装)", required = true)
    private Integer typeCd;

    @NotNull(message = "参数[purchaseReferencePrice]不能为空")
    @ApiModelProperty(value = "采购参考价", required = true)
    private Double purchaseReferencePrice;
    //============================


}
