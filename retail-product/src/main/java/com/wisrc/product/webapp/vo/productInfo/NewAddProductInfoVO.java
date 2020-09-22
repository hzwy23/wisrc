package com.wisrc.product.webapp.vo.productInfo;

import com.wisrc.product.webapp.vo.declareLabel.add.AddDeclareLabelVO;
import com.wisrc.product.webapp.vo.productAccessory.add.AddProductAccessoryVO;
import com.wisrc.product.webapp.vo.productPackingInfo.ProductPackingInfoVO;
import com.wisrc.product.webapp.vo.productSales.ProductSalesVO;
import com.wisrc.product.webapp.vo.productInfo.add.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 聚合产品信息
 */
@Data
@ApiModel
@Valid
public class NewAddProductInfoVO {
    //图片
    @Valid
    @ApiModelProperty(value = "产品图片")
    private List<ProductImagesVO> imagesList;

    //信息
    @Valid
    @NotNull(message = "产品基础信息参数[define]不能为空")
    @ApiModelProperty(value = "产品基础信息", required = true)
    private ProductDefineVO define;

    //加工
    @Valid
    @ApiModelProperty(value = "产品加工")
    private List<ProductMachineInfoVO> machineInfoList;

    //包材
    @Valid
    @ApiModelProperty(value = "包材")
    private List<ProductMachineInfoVO> packingMaterialList;

    //描述
    @Valid
    @ApiModelProperty(value = "产品描述")
    private ProductDetailsInfoVO detailsInfo;

    //申报规格
    @Valid
    @ApiModelProperty(value = "产品申报规格")
    private ProductSpecificationsInfoVO specificationsInfo;

    //产品申报信息
    @Valid
    @ApiModelProperty(value = "产品申报信息")
    private ProductDeclareInfoVO declareInfo;


    //特性标签(新改)
    @Valid
    @ApiModelProperty(value = "特性标签(新改)")
    private List<AddDeclareLabelVO> declareLabelList;

    //配件(新增)
    @Valid
    @ApiModelProperty(value = "配件(新增)")
    private List<AddProductAccessoryVO> accessoryList;

    //装箱信息(新增)
    @Valid
    @ApiModelProperty(value = "装箱信息(新增)")
    private ProductPackingInfoVO packingInfo;

    //销售信息(新增)
    @Valid
    @NotNull(message = "销售信息[productSales]不能为空")
    @ApiModelProperty(value = "销售信息(新增)", required = true)
    private ProductSalesVO productSales;

//    //产品申报自定义标签数组
//    @Valid
//    @ApiModelProperty(value = "产品申报自定义标签数组")
//    private List<CustomLabelVO> customLabelList;
//
//    //产品申报自定义标签数组
//    @Valid
//    @ApiModelProperty(value = "产品自定义配件数组")
//    private List<CustomAccessoryVO> customAccessoryList;

}
