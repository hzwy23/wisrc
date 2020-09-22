package com.wisrc.product.webapp.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 首页模糊查询参数
 */
@Data
@ApiModel
public class GetProductInfoVO {
    //需要忽略产品SKU
    @ApiModelProperty(value = "需要忽略产品SKU", required = false)
    private String[] ignoreSkuIds;
    //查询产品SKU
    @ApiModelProperty(value = "查询产品SKU")
    private String skuId;   //产品SKU

    @ApiModelProperty(value = "产品中文名")
    private String skuNameZh;   //产品中文名

    @ApiModelProperty(value = "状态 ")
    private Integer statusCd;

    @ApiModelProperty(value = "分类名称ID")
    private String classifyCd;

    @ApiModelProperty(value = "SKU 和 产品中文名模糊搜索")
    private String keyword;

    @ApiModelProperty(value = "忽略图片")
    private boolean ignoreImages;
    @ApiModelProperty(value = "类型")
    private Integer typeCd;
    @ApiModelProperty(value = "销售状态")
    private Integer salesStatusCd;

    public boolean getIgnoreImages() {
        return ignoreImages;
    }

    public void setIgnoreImages(boolean ignoreImages) {
        this.ignoreImages = ignoreImages;
    }


}
