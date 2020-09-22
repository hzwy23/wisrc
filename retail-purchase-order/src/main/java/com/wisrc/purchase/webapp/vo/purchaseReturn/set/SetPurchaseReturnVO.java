package com.wisrc.purchase.webapp.vo.purchaseReturn.set;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@ApiModel
public class SetPurchaseReturnVO {
    @Valid
    private SetPurchaseReturnInfoVO purchaseReturnInfo;
    @Valid
    @NotEmpty(message = "采购退货单列表不能为空")
    private List<SetPurchaseReturnDetailsVO> purchaseReturnDetailsList;
}
