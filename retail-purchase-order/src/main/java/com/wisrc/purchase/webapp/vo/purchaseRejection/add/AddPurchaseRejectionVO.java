package com.wisrc.purchase.webapp.vo.purchaseRejection.add;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;


@Data
@ApiModel
@Valid
public class AddPurchaseRejectionVO {
    @Valid
    @NotNull(message = "[purchaseRejectionInfo]不能为空")
    @ApiModelProperty(value = "采购拒收单基础信息")
    private AddPurchaseRejectionInfoVO purchaseRejectionInfo;

    @Valid
    @ApiModelProperty(value = "采购拒收单详细信息集合")
    private List<AddPurchaseRejectionDetailsVO> purchaseRejectionDetailsList;

}
