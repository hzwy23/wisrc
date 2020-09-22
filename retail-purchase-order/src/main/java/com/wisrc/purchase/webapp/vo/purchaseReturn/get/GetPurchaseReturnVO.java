package com.wisrc.purchase.webapp.vo.purchaseReturn.get;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class GetPurchaseReturnVO {
    @ApiModelProperty(value = "退货单")
    private String returnBill;

    @ApiModelProperty(value = "退货开始日期")
    private String createDateStartStr;

    @ApiModelProperty(value = "退货结束日期")
    private String createDateEndStr;

    @ApiModelProperty(value = "供应商编码")
    private String supplierId;

    @ApiModelProperty(value = "操作人处理人")
    private String employeeId;

    @ApiModelProperty(value = "仓库ID")
    private String warehouseId;


}
