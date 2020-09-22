package com.wisrc.purchase.webapp.vo.purchaseRejection.set;

import com.wisrc.purchase.webapp.utils.valid.PositiveMaxFour;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@ApiModel
@Valid
public class SetPurchaseRejectionDetailsVO {
    @NotNull(message = "库存[skuId]不能为空")
    @ApiModelProperty(value = "库存SKU")
    private String skuId;

    @NotNull(message = "拒收数量[rejectQuantity]不能为空")
    @ApiModelProperty(value = "拒收数量")
    private Integer rejectQuantity;

    @ApiModelProperty(value = "备品数量")
    private Integer spareQuantity;

    @ApiModelProperty(value = "批次")
    private String batchNumber;

    @ApiModelProperty(value = "不含税单价")
    @PositiveMaxFour(message = "不含税单价必须为4位小数以下")
    private Double unitPriceWithoutTax;

    @ApiModelProperty(value = "不含税金额")
    private Double amountWithoutTax;

    @ApiModelProperty(value = "税率(%)")
    private Double taxRate;

    @ApiModelProperty(value = "含税单价")
    @PositiveMaxFour(message = "含税单价必须为4位小数以下")
    private Double unitPriceWithTax;

    @ApiModelProperty(value = "含税金额")
    private Double amountWithTax;

}
