package com.wisrc.purchase.webapp.vo.purchaseReturn.set;

import com.wisrc.purchase.webapp.utils.valid.PositiveMaxFour;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel
public class SetPurchaseReturnDetailsVO {
    @NotBlank(message = "参数[rejectionDateStr]为必填")
    @ApiModelProperty(value = "库存SKU", required = true)
    private String skuId;

    @NotNull(message = "退货数量为0或者正整数")
    @Range(min = 0, message = "退货数量为0或者正整数")
    @ApiModelProperty(value = "退货数量", required = true)
    private Integer returnQuantity;
    //备品数可以不填 默认为0
    @Range(min = 0, message = "备品数量为0或者正整数")
    @ApiModelProperty(value = "备品数量,默认为数量0")
    private Integer spareQuantity;

    @ApiModelProperty(value = "批次号")
    private String batchNumber;

    @ApiModelProperty(value = "不含税单价")
    @PositiveMaxFour(message = "不含税单价必须为4位小数以下")
    private Double unitPriceWithoutTax;

    @ApiModelProperty(value = "不含税金额")
    private Double amountWithoutTax;

    @ApiModelProperty(value = "税率")
    private Double taxRate;

    @ApiModelProperty(value = "含税单价")
    @PositiveMaxFour(message = "含税单价必须为4位小数以下")
    private Double unitPriceWithTax;

    @ApiModelProperty(value = "含税金额")
    private Double amountWithTax;
}
