package com.wisrc.purchase.webapp.vo.purchaseRejection.set;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@ApiModel
@Valid
public class SetPurchaseRejectionVO {
    @Valid
    @NotNull(message = "[PurchaseRejectionInfo]不能为空")
    @ApiModelProperty(value = "采购拒收单基础信息集合")
    private SetPurchaseRejectionInfoVO PurchaseRejectionInfo;

    @Valid
    @NotNull(message = "[purchaseRejectionDetailsList]不能为空")
    @ApiModelProperty(value = "采购拒收单详细信息集合")
    private List<SetPurchaseRejectionDetailsVO> purchaseRejectionDetailsList;
}
