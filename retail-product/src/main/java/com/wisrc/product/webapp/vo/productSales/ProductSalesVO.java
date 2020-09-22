package com.wisrc.product.webapp.vo.productSales;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;


/**
 * 产品定义信息实体类
 */
@Data
@ApiModel
public class ProductSalesVO {
    @NotNull(message = "销售状态编码[salesStatusCd]错误")
    @ApiModelProperty(value = "销售状态")
    private Integer salesStatusCd;

    @ApiModelProperty(value = "安全库存天数")
    private Integer safetyStockDays;

    @ApiModelProperty(value = "国际运输天数")
    private Integer internationalTransportDays;

}
