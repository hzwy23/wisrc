package com.wisrc.product.webapp.vo.productInfo.add;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Min;

/**
 * 产品申报信息实体类
 */
@Data
@Valid
public class ProductDeclareInfoVO {
    @ApiModelProperty(value = "海关编号")
    private String customsNumber;

    @Min(value = 0, message = "无效的参数[taxRebatePoint]")
    @ApiModelProperty(value = "退税税点")
    private double taxRebatePoint;

    @ApiModelProperty(value = "开票单位")
    private String issuingOffice;

    @ApiModelProperty(value = "申报中文名")
    private String declareNameZh;

    @ApiModelProperty(value = "申报英文名")
    private String declareNameEn;

    @Min(value = 0, message = "无效的参数[declarePrice]")
    @ApiModelProperty(value = "申报价格")
    private double declarePrice;

    @Min(value = 0, message = "无效的参数[singleWeight]")
    @ApiModelProperty(value = "单品重量")
    private double singleWeight;

    // 需求作废 S ==========
    /*@ApiModelProperty(value = "开票品名")
    private String ticketName;*/
    // 需求作废 E ==========

    // 需求变更 S ==========  申报要素  改为 其他要素
    @ApiModelProperty(value = "其他要素（申报要素）")
    private String declarationElements;

    @ApiModelProperty(value = "原产地")
    private String originPlace;

    @ApiModelProperty(value = "材质")
    private String materials;

    @ApiModelProperty(value = "用途")
    private String typicalUse;

    @ApiModelProperty(value = "品牌")
    private String brands;

    @ApiModelProperty(value = "型号")
    private String model;

}
