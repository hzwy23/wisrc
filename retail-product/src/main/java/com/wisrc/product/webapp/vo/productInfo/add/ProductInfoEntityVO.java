package com.wisrc.product.webapp.vo.productInfo.add;

import com.wisrc.product.webapp.entity.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 聚合产品信息实体与VO便于比对使用的一个类
 */
@Data
@ApiModel
public class ProductInfoEntityVO {
    //图片
    @ApiModelProperty(value = "产品图片")
    private List<ProductImagesEntity> imagesList;
    //信息
    @ApiModelProperty(value = "产品基础信息")
    private ProductDefineEntity define;
    //加工
    @ApiModelProperty(value = "产品加工")
    private List<ProductMachineInfoEntity> machineInfoList;
    //描述
    @ApiModelProperty(value = "产品描述")
    private ProductDetailsInfoEntity detailsInfo;
    //申报规格
    @ApiModelProperty(value = "产品申报规格")
    private ProductSpecificationsInfoEntity specificationsInfo;
    //申报规格
    @ApiModelProperty(value = "产品申报信息")
    private ProductDeclareInfoEntity declareInfo;
    //申报标签
    @ApiModelProperty(value = "特性标签")
    private ProductDeclareLabelEntity declareLabel;

}
