package com.wisrc.product.webapp.vo.set;

import com.wisrc.product.webapp.vo.productInfo.add.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 聚合产品信息
 */
@Data
@ApiModel
public class SetProductInfoVO {
    //图片
    @NotNull(message = "参数[imagesList]不能为空")
    @ApiModelProperty(value = "产品图片", required = true)
    private List<ProductImagesVO> imagesList;

    //信息
    @NotNull(message = "参数[define]不能为空")
    @ApiModelProperty(value = "产品基础信息", required = true)
    private SetProductDefineVO define;

    //加工
    @NotNull(message = "参数[machineInfoList]不能为空")
    @ApiModelProperty(value = "产品加工", required = true)
    private List<ProductMachineInfoVO> machineInfoList;

    //描述
    @NotNull(message = "参数[detailsInfo]不能为空")
    @ApiModelProperty(value = "产品描述", required = true)
    private ProductDetailsInfoVO detailsInfo;

    //申报规格
    @NotNull(message = "参数[specificationsInfo]不能为空")
    @ApiModelProperty(value = "产品申报规格", required = true)
    private ProductSpecificationsInfoVO specificationsInfo;

    //申报规格
    @NotNull(message = "参数[declareInfo]不能为空")
    @ApiModelProperty(value = "产品申报信息", required = true)
    private ProductDeclareInfoVO declareInfo;

    //申报标签
    @NotNull(message = "参数[declareLabel]不能为空")
    @ApiModelProperty(value = "特性标签", required = true)
    private ProductDeclareLabelVO declareLabel;

}
